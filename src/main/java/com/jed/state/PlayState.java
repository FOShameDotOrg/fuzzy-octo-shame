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
    private boolean isDebugViewEnabled;
    
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
        } else if(command.equals(new BasicCommand("stepFrame"))) {
            if (paused) {
                stepFrame = true;
            }
        } else if(command.getName().equals(BasicCommandConstants.JUMP)) {
            currentMap.getPlayer().setJumping(true);
        } else if(command.getName().equals(BasicCommandConstants.MOVE_RIGHT)) {
            currentMap.getPlayer().setMovingRight(true);
        } else if(command.getName().equals(BasicCommandConstants.MOVE_LEFT)) {
            currentMap.getPlayer().setMovingLeft(true);
        }
    }

    @Override
    public void controlReleased(Command command) {
        LOGGER.debug("com.jed.state.PlayState#controlReleased");
        LOGGER.info("controlReleased {}",command.toString());
        if(command.getName().equals(BasicCommandConstants.MOVE_RIGHT)) {
            currentMap.getPlayer().setMovingRight(false);
        } else if(command.getName().equals(BasicCommandConstants.MOVE_LEFT)) {
            currentMap.getPlayer().setMovingLeft(false);
        } else if(command.getName().equals("toggleDebugView")) {
            isDebugViewEnabled = !isDebugViewEnabled;
            currentMap.setDebugViewEnabled(isDebugViewEnabled);
        }
    }

    /**
     *
     * @return currentMap
     */
    public GameMap getCurrentMap() {
        return currentMap;
    }
}
