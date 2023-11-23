package net.pyel.models;

import net.pyel.utils.CustomList;

public class Container {
	private int ID;
	private int size; //container size (10, 20 or 40 feet long; assume 8 feet width and height).
	private CustomList<Pallet> pallets = new CustomList<>();

	public Container(int ID, int size, CustomList<Pallet> pallets) {
		setID(ID);
		setSize(size);
		this.pallets = pallets;
	}


	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		if (ID > 0 && ID < 100000000) {
			this.ID = ID;
		}
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		if (size == 10 || size == 20 || size == 40) {
			this.size = size;
		}
	}

	public CustomList<Pallet> getPallets() {
		return pallets;
	}

	public void setPallets(CustomList<Pallet> pallets) {
		this.pallets = pallets;
	}

	public void addPallet(Pallet pallet) {
		pallets.add(pallet);
	}

	public void removePalletByIndex(int index) {
		pallets.remove(index);
	}

	public void removePallet(Pallet p) {
		pallets.remove(p);
	}

	public void updatePalletByIndex(int index) {
		//pallets.update(index);
	}

	@Override
	public String toString() {
		return "#" + ID + " [" + size + "]";
	}
}
