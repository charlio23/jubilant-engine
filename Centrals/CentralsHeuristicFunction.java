package Centrals;

import aima.search.framework.HeuristicFunction;

public class CentralsHeuristicFunction implements HeuristicFunction {

    public double getHeuristicValue(Object o) {
        return ((CentralsBoard) o).heuristicFunction();
    }
}
