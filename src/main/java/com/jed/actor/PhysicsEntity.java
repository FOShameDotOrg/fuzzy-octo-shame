package com.jed.actor;

import com.jed.util.Vector3f;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
abstract class PhysicsEntity extends AbstractEntity {

    /**
     * 
     * @param position position vector.
     * @param movement movement vector.
     * @param bounds bounds of entity.
     */
    PhysicsEntity(Vector3f position, Vector3f movement, Boundary bounds) {
        super(position, movement, bounds);
    }

    /**
     * 
     * @return mass
     */
    public abstract double mass();

}
