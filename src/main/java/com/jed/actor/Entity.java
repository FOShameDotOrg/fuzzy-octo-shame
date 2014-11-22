package com.jed.actor;

import com.jed.core.Displayable;
import com.jed.state.State;
import com.jed.util.Vector;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public abstract class Entity implements Displayable, State {

    /**
     * 
     */
    public Vector position;
    
    /**
     * 
     */
    public Boundary bounds;
    
    /**
     * 
     */
    public Vector movement;
    
    /**
     * 
     */
    public float acceleration = 0;

    /**
     * 
     * @param entity to collide down with.
     */
    public void collideDown(Entity entity) {
    }

    /**
     * 
     * @param entity to collide down with.
     */
    public void collideUp(Entity entity) {
    }

    /**
     * 
     * @param entity to collide down with.
     */
    public void collideLeft(Entity entity) {
    }

    /**
     * 
     * @param entity to collide down with.
     */
    public void collideRight(Entity entity) {
    }

    /**
     * 
     * @param position position vector.
     * @param movement movement vector.
     * @param bounds entitiy's bounds.
     */
    public Entity(Vector position, Vector movement, Boundary bounds) {
        this.position = position;
        this.bounds = bounds;
        bounds.owner = this;
        this.movement = movement;
    }

    @Override
    public void drawChildVertex2f(float x, float y) {
    }

    @Override
    public void entered() {
    }

    @Override
    public void leaving() {
    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
    }
    
    //TODO Implement Equals/hashCode
}
