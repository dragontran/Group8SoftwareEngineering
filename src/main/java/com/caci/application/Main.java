package main.java.com.caci.application;
import java.io.IOException;
import main.java.com.caci.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.com.caci.model.Model;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		// load main tab pane view
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/MainView.fxml"));
		
		// create new scene with view
		Scene scene = new Scene(fxmlLoader.load());

		// set stage & options	
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("File Split");
		
		// set model
		MainController mainController = (MainController) fxmlLoader.getController();
		mainController.setModel(new Model());
		mainController.setStage(primaryStage);
		
		// show stage
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
