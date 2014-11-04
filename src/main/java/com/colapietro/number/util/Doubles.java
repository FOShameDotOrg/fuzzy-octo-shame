/**
 * 
 */
package com.colapietro.number.util;

/**
 * @author Peter Colapietro
 *
 */
public class Doubles implements MachineEpsilonable  {

    /**
     * If floating point numbers are close enough in value.
     * 
     * @see <a href="http://stackoverflow.com/a/6837237">comparing float/double values using == operator</a>
     * 
     * @param a floating point number
     * @param b floating point number
     * @return true if a is close enough to b
     */
    public static boolean compareDoubles(double a, double b) {
         return(Math.abs(a/b - 1) < EPSILON);
    }
}
