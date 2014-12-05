package com.jed.state;

import javax.annotation.Nonnull;

/**
 * Created by Peter Colapietro on 11/26/14.
 * @author Peter Colapietro
 * @since 0.1.8
 *
 * @param <E>
 */
public final class GameStateStack<E extends State> extends AbstractGameStack<E> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Nonnull
    @Override
    public E push(@Nonnull final E o) {
        super.push(o);
        o.entered();
        return o;
    }

    @Override
    public synchronized E pop() {
        final E o = super.pop();
        o.leaving();
        return o;
    }

}
