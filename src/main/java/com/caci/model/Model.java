package main.java.com.caci.model;

import java.io.File;
import java.util.List;
import java.util.Observable;

import javafx.collections.FXCollections;

import main.java.com.caci.resources.assembler.Assembler;
import main.java.com.caci.resources.exceptions.AssembleException;
import main.java.com.caci.resources.exceptions.SplitException;
import main.java.com.caci.resources.splitter.FastSplit;

public class Model extends Observable {

	// file paths for splitter
	private File splitInputFile;
	private File splitOutputDir;

	// file paths for assembler
	private File joinOutFileDir;

	// file paths for checksum
	private File checksumSrcFileDir;

	// file list for assembler
	private List<AssembleTableElement> joinPartsList;

	// file list for checksum
	private List<Table> checksumPartsList;

	// progress bar values
	private double splitProgressBarValue;
	private double joinProgressBarValue;

	public Model() {
		this.splitInputFile = null;
		this.splitOutputDir = null;
		this.joinOutFileDir = null;
		this.checksumSrcFileDir = null;
		this.checksumPartsList = FXCollections.observableArrayList();
		this.joinPartsList = FXCollections.observableArrayList();
		this.splitProgressBarValue = 0.0;
		this.joinProgressBarValue = 0.0;
	}

	// update checksum input directory path
	public void setChecksumSrcDirPath(String checksumSrcDirPath) {
		this.checksumSrcFileDir = new File(checksumSrcDirPath);

		// TODO: make output better
		String out = "6" + checksumSrcDirPath;
		setChanged();
		notifyObservers(out);
	}

	public void addChecksumFileToList(File file) {
		// do not add directories to the list
		if (!file.isDirectory()) {
			// add if not already in list
			Table element = new Table(file);
			if (!this.checksumPartsList.contains(element)) {
				this.checksumPartsList.add(element);

				setChanged();
				notifyObservers(this.checksumPartsList);
			}
		}
	}

	public void removeChecksumFromList(Table element) {
		this.checksumPartsList.remove(element);

		setChanged();
		notifyObservers(element);
	}

	public void clearAllChecksum(){
		this.checksumPartsList.clear();
		this.setChecksumSrcDirPath("");

		String out = "clear_checksum";
		setChanged();
		notifyObservers(out);
	}

	// update split file input path
	public void setSplitInputPath(String inputPath) {
		this.splitInputFile = new File(inputPath);
		this.setSplitProgress(0.0);

		// TODO: make output better
		String out = "0" + inputPath;
		setChanged();
		notifyObservers(out);
	}

	// update split output directory path
	public void setSplitOutputPath(String outputPath) {
		this.splitOutputDir = new File(outputPath);
		this.setSplitProgress(0.0);

		// TODO: make output better
		String out = "1" + outputPath;
		setChanged();
		notifyObservers(out);
	}

	// split file
	public void splitFile(long size, boolean parts) throws Exception {
		Model model = this;
		setSplitProgress(0.0);

		/* quick error checking */
		// check for input path
		if (this.splitInputFile == null) {
			throw new SplitException("Input file has not been selected");
			// check for output path
		} else if (this.splitOutputDir == null) {
			throw new SplitException("Output directory has not been selected!");
			// check if input file exists
		} else if (!this.splitInputFile.exists()) {
			throw new SplitException("Selected input file does not exist!");
			// check if output directory exists
		} else if (!this.splitOutputDir.exists()) {
			throw new SplitException("Selected output directory does not exist!");
			// check if input file is a file
		} else if (!this.splitInputFile.isFile()) {
			throw new SplitException("Selected input file is not a file");
			// check if output dir is a directory
		} else if (!this.splitOutputDir.isDirectory()) {
			throw new SplitException("Selected output directory is not a directory");
			// check if file can be read
		} else if (!this.splitInputFile.canRead()) {
			throw new SplitException("Selected input file cannot be read");
		}

		FastSplit.split(splitInputFile, splitOutputDir, size, parts, model);

	}

	public void setSplitProgress(double value) {
		this.splitProgressBarValue = value;

		String splitProgressOutput = "5" + Double.toString(this.splitProgressBarValue);

		setChanged();
		notifyObservers(splitProgressOutput);
	}

	// update join src file dir path
	public void setJoinSrcDirPath(String joinSrcDirPath) {
		String out = "3" + joinSrcDirPath;
		setChanged();
		notifyObservers(out);
	}

	// update join output directory path
	public void setJoinOutDirPath(String outputPath) {
		this.joinOutFileDir = new File(outputPath);
		this.setJoinProgress(0.0);


		String out = "4" + outputPath;
		setChanged();
		notifyObservers(out);
	}

	// join file
	public void assembleFile() throws Exception{
		Model model = this;
		setJoinProgress(0.0);

		// check if join table is empty
		if (this.joinPartsList.isEmpty()) {
			throw new AssembleException("List of file parts to assemble is empty!");
			// check for output path
		} else if (this.joinOutFileDir == null) {
			throw new AssembleException("Output directory has not been selected!");
			// check if output directory exists
		} else if (!this.joinOutFileDir.exists()) {
			throw new AssembleException("Selected output directory does not exist!");
			// check if output dir is a directory
		} else if (!this.joinOutFileDir.isDirectory()) {
			throw new AssembleException("Selected output directory is not a directory!");
		} else {
			Assembler.assemble(joinPartsList, joinOutFileDir, model);
		}	
	}

	public void setJoinProgress(double value) {
		this.joinProgressBarValue = value;

		String joinProgressOutput = "2" + Double.toString(this.joinProgressBarValue);
		
		setChanged();
		notifyObservers(joinProgressOutput);
	}

	public void addFileToList(File file) {
		// Do not add directories to the list
		if (!file.isDirectory()) {
			// Add if not already in list
			AssembleTableElement element = new AssembleTableElement(file);
			if (!this.joinPartsList.contains(element)) {
				this.joinPartsList.add(element);
				this.setJoinProgress(0.0);

				setChanged();
				notifyObservers(this.joinPartsList);
			}
		}
	}

	public void removeFileFromList(AssembleTableElement element) {
		this.joinPartsList.remove(element);
		this.setJoinProgress(0.0);


		setChanged();
		notifyObservers(element);
	}

	public void clearPartsList() {
		this.joinPartsList.clear();
		this.setJoinSrcDirPath("");
		this.setJoinProgress(0.0);

		String out = "clear_parts";
		setChanged();
		notifyObservers(out);
	}	

	public double getSplitProgressBarValue() {
		return splitProgressBarValue;
	}

	public File getSplitInputFile() {
		return splitInputFile;
	}

	public File getSplitOutputDir() {
		return splitOutputDir;
	}
	
	public List<Table> getChecksumPartsList() {
		return checksumPartsList;
	}
}
