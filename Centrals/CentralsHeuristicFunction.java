package Centrals;

import IA.Energia.VEnergia;
import aima.search.framework.HeuristicFunction;

public abstract class CentralsHeuristicFunction implements HeuristicFunction {

	public double getHeuristicValue(Object o) {
		return heuristicFunction((CentralsBoard) o);
	}

	abstract double heuristicFunction(CentralsBoard board);

    ////// Auxiliares

    // Pre: clId es el identificador de un cliente
    //    centId es el identificador de una central
    private double distance(int clId, int centId) {
	Cliente cliente = clientes.get(clId);
	Central central = centrales.get(centId);
	double dx = cliente.getCoordX()-central.getCoordX();
	double dy = cliente.getCoordY()-central.getCoordY();
	return Math.sqrt(dx*dx + dy*dy);
    }

    // Pre: clId es el identificador de un cliente
    //    centId es el identificador de una central
    private double perdida(int clId, int centId) {
	return VEnergia.getPerdida(distance(clId, centId));
    }

    // Pre: centId es el identificador de una central
    private double costeMarcha(int centId) {
	Central central = centrales.get(centId);
	try {
	    return VEnergia.getCosteMarcha(central.getTipo()) + VEnergia.getCosteProduccionMW(central.getTipo())*central.getProduccion();
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
	return 0;
    }

    // Pre: centId es el identificador de una central
    private double costeParada(int centId) {
	Central central = centrales.get(centId);
	try{
	    return VEnergia.getCosteParada(central.getTipo());
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
	return 0;
    }

    // Pre: clId es el identificador de un cliente
    private double precioTarifa(int clId) {
	Cliente cliente = clientes.get(clId);
	try {
	    if (cliente.getTipo() == GARANTIZADO) {
		return VEnergia.getTarifaClienteGarantizada(cliente.getContrato())*cliente.getConsumo();
	    } else {
		return VEnergia.getTarifaClienteNoGarantizada(cliente.getContrato())*cliente.getConsumo();
	    }
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
	return 0;
    }

    public int getClientesSize() {
	return clientes.size();
    }

    public int getCentralesSize() {
	return centrales.size();
    }

    public boolean isGuaranteed(int id) {
	return clientes.get(id).getTipo() == GARANTIZADO;
    }

	

}
