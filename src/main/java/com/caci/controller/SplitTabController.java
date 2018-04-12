package main.java.com.caci.controller;

import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class SplitTabController implements Observer {

	@FXML
	private TextField srcTextField;

	@FXML
	private Button srcBrowseBtn;

	@FXML
	private TextField outTextField;

	@FXML
	private Button outBrowseBtn;

	@FXML
	private ProgressBar progressBar;

	@FXML
	private RadioButton bytesRadioBtn;

	@FXML
	private ToggleGroup splitOptions;

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

	private MainController mainController;

	@FXML
	void bytesRadioToggle(ActionEvent event) {
		this.bytesTextField.setDisable(false);
		this.bytesSizeComboBox.setDisable(false);
		this.partsTextField.setDisable(true);
	}

	@FXML
	void PartsRadioToggle(ActionEvent event) {
		this.partsTextField.setDisable(false);
		this.bytesTextField.setDisable(true);
		this.bytesSizeComboBox.setDisable(true);
	}

	@FXML
	void getOutDirectory(ActionEvent event) {

		// open directory chooser
		DirectoryChooser dirChooser = new DirectoryChooser();
		dirChooser.setTitle("Select Output Directory");

		String chooserPath;

		if (mainController.model().getSplitInputFile() != null && mainController.model().getSplitInputFile().exists()) {
			// set chooser path as parent directory of file
			chooserPath = mainController.model().getSplitInputFile().getParent();
		} else {
			// set local path as default path
			chooserPath = System.getProperty("user.dir");
		}
		File defaultDirectory = new File(chooserPath);
		dirChooser.setInitialDirectory(defaultDirectory);

		// show chooser
		// disable main stage when chooser is open
		File file = dirChooser.showDialog(mainController.stage());

		// TODO: error handling
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
		// disable main stage when chooser is open
		File file = fileChooser.showOpenDialog(mainController.stage());

		// TODO: error handling
		if (file != null) {
			// update split file input path in model
			mainController.model().setSplitInputPath(file.getAbsolutePath());
		}

	}

	// split files
	@FXML
	void splitFile(ActionEvent event) {
		splitBtn.setDisable(true);
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Task<Void> t = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				long prefix = 1;
				if (bytesRadioBtn.isSelected()) {
					// System.out.println(bytesTextField.getText() + " " +
					// bytesSizeComboBox.getValue());
					switch (bytesSizeComboBox.getValue()) {
					case "kilobytes":
						prefix = 1024;
						break;
					case "megabytes":
						prefix = 1048576;
						break;
					case "gigabytes":
						prefix = 1073741824;
						break;
					default:
						break;
					}

					mainController.model().splitFile(Long.parseLong(bytesTextField.getText()) * prefix, false);

				} else {

					mainController.model().splitFile(Long.parseLong(partsTextField.getText()), true);

				}
				return null;
			}

		};

		// exception handling for split thread
		t.setOnFailed(evt -> {

			 errorAlert("Error", "", (t.getException().getMessage()));

			splitBtn.setDisable(false);

		});
		executorService.submit(t);
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

		// TODO: make this better
		// dumb way to determine which component to update
		/* TODO: omg please this need to be better */
		if (arg instanceof String) {
			String updateInput = (String) arg;
			char flag = updateInput.charAt(0);
			updateInput = updateInput.substring(1);

			switch (flag) {
			case '0':
				srcTextField.setText(updateInput);
				break;
			case '1':
				outTextField.setText(updateInput);
				break;
			case '5':
				progressBar.setProgress(Double.parseDouble(updateInput));
				if (progressBar.getProgress() == 1) {
					splitBtn.setDisable(false);
				}

				break;

			}
		}
	}

	// set main controller
	public void injectMainController(MainController mainController) {
		this.mainController = mainController;
	}

	public void errorAlert(String title, String header, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(message);
		alert.showAndWait();
	}

}
