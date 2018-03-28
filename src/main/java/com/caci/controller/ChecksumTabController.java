package main.java.com.caci.controller;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import com.sun.javafx.collections.ObservableListWrapper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import main.java.com.caci.application.Main;
import main.java.com.caci.model.AssembleTableElement;
import main.java.com.caci.model.Table;

public class ChecksumTabController implements Observer{
	
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
	private TableView<Table> filePartsTable;

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
			mainController.model().addChecksumFileToList(file);
		}
	}
	
	@FXML
	void clearAllParts(ActionEvent event) {
		// TODO: get selected file in list	
		Table element = filePartsTable.getSelectionModel().getSelectedItem();
		//mainController.model().removeFileFromList(element);
	}
	
	@FXML
	void getSrcChecksumDirectory(ActionEvent event) {
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

			// update split file input path in model
			mainController.model().setChecksumSrcDirPath(file.getAbsolutePath());
			
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
				mainController.model().addChecksumFileToList(f);
			}
		}
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
				//outputTextField.setText(updateInput);
			}
		} else if (arg instanceof Table) {
			// remove the element from the table
			filePartsTable.getItems().remove(arg);
		} else if (arg instanceof ObservableListWrapper<?>){
			ObservableList<Table> list = (ObservableList<Table>) arg;
			for (Table e : list) {
				if (!filePartsTable.getItems().contains(e)) {
					filePartsTable.getItems().add(e);
				}
			}
		} else {
			Double progress = (Double) arg;
			progressBar.setProgress(progress);
		}
		
	}
}
