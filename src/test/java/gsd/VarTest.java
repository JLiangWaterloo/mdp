package gsd;

import static org.junit.Assert.*;
import org.junit.Test;

import static gsd.Var.*;

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
}
