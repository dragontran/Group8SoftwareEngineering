package main.java.com.caci.resources.assembler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.com.caci.model.Model;
import main.java.com.caci.resources.checksum.*;

public class Assembler {

	private final static int CRC32 = 0;
	private final static int ORIGINAL_FILE = 0;
	
	public static void assemble(String filename, String newfile, int numparts){
		Instant start = Instant.now();
		File ofile = new File(newfile);
		FileOutputStream fos;
		FileInputStream fis;
		byte[] fileBytes;
		int bytesRead = 0;

		// Create a list containing each file part
		List<File> list = new ArrayList<File>();
		for (int i = 0; i <= numparts; ++i){
			list.add(new File(filename+".part"+i));
		}
		
		ArrayList<Long> checksums = new ArrayList<Long>();
		try {
			FileReader fr = new FileReader(filename + ".checksums.csv");
			BufferedReader br = new BufferedReader(fr);
			String line;
			
			while ((line = br.readLine()) != null){
				checksums.add(Long.valueOf(line.substring(line.indexOf(',')+1, line.length())).longValue());
			}
			
			br.close();
			fr.close();
		} catch (FileNotFoundException e){
			System.out.println(filename + ".checksums.csv" + " not found :(");
		} catch (IOException e){
			System.out.println("IOException :(");
		}

		System.out.println("Combining files: "+filename+".part0 to "+filename+".part"+numparts);

		try {
			fos = new FileOutputStream(ofile,true);

			int i = 0;
			for (File file : list) {
				fis = new FileInputStream(file);
				fileBytes = new byte[(int) file.length()];
				bytesRead = fis.read(fileBytes, 0,(int)  file.length());
				assert(bytesRead == fileBytes.length);
				assert(bytesRead == (int) file.length());
				fos.write(fileBytes);
				fos.flush();
				fis.close();
				
				// Ensure checksums match
				Checksum checksum = new Checksum(fileBytes);
				if (checksum.getCheckSum() != checksums.get(i+1)){
					System.out.println("Part " + i + " checksum does not match saved checksum from split");
					System.out.println("Part " + i + " current checksum: " + checksum.getCheckSum());
					System.out.println("Part " + i + " saved checksum: " + checksums.get(i+1));
					// probably exit
				}
				
				fileBytes = null;
				fis = null;
				i++;
			}
			Instant end = Instant.now();
			System.out.println("Files combined successfully");
			System.out.println("Combined file saved as "+ofile.getName());
			Duration diff = Duration.between(start, end);
			System.out.println("Time elapsed: " + diff.toMillis() + " ms");
			Checksum checksum = new Checksum(ofile);
			System.out.println(newfile + " checksum: " + checksum.getCheckSum());
			
			if (checksum.getCheckSum() == checksums.get(0)){
				System.out.println("Assembled checksum matches checksum before being split");
			}
			
			fos.close();
			fos = null;
		} catch (FileNotFoundException e){
			System.out.println(filename + " part not found :(");
		} catch (IOException e){
			System.out.println("IOException");
		}
	}

	// TODO: error handling, throw instead of catch and handle in controller?
	public static void assemble(File srcDir, File outDir, Model model){
		Model model1 = model;
		
		Instant start = Instant.now();
		 
		String filename = getBaseFileName(srcDir);
		String newfile = filename;
		
		//TODO: make less terrible (-1 to remove crc32) (-1 to 0 index)
		// maybe is fine actually?
		int numparts = srcDir.list().length - 2;
		
		File ofile = new File(outDir.getAbsolutePath() + File.separator + newfile);
		FileOutputStream fos;
		FileInputStream fis;
		byte[] fileBytes;
		int bytesRead = 0;

		// create a list containing each file part
		File[] srcDirFiles = srcDir.listFiles();
		Arrays.sort(srcDirFiles);
		List<File> list = new ArrayList<File>(Arrays.asList(srcDirFiles));
		
		// Create list of checksums for each file part
		ArrayList<Long> checksums = new ArrayList<Long>();
		try {
			FileReader fr = new FileReader(list.get(CRC32));
			BufferedReader br = new BufferedReader(fr);
			String line;
			
			while ((line = br.readLine()) != null){
				checksums.add(Long.valueOf(line.substring(line.indexOf(',')+1, line.length())).longValue());
			}
			
			list.remove(CRC32);
			br.close();
			fr.close();
		} catch (FileNotFoundException e){
			System.out.println(filename + ".crc32" + " not found :(");
		} catch (IOException e){
			System.out.println("IOException :(");
		}

		System.out.println("Combining files: "+filename+".part0 to "+filename+".part"+numparts);

		try {
			fos = new FileOutputStream(ofile,true);

			int i = 0;
			for (File file : list) {
				fis = new FileInputStream(file);
				fileBytes = new byte[(int) file.length()];
				bytesRead = fis.read(fileBytes, 0,(int)  file.length());
				assert(bytesRead == fileBytes.length);
				assert(bytesRead == (int) file.length());
				fos.write(fileBytes);
				fos.flush();
				fis.close();
				
				// Ensure checksums match
				Checksum checksum = new Checksum(file);
				if (checksum.getCheckSum() != checksums.get(i+1)){
					System.out.println("Part " + i + " checksum does not match saved checksum from split");
					System.out.println("Part " + i + " current checksum: " + checksum.getCheckSum());
					System.out.println("Part " + i + " saved checksum: " + checksums.get(i+1));
					// probably exit
				}
								
				fileBytes = null;
				fis = null;
				i++;
				
				model1.setJoinProgress(i/numparts);
			}
			Instant end = Instant.now();
			Checksum checksum = new Checksum(ofile);
			System.out.println(newfile + " checksum: " + checksum.getCheckSum());
			
			if (checksum.getCheckSum() == checksums.get(ORIGINAL_FILE)){
				System.out.println("Assembled checksum matches checksum before being split");
				System.out.println("Files combined successfully");
			} else {
				System.out.println("Assembled checksum DOES NOT MATCH checksum before being split");
				System.out.println("Files NOT combined successfully");
			}
			System.out.println("Combined file saved as: "+ofile.getName());
			System.out.println("Combined file saved to directory: " + outDir.getAbsolutePath());
			
			Duration diff = Duration.between(start, end);
			System.out.println("Time elapsed: " + diff.toMillis() + " ms");
			
			model1.setJoinProgress(1);
			
			fos.close();
			fos = null;
		} catch (FileNotFoundException e){
			System.out.println(filename + " part not found :(");
		} catch (IOException e){
			System.out.println("IOException");
		}
	}
	
	private static String getBaseFileName(File srcDir) {
		String baseFile = "";
		String[] dirFiles = srcDir.list();
		for (String f : dirFiles) {
			if (f.contains(".part")) {
				baseFile = f.substring(0,f.lastIndexOf(".part"));
				break;
			}
		}
		return baseFile;
	}
}
