package Centrals;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;
import java.util.List;

public class CentralsSuccessorFunction implements SuccessorFunction {

    public List getSuccessors(Object o) {
        CentralsBoard board = (CentralsBoard) o;
        List successors = new ArrayList();
        
        for (int id = 0; id < board.getClientesSize(); ++id) {
            int jd;
            if (board.isGuaranteed(id)) jd = 0;
            else jd = -1;
            
            for (; board.getCentralesSize() >= jd; ++jd) {
                CentralsBoard newBoard = board.getCopy();
                newBoard.changeCentral(id, jd);
                successors.add(new Successor(id + " + " + jd, newBoard));
            }
        }
        
        return successors;
    }
}
