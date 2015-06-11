package gsd;

import static gsd.Var.isNeg;
import static gsd.Var.var;
import java.util.Arrays;

/**
 *
 * @author jimmy
 */
public class VSIDS implements BranchingHeuristic, DecisionMonitor {

    private final Solver solver;
    private final double[] activity;
    private final double decay;

    public VSIDS(Solver solver, double decay) {
        this.solver = solver;
        this.activity = new double[solver.nVars];
        this.decay = decay;

        solver.addDecisionMonitor(this);
    }

    public VSIDS(Solver solver) {
        this(solver, 0.95);
    }

    @Override
    public int pickBranchVar() {
        int var = -1;
        double max = -1;

        for (int i = 0; i < activity.length; i++) {
            if (activity[i] > max && solver.values[i] == Solver.UNDEFINED) {
                var = i;
                max = activity[i];
            }
        }
        return var;
    }

    @Override
    public void onDecision(int decisionLit, int[] conflictingClause) {
        if (conflictingClause != null) {
            for (int i = 0; i < conflictingClause.length; i++) {
                activity[var(conflictingClause[i])]++;
            }
            for (int i = 0; i < activity.length; i++) {
                activity[i] *= decay;
            }
        }
    }
}
