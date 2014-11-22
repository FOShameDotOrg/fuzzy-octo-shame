package org.colapietro.number.util;

import java.math.BigDecimal;

/**
 * Created by Peter Colapietro on 11/2/14.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Machine_epsilon#Approximation_using_Java">Approximation using Java</a>
 *
 */
public final class FloatingPointArithmeticConstants {

    /**
     *
     */
    private FloatingPointArithmeticConstants() {}

    /**
     * Machine epsilon gives an upper bound on the relative error due to rounding in floating point arithmetic.
     *
     * @see <a href="http://en.wikipedia.org/wiki/Machine_epsilon#Values_for_standard_hardware_floating_point_arithmetics"></a>
     */
    public static final int MACHINE_EPSILON = new BigDecimal("5.96e-08").intValue();

}
