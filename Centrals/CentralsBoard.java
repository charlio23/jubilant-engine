package Centrals;

import IA.Energia.Centrales;
import IA.Energia.Clientes;
import IA.Energia.Central;
import IA.Energia.Cliente;
import IA.Energia.VEnergia;

import java.util.Random;
import java.util.ArrayList;

public class CentralsBoard {

  private static final double HEURISTIC2_CONST = 50000;
  private int[] state;
  private double[] suministro;
  private int[] clientesEnCentral;
  private int no_satisfets = 0;
  private boolean exercici_5 = false;
  private double ganancia;
  private double heuristicValue1;
  private double heuristicValue2;
  private double heuristicValue_ex5;
  private double overflowPersonas;

  public static Centrales centrales;
  public static Clientes clientes;

  public static final int GARANTIZADO = 0;
  public static final int NOGARANTIZADO = 1;

  public static final int ONE = 0;
  public static final int RANDOM1 = 1;
  public static final int RANDOM2 = 2;
  public static final int DISTANCE1 = 3;
  public static final int DISTANCE2 = 4;
  public static final int FUZZY = 5;
  public static final int FUZZY2 = 6;
  public static final int BUIT = 7;

  public static final int NUMINICIO = 8;

  public static int SEED;

  public static final int MAXBENEFIT = 50000;

  public CentralsBoard(Centrales centrales, Clientes clientes, int estrategia, int seed) {
    this.centrales = centrales;
    this.clientes = clientes;
    this.state = new int[clientes.size()];
    this.clientesEnCentral=new int[centrales.size()];
    this.suministro = new double[centrales.size()];

    SEED = seed;
    initialize(estrategia);
    calculaSuministro();
    calculaGanancia();
    heuristicFunction();
  }

  public CentralsBoard(int[] pCentrales, int numClientes, double[] pClientes, double propg, int estrategia,int seed) {
    try {
      state = new int[clientes.size()];
      clientes = new Clientes(numClientes, pClientes, propg, seed);
      centrales = new Centrales(pCentrales, seed);
    } catch (Exception e) {
      e.printStackTrace();
    }
    SEED = seed;
    initialize(estrategia);
  }

  private CentralsBoard() {
  }

  private void initialize(int estrategia) {
    if (estrategia == ONE) {
      // Se asigna una central a todos los clientes
      Random r = new Random(SEED);
      int cnt = r.nextInt(centrales.size());
      for (int id = 0; id < state.length; ++id)
        state[id] = cnt;
    }
    if (estrategia == RANDOM1) {
      // Se asignan centrales a clientes garantizados de forma random
      Random r = new Random(SEED);
      for (int id = 0; id < state.length; ++id) {
        if (clientes.get(id).getContrato() == GARANTIZADO) {
          state[id] = r.nextInt(centrales.size());
        } else {
          state[id] = -1;
        }
      }
    }
    if (estrategia == RANDOM2) {
      // Se asignan centrales a clientes de forma random
      Random r = new Random(SEED);
      for (int id = 0; id < state.length; ++id)
        state[id] = r.nextInt(centrales.size());
    }
    if(estrategia == DISTANCE1) {
      // Se asigna la central mas proxima a cada cliente garantizado
      for (int id = 0; id < state.length; ++id) {
        int centId = -1;
        double minDis = 150; // maxima es 100*sqrt(2)
        if (clientes.get(id).getContrato() == GARANTIZADO) {
          for (int jd = 0; jd < centrales.size(); ++jd) {
            double auxDis = distance(id, jd);
            if (auxDis < minDis) {
              minDis = auxDis;
              centId = jd;
            }
          }
        }
        state[id] = centId;
      }
    }
    if(estrategia == DISTANCE2) {
      // Se asigna la central mas proxima a cada cliente
      for (int id = 0; id < state.length; ++id) {
        int centId = -1;
        double minDis = 150; // maxima es 100*sqrt(2)
        for (int jd = 0; jd < centrales.size(); ++jd) {
          double auxDis = distance(id, jd);
          if (auxDis < minDis) {
            minDis = auxDis;
            centId = jd;
          }
        }
        state[id] = centId;
      }
    }
    if(estrategia == FUZZY) {
      Random r = new Random(SEED);
      for(int id = 0; id < state.length; ++id) {
        double cand[] = new double[centrales.size()];
        double cumChance = 0;
        for(int jd = 0; jd < centrales.size(); ++jd) {
          double reach = 1-perdida(id, jd);
          double power = centrales.get(jd).getProduccion();
          cumChance += reach*reach*power;
          cand[jd] = cumChance;
          //Maybe consider existing assignments
        }
        double frand = r.nextDouble() * cumChance;
        state[id] = -1;
        for(int jd = 0; state[id] == -1 && jd < centrales.size(); ++jd) {
          if(cand[jd] > frand) state[id] = jd;
        }
      }
    }
    if(estrategia == FUZZY2) {
      Random r = new Random(SEED);
      for(int id = 0; id < state.length; ++id) {
        if(isGuaranteed(id)) {
          double cand[] = new double[centrales.size()];
          double cumChance = 0;
          for(int jd = 0; jd < centrales.size(); ++jd) {
            double reach = 1-perdida(id, jd);
            double power = centrales.get(jd).getProduccion();
            cumChance += reach*reach*power;
            cand[jd] = cumChance;
            //Maybe consider existing assignments
          }
          double frand = r.nextDouble() * cumChance;
          state[id] = -1;
          for(int jd = 0; state[id] == -1 && jd < centrales.size(); ++jd) {
            if(cand[jd] > frand) state[id] = jd;
          }
        }
      }
    }
    if(estrategia == BUIT) {
      // Se asigna la central mas proxima a cada cliente garantizado
      for (int id = 0; id < state.length; ++id) {
        state[id] = -1;
        if (clientes.get(id).getContrato() == GARANTIZADO) {
          ++no_satisfets;
        }
        exercici_5 = true;
      }
    }
  }

