package main.java.com.caci.model;

import java.util.Observable;

public class Model extends Observable{
	String inputPath = "";
	String outputPath = "";
	
	// file paths for splitter
	String splitInputPath = "";
	String splitOutputPath = "";
	
	// update split file input path
	public void setSplitInputPath(String inputPath) {
		this.splitInputPath = inputPath;
		String out = "0" + inputPath;
		setChanged();
		notifyObservers(out);
	}

	
	// update split output directory path
	public void setSplitOutputPath(String outputPath) {
		this.splitOutputPath = outputPath;
		String out = "1" + outputPath;
		setChanged();
		notifyObservers(out);
	}
	

}
