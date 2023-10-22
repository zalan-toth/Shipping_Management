package org.example;

public class Container {
	private int ID;
	private float size; //container size (10, 20 or 40 feet long; assume 8 feet width and height).
	private CustomList<Pallet> pallets = new CustomList<>();

	public Container(int ID, float size, CustomList<Pallet> pallets) {
		this.ID = ID;
		this.size = size;
		this.pallets = pallets;
	}


	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public CustomList<Pallet> getPallets() {
		return pallets;
	}

	public void setPallets(CustomList<Pallet> pallets) {
		this.pallets = pallets;
	}
}
