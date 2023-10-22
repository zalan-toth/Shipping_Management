package org.example;

public class CargoAPI {
	private final Sea sea = new Sea();
	private CustomList<Port> ports = new CustomList<>();
	private CustomList<Integer> test;

	public CargoAPI(CustomList<Integer> test) {
		this.test = test;
	}

	public void addElement() {
		test.add(1);
		test.add(2);
		test.add(3);
		test.add(4);
		test.add(5);
		test.add(6);
		test.add(7);
		test.add(8);
		test.add(9);
		test.add(912312331);
		test.add(99);

		test.remove(3);
		System.out.println("For-each loop");
		for (Object o : test) {
			System.out.println(o);
		}
		System.out.println("Normal for loop");
		for (int i = 0; i < test.size(); i++) {
			// access elements by their index (position)
			System.out.println(test.get(i));
		}
		System.out.println("Get indexOf: " + test.indexOf(4));
	}


}
