package com.jed.core;

import org.colapietro.lang.NotImplementedException;

/**
 * 
 * @author jlinde, Peter Colapietro
 * @since 0.1.0
 *
 */
public interface Displayable {
    /**
     *
     * @throws NotImplementedException NotImplementedException
     */
    void render() throws NotImplementedException;

    /**
     * 
     * @param x x
     * @param y y
     *
     * @throws NotImplementedException NotImplementedException
     */
     void drawChildVertex2f(float x, float y) throws NotImplementedException;
}
