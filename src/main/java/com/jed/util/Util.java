package com.jed.util;

import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 
 * @author jlinde, Peter Colapietro
 *
 */
public class Util {

    /**
     * 
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);

    /**
     * 
     * @param path path to texture file
     * @return texture object loaded from file
     */
    @Nullable
    public static Texture loadTexture(@Nonnull String path) {
        Texture texture = null;

        String type = path.substring(path.lastIndexOf('.') + 1).toUpperCase();

        try (final InputStream resourceAsStream = ResourceLoader.getResourceAsStream(path)){
            texture = TextureLoader.getTexture(type, resourceAsStream);
            LOGGER.debug("Texture loaded: " + texture);
            LOGGER.debug(">> Image width: " + texture.getImageWidth());
            LOGGER.debug(">> Image height: " + texture.getImageHeight());
            LOGGER.debug(">> Texture width: " + texture.getTextureWidth());
            LOGGER.debug(">> Texture height: " + texture.getTextureHeight());
            LOGGER.debug(">> Texture ID: " + texture.getTextureID());
            LOGGER.debug(">> Texture Alpha: " + texture.hasAlpha());
        } catch (IOException e) {
            LOGGER.error("An error occurred while loading texture", e);
            System.exit(ExitStatusCode.ERROR.getStatusCode());
        }
        return texture;
    }

    /**
     * 
     * @param value value to get closes power of two to
     * @return closest power of two to parameter
     */
    public static int getClosestPowerOfTwo(int value) {
        int power = 2;
        while (true) {
            if (value == power) {
                return value;
            } else if (value > power) {
                power *= 2;
            } else {
                return power;
            }
        }
    }

}
