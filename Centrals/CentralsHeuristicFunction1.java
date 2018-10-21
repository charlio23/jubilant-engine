package Centrals;

import aima.search.framework.HeuristicFunction;
import IA.Energia.*;
public class CentralsHeuristicFunction1 extends AbsHeuristicFunction {

    public double heuristicFunction(CentralsBoard board) {
        return board.getHeuristicValue1();
    }
}
