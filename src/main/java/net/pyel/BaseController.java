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
import java.util.ResourceBundle;

public class BaseController implements Initializable {
	private static CargoAPI cargoAPI = new CargoAPI();
	private CustomList<Port> ports;
	private CustomList<ContainerShip> shipsOnSea;
	Stage popupstage = new Stage();
	Parent popuproot;
	Scene popupScene;
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
	private void newPanel() throws IOException {
		BackgroundController.setCargoAPI(new CargoAPI());
		App.setRoot("ports");
		App.getStageInfo().setTitle("Shipping Management Panel | Ports");
		App.getStageInfo().setHeight(900);
		App.getStageInfo().setWidth(1400);
		App.getStageInfo().setResizable(false);


	}

	@FXML
	private void basePanel() throws IOException {
		BackgroundController.setCargoAPI(new CargoAPI());
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
	private ToggleButton toggleContainerButton = new ToggleButton();
	@FXML
	private ToggleButton toggleShipButton = new ToggleButton();

	@FXML
	private Label testLabel;
	private Port selectedPort;
	private ContainerShip selectedShip;

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
		initialize(null, null);
	}

	@FXML
	private void deselectContainer() {
		containerListView.getSelectionModel().clearSelection();
		dockedContainerListView.getSelectionModel().clearSelection();
		palletListView.setItems(null);
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
	private void deselectShip() {
		shipListView.getSelectionModel().clearSelection();
		dockedShipListView.getSelectionModel().clearSelection();
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
			System.out.println(newPort + " added.");
			refresh();
		}
	}

	@FXML
	private void addShip() {
		if (selectedPort != null && !shipNameBox.getText().isEmpty() && !shipIDBox.getText().isEmpty() && !shipURLBox.getText().isEmpty()) {
			ContainerShip newShip = new ContainerShip(shipNameBox.getText(), shipIDBox.getText(), null, null, null);
			if (selectedPort.getShips() == null) {
				selectedPort.setShips(new CustomList<>());
			}
			selectedPort.addContainerShip(newShip);
			System.out.println(newShip + " added.");
			refresh();
		}
	}

	@FXML
	private void addContainer() {

		Container newContainer = new Container(55, 5, null);
		if (selectedShip.getContainers() == null) {
			selectedShip.setContainers(new CustomList<>());
		}
		selectedShip.addContainer(newContainer);
		System.out.println(newContainer + " added.");
		updateOnlyFromShipLevel();

	}

	@FXML
	private void addContainerOnShore() {

		Container newContainer = new Container(55, 5, null);
		if (selectedPort.getContainers() == null) {
			selectedPort.setContainers(new CustomList<>());
		}
		selectedPort.addContainer(newContainer);
		System.out.println(newContainer + " added.");
		updateData();

	}

	@FXML
	private void updateShip() {
	}

	@FXML
	private void updatePort() {
		if (selectedPort != null && !portNameBox.getText().isEmpty() && !portIDBox.getText().isEmpty() && !portCountryBox.getText().isEmpty()) {
			selectedPort.update(portNameBox.getText(), Integer.parseInt(portIDBox.getText()), portCountryBox.getText());
			System.out.println(selectedPort + " updated.");
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
			// This will be called whenever the user selects a different item in the list
			if (newValue != null) {
				// Call initialize or any specific update method
				//refresh();
				updateOnlyFromShipLevel();
			}
		});
	}


	private void updateData() {
		selectedPort = portListView.getSelectionModel().getSelectedItem();
		System.out.println(selectedShip);
		if ((this.selectedPort != null)) {


			//CONTAINERS SECTION
			if (selectedPort.getContainers() == null) {
				dockedContainerListView.setItems(null);
			} else {
				dockedContainerListView.setItems(FXCollections.observableList(selectedPort.getContainers()));
			}
			//CONTAINERS END


			if (selectedPort.getShips() == null) {
				dockedShipListView.setItems(null);
				containerListView.setItems(null);
			} else {
				containerListView.setItems(null);
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
		if (selectedShip != null) {
			if (selectedShip.getContainers() == null) {
				containerListView.setItems(null);
				System.out.println("Hello!");
			} else {
				containerListView.setItems(FXCollections.observableList(selectedShip.getContainers()));
				System.out.println("No!");
			}
		}
	}

}