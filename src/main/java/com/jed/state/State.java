package com.jed.state;

import org.colapietro.lang.NotImplementedException;

/**
 * 
 * @author jlinde, Peter Colapietro
 * @since 0.1.0
 *
 */
public interface State {
    
    /**
     *
     * @throws NotImplementedException NotImplementedException
     */
    void entered() throws NotImplementedException;

    /**
     *
     * @throws NotImplementedException NotImplementedException
     */
    void leaving() throws NotImplementedException;

    /**
     *
     * @throws NotImplementedException NotImplementedException
     */
    void update() throws NotImplementedException;

}
