package com.jed.core;

import com.jed.actor.Player;
import com.jed.util.MapLoader;
import com.jed.util.Util;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.util.ResourceLoader;

import static org.junit.Assert.*;

/**
 * @author Peter Colapietro.
 */
public class FuzzyOctoShameTest {
    @Before
    public void setUp() throws Exception {
        Display.setDisplayMode(new DisplayMode(0, 0));
        Display.create();
    }

    @After
    public void tearDown() throws Exception {
        Display.destroy();
    }

    @Test
    public void testResourcesUsedLoading() throws Exception {
        assertNotNull(Util.loadTexture(Player.TEXTURE_PATH));
        assertNotNull(ResourceLoader.getResourceAsStream(MapLoader.LEVEL_ONE_PATH));
    }

}