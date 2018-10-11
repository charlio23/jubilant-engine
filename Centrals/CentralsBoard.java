package Centrals;

import IA.Energia.Centrales;
import IA.Energia.Clientes;
import IA.Energia.Central;
import IA.Energia.Cliente;

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

    public CentralsBoard(Centrales centrales, Clientes clientes, boolean primera) {
        this.centrales = centrales;
        this.clientes = clientes;
        this.state = new int[clientes.size()];

        if (primera) {
			// Se asignan centrales a clientes garantizados de forma random
            Random r = new Random();
            for (int id = 0; id < state.length; ++id) {
				if (clientes.get(id).getContrato() == GARANTIZADO) {
					state[id] = r.nextInt(centrales.size());
				} else {
					state[id] = -1;
				}
			}
        } else {
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
    }

    public double heuristicFunction() {
        //Ganancia
        double ganancia = 0;
        
        //Centrales
        boolean[] centralEnMarcha = new boolean[centrales.size()];
        for (int id = 0; id < state.length; ++id) {
			if (state[id] != -1)
				centralEnMarcha[state[id]] = true;
		}
		for (int jd = 0; jd < centrales.size(); ++jd) {
			if (centralEnMarcha[jd]) ganancia -= costeMarcha(jd);
			else ganancia -= costeParada(jd);
		}
			
		//Clientes
		for (int id = 0; id < state.length; ++id) {
			if (state[id] != -1) ganancia += precioTarifa(id);
			else ganancia -= 5*clientes.get(id).getConsumo();
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
    // 		centId es el identificador de una central 
    private double distance(int clId, int centId) {
		Cliente cliente = clientes.get(clId);
		Central central = centrales.get(centId);
		double dx = cliente.getCoordX()-central.getCoordX();
		double dy = cliente.getCoordY()-central.getCoordY();
		return Math.sqrt(dx*dx + dy*dy);
	}

    // Pre: clId es el identificador de un cliente
    // 		centId es el identificador de una central 
	private double perdida(int clId, int centId) {
		double dis = distance(clId, centId);
		if (dis <= 10) return 0.0;
		if (dis <= 25) return 0.1;
		if (dis <= 50) return 0.2;
		if (dis <= 75) return 0.4;
		return 0.6;
	}
	
    // Pre: centId es el identificador de una central 	
	private double costeMarcha(int centId) {
		Central central = centrales.get(centId);
		switch (central.getTipo()) {
			case CENTRALA: return 2000.0 + 5.0*central.getProduccion();
			case CENTRALB: return 1000.0 + 8.0*central.getProduccion();
			case CENTRALC: return 500.0 + 15.0*central.getProduccion();
			default: break;
		}
		return 0;
	}

    // Pre: centId es el identificador de una central 	
	private double costeParada(int centId) {
		Central central = centrales.get(centId);
		switch (central.getTipo()) {
			case CENTRALA: return 1500.0;
			case CENTRALB: return 500.0;
			case CENTRALC: return 150.0;
			default: break;
		}
		return 0;
	}

    // Pre: clId es el identificador de un cliente 	
	private double precioTarifa(int clId) {
		Cliente cliente = clientes.get(clId);
		if (cliente.getTipo() == GARANTIZADO) {
			switch (cliente.getContrato()) {
				case CLIENTEXG: return 40.0*cliente.getConsumo();
				case CLIENTEMG: return 50.0*cliente.getConsumo();
				case CLIENTEG: return 60.0*cliente.getConsumo();
				default: break;
			}
		} else {
			switch (cliente.getContrato()) {
				case CLIENTEXG: return 30.0*cliente.getConsumo();
				case CLIENTEMG: return 40.0*cliente.getConsumo();
				case CLIENTEG: return 50.0*cliente.getConsumo();
				default: break;
			}
		}
		return 0;
	}
}
