package net.pyel.models;

import net.pyel.utils.CustomList;

/**
 * Sea class - manages ships on sea
 *
 * @author Zalán Tóth
 */
public class Sea {
	private CustomList<ContainerShip> ships = new CustomList<>();

	public Sea(CustomList<ContainerShip> ships) {
		this.ships = ships;
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
}
