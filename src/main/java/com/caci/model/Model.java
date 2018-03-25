package main.java.com.caci.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.Observable;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import main.java.com.caci.resources.assembler.Assembler;
import main.java.com.caci.resources.splitter.FastSplit;

public class Model extends Observable {

	// file paths for splitter
	private File splitInputFile;
	private File splitOutputDir;

	// file paths for assembler
	private File joinSrcFileDir;
	private File joinOutFileDir;

	// file list for assembler
	private List<AssembleTableElement> joinPartsList;

	// progress bar values
	private double splitProgressBarValue;
	private double joinProgressBarValue;

	public Model() {
		this.splitInputFile = null;
		this.splitOutputDir = null;
		this.joinSrcFileDir = null;
		this.joinOutFileDir = null;
		this.joinPartsList = FXCollections.observableArrayList();
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

		Task<Void> task = new Task<Void>() {

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

		String splitProgressOutput = "5" + Double.toString(this.splitProgressBarValue); 

		setChanged();
		notifyObservers(splitProgressOutput);
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
	public void setJoinOutDirPath(String outputPath) {
		this.joinOutFileDir = new File(outputPath);
		this.setJoinProgress(0.0);

		// TODO: make output better
		String out = "4" + outputPath;
		setChanged();
		notifyObservers(out);
	}

	// join file
	public void assembleFile() {
		Model model = this;
		// TODO: make sure there is input and output path

		Task<Void> task = new Task<Void>() {

			// View / statusbar wouldnt update unless
			// join process was run on a concurrent thread
			@Override
			public Void call() {
				// TODO: error handling (currently caught in assemble?)
				//				try {
				Assembler.assemble(joinPartsList, joinOutFileDir, model);
				//				} catch (FileNotFoundException e) {
				//					System.out.println("file doesn't exist bro");
				//				} catch (FileAlreadyExistsException e) {
				//					// TODO: talk about how to handle this
				//					System.out.println("parts folder already exists here");
				//				} catch (IOException e) {
				//					// TODO Auto-generated catch block
				//					e.printStackTrace();
				//				}
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

	public void addFileToList(File file) {
		// add if not already in list
		AssembleTableElement element = new AssembleTableElement(file);
		if (!this.joinPartsList.contains(element)) {
			this.joinPartsList.add(element);
			
			setChanged();
			notifyObservers(this.joinPartsList);
		}
	}

	public void removeFileFromList(AssembleTableElement element) {
		this.joinPartsList.remove(element);

		setChanged();
		notifyObservers(element);
	}

	public void clearPartsList() {
		this.joinPartsList.clear();

		String out = "clear";
		setChanged();
		notifyObservers(out);
	}
}
