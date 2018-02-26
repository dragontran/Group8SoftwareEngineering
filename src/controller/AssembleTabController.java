/**
 * Sample Skeleton for 'AssembleTabView.fxml' Controller Class
 */

package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AssembleTabController {

    @FXML // fx:id="srcDirTextField"
    private TextField srcDirTextField; // Value injected by FXMLLoader

    @FXML // fx:id="srcDirBtn"
    private Button srcDirBtn; // Value injected by FXMLLoader

    @FXML // fx:id="removeFilePartBtn"
    private Button removeFilePartBtn; // Value injected by FXMLLoader

    @FXML // fx:id="joinBtn"
    private Button joinBtn; // Value injected by FXMLLoader

    @FXML // fx:id="outputFileTextField"
    private TextField outputFileTextField; // Value injected by FXMLLoader

    @FXML // fx:id="filePartsTable"
    private TableView<?> filePartsTable; // Value injected by FXMLLoader

    @FXML // fx:id="filePartNameCol"
    private TableColumn<?, ?> filePartNameCol; // Value injected by FXMLLoader

    @FXML // fx:id="filePartSizeCol"
    private TableColumn<?, ?> filePartSizeCol; // Value injected by FXMLLoader

    @FXML // fx:id="filePartPathCol"
    private TableColumn<?, ?> filePartPathCol; // Value injected by FXMLLoader

    @FXML // fx:id="addFilePartBtn"
    private Button addFilePartBtn; // Value injected by FXMLLoader

    @FXML // fx:id="clearFilePartsBtn"
    private Button clearFilePartsBtn; // Value injected by FXMLLoader

    @FXML
    void addFilePart(ActionEvent event) {

    }

    @FXML
    void clearFileParts(ActionEvent event) {

    }

    @FXML
    void getSrcDir(ActionEvent event) {

    }

    @FXML
    void joinFileParts(ActionEvent event) {

    }

    @FXML
    void removeFilePart(ActionEvent event) {

    }

}
