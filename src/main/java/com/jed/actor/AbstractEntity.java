package com.jed.actor;

import com.jed.core.Displayable;
import com.jed.state.State;
import com.jed.util.Vector2f;
import org.colapietro.lang.LangConstants;
import org.colapietro.lang.NotImplementedException;
import org.colapietro.lwjgl.physics.Collidable;

import javax.annotation.Nonnull;

/**
 * 
 * Abstract class whose subclasses are displayable, collidable, and contain various states. It is also
 * composed of a position and movement vector, acceleration, and a {@literal Boundary}.
 * 
 * @author jlinde, Peter Colapietro
 * @since 0.1.0
 * @see com.jed.core.Displayable
 * @see com.jed.state.State
 * @see org.colapietro.lwjgl.physics.Collidable
 *
 */
public abstract class AbstractEntity implements Displayable, State, Collidable {

    /**
     * 
     */
    public Vector2f position;
    
    /**
     * 
     */
    @Nonnull
    public final Boundary bounds;
    
    /**
     * 
     */
    public Vector2f movement;
    
    /**
     * 
     */
    protected float acceleration = 0;

    /**
     *
     * @param position position vector.
     * @param movement movement vector.
     * @param bounds entity's bounds.
     */
    protected AbstractEntity(Vector2f position, Vector2f movement, @Nonnull Boundary bounds) {
        this.position = position;
        this.bounds = bounds;
        bounds.owner = this;
        this.movement = movement;
    }

    @Override
    public void drawChildVertex2f(float x, float y) throws NotImplementedException {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);
    }

    @Override
    public void entered() throws NotImplementedException {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);
    }

    @Override
    public void leaving() throws NotImplementedException {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);
    }

    @Override
    public void update() throws NotImplementedException {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);
    }

    @Override
    public void render() throws NotImplementedException {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);
    }

    @Override
    public void collideDown(AbstractEntity entity) throws NotImplementedException {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);
    }

    @Override
    public void collideUp(AbstractEntity entity) throws NotImplementedException {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);
    }

    @Override
    public void collideLeft(AbstractEntity entity) throws NotImplementedException {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);
    }

    @Override
    public void collideRight(AbstractEntity entity) throws NotImplementedException {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);
    }

    /**
     *
     * @return acceleration
     */
    public float getAcceleration() {
        return acceleration;
    }

    //TODO Implement Equals/hashCode
}
