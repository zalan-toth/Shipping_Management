package org.example;

public class CargoAPI {
	private CustomList<ContainerShip> shipsOnSea;
	private CustomList<Port> ports;
	private CustomList<Integer> test;

	public CargoAPI(CustomList<Integer> test) {
		this.test = test;
	}

	public void addElement() {
		test.add(1);
		test.add(55);
		test.add(6);
		test.add(44);
		test.add(88);
		System.out.println(test.get(0));
		/*for (Object o : test) {
			System.out.println(o);
		}*/
		for (int i = 0; i < test.size(); i++) {
			// access elements by their index (position)
			System.out.println(test.get(i));
		}
	}


}
