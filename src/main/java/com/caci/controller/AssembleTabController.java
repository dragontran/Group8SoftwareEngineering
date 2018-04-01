package main.java.com.caci.controller;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.javafx.collections.ObservableListWrapper;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import main.java.com.caci.model.AssembleTableElement;

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
	private TableView<AssembleTableElement> filePartsTable;

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
		// open file chooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select a File to Add");

		// set local path as default path
		File defaultDirectory = new File(System.getProperty("user.dir"));
		fileChooser.setInitialDirectory(defaultDirectory);

		// show chooser
		// disable main stage when chooser is open
		File file = fileChooser.showOpenDialog(mainController.stage());

		if (file != null) {
			mainController.model().addFileToList(file);
		}
	}

	@FXML
	void clearAllParts(ActionEvent event) {
		mainController.model().clearPartsList();
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

		if (file != null) {
			// update join source directory path in model
			mainController.model().setJoinSrcDirPath(file.getAbsolutePath());

			// get files from specified directory and sort alphabetically (i.e. crc32 then parts0 -> partsN)
			File[] dirFiles = file.listFiles();
			// sort with crc32 first, then by file part number, finally by extra files
			Arrays.sort(dirFiles, new Comparator<File>() {

				@Override
				public int compare(File o1, File o2) {
					// put crc32 first
					if (o1.getName().contains(".crc32")) {
						return -1;
					} else if (o2.getName().contains(".crc32")) {
						return 1;
					}
					// compare by part number
					if (o1.getName().contains(".part") && o2.getName().contains(".part")) {
						String file1Part = (o1.getName()).replaceAll("\\D", "");
						String file2Part = (o2.getName()).replaceAll("\\D", "");
						Integer file1PartNo = Integer.parseInt(file1Part);
						Integer file2PartNo = Integer.parseInt(file2Part);
						return file1PartNo.compareTo(file2PartNo);
					} else {
						// put part files before extra files
						if (o1.getName().contains(".part")) {
							return -1;
						} else if (o2.getName().contains(".part")) {
							return 1;
						}
						return o1.compareTo(o2);
					}
				}

			});

			// populate table 
			for (File f : dirFiles) {
				mainController.model().addFileToList(f);
			}
		}
	}

	// join files
	@FXML
	void joinFileParts(ActionEvent event) {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Task<Void> t = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				mainController.model().assembleFile();
				return null;
			}

		};

		// exception handling for join thread
		t.setOnFailed(evt -> {
			System.err.println("The task failed with the following exception:");
			System.out.println(t.getException().getMessage());

			//TODO: Include relevant headers for errors from assemble function
			if (t.getException().getMessage().equals("Output directory has not been selected!")) {
				errorAlert("Error", "No Output Directory", (t.getException().getMessage()));
			} else if (t.getException().getMessage().equals("Selected output directory does not exist!")) {
				errorAlert("Error", "Directory Doesn't Exist", (t.getException().getMessage()));
			} else if (t.getException().getMessage().equals("Selected output directory is not a directory!")) {
				errorAlert("Error", "Not A Directory", (t.getException().getMessage()));
			} else if (t.getException().getMessage().equals("List of file parts to assemble is empty!")) {
				errorAlert("Error", "Empty Parts List", (t.getException().getMessage()));
			} else if (t.getException().getMessage().equals("Assembled files must be a .part or .crc32 file!")) {
				errorAlert("Error", "Invalid File Type", (t.getException().getMessage()));
			} else if (t.getException().getMessage().equals("There must be only one .crc32 file!")) {
				errorAlert("Error", "Multiple .crc32 Files Detected", (t.getException().getMessage()));
			} else {
				errorAlert("Error", "Unknown Error", (t.getException().getMessage()));
			}


		});
		executorService.submit(t);
	}

	@FXML
	void removePart(ActionEvent event) {
		AssembleTableElement element = filePartsTable.getSelectionModel().getSelectedItem();
		mainController.model().removeFileFromList(element);
	}

	// set main controller
	public void injectMainController(MainController mainController) {
		this.mainController = mainController;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof String) {
			if (arg.equals("clear")) {
				filePartsTable.getItems().clear();
			}

			// Update text field with file path
			String updateInput = (String) arg;
			char flag = updateInput.charAt(0);
			updateInput = updateInput.substring(1);

			if (flag == '3') {
				srcDirTextField.setText(updateInput);
			} else if (flag == '4') {
				outputTextField.setText(updateInput);
			}
		} else if (arg instanceof AssembleTableElement) {
			// remove the element from the table
			filePartsTable.getItems().remove(arg);
		} else if (arg instanceof ObservableListWrapper<?>){
			if (!((ObservableList<?>)arg).isEmpty() && ((ObservableList<?>)arg).get(0) instanceof AssembleTableElement) {
				ObservableList<AssembleTableElement> list = (ObservableList<AssembleTableElement>) arg;
				for (AssembleTableElement e : list) {
					if (!filePartsTable.getItems().contains(e)) {
						filePartsTable.getItems().add(e);
					}
				}
			}
		} else {
			Double progress = (Double) arg;
			progressBar.setProgress(progress);
		}
	}

	public void errorAlert(String title, String header, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(message);
		alert.showAndWait();
	}

}
