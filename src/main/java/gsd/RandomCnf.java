package gsd;

import java.util.Random;

/**
 *
 * @author jimmy
 */
public class RandomCnf {

    private static final Random rand = new Random();

    public static Cnf random3Cnf(int nVars, int nClauses) {
        int[][] clauses = new int[nClauses][3];
        for (int i = 0; i < nClauses; i++) {
            int[] clause = clauses[i];
            for (int j = 0; j < clause.length; j++) {
                clause[j] = rand.nextBoolean()
                        ? Var.posLit(rand.nextInt(nVars))
                        : Var.negLit(rand.nextInt(nVars));
            }
        }
        return new Cnf(nVars, clauses);
    }
}
