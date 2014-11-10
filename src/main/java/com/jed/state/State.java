package com.jed.state;

import com.jed.core.Displayable;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public interface State extends Displayable {
    
    /**
     * 
     */
    void entered();

    /**
     * 
     */
    void leaving();

    /**
     * 
     */
    void update();
}
