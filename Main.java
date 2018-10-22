import Centrals.CentralsBoard;
import Centrals.CentralsGoalTest;
import Centrals.CentralsHeuristicFunction1;
import Centrals.CentralsHeuristicFunction2;
import Centrals.CentralsHeuristicFunction3;
import Centrals.CentralsHeuristicFunction_ex5;
import Centrals.CentralsSuccessorFunction1;
import Centrals.CentralsSuccessorFunction2;
import Centrals.CentralsSuccessorFunction3;
import Centrals.CentralsSuccessorFunction_ex5;
import IA.Energia.Centrales;
import IA.Energia.Clientes;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        try {
            Random rand = new Random(System.currentTimeMillis());
            final int Seed = rand.nextInt(10000);
            Random rIni = new Random(rand.nextInt(10000));

            int[] param = new int[]{5, 10, 25};
            Centrales c = new Centrales(param, rIni.nextInt(10000));
            double[] propc = new double[]{0.2D, 0.3D, 0.5D};
            Clientes cl = new Clientes(1000,propc, 0.75D, rIni.nextInt(10000));

            CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, Seed);
            Problem p = new Problem(centralsBoard,
                                    new CentralsSuccessorFunction2(),
                                    new CentralsGoalTest(),
                                    new CentralsHeuristicFunction1());
            Search search = new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(p, search);

            System.out.println();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
            CentralsBoard finalState = (CentralsBoard) search.getGoalState();
            finalState.printState();
            if (finalState.isCorrect()) System.out.println("Estat Correcte");
            else System.out.println("Una central sobreprodueix");
            System.out.print("Ganancia: ");
            System.out.println(finalState.getGanancia());
            //finalState.ReportUse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while(keys.hasNext()) {
            String key = (String)keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }

    }

    private static void printActions(List actions) {
        for(int i = 0; i < actions.size(); ++i) {
            String action = (String)actions.get(i);
            System.out.println(action);
        }

    }
}
