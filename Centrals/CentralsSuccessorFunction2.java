package Centrals;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CentralsSuccessorFunction2 extends AbsCentralsSuccessorFunction {

    public List getSuccessors(Object o) {
        CentralsBoard board = (CentralsBoard) o;
        List successors = new ArrayList();
        Random rnd = new Random((int)board.getGanancia());
        int[] Cen = board.getNonSaturatedCentrals();
        for (int id = 0; id < board.getClientesSize(); ++id) {
            for (int jd = 0; jd < Cen.length; ++jd) {
                int curC = Cen[jd];
                CentralsBoard newBoard = board.getCopy();
                if (newBoard.setCentral(id, curC)) {
                    successors.add(new Successor(id + " + " + curC, newBoard));
                }
            }
            CentralsBoard newBoard = board.getCopy();
            if (newBoard.setCentral(id, -1)) {
                successors.add(new Successor(id + " + " + -1, newBoard));
            }
        }
        int[] satCl = board.getSaturatedClients();
        int[] nSatCl = board.getNonSaturatedClients();
        for (int i = 0; i < 10000; ++i) {
            int id = rnd.nextInt(board.getClientesSize());
            int id2 = rnd.nextInt(board.getClientesSize());
            CentralsBoard newBoard = board.getCopy();
            if(newBoard.swapCentral(id, id2)) {
                successors.add(new Successor(id  + " <=> " + id2, newBoard));
            }
        }
        for (int id = 0; id < nSatCl.length; ++id) {
            int j1 = board.getState(id);
            if(j1 != -1) {
                if(satCl.length < 100 || true) {
                    for(int id2 = 0; id2 < satCl.length; ++id2) {
                        CentralsBoard newBoard = board.getCopy();
                        if(newBoard.substituteClient(nSatCl[id], satCl[id2])) {
                            successors.add(new Successor(nSatCl[id]  + " <= " + satCl[id2], newBoard));
                        }
                    }
                }
                else {
                    for(int k = 0; k < 100; ++k) {
                        int id2 = rnd.nextInt(satCl.length);
                        CentralsBoard newBoard = board.getCopy();
                        if (newBoard.substituteClient(nSatCl[id], satCl[id2])) {
                            successors.add(new Successor(nSatCl[id] + " <= " + satCl[id2], newBoard));
                        }
                    }
                }
            }
        }
        return successors;
    }
}