  public boolean isCorrect() {
    double[] demanda = new double[centrales.size()];
    double[] maxProduccion = new double[centrales.size()];
    for (int id = 0; id < centrales.size(); ++id) {
      demanda[id] = 0;
      maxProduccion[id] = centrales.get(id).getProduccion();
    }
    for (int id = 0; id < state.length; ++id) {
      int centId = state[id];
      if (centId == -1) continue;
      demanda[centId] += clientes.get(id).getConsumo()/(1-perdida(id,centId));
      if (demanda[centId] > maxProduccion[centId]) return false;
    }
    return true;
  }

  //OPERADORES

  //Pre: centId puede ser -1 sii Cliente clId no es garantizado
  public boolean setCentral(int clId, int centId) {
    if (centId == state[clId]) return false;
    if (clientes.get(clId).getContrato() == NOGARANTIZADO || centId != -1 || exercici_5){
      if (clientes.get(clId).getContrato() == GARANTIZADO && state[clId] == -1 && centId != -1)
        --no_satisfets;
      else if (clientes.get(clId).getContrato() == GARANTIZADO && state[clId] != -1 && centId == -1)
        ++no_satisfets;
      int oldCentId = state[clId];
      state[clId] = centId;
      double nuevoOverflowPersonas = overflowPersonas;
      if (oldCentId != -1) {
        //mirem l'overflow de la central original i si penalitza el sumem
        double nuevoSuministro = suministro[oldCentId] - clientes.get(clId).getConsumo() / (1 - perdida(clId, oldCentId));
        double overflow = suministro[oldCentId] - centrales.get(oldCentId).getProduccion();
        if (overflow > 0) {
          overflow = Math.max(overflow, 1.0);
          heuristicValue1 -= overflow * overflow * MAXBENEFIT * centrales.size();
          heuristicValue_ex5 -= overflow * overflow;
          double nuevoOverflow = nuevoSuministro - centrales.get(oldCentId).getProduccion();
          if (nuevoOverflow > 0) {
            --nuevoOverflowPersonas;
            nuevoOverflow = Math.max(nuevoOverflow, 1.0);
            heuristicValue1 += nuevoOverflow * nuevoOverflow * MAXBENEFIT * centrales.size();
            heuristicValue_ex5 += nuevoOverflow * nuevoOverflow;
          } else {
            nuevoOverflowPersonas -= clientesEnCentral[oldCentId];
          }
        }
        --clientesEnCentral[oldCentId];
        // ara ganancia
        double aux = 0;
        if (nuevoSuministro <= 1e-10) {
          aux += costeMarcha(oldCentId);
          aux -= costeParada(oldCentId);
        }
        ganancia += aux;
        heuristicValue1 -=aux;
      } else {
        double aux = 0;
        aux += precioTarifa(clId);
        try {
          aux += VEnergia.getTarifaClientePenalizacion(clientes.get(clId).getContrato())*clientes.get(clId).getConsumo();
        } catch (Exception e) {
          e.printStackTrace();
        }
        ganancia += aux;
        heuristicValue1 -= aux;
      }
      if (centId != -1) {
        double nuevoSuministro = suministro[centId] + clientes.get(clId).getConsumo() / (1 - perdida(clId, state[clId]));
        double nuevoOverflow = nuevoSuministro - centrales.get(centId).getProduccion();
        if (nuevoOverflow > 0) {
          nuevoOverflow = Math.max(nuevoOverflow, 1.0);
          heuristicValue1 += nuevoOverflow * nuevoOverflow * MAXBENEFIT * centrales.size();
          heuristicValue_ex5 += nuevoOverflow * nuevoOverflow;
          double overflow = suministro[centId] - centrales.get(centId).getProduccion();
          ++nuevoOverflowPersonas;
          if (overflow > 0) {
            overflow = Math.max(overflow, 1.0);
            heuristicValue1 -= overflow * overflow * MAXBENEFIT * centrales.size();
            heuristicValue_ex5 -= overflow * overflow;
          } else {
            nuevoOverflowPersonas += clientesEnCentral[centId];
          }
        }
        ++clientesEnCentral[centId];
        // ara ganancia
        double aux = 0;
        if (suministro[centId] <= 1e-10) {
          aux -= costeMarcha(centId);
          aux += costeParada(centId);
        }
        ganancia += aux;
        heuristicValue1 -=aux;
      } else {
        double aux = 0;
        aux -= precioTarifa(clId);
        try {
          aux -= VEnergia.getTarifaClientePenalizacion(clientes.get(clId).getContrato())*clientes.get(clId).getConsumo();
        } catch (Exception e) {
          e.printStackTrace();
        }
        ganancia += aux;
        heuristicValue1 -= aux;
      }


      // arreglo suministre
      if (oldCentId != -1) {
        suministro[oldCentId] -= clientes.get(clId).getConsumo() / (1 - perdida(clId, oldCentId));
      }
      if (centId != -1) {
        suministro[centId] += clientes.get(clId).getConsumo() / (1 - perdida(clId, state[clId]));
      }
      overflowPersonas = nuevoOverflowPersonas;
      heuristicValue2 = -ganancia + overflowPersonas*overflowPersonas*HEURISTIC2_CONST;
      return true;
    }
    return false;
  }


