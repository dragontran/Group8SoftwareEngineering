package caci;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Splitter {	
	private static byte PART_SIZE = 100;
	
	public static void split(String filename){
		try {
			File inputFile = new File(filename);
			FileInputStream fis;
			String newFileName;
			FileOutputStream filePart;
			int fileSize = (int) inputFile.length();
			int nChunks = 0;
			int read = 0; 
			int readLength = PART_SIZE;
			byte[] byteChunkPart;
			
			fis = new FileInputStream(inputFile);
			while (fileSize > 0) {
				// don't read past the end of the file
				if (fileSize <= PART_SIZE) {
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
			System.out.println(inputFile.getName() + " was split into " + (nChunks-1) + " parts");
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
