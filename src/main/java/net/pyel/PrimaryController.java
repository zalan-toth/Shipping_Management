package net.pyel;

import javafx.fxml.FXML;

import java.io.IOException;

/**
 * NOT NEEDED
 *
 * @author default fxml
 */
public class PrimaryController {

	private CargoAPI cargoAPI = new CargoAPI(null);

	@FXML
	private void switchToSecondary() throws IOException {
		save();
		App.setRoot("secondary");


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

}