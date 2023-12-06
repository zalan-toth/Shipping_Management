package net.pyel;

import net.pyel.models.Container;
import net.pyel.models.ContainerShip;
import net.pyel.models.Pallet;
import net.pyel.models.Port;
import net.pyel.utils.CustomList;
import net.pyel.utils.StringSimilarity;

import java.util.Objects;

/**
 * Cargo - Every cargo data can be found in Cargo, the object of this class is saved and loaded, it includes algorithms for functions
 *
 * @author Zalán Tóth
 */
public class Cargo {

	private CustomList<ContainerShip> shipsOnSea = new CustomList<>();

	private CustomList<Port> ports = new CustomList<>();
	private String currency = "€";
	private boolean debugMode = false;
	private CustomList<String> countries = new CustomList<>();
	private CustomList<Integer> containerID = new CustomList<>();
	private CustomList<String> shipID = new CustomList<>();
	private CustomList<String> palletIM = new CustomList<>();

	public CustomList<Integer> getContainerID() {
		return containerID;
	}

	public void setContainerID(CustomList<Integer> containerID) {
		this.containerID = containerID;
	}

	public CustomList<String> getShipID() {
		return shipID;
	}

	public void setShipID(CustomList<String> shipID) {
		this.shipID = shipID;
	}

	public CustomList<String> getPalletIM() {
		return palletIM;
	}

	public void setPalletIM(CustomList<String> palletIM) {
		this.palletIM = palletIM;
	}

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

	private float getSystemValuation() {
		float totalValuation = 0;
		if (ports == null) {
			return totalValuation;
		}
		for (Port port : ports) {
			totalValuation += port.getValue();
		}
		return totalValuation;
	}

	private String formattedValue(float val) {
		return " {" + currency + val + "}";
	}

