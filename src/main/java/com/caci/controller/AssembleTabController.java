package main.java.com.caci.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class AssembleTabController {

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

	@FXML
	void addPart(ActionEvent event) {

	}

	@FXML
	void clearAllParts(ActionEvent event) {

	}

	@FXML
	void getOutputPath(ActionEvent event) {

	}

	@FXML
	void getSrcDirectory(ActionEvent event) {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("hello, andrew");
    	alert.setHeaderText(null);
    	alert.setContentText("have fun >:)");
    	alert.showAndWait();
	}

	@FXML
	void joinFileParts(ActionEvent event) {

	}

	@FXML
	void removePart(ActionEvent event) {

	}

}
