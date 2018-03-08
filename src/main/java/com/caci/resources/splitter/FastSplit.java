package main.java.com.caci.resources.splitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.nio.file.Paths;

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

	public static void split(File splitInputFile, File splitOutputDir, long inputSize)
			throws IOException, FileNotFoundException, FileAlreadyExistsException {

		// file parts
		long splitSize = 145997824;

		// 256 megabyte memory buffer for reading source file
		int bufferSize = 256 * 1048576;

		// input file name
		File source = splitInputFile;
		System.out.println(splitInputFile.getName());

		// output directory
		File output = splitOutputDir;

		// channel to read a file
		FileChannel sourceChannel = null;
		
		String folderPath;

		/* TODO: maybe create a new directory? */
		// check if output path exists and is a directory
		if (output.exists() && output.isDirectory()) {
			
			// file name without file extention
			String fileName = source.getName().replaceFirst("[.][^.]+$", "");
			// new directory path
			folderPath = output.getAbsolutePath() + File.separator + fileName +" parts";
			System.out.println(folderPath);
			
			File filePartsFolder = new File(folderPath);
			
			if(!filePartsFolder.mkdir()) {
				throw new FileAlreadyExistsException(folderPath);
			}
		} else {
			System.out.println("folder doesn't exist bro");
			throw new FileNotFoundException("Could not find directory");
		}

		try {

			// file channel for source file byte stream
			sourceChannel = new FileInputStream(source).getChannel();

			// buffer for reading
			ByteBuffer buffer = ByteBuffer.allocateDirect(bufferSize);

			// total bytes written to output
			long totalBytesWritten = 0;

			// calculate number of chunks
			double numberOfChunks = Math.ceil(sourceChannel.size() / (double) splitSize);

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
							String outputPath = folderPath + File.separator + source.getName();
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

						System.out.println(String.format(
								"Byte buffer has %d remaining bytes; chunk has %d bytes free; writing up to %d bytes to chunk",
								buffer.remaining(), chunkBytesFree, bytesToWrite));

						// set limit in buffer up to where bytes can be read
						buffer.limit(bytesWrittenFromBuffer + bytesToWrite);

						int bytesWritten = outputChannel.write(buffer);

						// increment
						outputChunkBytesWritten += bytesWritten;
						bytesWrittenFromBuffer += bytesWritten;
						totalBytesWritten += bytesWritten;
						System.out.println(String.format(
								"Wrote %d to chunk; %d bytes written to chunk so far; %d bytes written from buffer so far; %d bytes written in total",
								bytesWritten, outputChunkBytesWritten, bytesWrittenFromBuffer, totalBytesWritten));

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

		// calculateChecksums("hi", outputString);

		System.out.println("done");
	}

	// TODO: make this better :\ cant use fancy byte[] or files with nio
	public static void calculateChecksums(String filePath, String dirPath) {
		File dir = new File(dirPath);
		Checksum test;
		PrintWriter f0 = null;
		try {

			f0 = new PrintWriter(new FileWriter(dirPath + "\\test.crc32"));

			File[] directoryListing = dir.listFiles();
			f0.println("hi");
			if (directoryListing != null) {
				for (File child : directoryListing) {
					// Do something with child

					test = new Checksum(child);
					f0.println(child.getName() + "," + test.getCheckSum());
					System.out.println(test.getCheckSum());
				}
				f0.close();
			}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		} finally {
			f0.close();
		}
	}
}
