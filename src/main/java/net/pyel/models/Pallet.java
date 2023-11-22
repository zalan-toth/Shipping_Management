package net.pyel.models;

public class Pallet {
	private String description;
	private int quantity;
	private float unitValue;
	private float totalWeight;
	private String internationalMark;
	private int size; //in cubic feet

	/*TODO Note that you should not allow more pallets in a container than it could hold based
	on the container’s size. For example, a 10 foot-long container can hold 8x8x10=640
	cubic feet in total, so don’t allow a combination of pallets that exceeds this (ignore
	the shape, etc. of goods/cargo).*/

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
