package com.jed.state;

import javax.annotation.Nonnull;
import java.util.Stack;

/**
 * Created by Peter Colapietro on 11/26/14.
 * @author Peter Colapietro
 * @since 0.1.8
 */
public final class GameStateStack extends AbstractGameStack<AbstractGameState> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Nonnull
    @Override
    public AbstractGameState push(@Nonnull final AbstractGameState o) {
        super.push(o);
        o.entered();
        return o;
    }

    @Override
    public synchronized AbstractGameState pop() {
        final AbstractGameState o = super.pop();
        o.leaving();
        return o;
    }

}
