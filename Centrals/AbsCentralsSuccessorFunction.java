package Centrals;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;
import java.util.List;

public abstract class AbsCentralsSuccessorFunction implements SuccessorFunction {

    public abstract List getSuccessors(Object o);
}
