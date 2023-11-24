package net.pyel;

import net.pyel.models.Container;
import net.pyel.models.ContainerShip;
import net.pyel.models.Pallet;
import net.pyel.models.Port;
import net.pyel.utils.CustomList;

public class Cargo {
	public Cargo(CustomList<ContainerShip> shipsOnSea, CustomList<Port> ports, CustomList<String> countries) {
		if (shipsOnSea == null) {
			this.shipsOnSea = new CustomList<>();
		} else {
			this.shipsOnSea = shipsOnSea;
		}
		this.ports = ports;
		this.countries = countries;
	}


	public CustomList<String> returnShips() {
		CustomList<String> shipsToReturn = new CustomList<>();
		for (Port port : ports) {
			shipsToReturn.add("[PORT] " + port.toString());
			System.out.println(port.toString());
			CustomList<ContainerShip> ships = port.getShips();
			if (ships != null) {
				for (ContainerShip ship : ships) {
					shipsToReturn.add("    > [SHIP] " + ship);
				}
			}
		}

		shipsToReturn.add("[SEA] Sailing across the narrow sea...");
		for (ContainerShip ship : shipsOnSea) {
			shipsToReturn.add("    > [SHIP] " + ship.toString());
		}
		shipsToReturn.add("[EOL] End of structured listing");
		return shipsToReturn;
	}

	public CustomList<String> returnGoods() {
		CustomList<String> goodsToReturn = new CustomList<>();
		for (Port port : ports) {

			goodsToReturn.add("[PORT] " + port.toString());

			//CONTAINERS BEGIN
			CustomList<Container> cntrs = port.getContainers();
			if (cntrs != null) {
				for (Container cntr : cntrs) {
					goodsToReturn.add("    > [CNTR] " + cntr);
					CustomList<Pallet> currentPallets = cntr.getPallets();
					if (currentPallets != null) {
						for (int j = 0; j < currentPallets.size(); j++) {
							Pallet p = currentPallets.get(j);
							goodsToReturn.add("          > [PLLT] " + p);
							goodsToReturn.add("                > (GOODS) Size:" + p.getSize() + ", Weight:" + p.getTotalWeight());
							goodsToReturn.add("                          Quantity:" + p.getQuantity() + ", Unit Value:" + p.getUnitValue());
							goodsToReturn.add("                          Description:" + p.getDescription());

						}
					}

				}

			}

			//SHIPS BEGIN
			CustomList<ContainerShip> ships = port.getShips();
			if (ships != null) {
				for (ContainerShip ship : ships) {
					goodsToReturn.add("    > [SHIP] " + ship);
					CustomList<Container> cntrs2 = ship.getContainers();
					for (Container cntr2 : cntrs2) {
						goodsToReturn.add("          > [CNTR] " + cntr2);
						CustomList<Pallet> currentPallets = cntr2.getPallets();
						if (currentPallets != null) {
							for (int j = 0; j < currentPallets.size(); j++) {
								goodsToReturn.add("                > [PLLT] " + currentPallets.get(j));

							}
						}

					}

				}

			}
		}

		goodsToReturn.add("[SEA] Sailing across the narrow seas...");
		for (ContainerShip seaShip : shipsOnSea) {

			goodsToReturn.add("    > [SHIP] " + seaShip.toString());

			//CONTAINERS BEGIN
			CustomList<Container> cntrs = seaShip.getContainers();
			if (cntrs != null) {
				for (Container cntr : cntrs) {
					goodsToReturn.add("          > [CNTR] " + cntr);
					CustomList<Pallet> currentPallets = cntr.getPallets();
					if (currentPallets != null) {
						for (int j = 0; j < currentPallets.size(); j++) {
							goodsToReturn.add("                > [PLLT] " + currentPallets.get(j));

						}
					}

				}

			}

		}
		goodsToReturn.add("[EOL] End of structured listing");
		return goodsToReturn;
	}

	private CustomList<ContainerShip> shipsOnSea = new CustomList<>();

	private CustomList<Port> ports = new CustomList<>();
	private CustomList<String> countries = new CustomList<>();

	public void addPort(Port port) {
		ports.add(port);
	}

	public void removePort(int index) {
		ports.remove(index);
	}

	public void updateShipByIndex(int index) {
		//ships.update(index);
	}

	public void addShipToPort(int portIndex, ContainerShip containerShip) {
		ports.get(portIndex).addContainerShip(containerShip);
	}

	public void removeShipFromPort(int portIndex, int shipIndex) {
		ports.get(portIndex).removeContainerShipByIndex(shipIndex);
	}

	public void addShipToSea(ContainerShip containerShip) {
		shipsOnSea.add(containerShip);
	}

	public void removeShipFromSea(int shipIndex) {
		shipsOnSea.remove(shipIndex);
	}

	public void addContainerToPort(int portIndex, Container container) {
		ports.get(portIndex).addContainer(container);
	}

	public void removeContainerFromPort(int portIndex, int containerIndex) {
		ports.get(portIndex).removeContainerByIndex(containerIndex);
	}


	public void addContainerToShip(int shipID, Container container) {
	}

	public void removeContainerFromShip(int shipID, int containerIndex) {
	}

	public void addPalletToContainer(int containerID, Pallet pallet) {
	}

	public void removePalletFromContainer(int containerID, int containerIndex) {
	}

	//░██████╗░███████╗████████╗████████╗███████╗██████╗░░██████╗
	//██╔════╝░██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝
	//██║░░██╗░█████╗░░░░░██║░░░░░░██║░░░█████╗░░██████╔╝╚█████╗░
	//██║░░╚██╗██╔══╝░░░░░██║░░░░░░██║░░░██╔══╝░░██╔══██╗░╚═══██╗
	//╚██████╔╝███████╗░░░██║░░░░░░██║░░░███████╗██║░░██║██████╔╝
	//░╚═════╝░╚══════╝░░░╚═╝░░░░░░╚═╝░░░╚══════╝╚═╝░░╚═╝╚═════╝░
	public CustomList<ContainerShip> getShipsOnSea() {
		return shipsOnSea;
	}

	public CustomList<Port> getPorts() {
		return ports;
	}

	public CustomList<ContainerShip> getAllContainerShips() {
		return null;
	}

	//░██████╗███████╗████████╗████████╗███████╗██████╗░░██████╗
	//██╔════╝██╔════╝╚══██╔══╝╚══██╔══╝██╔════╝██╔══██╗██╔════╝
	//╚█████╗░█████╗░░░░░██║░░░░░░██║░░░█████╗░░██████╔╝╚█████╗░
	//░╚═══██╗██╔══╝░░░░░██║░░░░░░██║░░░██╔══╝░░██╔══██╗░╚═══██╗
	//██████╔╝███████╗░░░██║░░░░░░██║░░░███████╗██║░░██║██████╔╝
	//╚═════╝░╚══════╝░░░╚═╝░░░░░░╚═╝░░░╚══════╝╚═╝░░╚═╝╚═════╝░


	public void setShipsOnSea(CustomList<ContainerShip> shipsOnSea) {
		this.shipsOnSea = shipsOnSea;
	}

	public void setPorts(CustomList<Port> ports) {
		this.ports = ports;
	}
}
