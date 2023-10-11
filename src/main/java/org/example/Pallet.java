package org.example;

public class Pallet {
	private String description;
	private int quantity;
	private float unitValue;
	private float totalWeight;
	private int size; //in cubic feet

	/*TODO Note that you should not allow more pallets in a container than it could hold based
	on the container’s size. For example, a 10 foot-long container can hold 8x8x10=640
	cubic feet in total, so don’t allow a combination of pallets that exceeds this (ignore
	the shape, etc. of goods/cargo).*/
}
