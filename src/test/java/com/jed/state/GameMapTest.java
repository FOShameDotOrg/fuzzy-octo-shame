package com.jed.state;

import com.jed.util.MapLoader;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lwjgl.opengl.Display;

public class GameMapTest {

    private GameMap gameMap;

    @Before
    public void setUp() throws Exception {
        Display.create();
        gameMap = MapLoader.loadMap();
        gameMap.entered();
    }

    @After
    public void tearDown() throws Exception {
        gameMap = null;
    }

    @Test
    public void testUpdate() throws Exception {
        Assert.assertNotNull(gameMap.getPlayer());
        gameMap.update();
    }
}