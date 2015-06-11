package gsd;

import static gsd.Var.isPos;
import static gsd.Var.neg;
import static gsd.Var.negLit;
import static gsd.Var.posLit;
import static gsd.Var.var;
import java.util.Arrays;

/**
 *
 * @author jimmy
 */
public class Solver {

    protected static final byte UNDEFINED = 0;
    protected static final byte TRUE = 1;
    protected static final byte FALSE = 2;

    protected final int nVars;
    protected final int[][] clauses;

    protected final int[] trail;
    protected int trailSize = 0;
    protected final byte[] values;

    private final int[][] watches;
    private final int[] watchesSize;

    private BranchingHeuristic branchingHeuristic;

    private DecisionMonitor[] decisionMonitors = new DecisionMonitor[0];

    public int decisions = 0;
    public int conflicts = 0;

    public Solver(Cnf cnf) {
        this.nVars = cnf.nVars();
        this.clauses = cnf.clauses();

        this.trail = new int[nVars];
        this.values = new byte[nVars];
        this.watches = new int[nVars * 2][2];
        this.watchesSize = new int[nVars * 2];

        for (int i = 0; i < clauses.length; i++) {
            // Watch the first literal of the clause.
            addWatch(i, neg(clauses[i][0]));
        }

        this.branchingHeuristic = new StaticOrder(this);
    }

    public void setBranchingHeuristic(BranchingHeuristic branchingHeuristic) {
        this.branchingHeuristic = branchingHeuristic;
    }

    public void addDecisionMonitor(DecisionMonitor decisionMonitor) {
        decisionMonitors = Arrays.copyOf(decisionMonitors, decisionMonitors.length + 1);
        decisionMonitors[decisionMonitors.length - 1] = decisionMonitor;
    }

    private void addWatch(int clauseRef, int lit) {
        // Expand the watches data structure capacity if needed.
        if (watches[lit].length == watchesSize[lit]) {
            watches[lit] = Arrays.copyOf(watches[lit], watches[lit].length * 2);
        }
        watches[lit][watchesSize[lit]++] = clauseRef;
    }

    private int[] updateWatches(int lit) {
        assert values[var(lit)] == (isPos(lit) ? TRUE : FALSE);

        int[] watch = watches[lit];
        int watchSize = watchesSize[lit];
        for (int i = 0; i < watchSize; i++) {
            int clauseRef = watch[i];
            int[] clause = clauses[clauseRef];
            int validWatch = validWatch(clause);
            if (validWatch == -1) {
                return clause;
            }
            int validWatchLit = neg(clause[validWatch]);
            assert validWatchLit != lit;
            removeWatch(i, lit);
            addWatch(clauseRef, validWatchLit);
        }

        return null;
    }

    private void removeWatch(int num, int lit) {
        int[] watch = watches[lit];
        watch[num] = watch[--watchesSize[lit]];
    }

    private int validWatch(int[] clause) {
        for (int i = 0; i < clause.length; i++) {
            int lit = clause[i];
            int value = values[var(lit)];
            if (value == UNDEFINED
                    || ((value == TRUE) == isPos(lit))) {
                return i;
            }
        }
        return -1;
    }

    public boolean solve() {
        int conflictVar = -1;
        while (trailSize < nVars) {
            int decisionLit;
            if (conflictVar >= 0) {
                trail[trailSize++] = conflictVar;
                values[conflictVar] = TRUE;
                decisionLit = posLit(conflictVar);
                conflicts++;
                conflictVar = -1;
            } else {
                int decisionVar = branchingHeuristic.pickBranchVar();
                trail[trailSize++] = decisionVar;
                values[decisionVar] = FALSE;
                decisionLit = negLit(decisionVar);
                decisions++;
            }
            int[] conflictingClause = updateWatches(decisionLit);
            if (conflictingClause != null) {
                for (DecisionMonitor decisionMonitor : decisionMonitors) {
                    decisionMonitor.onDecision(decisionLit, conflictingClause);
                }
                // Backtrack.
                while (--trailSize >= 0 && values[trail[trailSize]] == TRUE) {
                    values[trail[trailSize]] = UNDEFINED;
                }
                if (trailSize == -1) {
                    return false;
                }
                conflictVar = trail[trailSize];
            } else {
                for (DecisionMonitor decisionMonitor : decisionMonitors) {
                    decisionMonitor.onDecision(decisionLit, null);
                }
            }
        }
        return true;
    }

    public boolean[] model() {
        boolean[] model = new boolean[nVars];
        for (int i = 0; i < model.length; i++) {
            model[i] = values[i] == TRUE;
        }
        return model;
    }
}
