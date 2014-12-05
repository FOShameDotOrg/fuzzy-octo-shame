package com.jed.actor;

import com.jed.util.Vector2f;
import org.lwjgl.opengl.GL11;

import com.jed.util.Rectangle;

/**
 * 
 * @author jlinde, Peter Colapietro
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
        return owner.position.x + position.x + rectangle.getWidth();
    }

    @Override
    public double getLeftBound() {
        return owner.position.x + position.x;
    }

    @Override
    public double getUpperBound() {
        return owner.position.y + position.y;
    }

    @Override
    public double getLowerBound() {
        return owner.position.y + position.y + rectangle.getHeight();
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
        owner.drawChildVertex2f(position.x, position.y);
        owner.drawChildVertex2f(position.x + getWidth(), position.y);
        owner.drawChildVertex2f(position.x + getWidth(), position.y + getHeight());
        owner.drawChildVertex2f(position.x, position.y + getHeight());
        GL11.glEnd();
    }
}
