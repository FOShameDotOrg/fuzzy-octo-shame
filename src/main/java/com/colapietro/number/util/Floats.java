package com.colapietro.number.util;

import static com.colapietro.number.util.FloatingPointArithmeticConstants.MACHINE_EPSILON;

/**
 * @author Peter Colapietro
 *
 */
public final class Floats {

    /**
     *
     */
    private Floats() {}

    /**
     * If floating point numbers are close enough in value.
     *
     * @param a floating point number
     * @param b floating point number
     * @return if a is close enough to b
     * @see <a href="http://stackoverflow.com/a/6837237">comparing float/double values using == operator</a>
     */
    public static boolean compareFloats(final float a, final float b) {
        return Math.abs(a / b - 1) < MACHINE_EPSILON;
    }
}
