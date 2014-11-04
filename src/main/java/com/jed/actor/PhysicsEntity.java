package com.jed.actor;

import com.jed.util.Vector;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public abstract class PhysicsEntity extends Entity {

    /**
     * 
     * @param position position vector.
     * @param movement movement vector.
     * @param bounds bounds of entity.
     */
    public PhysicsEntity(Vector position, Vector movement, Boundary bounds) {
        super(position, movement, bounds);
    }

    /**
     * 
     * @return mass
     */
    public abstract double mass();

}
