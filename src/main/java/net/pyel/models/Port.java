package net.pyel.models;

import net.pyel.utils.CustomList;

public class Port {
	private String name;
	private int code; //TODO unique
	private String country;
	private CustomList<Container> containers = new CustomList<>();
	private CustomList<ContainerShip> ships = new CustomList<>();

	public Port(String name, int code, String country, CustomList<ContainerShip> ships) {
		this.name = name;
		this.code = code;
		this.country = country;
		this.ships = ships;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public CustomList<ContainerShip> getShips() {
		return ships;
	}

	public void setShips(CustomList<ContainerShip> ships) {
		this.ships = ships;
	}

	public void addContainerShip(ContainerShip containerShip) {
		ships.add(containerShip);
	}

	public void removeContainerShipByIndex(int index) {
		ships.remove(index);
	}

	public void updateContainerShipByIndex(int index) {
		//ships.update(index);
	}

	public void addContainer(Container container) {
		containers.add(container);
	}

	public void removeContainerByIndex(int index) {
		containers.remove(index);
	}

	public void updateContainerByIndex(int index) {
		//containers.update(index);
	}

	@Override
	public String toString() {
		return getCountry() + getCode() + " - " + getName();
	}
}
