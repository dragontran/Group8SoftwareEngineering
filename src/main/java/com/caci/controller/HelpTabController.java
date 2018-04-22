package main.java.com.caci.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class HelpTabController {

    private String imageDirectory = "/main/java/com/caci/images/";

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
