package org.example;

public class Sea {
	private CustomList<ContainerShip> ships = new CustomList<>();

	public CustomList<ContainerShip> getShips() {
		return ships;
	}

	public void setShips(CustomList<ContainerShip> ships) {
		this.ships = ships;
	}
}
