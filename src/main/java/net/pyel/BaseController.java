package net.pyel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import net.pyel.models.Port;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BaseController implements Initializable {

	private CargoAPI cargoAPI = new CargoAPI();

	@FXML
	private void switchToSecondary() throws IOException {
		save();
		App.setRoot("ports");


	}

	@FXML
	private void loadData() throws IOException {
		load();


	}

	private void save() {

		try {
			System.out.println("Data save attempted.");
			cargoAPI.save();
		} catch (Exception e) {
			System.err.println("Error writing to file: " + e);
		}
	}

	private void load() {
		try {
			System.out.println("Data load attempted.");
			cargoAPI.load();
		} catch (Exception e) {
			System.err.println("Error reading from file: " + e);
		}
	}

	@FXML
	private ListView<Port> portListView;

	@FXML
	private Label testLabel;
	private Port selectedPort;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		portListView.getItems().addAll(cargoAPI.cargo.getPorts().get(0));
	}
}