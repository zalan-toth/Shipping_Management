package net.pyel;

import net.pyel.models.Port;
import net.pyel.models.Sea;
import net.pyel.utils.CustomList;

public class Cargo {
	public Cargo(Sea sea, CustomList<Port> ports) {
		this.sea = sea;
		this.ports = ports;
	}

	private Sea sea = new Sea(new CustomList<>());
	private CustomList<Port> ports = new CustomList<>();

	public Sea getSea() {
		return sea;
	}

	public void setSea(Sea sea) {
		this.sea = sea;
	}

	public CustomList<Port> getPorts() {
		return ports;
	}

	public void setPorts(CustomList<Port> ports) {
		this.ports = ports;
	}
}
