package Centrals;

import IA.Energia.Centrales;
import IA.Energia.Clientes;
import IA.Energia.Central;
import IA.Energia.Cliente;
import IA.Energia.VEnergia;

import java.util.Random;

public class CentralsBoard {

    private int[] state;
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

    public static final double MAXBENEFIT = 50000;

    public CentralsBoard(Centrales centrales, Clientes clientes, int estrategia) {
        this.centrales = centrales;
        this.clientes = clientes;
        this.state = new int[clientes.size()];

        if (estrategia == ONE) {
            // Se asigna una central a todos los clientes
            Random r = new Random();
            int cnt = r.nextInt(centrales.size());
            for (int id = 0; id < state.length; ++id)
                state[id] = cnt;
        }
        if (estrategia == RANDOM1) {
            // Se asignan centrales a clientes garantizados de forma random
            Random r = new Random();
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
            Random r = new Random();
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
                double frand = Math.random()*cumChance;
                state[id] = -1;
                for(int jd = 0; state[id] == -1 && jd < centrales.size(); ++jd) {
                    if(cand[jd] > frand) state[id] = jd;
                }
            }
        }
    }

    public CentralsBoard(int numCentrales, int[] pCentrales, int numClientes, int[] pClientes, int estrategia) {
        state = new int[clientes.size()];
        //centrales = new Centrales(numCentrales);

    }

    private CentralsBoard() {
    }



    public boolean allGarantizados() {
        //Todos los clientes que han contratado servicio garantizado han de ser servidos
        for (int id = 0; id < state.length; ++id)
            if (clientes.get(id).getContrato() == GARANTIZADO && state[id] == -1)
                return false;
        return true;
    }

    public boolean isCorrect() {
        //Un cliente solo se asigna una central
        // Por construccion, se satisface

        //A los clientes se les sirve siempre completamente
        // Se puede suponer que si sucede y solo comprobar que no se sobrepasa la produccion

        //Todos los clientes que han contratado servicio garantizado han de ser servidos
        if (!allGarantizados()) return false;

        //No se puede asignar a una central mas demanda de la que puede producir
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

    //Pre: centId puede ser -1 sii Cliente clId no es garantizado
    public boolean changeCentral(int clId, int centId) {
        if (clientes.get(clId).getContrato() == NOGARANTIZADO || centId != -1){
            state[clId] = centId;
            return true;
        }
        return false;
    }

    public CentralsBoard getCopy() {
        CentralsBoard curBoard = new CentralsBoard();
        curBoard.state = this.state.clone();
        return curBoard;
    }

    public void printState() {
        System.out.println("--------");
        for (int stat: state) {
            System.out.print(' ' + String.valueOf(stat));
        }
    }

    public double[] getCentrales() {
    }
}
