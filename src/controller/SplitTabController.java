package controller;


import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;


public class SplitTabController {

    @FXML
    private TextField srcTextField;

    @FXML
    private Button srcBrowseBtn;

    @FXML
    private TextField outTextField;

    @FXML
    private Button outBrowseBtn;

    @FXML
    private ProgressBar statusBar;

    @FXML
    private RadioButton bytesRadioBtn;

    @FXML
    private RadioButton partsRadioBtn;

    @FXML
    private TextField partsTextField;

    @FXML
    private TextField bytesTextField;

    @FXML
    private ComboBox<String> bytesSizeComboBox;

    @FXML
    private Button splitBtn;

    @FXML
    void getOutDirectory(ActionEvent event) {
    	// open directory chooser 
    	DirectoryChooser dirChooser = new DirectoryChooser();
    	dirChooser.setTitle("Select Output Directory");
    	
    	// set local path as default path
    	File defaultDirectory = new File(System.getProperty("user.dir"));
    	dirChooser.setInitialDirectory(defaultDirectory);
    	
    	// show chooser
    	File file = dirChooser.showDialog(null);
    	if(file != null) {
    		System.out.println(file.getAbsolutePath());
    	}
    }
    
 
    @FXML
    void getSrcFile(ActionEvent event) {
    	// open file chooser
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Select File");
    	
    	// set local path as default path
    	File defaultDirectory = new File(System.getProperty("user.dir"));
    	fileChooser.setInitialDirectory(defaultDirectory);
    	
    	// show chooser
    	File file = fileChooser.showOpenDialog(null);
    	if(file != null) {
    		System.out.println(file.getAbsolutePath());
    	}
    }
    
    @FXML
    void splitFile(ActionEvent event) {

    }
    
    @FXML
    public void initialize() {
    	bytesSizeComboBox.getItems().clear();
    	bytesSizeComboBox.getItems().addAll("bytes","kilobytes" ,"megabytes", "gigabytes");
    	bytesSizeComboBox.getSelectionModel().select("megabytes");
    }

		// set local path as default path
		File defaultDirectory = new File(System.getProperty("user.dir"));
		dirChooser.setInitialDirectory(defaultDirectory);

		// show chooser
		File file = dirChooser.showDialog(null);
		if (file != null) {
			// update split output directory path in model
			mainController.model().setSplitOutputPath(file.getAbsolutePath());
		}

	}

	@FXML
	void getSrcFile(ActionEvent event) {
		// open file chooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select File");

		// set local path as default path
		File defaultDirectory = new File(System.getProperty("user.dir"));
		fileChooser.setInitialDirectory(defaultDirectory);

		// show chooser
		File file = fileChooser.showOpenDialog(null);

		// TODO: error handling
		if (file != null) {

			// update split file input path in model
			mainController.model().setSplitInputPath(file.getAbsolutePath());
		}

	}

	@FXML
	void splitFile(ActionEvent event) {

	}

	@FXML
	public void initialize() {
		// initialize combo box with items
		bytesSizeComboBox.getItems().clear();
		bytesSizeComboBox.getItems().addAll("bytes", "kilobytes", "megabytes", "gigabytes");
		bytesSizeComboBox.getSelectionModel().select("megabytes");
	}

	@Override
	public void update(Observable o, Object arg) {
		// Update text field with file path
		String updateInput = (String) arg;
		char flag = updateInput.charAt(0);
		updateInput = updateInput.substring(1);
		
		// TODO: make this better
		// dumb way to determine which component to update
		if (flag == '0') {
			srcTextField.setText(updateInput);
		} else if (flag == '1') {
			outTextField.setText(updateInput);
		}
		

	}

}
