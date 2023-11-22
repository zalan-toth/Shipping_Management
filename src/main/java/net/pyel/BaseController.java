package net.pyel;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.pyel.models.Container;
import net.pyel.models.ContainerShip;
import net.pyel.models.Pallet;
import net.pyel.models.Port;
import net.pyel.utils.CustomList;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class BaseController implements Initializable {
	private static CargoAPI cargoAPI = new CargoAPI(null);
	private CustomList<Port> ports;
	private CustomList<ContainerShip> shipsOnSea = new CustomList<>();
	Stage popupstage = new Stage();
	Parent popuproot;
	Scene popupScene;
	Stage terminalStage = new Stage();
	Parent terminalRoot;
	Scene terminalScene;
	boolean setRun = true;

	public BaseController() {
		cargoAPI = BackgroundController.getCargoAPI();
		ports = cargoAPI.cargo.getPorts();
		shipsOnSea = cargoAPI.cargo.getShipsOnSea();
		popupstage.initModality(Modality.WINDOW_MODAL);
		popupstage.setResizable(false);
		popupstage.close();
		/*if (shipsOnSea == null) {
			shipsOnSea = new CustomList<>();
		}*/
	}

	@FXML
	private void logTerminal() throws IOException {
		terminalRoot = FXMLLoader.load(getClass().getResource("log.fxml"));
		terminalScene = new Scene(terminalRoot);
		terminalStage.setScene(terminalScene);
		terminalStage.setResizable(true);
		terminalStage.setTitle("Shipping Management Panel | Experimental Log Terminal");
		terminalStage.show();
	}

	@FXML
	TextField logInput = new TextField();

	@FXML
	ListView<String> log = new ListView<>();
	@FXML
	TextField handler = new TextField();
	String previousCommand = "";


	@FXML
	private void logPrompt() {
		String input = logInput.getText();
		logInput.clear();
		if (input.length() == 3) {
			if (input.toLowerCase().substring(0, 3).equals("log")) {
				terminalOutError("Usage: log [message]");
				return;
			}
		}
		if (input.length() < 4) {
			terminalOutError("Invalid command in log prompt.");
			return;
		}
		if (input.length() == 4) {
			if (input.toLowerCase().substring(0, 4).equals("help")) {
				terminalOutHelp("clear - Clear terminal");
				terminalOutHelp("bogosort - Bogo");
				terminalOutHelp("handler [name] - Change handler");
				terminalOutHelp("log [message] - Log a message");
				terminalOutHelp("help - Help page");
				terminalOut("HELP executed");
				return;
			}
		}
		if (input.length() == 5) {
			if (input.toLowerCase().substring(0, 5).equals("clear")) {
				terminalOut("CLEAR executed");
				log.getItems().clear();
				return;
			}
		}
		if (input.length() == 7) {
			if (input.toLowerCase().substring(0, 7).equals("handler")) {
				terminalOutError("Usage: handler [name]");
				return;
			}
		}
		if (input.length() > 7) {
			if (input.toLowerCase().substring(0, 8).equals("handler ")) {
				String name = input.substring(8);
				cargoAPI.currentHandler = name;
				terminalOut("Look at me! I'm Mr. Meeseeks!");
				return;
			}
		}

		if (input.length() == 8) {
			if (input.toLowerCase().substring(0, 8).equals("bogosort")) {

				Random random = new Random();
				int variable = random.nextInt(30001);
				int numero = 69;
				int count = 0;
				while (variable != numero) {
					variable = random.nextInt(30001);
					terminalOutHelp("" + variable);
					log.getItems().clear();
					count++;
				}
				terminalOut("Your public static void main String args[" + numero + "] quantum bogo is found in " + count + " attempt(s).");

				return;
			}
		}
		if (input.toLowerCase().substring(0, 4).equals("log ")) {

			if (input.length() > 4) {
				String message = input.substring(4);
				terminalOut(message);
			} else {
				terminalOutError("Usage: log [message]");
			}
		} else {
			terminalOutError("Invalid command in log prompt.");
		}
	}

	public void terminalOut(String out) {
		String message = "[" + cargoAPI.currentHandler + "]" + " > " + out;
		if (cargoAPI.currentHandler.isEmpty()) {
			message = " > " + out;
		}
		System.out.println(message);
		log.getItems().add(0, message);
	}

	public void terminalOutError(String out) {
		String message = "(!) " + out;
		System.out.println(message);
		log.getItems().add(0, message);
	}

	public void terminalOutHelp(String out) {
		String message = "| " + out;
		//System.out.println(message);
		log.getItems().add(0, message);
	}

	@FXML
	private void newPanel() throws IOException {
		BackgroundController.setCargoAPI(new CargoAPI(handler.getText()));
		App.setRoot("ports");
		App.getStageInfo().setTitle("Shipping Management Panel | Ports");
		App.getStageInfo().setHeight(900);
		App.getStageInfo().setWidth(1400);
		App.getStageInfo().setResizable(false);


	}

	@FXML
	private void basePanel() throws IOException {
		BackgroundController.setCargoAPI(new CargoAPI(handler.getText()));
		App.getStageInfo().setFullScreen(false);
		App.getStageInfo().setHeight(647);
		App.getStageInfo().setWidth(1024);
		App.getStageInfo().setResizable(false);
		App.getStageInfo().setTitle("Shipping Management Panel | Welcome");
		App.setRoot("base");


	}

	@FXML
	private void refresh() {
		initialize(null, null);


	}

	@FXML
	private void loadPanel() throws IOException {
		BackgroundController.setCargoAPI(new CargoAPI(handler.getText()));
		cargoAPI = BackgroundController.getCargoAPI();
		loadData();
		ports = cargoAPI.cargo.getPorts();
		shipsOnSea = cargoAPI.cargo.getShipsOnSea();
		/*if (shipsOnSea == null) {
			shipsOnSea = new CustomList<>();
		}*/
		initialize(null, null);
		App.setRoot("ports");
		App.getStageInfo().setHeight(900);
		App.getStageInfo().setWidth(1400);
		App.getStageInfo().setResizable(false);


	}

	@FXML
	private void openPopupForPortAdd() throws IOException {
		popuproot = FXMLLoader.load(getClass().getResource("popupadd.fxml"));
		popupScene = new Scene(popuproot);
		popupstage.setScene(popupScene);
		//popupstage.initOwner(popupstage.getScene().getWindow());
		popupstage.setResizable(false);
		popupstage.show();
	}

	@FXML
	private void openHelpMenu() throws IOException {
		popuproot = FXMLLoader.load(getClass().getResource("help.fxml"));
		popupstage.setTitle("Shipping Management Panel | About & Help Centre");
		popupScene = new Scene(popuproot);
		popupstage.setScene(popupScene);
		popupstage.show();
	}

	@FXML
	private void loadData() {
		BackgroundController.loadData();
		BackgroundController.setCargoAPI(cargoAPI);
		deselectPort();
		ports = cargoAPI.cargo.getPorts();
		shipsOnSea = cargoAPI.cargo.getShipsOnSea();
		initialize(null, null);
	}


	@FXML
	private void saveData() {
		BackgroundController.saveData();
	}

	@FXML
	private ListView<Pallet> palletListView = new ListView<>();
	@FXML
	private ListView<Container> dockedContainerListView = new ListView<>();
	@FXML
	private ListView<Container> containerListView = new ListView<>();
	@FXML
	private ListView<ContainerShip> dockedShipListView = new ListView<>();
	@FXML
	private ListView<ContainerShip> shipListView = new ListView<>();
	@FXML
	private ListView<Port> portListView = new ListView<>();
	@FXML
	private TextField portNameBox = new TextField();
	@FXML
	private TextField portIDBox = new TextField();
	@FXML
	private TextField portCountryBox = new TextField();
	@FXML
	private TextField shipIDBox = new TextField();
	@FXML
	private TextField shipNameBox = new TextField();
	@FXML
	private TextField shipCountryBox = new TextField();
	@FXML
	private TextField shipURLBox = new TextField();
	@FXML
	private Text portIDInfo = new Text();
	@FXML
	private Text shipIDInfo = new Text();
	@FXML
	private ImageView portFlag = new ImageView();
	@FXML
	private ImageView imgShipOnSea = new ImageView();
	@FXML
	private ImageView imgShipDocked = new ImageView();
	@FXML
	private ImageView imgContainersOnShore = new ImageView();
	@FXML
	private ImageView imgContainersOnShip = new ImageView();
	@FXML
	private ImageView shipImage = new ImageView();
	@FXML
	private ToggleButton toggleContainerButton = new ToggleButton();
	@FXML
	private ToggleButton toggleShipButton = new ToggleButton();
	@FXML
	private ToggleButton length10toggle, length20toggle, length40toggle = new ToggleButton();
	private int containerLengthValue;
	@FXML
	private Label testLabel;
	private Port selectedPort;
	private ContainerShip selectedShip;
	private ContainerShip selectedShipOnSea;
	private Container selectedContainer;
	private Container selectedContainerOnShip;
	private Pallet selectedPallet;

	@FXML
	private void deselectPort() {
		portListView.getSelectionModel().clearSelection();
		portIDInfo.setText("-");
		portIDBox.setText("");
		portNameBox.setText("");
		portCountryBox.setText("");
		containerListView.setItems(null);
		dockedContainerListView.setItems(null);
		palletListView.setItems(null);
		deselectShip();
		initialize(null, null);
	}

	@FXML
	private void deselectShip() {
		shipListView.getSelectionModel().clearSelection();
		dockedShipListView.getSelectionModel().clearSelection();
		shipIDInfo.setText("-");
		shipURLBox.setText("");
		shipNameBox.setText("");
		shipCountryBox.setText("");
		shipIDBox.setText("");
		deselectContainer();
	}

	@FXML
	private void deselectContainer() {
		deselectDockedContainer();
		deselectShipContainer();
	}

	@FXML
	private void deselectShipContainer() {
		containerListView.getSelectionModel().clearSelection();
		palletListView.setItems(null);
	}

	@FXML
	private void deselectDockedContainer() {
		dockedContainerListView.getSelectionModel().clearSelection();
		palletListView.setItems(null);
	}

	@FXML
	private void length10Toggle() {
		length10toggle.setSelected(true);
		length20toggle.setSelected(false);
		length40toggle.setSelected(false);
		containerLengthValue = 10;
	}

	@FXML
	private void length20Toggle() {
		length10toggle.setSelected(false);
		length20toggle.setSelected(true);
		length40toggle.setSelected(false);
		containerLengthValue = 20;
	}

	@FXML
	private void length40Toggle() {
		length10toggle.setSelected(false);
		length20toggle.setSelected(false);
		length40toggle.setSelected(true);
		containerLengthValue = 40;
	}

	@FXML
	private void toggleContainer() {
		if (toggleContainerButton.isSelected()) {
			toggleContainerButton.setStyle("-fx-background-color: #991cff;");
			toggleContainerButton.setText("Selected mode: SHIP");
			imgContainersOnShore.setOpacity(0.2);
			imgContainersOnShip.setOpacity(1);
			containerListView.setDisable(false);
			dockedContainerListView.setDisable(true);
		} else {
			toggleContainerButton.setStyle("-fx-background-color: #ff4d02;");
			toggleContainerButton.setText("Selected mode: PORT");
			imgContainersOnShore.setOpacity(1);
			imgContainersOnShip.setOpacity(0.2);
			containerListView.setDisable(true);
			dockedContainerListView.setDisable(false);
		}
		deselectContainer();
	}


	@FXML
	private void toggleShip() {
		if (toggleShipButton.isSelected()) {
			toggleShipButton.setStyle("-fx-background-color: #1143b8;");
			toggleShipButton.setText("Selected mode: SEA");
			imgShipOnSea.setOpacity(1);
			imgShipDocked.setOpacity(0.2);
			shipListView.setDisable(false);
			dockedShipListView.setDisable(true);
		} else {
			toggleShipButton.setStyle("-fx-background-color: #ff4d02;");
			toggleShipButton.setText("Selected mode: PORT");
			imgShipOnSea.setOpacity(0.2);
			imgShipDocked.setOpacity(1);
			shipListView.setDisable(true);
			dockedShipListView.setDisable(false);
		}
		deselectShip();
	}

	@FXML
	private void addAPort() {
		ContainerShip newShip = new ContainerShip("name", "11", "HU", "http:sadads", null);
		CustomList<ContainerShip> newList = new CustomList<>();
		newList.add(newShip);
		ports.add(new Port("Waterford", 67, "IE", newList, null));

		shipsOnSea.add(newShip);
		shipsOnSea.add(newShip);
		shipsOnSea.add(newShip);
		initialize(null, null);
	}

	@FXML
	private void addPort() {
		if (!portNameBox.getText().isEmpty() && !portIDBox.getText().isEmpty()) {
			Port newPort = new Port(portNameBox.getText(), Integer.parseInt(portIDBox.getText()), portCountryBox.getText(), null, null);
			ports.add(newPort);
			terminalOut(newPort + " added.");
			refresh();
		}
	}

	@FXML
	private void addShip() {
		if (selectedPort != null && !shipNameBox.getText().isEmpty() && !shipIDBox.getText().isEmpty() && !shipCountryBox.getText().isEmpty()) {
			ContainerShip newShip = new ContainerShip(shipNameBox.getText(), shipIDBox.getText(), shipCountryBox.getText(), shipURLBox.getText(), new CustomList<>());
			if (selectedPort.getShips() == null) {
				selectedPort.setShips(new CustomList<>());
			}
			selectedPort.addContainerShip(newShip);
			terminalOut(newShip + " added.");
			refresh();
		}
	}

	@FXML
	private void addShipToSea() {
		if (!shipNameBox.getText().isEmpty() && !shipIDBox.getText().isEmpty() && !shipCountryBox.getText().isEmpty()) {
			ContainerShip newShip = new ContainerShip(shipNameBox.getText(), shipIDBox.getText(), shipCountryBox.getText(), shipURLBox.getText(), new CustomList<>());
			shipsOnSea.add(newShip);
			terminalOut(newShip + " added to sea.");
			//updateOnlyFromContainerLevel();
			refresh();
		}
	}

	@FXML
	private void addPallet() {
		Pallet newPallet = new Pallet("desc", "GB-FC0001", 5, 0, 0, 0);
		if (!toggleContainerButton.isSelected()) {
			if (selectedContainer.getPallets() == null) {
				selectedContainer.setPallets(new CustomList<>());
			}
			selectedContainer.addPallet(newPallet);
		} else if (toggleContainerButton.isSelected()) {

			if (selectedContainerOnShip.getPallets() == null) {
				selectedContainerOnShip.setPallets(new CustomList<>());
			}
			selectedContainerOnShip.addPallet(newPallet);
		}
		terminalOut(newPallet + " added.");
		refresh();

	}

	@FXML
	private void addContainer() {
		Container newContainer = new Container(55, 5, null);
		if (!toggleShipButton.isSelected()) {
			if (selectedShip.getContainers() == null) {
				selectedShip.setContainers(new CustomList<>());
			}
			selectedShip.addContainer(newContainer);
		} else if (toggleShipButton.isSelected()) {

			if (selectedShipOnSea.getContainers() == null) {
				selectedShipOnSea.setContainers(new CustomList<>());
			}
			selectedShipOnSea.addContainer(newContainer);
		}
		terminalOut(newContainer + " added.");
		refresh();

	}

	@FXML
	private void addContainerOnShore() {

		Container newContainer = new Container(55, 5, null);
		if (selectedPort.getContainers() == null) {
			selectedPort.setContainers(new CustomList<>());
		}
		selectedPort.addContainer(newContainer);
		terminalOut(newContainer + " added.");
		updateData();

	}

	@FXML
	private void updateContainer() {
	}

	@FXML
	private void updateShip() {
	}

	@FXML
	private void updatePort() {
		if (selectedPort != null && !portNameBox.getText().isEmpty() && !portIDBox.getText().isEmpty() && !portCountryBox.getText().isEmpty()) {
			selectedPort.update(portNameBox.getText(), Integer.parseInt(portIDBox.getText()), portCountryBox.getText());
			terminalOut(selectedPort + " updated.");
			refresh();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		if (setRun) {
			setupPortListViewListener();
			setRun = false;
		}
		//portListView.getItems().addAll(ports);
		shipListView.setItems(FXCollections.observableList(shipsOnSea)); //Yay!
		portListView.setItems(FXCollections.observableList(ports)); //Yay!
		updateData();
		//selectedPort = portListView.getSelectionModel().getSelectedItem();
		//ContainerShip selectedShip = shipListView.getSelectionModel().getSelectedItem();
		//Container selectedContainer = containerListView.getSelectionModel().getSelectedItem();
		//Pallet selectedPallet = palletListView.getSelectionModel().getSelectedItem();
		//if (shipsOnSea != null) {
		//	shipListView.setItems(FXCollections.observableList(shipsOnSea));
		//}
		/*if ((this.selectedPort != null)) {
			if (selectedPort.getShips() == null) {
				dockedShipListView.setItems(null);
			} else {
				dockedShipListView.setItems(FXCollections.observableList(selectedPort.getShips()));
			}
		} else {
			dockedShipListView.setItems(null);
		}*/
		//if (selectedShip != null) {
		//	containerListView.setItems(FXCollections.observableList(selectedShip.getContainers()));
		//}
		//if (selectedContainer != null) {
		//	palletListView.setItems(FXCollections.observableList(selectedContainer.getPallets()));
		//}


	}

	private void setupPortListViewListener() {
		portListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			// This will be called whenever the user selects a different item in the list
			if (newValue != null) {
				// Call initialize or any specific update method
				//refresh();
				updateData();
			}
		});
		dockedShipListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				updateOnlyFromShipLevel();
			}
		});
		shipListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				updateData();
			}
		});
		containerListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				updateOnlyFromContainerOnShipLevel();
			}
		});
		dockedContainerListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				updateOnlyFromContainerLevel();
			}
		});
		palletListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				updateOnlyFromPalletLevel();
			}
		});
		log.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			// This will be called whenever the user selects a different item in the list
			if (newValue != null) {
				updateData();
			}
		});
		logInput.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				logPrompt();
				previousCommand = logInput.getText();
			}

		});
	}


	private void updateData() {
		selectedPort = portListView.getSelectionModel().getSelectedItem();
		selectedShipOnSea = shipListView.getSelectionModel().getSelectedItem();
		if (selectedShipOnSea != null && toggleShipButton.isSelected()) {
			if (true) {
				if (selectedShipOnSea.getContainers() == null) {
					containerListView.setItems(null);
				} else {
					containerListView.setItems(FXCollections.observableList(selectedShipOnSea.getContainers()));
					containerListView.refresh();
					updateOnlyFromContainerOnShipLevel();
				}
			}

			shipIDInfo.setText(selectedShipOnSea.getCountry() + selectedShipOnSea.getID());
			shipIDBox.setText("" + selectedShipOnSea.getID());
			shipNameBox.setText(selectedShipOnSea.getName());
			shipCountryBox.setText(selectedShipOnSea.getCountry());
			shipURLBox.setText(selectedShipOnSea.getPhotoURL());
		}
		//terminalOut(selectedShip);
		if ((this.selectedPort != null)) {


			//dockedCONTAINERS SECTION
			if (selectedPort.getContainers() == null) {
				dockedContainerListView.setItems(null);
			} else {
				dockedContainerListView.setItems(FXCollections.observableList(selectedPort.getContainers()));
			}
			//dockedCONTAINERS END


			if (selectedPort.getShips() == null) {
				dockedShipListView.setItems(null);
			} else {
				dockedShipListView.setItems(FXCollections.observableList(selectedPort.getShips()));

				//SHIP BEGIN
				updateOnlyFromShipLevel();
				//SHIP END
			}
			portIDBox.setText("" + selectedPort.getCode());
			portNameBox.setText(selectedPort.getName());
			portIDInfo.setText(selectedPort.getCountry() + selectedPort.getCode());
			portCountryBox.setText(selectedPort.getCountry());
			//Image image = new Image(("@img/flags/") + selectedPort.getCountry().toLowerCase() + ".png");
			//portFlag.setImage(image);

		} else {
			dockedShipListView.setItems(null);
		}

	}

	private void updateOnlyFromShipLevel() {
		selectedShip = dockedShipListView.getSelectionModel().getSelectedItem();
		if (selectedShip != null && !toggleShipButton.isSelected()) {
			if (true) {
				if (selectedShip.getContainers() == null) {
					containerListView.setItems(null);
				} else {
					containerListView.setItems(FXCollections.observableList(selectedShip.getContainers()));
					updateOnlyFromContainerOnShipLevel();
				}
			}

			shipIDInfo.setText(selectedShip.getCountry() + selectedShip.getID());
			shipIDBox.setText("" + selectedShip.getID());
			shipNameBox.setText(selectedShip.getName());
			shipCountryBox.setText(selectedShip.getCountry());
			shipURLBox.setText(selectedShip.getPhotoURL());
			/*if (selectedShip.getPhotoURL() != null) {
				Image image = new Image(selectedShip.getPhotoURL());
				shipImage = new ImageView(image);
			}*/
		}
	}

	private void updateOnlyFromContainerLevel() {
		selectedContainer = dockedContainerListView.getSelectionModel().getSelectedItem();
		if (selectedContainer != null) {
			if (!toggleContainerButton.isSelected()) {
				if (selectedContainer.getPallets() == null) {
					palletListView.setItems(null);
				} else {
					palletListView.setItems(FXCollections.observableList(selectedContainer.getPallets()));
					updateOnlyFromPalletLevel();
				}
			}
			//containerIDBox
		}
	}

	private void updateOnlyFromContainerOnShipLevel() {
		selectedContainerOnShip = containerListView.getSelectionModel().getSelectedItem();

		if (selectedContainerOnShip != null) {
			if (toggleContainerButton.isSelected()) {
				if (selectedContainerOnShip.getPallets() == null) {
					palletListView.setItems(null);
				} else {
					palletListView.setItems(FXCollections.observableList(selectedContainerOnShip.getPallets()));
					updateOnlyFromPalletLevel();
				}
			}
			//containerIDBox
		}
	}

	private void updateOnlyFromPalletLevel() {
		selectedPallet = palletListView.getSelectionModel().getSelectedItem();

		if (selectedPallet != null) {
			//palletIDBox
		}
	}
}