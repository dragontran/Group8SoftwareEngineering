package main.java.com.caci.controller;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Observable;
import java.util.Observer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;

public class AssembleTabController implements Observer {

	@FXML
	private TextField srcDirTextField;

	@FXML
	private Button srcDirBrowseBtn;

	@FXML
	private Button removePartBtn;

	@FXML
	private Button joinBtn;

	@FXML
	private TextField outputTextField;

	@FXML
	private TableView<?> filePartsTable;

	@FXML
	private Button addPartBtn;

	@FXML
	private Button clearAllPartsBtn;

	@FXML
	private ProgressBar progressBar;

	@FXML
	private Button outputBrowseBtn;

	private MainController mainController;

	@FXML
	void addPart(ActionEvent event) {

	}

	@FXML
	void clearAllParts(ActionEvent event) {

	}

	@FXML
	void getOutputPath(ActionEvent event) {
		// open directory chooser
		DirectoryChooser dirChooser = new DirectoryChooser();
		dirChooser.setTitle("Select Source Directory");

		// set local path as default path
		File defaultDirectory = new File(System.getProperty("user.dir"));
		dirChooser.setInitialDirectory(defaultDirectory);

		// show chooser
		// disable main stage when chooser is open
		File file = dirChooser.showDialog(mainController.stage());

		// TODO: error handling
		if (file != null) {
			// update join output directory path in model
			mainController.model().setJoinOutDirPath(file.getAbsolutePath());
		}
	}

	@FXML
	void getSrcDirectory(ActionEvent event) {
		// open directory chooser
		DirectoryChooser dirChooser = new DirectoryChooser();
		dirChooser.setTitle("Select Source Directory");

		// set local path as default path
		File defaultDirectory = new File(System.getProperty("user.dir"));
		dirChooser.setInitialDirectory(defaultDirectory);

		// show chooser
		// disable main stage when chooser is open
		File file = dirChooser.showDialog(mainController.stage());

		// TODO: error handling
		if (file != null) {
			// update join source directory path in model
			mainController.model().setJoinSrcDirPath(file.getAbsolutePath());
			
			// get files from specified directory and sort alphabetically (i.e. crc32 then parts0 -> partsN)
			File[] dirFiles = file.listFiles();
			// sort with crc32 first then by file part number
			Arrays.sort(dirFiles, new Comparator<File>() {

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
			// populate table 
			for (File f : dirFiles) {
				// TODO: populate table
			}
		}
	}

	@FXML
	void joinFileParts(ActionEvent event) {
		mainController.model().assembleFile();
	}

	@FXML
	void removePart(ActionEvent event) {

	}

	// set main controller
	public void injectMainController(MainController mainController) {
		this.mainController = mainController;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (arg instanceof String) {
			// Update text field with file path
			String updateInput = (String) arg;
			char flag = updateInput.charAt(0);
			updateInput = updateInput.substring(1);

			// TODO: make this better
			// dumb way to determine which component to update
			if (flag == '3') {
				srcDirTextField.setText(updateInput);
			} else if (flag == '4') {
				outputTextField.setText(updateInput);
			}
		} else {
			Double progress = (Double) arg;
			progressBar.setProgress(progress);
		}
	}

}
