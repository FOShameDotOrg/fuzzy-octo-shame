package com.jed.actor;

import com.jed.core.Displayable;
import com.jed.util.Vector;

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
    public final Vector position;
    
    /**
     * 
     */
    public final Vector[] verticies;

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
     * @param verticies verticies. 
     */
    public Boundary(Vector position, Vector[] verticies) {
        this.verticies = verticies;
        this.position = position;
    }

    /**
     * 
     * @return world position.
     */
    public Vector getWorldPosition() {
        return owner.position.add(position);
    }

    /**
     * 
     * @return next world position.
     */
    public Vector getNextWorldPosition() {
        return getWorldPosition().add(owner.movement);
    }

    @Override
    public void render() {
    }

    @Override
    public void drawChildVertex2f(float x, float y) {
    }
}
