package gsd;

/**
 *
 * @author jimmy
 */
public interface DecisionMonitor {

    /**
     * A decision has been made, either by the branching heuristic (in which
     * case the literal is negative) or by conflict (in which case the literal
     * is positive).
     *
     * @param decisionLit the decision literal
     * @param conflictingClause the conflicting clause or {@code null} if no
     * conflict
     */
    public void onDecision(int decisionLit, int[] conflictingClause);
}
