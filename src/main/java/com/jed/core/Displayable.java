package com.jed.core;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public interface Displayable {
    /**
     * 
     */
    void render();

    /**
     * 
     * @param x x
     * @param y y
     */
     void drawChildVertex2f(float x, float y);
}
