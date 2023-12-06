package net.pyel.models;

import net.pyel.BackgroundController;
import net.pyel.utils.CustomList;

import java.util.Objects;

/**
 * Port class
 *
 * @author Zalán Tóth
 */
public class Port {
	private String name;
	private int code = -1; //TODO unique
	private String country;
	private CustomList<Container> containers = new CustomList<>();
	private CustomList<ContainerShip> ships = new CustomList<>();

	public Port(String name, int code, String country, CustomList<ContainerShip> ships, CustomList<Container> containers) {
		if (name.length() > 160) {
			this.name = name.substring(0, 160);
		} else {
			this.name = name;
		}
		setCode(code);
		if (country.length() > 2) {
			this.country = country.substring(0, 2);
		} else {
			this.country = country;
		}
		setShips(ships);
		setContainers(containers);
	}

	public CustomList<Container> getContainers() {
		return containers;
	}

	public void setContainers(CustomList<Container> containers) {
		this.containers = containers;
	}

	public void updateContainer(Container containerToBeUpdated, int ID, int size) {
		if ((containerToBeUpdated.getCurrentTakenSize()) > (size * 8 * 8)) {
			return;
		}
		if (containerToBeUpdated.getID() == ID) {
			containerToBeUpdated.updateWithoutID(size);
		}
		/*for (Container c : containers) {
			if (c != containerToBeUpdated) {
				System.out.println(c + "=" + containerToBeUpdated);
				if (c.getID() == ID) {
					return;
				}
			}
		}*/
		containerToBeUpdated.update(ID, size);
	}

	public void updateShip(ContainerShip shipToBeUpdated, String name, String ID, String country, String URL) {

		if (Objects.equals(shipToBeUpdated.getID(), ID)) {
			shipToBeUpdated.updateWithoutID(name, country, URL);
		}
		shipToBeUpdated.update(name, ID, country, URL);
	}

	public void update(String name, int code, String country) {
		setName(name);
		setCode(code);
		setCountry(country);
	}

	public void update(String name, int code, String country, CustomList<ContainerShip> ships) {
		setName(name);
		setCode(code);
		setCountry(country);
		setShips(ships);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name.length() <= 160) {
			this.name = name;
		}
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		if (code > 0 && code <= 100000000) {
			this.code = code;
		}
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		if (country.length() <= 2) {
			this.country = country;
		}
	}

	public CustomList<ContainerShip> getShips() {
		return ships;
	}

	public void setShips(CustomList<ContainerShip> ships) {
		this.ships = ships;
	}

	public void addContainerShip(ContainerShip containerShip) {
		if (containerShip.getID().equals("")) {
			return;
		}
		for (String checkID : BackgroundController.getCargo().getShipID()) {
			if (containerShip.getID().equals(checkID)) {
				return;
			}
		}
		BackgroundController.getCargo().getShipID().add(containerShip.getID());
		if (ships == null) {
			ships = new CustomList<>();
		}
		ships.add(containerShip);
	}

	public void removeShipFromPort(ContainerShip ship) {
		BackgroundController.getCargo().getShipID().remove(ship.getID());
		ships.remove(ship);
	}

	public void removeContainerShipByIndex(int index) {
		ships.remove(index);
	}

	public void updateContainerShipByIndex(int index, ContainerShip updatedShip) {
		ships.set(index, updatedShip);
	}

	public void addContainer(Container container) {
		if (container.getID() == -1) {
			return;
		}
		for (Integer checkID : BackgroundController.getCargo().getContainerID()) {
			if (checkID.equals(container.getID())) {
				return;
			}
		}
		BackgroundController.getCargo().getContainerID().add(container.getID());
		if (containers == null) {
			containers = new CustomList<>();
		}
		containers.add(container);
	}

	public void removeContainerByIndex(int index) {
		containers.remove(index);
	}

	public void removeContainer(Container c) {
		BackgroundController.getCargo().getContainerID().remove((Integer) c.getID());
		containers.remove(c);
	}

	public void removeShip(ContainerShip s) {
		removeShipFromPort(s);
	}

	public void updateContainerByIndex(int index) {
		//containers.update(index);
	}

	public float getValue() {
		float val = 0;
		CustomList<Container> c = this.getContainers();
		if (c != null) {
			for (int i = 0; i < c.getSize(); i++) {
				val += c.get(i).getValue();
			}
		}
		CustomList<ContainerShip> s = this.getShips();
		if (s != null) {
			for (int i = 0; i < s.getSize(); i++) {
				val += s.get(i).getValue();
			}
		}
		return val;
	}

	@Override
	public String toString() {
		return getCountry() + getCode() + " - " + getName();
	}
}
