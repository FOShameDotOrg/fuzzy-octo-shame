package com.jed.actor;

import com.jed.core.Displayable;
import com.jed.util.Vector2f;
import org.colapietro.lang.LangConstants;
import org.colapietro.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.Arrays;

/**
 * Abstract class who associates another {@link com.jed.actor.AbstractEntity} as an "owner." The owner's
 * boundary is defined by a subclass of {@link com.jed.actor.Boundary}'s implementation.
 * 
 * @author jlinde, Peter Colapietro
 * @since 0.1.0
 *
 * @see com.jed.core.Displayable
 *
 */
public abstract class Boundary implements Displayable {

    /**
     *
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Boundary.class);

    /**
     * 
     */
    private AbstractEntity owner;
    
    /**
     * 
     */
    private final Vector2f position;
    
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
        return owner.getPosition().add(position);
    }

    /**
     * 
     * @return next world position.
     */
    @Nonnull
    public Vector2f getNextWorldPosition() {
        return getWorldPosition().add(owner.getMovement());
    }

    /**
     *
     * @return position
     */
    public Vector2f getPosition() {
        return position;
    }

    /**
     *
     * @return owner
     */
    public AbstractEntity getOwner() {
        return owner;
    }

    /**
     *
     * @param owner owner
     */
    public void setOwner(AbstractEntity owner) {
        this.owner = owner;
    }

    @Override
    public void render() throws NotImplementedException {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);
    }

    @Override
    public void drawChildVertex2f(float x, float y) throws NotImplementedException {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Boundary boundary = (Boundary) o;

        if (owner != null ? !owner.equals(boundary.owner) : boundary.owner != null) return false;
        if (position != null ? !position.equals(boundary.position) : boundary.position != null) return false;
        if (!Arrays.equals(vertices, boundary.vertices)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = owner != null ? owner.hashCode() : 0;
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (vertices != null ? Arrays.hashCode(vertices) : 0);
        return result;
    }
}
