package com.jed.actor;

import com.jed.util.Vector;

public abstract class PhysicsEntity extends Entity {

    public PhysicsEntity(Vector position, Vector movement, Boundary bounds) {
        super(position, movement, bounds);
    }

    public abstract double mass();

}
