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

}
