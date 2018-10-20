package Centrals;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

import IA.Energia.Centrales;
import IA.Energia.Clientes;
import IA.Energia.Central;
import IA.Energia.Cliente;
import IA.Energia.VEnergia;

import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.ArrayList;

public class CentralsDemo {
    public static void printMainMenu(){
        System.out.println("Escoger grupo de ejecuciones");
        System.out.println("1- Grupo 1: Compara operadores");
        System.out.println("2- Grupo 2: Compara soluciones iniciales");
        System.out.println("3- Grupo 3: Experimentos para hallar parametros de SA");
        System.out.println("4- Grupo 4: HC para valores crecientes");
    //    System.out.println("5- Grupo 5: HC y SA con comienzo no garantizado");
        System.out.println("6- Grupo 6: HC y SA con otras proporciones");
        System.out.println("0- Fin");
    }
    

	public static void grupo1Menu(Scanner sc) {
        System.out.println("Escoger ejecuciones de Heuristica vs Sucesores");
        System.out.println("1- Ronda corta: nClientes = 500, repeticiones = 5, todos con todos");	
        System.out.println("2- Ronda media: nClientes = 1000, repeticiones = 5, todos con todos");	
        System.out.println("0- Atras");
        int option = sc.nextInt();
        sc.nextLine();
        switch (option) {
			case 1:
				subgrupo1Corta();
                System.out.println("");
                break;
			case 2:
				subgrupo1Media();
                System.out.println("");
                break;

            case 0:
				break;
            default:
                System.out.println("Opción inválida. Has escrito " + option);
				
				grupo2Menu(sc);
		}
	}
	
