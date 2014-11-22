package com.jed.actor;

import com.jed.util.BasicShapeRenderer;
import com.jed.util.Vector;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public class Ball extends PhysicsEntity {

    /**
     * 
     */
    private int segments;
    
    /**
     * 
     */
    private float g;

    /**
     * 
     */
    private float r;
    
    /**
     * 
     */
    private float b;

    /**
     * 
     * @param displacement displacement.
     * @param movement movement.
     * @param bounds bounds.
     * @param segments segments.
     * @param r red.
     * @param g green.
     * @param b blue.
     */
    public Ball(Vector displacement, Vector movement, Boundary bounds, int segments, float r, float g, float b) {
        super(displacement, movement, bounds);

        this.r = r;
        this.g = g;
        this.b = b;

        this.segments = segments;
    }

    @Override
    public double mass() {
        return getRadius();
    }

    /**
     * 
     * @return radius of ball.
     */
    public int getRadius() {
        return ((CircleBoundary) bounds).radius;
    }

    @Override
    public void render() {
        position = position.add(movement);
        BasicShapeRenderer.drawFilledCircle(
                position.x,
                position.y,
                getRadius(),
                segments,
                r, g, b);

    }
}
