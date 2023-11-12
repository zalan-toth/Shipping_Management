package net.pyel;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
	Stage popupstage = new Stage();
	Parent popuproot;
	Scene popupScene;

	public BaseController() {
		cargoAPI = BackgroundController.getCargoAPI();
		ports = cargoAPI.cargo.getPorts();
		popupstage.initModality(Modality.WINDOW_MODAL);
		popupstage.setResizable(false);
		popupstage.close();
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
		ports = cargoAPI.cargo.getPorts();
		initialize(null, null);
	}


	@FXML
	private void saveData() {
		BackgroundController.saveData();
	}

	@FXML
	private ListView<Pallet> palletListView = new ListView<>();
	@FXML
	private ListView<Container> containerListView = new ListView<>();
	@FXML
	private ListView<ContainerShip> dockedShipListView = new ListView<>();
	@FXML
	private ListView<ContainerShip> shipListView = new ListView<>();
	@FXML
	private ListView<Port> portListView = new ListView<>();
	@FXML
	private TextField popUpAddPortName = new TextField();
	@FXML
	private TextField popUpAddPortID = new TextField();
	@FXML
	private TextField popUpAddPortCountry = new TextField();
	@FXML
	private CheckBox popUpAddKeepMeOpen = new CheckBox();
	@FXML
	private Button popUpAddAddButton = new Button();

	@FXML
	private Label testLabel;
	private Port selectedPort;

	@FXML
	private void deselectPort() {
		portListView.getSelectionModel().clearSelection();
		initialize(null, null);
	}

	@FXML
	private void addAPort() {
		ContainerShip newShip = new ContainerShip("name", "11", "HU", "http:sadads", null);
		CustomList<ContainerShip> newList = new CustomList<>();
		newList.add(newShip);
		ports.add(new Port("Waterford", 67, "IE", newList));
		cargoAPI.cargo.getSea().addContainerShip(newShip);
		cargoAPI.cargo.getSea().addContainerShip(newShip);
		cargoAPI.cargo.getSea().addContainerShip(newShip);
		initialize(null, null);
	}

	@FXML
	private void addPort() {
		if (!popUpAddPortName.getText().isEmpty() && !popUpAddPortID.getText().isEmpty() && !popUpAddPortCountry.getText().isEmpty()) {
			Port newPort = new Port(popUpAddPortName.getText(), Integer.parseInt(popUpAddPortID.getText()), popUpAddPortCountry.getText(), null);
			ports.add(newPort);
			initialize(null, null);
			if (!popUpAddKeepMeOpen.isSelected()) {
				popupstage.getOnCloseRequest();
			}
			popUpAddPortCountry.setText("");
			popUpAddPortName.setText("");
			popUpAddPortID.setText("");
			System.out.println(newPort + " added.");

		}
		initialize(null, null);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//portListView.getItems().addAll(ports);
		portListView.setItems(FXCollections.observableList(ports)); //Yay!
		Port selectedPort = portListView.getSelectionModel().getSelectedItem();
		ContainerShip selectedShip = shipListView.getSelectionModel().getSelectedItem();
		Container selectedContainer = containerListView.getSelectionModel().getSelectedItem();
		Pallet selectedPallet = palletListView.getSelectionModel().getSelectedItem();
		if (cargoAPI.cargo.getSea() != null) {
			shipListView.setItems(FXCollections.observableList(cargoAPI.cargo.getSea().getShips()));
		}
		if (selectedPort != null) {
			dockedShipListView.setItems(FXCollections.observableList(selectedPort.getShips()));
		}
		if (selectedShip != null) {
			containerListView.setItems(FXCollections.observableList(selectedShip.getContainers()));
		}
		if (selectedContainer != null) {
			palletListView.setItems(FXCollections.observableList(selectedContainer.getPallets()));
		}


	}


}