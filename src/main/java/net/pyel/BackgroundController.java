package net.pyel;

import net.pyel.models.Port;
import net.pyel.utils.CustomList;

/**
 * Manages saves and loads in the background mainly
 *
 * @author Zalán Tóth
 */
public class BackgroundController {
	private static CargoAPI cargoAPI = new CargoAPI("");
	private CustomList<Port> ports = cargoAPI.cargo.getPorts();

	public static CargoAPI getCargoAPI() {
		return cargoAPI;


	}

	public static Cargo getCargo() {
		return cargoAPI.cargo;


	}


	public static void setCargo(Cargo cargo) {
		cargoAPI.cargo = cargo;


	}

	public static void setCargoAPI(CargoAPI newCargoAPI) {
		cargoAPI = newCargoAPI;


	}

	public static void loadData() {
		load();
	}

	public static void saveData() {
		save();
	}

	private static void save() {

		try {
			System.out.println("Data save attempted.");
			cargoAPI.save();
		} catch (Exception e) {
			System.err.println("Error writing to file: " + e);
		}
	}

	private static void load() {
		try {
			System.out.println("Data load attempted.");
			cargoAPI.load();
		} catch (Exception e) {
			System.err.println("Error reading from file: " + e);
		}

	}
}