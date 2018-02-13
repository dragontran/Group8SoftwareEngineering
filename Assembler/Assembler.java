package Assembler;

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
import java.util.List;

import Checksum.Checksum;

public class Assembler {

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
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}

		System.out.println("Combining files: "+filename+".part0 to "+filename+".part"+numparts);

		try {
			fos = new FileOutputStream(ofile,true);

			for (File file : list) {
				fis = new FileInputStream(file);
				fileBytes = new byte[(int) file.length()];
				bytesRead = fis.read(fileBytes, 0,(int)  file.length());
				assert(bytesRead == fileBytes.length);
				assert(bytesRead == (int) file.length());
				fos.write(fileBytes);
				fos.flush();
				fileBytes = null;
				fis.close();
				fis = null;
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
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
