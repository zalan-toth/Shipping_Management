package net.pyel;

import net.pyel.models.Container;
import net.pyel.models.ContainerShip;
import net.pyel.models.Pallet;
import net.pyel.models.Port;
import net.pyel.utils.CustomList;

public class Cargo {
	public Cargo(CustomList<ContainerShip> shipsOnSea, CustomList<Port> ports, CustomList<String> countries) {
		if (shipsOnSea == null) {
			this.shipsOnSea = new CustomList<>();
		} else {
			this.shipsOnSea = shipsOnSea;
		}
		this.ports = ports;
		this.countries = countries;
	}

	private CustomList<ContainerShip> shipsOnSea = new CustomList<>();

	private CustomList<Port> ports = new CustomList<>();
	private CustomList<String> countries = new CustomList<>();

	public void addPort(Port port) {
		ports.add(port);
	}

	public void removePort(int index) {
		ports.remove(index);
	}

	public void updateShipByIndex(int index) {
		//ships.update(index);
	}

	public void addShipToPort(int portIndex, ContainerShip containerShip) {
		ports.get(portIndex).addContainerShip(containerShip);
	}

	public void removeShipFromPort(int portIndex, int shipIndex) {
		ports.get(portIndex).removeContainerShipByIndex(shipIndex);
	}

	public void addShipToSea(ContainerShip containerShip) {
		shipsOnSea.add(containerShip);
	}

	public void removeShipFromSea(int shipIndex) {
		shipsOnSea.remove(shipIndex);
	}

	public void addContainerToPort(int portIndex, Container container) {
		ports.get(portIndex).addContainer(container);
	}

	public void removeContainerFromPort(int portIndex, int containerIndex) {
		ports.get(portIndex).removeContainerByIndex(containerIndex);
	}


	public void addContainerToShip(int shipID, Container container) {
	}

	public void removeContainerFromShip(int shipID, int containerIndex) {
	}

	public void addPalletToContainer(int containerID, Pallet pallet) {
	}

	public void removePalletFromContainer(int containerID, int containerIndex) {
	}

	//░██████╗░███████╗████████╗████████╗███████╗██████╗░░██████╗
	//██╔════╝░██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝
	//██║░░██╗░█████╗░░░░░██║░░░░░░██║░░░█████╗░░██████╔╝╚█████╗░
	//██║░░╚██╗██╔══╝░░░░░██║░░░░░░██║░░░██╔══╝░░██╔══██╗░╚═══██╗
	//╚██████╔╝███████╗░░░██║░░░░░░██║░░░███████╗██║░░██║██████╔╝
	//░╚═════╝░╚══════╝░░░╚═╝░░░░░░╚═╝░░░╚══════╝╚═╝░░╚═╝╚═════╝░
	public CustomList<ContainerShip> getShipsOnSea() {
		return shipsOnSea;
	}

	public CustomList<Port> getPorts() {
		return ports;
	}

	public CustomList<ContainerShip> getAllContainerShips() {
		return null;
	}

	//░██████╗███████╗████████╗████████╗███████╗██████╗░░██████╗
	//██╔════╝██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝
	//╚█████╗░█████╗░░░░░██║░░░░░░██║░░░█████╗░░██████╔╝╚█████╗░
	//░╚═══██╗██╔══╝░░░░░██║░░░░░░██║░░░██╔══╝░░██╔══██╗░╚═══██╗
	//██████╔╝███████╗░░░██║░░░░░░██║░░░███████╗██║░░██║██████╔╝
	//╚═════╝░╚══════╝░░░╚═╝░░░░░░╚═╝░░░╚══════╝╚═╝░░╚═╝╚═════╝░


	public void setShipsOnSea(CustomList<ContainerShip> shipsOnSea) {
		this.shipsOnSea = shipsOnSea;
	}

	public void setPorts(CustomList<Port> ports) {
		this.ports = ports;
	}
}
