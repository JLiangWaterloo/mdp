package gsd;

import static gsd.Var.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author jimmy
 */
public class VarTest {

    @Test
    public void testPosLit() {
        assertTrue(isPos(posLit(0)));
        assertFalse(isNeg(posLit(0)));
        assertEquals(0, var(posLit(0)));
        assertEquals(1, var(posLit(1)));
    }

    @Test
    public void testNegLit() {
        assertFalse(isPos(negLit(0)));
        assertTrue(isNeg(negLit(0)));
        assertEquals(0, var(negLit(0)));
        assertEquals(1, var(negLit(1)));
    }

    @Test
    public void testNeg() {
        assertEquals(posLit(0), neg(negLit(0)));
        assertEquals(negLit(0), neg(posLit(0)));
    }

    @Test
    public void testFromDimacs() {
        assertEquals(0, var(fromDimacs(1)));
        assertTrue(isPos(fromDimacs(1)));
        assertEquals(0, var(fromDimacs(-1)));
        assertFalse(isPos(fromDimacs(-1)));
    }

    @Test
    public void testToDimacs() {
        assertEquals(1, toDimacs(posLit(0)));
        assertEquals(-1, toDimacs(negLit(0)));
    }
}
