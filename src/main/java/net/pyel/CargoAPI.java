package net.pyel;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import net.pyel.models.*;
import net.pyel.utils.CustomList;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CargoAPI {

	Cargo cargo = new Cargo(null, new CustomList<>());
	private CustomList<Integer> test;

	public CargoAPI() {
	}


	public void save() throws Exception {
		XStream xstream = new XStream(new DomDriver());
		ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("cargo.xml"));
		out.writeObject(cargo);
		out.close();
	}

	public void load() throws Exception {
		//list of classes that you wish to include in the serialisation, separated by a comma
		Class<?>[] classes = new Class[]{Cargo.class, Port.class, Sea.class, ContainerShip.class, Container.class, Pallet.class};

		//setting up the xstream object with default security and the above classes
		XStream xstream = new XStream(new DomDriver());
		XStream.setupDefaultSecurity(xstream);
		xstream.allowTypes(classes);

		//doing the actual serialisation to an XML file
		ObjectInputStream in = xstream.createObjectInputStream(new FileReader("cargo.xml"));
		cargo = (Cargo) in.readObject();
		in.close();
	}
   /*
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
	}*/


}
