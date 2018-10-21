package Centrals;

import aima.search.framework.HeuristicFunction;
import IA.Energia.*;
public class CentralsHeuristicFunction3 extends AbsHeuristicFunction {

    public double heuristicFunction(CentralsBoard board) {
        return board.getHeuristicValue3();
    }
}
