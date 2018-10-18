package Centrals;

import aima.search.framework.HeuristicFunction;
import IA.Energia.*;
public class CentralsHeuristicFunction4 extends AbsHeuristicFunction {

    public double heuristicFunction(CentralsBoard board) {
        //Ganancia
        double ganancia = 0;
        Centrales centrales = board.centrales;
        Clientes clientes = board.clientes;
        int[] state = board.getState();
        double[] suministro = new double[centrales.size()];
        for(int i = 0; i < suministro.length; ++i) {
            suministro[i] = 0.0;
        }
        for (int id = 0; id < state.length; ++id) {
            if (state[id] != -1)
                try {
                    suministro[state[id]] += clientes.get(id).getConsumo() / (1 - perdida(id, state[id]));

                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        double pen = 0;
        for(int jd = 0; jd < centrales.size(); ++jd) {
            double overflow = suministro[jd] - centrales.get(jd).getProduccion();
            if(overflow > 0) {
                overflow = Math.max(overflow, 1.0);
                pen += overflow * overflow * MAXBENEFIT;
            }
        }
        if(pen > 1) return pen;
        return -ganancia;
    }
}