  //simple swap de dos centrales
  public boolean swapCentral(int clId1, int clId2) {
    int cen1 = state[clId1];
    int cen2 = state[clId2];
    if(setCentral(clId1, cen2)) {
      if(setCentral(clId2,cen1)) return true;
      setCentral(clId1,cen1);
    }
    return false;
  }
  //le quito la central a un cliente con otro nuevo
  public boolean substituteClient(int clId1, int clId2) {
    int cen1 = state[clId1];
    int cen2 = state[clId2];
    if(setCentral(clId2, cen1)) {
      if(setCentral(clId1, -1)) return true;
      setCentral(clId2, cen2);
    }
    return false;
  }

  ////// Auxiliares
  // Pre: clId es el identificador de un cliente
  //    centId es el identificador de una central
  public static double distance(int clId, int centId) {
    Cliente cliente = clientes.get(clId);
    Central central = centrales.get(centId);
    double dx = cliente.getCoordX()-central.getCoordX();
    double dy = cliente.getCoordY()-central.getCoordY();
    return Math.sqrt(dx*dx + dy*dy);
  }

  // Pre: clId es el identificador de un cliente
  //    centId es el identificador de una central
  public static double perdida(int clId, int centId) {
    return VEnergia.getPerdida(distance(clId, centId));
  }

