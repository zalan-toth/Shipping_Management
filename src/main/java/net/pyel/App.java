package net.pyel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

	CargoAPI cargoAPI = new CargoAPI();
	private static Scene scene;

	@Override
	public void start(Stage stage) throws IOException {
		load();
		save();
		scene = new Scene(loadFXML("primary"), 640, 480);
		stage.setScene(scene);
		stage.show();
	}


	private void save() {

		try {
			cargoAPI.save();
		} catch (Exception e) {
			System.err.println("Error writing to file: " + e);
		}
	}

	private void load() {
		try {
			cargoAPI.load();
		} catch (Exception e) {
			System.err.println("Error reading from file: " + e);
		}
	}

	static void setRoot(String fxml) throws IOException {
		scene.setRoot(loadFXML(fxml));
	}

	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
		return fxmlLoader.load();
	}

	public static void main(String[] args) {
		//cargo.addElement();
		launch();

	}

}