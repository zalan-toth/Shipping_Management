package net.pyel.models;

import net.pyel.BackgroundController;
import net.pyel.utils.CustomList;

import java.util.Objects;

/**
 * Container class
 *
 * @author Zalán Tóth
 */
public class Container {
	private int ID = -1;
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

	public int getTotalSize() {
		return size * 8 * 8;
	}

	public void setSize(int size) {
		if (size == 10 || size == 20 || size == 40) {
			this.size = size;
		}
	}

	public int getCurrentTakenSize() {
		int s = 0;
		if (pallets == null) {
			return s;
		}
		for (Pallet p : pallets) {
			s += p.getSize();
		}
		return s;
	}

	public float getValue() {
		float val = 0;
		CustomList<Pallet> p = this.getPallets();
		if (p != null) {
			for (int i = 0; i < p.getSize(); i++) {
				val += p.get(i).getValue();
			}
		}
		return val;
	}

	public CustomList<Pallet> getPallets() {
		return pallets;
	}

	public void setPallets(CustomList<Pallet> pallets) {
		this.pallets = pallets;
	}

	public boolean addPallet(Pallet pallet) {

		if (pallet.getInternationalMark().equals("")) {
			return false;
		}
		for (String checkIM : BackgroundController.getCargo().getPalletIM()) {
			if (Objects.equals(pallet.getInternationalMark(), checkIM)) {
				return false;
			}
		}

		int totalSize = 0;
		for (Pallet p : pallets) {
			totalSize += p.getSize();
		}
		if (totalSize + pallet.getSize() <= this.getTotalSize()) {
			BackgroundController.getCargo().getPalletIM().add(pallet.getInternationalMark());
			pallets.add(pallet);
			return true;
		}
		return false;
	}

	public void removePalletByIndex(int index) {
		pallets.remove(index);
	}

	public void removePallet(Pallet p) {
		BackgroundController.getCargo().getPalletIM().remove(p.getInternationalMark());
		pallets.remove(p);
	}

	public void updatePalletByIndex(int index) {
		//pallets.update(index);
	}

	public void updatePallet(Pallet palletToBeUpdated, String internationalMark, String description, int quantity, float unitValue, float totalWeight, int size) {
		for (Pallet p : pallets) {
			if (internationalMark.equals(p.getInternationalMark()) && !p.equals(palletToBeUpdated)) {
				return;
			}
		}
		int totalSize = 0;
		for (Pallet p : pallets) {
			totalSize += p.getSize();
		}
		if (totalSize + size - palletToBeUpdated.getSize() <= this.getTotalSize()) {

			if (Objects.equals(palletToBeUpdated.getInternationalMark(), internationalMark)) {
				palletToBeUpdated.updateWithoutID(description, quantity, unitValue, totalWeight, size);
			}
			palletToBeUpdated.update(internationalMark, description, quantity, unitValue, totalWeight, size);
		}
	}

	public void updateWithoutID(int size) {
		setSize(size);
	}

	public void update(int ID, int size) {
		for (Integer checkID : BackgroundController.getCargo().getContainerID()) {
			if (checkID.equals((Integer) ID)) {
				return;
			}
		}
		BackgroundController.getCargo().getContainerID().remove((Integer) this.ID);
		BackgroundController.getCargo().getContainerID().add((Integer) ID);
		setID(ID);
		setSize(size);
	}

	public void updatePallet(Pallet pallet) {
		int totalSize = 0;
		for (Pallet p : pallets) {
			totalSize += p.getSize();
		}
		if (totalSize + pallet.getSize() <= this.getTotalSize()) {

			pallet.update(pallet.getInternationalMark(), pallet.getDescription(), pallet.getQuantity(), pallet.getUnitValue(), pallet.getTotalWeight(), pallet.getSize());
		}
	}

	@Override
	public String toString() {
		return "#" + ID + " [" + getCurrentTakenSize() + "/" + getTotalSize() + "]";
	}
}
