package application;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		// load main tab pane view
		Parent root = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
		
		// create new scene with view
		Scene scene = new Scene(root);

		// set stage & options	
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("I hate this");
		
		// set model
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
