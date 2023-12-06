package net.pyel;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

/**
 * Base Controller - Manages all windows with fxml
 *
 * @author Zalán Tóth
 */
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
	Stage facilityStage = new Stage();
	Parent facilityRoot;
	Scene facilityScene;
	boolean setRun = true;

	public BaseController() {
		cargoAPI = BackgroundController.getCargoAPI();
		ports = cargoAPI.cargo.getPorts();
		shipsOnSea = cargoAPI.cargo.getShipsOnSea();
		/*popupstage.initModality(Modality.WINDOW_MODAL);
		popupstage.setResizable(false);
		popupstage.close();
		/*if (shipsOnSea == null) {
			shipsOnSea = new CustomList<>();
		}*/
	}

	@FXML
	private void smartAddFacility(CustomList<String> list) throws IOException {
		facilityRoot = FXMLLoader.load(getClass().getResource("facility.fxml"));
		facilityScene = new Scene(facilityRoot);
		facilityStage.setScene(facilityScene);
		facilityStage.setResizable(false);
		facilityStage.initModality(Modality.APPLICATION_MODAL);
		facilityStage.setTitle("Shipping Management Panel | Facility");
		for (String str : list) {
			System.out.println(str);
			viewFacility.getItems().add(str);
		}
		initialize(null, null);
		facilityStage.show();

	}

	@FXML
	private void facilityWindow() throws IOException {
		facilityScene = new Scene(facilityRoot);
		facilityStage.setScene(facilityScene);
		facilityStage.setResizable(false);
		facilityStage.initModality(Modality.APPLICATION_MODAL);
	}

	@FXML
	private void logTerminal() throws IOException {
		terminalRoot = FXMLLoader.load(getClass().getResource("log.fxml"));
		terminalScene = new Scene(terminalRoot);
		terminalStage.setScene(terminalScene);
		terminalStage.setResizable(true);
		terminalStage.setTitle("Shipping Management Panel | Experimental Terminal");
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
			terminalOutError("Invalid command in log prompt. Type \"help\" for help.");
			return;
		}
		if (input.length() == 4) {
			if (input.toLowerCase().substring(0, 4).equals("help")) {

				terminalOutHelp("-------------------------------------");
				terminalOutHelp("reset - Resets the program, erases loaded data");
				terminalOutHelp("value - Structured valuation of all goods");
				terminalOutHelp("currency [value] - Change display currency (currently " + cargoAPI.cargo.getCurrency() + ")");
				terminalOutHelp("debug - Switch debug mode (current: " + cargoAPI.cargo.isDebugMode() + ")");
				terminalOutHelp("goods - Structured ciew of all goods");
				terminalOutHelp("ships - Structured view of all ships");
				terminalOutHelp("findall [mark] - Find all goods by given Mark part");
				terminalOutHelp("search [mark] - Search for goods by Mark");
				terminalOutHelp("--------------FACILITIES-------------");
				terminalOutHelp("clear - Clear terminal");
				terminalOutHelp("bogosort - Bogo");
				terminalOutHelp("handler [name] - Change handler");
				terminalOutHelp("log [message] - Log a message");
				terminalOutHelp("help - Help page");
				terminalOutHelp("---------------HELP MENU-------------");
				terminalOut("HELP executed");
				return;
			}
		}
		if (input.length() == 5) {
			if (input.toLowerCase().substring(0, 5).equals("clear")) {
				terminalOut("CLEAR executed");
				log.getItems().clear();
				return;
			} else if (input.toLowerCase().substring(0, 5).equals("ships")) {
				viewAllShipsInTerminal();
				return;
			} else if (input.toLowerCase().substring(0, 5).equals("debug")) {
				switchDebugMode();
				return;
			} else if (input.toLowerCase().substring(0, 5).equals("reset")) {
				log.getItems().clear();
				terminalOut("RESET executed");
				try {
					newPanel();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				return;
			} else if (input.toLowerCase().substring(0, 5).equals("goods")) {
				viewAllGoodsInTerminal();
				return;
			} else if (input.toLowerCase().substring(0, 5).equals("value")) {
				structuredValuationOfGoods();
				return;
			}
		}
		if (input.length() == 6 || input.length() == 7) {
			if (input.toLowerCase().substring(0, 6).equals("search")) {
				terminalOutError("Usage: search [mark]");
				return;
			}
		}

		if (input.length() == 8 || input.length() == 9) {
			if (input.toLowerCase().substring(0, 8).equals("currency")) {
				terminalOutError("Usage: currency [currency]");
				return;
			}
		}

		if (input.length() > 9) {
			if (input.toLowerCase().substring(0, 9).equals("currency ")) {
				String currency = input.substring(9);
				cargoAPI.cargo.setCurrency(currency);
				terminalOut("Currency changed to " + currency);
				return;
			}
		}
		if (input.length() > 7) {
			if (input.toLowerCase().substring(0, 7).equals("search ")) {
				String mark = input.substring(7);
				searchForGoods(mark);

				return;
			}
		}

		if (input.length() == 7) {
			if (input.toLowerCase().substring(0, 7).equals("handler")) {
				terminalOutError("Usage: handler [name]");
				return;
			}
			if (input.toLowerCase().substring(0, 7).equals("findall")) {
				terminalOutError("Usage: findall [mark part]");
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
			if (input.toLowerCase().substring(0, 8).equals("findall ")) {
				String mark = input.substring(8);
				findAllGoods(mark);

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
			terminalOutError("Invalid command in log prompt. Type \"help\" for help.");
		}
	}

	public void searchForGoods(String mark) {
		log.getItems().clear();
		terminalOut("Looking for a pallet that has the mark " + mark + "...");
		for (String str : cargoAPI.cargo.searchForGood(mark)) {
			log.getItems().add(str);
		}
	}

	public void findAllGoods(String mark) {
		log.getItems().clear();
		terminalOut("Looking for pallets that has the part \"" + mark + "\" in their mark...");
		for (String str : cargoAPI.cargo.findAllGoods(mark)) {
			log.getItems().add(str);
		}
	}

	public void switchDebugMode() {
		boolean current = cargoAPI.cargo.isDebugMode();
		if (current) {
			cargoAPI.cargo.setDebugMode(false);
		} else {
			cargoAPI.cargo.setDebugMode(true);
		}
		terminalOut("Set DEBUG mode to " + cargoAPI.cargo.isDebugMode());
	}

	public void viewAllShipsInTerminal() {
		log.getItems().clear();
		terminalOutHelp("----------------------------------------");
		terminalOutHelp("VIEW ALL SHIPS IN STRUCTURED VIEW");
		terminalOutHelp("----------------------------------------");
		for (String str : cargoAPI.cargo.returnShips()) {
			log.getItems().add(str);
		}
	}

	public void viewAllGoodsInTerminal() {
		log.getItems().clear();
		terminalOutHelp("----------------------------------------");
		terminalOutHelp("VIEW ALL GOODS IN STRUCTURED VIEW");
		terminalOutHelp("----------------------------------------");
		for (String str : cargoAPI.cargo.returnGoods()) {
			log.getItems().add(str);
		}

	}

	public void structuredValuationOfGoods() {
		log.getItems().clear();
		terminalOutHelp("----------------------------------------");
		terminalOutHelp("=Value on each level showed at the end =");
		terminalOutHelp("STRUCTURED VALUATION OF ALL GOODS");
		terminalOutHelp("----------------------------------------");
		for (String str : cargoAPI.cargo.returnValues()) {
			log.getItems().add(str);
		}

	}

	public void terminalReverse(String out) {
		String message = out;
		System.out.println(message);
		log.getItems().add(message);
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
		popupstage.setResizable(false);
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
	private ListView<String> viewFacility = new ListView<>();
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
	private Text containerIDInfo = new Text();
	@FXML
	private Text palletIDInfo = new Text();
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
	@FXML
	private TextField containerIDBox = new TextField();
	@FXML
	private TextField containerCapacityBox = new TextField();
	@FXML
	private TextField containerSizeBox = new TextField();
	@FXML
	private TextField palletIDBox = new TextField();
	@FXML
	private TextField palletSizeBox = new TextField();
	@FXML
	private TextField palletWeightBox = new TextField();
	@FXML
	private TextField palletValueBox = new TextField();
	@FXML
	private TextField palletAmountBox = new TextField();
	@FXML
	private TextArea palletDescBox = new TextArea();
	private int containerLengthValue = 20;
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
	private void deselectPallet() {
		smartAddReturn.setText("");
		palletListView.getSelectionModel().clearSelection();
		palletIDInfo.setText("-");
		palletIDBox.setText("");
		palletAmountBox.setText("");
		palletValueBox.setText("");
		palletWeightBox.setText("");
		palletSizeBox.setText("");
		palletDescBox.setText("");
	}

	@FXML
	private void deselectContainer() {

		deselectPallet();
		deselectDockedContainer();
		deselectShipContainer();
		containerIDBox.setText("");
		lengthInvalid();
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
	private void lengthInvalid() {
		if (length10toggle != null && length20toggle != null && length40toggle != null) {
			length10toggle.setSelected(false);
			length20toggle.setSelected(false);
			length40toggle.setSelected(false);
		}
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

	/*
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
	*/
	@FXML
	private void addPort() {
		if (!portNameBox.getText().isEmpty() && !portIDBox.getText().isEmpty()) {
			Port newPort = new Port(portNameBox.getText(), Integer.parseInt(portIDBox.getText()), portCountryBox.getText(), null, null);
			cargoAPI.cargo.addPort(newPort);
			terminalOut(newPort + " added.");
			refresh();
		}
	}

	@FXML
	private void removePort() {
		cargoAPI.cargo.removePort(selectedPort);
		selectedPort = null;
		deselectPort();
		refresh();
	}

	@FXML
	private void removeShipFromPort() {
		selectedPort.removeShipFromPort(selectedShip);
		selectedShip = null;
		deselectShip();
		refresh();
	}

	@FXML
	private void removeShipFromSea() {
		cargoAPI.cargo.removeShipFromSea(selectedShipOnSea);
		selectedShipOnSea = null;
		deselectShip();
		refresh();
	}

	@FXML
	private void removeContainerFromPort() {
		selectedPort.removeContainer(selectedContainer);
		selectedContainer = null;
		deselectContainer();
		refresh();
	}

	@FXML
	private void removeContainerFromShip() {
		if (toggleShipButton.isSelected()) {
			selectedShipOnSea.removeContainer(selectedContainer);
			selectedShipOnSea = null;
		} else if (!toggleShipButton.isSelected()) {
			selectedShip.removeContainer(selectedContainer);
			selectedShip = null;
		}
		deselectContainer();
		refresh();
	}

	@FXML
	private void removePallet() {
		if (toggleContainerButton.isSelected()) {
			selectedContainerOnShip.removePallet(selectedPallet);
			selectedContainerOnShip = null;
		} else if (!toggleContainerButton.isSelected()) {
			selectedContainer.removePallet(selectedPallet);
			selectedContainer = null;
		}
		deselectPallet();
		refresh();
	}

	@FXML
	private void loadContainerToShip() {
		if (toggleShipButton.isSelected()) {
			if (selectedContainer != null && selectedShipOnSea != null) {
				Container containerToLoad = selectedContainer;
				selectedPort.removeContainer(selectedContainer);
				selectedShipOnSea.addContainer(containerToLoad);
				selectedContainer = null;
				terminalOut(containerToLoad + " loaded to Ship " + selectedShipOnSea);
			}
		} else if (!toggleShipButton.isSelected()) {
			if (selectedContainer != null && selectedShip != null) {
				Container containerToLoad = selectedContainer;
				selectedPort.removeContainer(selectedContainer);
				selectedShip.addContainer(containerToLoad);
				selectedContainer = null;
				terminalOut(containerToLoad + " loaded to Ship " + selectedShip);
			}
		}
		deselectContainer();
		refresh();
	}

	@FXML
	private void unloadContainerFromShip() {
		if (toggleShipButton.isSelected()) {
			if (selectedContainerOnShip != null && selectedShipOnSea != null) {
				Container containerToUnload = selectedContainerOnShip;
				selectedShipOnSea.removeContainer(containerToUnload);
				selectedPort.addContainer(selectedContainerOnShip);
				selectedContainerOnShip = null;
				terminalOut(containerToUnload + " loaded to Port " + selectedPort);
			}
		} else if (!toggleShipButton.isSelected()) {
			if (selectedContainerOnShip != null && selectedShip != null) {
				Container containerToUnload = selectedContainerOnShip;
				selectedShip.removeContainer(containerToUnload);
				selectedPort.addContainer(selectedContainerOnShip);
				selectedContainerOnShip = null;
				terminalOut(containerToUnload + " loaded to Port " + selectedPort);
			}
		}
		deselectContainer();
		refresh();
	}

	@FXML
	private void dockShipToPort() {
		if (selectedPort != null) {
			ContainerShip shipToDock = selectedShipOnSea;
			cargoAPI.cargo.removeShipFromSea(selectedShipOnSea);
			selectedPort.addContainerShip(shipToDock);
			selectedShipOnSea = null;
			terminalOut(shipToDock + " docked to Port " + selectedPort);
			deselectShip();
			refresh();
		}
	}

	@FXML
	private void unDockShipFromPort() {
		if (selectedShip != null) {
			ContainerShip shipToUndock = selectedShip;
			selectedPort.removeShip(shipToUndock);
			cargoAPI.cargo.addShipToSea(shipToUndock);
			selectedShip = null;
			terminalOut(shipToUndock + " launched to sea.");
			deselectShip();
			refresh();
		}
	}

	@FXML
	private void addShip() {
		if (selectedPort == null) {
			return;
		}
		if (!shipNameBox.getText().isEmpty() && !shipIDBox.getText().isEmpty() && !shipCountryBox.getText().isEmpty()) {
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
			cargoAPI.cargo.addShipToSea(newShip);
			terminalOut(newShip + " added to sea.");
			//updateOnlyFromContainerLevel();
			refresh();
		}
	}

	@FXML
	Text smartAddReturn = new Text();

	@FXML
	private void clearOutputTerminal() {
		log.getItems().clear();


	}

	@FXML
	private void smartAdd() {
		if (!palletIDBox.getText().isEmpty() && !palletAmountBox.getText().isEmpty() && !palletValueBox.getText().isEmpty() && !palletSizeBox.getText().isEmpty() && !palletWeightBox.getText().isEmpty()) {
			Pallet newPallet = new Pallet(palletDescBox.getText(), palletIDBox.getText(), Integer.parseInt(palletAmountBox.getText()), Float.parseFloat(palletValueBox.getText()), Float.parseFloat(palletWeightBox.getText()), Integer.parseInt(palletSizeBox.getText()));

			CustomList<String> list = cargoAPI.cargo.smartAdd(newPallet);
			smartAddReturn.setText("Smart Add executed.");
			log.getItems().clear();
			for (String str : list) {
				terminalReverse(str);
			}
			refresh();
		}


	}

	@FXML
	private void addPallet() {
		smartAddReturn.setText("");
		if (selectedContainer == null && selectedContainerOnShip == null) {
			return;
		}
		if (!palletIDBox.getText().isEmpty() && !palletAmountBox.getText().isEmpty() && !palletValueBox.getText().isEmpty() && !palletSizeBox.getText().isEmpty() && !palletWeightBox.getText().isEmpty()) {
			Pallet newPallet = new Pallet(palletDescBox.getText(), palletIDBox.getText(), Integer.parseInt(palletAmountBox.getText()), Float.parseFloat(palletValueBox.getText()), Float.parseFloat(palletWeightBox.getText()), Integer.parseInt(palletSizeBox.getText()));
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
			refresh();
		}


	}

	@FXML
	private void addContainer() {
		if (selectedShip != null && selectedShipOnSea != null) {
			return;
		}
		if (!containerIDBox.getText().isEmpty() && containerLengthValue != 0) {
			Container c = new Container(Integer.parseInt(containerIDBox.getText()), containerLengthValue, null);
			if (!toggleShipButton.isSelected()) {
				if (selectedShip.getContainers() == null) {
					selectedShip.setContainers(new CustomList<>());
				}
				selectedShip.addContainer(c);
			} else if (toggleShipButton.isSelected()) {

				if (selectedShipOnSea.getContainers() == null) {
					selectedShipOnSea.setContainers(new CustomList<>());
				}
				selectedShipOnSea.addContainer(c);
			}
			terminalOut(c + " added.");
			refresh();
		}
	}

	@FXML
	private void addContainerOnShore() {
		if (selectedPort != null && !containerIDBox.getText().isEmpty() && containerLengthValue != 0) {
			if (selectedPort.getContainers() == null) {
				selectedPort.setContainers(new CustomList<>());
			}
			Container c = new Container(Integer.parseInt(containerIDBox.getText()), containerLengthValue, null);
			selectedPort.addContainer(c);
		}
		updateData();

	}

	@FXML
	private void updateContainer() {
		if (!toggleContainerButton.isSelected() && selectedPort != null && selectedContainer != null && !containerIDBox.getText().isEmpty() && containerLengthValue != 0) {
			selectedPort.updateContainer(selectedContainer, Integer.parseInt(containerIDBox.getText()), containerLengthValue);
		} else if (toggleContainerButton.isSelected() && selectedContainerOnShip != null && !containerIDBox.getText().isEmpty() && containerLengthValue != 0) {
			if (selectedShip != null) {
				selectedShip.updateContainer(selectedContainerOnShip, Integer.parseInt(containerIDBox.getText()), containerLengthValue);
			} else if (selectedShipOnSea != null) {
				selectedShipOnSea.updateContainer(selectedContainerOnShip, Integer.parseInt(containerIDBox.getText()), containerLengthValue);
			}
		}
		refresh();
	}

	@FXML
	private void updateShip() {
		if (selectedShip != null && !shipNameBox.getText().isEmpty() && !shipIDBox.getText().isEmpty() && !shipCountryBox.getText().isEmpty()) {
			selectedPort.updateShip(selectedShip, shipNameBox.getText(), shipIDBox.getText(), shipCountryBox.getText(), shipURLBox.getText());
		} else if (selectedShipOnSea != null && !shipNameBox.getText().isEmpty() && !shipIDBox.getText().isEmpty() && !shipCountryBox.getText().isEmpty()) {
			cargoAPI.cargo.updateSeaShip(selectedShipOnSea, shipNameBox.getText(), shipIDBox.getText(), shipCountryBox.getText(), shipURLBox.getText());
		}
		refresh();
	}

	@FXML
	private void updatePallet() {
		smartAddReturn.setText("");
		if (selectedContainer != null && !palletIDBox.getText().isEmpty() && !palletAmountBox.getText().isEmpty() && !palletValueBox.getText().isEmpty() && !palletSizeBox.getText().isEmpty() && !palletWeightBox.getText().isEmpty()) {
			selectedContainer.updatePallet(selectedPallet, palletIDBox.getText(), palletDescBox.getText(), Integer.parseInt(palletAmountBox.getText()), Float.parseFloat(palletValueBox.getText()), Float.parseFloat(palletWeightBox.getText()), Integer.parseInt(palletSizeBox.getText()));
		} else if (selectedContainerOnShip != null && !palletIDBox.getText().isEmpty() && !palletAmountBox.getText().isEmpty() && !palletValueBox.getText().isEmpty() && !palletSizeBox.getText().isEmpty() && !palletWeightBox.getText().isEmpty()) {
			selectedContainerOnShip.updatePallet(selectedPallet, palletIDBox.getText(), palletDescBox.getText(), Integer.parseInt(palletAmountBox.getText()), Float.parseFloat(palletValueBox.getText()), Float.parseFloat(palletWeightBox.getText()), Integer.parseInt(palletSizeBox.getText()));
		}
		refresh();
	}

	@FXML
	private void updatePort() {
		if (selectedPort != null && !portNameBox.getText().isEmpty() && !portIDBox.getText().isEmpty() && !portCountryBox.getText().isEmpty()) {
			cargoAPI.cargo.updatePort(selectedPort, portNameBox.getText(), Integer.parseInt(portIDBox.getText()), portCountryBox.getText());
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
		viewFacility.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			// This will be called whenever the user selects a different item in the list
			if (newValue != null) {
				refresh();
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
				containerIDBox.setText(selectedContainer.getID() + "");
				int contSize = selectedContainer.getSize();
				if (contSize == 10) {
					length10Toggle();
				} else if (contSize == 20) {
					length20Toggle();
				} else if (contSize == 40) {
					length40Toggle();
				} else {
					lengthInvalid();
				}
				containerCapacityBox.setText(contSize * 8 * 8 + "");
				int fullSize = 0;
				if (selectedContainer.getPallets() != null) {
					CustomList<Pallet> palletsInContainer = selectedContainer.getPallets();
					for (Pallet p : palletsInContainer) {
						fullSize += p.getSize();
					}
				}
				containerSizeBox.setText("" + fullSize);

			}
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
				containerIDInfo.setText(selectedContainerOnShip.getID() + "");
				containerIDBox.setText(selectedContainerOnShip.getID() + "");
				int contSize = selectedContainerOnShip.getSize();
				if (contSize == 10) {
					length10Toggle();
				} else if (contSize == 20) {
					length20Toggle();
				} else if (contSize == 40) {
					length40Toggle();
				} else {
					lengthInvalid();
				}
				containerCapacityBox.setText(contSize * 8 * 8 + "");
				int fullSize = 0;
				if (selectedContainerOnShip.getPallets() != null) {
					CustomList<Pallet> palletsInContainer = selectedContainerOnShip.getPallets();
					for (Pallet p : palletsInContainer) {
						fullSize += p.getSize();
					}
				}
				containerSizeBox.setText("" + fullSize);
			}
		}
	}

	private void updateOnlyFromPalletLevel() {
		selectedPallet = palletListView.getSelectionModel().getSelectedItem();

		if (selectedPallet != null) {
			palletIDInfo.setText(selectedPallet.getInternationalMark() + "");
			palletIDBox.setText(selectedPallet.getInternationalMark() + "");
			palletAmountBox.setText(selectedPallet.getQuantity() + "");
			palletValueBox.setText(selectedPallet.getUnitValue() + "");
			palletWeightBox.setText(selectedPallet.getTotalWeight() + "");
			palletSizeBox.setText(selectedPallet.getSize() + "");
			palletDescBox.setText(selectedPallet.getDescription());
		}
	}
}