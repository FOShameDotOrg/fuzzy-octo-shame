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
    public void entered();

    /**
     * 
     */
    public void leaving();

    /**
     * 
     */
    public void update();
}
