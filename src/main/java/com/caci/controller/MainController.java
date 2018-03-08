package main.java.com.caci.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import main.java.com.caci.model.Model;

public class MainController {

	@FXML
	private TabPane tabPane;
	@FXML
	private SplitTabController splitTabController;
	@FXML
	private AssembleTabController assembleTabController;
	@FXML
	private HelpTabController helpTabController;

	private Model model;
	
	private Stage primaryStage;

	// set model
	public void setModel(Model model) {
		this.model = model;
		model.addObserver(splitTabController);
		// model.addObserver(assembleTabController);
		// model.addObserver(helpTabController);
	}
	
	public void setStage(Stage stage) {
		this.primaryStage = stage;
	}

	// get model
	public Model model() {
		return this.model;
	}
	
	public Stage stage() {
		return this.primaryStage;
	}

	@FXML
	void initialize() {
		// inject main controller into subtab controllers
		splitTabController.injectMainController(this);
		// assembleTabController.injectMainController(this);
		// helpTabController.injectMainController(this);
	}
}
