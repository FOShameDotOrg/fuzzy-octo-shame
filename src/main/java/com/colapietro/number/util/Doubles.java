/**
 * 
 */
package com.colapietro.number.util;

import java.math.BigDecimal;

/**
 * @author Peter Colapietro
 *
 */
public class Doubles {
	private static final int epsilon = new BigDecimal("5.96e-08").intValue();

	/**
	 * If floating point numbers are close enough in value
	 * 
	 * @see <a href="http://stackoverflow.com/a/6837237">comparing float/double values using == operator</a>
	 * 
	 * @param a floating point number
	 * @param b floating point number
	 * @return
	 */
	public static boolean compareDoubles(double a, double b) {
		 return(Math.abs(a/b - 1) < epsilon);
	}
}
