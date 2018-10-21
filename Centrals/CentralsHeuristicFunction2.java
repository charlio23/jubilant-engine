package Centrals;

import aima.search.framework.HeuristicFunction;
import IA.Energia.*;
public class CentralsHeuristicFunction2 extends AbsHeuristicFunction {

    public double heuristicFunction(CentralsBoard board) {
        return board.getHeuristicValue2();
    }
}
