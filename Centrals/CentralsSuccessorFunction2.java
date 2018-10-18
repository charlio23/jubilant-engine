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
        //board.printState();
        if(board.isCorrect()){
            System.out.println(String.valueOf(board.getGanancia()));
        }
        else System.out.println("ROP");
        for (int id = 0; id < board.getClientesSize(); ++id) {
            int jd;
            if (board.isGuaranteed(id)) jd = 0;
            else jd = -1;
            for (; jd < board.getCentralesSize(); ++jd) {
                CentralsBoard newBoard = board.getCopy();
                if (newBoard.setCentral(id, jd)) {
                    successors.add(new Successor(id + " + " + jd, newBoard));
                }
            }
        }
        for (int id = 0; id < board.getClientesSize(); ++id) {
            Random rnd = new Random(board.SEED);
            int j1 = board.getState(id);
            if(j1 != -1) {
                for(int k = 0; k < 10; ++k) {
                    int id2 = rnd.nextInt(board.getClientesSize());
                    if(id != id2) {
                        CentralsBoard newBoard = board.getCopy();
                        if (newBoard.swapCentral(id,id2)) {
                            successors.add(new Successor(id + " <=> " + id2, newBoard));
                        }
                    }
                }
            }
        }
        for (int id = 0; id < board.getClientesSize(); ++id) {
            Random rnd = new Random(board.SEED);
            int j1 = board.getState(id);
            if(j1 != -1) {
                for(int k = 0; k < 100; ++k) {
                    int id2 = rnd.nextInt(board.getClientesSize());
                    if(id != id2) {
                        CentralsBoard newBoard = board.getCopy();
                        if (newBoard.substituteClient(id, id2)) {
                            successors.add(new Successor(id + " <= " + id2, newBoard));
                        }
                    }
                }
            }
        }
        return successors;
    }
}
