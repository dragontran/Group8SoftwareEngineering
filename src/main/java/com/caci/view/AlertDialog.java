package main.java.com.caci.view;

import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.stage.Window;

public class AlertDialog {
	static public void errorAlert(String message, Stage stage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("An error has occurred");
		alert.setContentText(message);

		Window window = stage.getScene().getWindow();
		alert.initOwner(window);

		alert.showAndWait();
	}

	static public void successAlert(Stage stage) {

		Alert alert = new Alert(AlertType.INFORMATION);

		alert.setTitle("Success");
		alert.setHeaderText(null);
		alert.setContentText("File split successfully");

		Window window = stage.getScene().getWindow();
		alert.initOwner(window);

		alert.showAndWait();

	}

	static public void stackTraceAlert(Throwable throwable, Stage stage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("An unexpected error has occurred");
		alert.setContentText(throwable.getMessage());

		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);

		Window window = stage.getScene().getWindow();
		alert.initOwner(window);

		alert.showAndWait();
	}

}