	public CustomList<String> returnValues() {
		CustomList<String> goodsToReturn = new CustomList<>();
		goodsToReturn.add("TOTAL VALUATION OF THE SYSTEM: " + formattedValue(getSystemValuation()));
		goodsToReturn.add("");
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
									goodsToReturn.add("Found " + name + " in " + p.getInternationalMark());
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
										goodsToReturn.add("Found " + name + " in " + p.getInternationalMark());
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


	public String getCurrency() {
		return currency;
	}

	public boolean isDebugMode() {
		return debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

	public CustomList<String> smartAdd(Pallet pallet) {
		CustomList<String> listToReturn = new CustomList<>();
		double bestResultSoFar = 0;
		int area = 0;
		int possibleElements = 0;
		double defaultSimilarityValue = 0.5;
		Port bestPortSoFar = null;
		ContainerShip bestShipSoFar = null;
		Container bestContainerSoFar = null;
		Pallet bestPalletSoFar = null;
		if (ports != null) {
			for (Port po1 : ports) {
				if (po1.getShips() != null) {
					for (ContainerShip sh1 : po1.getShips()) {
						if (sh1.getContainers() != null) {
							for (Container co1 : sh1.getContainers()) {
								if (co1.getPallets() != null) {
									for (Pallet pa1 : co1.getPallets()) {
										if (pa1.getInternationalMark() != null && !Objects.equals(pa1.getInternationalMark(), pallet.getInternationalMark())) {
											double currentSimilarity = StringSimilarity.findSimilarity(pa1.getInternationalMark(), pallet.getInternationalMark());
											if (currentSimilarity > defaultSimilarityValue && currentSimilarity > bestResultSoFar) {
												if (debugMode) {
													listToReturn.add("FOUND POSSIBLE LOCATION> " + "@ " + pa1 + " [" + currentSimilarity * 100 + "%]");
												}
												possibleElements++;
												if (co1.addPallet(pallet)) {
													if (debugMode) {
														listToReturn.add("  - ADD SUCCESS, STORING LOCATION");
													}
													area = 1;
													bestResultSoFar = currentSimilarity;
													bestPortSoFar = po1;
													bestShipSoFar = sh1;
													bestContainerSoFar = co1;
													bestPalletSoFar = pa1;
													co1.removePallet(pallet);
												} else {
													if (debugMode) {
														listToReturn.add("  - ADD FAILED, DROPPING LOCATION");
													}

												}
												if (debugMode) {
													listToReturn.add("");
												}
											}
										}
									}
								}
							}
						}
					}
				}
				if (po1.getContainers() != null) {
					for (Container co2 : po1.getContainers()) {
						if (co2.getPallets() != null) {
							for (Pallet pa2 : co2.getPallets()) {
								if (pa2.getInternationalMark() != null && !Objects.equals(pa2.getInternationalMark(), pallet.getInternationalMark())) {
									double currentSimilarity = StringSimilarity.findSimilarity(pa2.getInternationalMark(), pallet.getInternationalMark());
									if (currentSimilarity > defaultSimilarityValue && currentSimilarity > bestResultSoFar) {
										if (debugMode) {
											listToReturn.add("FOUND POSSIBLE LOCATION> " + "@ " + pa2 + " [" + currentSimilarity * 100 + "%]");
										}
										possibleElements++;
										if (co2.addPallet(pallet)) {
											if (debugMode) {
												listToReturn.add("  - ADD SUCCESS, STORING LOCATION");
											}
											area = 2;
											bestResultSoFar = currentSimilarity;
											bestPortSoFar = po1;
											bestContainerSoFar = co2;
											bestPalletSoFar = pa2;
											co2.removePallet(pallet);
										} else {
											if (debugMode) {
												listToReturn.add("  - ADD FAILED, DROPPING LOCATION");
											}

										}
										if (debugMode) {
											listToReturn.add("");
										}
									}
								}
							}
						}
					}
				}

			}
		}
		if (shipsOnSea != null) {
			for (ContainerShip sh2 : shipsOnSea) {
				if (sh2.getContainers() != null) {
					for (Container co3 : sh2.getContainers()) {
						if (co3.getPallets() != null) {
							for (Pallet pa3 : co3.getPallets()) {
								if (pa3.getInternationalMark() != null && !Objects.equals(pa3.getInternationalMark(), pallet.getInternationalMark())) {
									double currentSimilarity = StringSimilarity.findSimilarity(pa3.getInternationalMark(), pallet.getInternationalMark());
									if (currentSimilarity > defaultSimilarityValue && currentSimilarity > bestResultSoFar) {
										if (debugMode) {
											listToReturn.add("FOUND POSSIBLE LOCATION> " + "@ " + pa3 + " [" + currentSimilarity * 100 + "%]");
										}
										possibleElements++;
										if (co3.addPallet(pallet)) {
											if (debugMode) {
												listToReturn.add("  - ADD SUCCESS, STORING LOCATION");
											}
											area = 1;
											bestResultSoFar = currentSimilarity;
											bestShipSoFar = sh2;
											bestContainerSoFar = co3;
											bestPalletSoFar = pa3;
											co3.removePallet(pallet);
										} else {
											if (debugMode) {
												listToReturn.add("  - ADD FAILED, DROPPING LOCATION");
											}

										}
										if (debugMode) {
											listToReturn.add("");
										}
									}
								}
							}
						}

					}
				}

			}
		}

		if (area == 0) {
			listToReturn.add("No suitable container found for " + pallet.getInternationalMark());
		} else if (area == 1) {
			bestContainerSoFar.addPallet(pallet);
			listToReturn.add("Checked  " + possibleElements + " elements.");
			listToReturn.add("Best location for pallet is " + pallet.getInternationalMark());
			listToReturn.add("based on International Mark similarity. (" + StringSimilarity.findSimilarity(bestPalletSoFar.getInternationalMark(), pallet.getInternationalMark()) * 100 + "%)");
			listToReturn.add("The location:");
			listToReturn.add("In Port " + bestPortSoFar);
			listToReturn.add("  In Ship " + bestShipSoFar);
			listToReturn.add("    In Container " + bestContainerSoFar);
		} else if (area == 2) {
			bestContainerSoFar.addPallet(pallet);
			listToReturn.add("Checked  " + possibleElements + " elements.");
			listToReturn.add("Best location for pallet is " + pallet.getInternationalMark());
			listToReturn.add("based on International Mark similarity. (" + StringSimilarity.findSimilarity(bestPalletSoFar.getInternationalMark(), pallet.getInternationalMark()) * 100 + "%)");
			listToReturn.add("The location:");
			listToReturn.add("In Port " + bestPortSoFar);
			listToReturn.add("  In Container " + bestContainerSoFar);
		} else if (area == 3) {
			bestContainerSoFar.addPallet(pallet);
			listToReturn.add("Checked  " + possibleElements + " elements.");
			listToReturn.add("Best location for pallet is " + pallet.getInternationalMark());
			listToReturn.add("based on International Mark similarity. (" + StringSimilarity.findSimilarity(bestPalletSoFar.getInternationalMark(), pallet.getInternationalMark()) * 100 + "%)");
			listToReturn.add("The location:");
			listToReturn.add("In Ship on sea " + bestShipSoFar);
			listToReturn.add("  In Container " + bestContainerSoFar);
		}
		return listToReturn;
	}

	public CustomList<String> smartAddFastMethod(Pallet pallet) { //OLD CODE FOR SMARTADD
		CustomList<String> listToReturn = new CustomList<>();
		if (ports != null) {
			for (Port po1 : ports) {
				if (po1.getShips() != null) {
					for (ContainerShip sh1 : po1.getShips()) {
						if (sh1.getContainers() != null) {
							for (Container co1 : sh1.getContainers()) {
								if (co1.getPallets() != null) {
									for (Pallet pa1 : co1.getPallets()) {
										if (pa1.getInternationalMark() != null && !Objects.equals(pa1.getInternationalMark(), pallet.getInternationalMark())) {
											if (StringSimilarity.findSimilarity(pa1.getInternationalMark(), pallet.getInternationalMark()) > 0.7) {
												if (co1.addPallet(pallet)) {
													listToReturn.add("Found a suitable location for pallet " + pallet.getInternationalMark());
													listToReturn.add("based on International Mark similarity. (" + StringSimilarity.findSimilarity(pa1.getInternationalMark(), pallet.getInternationalMark()) * 100 + "%)");
													listToReturn.add("The location:");
													listToReturn.add("In Port " + po1);
													listToReturn.add("  In Ship " + sh1);
													listToReturn.add("    In Container " + co1);
													return listToReturn;
												}
											}
										}
									}
								}
							}
						}
					}
				}
				if (po1.getContainers() != null) {
					for (Container co2 : po1.getContainers()) {
						if (co2.getPallets() != null) {
							for (Pallet pa2 : co2.getPallets()) {
								if (pa2.getInternationalMark() != null && !Objects.equals(pa2.getInternationalMark(), pallet.getInternationalMark())) {
									if (StringSimilarity.findSimilarity(pa2.getInternationalMark(), pallet.getInternationalMark()) > 0.7) {
										if (co2.addPallet(pallet)) {
											listToReturn.add("Found a suitable location for pallet " + pallet.getInternationalMark());
											listToReturn.add("based on International Mark similarity. (" + StringSimilarity.findSimilarity(pa2.getInternationalMark(), pallet.getInternationalMark()) * 100 + "%)");
											listToReturn.add("The location:");
											listToReturn.add("In Port " + po1);
											listToReturn.add("  In Container " + co2);
											return listToReturn;
										}
									}
								}
							}
						}
					}
				}

			}
		}
		if (shipsOnSea != null) {
			for (ContainerShip sh2 : shipsOnSea) {
				if (sh2.getContainers() != null) {
					for (Container co3 : sh2.getContainers()) {
						if (co3.getPallets() != null) {
							for (Pallet pa3 : co3.getPallets()) {
								if (pa3.getInternationalMark() != null && !Objects.equals(pa3.getInternationalMark(), pallet.getInternationalMark())) {
									if (StringSimilarity.findSimilarity(pa3.getInternationalMark(), pallet.getInternationalMark()) > 0.7) {
										if (co3.addPallet(pallet)) {
											listToReturn.add("Found a suitable location for pallet " + pallet.getInternationalMark());
											listToReturn.add("based on International Mark similarity. (" + StringSimilarity.findSimilarity(pa3.getInternationalMark(), pallet.getInternationalMark()) * 100 + "%)");
											listToReturn.add("The location:");
											listToReturn.add("In Ship on sea " + sh2);
											listToReturn.add("  In Container " + co3);
											return listToReturn;
										}
									}
								}
							}
						}

					}
				}

			}
		}

		listToReturn.add("No suitable container found for " + pallet.getInternationalMark());
		return listToReturn;
	}

	public void addPort(Port port) {
		if (port.getCode() == -1) {
			return;
		}
		for (Port p : ports) {
			if (port.getCode() == p.getCode()) {
				return;
			}
		}
		ports.add(port);
	}

	public void removeShipFromSea(ContainerShip ship) {
		BackgroundController.getCargo().getShipID().remove(ship.getID());
		shipsOnSea.remove(ship);
	}

	public void removePort(Port port) {
		ports.remove(port);
	}

	public void updateShipByIndex(int index) {
		//ships.update(index);
	}

	public void updateSeaShip(ContainerShip seaShipToBeUpdated, String name, String ID, String country, String URL) {
		/*for (ContainerShip s : shipsOnSea) {
			if (s.getID() == ID && !s.equals(seaShipToBeUpdated)) {
				return;
			}
		}
		for (Port p : ports) {
			for (ContainerShip s : p.getShips()) {
				if (s.getID() == ID && !s.equals(seaShipToBeUpdated)) {
					return;
				}
			}
		}*/

		if (Objects.equals(seaShipToBeUpdated.getID(), ID)) {
			seaShipToBeUpdated.updateWithoutID(name, country, URL);
		}
		seaShipToBeUpdated.update(name, ID, country, URL);
	}

	public void updatePort(Port portToBeUpdated, String name, int code, String country) {
		for (Port p : ports) {
			if (p.getCode() == code && !p.equals(portToBeUpdated)) {
				// Found a different port with the same code, so do not update
				return;
			}
		}
		portToBeUpdated.update(name, code, country);
	}

	public void updateShipOnSea(ContainerShip shipToUpdate) {
		//ships.update(index);
	}

	public void addShipToPort(int portIndex, ContainerShip containerShip) {
		ports.get(portIndex).addContainerShip(containerShip);
	}

	public void removeShipFromPort(int portIndex, int shipIndex) {
		ports.get(portIndex).removeContainerShipByIndex(shipIndex);
	}

	public void addShipToSea(ContainerShip containerShip) {
		if (containerShip.getID() == "") {
			return;
		}
		for (String checkID : BackgroundController.getCargo().getShipID()) {
			if (containerShip.getID().equals(checkID)) {
				return;
			}
		}
		BackgroundController.getCargo().getShipID().add(containerShip.getID());
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
