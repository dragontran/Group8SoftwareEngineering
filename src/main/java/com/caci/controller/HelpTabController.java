package main.java.com.caci.controller;

import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class HelpTabController {

    private String imageDirectory = "/src/main/java/com/caci/images/";

    @FXML
    private TextArea titleArea;

    @FXML
    private TextArea step1Area;

    @FXML
    private TextArea step2Area;

    @FXML
    private TextArea step3Area;

    @FXML
    private TextArea step4Area;

    @FXML
    private ImageView imageView;


    @FXML
    void displayStep1Image(MouseEvent event) {
        loadImage(imageDirectory + "sourceDirectory.jpg");
    }

    @FXML
    void displayStep2Image(MouseEvent event) {
        loadImage(imageDirectory + "outputDirectory.jpg");
    }

    @FXML
    void displayStep3Image(MouseEvent event) {
        loadImage(imageDirectory + "fileParts.jpg");
    }

    @FXML
    private void loadImage(String filePath) {
        Image image = new Image(filePath);
        imageView.setImage(image);
    }
}
