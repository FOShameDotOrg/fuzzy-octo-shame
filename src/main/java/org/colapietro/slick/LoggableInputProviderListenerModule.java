package org.colapietro.slick;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import org.newdawn.slick.command.InputProviderListener;

/**
 * Created by Peter Colapietro on 11/23/14.
 * @author Peter Colapietro
 * @since 0.1.8
 */
public class LoggableInputProviderListenerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(InputProviderListener.class).to(LoggableInputProviderListener.class).in(Singleton.class);
    }
}
