package com.jed.util;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public class Vector {

    /**
     * 
     */
    public float x;
    
    /**
     * 
     */
    public float y;

    /**
     * 
     */
    public Vector() {
    }

    /**
     * 
     * @param x x
     * @param y y
     */
    public Vector(float x, float y) {
        super();
        this.x = x;
        this.y = y;
    }

    /**
     * 
     * @param o other vector
     * @return distance between the this and o.
     */
    public double distance(final Vector o) {
        double axBx = x - o.x;
        axBx *= axBx;
        double ayBy = (y - o.y);
        ayBy *= ayBy;

        return Math.sqrt(axBx + ayBy);
    }

    /**
     * 
     * @param o other vector.
     * @return resultant vector of adding this and o. 
     */
    public Vector add(Vector o) {
        return new Vector(this.x + o.x, this.y + o.y);
    }

    /**
     * 
     * @param o other vector.
     * @return resultant vector of subtracting o from this.
     */
    public Vector subtract(Vector o) {
        return new Vector(o.x - this.x, o.y - this.y);
    }

    /**
     * 
     * @return magnitude.
     */
    public double magnitude() {
        return Math.sqrt((this.x * this.x) + (this.y * this.y));
    }

    /**
     * @see java.lang.Math#atan2(double y, double x)
     * 
     * @return theta.
     */
    public double angle() {
        return Math.atan2(this.y, this.x);
    }

    /**
     * Geometrically, dot product is the product of the Euclidean magnitudes of
     * two vectors and the cosine of the angle between them.
     * 
     * @see <a href="http://en.wikipedia.org/wiki/Dot_product">Dot Product Wiki</a>
     * 
     * @param v1 second operand operand, where this vector is the first.
     * @return dot product.
     */
    public double dotProduct(Vector v1) {
        return x * v1.x + y * v1.y;
    }

    /**
     * 
     * @return normalized vector.
     */
    public Vector normalize() {
        Vector v2 = new Vector();

        double length = magnitude();
        if (length != 0) {
            v2.x = x / (float) length;
            v2.y = y / (float) length;
        }

        return v2;
    }

    /**
     * 
     * @param scaleFactor factor to scale vector by.
     * @return new scaled vector.
     */
    public Vector scale(float scaleFactor) {
        return new Vector(x * scaleFactor, y * scaleFactor);
    }

    /**
     * 
     * @return a copy of this vector.
     */
    public Vector copy() {
        return new Vector(x, y);
    }

}
