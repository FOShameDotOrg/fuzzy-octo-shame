package com.colapietro.number.util;

import java.math.BigDecimal;

/**
 * Created by Peter Colapietro on 11/2/14. FIXME See: http://stackoverflow.com/a/320642
 */
public interface MachineEpsilonable {

    /**
     * Machine epsilon gives an upper bound on the relative error due to rounding in floating point arithmetic.
     * @see <a href="http://en.wikipedia.org/wiki/Machine_epsilon#Values_for_standard_hardware_floating_point_arithmetics"></a>
     */
    int EPSILON = new BigDecimal("5.96e-08").intValue();
}
