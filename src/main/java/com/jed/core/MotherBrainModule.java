package com.jed.core;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * Created by Peter Colapietro on 10/31/14.
 */
public class MotherBrainModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Startable.class).to(MotherBrain.class).in(Singleton.class);
    }
}
