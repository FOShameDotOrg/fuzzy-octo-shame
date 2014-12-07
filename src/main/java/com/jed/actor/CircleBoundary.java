package com.jed.actor;

import com.jed.util.Vector2f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public class CircleBoundary extends Boundary {

    /**
     *
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CircleBoundary.class);


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
        return getOwner().getPosition().x + radius;
    }

    @Override
    public double getLeftBound() {
        return getOwner().getPosition().x - radius;
    }

    @Override
    public double getUpperBound() {
        return getOwner().getPosition().y - radius;
    }

    @Override
    public double getLowerBound() {
        return getOwner().getPosition().y + radius;
    }

    @Override
    public int getWidth() {
        return radius * 2;
    }

    @Override
    public int getHeight() {
        return getWidth();
    }

    @Override
    public void render() {
        LOGGER.warn("{}","No OP com.jed.actor.CircleBoundary#render");
    }
}
