package org.colapietro.number.util;

import static org.colapietro.number.util.FloatingPointArithmeticConstants.DOUBLE_MACHINE_EPSILON;
import static java.lang.Double.MIN_NORMAL;

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
     * @see <a href="http://stackoverflow.com/a/4915891">How should I do floating point comparison?</a>
     * @see <a href="http://stackoverflow.com/a/6837237">comparing float/double values using == operator</a>
     * @see <a href="http://www.ibm.com/developerworks/java/library/j-jtp0114/#N10255">Java theory and practice: Where's your point?</a>
     */
    public static boolean compareDoubles(final double a, final double b) {
        final double absA = Math.abs(a);
        final double absB = Math.abs(b);
        final double diff = Math.abs(a - b);

        if (a == b) { // shortcut, handles infinities
            return true;
        } else if (a == 0 || b == 0 || diff < MIN_NORMAL) {
            // a or b is zero or both are extremely close to it
            // relative error is less meaningful here
            return diff < (DOUBLE_MACHINE_EPSILON * MIN_NORMAL);
        } else { // use relative error
            return diff / (absA + absB) < DOUBLE_MACHINE_EPSILON;
        }
    }
}
