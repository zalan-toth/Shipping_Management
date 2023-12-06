package net.pyel;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import net.pyel.models.*;
import net.pyel.utils.CustomList;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

/**
 * CargoAPI, API for Cargo, save and load happens here, utilized by backgroundcontroller
 *
 * @author Zalán Tóth
 */
public class CargoAPI {

	Cargo cargo = new Cargo(new CustomList<>(), new CustomList<>(), new CustomList<>()); //center of data
	private CustomList<Integer> test;

	public String currentHandler = "";


	public CargoAPI(String handler) {
		currentHandler = handler;
		/*Port port = new Port("Triest", 1, "IT", null);
		cargo.addPort(port);

		Port port2 = new Port("Balaton", 2, "HU", null);
		cargo.addPort(port2);
		Port port3 = new Port("Yes", 3, "IE", null);
		cargo.addPort(port3);
		Port port4 = new Port("No", 4, "HU", null);
		cargo.addPort(port4);*/
	}


	//██████╗░███████╗██████╗░░██████╗██╗░██████╗████████╗███████╗███╗░░██╗░█████╗░███████╗
	//██╔══██╗██╔════╝██╔══██╗██╔════╝██║██╔════╝╚══██╔══╝██╔════╝████╗░██║██╔══██╗██╔════╝
	//██████╔╝█████╗░░██████╔╝╚█████╗░██║╚█████╗░░░░██║░░░█████╗░░██╔██╗██║██║░░╚═╝█████╗░░
	//██╔═══╝░██╔══╝░░██╔══██╗░╚═══██╗██║░╚═══██╗░░░██║░░░██╔══╝░░██║╚████║██║░░██╗██╔══╝░░
	//██║░░░░░███████╗██║░░██║██████╔╝██║██████╔╝░░░██║░░░███████╗██║░╚███║╚█████╔╝███████╗
	//╚═╝░░░░░╚══════╝╚═╝░░╚═╝╚═════╝░╚═╝╚═════╝░░░░╚═╝░░░╚══════╝╚═╝░░╚══╝░╚════╝░╚══════╝


	/**
	 * The save method uses the XStream component to write all the objects in the ArrayList
	 * to the xml file stored on the hard disk.
	 *
	 * @throws Exception An exception is thrown if an error occurred during the save e.g. drive is full.
	 */
	public void save() throws Exception {
		XStream xstream = new XStream(new DomDriver());
		ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("cargo.xml"));
		out.writeObject(cargo);
		out.close();
	}

	/**
	 * The load method uses the XStream component to read all the objects from the xml
	 * file stored on the hard disk.
	 *
	 * @throws Exception An exception is thrown if an error occurred during the load e.g. a missing file.
	 */
	public void load() throws Exception {
		//list of classes that you wish to include in the serialisation, separated by a comma
		Class<?>[] classes = new Class[]{Cargo.class, Port.class, Sea.class, ContainerShip.class, Container.class, Pallet.class};

		//setting up the xstream object with default security and the above classes
		XStream xstream = new XStream(new DomDriver());
		//XStream.setupDefaultSecurity(xstream);
		xstream.allowTypes(classes);

		//doing the actual serialisation to an XML file
		ObjectInputStream in = xstream.createObjectInputStream(new FileReader("cargo.xml"));
		cargo = (Cargo) in.readObject();
		in.close();
		if (cargo.getShipsOnSea() == null) {
			cargo.setShipsOnSea(new CustomList<>());
		}
	}

	//███████╗░██████╗░██╗░░░██╗░█████╗░██╗░░░░░░██████╗
	//██╔════╝██╔═══██╗██║░░░██║██╔══██╗██║░░░░░██╔════╝
	//█████╗░░██║██╗██║██║░░░██║███████║██║░░░░░╚█████╗░
	//██╔══╝░░╚██████╔╝██║░░░██║██╔══██║██║░░░░░░╚═══██╗
	//███████╗░╚═██╔═╝░╚██████╔╝██║░░██║███████╗██████╔╝
	//╚══════╝░░░╚═╝░░░░╚═════╝░╚═╝░░╚═╝╚══════╝╚═════╝░

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CargoAPI)) return false;

		CargoAPI cargoAPI = (CargoAPI) o;

		return Objects.equals(cargo, cargoAPI.cargo);
	}

	@Override
	public int hashCode() {
		return cargo != null ? cargo.hashCode() : 0;
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
