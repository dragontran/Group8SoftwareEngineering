package Assembler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
			fos.close();
			fos = null;
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
