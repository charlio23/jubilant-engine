package Centrals;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CentralsSuccessorFunction4 extends AbsCentralsSuccessorFunction {

  public List getSuccessors(Object o) {
    CentralsBoard board = (CentralsBoard) o;
    List successors = new ArrayList();
    //board.printState();
    /*
    CentralsHeuristicFunction5 heuristic = new CentralsHeuristicFunction5();
    if(board.isCorrect()){
      System.out.println(String.valueOf(board.getGanancia()));
    }

    else System.out.println("ROP " + heuristic.heuristicFunction(board));
    */
    int[] nonSaturated = board.getNonSaturatedCentrals();

    for (int id = 0; id < board.getClientesSize(); ++id) {
      int central = board.getState(id);
      CentralsBoard newBoard1 = board.getCopy();
      if (board.getState(id) != -1 && newBoard1.setCentral(id, -1)) {
        successors.add(new Successor(id + " + " + -1, newBoard1));
      }
      for (int jd = 0; jd < nonSaturated.length; ++jd) {
        if (nonSaturated[jd] == board.getState(id)) continue;
        CentralsBoard newBoard = board.getCopy();
        if (newBoard.setCentral(id, nonSaturated[jd])) {
          successors.add(new Successor(id + " + " + nonSaturated[jd], newBoard));
        }
      }
    }
    return successors;
  }
}
