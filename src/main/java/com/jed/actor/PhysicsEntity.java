package com.jed.actor;

import com.jed.util.Vector2f;

/**
 *
 * Abstract class identical to {@link AbstractEntity} except for the addition of
 * com.jed.actor.PhysicsEntity#mass().
 * 
 * @author jlinde, Peter Colapietro
 * @since 0.1.0
 *
 */
abstract class PhysicsEntity extends AbstractEntity {

    /**
     * 
     * @param position position vector.
     * @param movement movement vector.
     * @param bounds bounds of entity.
     */
    PhysicsEntity(Vector2f position, Vector2f movement, Boundary bounds) {
        super(position, movement, bounds);
    }

    /**
     * 
     * @return mass
     */
    public abstract double mass();

}
