package Splitter;

import java.io.*;
import java.time.Duration;
import java.time.Instant;

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

			System.out.println(filename + " is " + inputFile.length() + " bytes long");


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
				byteChunkPart = null;
				filePart = null;
				nChunks++;
			}
			Instant end = Instant.now();
			System.out.println(inputFile.getName() + " was split into " + (nChunks-1) + " parts");
			Duration diff = Duration.between(start, end);
			System.out.println("Time elapsed: " + diff.toMillis() + " ms");

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