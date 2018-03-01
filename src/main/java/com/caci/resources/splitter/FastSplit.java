import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

// TODO: adapt / generalize to split class

/**
 * Source code to split a file in to chunks using java nio.
 *
 */
// reference
// http://todayguesswhat.blogspot.com/2014/05/java-split-large-file-sample-code-high.html

public class FastSplit {
	// File channels do get closed ... maybe
	public static void main(String[] args) throws IOException {

		System.out.println("Hey");
		split("C:\\Users\\Dragon\\Desktop\\test\\game.iso");
		// calculateChecksums("hey", "C:\\Users\\Dragon\\Desktop\\test\\");

	}

	private static void closeChannel(FileChannel channel) {
		if (channel != null) {
			try {
				channel.close();
			} catch (Exception ignore) {
				;
			}
		}
	}

	public static void split(String fileName) throws IOException {

		// 500 mb file parts
		long splitSize = 145997824;

		// 256 Megabyte memory buffer for reading source file
		int bufferSize = 256 * 1048576;

		// input file name
		String source = fileName;

		// channel to read a file
		FileChannel sourceChannel = null;

		Path path = Paths.get(source);

		/* TODO: maybe create a new directory? */
		// if (Files.notExists(path)) {
		// System.out.println("test? idk");
		// }

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
							String outputName = String.format(outputFileFormat, source, outputChunkNumber);

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
						// %d bytes to chunk",
						// buffer.remaining(), chunkBytesFree, bytesToWrite));

						// set limit in buffer up to where bytes can be read
						buffer.limit(bytesWrittenFromBuffer + bytesToWrite);

						int bytesWritten = outputChannel.write(buffer);

						// increment
						outputChunkBytesWritten += bytesWritten;
						bytesWrittenFromBuffer += bytesWritten;
						totalBytesWritten += bytesWritten;

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

		calculateChecksums("hi", "C:\\Users\\Dragon\\Desktop\\test\\");
	}

	// TODO: make this better :\ cant use fancy byte[] or files with nio
	public static void calculateChecksums(String filePath, String dirPath) {
		File dir = new File(dirPath);
		Checksum test;
		PrintWriter f0 = null;
		try {

			f0 = new PrintWriter(new FileWriter(dirPath + "\\checksum.crc32"));

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
