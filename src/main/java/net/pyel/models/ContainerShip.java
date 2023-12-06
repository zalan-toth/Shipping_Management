package net.pyel.models;

import net.pyel.BackgroundController;
import net.pyel.utils.CustomList;

/**
 * Ship class
 *
 * @author Zalán Tóth
 */
public class ContainerShip {
	private String name;
	private String ID = "";
	private String country; //flagStatus
	private String photoURL;
	//TODO list of on board storage of containers
	private CustomList<Container> containers = new CustomList<>();

	public ContainerShip(String name, String ID, String country, String photoURL, CustomList<Container> containers) {
		if (country.length() > 2) {
			this.country = country.substring(0, 2);
		} else {
			this.country = country;
		}

		if (country.length() > 60) {
			this.name = name.substring(0, 60);
		} else {
			this.name = name;
		}


		if (ID.length() > 20) {
			this.ID = ID.substring(0, 20);
		} else {
			this.ID = ID;
		}
		setPhotoURL(photoURL);
		setContainers(containers);
	}


	public float getValue() {
		float val = 0;
		CustomList<Container> c = this.getContainers();
		if (c != null) {
			for (int i = 0; i < c.getSize(); i++) {
				val += c.get(i).getValue();
			}
		}
		return val;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name.length() <= 60) {
			this.name = name;
		}
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		if (ID.length() <= 20) {
			this.ID = ID;
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

	public String getPhotoURL() {
		return photoURL;
	}

	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}

	public CustomList<Container> getContainers() {
		return containers;
	}

	public void setContainers(CustomList<Container> containers) {
		this.containers = containers;
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
		containers.add(container);
	}

	public void updateContainer(Container containerToBeUpdated, int ID, int size) {
		if ((containerToBeUpdated.getCurrentTakenSize()) > (size * 8 * 8)) {
			return;
		}
		if (containerToBeUpdated.getID() == ID) {
			containerToBeUpdated.updateWithoutID(size);
		}
		containerToBeUpdated.update(ID, size);
	}

	public void removeContainerByIndex(int index) {
		containers.remove(index);
	}

	public void removeContainer(Container c) {
		BackgroundController.getCargo().getContainerID().remove((Integer) c.getID());
		containers.remove(c);
	}

	public void updateWithoutID(String name, String country, String URL) {
		setName(name);
		setCountry(country);
		setPhotoURL(URL);
	}

	public void update(String name, String ID, String country, String URL) {
		for (String checkID : BackgroundController.getCargo().getShipID()) {
			if (ID.equals(checkID)) {
				return;
			}
		}
		BackgroundController.getCargo().getShipID().remove(this.ID);
		BackgroundController.getCargo().getShipID().add(ID);
		setName(name);
		setID(ID);
		setCountry(country);
		setPhotoURL(URL);
	}

	public void addPalletToContainers(int containerIndex, Pallet pallet) {
		containers.get(containerIndex).addPallet(pallet);
	}

	public void removePalletToContainers(int containerIndex, int palletIndex) {
		containers.get(containerIndex).removePalletByIndex(palletIndex);
	}

	@Override
	public String toString() {
		return getCountry() + getID() + " - " + getName();
	}
}
