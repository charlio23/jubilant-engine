package Centrals;

public class CentralsBoard {

    private double [] state;

    public CentralsBoard(double[] init) {
        this.state = new double[init.length];
        for (int i = 0; i < init.length; ++i) {
            state[i] = init[i];
        }
    }

    public double heuristicFunction() {
        return 0;
    }

}
