package gsd;

/**
 * Methods for converting between variables and literals.
 *
 * Variables are indexed from 0. The least-significant bit of a literal is "0"
 * if it is positive, "1" otherwise. The remaining bits are the variable.
 *
 * @author jimmy
 */
public final class Var {

    private Var() {
    }

    public static int posLit(int var) {
        assert var >= 0;
        return var << 1;
    }

    public static int negLit(int var) {
        assert var >= 0;
        return (var << 1) ^ 1;
    }

    public static int neg(int lit) {
        return lit ^ 1;
    }

    /**
     * @param lit
     * @return {@code true} if the literal is positive, {@code false} otherwise.
     */
    public static boolean isPos(int lit) {
        return (lit & 1) == 0;
    }

    /**
     * @param lit
     * @return {@code true} if the literal is negative, {@code false} otherwise.
     */
    public static boolean isNeg(int lit) {
        return (lit & 1) == 1;
    }

    public static int var(int lit) {
        return lit >> 1;
    }
}
