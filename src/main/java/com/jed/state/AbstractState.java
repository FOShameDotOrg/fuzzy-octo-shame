package com.jed.state;

import org.colapietro.lang.LangConstants;
import org.colapietro.lang.NotImplementedException;

/**
 * Created by Peter Colapietro on 11/22/14.
 *
 * @author Peter Colapietro
 * @since 0.1.8
 */
public abstract class AbstractState implements State {

    @Override
    public void entered() throws NotImplementedException {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);
    }

    @Override
    public void leaving() {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);
    }

    @Override
    public void update() {
        throw new NotImplementedException(LangConstants.NOT_IMPLEMENTED_YET_MESSAGE);
    }
}
