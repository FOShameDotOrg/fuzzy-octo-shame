package org.colapietro.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.jed.state.GameStateManager;
import com.jed.state.StateManager;

/**
 * Created by Peter Colapietro on 12/4/14.
 * @author Peter Colapietro
 * @since 1.8
 */
public final class StateManagerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(StateManager.class).to(GameStateManager.class).in(Singleton.class);
    }
}
