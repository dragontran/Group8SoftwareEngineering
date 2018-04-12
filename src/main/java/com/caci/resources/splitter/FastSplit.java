package main.java.com.caci.resources.splitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import javax.naming.SizeLimitExceededException;

import main.java.com.caci.model.Model;
import main.java.com.caci.resources.checksum.Checksum;

/**
 * Source code to split a file in to chunks using java nio.
 */

// reference
// http://todayguesswhat.blogspot.com/2014/05/java-split-large-file-sample-code-high.html

public class FastSplit {

	private static void closeChannel(FileChannel channel) {
		if (channel != null) {
			try {
				channel.close();
			} catch (Exception ignore) {
				;
			}
		}
	}

	public static void split(File splitInputFile, File splitOutputDir, long inputSplitSize, boolean parts, Model m)
			throws IOException, FileNotFoundException, FileAlreadyExistsException, SizeLimitExceededException {
		Model model = m;

		// input file name
		File source = splitInputFile;

		// output directory
		File output = splitOutputDir;

		// channel to read a file
		FileChannel sourceChannel = null;

		String folderPartsPath;

		try {

			// file channel for source file byte stream
			sourceChannel = new FileInputStream(source).getChannel();

			// file parts
			long splitSize = 0;

			// calculate number of chunks
			double numberOfChunks = 0.0;

			long fileSize = sourceChannel.size();

			if (parts) {

				numberOfChunks = inputSplitSize;
				splitSize = (long) Math.ceil(fileSize / numberOfChunks);

			} else {
				if (inputSplitSize > fileSize) {
					throw new SizeLimitExceededException("File size is smaller than split size.");
				}

				splitSize = inputSplitSize;
				numberOfChunks = Math.ceil(fileSize / (double) splitSize);
			}

			// check if output path exists and is a directory
			if (output.exists() && output.isDirectory()) {

				// file name without file extention
				String fileName = source.getName().replaceFirst("[.][^.]+$", "");
				// new directory path
				folderPartsPath = output.getAbsolutePath() + File.separator + fileName + " parts";

				File filePartsFolder = new File(folderPartsPath);

				if (!filePartsFolder.mkdir()) {
					throw new FileAlreadyExistsException("Parts folder already exists.");
				}

			} else {
				throw new FileNotFoundException("Could not find output directory.");
			}

			// 256 megabyte memory buffer for reading source file
			int bufferSize = 256 * 1048576;

			// buffer for reading
			ByteBuffer buffer = ByteBuffer.allocateDirect(bufferSize);

			// total bytes written to output
			long totalBytesWritten = 0;

			// channel to output split files
			FileChannel outputChannel = null;

			// the file part number
			long outputChunkNumber = 0;

			// number of bytes written to file part
			long outputChunkBytesWritten = 0;

			// format for file part outputs
			String outputFileFormat = "%s.part%d";

			try {

				// loop that reads data from the file channel into the buffer
				for (int bytesRead = sourceChannel.read(buffer); bytesRead != -1; bytesRead = sourceChannel
						.read(buffer)) {

					// convert the buffer from writing data to buffer from disk to reading mode
					buffer.flip();

					// number of bytes written from buffer
					int bytesWrittenFromBuffer = 0;

					// continue creating file parts while buffer still contains stuff
					while (buffer.hasRemaining()) {

						// create a file channel for output if one doesn't exist
						if (outputChannel == null) {
							outputChunkBytesWritten = 0;

							// output file name
							String outputPath = folderPartsPath + File.separator + source.getName();
							String outputName = String.format(outputFileFormat, outputPath, outputChunkNumber);

							// increment part number
							outputChunkNumber++;

							// create new output file stream channel for file part
							outputChannel = new FileOutputStream(outputName).getChannel();
						}

						// space left in a file part
						long chunkBytesFree = (splitSize - outputChunkBytesWritten);

						// maximum bytes that should be read from current byte buffer
						int bytesToWrite = (int) Math.min(buffer.remaining(), chunkBytesFree);

						// set limit in buffer up to where bytes can be read
						buffer.limit(bytesWrittenFromBuffer + bytesToWrite);

						int bytesWritten = outputChannel.write(buffer);

						// increment
						outputChunkBytesWritten += bytesWritten;
						bytesWrittenFromBuffer += bytesWritten;
						totalBytesWritten += bytesWritten;

						// set progress bar
						model.setSplitProgress(((double) totalBytesWritten / (double) (sourceChannel.size() * 1.1)));

						// reset limit
						buffer.limit(bytesRead);

						if (totalBytesWritten == sourceChannel.size()) {

							// close file channel when there is no more bytes to write
							closeChannel(outputChannel);
							outputChannel = null;

							break;
						} else if (outputChunkBytesWritten == splitSize) {

							// close file channel when file part is filled
							closeChannel(outputChannel);
							outputChannel = null;
						}
					}
					// clear buffer
					buffer.clear();
				}

			} finally {
				// close output channel
				closeChannel(outputChannel);
			}

		} finally {
			// close file channel
			closeChannel(sourceChannel);
		}

		// calculate checksums
		calculateChecksums(splitInputFile, folderPartsPath, model);

		model.setSplitProgress(1);

	}

	// TODO: implement quotes to prevent comma delimited breakage
	public static void calculateChecksums(File splitInputFile, String splitPartsDir, Model model) throws IOException {

		File dir = new File(splitPartsDir);
		Checksum test;
		FileWriter fileWriter = null;

		try {
			// get checksum file name
			String checksumFileName = splitInputFile.getName() + ".crc32";

			ArrayList<String> fileChecksumList = new ArrayList<String>();
			
			// get split file name
			String fileName = splitInputFile.getName();

			// calculate checksum for file
			String fileChecksum = String.format("%s,%d\n", fileName, (new Checksum(splitInputFile)).getCheckSum());

			// add to list
			fileChecksumList.add(fileChecksum);

			// get files parts in parts folder
			File[] directoryListing = dir.listFiles();

			// overloaded sort to sort by part numbers
			Arrays.sort(directoryListing, new Comparator<File>() {

				@Override
				public int compare(File o1, File o2) {
					// parse part number
					String file1Part = (o1.getName()).replaceAll("\\D", "");
					String file2Part = (o2.getName()).replaceAll("\\D", "");
					
					// convert to int
					Integer file1PartNo = Integer.parseInt(file1Part);
					Integer file2PartNo = Integer.parseInt(file2Part);
					
					// compare
					return file1PartNo.compareTo(file2PartNo);
				}
			});
			
			
			if (directoryListing != null) {
				
				// progress bar value (now at 90%)
				double progress = .1 / directoryListing.length;
				
				// iterate through to file parts list 
				for (File child : directoryListing) {
					
					// calculate checksum
					test = new Checksum(child);
					
					// add file name and checksum to output list
					fileChecksumList.add(child.getName() + "," + test.getCheckSum() + "\n");

					// update progress bar
					model.setSplitProgress(model.getSplitProgressBarValue() + progress);

				}
			}

			// create checksum file
			fileWriter = new FileWriter(dir.getAbsolutePath() + File.separator + checksumFileName);

			for (String output : fileChecksumList) {

				// write output
				fileWriter.write(output);
			}

		} finally {
			// close file
			fileWriter.close();

		}
	}
}
