package gsd;

import static gsd.Var.isPos;
import static gsd.Var.var;

/**
 *
 * @author jimmy
 */
public class Verifier {

    private static boolean isSatisfied(int[] clause, boolean[] model) {
        for (int lit : clause) {
            if (model[var(lit)] == isPos(lit)) {
                return true;
            }
        }
        return false;
    }

    public static int[] verify(Cnf cnf, boolean[] model) {
        for (int[] clause : cnf.clauses()) {
            if (!isSatisfied(clause, model)) {
                return clause;
            }
        }
        return null;
    }
}
