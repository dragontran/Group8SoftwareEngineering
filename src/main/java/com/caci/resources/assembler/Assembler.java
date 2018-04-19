package main.java.com.caci.resources.assembler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import main.java.com.caci.model.AssembleTableElement;
import main.java.com.caci.model.Model;
import main.java.com.caci.resources.checksum.*;
import main.java.com.caci.resources.exceptions.AssembleException;


public class Assembler {

	private final static int CRC32 = 0;
	private final static int ORIGINAL_FILE = 0;

	private static String filename = "";

	// assembles the files in the joinPartsList into a single file, based on the base filename of the provided files, and saves
	// the new file into the outDir (output directory).
	// several checks are performed to ensure the files provided are valid before and during the join, if an error occurs an 
	// exception is thrown and the join stops
	public static void assemble(List<AssembleTableElement> joinPartsList, File outDir, Model model) throws AssembleException {
		Model model1 = model;

		filename = "";

		// ensure files are valid before attempting to join
		if (areValidFiles(joinPartsList)) {

			// -2 to remove .crc32 and 0 index
			int numparts = joinPartsList.size() - 2;

			// create the output file given the output directory and name of the base file
			// file name assigned in areValidFiles function
			File ofile = createOutputFile(outDir,filename);

			FileOutputStream fos;
			FileInputStream fis;
			byte[] fileBytes;
			int bytesRead = 0;

			// create a list containing each file part
			ArrayList<File> partFilesArrayList = new ArrayList<File>();
			for (AssembleTableElement e : joinPartsList){
				partFilesArrayList.add(e.getFile());
			}
			File[] partFiles = partFilesArrayList.toArray(new File[partFilesArrayList.size()]);

			// sort with crc32 first then by file part number
			Arrays.sort(partFiles, new Comparator<File>() {

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

			List<File> list = new ArrayList<File>(Arrays.asList(partFiles));

			// Create list of checksums for each file part
			ArrayList<Long> checksums = getChecksumList(list,filename);

			try {
				fos = new FileOutputStream(ofile,true);

				// go through each file in the list, read the bytes of the file
				// and append to the output file, ensuring the file checksum matches
				// the checksum stored in the crc32 file
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
						fos.close();
						fos = null;
						throw new AssembleException("Part " + i + " file is invalid! The checksum does not match the checksum stored in the .crc32 file! Try redownloading it!");
					}

					// prepare for next loop iteration
					fileBytes = null;
					fis = null;
					i++;

					// update progress (numparts +1 to account for 0 indexing)
					model1.setJoinProgress((double) i/(numparts+1));
				}

				// generate a checksum for the output file
				Checksum checksum = new Checksum(ofile);

				// ensure checksums match
				if (checksum.getCheckSum() == checksums.get(ORIGINAL_FILE)){
					model1.setJoinProgress(1);
				} else {
					fos.close();
					fos = null;
					throw new AssembleException("Assembled checksum DOES NOT MATCH checksum before being split! If the error still persists try redownloading the part files and the crc32 file!");
				}

				fos.close();
				fos = null;
			} catch (FileNotFoundException e){
				throw new AssembleException("One of the " + filename + " part files is missing!");
			} catch (IOException e){
				throw new AssembleException("IOException combining part files!");
			}
		}
	}

	// gets the base file name from the provided file (returns the empty string if the file was not a .crc32 or a part file)
	private static String getBaseFileName(File file) {
		String baseFile = "";
		String fname = file.getName();
		if (fname.contains(".crc32")) {
			baseFile = fname.substring(0,fname.lastIndexOf(".crc32"));
		} else if (fname.contains(".part")) {
			baseFile = fname.substring(0,fname.lastIndexOf(".part"));
		}

		return baseFile;
	}

	// checks if there is a crc32 file, parts files corresponding to the crc32 file, and no extra files
	private static boolean areValidFiles(List<AssembleTableElement> joinPartsList) throws AssembleException {
		boolean hascrc32 = false;
		File crc32 = null;
		for (AssembleTableElement e: joinPartsList) {
			// only use .crc32 or .part files
			if (!e.getFileName().contains(".part") && !e.getFileName().contains(".crc32")) {
				throw new AssembleException("Assembled files must be a .part or .crc32 file!");
			}
			if (e.getFileName().contains(".crc32")) {
				// multiple crc32 files
				if (!hascrc32) {
					hascrc32 = true;
					crc32 = e.getFile();
				} else {
					throw new AssembleException("There must be only one .crc32 file!");
				}
			}
		}
		// no crc32 file
		if (!hascrc32) {
			throw new AssembleException("You must include a .crc32 file!");
		}
		// get base file name from crc32 file
		filename = getBaseFileName(crc32);

		// ensure there are no additional files
		for (AssembleTableElement e: joinPartsList) {
			// not the same as a base file as indicated by the crc32 file included
			if (!e.getFileName().contains(filename)) {
				throw new AssembleException("File does not have the same base file as the crc32 file!");
			}
		}

		// get all part files from the crc32 file
		ArrayList<String> partFiles = new ArrayList<String>();
		try {
			FileReader fr = new FileReader(crc32);
			BufferedReader br = new BufferedReader(fr);
			String line;

			while ((line = br.readLine()) != null){
				// don't add the original file, only part files
				if (line.contains(".part")) {
					partFiles.add(line.substring(0, line.indexOf(',')));
				}
			}
			br.close();
			fr.close();
		} catch (FileNotFoundException e){
			throw new AssembleException(filename + ".crc32 not found!");
		} catch (IOException e){
			throw new AssembleException("IOException reading .crc32 file!");
		}

		// go through the parts list and remove all files present from partFiles array
		for (AssembleTableElement e : joinPartsList) {
			// only check part files (not crc32)
			if (e.getFileName().contains(".part")) {
				if (partFiles.contains(e.getFileName())) {
					partFiles.remove(e.getFileName());
				} 
			}
		}
		
		// indicates missing part file(s) (size should be 0 if everything is present because everything would have been removed)
		if (partFiles.size() != 0) {
			// multiple missing files
			if (partFiles.size() > 1) {
				StringBuilder missingFiles = new StringBuilder();
				for (String missingFile : partFiles) {
					missingFiles.append(missingFile);
					missingFiles.append(", ");
				}
				throw new AssembleException("The parts list is missing the following files: " + missingFiles.toString().substring(0,missingFiles.length()-2) + "!");
			// one file missing
			} else {
				throw new AssembleException("The parts list is missing the following file: " + partFiles.get(0) + "!");
			}
		}

		return true;
	}

	// Gets array list of checksums from the csv for the specified filename
	private static ArrayList<Long> getChecksumList(List<File> list, String filename) throws AssembleException {
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
			throw new AssembleException(filename + ".crc32 not found!");
		} catch (IOException e){
			throw new AssembleException("IOException reading .crc32 file!");
		}
		return checksums;
	}

	// Creates file at specified directory with given filename
	private static File createOutputFile(File outDir, String filename) throws AssembleException {
		File ofile = null;

		try {
			ofile = new File(outDir.getAbsolutePath() + File.separator + filename);
			if (!ofile.createNewFile()) {
				throw new AssembleException("Output file already exists in output directory and will be overwritten!");
			}
		} catch (IOException e) {
			throw new AssembleException("IOException creating output file!");
		}

		return ofile;
	}

}