  // Pre: centId es el identificador de una central
  public static double costeMarcha(int centId) {
    Central central = centrales.get(centId);
    try {
      return VEnergia.getCosteMarcha(central.getTipo()) + VEnergia.getCosteProduccionMW(central.getTipo())*central.getProduccion();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return 0;
  }

  // Pre: centId es el identificador de una central
  public static double costeParada(int centId) {
    Central central = centrales.get(centId);
    try{
      return VEnergia.getCosteParada(central.getTipo());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return 0;
  }

  // Pre: clId es el identificador de un cliente
  public static double precioTarifa(int clId) {
    Cliente cliente = clientes.get(clId);
    try {
      if (cliente.getTipo() == GARANTIZADO) {
        return VEnergia.getTarifaClienteGarantizada(cliente.getContrato())*cliente.getConsumo();
      } else {
        return VEnergia.getTarifaClienteNoGarantizada(cliente.getContrato())*cliente.getConsumo();
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return 0;
  }

  public int getClientesSize() {
    return clientes.size();
  }

  public int getCentralesSize() {
    return centrales.size();
  }

  public boolean isGuaranteed(int id) {
    return clientes.get(id).getTipo() == GARANTIZADO;
  }

  public CentralsBoard getCopy() {
    CentralsBoard curBoard = new CentralsBoard();
    curBoard.state = this.state.clone();
    curBoard.suministro = this.suministro.clone();
    curBoard.heuristicValue1 = this.heuristicValue1;
    curBoard.heuristicValue2 = this.heuristicValue2;
    curBoard.heuristicValue_ex5 = this.heuristicValue_ex5;
    curBoard.no_satisfets = this.no_satisfets;
    curBoard.exercici_5 = this.exercici_5;
    curBoard.overflowPersonas = this.overflowPersonas;
    curBoard.ganancia = this.ganancia;
    curBoard.clientesEnCentral = this.clientesEnCentral.clone();
    return curBoard;
  }

  public void printState() {
    System.out.println("--------");
    for (int stat: state) {
      System.out.print(' ' + String.valueOf(stat));
    }
    System.out.println();
  }

  public int[] getState() {
    return state.clone();
  }

  public int getState(int jd) {
    return state[jd];
  }

  public double getConsumo(int id) {
    return clientes.get(id).getConsumo();
  }

  public double getProduccion(int jd) {
    return centrales.get(jd).getProduccion();
  }

  public int[] getNonSaturatedCentrals() {
    double[] demanda = new double[centrales.size()];
    double[] maxProduccion = new double[centrales.size()];
    for (int id = 0; id < centrales.size(); ++id) {
      demanda[id] = 0;
      maxProduccion[id] = centrales.get(id).getProduccion();
    }
    for (int id = 0; id < state.length; ++id) {
      int centId = state[id];
      if (centId == -1) continue;
      demanda[centId] += clientes.get(id).getConsumo()/(1-perdida(id,centId));
    }
    int cnt = 0;
    for (int jd = 0; jd < centrales.size(); ++jd) if(demanda[jd] <= maxProduccion[jd]) cnt++;
    int[] ans = new int[cnt];
    cnt = 0;
    for(int jd = 0; jd < centrales.size(); ++jd) if(demanda[jd] <= maxProduccion[jd]) ans[cnt++] = jd;
    return ans;
  }
  public int[] getSaturatedCentrals() {
    double[] demanda = new double[centrales.size()];
    double[] maxProduccion = new double[centrales.size()];
    for (int id = 0; id < centrales.size(); ++id) {
      demanda[id] = 0;
      maxProduccion[id] = centrales.get(id).getProduccion();
    }
    for (int id = 0; id < state.length; ++id) {
      int centId = state[id];
      if (centId == -1) continue;
      demanda[centId] += clientes.get(id).getConsumo()/(1-perdida(id,centId));
    }
    int cnt = 0;
    for (int jd = 0; jd < centrales.size(); ++jd) if(demanda[jd] > maxProduccion[jd]) cnt++;
    int[] ans = new int[cnt];
    cnt = 0;
    for(int jd = 0; jd < centrales.size(); ++jd) if(demanda[jd] > maxProduccion[jd]) ans[cnt++] = jd;
    return ans;
  }

  public int[] getSaturatedClients() {
    double[] demanda = new double[centrales.size()];
    double[] maxProduccion = new double[centrales.size()];
    for (int id = 0; id < centrales.size(); ++id) {
      demanda[id] = 0;
      maxProduccion[id] = centrales.get(id).getProduccion();
    }
    for (int id = 0; id < state.length; ++id) {
      int centId = state[id];
      if (centId == -1) continue;
      demanda[centId] += clientes.get(id).getConsumo()/(1-perdida(id,centId));
    }
    int cnt = 0;
    for (int id = 0; id < state.length; ++id) if(state[id] != -1 && demanda[state[id]] > maxProduccion[state[id]]) cnt++;
    int[] ans = new int[cnt];
    cnt = 0;
    for(int id = 0; id < state.length; ++id) if(state[id] != -1 && demanda[state[id]] > maxProduccion[state[id]]) ans[cnt++] = id;
    return ans;
  }
  public int[] getNonSaturatedClients() {
    double[] demanda = new double[centrales.size()];
    double[] maxProduccion = new double[centrales.size()];
    for (int id = 0; id < centrales.size(); ++id) {
      demanda[id] = 0;
      maxProduccion[id] = centrales.get(id).getProduccion();
    }
    for (int id = 0; id < state.length; ++id) {
      int centId = state[id];
      if (centId == -1) continue;
      demanda[centId] += clientes.get(id).getConsumo()/(1-perdida(id,centId));
    }
    int cnt = 0;
    for (int id = 0; id < state.length; ++id) if(state[id] != -1 && demanda[state[id]] < maxProduccion[state[id]]) cnt++;
    int[] ans = new int[cnt];
    cnt = 0;
    for(int id = 0; id < state.length; ++id) if(state[id] != -1 && demanda[state[id]] < maxProduccion[state[id]]) ans[cnt++] = id;
    return ans;
  }

  public double[] getPercentUsedCentrals() {
    double[] percent = new double[3];
    int[] tipoCentral = new int[3];
    boolean[] centralesEnMarcha = new boolean[centrales.size()];
    for (int id = 0; id < state.length; ++id) {
      int centId = state[id];
      if (centId != -1 && !centralesEnMarcha[centId]) {
        Central central = centrales.get(id);
        percent[central.getTipo()]++;
        tipoCentral[central.getTipo()]++;
        centralesEnMarcha[centId] = true;
      }
    }

    for (int i = 0; i < percent.length; ++i)
      percent[i] /= tipoCentral[i];

    return percent;
  }

  public double getHeuristicValue1() {
    return heuristicValue1;
  }
  public double getHeuristicValue2() {
    //System.out.println(heuristicValue2);
    return heuristicValue2;
  }
  public double getHeuristicValue3() {
    if (heuristicValue1 > 1) return heuristicValue1;
    else return -ganancia;
  }
  public double getHeuristicValue_ex5() {
    double constant = ((double)MAXBENEFIT)*((double)MAXBENEFIT);
    if (no_satisfets > 0) return ((double)no_satisfets)*((double)no_satisfets)*constant;
    if (heuristicValue_ex5 > 0.1) return heuristicValue_ex5;
    else return -ganancia;
  }

  public double getGanancia(){
    return ganancia;
  }

  private void calculaSuministro() {
    for (int i = 0; i < suministro.length; ++i) {
      suministro[i] = 0.0;
      clientesEnCentral[i] = 0;
    }

    for (int id = 0; id < state.length; ++id) {
      if (state[id] != -1)
        try {
          suministro[state[id]] += clientes.get(id).getConsumo() / (1 - perdida(id, state[id]));
          ++clientesEnCentral[state[id]];
        } catch (Exception e) {
          e.printStackTrace();
        }
    }
  }

  private void calculaGanancia() {
    //Centrales
    for (int jd = 0; jd < centrales.size(); ++jd) {
      if (suministro[jd] > 0) ganancia -= costeMarcha(jd);
      else ganancia -= costeParada(jd);
    }

    //Clientes
    for (int id = 0; id < state.length; ++id) {
      try{
        if (state[id] != -1) ganancia += precioTarifa(id);
        else ganancia -= VEnergia.getTarifaClientePenalizacion(clientes.get(id).getContrato())*clientes.get(id).getConsumo();
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private void heuristicFunction() {
    heuristicValue1 = -ganancia;
    heuristicValue2 = -ganancia;
    heuristicValue_ex5 = 0;
    overflowPersonas = 0;
    for (int jd = 0; jd < centrales.size(); ++jd) {
      double overflow = suministro[jd] - centrales.get(jd).getProduccion();
      if (overflow > 0) {
        overflow = Math.max(overflow, 1.0);
        heuristicValue1 += overflow * overflow * MAXBENEFIT * centrales.size();
        heuristicValue_ex5 += overflow * overflow;
        overflowPersonas += clientesEnCentral[jd];
      }
    }
    heuristicValue2 += overflowPersonas*overflowPersonas*HEURISTIC2_CONST;
  }

  public void ReportUse() {
    int[] tipoCentral = new int[3];
    for (int id = 0; id < state.length; ++id) {
      int centId = state[id];
      if (centId != -1) {
        Central central = centrales.get(centId);
        tipoCentral[central.getTipo()]++;
      }
    }
    for(int i = 0; i < 3; ++i) System.out.print(tipoCentral[i] + " ");
    System.out.println();
  }
}
