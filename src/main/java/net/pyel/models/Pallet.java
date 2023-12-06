package net.pyel.models;

import net.pyel.BackgroundController;

/**
 * Pallet class
 *
 * @author Zalán Tóth
 */
public class Pallet {
	private String description;
	private int quantity;
	private float unitValue;
	private float totalWeight;
	private String internationalMark = "";
	private int size; //in cubic feet


	public Pallet(String description, String internationalMark, int quantity, float unitValue, float totalWeight, int size) {
		this.description = description;
		this.quantity = quantity;
		this.unitValue = unitValue;
		this.totalWeight = totalWeight;
		this.size = size;
		this.internationalMark = internationalMark;
	}

	public String getInternationalMark() {
		return internationalMark;
	}

	public void setInternationalMark(String internationalMark) {
		this.internationalMark = internationalMark;
	}

	public void updateWithoutID(String description, int quantity, float unitValue, float totalWeight, int size) {
		setDescription(description);
		setQuantity(quantity);
		setUnitValue(unitValue);
		setTotalWeight(totalWeight);
		setSize(size);
	}

	public void update(String internationalMark, String description, int quantity, float unitValue, float totalWeight, int size) {
		for (String checkID : BackgroundController.getCargo().getPalletIM()) {
			if (checkID.equals(internationalMark)) {
				return;
			}
		}
		BackgroundController.getCargo().getPalletIM().remove(this.internationalMark);
		BackgroundController.getCargo().getPalletIM().add(internationalMark);
		setInternationalMark(internationalMark);
		setDescription(description);
		setQuantity(quantity);
		setUnitValue(unitValue);
		setTotalWeight(totalWeight);
		setSize(size);
	}

	public float getValue() {
		return unitValue * quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getUnitValue() {
		return unitValue;
	}

	public void setUnitValue(float unitValue) {
		this.unitValue = unitValue;
	}

	public float getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(float totalWeight) {
		this.totalWeight = totalWeight;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return internationalMark + " [" + size + "]";
	}
}
