package main.java.com.caci.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;

import main.java.com.caci.resources.splitter.FastSplit;

public class Model extends Observable {

	// String inputPath = "";
	// String outputPath = "";

	// file paths for splitter
	private File splitInputFile;
	private File splitOutputDir;

	public Model() {
		this.splitInputFile = null;
		this.splitOutputDir = null;

	}

	// update split file input path
	public void setSplitInputPath(String inputPath) {
		this.splitInputFile = new File(inputPath);

		// TODO: make output better
		String out = "0" + inputPath;
		setChanged();
		notifyObservers(out);
	}

	// update split output directory path
	public void setSplitOutputPath(String outputPath) {
		this.splitOutputDir = new File(outputPath);

		// TODO: make output better
		String out = "1" + outputPath;
		setChanged();
		notifyObservers(out);
	}

	// split file
	public void splitFile() {

		// TODO: make sure there is input and output path
		try {

			FastSplit.split(this.splitInputFile, this.splitOutputDir, 0);
		} catch (FileNotFoundException e) {
			System.out.println("file not found brother");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
