package gsd;

import java.io.IOException;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author jimmy
 */
public class SolverTest {

    @Test
    public void testSat() throws IOException {
        Cnf cnf = DimacsParser.parse(
                "c this is a comment\n"
                + "c this is also a comment\n"
                + "p cnf 5 4\n"
                + "1 2 3 0\n"
                + "-2 3 0\n"
                + "-1 2 0\n"
                + "1 -3 0\n");
        Solver s = new Solver(cnf);
        assertTrue(s.solve());
        assertNull(Verifier.verify(cnf, s.model()));
    }

    @Test
    public void testUnsat() throws IOException {
        Cnf cnf = DimacsParser.parse(
                "c this is a comment\n"
                + "c this is also a comment\n"
                + "p cnf 5 5\n"
                + "1 2 3 0\n"
                + "-2 3 0\n"
                + "-1 2 0\n"
                + "1 -3 0\n"
                + "-1 -3 0\n");
        Solver s = new Solver(cnf);
        assertFalse(s.solve());
    }
}
