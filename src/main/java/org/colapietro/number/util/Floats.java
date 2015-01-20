package org.colapietro.number.util;

import static org.colapietro.number.util.FloatingPointArithmeticConstants.FLOAT_MACHINE_EPSILON;
import static java.lang.Float.MIN_NORMAL;

/**
 * @author Peter Colapietro
 *
 */
final class Floats {

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
     * @see <a href="http://stackoverflow.com/a/4915891">How should I do floating point comparison?</a>
     * @see <a href="http://stackoverflow.com/a/6837237">comparing float/double values using == operator</a>
     * @see <a href="http://www.ibm.com/developerworks/java/library/j-jtp0114/#N10255">Java theory and practice: Where's your point?</a>
     */
    public static boolean compareFloats(final float a, final float b) {
        final float absA = Math.abs(a);
        final float absB = Math.abs(b);
        final float diff = Math.abs(a - b);

        if (a == b) { // shortcut, handles infinities
            return true;
        } else if (a == 0 || b == 0 || diff < MIN_NORMAL) {
            // a or b is zero or both are extremely close to it
            // relative error is less meaningful here
            return diff < (FLOAT_MACHINE_EPSILON * MIN_NORMAL);
        } else { // use relative error
            return diff / (absA + absB) < FLOAT_MACHINE_EPSILON;
        }
    }
}
