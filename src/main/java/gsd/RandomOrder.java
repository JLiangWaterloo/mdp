package gsd;

import java.util.Random;

/**
 *
 * @author jimmy
 */
public class RandomOrder implements BranchingHeuristic {

    private final Solver solver;
    private final Random rand = new Random();

    public RandomOrder(Solver solver) {
        this.solver = solver;
    }

    @Override
    public int pickBranchVar() {
        int var;
        do {
            var = rand.nextInt(solver.nVars);
        } while (solver.values[var] != Solver.UNDEFINED);
        return var;
    }
}
