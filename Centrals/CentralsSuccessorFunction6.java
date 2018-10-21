package Centrals;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CentralsSuccessorFunction6 extends AbsCentralsSuccessorFunction {

  static void shuffleArray(int[] ar)
  {
    Random rnd = ThreadLocalRandom.current();
    for (int i = ar.length - 1; i > 0; i--)
    {
      int index = rnd.nextInt(i + 1);
      // Simple swap
      int a = ar[index];
      ar[index] = ar[i];
      ar[i] = a;
    }
  }

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

    if (nonSaturated.length == board.getCentralesSize()) {
      int[] shuffle= new int[board.getClientesSize()];
      for (int id = 0; id < board.getClientesSize(); ++id) {
        shuffle[id] = id;
      }
      for (int id = 0; id < board.getClientesSize(); ++id) {
        shuffleArray(shuffle);
        int centralid = board.getState(id); 
        if (centralid == -1) continue;
        int conter = 300;
        for (int jd = 0; jd < board.getClientesSize(); ++jd) {
          int centraljd = board.getState(jd);
          if (centraljd != -1) continue;
          conter--;
          if (conter == 0) break;
          CentralsBoard newBoard = board.getCopy();
          if (newBoard.setCentral(id, centraljd) && newBoard.setCentral(jd, centralid)) {
            successors.add(new Successor("swap " + id + " : "+ centralid + " <-> " + jd + " : " + centraljd, newBoard));
          }
        }
      }
    }

    for (int id = 0; id < board.getClientesSize(); ++id) {
      int central = board.getState(id);
      CentralsBoard newBoard1 = board.getCopy();
      if (central != -1 && newBoard1.setCentral(id, -1)) {
        successors.add(new Successor(id + " + " + -1 + " - " + central, newBoard1));
      }
      for (int jd = 0; jd < nonSaturated.length; ++jd) {
        if (nonSaturated[jd] == central) continue;
        CentralsBoard newBoard = board.getCopy();
        if (newBoard.setCentral(id, nonSaturated[jd])) {
          successors.add(new Successor(id + " + " + nonSaturated[jd] + " - " + central, newBoard));
        }
      }
    }
    return successors;
  }
}
