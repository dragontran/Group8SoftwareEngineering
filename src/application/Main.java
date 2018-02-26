package application;
import java.io.IOException;
import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		// load main tab pane view
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
		
		// create new scene with view
		Scene scene = new Scene(fxmlLoader.load());

		// set stage & options	
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setTitle("I hate this");
		
		// set model
		MainController mainController = (MainController) fxmlLoader.getController();
		mainController.setModel(new Model());
		
		
		// show stage
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
