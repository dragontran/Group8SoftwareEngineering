package main.java.com.caci.controller;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import main.java.com.caci.application.Main;
import main.java.com.caci.model.Table;

public class ChecksumTabController implements Observer{
	
	@FXML
	private TextField srcDirTextField;
	
	@FXML
	private Button srcDirBrowseBtn;
	
	@FXML
	private Button removePartBtn;
	
	@FXML
	private Button addPartBtn;

	@FXML
	private Button clearAllPartsBtn;

	@FXML
	private ProgressBar progressBar;
	
	@FXML
	private TableView<Table> tableId;
	
	// DEFINE TABLE
	@FXML
	private TableColumn<Table, String> iName;
	@FXML
	private TableColumn<Table, String> iSize;
	@FXML
	private TableColumn<Table, String> iPath;
	@FXML
	private TableColumn<Table, String> iChecksum;
	
	// DEFINE VARIABLES
	private int iNumber = 1;
	// CREATE TABLE DATA
	final ObservableList<Table> data = FXCollections.observableArrayList(
			new Table("v.jpg", "Name 1", "caci/", "????"));
	
	private MainController mainController;
	
	@FXML
	void clearAllParts(ActionEvent event) {

	}
	
	@FXML
	void getSrcDirectory(ActionEvent event) {
		// open file chooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select File");

		// set local path as default path
		File defaultDirectory = new File(System.getProperty("user.dir"));
		fileChooser.setInitialDirectory(defaultDirectory);

		// show chooser
		// disable main stage when chooser is open
		File file = fileChooser.showOpenDialog(mainController.stage());

		// TODO: error handling
		if (file != null) {

			// update split file input path in model
			mainController.model().setSplitInputPath(file.getAbsolutePath());
		}
		srcDirTextField.setText(file.getAbsolutePath());
		
		iName.setCellValueFactory(new PropertyValueFactory<Table, String>("firstName"));
		iSize.setCellValueFactory(new PropertyValueFactory<Table, String>("lastName"));
		iPath.setCellValueFactory(new PropertyValueFactory<Table, String>("birthday"));
		iChecksum.setCellValueFactory(new PropertyValueFactory<Table, String>("birthday"));
		
		tableId.setItems(getPeople());
	}
	
	@FXML
	void removePart(ActionEvent event) {

	}
	
	// set main controller
	public void injectMainController(MainController mainController) {
		this.mainController = mainController;
	}
	
	@FXML
	void addPart(ActionEvent event) {

	}

	@Override
	public void update(Observable o, Object arg) {
		
	}

	public ObservableList<Table> getPeople(){
		
		ObservableList<Table> people = FXCollections.observableArrayList();
		people.add(new Table("Frank", "Sinatra", "ree", "ree"));
		
		return people;
	}
}
