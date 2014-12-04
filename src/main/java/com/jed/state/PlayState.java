package com.jed.state;

import com.jed.util.MapLoader;
import org.colapietro.slick.command.BasicCommandConstants;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.InputProviderListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public class PlayState extends AbstractGameState implements InputProviderListener {

    /**
     *
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayState.class);


    /**
     * 
     */
    private GameMap currentMap;

    /**
     * 
     */
    private boolean paused = true;
    
    /**
     * 
     */
    private boolean stepFrame = false;
    
    /**
     * 
     */
    @SuppressWarnings("unused")
    private final boolean isDebugViewEnabled;
    
    /**
     * @since 0.1.8
     * 
     * @param isDebugViewEnabled isDebugViewEnabled
     */
    public PlayState(boolean isDebugViewEnabled) {
        this.isDebugViewEnabled = isDebugViewEnabled;
        currentMap = MapLoader.loadMap();
        currentMap.setDebugViewEnabled(isDebugViewEnabled);
    }

    @Override
    public void entered() {
        currentMap = MapLoader.loadMap();
        currentMap.setDebugViewEnabled(isDebugViewEnabled);
        currentMap.entered();
    }

    @Override
    public void update() {
        if (!paused || stepFrame) {
            currentMap.update();
        }
        stepFrame = false;
    }

    @Override
    public void render() {
        currentMap.render();
    }

    @Override
    public void controlPressed(Command command) {
        LOGGER.debug("com.jed.state.PlayState#controlPressed");
        LOGGER.info("controlPressed {}",command.toString());
        if(command.equals(new BasicCommand("pauseToggle"))) {
            paused = !paused;
        }
        if(command.equals(new BasicCommand("stepFrame"))) {
            if (paused) {
                stepFrame = true;
            }
        }
        if(command.getName().equals(BasicCommandConstants.JUMP)) {
            currentMap.getPlayer().jump();
        }
    }

    @Override
    public void controlReleased(Command command) {
        LOGGER.debug("com.jed.state.PlayState#controlReleased");
        LOGGER.info("controlReleased {}",command.toString());
    }

    /**
     *
     * @return currentMap
     */
    public GameMap getCurrentMap() {
        return currentMap;
    }
}
