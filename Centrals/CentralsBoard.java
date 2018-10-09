package Centrals;

import IA.Energia.Centrales;
import IA.Energia.Clientes;

import java.util.Random;

public class CentralsBoard {

    private int [] state;
    private Centrales centrales;
    private Clientes clientes;

    public static final int CENTRALA = 0;
    public static final int CENTRALB = 1;
    public static final int CENTRALC = 2;
    public static final int CLIENTEXG = 0;
    public static final int CLIENTEMG = 1;
    public static final int CLIENTEG = 2;
    public static final int GARANTIZADO = 0;
    public static final int NOGARANTIZADO = 1;

    public CentralsBoard(double[] init) {
       // this.state = new double[init.length];
        for (int i = 0; i < init.length; ++i) {
      //      state[i] = init[i];
        }
    }

    public CentralsBoard(Centrales centrales, Clientes clientes, boolean primera) {
        this.centrales = centrales;
        this.clientes = clientes;
        this.state = new int[clientes.size()];
        if (primera) {
            Random r = new Random();
            for (int i = 0; i < state.length; ++i) {
             //   if (clientes.get(clId)) ////seguir
            }
            // random
        } else {
            // fuzzy(random) de mes a prop
        }
    }

    public double heuristicFunction() {
        //Ganancia
        return 0;
    }

    public boolean allGarantizados() {
        //Todos los clientes que han contratado servicio garantizado han de ser servidos
        return false;
    }

    public boolean isCorrect() {
        //No se puede asignar a una central mas demanda de la que puede producir
        //Un cliente solo se asigna una central
        //A los clientes se les sirve siempre completamente
        //Todos los clientes que han contratado servicio garantizado han de ser servidos
        return false;
    }

    //Pre: centId puede ser -1 sii Cliente clId no es garantizado
    public void changeCentral(int clId, int centId) {
        if (clientes.get(clId).getContrato() == NOGARANTIZADO || centId != -1) state[clId] = centId;
    }
}
