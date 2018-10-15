package Centrals;

import IA.Energia.Centrales;
import IA.Energia.Clientes;
import IA.Energia.Central;
import IA.Energia.Cliente;
import IA.Energia.VEnergia;

import java.util.Random;

public class CentralsBoard {

    private int[] state;
    private static Centrales centrales;
    private static Clientes clientes;

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
                    cand[id] = cumChance;
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
    
    public CentralsBoard(int[] cent, int seed1, int ncl, double[] propc, double propg,int seed2, int estrategia) {
	try {
	    Centrales centrales = new Centrales(cent, seed1);
	    Clientes clientes = new Clientes(ncl, propc, propg, seed2);
	    CentralsBoard board = new CentralsBoard(centrales, clientes, estrategia);  
            this.state = board.getState();      
	    this.centrales = centrales;
	    this.clientes = clientes;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private CentralsBoard() {
    }
    
    //TODO: Change heuristic funtion to reflect penalizations
    public double heuristicFunction() {
        //Ganancia
        double ganancia = 0;

        //Centrales
        double[] suministro = new double[centrales.size()];
        for (int id = 0; id < state.length; ++id) {
            if (state[id] != -1)
                suministro[state[id]] = clientes.get(id).getConsumo()/(1-perdida(id, state[id]));
        }
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
        
        for(int jd = 0; jd < centrales.size(); ++jd) {
            double overflow = Math.max(0.0, suministro[jd] - centrales.get(jd).getProduccion());
            ganancia -= overflow * overflow * MAXBENEFIT;
        }

        return -ganancia;
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
        for (int id = 0; id < centrales.size(); ++id)
            maxProduccion[id] = centrales.get(id).getProduccion();
        for (int id = 0; id < state.length; ++id) {
            int centId = state[id];
            demanda[centId] += clientes.get(id).getConsumo()/(1-perdida(id,centId));
            if (demanda[centId] > maxProduccion[centId]) return false;
        }

        return true;
    }

    //Pre: centId puede ser -1 sii Cliente clId no es garantizado
    public void changeCentral(int clId, int centId) {
        if (clientes.get(clId).getContrato() == NOGARANTIZADO || centId != -1) state[clId] = centId;
    }

    ////// Auxiliares

    // Pre: clId es el identificador de un cliente
    //    centId es el identificador de una central
    private double distance(int clId, int centId) {
        Cliente cliente = clientes.get(clId);
        Central central = centrales.get(centId);
        double dx = cliente.getCoordX()-central.getCoordX();
        double dy = cliente.getCoordY()-central.getCoordY();
        return Math.sqrt(dx*dx + dy*dy);
    }

    // Pre: clId es el identificador de un cliente
    //    centId es el identificador de una central
    private double perdida(int clId, int centId) {
        return VEnergia.getPerdida(distance(clId, centId));
    }

    // Pre: centId es el identificador de una central
    private double costeMarcha(int centId) {
        Central central = centrales.get(centId);
        try {
            return VEnergia.getCosteMarcha(central.getTipo()) + VEnergia.getCosteProduccionMW(central.getTipo())*central.getProduccion();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    // Pre: centId es el identificador de una central
    private double costeParada(int centId) {
        Central central = centrales.get(centId);
        try{
            return VEnergia.getCosteParada(central.getTipo());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    // Pre: clId es el identificador de un cliente
    private double precioTarifa(int clId) {
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

    public int[] getState() {
        return state;
    }
    
    public boolean isGuaranteed(int id) {
        return clientes.get(id).getTipo() == GARANTIZADO;
    }
    
    public CentralsBoard getCopy() {
        CentralsBoard curBoard = new CentralsBoard();
        curBoard.state = this.state.clone();
        return curBoard;
    }
}
