package main.java.com.caci.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Observable;

import javafx.concurrent.Task;
import main.java.com.caci.resources.splitter.FastSplit;

public class Model extends Observable {

	// String inputPath = "";
	// String outputPath = "";

	// file paths for splitter
	private File splitInputFile;
	private File splitOutputDir;
	
	// file paths for assembler
	private File joinSrcFileDir;
	private File joinOutputFile;

	private double splitProgressBarValue;
	private double joinProgressBarValue;

	public Model() {
		this.splitInputFile = null;
		this.splitOutputDir = null;
		this.joinSrcFileDir = null;
		this.joinOutputFile = null;
		this.splitProgressBarValue = 0.0;
		this.joinProgressBarValue = 0.0;
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
	public void splitFile(long size, boolean parts) {
		Model model = this;
		// TODO: make sure there is input and output path

		Task task = new Task<Void>() {
			
			// View / statusbar wouldnt update unless
			// split process was run on a concurrent thread
			@Override
			public Void call() {
				try {
					FastSplit.split(splitInputFile, splitOutputDir, size, parts, model);
				} catch (FileNotFoundException e) {
					System.out.println("file doesn't exist bro");
				} catch (FileAlreadyExistsException e) {
					// TODO: talk about how to handle this
					System.out.println("parts folder already exists here");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		};
		new Thread(task).start();

	}
	
	public void setSplitProgress(double value) {
		this.splitProgressBarValue = value;

		setChanged();
		notifyObservers(this.splitProgressBarValue);
	}

	// update join src file dir path
	public void setJoinSrcDirPath(String joinSrcDirPath) {
		this.joinSrcFileDir = new File(joinSrcDirPath);
		this.setJoinProgress(0.0);

		// TODO: make output better
		String out = "3" + joinSrcDirPath;
		setChanged();
		notifyObservers(out);
	}

	// update join output directory path
	public void setJoinOutputPath(String outputPath) {
		this.joinOutputFile = new File(outputPath);
		this.setSplitProgress(0.0);
		
		// TODO: make output better
		String out = "4" + outputPath;
		setChanged();
		notifyObservers(out);
	}
	
	// split file
	public void assembleFile(long size, boolean parts) {
		Model model = this;
		// TODO: make sure there is input and output path

		Task task = new Task<Void>() {
			
			// View / statusbar wouldnt update unless
			// split process was run on a concurrent thread
			@Override
			public Void call() {
				try {
					FastSplit.split(splitInputFile, splitOutputDir, size, parts, model);
				} catch (FileNotFoundException e) {
					System.out.println("file doesn't exist bro");
				} catch (FileAlreadyExistsException e) {
					// TODO: talk about how to handle this
					System.out.println("parts folder already exists here");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		};
		new Thread(task).start();

	}
	
	public void setJoinProgress(double value) {
		this.joinProgressBarValue = value;

		setChanged();
		notifyObservers(this.joinProgressBarValue);
	}
}
