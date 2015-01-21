package com.jed.state;

import com.jed.actor.AbstractEntity;
import com.jed.util.Vector2f;
import org.lwjgl.opengl.GL11;

import com.jed.actor.Boundary;

import javax.annotation.Nonnull;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public final class MapTile extends AbstractEntity {

    /**
     * 
     */
    private float glTexX;

    /**
     *
     */
    private float glTexY;

    /**
     *
     */
    private final float glTexWidth;

    /**
     *
     */
    private final float glTexHeight;
    
    /**
     * 
     */
    private int tileId;

    /**
     * 
     */
    private final GameMap map;

    /**
     * 
     */
    //TODO: TEMPORARY!
    private boolean colliding;
    
    /**
     * 
     */
    private boolean evaluating;

    /**
     * FIXME, the parameters is too damn high.
     * @param position FIXME Javadoc
     * @param bounds FIXME Javadoc
     * @param glTexX FIXME Javadoc
     * @param glTexY FIXME Javadoc
     * @param glTexWidth FIXME Javadoc
     * @param glTexHeight FIXME Javadoc
     * @param tileId FIXME Javadoc
     * @param map FIXME Javadoc
     */
    public MapTile( Vector2f position,
                    @Nonnull Boundary bounds,
                    float glTexX,float glTexY,
                    float glTexWidth,
                    float glTexHeight,
                    int tileId,
                    GameMap map) {
        super(position, new Vector2f(0, 0), bounds);

        this.glTexX = glTexX;
        this.glTexY = glTexY;
        this.tileId = tileId;

        this.glTexWidth = glTexWidth;
        this.glTexHeight = glTexHeight;

        this.map = map;
    }

    /**
     * 
     * @return glTexX
     */
    public float getGlTexX() {
        return glTexX;
    }

    /**
     * 
     * @param glTexX glTexX
     */
    public void setGlTexX(float glTexX) {
        this.glTexX = glTexX;
    }

    /**
     * 
     * @return glTexY
     */
    public float getGlTexY() {
        return glTexY;
    }

    /**
     * 
     * @param glTexY glTexY
     */
    public void setGlTexY(float glTexY) {
        this.glTexY = glTexY;
    }

    /**
     * 
     * @return tileId
     */
    public int getTileId() {
        return tileId;
    }

    /**
     * 
     * @param tileId tileId
     */
    public void setTileId(int tileId) {
        this.tileId = tileId;
    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
        //TODO: Tile collision coloring is temporary...
        if (colliding) {
            GL11.glColor3f(0, 0, 1f);
        } else if (evaluating) {
            GL11.glColor3f(1f, 0, 1f);
        } else {
            GL11.glColor3f(1, 1, 1);
        }


        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(glTexX, glTexY);
        map.drawChildVertex2f(getPosition().x, getPosition().y);

        GL11.glTexCoord2f(glTexX + glTexWidth, glTexY);
        map.drawChildVertex2f(getPosition().x + getBounds().getWidth(), getPosition().y);

        GL11.glTexCoord2f(glTexX + glTexWidth, glTexY + glTexHeight);
        map.drawChildVertex2f(getPosition().x + getBounds().getWidth(), getPosition().y + getBounds().getHeight());

        GL11.glTexCoord2f(glTexX, glTexY + glTexHeight);
        map.drawChildVertex2f(getPosition().x, getPosition().y + getBounds().getHeight());

        GL11.glEnd();
    }

    /**
     *
     * @param colliding colliding
     */
    public void setColliding(boolean colliding) {
        this.colliding = colliding;
    }

    /**
     *
     * @param evaluating evaluating
     */
    public void setEvaluating(boolean evaluating) {
        this.evaluating = evaluating;
    }
}
