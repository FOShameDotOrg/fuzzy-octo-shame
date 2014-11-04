package com.jed.actor;

import com.jed.util.Vector;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public class CircleBoundary extends Boundary {

    /**
     * 
     * @param radius initial radius.
     */
    public CircleBoundary(int radius) {
        super(new Vector(0, 0), new Vector[]{});
        this.radius = radius;
    }

    /**
     * 
     */
    public int radius;

    /**
     * 
     * @return radius of circle boundary.
     */
    public int getRadius() {
        return radius;
    }

    /**
     * 
     * @param radius radius to set.
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public double getRightBound() {
        return owner.position.x + radius;
    }

    @Override
    public double getLeftBound() {
        return owner.position.x - radius;
    }

    @Override
    public double getUpperBound() {
        return owner.position.y - radius;
    }

    @Override
    public double getLowerBound() {
        return owner.position.y + radius;
    }

    @Override
    public int getWidth() {
        return radius * 2;
    }

    @Override
    public int getHeight() {
        return getWidth();
    }
}
