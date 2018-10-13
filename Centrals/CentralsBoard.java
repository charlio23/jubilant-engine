package Centrals;

import IA.Energia.Centrales;
import IA.Energia.Clientes;
import IA.Energia.Central;
import IA.Energia.Cliente;
import IA.Energia.VEnergia;

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
			else ganancia -= getTarifaClientePenalizacion(clientes.get(id).getContrato())*clientes.get(id).getConsumo();
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
		return getPerdida(distance(clId, centId));
	}
	
    // Pre: centId es el identificador de una central 	
	private double costeMarcha(int centId) {
		Central central = centrales.get(centId);
		return getCosteMarcha(central.getTipo()) + getCosteProduccionMW(central.getTipo())*central.getProduccion();
	}

    // Pre: centId es el identificador de una central 	
	private double costeParada(int centId) {
		Central central = centrales.get(centId);
		return getCosteParada(central.getTipo());
	}

    // Pre: clId es el identificador de un cliente 	
	private double precioTarifa(int clId) {
		Cliente cliente = clientes.get(clId);
		if (cliente.getTipo() == GARANTIZADO) {
			return getTarifaClienteGarantizado(cliente.getContrato)*cliente.getConsumo();
		} else {
			return getTarifaClienteNoGarantizado(cliente.getContrato)*cliente.getConsumo();
		}
	}
}
