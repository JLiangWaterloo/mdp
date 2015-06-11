package gsd;

import java.util.Arrays;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author jimmy
 */
public class SolverFuzzTest {

    @Test
    public void testRandom3Cnf() {
        int n = 5;

        int[] count = new int[n];
        int[] decisions = new int[n];

        int size = 10000;

        for (int i = 0; i < size; i++) {
            if (i % 10 == 0) {
                System.out.println("... " + i);
            }
            Cnf cnf = RandomCnf.random3Cnf(30, 128);
            Solver[] solvers = new Solver[n];
            for (int j = 0; j < solvers.length; j++) {
                solvers[j] = new Solver(cnf);
            }
            solvers[0].setBranchingHeuristic(new ExponentialRecencyWeightedAverage(solvers[0], 0, 0.05, false));
            solvers[1].setBranchingHeuristic(new ExponentialRecencyWeightedAverage(solvers[1], 0, 0.05, true));
            solvers[2].setBranchingHeuristic(new RandomVariable(solvers[2]));
            solvers[3].setBranchingHeuristic(new StaticOrder(solvers[3]));
            solvers[4].setBranchingHeuristic(new VSIDS(solvers[4]));
            for (int j = 0; j < solvers.length; j++) {
                if (solvers[j].solve()) {
                    assertNull(Verifier.verify(cnf, solvers[j].model()));
                    count[j]++;
                }
                decisions[j] += solvers[j].decisions;
            }
        }
        System.out.println(Arrays.toString(count));
        System.out.println(Arrays.toString(decisions));
    }
}
