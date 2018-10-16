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
                if (newBoard.changeCentral(id, jd)) {
                    successors.add(new Successor(id + " + " + jd, newBoard));
                }
            }
        }
        for (int id = 0; id < board.getClientesSize(); ++id) {
            Random rnd = new Random();
            int j1 = board.getState(id);
            if(j1 != -1) {
                for(int k = 0; k < 10; ++k) {
                    int id2 = rnd.nextInt(board.getClientesSize());
                    if(id != id2) {
                        CentralsBoard newBoard = board.getCopy();
                        int j2 = board.getState(id2);
                        if(j1 != j2 && j2 != -1) {
                            if (newBoard.changeCentral(id2, j1) && newBoard.changeCentral(id, j2)) {
                                successors.add(new Successor(id + " <=> " + id2, newBoard));
                            }
                        }
                    }
                }
            }
        }
        for (int id = 0; id < board.getClientesSize(); ++id) {
            Random rnd = new Random();
            int j1 = board.getState(id);
            if(j1 != -1) {
                for(int k = 0; k < 100; ++k) {
                    int id2 = rnd.nextInt(board.getClientesSize());
                    int j2 = board.getState(id2);
                    if(id != id2) {
                        CentralsBoard newBoard = board.getCopy();
                        if (newBoard.changeCentral(id2, j1) && newBoard.changeCentral(id, -1)) {
                            successors.add(new Successor(id + " <= " + id2, newBoard));
                        }
                    }
                }
            }
        }
        return successors;
    }
}
