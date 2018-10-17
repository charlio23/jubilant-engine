package Centrals;


import IA.Energia.Centrales;
import IA.Energia.Clientes;
import IA.Energia.Central;
import IA.Energia.Cliente;
import IA.Energia.VEnergia;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CentralsSuccessorFunction3 extends AbsCentralsSuccessorFunction
{

  public List getSuccessors (Object o)
  {
    CentralsBoard board = (CentralsBoard) o;
    List successors = new ArrayList ();
    int[] state = board.getState ();
    //board.printState();
    if (board.isCorrect ())
      {
	//System.out.println();
	//System.out.println(String.valueOf(board.getGanancia()));
      }				//else System.out.print("ROP + ");

    for (int id = 0; id < board.getClientesSize (); ++id)
      {
	int jd;
	if (board.isGuaranteed (id))
	  jd = 0;
	else
	  jd = -1;
	for (; jd < board.getCentralesSize (); ++jd)
	  {
	    CentralsBoard newBoard = board.getCopy ();
	    if (newBoard.changeCentral (id, jd))
	      {
		successors.add (new
				Successor (id + " + " + jd + " - " +
					   (board.getState ())[id],
					   newBoard));
	      }
	  }
      }



    // Reducir sobreproducción
    double[] suministro = new double[board.getCentralesSize ()];
    boolean[]centralesSobreProd = new boolean[board.getCentralesSize ()];
    for (int i = 0; i < suministro.length; ++i)
      {
	suministro[i] = 0.0;
      }
    for (int id = 0; id < board.getClientesSize (); ++id)
      {
	CentralsBoard newBoard = board.getCopy ();
	if (state[id] != -1 && !centralesSobreProd[state[id]])
	  {
	    try
	    {
	      double extra =
		board.getConsumo (id) / (1 - board.perdida (id, state[id]));
	      if (suministro[state[id]] + extra >
		  board.getProduccion (state[id]))
		{
		  newBoard.changeCentral (id, -1);
		}
	      else
		{
		  suministro[state[id]] += extra;
		}
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace ();
	    }
	  }
	successors.add (new Successor ("~C -", newBoard));
      }


    // Llenar algo
    for (int id = 0; id < board.getClientesSize (); ++id)
      {
	if (state[id] != -1)
	  {
	    suministro[state[id]] +=
	      board.getConsumo (id) / (1 - board.perdida (id, state[id]));
	  }
      }

    for (int jd = 0; jd < board.getCentralesSize (); ++jd)
      {
	if (suministro[jd] > 0)
	  {
	    CentralsBoard newBoard = board.getCopy ();
	    int id = 0;
	    int count = 0;
	    while (id < board.getClientesSize ())
	      {
		if (state[id] == -1)
		  {
		    double extra =
		      board.getConsumo (id) / (1 - board.perdida (id, jd));
		    if (extra + suministro[jd] > board.getProduccion (jd))
		      {
			newBoard.changeCentral (id, jd);
			suministro[jd] += extra;
			successors.add (new
					Successor ("sub ~c =" + jd + " -> " +
						   count, newBoard));
			count++;
		      }
		  }
		id++;
	      }
	    id--;
	    newBoard.changeCentral (id, -1);
	    successors.add (new
			    Successor ("~c =" + jd + " -> " + count,
				       newBoard));
	  }
      }





    // Llenar
    boolean[]centralesEnMarcha = new boolean[board.getCentralesSize ()];
    boolean[]clientesSinEnergia = new boolean[board.getClientesSize ()];
    suministro = new double[board.getCentralesSize ()];
    for (int id = 0; id < board.getClientesSize (); ++id)
      {
	if (state[id] != -1)
	  {
	    suministro[state[id]] +=
	      board.getConsumo (id) / (1 - board.perdida (id, state[id]));
	    centralesEnMarcha[state[id]] = true;
	  }
	else
	  clientesSinEnergia[id] = true;

      }

    for (int jd = 0; jd < board.getCentralesSize (); ++jd)
      {
	if (!centralesEnMarcha[jd])
	  {
	    CentralsBoard newBoard = board.getCopy ();
	    double maxDemanda = board.getProduccion (jd);
	    int id = 0;
	    int count = 0;
	    while (maxDemanda > 0 && id < board.getClientesSize ())
	      {
		if (clientesSinEnergia[id])
		  {
		    newBoard.changeCentral (id, jd);
		    count++;
		    maxDemanda -=
		      board.getConsumo (id) / (1 - board.perdida (id, jd));
		    successors.add (new
				    Successor ("sub C =" + jd + " | " + count,
					       newBoard));
		  }
		id++;
	      }
	    id--;
	    newBoard.changeCentral (id, -1);
	    successors.add (new
			    Successor ("C =" + jd + " | " + count, newBoard));
	  }
      }


    return successors;
  }
}























		/*
		   // Vaciar
		   double[] suministro = new double[board.getCentralesSize()];
		   boolean[] centralesSobreProd = new boolean[board.getCentralesSize()];
		   for(int i = 0; i < suministro.length; ++i) {
		   suministro[i] = 0.0;
		   }
		   for (int id = 0; id < board.getClientesSize(); ++id) {
		   if (state[id] != -1 && !centralesSobreProd[state[id]]) {
		   try {
		   suministro[state[id]] += board.getConsumo(id) / (1 - board.perdida(id, state[id]));
		   if (suministro[state[id]] > board.getProduccion(state[id])) centralesSobreProd[state[id]] = true;
		   } catch (Exception e) {
		   e.printStackTrace();
		   }
		   }
		   }

		   CentralsBoard newBoardSP = board.getCopy();
		   for (int id = 0; id < board.getClientesSize(); ++id) {
		   if (state[id] != -1 && centralesSobreProd[state[id]] && !board.isGuaranteed(id)) {
		   newBoardSP.changeCentral(id, -1);
		   }
		   }
		   successors.add(new Successor("C -", newBoardSP));
		 */
