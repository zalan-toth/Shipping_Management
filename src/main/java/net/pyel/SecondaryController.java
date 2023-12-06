package net.pyel;

import javafx.fxml.FXML;

import java.io.IOException;

/**
 * NOT NEEDED
 *
 * @author default fxml
 */
public class SecondaryController {

	@FXML
	private void switchToPrimary() throws IOException {
		App.setRoot("primary");
	}
}