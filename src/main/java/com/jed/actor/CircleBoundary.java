package com.jed.actor;

import com.jed.util.Vector2f;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public class CircleBoundary extends Boundary {
    
    /**
     * 
     */
    public int radius;

    /**
     * 
     * @param radius initial radius.
     */
    public CircleBoundary(final int radius) {
        super(new Vector2f(0, 0), new Vector2f[]{});
        this.radius = radius;
    }

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
    public void setRadius(final int radius) {
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
