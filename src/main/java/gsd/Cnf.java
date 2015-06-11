package gsd;

/**
 *
 * @author jimmy
 */
public class Cnf {

    private final int nVars;
    private final int[][] clauses;

    public Cnf(int nVars, int[][] clauses) {
        this.nVars = nVars;
        this.clauses = clauses;
    }

    public int nVars() {
        return nVars;
    }

    public int[][] clauses() {
        return clauses;
    }
}
