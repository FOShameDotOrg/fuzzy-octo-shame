package com.jed.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.colapietro.guice.StateManagerModule;
import org.colapietro.slick.LoggableInputListener;
import org.colapietro.slick.LoggableInputListenerModule;
import org.newdawn.slick.InputListener;

/**
 * Codename: Fuzzy Octo Shame
 *
 * Created by Peter Colapietro on 11/26/14.
 * @author Peter Colapietro
 * @since 0.1.8
 */
public class FuzzyOctoShame {

    /**
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        final Injector injector = Guice.createInjector(
                new MotherBrainModule(),
                new LoggableInputListenerModule(),
                new StateManagerModule()
        );
        final MotherBrain motherBrain = injector.getInstance(MotherBrain.class);
        final InputListener basicInputListener = injector.getInstance(LoggableInputListener.class);
        motherBrain.setInputListener(basicInputListener);
        motherBrain.start();
    }

}
