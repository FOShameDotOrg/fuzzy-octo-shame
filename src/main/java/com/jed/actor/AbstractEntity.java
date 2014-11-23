package com.jed.actor;

import com.jed.core.Displayable;
import com.jed.state.State;
import com.jed.util.Vector3f;
import org.colapietro.lang.LangConstants;
import org.colapietro.lang.NotImplementedException;
import org.colapietro.lwjgl.physics.Collidable;

import javax.annotation.Nonnull;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public abstract class AbstractEntity implements Displayable, State, Collidable {

    /**
     * 
     */
    public Vector3f position;
    
    /**
     * 
     */
    @Nonnull
    public final Boundary bounds;
    
    /**
     * 
     */
    public Vector3f movement;
    
    /**
     * 
     */
    public float acceleration = 0;

    /**
     *
     * @param position position vector.
     * @param movement movement vector.
     * @param bounds entity's bounds.
     */
    protected AbstractEntity(Vector3f position, Vector3f movement, @Nonnull Boundary bounds) {
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
    
    //TODO Implement Equals/hashCode
}
