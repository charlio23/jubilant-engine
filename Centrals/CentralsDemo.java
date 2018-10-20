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
        System.out.println("0- Fin");
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
            Centrales c = new Centrales(param, 4);
            double[] propc = new double[]{0.2D, 0.3D, 0.5D};
            Clientes cl = new Clientes(1000,propc, 0.5D, 1);           
            
            for (int i = 0; i < n; ++i) {
				int seed = r.nextInt(1729);
				seeds[i] = seed;
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
            Centrales c = new Centrales(param, 4);
            double[] propc = new double[]{0.2D, 0.3D, 0.5D};
            Clientes cl = new Clientes(1000,propc, 0.5D, 1);

            for (int i = 0; i < n; i++) {
                int seed = r.nextInt(1729);
                //seeds.set(i,seed);
                seeds.add(seed);
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
                    System.out.println(s + " " + " " + l + " " + k + " " + v + " " + et + " " + c);

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
					case 3:
						grupo3Menu(sc);
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
