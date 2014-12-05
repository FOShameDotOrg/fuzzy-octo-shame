package com.jed.actor;

import com.jed.core.Displayable;
import com.jed.util.Vector2f;

import javax.annotation.Nonnull;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public abstract class Boundary implements Displayable {

    /**
     * 
     */
    public AbstractEntity owner;
    
    /**
     * 
     */
    final Vector2f position;
    
    /**
     * 
     */
    public final Vector2f[] vertices;

    /**
     * 
     * @return right bound.
     */
    public abstract double getRightBound();

    /**
     * 
     * @return left bound.
     */
    public abstract double getLeftBound();

    /**
     * 
     * @return upper bound.
     */
    public abstract double getUpperBound();

    /**
     * 
     * @return lower bound.
     */
    public abstract double getLowerBound();

    /**
     * 
     * @return width.
     */
    public abstract int getWidth();

    /**
     * 
     * @return height.
     */
    public abstract int getHeight();

    /**
     * 
     * @param position position.
     * @param vertices vertices.
     */
    Boundary(Vector2f position, Vector2f[] vertices) {
        this.vertices = vertices;
        this.position = position;
    }

    /**
     * 
     * @return world position.
     */
    @Nonnull
    public Vector2f getWorldPosition() {
        return owner.position.add(position);
    }

    /**
     * 
     * @return next world position.
     */
    @Nonnull
    public Vector2f getNextWorldPosition() {
        return getWorldPosition().add(owner.movement);
    }

}
