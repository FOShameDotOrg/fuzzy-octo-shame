package com.jed.actor;

import com.jed.core.Displayable;
import com.jed.util.Vector2f;
import org.colapietro.lang.LangConstants;
import org.colapietro.lang.NotImplementedException;

import javax.annotation.Nonnull;

/**
 * 
 * @author jlinde, Peter Colapietro
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
}
