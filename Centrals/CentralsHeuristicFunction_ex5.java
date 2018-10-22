package Centrals;

import aima.search.framework.HeuristicFunction;
import IA.Energia.*;
public class CentralsHeuristicFunction_ex5 extends AbsHeuristicFunction {

    public double heuristicFunction(CentralsBoard board) {
        double ld =  board.getHeuristicValue_ex5();
        //System.out.println(ld);
        return ld;
    }
}
