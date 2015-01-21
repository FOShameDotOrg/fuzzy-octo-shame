package com.jed.util;

import javax.annotation.Nonnull;

/**
 * 
 * @author jlinde, Peter Colapietro
 * @since 0.1.8
 *
 */
public final class Vector2f {

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
    private Vector2f() {
        super();
    }

    /**
     * 
     * @param x x
     * @param y y
     */
    public Vector2f(float x, float y) {
        this();
        this.x = x;
        this.y = y;
    }

    /**
     * 
     * @param o other vector
     * @return distance between the this and o.
     */
    public double distance(@Nonnull final Vector2f o) {
        double axBx = x - o.x;
        axBx = Math.pow(axBx, 2.0d);
        double ayBy = (y - o.y);
        ayBy = Math.pow(ayBy, 2.0d);
        return Math.sqrt(axBx + ayBy);
    }

    /**
     * 
     * @param o other vector.
     * @return resultant vector of adding this and o. 
     */
    @Nonnull
    public Vector2f add(@Nonnull Vector2f o) {
        return new Vector2f(this.x + o.x, this.y + o.y);
    }

    /**
     * 
     * @param o other vector.
     * @return resultant vector of subtracting o from this.
     */
    @Nonnull
    public Vector2f subtract(@Nonnull Vector2f o) {
        return new Vector2f(o.x - this.x, o.y - this.y);
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
    public double dotProduct(@Nonnull Vector2f v1) {
        return x * v1.x + y * v1.y;
    }

    /**
     * 
     * @return normalized vector.
     */
    @Nonnull
    public Vector2f normalize() {
        Vector2f v2 = new Vector2f();

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
    @Nonnull
    public Vector2f scale(float scaleFactor) {
        return new Vector2f(x * scaleFactor, y * scaleFactor);
    }

    /**
     * 
     * @return a copy of this vector.
     */
    @Nonnull
    public Vector2f copy() {
        return new Vector2f(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector2f vector2f = (Vector2f) o;

        if (Float.compare(vector2f.x, x) != 0) return false;
        if (Float.compare(vector2f.y, y) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        return result;
    }
}
