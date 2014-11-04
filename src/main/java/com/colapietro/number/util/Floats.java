/**
 * 
 */
package com.colapietro.number.util;

import java.math.BigDecimal;

/**
 * @author Peter Colapietro
 *
 * TODO consider looking into <a href="http://en.wikipedia.org/wiki/Machine_epsilon#Approximation_using_Java">Approximation using Java</a>
 *
 */
public class Floats implements MachineEpsilonable {

    /**
     * If floating point numbers are close enough in value.
     * 
     * @see <a href="http://stackoverflow.com/a/6837237">comparing float/double values using == operator</a>
     * 
     * @param a floating point number
     * @param b floating point number
     * @return if a is close enough to b
     */
    public static boolean compareFloats(float a, float b) {
         return(Math.abs(a/b - 1) < EPSILON);
    }
}
