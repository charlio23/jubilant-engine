package Centrals;

import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;
import java.util.List;

public class CentralsSuccessorFunction implements SuccessorFunction {

    public List getSuccessors(Object o) {
        CentralsBoard board = (CentralsBoard) o;
        List succesors = new ArrayList();
        
        for (int id = 0; id < board.getClientesSize(); ++id) {
            int jd;
            if (board.isGuaranteed) jd = 0;
            else jd = -1;
            for (jd; jd < getCentralesSize(); ++jd) {
                CentralsBoard newBoard = board.getCopy();
                newBoard.changeCentral(id, jd);
                successors.add(new Successor(id + " + " + jd, newBoard));
            }
        }
        
        return succesors;
    }
}
