package gsd;

import static gsd.Var.isNeg;
import static gsd.Var.var;
import java.util.Arrays;

/**
 *
 * @author jimmy
 */
public class ExponentialRecencyWeightedAverage implements BranchingHeuristic, DecisionMonitor {

    private final Solver solver;
    private final double[] Q;
    private final double alpha;
    boolean d;

    public ExponentialRecencyWeightedAverage(Solver solver, double optimisticInitialValue, double alpha, boolean d) {
        this.solver = solver;
        this.Q = new double[solver.nVars];
        Arrays.fill(Q, optimisticInitialValue);
        this.alpha = alpha;
        this.d = d;

        solver.addDecisionMonitor(this);
    }

    public ExponentialRecencyWeightedAverage(Solver solver) {
        this(solver, 1, 0.05, true);
    }

    @Override
    public int pickBranchVar() {
        int var = -1;
        double max = -1;

        for (int i = 0; i < Q.length; i++) {
            if (Q[i] > max && solver.values[i] == Solver.UNDEFINED) {
                var = i;
                max = Q[i];
            }
        }
        return var;
    }

    @Override
    public void onDecision(int decisionLit, int[] conflictingClause) {
        if (this.d || isNeg(decisionLit)) {
            double reward = conflictingClause == null ? 0 : 1;
            int decisionVar = var(decisionLit);
            double oldQ = Q[decisionVar];
            Q[decisionVar] = oldQ + alpha * (reward - oldQ);
        }
    }
}
