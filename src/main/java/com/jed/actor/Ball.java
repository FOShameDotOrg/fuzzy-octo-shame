package com.jed.actor;

import com.jed.util.BasicShapeRenderer;
import com.jed.util.Vector2f;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public class Ball extends PhysicsEntity {

    /**
     * 
     */
    private final int segments;
    
    /**
     * 
     */
    private final float g;

    /**
     * 
     */
    private final float r;
    
    /**
     * 
     */
    private final float b;

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
    public Ball(Vector2f displacement, Vector2f movement, Boundary bounds, int segments, float r, float g, float b) {
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
        return ((CircleBoundary) getBounds()).radius;
    }

    @Override
    public void render() {
        setPosition(getPosition().add(getMovement()));
        BasicShapeRenderer.drawFilledCircle(
                getPosition().x,
                getPosition().y,
                getRadius(),
                segments,
                r, g, b);

    }
}
