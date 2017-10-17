package com.jed.util;

import com.jed.actor.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;

import static org.junit.Assert.*;

/**
 * @author Peter Colapietro.
 */
public class UtilTest {

    private static final String VALID_TEXTURE_PATH = Player.TEXTURE_PATH;
    private static final String INVALID_TEXTURE_PATH = "MEGA_MAN_SH.png";

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
    public void loadTexture() throws Exception {
        final Texture texture = Util.loadTexture(VALID_TEXTURE_PATH);
        assertNotNull(texture);

    }

    @Test(expected = RuntimeException.class)
    public void testLoadInvalidTexturePath() throws Exception {
        Util.loadTexture(INVALID_TEXTURE_PATH);
    }


    @Test
    public void getClosestPowerOfTwo() throws Exception {
        assertTrue(true); // FIXME
    }

}