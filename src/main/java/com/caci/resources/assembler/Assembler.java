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
import java.util.Comparator;
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
		
		ArrayList<Long> checksums = getChecksumList(filename);

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
	public static void assemble(File srcDir, File outDir, Model model) {
		Model model1 = model;
		
		Instant start = Instant.now();
		 
		String filename = getBaseFileName(srcDir);
		String newfile = filename;
		
		//TODO: make work for more than just srcDir (-1 to remove crc32) (-1 to 0 index)
		// this means use the list (not yet implemented)
		int numparts = srcDir.list().length - 2;
		File ofile = null;

		// Creates file at specified location
		try {
			 ofile = new File(outDir.getAbsolutePath() + File.separator + filename);
			if (ofile.createNewFile()) {
				System.out.printf("File Created"); //TODO: Pass Confirmation to User?
			} else {
				System.out.printf("File already exists"); //TODO: Warn user and allow to overwrite file?
			}
		} catch (IOException e) {
			System.out.printf(e.getMessage());
		}
		FileOutputStream fos;
		FileInputStream fis;
		byte[] fileBytes;
		int bytesRead = 0;

		// create a list containing each file part
		File[] srcDirFiles = srcDir.listFiles();
		// sort with crc32 first then by file part number
		Arrays.sort(srcDirFiles, new Comparator<File>() {

			@Override
			public int compare(File o1, File o2) {
				if (o1.getName().contains(".crc32")) {
					return -1;
				} else if (o2.getName().contains(".crc32")) {
					return 1;
				}
				String file1Part = (o1.getName()).replaceAll("\\D", "");
				String file2Part = (o2.getName()).replaceAll("\\D", "");
				Integer file1PartNo = Integer.parseInt(file1Part);
				Integer file2PartNo = Integer.parseInt(file2Part);
				return file1PartNo.compareTo(file2PartNo);
			}

		});
		
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
			System.out.println("Combined file saved as: " + newfile);
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

	// Gets arraylist of checksums from the csv for the specified filename
	private static ArrayList<Long> getChecksumList(String filename) {
		ArrayList<Long> checksums = new ArrayList<Long>();
		try {
			FileReader fr = new FileReader(filename + ".checksums.csv");
			BufferedReader br = new BufferedReader(fr);
			String line;

			while ((line = br.readLine()) != null){
				checksums.add(Long.valueOf(line.substring(line.indexOf(',')+1, line.length())));
			}

			br.close();
			fr.close();
		} catch (FileNotFoundException e){
			System.out.println(filename + ".checksums.csv" + " not found :(");
		} catch (IOException e){
			System.out.println("IOException :(");
		}
		return checksums;
	}
}
