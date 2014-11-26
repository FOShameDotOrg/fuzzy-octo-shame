package com.jed.state;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public interface State {
    
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
