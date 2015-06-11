package gsd;

/**
 *
 * @author jimmy
 */
public class StaticOrder implements BranchingHeuristic {

    private final Solver solver;

    public StaticOrder(Solver solver) {
        this.solver = solver;
    }

    @Override
    public int pickBranchVar() {
        int var;
        for (var = 0; var < solver.nVars && solver.values[var] != Solver.UNDEFINED; var++) {
        }
        return var;
    }
}
