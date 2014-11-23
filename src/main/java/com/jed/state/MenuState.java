package com.jed.state;

import com.jed.util.Vector3f;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public class MenuState extends GameState {

    /**
     * 
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuState.class);

    /**
     * 
     */
    private UnicodeFont font;

    /**
     * 
     */
    private String daString;
    
    /**
     * 
     */
    private Vector3f coords;

    /**
     * 
     * @param manager game state manager
     */
    public MenuState(GameStateManager manager) {
        super(manager);
    }

    /**
     * 
     * @return daString
     */
    public String getDaString() {
        return daString;
    }

    /**
     * 
     * @param daString daString
     */
    public void setDaString(String daString) {
        this.daString = daString;
    }

    /**
     * 
     * @return coords
     */
    public Vector3f getCoords() {
        return coords;
    }

    /**
     * 
     * @param coords coords
     */
    public void setCoords(Vector3f coords) {
        this.coords = coords;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void entered() {
        java.awt.Font awtFont = new java.awt.Font("Arial", java.awt.Font.PLAIN, 24);
        font = new UnicodeFont(awtFont);
        font.getEffects().add(new ColorEffect(java.awt.Color.RED));
        font.addAsciiGlyphs();
        try {
            font.loadGlyphs();
        } catch (SlickException e) {
            LOGGER.error("Failed to load Font!", e);
        }
    }

    @Override
    public void leaving() {
    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        font.drawString(coords.x, coords.y, daString);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

}