	private static void subgrupo1Corta() {
		try {
			System.out.println("heuristica sucesors execTime correct");
			int repeticiones = 5;
			int[] param = new int[]{5, 10, 25};
			double[] propc = new double[]{0.2D, 0.3D, 0.5D};
			Random r = new Random();			
            
            int[] seeds = new int[repeticiones];
            for (int i = 0; i < repeticiones; ++i) seeds[i] = r.nextInt(1729);
            

			double sumExecTime, sumGanancias;
			boolean correct;
			
			// 1 1
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction1(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction1());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(1 + " " + 1 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 1 2
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction2(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction1());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(1 + " " + 2 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 1 3
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction3(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction1());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(1 + " " + 3 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 1 4
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction4(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction1());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(1 + " " + 4 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 1 5
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction5(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction1());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(1 + " " + 5 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 1 6
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction6(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction1());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(1 + " " + 6 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			
			
			
			// 2 1
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction1(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction2());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(2 + " " + 1 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 2 2
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction2(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction2());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(2 + " " + 2 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 2 3
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction3(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction2());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(2 + " " + 3 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 2 4
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction4(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction2());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(2 + " " + 4 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 2 5
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction5(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction2());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(2 + " " + 5 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 2 6
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction6(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction2());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(2 + " " + 6 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			
			
			
			// 3 1
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction1(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction3());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(3 + " " + 1 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 3 2
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction2(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction3());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(3 + " " + 2 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 3 3
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction3(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction3());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(3 + " " + 3 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 3 4
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction4(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction3());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(3 + " " + 4 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 3 5
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction5(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction3());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(3 + " " + 5 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 3 6
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction6(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction3());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(3 + " " + 6 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			
			
			
			// 4 1
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction1(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction4());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(4 + " " + 1 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 4 2
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction2(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction4());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(4 + " " + 2 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 4 3
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction3(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction4());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(4 + " " + 3 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 4 4
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction4(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction4());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(4 + " " + 4 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 4 5
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction5(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction4());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(4 + " " + 5 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 4 6
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction6(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction4());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(4 + " " + 6 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			
			
			
			// 5 1
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction1(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction5());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(5 + " " + 1 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 5 2
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction2(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction5());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(5 + " " + 2 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 5 3
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction3(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction5());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(5 + " " + 3 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 5 4
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction4(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction5());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(5 + " " + 4 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 5 5
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction5(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction5());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(5 + " " + 5 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 5 6
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(500, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction6(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction5());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(5 + " " + 6 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private static void subgrupo1Media() {
		try {
			System.out.println("heuristica sucesors execTime correct");
			int repeticiones = 5;
			int[] param = new int[]{5, 10, 25};
			double[] propc = new double[]{0.2D, 0.3D, 0.5D};
			Random r = new Random();			
            
            int[] seeds = new int[repeticiones];
            for (int i = 0; i < repeticiones; ++i) seeds[i] = r.nextInt(1729);
            

			double sumExecTime, sumGanancias;
			boolean correct;
			
			// 1 1
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction1(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction1());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(1 + " " + 1 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 1 2
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction2(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction1());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(1 + " " + 2 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 1 3
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction3(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction1());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(1 + " " + 3 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 1 4
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction4(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction1());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(1 + " " + 4 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 1 5
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction5(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction1());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(1 + " " + 5 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 1 6
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction6(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction1());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(1 + " " + 6 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			
			
			
			// 2 1
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction1(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction2());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(2 + " " + 1 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 2 2
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction2(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction2());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(2 + " " + 2 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 2 3
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction3(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction2());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(2 + " " + 3 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 2 4
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction4(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction2());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(2 + " " + 4 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 2 5
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction5(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction2());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(2 + " " + 5 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 2 6
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction6(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction2());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(2 + " " + 6 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			
			
			
			// 3 1
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction1(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction3());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(3 + " " + 1 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 3 2
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction2(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction3());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(3 + " " + 2 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 3 3
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction3(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction3());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(3 + " " + 3 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 3 4
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction4(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction3());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(3 + " " + 4 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 3 5
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction5(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction3());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(3 + " " + 5 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 3 6
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction6(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction3());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(3 + " " + 6 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			
			
			
			// 4 1
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction1(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction4());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(4 + " " + 1 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 4 2
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction2(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction4());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(4 + " " + 2 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 4 3
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction3(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction4());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(4 + " " + 3 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 4 4
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction4(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction4());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(4 + " " + 4 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 4 5
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction5(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction4());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(4 + " " + 5 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 4 6
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction6(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction4());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(4 + " " + 6 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			
			
			
			// 5 1
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction1(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction5());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(5 + " " + 1 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 5 2
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction2(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction5());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(5 + " " + 2 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 5 3
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction3(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction5());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(5 + " " + 3 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 5 4
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction4(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction5());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(5 + " " + 4 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 5 5
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction5(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction5());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(5 + " " + 5 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			// 5 6
			sumExecTime = sumGanancias = 0;
			correct = true;	
			for (int z = 0; z < repeticiones; ++z) {
				int seed = seeds[z];
				Centrales c = new Centrales(param, seed);					
				Clientes cl = new Clientes(1000, propc, 0.75D,seed);   			
				CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
				Problem p = new Problem(centralsBoard,
										new CentralsSuccessorFunction6(),
										new CentralsGoalTest(),
										new CentralsHeuristicFunction5());
				long startTime = System.currentTimeMillis();
				HillClimbingSearch search = new HillClimbingSearch();
				SearchAgent agent = new SearchAgent(p, search);
				long endTime = System.currentTimeMillis();
				long execTime = endTime - startTime;
						
				CentralsBoard finalState = (CentralsBoard) search.getGoalState();
				sumExecTime += execTime;
				sumGanancias += finalState.getGanancia();
				correct &= finalState.isCorrect();
				if (!correct) z = repeticiones;						
			}
			System.out.println(5 + " " + 6 + " " + sumGanancias/repeticiones + " " + " " + sumExecTime/repeticiones + " " + correct + " ");
			
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	



	public static void grupo2Menu(Scanner sc) {
        System.out.println("Escoger ejecuciones");
        System.out.println("1- Subgrupo 1: Ejecucion de todas las soluciones iniciales");		
        System.out.println("0- Atras");
        int option = sc.nextInt();
        sc.nextLine();
        switch (option) {
			case 1:
				subgrupo2SolIni();
                System.out.println("");
                break;
            case 0:
				break;
            default:
                System.out.println("Opción inválida. Has escrito " + option);
				
				grupo2Menu(sc);
		}
	}

	private static void subgrupo2SolIni() {
		try {
			System.out.println("ganancia nCentrales execTime correct inicio");
			int repeticiones = 10;
			int[] param = new int[]{5, 10, 25};
			double[] propc = new double[]{0.2D, 0.3D, 0.5D};
			Random r = new Random();			
            
            int[] seeds = new int[repeticiones];
            for (int i = 0; i < repeticiones; ++i) seeds[i] = r.nextInt(1729);
            
            for (int ini = 0; ini < CentralsBoard.NUMINICIO; ++ini) {
				double sumExecTime = 0;
				double sumGanancias = 0;
				boolean correct = true;
				
				for (int j = 0; j < repeticiones; ++j) {
					int seed = seeds[ini];
					Centrales c = new Centrales(param, seed);					
					Clientes cl = new Clientes(1000,propc, 0.75D,seed);   			
					CentralsBoard centralsBoard = new CentralsBoard(c, cl, ini, seed);
					Problem p = new Problem(centralsBoard,
											new CentralsSuccessorFunction6(),
											new CentralsGoalTest(),
											new CentralsHeuristicFunction4());
					long startTime = System.currentTimeMillis();
					HillClimbingSearch search = new HillClimbingSearch();
					SearchAgent agent = new SearchAgent(p, search);
					long endTime = System.currentTimeMillis();
					long execTime = endTime - startTime;
					
					CentralsBoard finalState = (CentralsBoard) search.getGoalState();
					sumExecTime += execTime;
					sumGanancias += finalState.getGanancia();
					correct &= finalState.isCorrect();
					if (!correct) j = repeticiones;
				}
				System.out.println(sumGanancias/repeticiones + " " + param[2] + " " + sumExecTime/repeticiones + " " + correct + " " + ini);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}				
	}


	public static void grupo3Menu(Scanner sc) {
        System.out.println("Escoger ejecuciones");
        System.out.println("1- Subgrupo 1: Previo para saber steps");		
        System.out.println("2- Subgrupo 2: Comparar con k = {1, 5, 25, 125} con lambda = {1, 0.01, 0.0001} y 20000 steps");
        System.out.println("9- Todas");
        System.out.println("0- Atras");
        int option = sc.nextInt();
        sc.nextLine();
        switch (option) {
			case 1:
				subgrupo3PrevSteps();
                System.out.println("");
                break;
			case 2:
				subgrupo3KLambdaGeneral();
                System.out.println("");
                break;
			case 9:
				subgrupo3PrevSteps();
                System.out.println("1: Hecho");
				subgrupo3KLambdaGeneral();
                System.out.println("2: Hecho");
                break;
            case 0:
				break;
            default:
                System.out.println("Opción inválida. Has escrito " + option);
				
				grupo3Menu(sc);
		}
	}
	
	private static void subgrupo3PrevSteps() {
		try {
			int n = 10;
			int repeticiones = 5;
			Random r = new Random();
			int[] seeds = new int[n];
            ArrayList<ArrayList<Double>> globalValuesSA = new ArrayList(n);
            ArrayList<ArrayList<Double>> globalValuesHC = new ArrayList(n);
            ArrayList<ArrayList<Long>> globalExecTimeSA = new ArrayList(n);
            ArrayList<ArrayList<Long>> globalExecTimeHC = new ArrayList(n);
            ArrayList<ArrayList<Boolean>> globalCorrectSA = new ArrayList(n);
            ArrayList<ArrayList<Boolean>> globalCorrectHC = new ArrayList(n);
            ArrayList<ArrayList<Integer>> globalUsedStep = new ArrayList(n);
            
            int steps[] = {10000,20000,40000,70000,110000};
            int stiter = 100;
            double lambda = 0.005;
            int k = 20;
            
            int[] param = new int[]{5, 10, 25};
            double[] propc = new double[]{0.2D, 0.3D, 0.5D};           
            
            for (int i = 0; i < n; ++i) {
				int seed = r.nextInt(1729);
				seeds[i] = seed;
				Centrales c = new Centrales(param, seed);
				Clientes cl = new Clientes(1000,propc, 0.75D,seed);           
				
                ArrayList<Double> valuesSA = new ArrayList(steps.length);
                ArrayList<Double> valuesHC = new ArrayList(steps.length);
                ArrayList<Long> execTimeSA = new ArrayList(steps.length);
                ArrayList<Long> execTimeHC = new ArrayList(steps.length);
                ArrayList<Boolean> correctSA = new ArrayList(steps.length);				
                ArrayList<Boolean> correctHC = new ArrayList(steps.length);
                ArrayList<Integer> usedSteps = new ArrayList(steps.length);						
				
				for (int j = 0; j < steps.length; ++j) {
					int step = steps[j];
					double valueMeanSA = 0;
					double valueMeanHC = 0;
					long execTimeMeanSA = 0;
					long execTimeMeanHC = 0;
					boolean correctMeanSA = true;
					boolean correctMeanHC = true;
					
					for (int z = 0; z < repeticiones; ++z) {
						CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
						Problem p = new Problem(centralsBoard,
												new CentralsSuccessorFunction6(),
												new CentralsGoalTest(),
												new CentralsHeuristicFunction4());
						long startTime = System.currentTimeMillis();
                        SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(step, stiter, k, lambda);
						SearchAgent agent = new SearchAgent(p, search);
                        //search.traceOn();
                        long endTime = System.currentTimeMillis();
                        long execTime = endTime - startTime;
						CentralsBoard finalState = (CentralsBoard) search.getGoalState();
						correctMeanSA &= finalState.isCorrect();
						if (!correctMeanSA) z = repeticiones;
                        valueMeanSA += finalState.getGanancia();
                        execTimeMeanSA += execTime;				
					}
					
					CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
					Problem p = new Problem(centralsBoard,
											new CentralsSuccessorFunction6(),
											new CentralsGoalTest(),
											new CentralsHeuristicFunction4());
					long startTime = System.currentTimeMillis();
					Search search = new HillClimbingSearch();
					SearchAgent agent = new SearchAgent(p, search);	
                    long endTime = System.currentTimeMillis();
                    long execTime = endTime - startTime;
					CentralsBoard finalState = (CentralsBoard) search.getGoalState();
                    valueMeanHC = finalState.getGanancia();
                    execTimeMeanHC = execTime;											

                    valueMeanSA /= repeticiones;
                    execTimeMeanSA /= repeticiones;
                    
                    valuesSA.add(valueMeanSA);
                    valuesHC.add(valueMeanHC);
                    execTimeSA.add(execTimeMeanSA);
                    execTimeHC.add(execTimeMeanHC);
                    correctSA.add(correctMeanSA);
                    correctHC.add(finalState.isCorrect());
                    usedSteps.add(step);						
				}
			}
			
			System.out.println("seed steps ganSA ganHC execTimeSA execTimeHC correct");
			for (int i = 0; i < n; i++) {
				int s = seeds[i];
				for (int j = 0; j < steps.length; j++) {
					int st = globalUsedStep.get(i).get(j);
					double vSA = globalValuesSA.get(i).get(j);
					double vHC = globalValuesHC.get(i).get(j);
					double etSA = globalExecTimeSA.get(i).get(j);
					double etHC = globalExecTimeHC.get(i).get(j);
					boolean cSA = globalCorrectSA.get(i).get(j);
					boolean cHC = globalCorrectHC.get(i).get(j);
					System.out.println(s + " " + st + " " + vSA + " " + vHC + " " +  etSA + " " + etHC + " " + cSA + " " + cHC);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}

    private static void subgrupo3KLambdaGeneral() {
        try {
            int n = 10;
            int repeticiones = 5;
            Random r = new Random();
            ArrayList<Integer> seeds = new ArrayList(n);
            ArrayList<ArrayList<Double>> values = new ArrayList(n);
            ArrayList<ArrayList<Long>> execTimes = new ArrayList(n);
            ArrayList<ArrayList<Integer>> usedKs = new ArrayList(n);
            ArrayList<ArrayList<Double>> usedLambdas = new ArrayList(n);
            ArrayList<ArrayList<Boolean>> corrects = new ArrayList(n);

            double lambdas[] = {1,0.01,0.0001};
            int ks[] = {1,5,25,125};
            int steps = 20000;
            int stiter = 100;
            
            int[] param = new int[]{5, 10, 25};
            double[] propc = new double[]{0.2D, 0.3D, 0.5D};

            for (int i = 0; i < n; i++) {
                int seed = r.nextInt(1729);
                seeds.add(seed);
				Centrales c = new Centrales(param, seed);
				Clientes cl = new Clientes(1000,propc, 0.75D,seed);                
                ArrayList<Double> valuesSet = new ArrayList(lambdas.length*ks.length);
                ArrayList<Long> execTimesSet = new ArrayList(lambdas.length*ks.length);
                ArrayList<Integer> usedKsSet = new ArrayList(lambdas.length*ks.length);
                ArrayList<Double> usedLambdasSet = new ArrayList(lambdas.length*ks.length);
                ArrayList<Boolean> correctsSet = new ArrayList(lambdas.length*ks.length);
                for (int j = 0; j < lambdas.length; j++) {
                    double lambda = lambdas[j];
                    boolean correct = true;
                    for (int h = 0; h < ks.length; h++) {
                        int k = ks[h];
                        double meanValue = 0;
                        long meanExecTime = 0;
                        for (int z = 0; z < repeticiones; z++) {
                            long startTime = System.currentTimeMillis();
							CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
							Problem p = new Problem(centralsBoard,
													new CentralsSuccessorFunction6(),
													new CentralsGoalTest(),
													new CentralsHeuristicFunction4());
                            SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(steps, stiter, k, lambda);
                            SearchAgent agent = new SearchAgent(p, search);
                            long endTime = System.currentTimeMillis();
                            long execTime = endTime - startTime;
                            CentralsBoard finalState = (CentralsBoard) search.getGoalState();
                            meanValue += finalState.getGanancia();
                            meanExecTime += execTime;
                            correct &= finalState.isCorrect();
                        }
                        valuesSet.add(meanValue/repeticiones);
                        execTimesSet.add(meanExecTime/repeticiones);
                        usedKsSet.add(k);
                        usedLambdasSet.add(lambda);
                        correctsSet.add(correct);
                    }
                }
                values.add(valuesSet);
                execTimes.add(execTimesSet);
                usedKs.add( usedKsSet);
                usedLambdas.add(usedLambdasSet);
                corrects.add(correctsSet);
                System.out.println(seed);
            }
            System.out.println("seed lambda k value execTime correct");
            for (int i = 0; i < n; i++) {
                int s = seeds.get(i);
                for (int j = 0; j < lambdas.length*ks.length; j++) {
                    double l = usedLambdas.get(i).get(j);
                    int k = usedKs.get(i).get(j);
                    double v = values.get(i).get(j);
                    double et = execTimes.get(i).get(j);
                    boolean co = corrects.get(i).get(j);
                    System.out.println(s + " " + " " + l + " " + k + " " + v + " " + et + " " + co);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


	public static void grupo4Menu(Scanner sc) {
        System.out.println("Escoger ejecuciones");
        System.out.println("1- Subgrupo 1: |Clientes| = {500, 1000, 1500, 2000}");		
        System.out.println("2- Subgrupo 2: |Centrales| = {40, 80, 120, 160}");
        System.out.println("9- Todas");
        System.out.println("0- Atras");
        int option = sc.nextInt();
        sc.nextLine();
        switch (option) {
			case 1:
				subgrupo4Clientes();
                System.out.println("");
                break;
			case 2:
				subgrupo4Centrales();
                System.out.println("");
                break;
			case 9:
				subgrupo4Clientes();
                System.out.println("1: Hecho");
				subgrupo4Centrales();
                System.out.println("2: Hecho");
                break;
            case 0:
				break;
            default:
                System.out.println("Opción inválida. Has escrito " + option);
				
				grupo4Menu(sc);
		}		
	}
	
	private static void subgrupo4Clientes() {
		try {
			System.out.println("ganancia nClientes execTime correct");
			int repeticiones = 10;
			int[] param = new int[]{5, 10, 25};
			double[] propc = new double[]{0.2D, 0.3D, 0.5D};
			int[] nClientes = new int[] {500, 1000, 1500, 2000};
			Random r = new Random();			
            
            int[] seeds = new int[repeticiones];
            for (int i = 0; i < nClientes.length; ++i) {
				double sumExecTime = 0;
				double sumGanancias = 0;
				boolean correct = true;
				seeds[i] = r.nextInt(1729);
				
				for (int j = 0; j < repeticiones; ++j) {
					int seed = seeds[j];
					Centrales c = new Centrales(param, seed);					
					Clientes cl = new Clientes(nClientes[i],propc, 0.75D,seed);   			
					CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
					Problem p = new Problem(centralsBoard,
											new CentralsSuccessorFunction6(),
											new CentralsGoalTest(),
											new CentralsHeuristicFunction4());
					long startTime = System.currentTimeMillis();
					HillClimbingSearch search = new HillClimbingSearch();
					SearchAgent agent = new SearchAgent(p, search);
					long endTime = System.currentTimeMillis();
					long execTime = endTime - startTime;
					
					CentralsBoard finalState = (CentralsBoard) search.getGoalState();
					sumExecTime += execTime;
					sumGanancias += finalState.getGanancia();
					correct &= finalState.isCorrect();
					if (!correct) j = repeticiones;
									
				}
				System.out.println(sumGanancias/repeticiones + " " + nClientes[i] + " " + sumExecTime/repeticiones + " " + correct);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void subgrupo4Centrales() {
		try {
			System.out.println("ganancia nCentrales execTime correct");
			int repeticiones = 10;
			int[] paramIni = new int[]{5, 10, 25};
			double[] propc = new double[]{0.2D, 0.3D, 0.5D};
			int[] nCentrales = new int[] {40, 80, 120, 160};
			Random r = new Random();			
            
            int[] seeds = new int[repeticiones];
            for (int i = 0; i < nCentrales.length; ++i) {
				double sumExecTime = 0;
				double sumGanancias = 0;
				boolean correct = true;
				seeds[i] = r.nextInt(1729);
				
				int[] param = new int[paramIni.length];
				for (int h = 0; h < param.length; ++h) {
					param[h] = paramIni[h]*nCentrales[i]/40;
				}
				
				for (int j = 0; j < repeticiones; ++j) {
					int seed = seeds[j];
					Centrales c = new Centrales(param, seed);					
					Clientes cl = new Clientes(1000,propc, 0.75D,seed);   			
					CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
					Problem p = new Problem(centralsBoard,
											new CentralsSuccessorFunction6(),
											new CentralsGoalTest(),
											new CentralsHeuristicFunction4());
					long startTime = System.currentTimeMillis();
					HillClimbingSearch search = new HillClimbingSearch();
					SearchAgent agent = new SearchAgent(p, search);
					long endTime = System.currentTimeMillis();
					long execTime = endTime - startTime;
					
					CentralsBoard finalState = (CentralsBoard) search.getGoalState();
					sumExecTime += execTime;
					sumGanancias += finalState.getGanancia();
					correct &= finalState.isCorrect();
					if (!correct) j = repeticiones;
									
				}
				System.out.println(sumGanancias/repeticiones + " " + nCentrales[i] + " " + sumExecTime/repeticiones + " " + correct);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}


	public static void grupo6Menu(Scanner sc) {
        System.out.println("Escoger ejecuciones");
        System.out.println("1- Subgrupo 1: HC y propc[2] = {25, 50, 100}");		
        System.out.println("2- Subgrupo 2: SA y propc[2] = {25, 50, 100}");
        System.out.println("9- Todas");
        System.out.println("0- Atras");
        int option = sc.nextInt();
        sc.nextLine();
        switch (option) {
			case 1:
				subgrupo6HC();
                System.out.println("");
                break;
			case 2:
				subgrupo6SA();
                System.out.println("");
                break;
			case 9:
				subgrupo6HC();
                System.out.println("1: Hecho");
				subgrupo6SA();
                System.out.println("2: Hecho");
                break;
            case 0:
				break;
            default:
                System.out.println("Opción inválida. Has escrito " + option);
				
				grupo6Menu(sc);
		}		
	}

	private static void subgrupo6HC() {
		try {
			System.out.println("ganancia nCentrales execTime correct tipoA tipoB tipoC");
			int repeticiones = 10;
			int[] paramIni = new int[]{5, 10, 25};
			double[] propc = new double[]{0.2D, 0.3D, 0.5D};
			Random r = new Random();			
            
            int[] seeds = new int[repeticiones];
            for (int i = 0; i < repeticiones; ++i) seeds[i] = r.nextInt(1729);
            
            for (int i = 1; i <= 3; ++i) {
				double sumExecTime = 0;
				double sumGanancias = 0;
				boolean correct = true;
				
				int[] param = new int[paramIni.length];
				param[2] = i*paramIni[2];
				
				double[] meanPercent = new double[3];
				for (int j = 0; j < repeticiones; ++j) {
					int seed = seeds[j];
					Centrales c = new Centrales(param, seed);					
					Clientes cl = new Clientes(1000,propc, 0.75D,seed);   			
					CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
					Problem p = new Problem(centralsBoard,
											new CentralsSuccessorFunction6(),
											new CentralsGoalTest(),
											new CentralsHeuristicFunction4());
					long startTime = System.currentTimeMillis();
					HillClimbingSearch search = new HillClimbingSearch();
					SearchAgent agent = new SearchAgent(p, search);
					long endTime = System.currentTimeMillis();
					long execTime = endTime - startTime;
					
					CentralsBoard finalState = (CentralsBoard) search.getGoalState();
					sumExecTime += execTime;
					sumGanancias += finalState.getGanancia();
					correct &= finalState.isCorrect();
					if (!correct) j = repeticiones;
					
					double[] percent = finalState.getPercentUsedCentrals();
					for (int h = 0; h < 3; ++h) meanPercent[h] += percent[h]/repeticiones;
				}
				System.out.println(sumGanancias/repeticiones + " " + param[2] + " " + sumExecTime/repeticiones + " " + correct + " " + meanPercent[0] + " " + meanPercent[1] + " " + meanPercent[2]);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}

	private static void subgrupo6SA() {
		try {
			System.out.println("ganancia nCentrales execTime correct tipoA tipoB tipoC");
			int repeticiones = 10;
			int[] paramIni = new int[]{5, 10, 25};
			double[] propc = new double[]{0.2D, 0.3D, 0.5D};
			Random r = new Random();			
            
            int steps = 200000;
            int stiter = 1000;
            int k = 20;
            double lambda = 0.005;
            
            int[] seeds = new int[repeticiones];
            for (int i = 0; i < repeticiones; ++i) seeds[i] = r.nextInt(1729);
            
            for (int i = 1; i <= 3; ++i) {
				int[] param = new int[paramIni.length];
				param[2] = i*paramIni[2];
				
				for (int j = 0; j < repeticiones; ++j) {
					double sumExecTime = 0;
					double sumGanancias = 0;
					boolean correct = true;
					double[] meanPercent = new double[3];
				
					int seed = seeds[j];
					Centrales c = new Centrales(param, seed);					
					Clientes cl = new Clientes(1000,propc, 0.75D,seed);  					

					int Z = 5;
					for (int z = 0; z < Z; z++) {
						long startTime = System.currentTimeMillis();
						CentralsBoard centralsBoard = new CentralsBoard(c, cl, CentralsBoard.FUZZY, seed);
						Problem p = new Problem(centralsBoard,
												new CentralsSuccessorFunction6(),
												new CentralsGoalTest(),
												new CentralsHeuristicFunction4());
						SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(steps, stiter, k, lambda);
						SearchAgent agent = new SearchAgent(p, search);
						long endTime = System.currentTimeMillis();
						long execTime = endTime - startTime;
						CentralsBoard finalState = (CentralsBoard) search.getGoalState();
						sumGanancias += finalState.getGanancia()/Z;
						sumExecTime += execTime/Z;
						correct &= finalState.isCorrect();
						if (!correct) z = Z;
						
						double[] percent = finalState.getPercentUsedCentrals();
						for (int h = 0; h < 3; ++h) meanPercent[h] += percent[h]/Z;
					}
					System.out.println(sumGanancias/repeticiones + " " + param[2] + " " + sumExecTime/repeticiones + " " + correct + " " + meanPercent[0]/repeticiones + " " + meanPercent[1]/repeticiones + " " + meanPercent[2]/repeticiones);
				}                        
			}

		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}





	public static void main(String[] args){
			Scanner sc = new Scanner(System.in);
			//Bienvenida//
			System.out.println("SPAM! Menu principal");
			//Fin Bienvenida//
			boolean theresMore = true;
			while (theresMore) {
				printMainMenu();
				int option = sc.nextInt();
				sc.nextLine();
				switch (option) {
					case 1:
						grupo1Menu(sc);
						break;
					case 2:
						grupo2Menu(sc);
						break;
					case 3:
						grupo3Menu(sc);
						break;
					case 4:
						grupo4Menu(sc);
						break;
					case 0:
						theresMore = false;
						System.out.println("FIN");
						break;
					default:
						System.out.println("Opción inválida. Has escrito " + option);
				}
			}
		}
}
