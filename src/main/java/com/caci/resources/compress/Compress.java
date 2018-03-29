package main.java.com.caci.resources.compress;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;

public class Compress {
	
	// Object for creating a compressed bzip2 file
	private BZip2CompressorOutputStream compressOut;
	
	// Object for decompression of a compressed bzip2 file
	private BZip2CompressorInputStream compressIn;
	
	public Compress() {
	}

	public void CompressFile(Path path) {
		
		// Memory usage is 400k + (9 * blocksize)		
		// blocksise defaults to 9 (Larger the blocksize better compression. Max is 9)
		
		try {
			// Pull file into InputStream using the given path
			InputStream in = Files.newInputStream(path);
			// Select output location and set to OutputStream
			OutputStream out = Files.newOutputStream(Paths.get("test.bz2"));
			
			// Created a BufferedOutputStream so we don't get a memory error
			BufferedOutputStream buffOut = new BufferedOutputStream(out);
			
			long time = System.currentTimeMillis();
					
			// Set compress object to the BZip2 Compression Output
			compressOut = new BZip2CompressorOutputStream(buffOut);
			
			int buffersize = 1000*1000;
			final byte[] buffer = new byte[buffersize];
			int n = 0;
			while ((n = in.read(buffer)) != -1) {
				System.out.println("Compressing");
				// Write compressed bytes to the BufferedOutput/OutputStream
				compressOut.write(buffer);
			}
			
			// Calculate time taken to finish in minutes
			time =  ((System.currentTimeMillis() - time)/1000)/60;
			System.out.println("Compressed in: " + time + " Minutes");
			
			// Close everything out
			compressOut.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
