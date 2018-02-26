/**
 * Sample Skeleton for 'HelpTabView.fxml' Controller Class
 */

package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class HelpTabController {

    @FXML // fx:id="helpBtn"
    private Button helpBtn; // Value injected by FXMLLoader

    @FXML
    void helpMePlease(ActionEvent event) {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("please help");
    	alert.setHeaderText(null);
    	alert.setContentText("help");
    	alert.showAndWait();
    }

}
