package gsd;

import static gsd.Var.fromDimacs;
import java.io.IOException;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author jimmy
 */
public class DimacsParserTest {

    @Test
    public void testParse() throws IOException {
        Cnf cnf = DimacsParser.parse(
                "c this is a comment\n"
                + "c this is also a comment\n"
                + "p cnf 5 4\n"
                + "1 2 3 0\n"
                + "-2 3 0\n"
                + "-1 2 0\n"
                + "1 -3 0\n");
        int[][] clauses = cnf.clauses();

        assertEquals(5, cnf.nVars());
        assertEquals(4, clauses.length);
        assertArrayEquals(new int[]{fromDimacs(1), fromDimacs(2), fromDimacs(3)}, clauses[0]);
        assertArrayEquals(new int[]{fromDimacs(-2), fromDimacs(3)}, clauses[1]);
        assertArrayEquals(new int[]{fromDimacs(-1), fromDimacs(2)}, clauses[2]);
        assertArrayEquals(new int[]{fromDimacs(1), fromDimacs(-3)}, clauses[3]);
    }
}
