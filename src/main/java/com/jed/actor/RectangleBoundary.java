package com.jed.actor;

import com.jed.util.Vector2f;
import org.lwjgl.opengl.GL11;

import com.jed.util.Rectangle;

/**
 *
 * Concrete implementation of {@link com.jed.actor.Boundary} which represents
 * a {@link com.jed.util.Rectangle}. Use {@link com.jed.actor.PolygonBoundary}
 * if you want a concrete implementation for a polygon that is not rectangle.
 *
 * @author jlinde, Peter Colapietro
 * @since 0.1.0
 *
 */
public class RectangleBoundary extends Boundary {

    /**
     * 
     */
    private final Rectangle rectangle;

    /**
     * 
     * @param rectangle rectangle
     * @param position position of rectangle boundary
     */
    public RectangleBoundary(Rectangle rectangle, Vector2f position) {
        super(position, new Vector2f[]{});
        this.rectangle = rectangle;
    }

    @Override
    public double getRightBound() {
        return getOwner().getPosition().x + getPosition().x + rectangle.getWidth();
    }

    @Override
    public double getLeftBound() {
        return getOwner().getPosition().x + getPosition().x;
    }

    @Override
    public double getUpperBound() {
        return getOwner().getPosition().y + getPosition().y;
    }

    @Override
    public double getLowerBound() {
        return getOwner().getPosition().y + getPosition().y + rectangle.getHeight();
    }

    @Override
    public int getWidth() {
        return rectangle.getWidth();
    }

    @Override
    public int getHeight() {
        return rectangle.getHeight();
    }

    @Override
    public void render() {
        //Bounding Box
        GL11.glColor3f(1f, 0, 0);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        getOwner().drawChildVertex2f(getPosition().x, getPosition().y);
        getOwner().drawChildVertex2f(getPosition().x + getWidth(), getPosition().y);
        getOwner().drawChildVertex2f(getPosition().x + getWidth(), getPosition().y + getHeight());
        getOwner().drawChildVertex2f(getPosition().x, getPosition().y + getHeight());
        GL11.glEnd();
    }
}
