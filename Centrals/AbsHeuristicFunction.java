package Centrals;

import aima.search.framework.HeuristicFunction;

public abstract class AbsHeuristicFunction implements HeuristicFunction {

    protected static final double MAXBENEFIT = 50000;

    public double getHeuristicValue(Object o) {
        return heuristicFunction((CentralsBoard) o);
    }

    abstract double heuristicFunction(CentralsBoard board);

    protected double distance(int clId, int centId) {
        return CentralsBoard.distance(clId, centId);
    }

    protected double perdida(int clId, int centId) {
        return CentralsBoard.perdida(clId, centId);
    }

    protected double costeMarcha(int centId) {
        return CentralsBoard.costeMarcha(centId);
    }

    protected double costeParada(int centId) {
        return CentralsBoard.costeParada(centId);
    }

    protected double precioTarifa(int clId) {
        return CentralsBoard.precioTarifa(clId);
    }
}
