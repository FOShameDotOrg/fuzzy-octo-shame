package com.jed.state;

import com.jed.actor.AbstractEntity;

import javax.annotation.Nonnull;
import java.util.Stack;


/**
 * Created by Peter Colapietro on 11/26/14.
 * @author Peter Colapietro
 * @since 0.1.8
 * @param <E> type of object
 */
public final class GameEntityStack<E extends AbstractEntity> extends Stack<E> {

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
