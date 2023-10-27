package net.pyel.models;

import net.pyel.utils.CustomList;

public class ContainerShip {
	private String name;
	private String ID;
	private String country; //flagStatus
	private String photoURL;
	//TODO list of on board storage of containers
	private CustomList<Container> containers = new CustomList<>();

	public ContainerShip(String name, String ID, String country, String photoURL, CustomList<Container> containers) {
		this.name = name;
		this.ID = ID;
		this.country = country;
		this.photoURL = photoURL;
		this.containers = containers;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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
		containers.add(container);
	}

	public void removeContainerByIndex(int index) {
		containers.remove(index);
	}

	public void updateContainerByIndex(int index) {
		//containers.update(index);
	}

	public void addPalletToContainers(int containerIndex, Pallet pallet) {
		containers.get(containerIndex).addPallet(pallet);
	}

	public void removePalletToContainers(int containerIndex, int palletIndex) {
		containers.get(containerIndex).removePalletByIndex(palletIndex);
	}

}
