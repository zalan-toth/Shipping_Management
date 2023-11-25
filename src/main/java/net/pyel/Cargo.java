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

	public void setCurrency(String currency) {
		this.currency = currency;
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
								Pallet p = currentPallets.get(j);
								goodsToReturn.add("                > [PLLT] " + p);
								goodsToReturn.add("                      > (GOODS) Size:" + p.getSize() + ", Weight:" + p.getTotalWeight());
								goodsToReturn.add("                                Quantity:" + p.getQuantity() + ", Unit Value:" + p.getUnitValue());
								goodsToReturn.add("                                Description:" + p.getDescription());

							}
						}

					}

				}

			}
		}

		goodsToReturn.add("[SEA] Sailing across the narrow sea...");
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
							Pallet p = currentPallets.get(j);
							goodsToReturn.add("                > [PLLT] " + p);
							goodsToReturn.add("                      > (GOODS) Size:" + p.getSize() + ", Weight:" + p.getTotalWeight());
							goodsToReturn.add("                                Quantity:" + p.getQuantity() + ", Unit Value:" + p.getUnitValue());
							goodsToReturn.add("                                Description:" + p.getDescription());

						}
					}

				}

			}

		}
		goodsToReturn.add("[EOL] End of structured listing");
		return goodsToReturn;
	}

	private String formattedValue(float val) {
		return " {" + currency + val + "}";
	}

	public CustomList<String> returnValues() {
		CustomList<String> goodsToReturn = new CustomList<>();
		for (Port port : ports) {

			goodsToReturn.add("[PORT] " + port.toString() + formattedValue(port.getValue()));

			//CONTAINERS BEGIN
			CustomList<Container> cntrs = port.getContainers();
			if (cntrs != null) {
				for (Container cntr : cntrs) {
					goodsToReturn.add("    > [CNTR] " + cntr + formattedValue(cntr.getValue()));
					CustomList<Pallet> currentPallets = cntr.getPallets();
					if (currentPallets != null) {
						for (int j = 0; j < currentPallets.size(); j++) {
							Pallet p = currentPallets.get(j);
							goodsToReturn.add("          > [PLLT] " + p + formattedValue(p.getValue()));
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
					goodsToReturn.add("    > [SHIP] " + ship + formattedValue(ship.getValue()));
					CustomList<Container> cntrs2 = ship.getContainers();
					for (Container cntr2 : cntrs2) {
						goodsToReturn.add("          > [CNTR] " + cntr2 + formattedValue(cntr2.getValue()));
						CustomList<Pallet> currentPallets = cntr2.getPallets();
						if (currentPallets != null) {
							for (int j = 0; j < currentPallets.size(); j++) {
								Pallet p = currentPallets.get(j);
								goodsToReturn.add("                > [PLLT] " + p + formattedValue(p.getValue()));
								goodsToReturn.add("                      > (GOODS) Size:" + p.getSize() + ", Weight:" + p.getTotalWeight());
								goodsToReturn.add("                                Quantity:" + p.getQuantity() + ", Unit Value:" + p.getUnitValue());
								goodsToReturn.add("                                Description:" + p.getDescription());

							}
						}

					}

				}

			}
		}

		goodsToReturn.add("[SEA] Sailing across the narrow sea...");
		for (ContainerShip seaShip : shipsOnSea) {

			goodsToReturn.add("    > [SHIP] " + seaShip.toString() + formattedValue(seaShip.getValue()));

			//CONTAINERS BEGIN
			CustomList<Container> cntrs = seaShip.getContainers();
			if (cntrs != null) {
				for (Container cntr : cntrs) {
					goodsToReturn.add("          > [CNTR] " + cntr + formattedValue(cntr.getValue()));
					CustomList<Pallet> currentPallets = cntr.getPallets();
					if (currentPallets != null) {
						for (int j = 0; j < currentPallets.size(); j++) {
							Pallet p = currentPallets.get(j);
							goodsToReturn.add("                > [PLLT] " + p + formattedValue(p.getValue()));
							goodsToReturn.add("                      > (GOODS) Size:" + p.getSize() + ", Weight:" + p.getTotalWeight());
							goodsToReturn.add("                                Quantity:" + p.getQuantity() + ", Unit Value:" + p.getUnitValue());
							goodsToReturn.add("                                Description:" + p.getDescription());

						}
					}

				}

			}

		}
		goodsToReturn.add("[EOL] End of structured listing");
		return goodsToReturn;
	}

	public CustomList<String> findAllGoods(String name) {
		CustomList<String> goodsToReturn = new CustomList<>();
		for (Port port : ports) {
			CustomList<Container> cntrs = port.getContainers();
			if (cntrs != null) {
				for (Container cntr : cntrs) {
					CustomList<Pallet> currentPallets = cntr.getPallets();
					if (currentPallets != null) {
						for (int j = 0; j < currentPallets.size(); j++) {
							Pallet p = currentPallets.get(j);
							if (debugMode) {
								goodsToReturn.add("Comparing \"" + p.getInternationalMark() + "\" to \"" + name + "\"");
							}
							if (p.getInternationalMark() != null && name != null) {
								if (p.getInternationalMark().contains(name)) {
									goodsToReturn.add("Found " + name);
									goodsToReturn.add("  - PALLET - ");
									goodsToReturn.add("International Mark:" + p.getInternationalMark());
									goodsToReturn.add("Size:" + p.getSize() + ", Weight:" + p.getTotalWeight());
									goodsToReturn.add("Quantity:" + p.getQuantity() + ", Unit Value:" + p.getUnitValue());
									goodsToReturn.add("Description:" + p.getDescription());
									goodsToReturn.add("  - POSITION - ");
									goodsToReturn.add("In PORT " + port);
									goodsToReturn.add("  > In CONTAINER " + cntr);
									goodsToReturn.add("------------------------------------------------");
								}
							}

						}
					}

				}

			}

			//SHIPS BEGIN
			CustomList<ContainerShip> ships = port.getShips();
			if (ships != null) {
				for (ContainerShip ship : ships) {
					CustomList<Container> cntrs2 = ship.getContainers();
					for (Container cntr2 : cntrs2) {
						CustomList<Pallet> currentPallets = cntr2.getPallets();
						if (currentPallets != null) {
							for (int j = 0; j < currentPallets.size(); j++) {
								Pallet p = currentPallets.get(j);
								if (debugMode) {
									goodsToReturn.add("Comparing \"" + p.getInternationalMark() + "\" to \"" + name + "\"");
								}
								if (p.getInternationalMark() != null && name != null) {
									if (p.getInternationalMark().contains(name)) {
										goodsToReturn.add("Found " + name);
										goodsToReturn.add("  - PALLET - ");
										goodsToReturn.add("International Mark:" + p.getInternationalMark());
										goodsToReturn.add("Size:" + p.getSize() + ", Weight:" + p.getTotalWeight());
										goodsToReturn.add("Quantity:" + p.getQuantity() + ", Unit Value:" + p.getUnitValue());
										goodsToReturn.add("Description:" + p.getDescription());
										goodsToReturn.add("  - POSITION - ");
										goodsToReturn.add("In PORT " + port);
										goodsToReturn.add("  > In SHIP " + ship);
										goodsToReturn.add("    > In CONTAINER " + cntr2);
										goodsToReturn.add("------------------------------------------------");
									}
								}

							}
						}

					}

				}

			}
		}

		for (ContainerShip seaShip : shipsOnSea) {


			//CONTAINERS BEGIN
			CustomList<Container> cntrs = seaShip.getContainers();
			if (cntrs != null) {
				for (Container cntr : cntrs) {
					CustomList<Pallet> currentPallets = cntr.getPallets();
					if (currentPallets != null) {
						for (int j = 0; j < currentPallets.size(); j++) {
							Pallet p = currentPallets.get(j);
							if (debugMode) {
								goodsToReturn.add("Comparing \"" + p.getInternationalMark() + "\" to \"" + name + "\"");
							}
							if (p.getInternationalMark() != null && name != null) {
								if (p.getInternationalMark().contains(name)) {
									goodsToReturn.add("Found " + name + " in " + p.getInternationalMark());
									goodsToReturn.add("  - PALLET - ");
									goodsToReturn.add("International Mark:" + p.getInternationalMark());
									goodsToReturn.add("Size:" + p.getSize() + ", Weight:" + p.getTotalWeight());
									goodsToReturn.add("Quantity:" + p.getQuantity() + ", Unit Value:" + p.getUnitValue());
									goodsToReturn.add("Description:" + p.getDescription());
									goodsToReturn.add("  - POSITION - ");
									goodsToReturn.add("In SHIP (on sea) " + seaShip);
									goodsToReturn.add("  > In CONTAINER " + cntr);
									goodsToReturn.add("------------------------------------------------");
								}
							}

						}
					}

				}

			}

		}
		if (goodsToReturn.isEmpty()) {
			goodsToReturn.add("Unfortunately no results found for \"" + name + "\"");
		}
		return goodsToReturn;
	}

	public CustomList<String> searchForGood(String name) {
		CustomList<String> goodsToReturn = new CustomList<>();
		for (Port port : ports) {
			CustomList<Container> cntrs = port.getContainers();
			if (cntrs != null) {
				for (Container cntr : cntrs) {
					CustomList<Pallet> currentPallets = cntr.getPallets();
					if (currentPallets != null) {
						for (int j = 0; j < currentPallets.size(); j++) {
							Pallet p = currentPallets.get(j);
							if (debugMode) {
								goodsToReturn.add("Comparing \"" + p.getInternationalMark() + "\" to \"" + name + "\"");
							}
							if (name.equals(p.getInternationalMark())) {
								goodsToReturn.add("Found " + name);
								goodsToReturn.add("  - PALLET - ");
								goodsToReturn.add("International Mark:" + p.getInternationalMark());
								goodsToReturn.add("Size:" + p.getSize() + ", Weight:" + p.getTotalWeight());
								goodsToReturn.add("Quantity:" + p.getQuantity() + ", Unit Value:" + p.getUnitValue());
								goodsToReturn.add("Description:" + p.getDescription());
								goodsToReturn.add("  - POSITION - ");
								goodsToReturn.add("In PORT " + port);
								goodsToReturn.add("  > In CONTAINER " + cntr);
								return goodsToReturn;
							}

						}
					}

				}

			}

			//SHIPS BEGIN
			CustomList<ContainerShip> ships = port.getShips();
			if (ships != null) {
				for (ContainerShip ship : ships) {
					CustomList<Container> cntrs2 = ship.getContainers();
					for (Container cntr2 : cntrs2) {
						CustomList<Pallet> currentPallets = cntr2.getPallets();
						if (currentPallets != null) {
							for (int j = 0; j < currentPallets.size(); j++) {
								Pallet p = currentPallets.get(j);
								if (debugMode) {
									goodsToReturn.add("Comparing \"" + p.getInternationalMark() + "\" to \"" + name + "\"");
								}
								if (name.equals(p.getInternationalMark())) {
									goodsToReturn.add("Found " + name);
									goodsToReturn.add("  - PALLET - ");
									goodsToReturn.add("International Mark:" + p.getInternationalMark());
									goodsToReturn.add("Size:" + p.getSize() + ", Weight:" + p.getTotalWeight());
									goodsToReturn.add("Quantity:" + p.getQuantity() + ", Unit Value:" + p.getUnitValue());
									goodsToReturn.add("Description:" + p.getDescription());
									goodsToReturn.add("  - POSITION - ");
									goodsToReturn.add("In PORT " + port);
									goodsToReturn.add("  > In SHIP " + ship);
									goodsToReturn.add("    > In CONTAINER " + cntr2);
									return goodsToReturn;
								}

							}
						}

					}

				}

			}
		}

		for (ContainerShip seaShip : shipsOnSea) {


			//CONTAINERS BEGIN
			CustomList<Container> cntrs = seaShip.getContainers();
			if (cntrs != null) {
				for (Container cntr : cntrs) {
					CustomList<Pallet> currentPallets = cntr.getPallets();
					if (currentPallets != null) {
						for (int j = 0; j < currentPallets.size(); j++) {
							Pallet p = currentPallets.get(j);
							if (debugMode) {
								goodsToReturn.add("Comparing \"" + p.getInternationalMark() + "\" to \"" + name + "\"");
							}
							if (name.equals(p.getInternationalMark())) {
								goodsToReturn.add("Found " + name);
								goodsToReturn.add("  - PALLET - ");
								goodsToReturn.add("International Mark:" + p.getInternationalMark());
								goodsToReturn.add("Size:" + p.getSize() + ", Weight:" + p.getTotalWeight());
								goodsToReturn.add("Quantity:" + p.getQuantity() + ", Unit Value:" + p.getUnitValue());
								goodsToReturn.add("Description:" + p.getDescription());
								goodsToReturn.add("  - POSITION - ");
								goodsToReturn.add("In SHIP (on sea) " + seaShip);
								goodsToReturn.add("  > In CONTAINER " + cntr);
								return goodsToReturn;
							}

						}
					}

				}

			}

		}
		goodsToReturn.add("Unfortunately no results found for \"" + name + "\"");
		return goodsToReturn;
	}

	private CustomList<ContainerShip> shipsOnSea = new CustomList<>();

	private CustomList<Port> ports = new CustomList<>();
	private String currency = "€";
	private boolean debugMode = false;
	private CustomList<String> countries = new CustomList<>();

	public String getCurrency() {
		return currency;
	}

	public boolean isDebugMode() {
		return debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

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
