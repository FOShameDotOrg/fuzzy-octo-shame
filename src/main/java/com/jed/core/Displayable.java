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
    void draw();

    /**
     * 
     * @param x x
     * @param y y
     */
    public void drawChildVertex2f(float x, float y);
}
