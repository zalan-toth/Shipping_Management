package net.pyel;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import net.pyel.models.Port;
import net.pyel.utils.CustomList;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BaseController implements Initializable {

	private CargoAPI cargoAPI = new CargoAPI();

	CustomList<Port> ports = cargoAPI.cargo.getPorts();


	@FXML
	private void newPanel() throws IOException {
		App.setRoot("ports");


	}

	@FXML
	private void loadPanel() throws IOException {
		loadData();
		App.setRoot("ports");


	}

	@FXML
	private void loadData() {
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
	private ListView<Port> portListView = new ListView<>();

	@FXML
	private Label testLabel;
	private Port selectedPort;

	@FXML
	private void addAPort() {
		ports.add(new Port("Waterford", 67, "IE", null));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//portListView.getItems().addAll(ports);
		portListView.setItems(FXCollections.observableList(ports)); //Yay!
	}
}