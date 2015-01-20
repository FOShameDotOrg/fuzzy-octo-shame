package org.colapietro.number.util;

import java.math.BigDecimal;

/**
 * Created by Peter Colapietro on 11/2/14.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Machine_epsilon#Approximation_using_Java">Approximation using Java</a>
 *
 */
final class FloatingPointArithmeticConstants {

    /**
     *
     */
    private FloatingPointArithmeticConstants() {}

    /**
     * Machine epsilon gives an upper bound on the relative error due to rounding in floating point arithmetic.
     *
     * @see <a href="http://en.wikipedia.org/wiki/Machine_epsilon#Values_for_standard_hardware_floating_point_arithmetics"></a>
     */
    public static final float FLOAT_MACHINE_EPSILON = new BigDecimal("5.96e-08").floatValue();

    /**
     * Machine epsilon gives an upper bound on the relative error due to rounding in floating point arithmetic.
     *
     * @see <a href="http://en.wikipedia.org/wiki/Machine_epsilon#Values_for_standard_hardware_floating_point_arithmetics"></a>
     */
    public static final double DOUBLE_MACHINE_EPSILON = new BigDecimal("1.11e-16").doubleValue();

}
