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

// TODO: adapt / generalize to split class

/**
 * Source code to split a file in to chunks using java nio.
 *
 */
// reference
// http://todayguesswhat.blogspot.com/2014/05/java-split-large-file-sample-code-high.html

public class FastSplit {
	// File channels do get closed ... maybe
	// public static void main(String[] args) throws IOException {
	//
	// System.out.println("Hey");
	// split("C:\\Users\\Dragon\\Desktop\\test\\game.iso");
	// // calculateChecksums("hey", "C:\\Users\\Dragon\\Desktop\\test\\");
	//
	// }

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
					throw new SizeLimitExceededException();
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
					throw new FileAlreadyExistsException(folderPartsPath);
				}

			} else {
				throw new FileNotFoundException("Could not find directory");
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

						// System.out.println(String.format(
						// "Byte buffer has %d remaining bytes; chunk has %d bytes free; writing up to
						// %d bytes to chunk %d",
						// buffer.remaining(), chunkBytesFree, bytesToWrite, outputChunkNumber));

						// set limit in buffer up to where bytes can be read
						buffer.limit(bytesWrittenFromBuffer + bytesToWrite);

						int bytesWritten = outputChannel.write(buffer);

						// increment
						outputChunkBytesWritten += bytesWritten;
						bytesWrittenFromBuffer += bytesWritten;
						totalBytesWritten += bytesWritten;

						// TODO test;
						model.setSplitProgress(((double) totalBytesWritten / (double) (sourceChannel.size() * 1.1)));
						// System.out.println( totalBytesWritten + " " + sourceChannel.size() + " " +
						// ((double)totalBytesWritten / (double)sourceChannel.size()));

						// System.out.println(String.format(
						// "Wrote %d to chunk; %d bytes written to chunk so far; %d bytes written from
						// buffer so far; %d bytes written in total",
						// bytesWritten, outputChunkBytesWritten, bytesWrittenFromBuffer,
						// totalBytesWritten));

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

					buffer.clear();
				}

			} finally {
				closeChannel(outputChannel);
			}

		} finally {
			closeChannel(sourceChannel);
		}

		calculateChecksums(splitInputFile, folderPartsPath, model);
		/* TODO: make this not jank */
		// quick and dirty way of showing checksum progress...
		model.setSplitProgress(1);

		// System.out.println("done");
	}

	// TODO: make this better :\ cant use fancy byte[] or files with nio
	// TODO: Fix checksum bug (exclude calculation for checksum file
	// TODO: implement quotes to prevent comma delimited breakage
	public static void calculateChecksums(File splitInputFile, String splitPartsDir, Model model) throws IOException {

		File dir = new File(splitPartsDir);
		Checksum test;
		FileWriter fileWriter = null;
		try {

			String checksumFileName = splitInputFile.getName() + ".crc32";

			ArrayList<String> fileChecksumList = new ArrayList<String>();

			// TODO: error handling stuff
			String fileName = splitInputFile.getName();

			String fileChecksum = String.format("%s,%d\n", fileName, (new Checksum(splitInputFile)).getCheckSum());

			fileChecksumList.add(fileChecksum);

			File[] directoryListing = dir.listFiles();

			Arrays.sort(directoryListing, new Comparator<File>() {

				@Override
				public int compare(File o1, File o2) {
					String file1Part = (o1.getName()).replaceAll("\\D", "");
					String file2Part = (o2.getName()).replaceAll("\\D", "");
					Integer file1PartNo = Integer.parseInt(file1Part);
					Integer file2PartNo = Integer.parseInt(file2Part);
					return file1PartNo.compareTo(file2PartNo);
				}

			});

			if (directoryListing != null) {
				double progress = .1 / directoryListing.length;
				for (File child : directoryListing) {

					test = new Checksum(child);

					fileChecksumList.add(child.getName() + "," + test.getCheckSum() + "\n");

					model.setSplitProgress(model.getSplitProgressBarValue() + progress);

				}
			}

			fileWriter = new FileWriter(dir.getAbsolutePath() + File.separator + checksumFileName);

			for (String output : fileChecksumList) {

				fileWriter.write(output);
			}

		} finally {

			fileWriter.close();

		}
	}
}
