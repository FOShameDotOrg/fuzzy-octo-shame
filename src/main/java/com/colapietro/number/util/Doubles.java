package com.colapietro.number.util;

import static com.colapietro.number.util.FloatingPointArithmeticConstants.MACHINE_EPSILON;

/**
 * @author Peter Colapietro
 *
 */
public final class Doubles {

    /**
     * Private constructor to prevent instantiation.
     */
    private Doubles() {}

    /**
     * If floating point numbers are close enough in value.
     *
     * @param a double precision floating point number
     * @param b double precision floating point number
     * @return true if a is close enough to b
     * @see <a href="http://stackoverflow.com/a/6837237">comparing float/double values using == operator</a>
     */
    public static boolean compareDoubles(final double a, final double b) {
        return Math.abs(a / b - 1) < MACHINE_EPSILON;
    }
}
