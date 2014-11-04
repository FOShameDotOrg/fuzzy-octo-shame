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

    public Vector position;
    public Boundary bounds;
    public Vector movement;
    public float acceleration = 0;

    public void collideDown(Entity entity) {
    }

    public void collideUp(Entity entity) {
    }

    public void collideLeft(Entity entity) {
    }

    public void collideRight(Entity entity) {
    }

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
    public void draw() {
    }
    
    //TODO Implement Equals/hashCode
}
