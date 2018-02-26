package resources.splitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;

import resources.checksum.Checksum;

public class Splitter {	

	final static int MAX_SPLIT = 500000000;
	final static int MIN_SPLIT = 100;
	
	public static void split(String filename){
		try {
			Instant start = Instant.now();
			File inputFile = new File(filename);
			long splitSize = getSplitSize(inputFile.length());
			FileInputStream fis;
			String newFileName;
			FileOutputStream filePart;
			int fileSize = (int) inputFile.length();
			int nChunks = 0;
			int read = 0; 
			int readLength = (int)splitSize;
			byte[] byteChunkPart;
			
			PrintWriter pw = new PrintWriter(new File(filename + ".checksums.csv"));
			
			Checksum inputChecksum = new Checksum(inputFile);
			System.out.println(filename + " checksum: " + inputChecksum.getCheckSum());
			System.out.println(filename + " is " + inputFile.length() + " bytes long");

			pw.write(filename + ',' + inputChecksum.getCheckSum() + '\n');
			
			fis = new FileInputStream(inputFile);
			while (fileSize > 0) {
				// don't read past the end of the file
				if (fileSize <= splitSize) {
					readLength = fileSize;
				}
				byteChunkPart = new byte[readLength];
				read = fis.read(byteChunkPart, 0, readLength);
				fileSize -= read;
				assert (read == byteChunkPart.length);
				newFileName = filename + ".part" + Integer.toString(nChunks);
				filePart = new FileOutputStream(new File(newFileName));
				filePart.write(byteChunkPart);
				filePart.flush();
				filePart.close();
				
				Checksum checksum = new Checksum(byteChunkPart);
				System.out.println(newFileName + " checksum: " + checksum.getCheckSum());
				pw.write(newFileName + ',' + checksum.getCheckSum() + '\n');
				
				byteChunkPart = null;
				filePart = null;
				nChunks++;
			}
			Instant end = Instant.now();
			System.out.println(inputFile.getName() + " was split into " + (nChunks-1) + " parts");
			Duration diff = Duration.between(start, end);
			System.out.println("Time elapsed: " + diff.toMillis() + " ms");
			
			pw.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static long getSplitSize(long nbytes){
		long splitsize = nbytes;
		
		// min 100 byte split
		if (nbytes > MIN_SPLIT){
			splitsize = nbytes/10 + nbytes%10;
			// max 500mb split
			if (splitsize > MAX_SPLIT){
				splitsize = MAX_SPLIT;
			}
		}

		return splitsize;
	}
}
