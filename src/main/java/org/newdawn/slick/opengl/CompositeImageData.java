package org.newdawn.slick.opengl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.util.Log;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A composite data source that checks multiple loaders in order of
 * preference
 * 
 * @author kevin
 */
public class CompositeImageData implements LoadableImageData  {
    /** The list of images sources in order of preference to try loading the data with */
    @Nonnull
    private List<LoadableImageData> sources = new ArrayList<>();
    /** The data source that worked and was used - or null if no luck */
    private LoadableImageData picked;

    /**
     * Add a potentional source of image data
     *
     * @param data The data source to try
     */
    public void add(LoadableImageData data) {
        sources.add(data);
    }

    /**
     * @see org.newdawn.slick.opengl.LoadableImageData#loadImage(java.io.InputStream)
     */
    @Nullable
    public ByteBuffer loadImage(@Nonnull InputStream fis) throws IOException {
        return loadImage(fis, false, null);
    }

    /**
     * @see org.newdawn.slick.opengl.LoadableImageData#loadImage(java.io.InputStream, boolean, int[])
     */
    @Nullable
    public ByteBuffer loadImage(@Nonnull InputStream fis, boolean flipped, @Nullable int[] transparent) throws IOException {
        return loadImage(fis, flipped, false, transparent);
    }

    /**
     * @see org.newdawn.slick.opengl.LoadableImageData#loadImage(java.io.InputStream, boolean, boolean, int[])
     */
    @Nullable
    public ByteBuffer loadImage(@Nonnull InputStream is, boolean flipped, boolean forceAlpha, @Nullable int[] transparent) throws IOException {
        CompositeIOException exception = new CompositeIOException();
        ByteBuffer buffer = null;

        BufferedInputStream in = new BufferedInputStream(is, is.available());
        in.mark(is.available());

        // cycle through our source until one of them works
        for (int i=0;i<sources.size();i++) {
            in.reset();
            try {
                LoadableImageData data = sources.get(i);

                buffer = data.loadImage(in, flipped, forceAlpha, transparent);
                picked = data;
                break;
            } catch (Exception e) {
                Log.warn(sources.get(i).getClass()+" failed to read the data", e);
                exception.addException(e);
            }
        }

        if (picked == null) {
            throw exception;
        }

        return buffer;
    }

    /**
     * Check the state of the image data and throw a
     * runtime exception if theres a problem
     */
    private void checkPicked() {
        if (picked == null) {
            throw new RuntimeException("Attempt to make use of uninitialised or invalid composite image data");
        }
    }

    /**
     * @see org.newdawn.slick.opengl.ImageData#getFormat()
     */
    public Format getFormat() {
        checkPicked();

        return picked.getFormat();
    }

    /**
     * @see org.newdawn.slick.opengl.ImageData#getHeight()
     */
    public int getHeight() {
        checkPicked();

        return picked.getHeight();
    }

    /**
     * @see org.newdawn.slick.opengl.ImageData#getImageBufferData()
     */
    public ByteBuffer getImageBufferData() {
        checkPicked();

        return picked.getImageBufferData();
    }

    /**
     * @see org.newdawn.slick.opengl.ImageData#getTexHeight()
     */
    public int getTexHeight() {
        checkPicked();

        return picked.getTexHeight();
    }

    /**
     * @see org.newdawn.slick.opengl.ImageData#getTexWidth()
     */
    public int getTexWidth() {
        checkPicked();

        return picked.getTexWidth();
    }

    /**
     * @see org.newdawn.slick.opengl.ImageData#getWidth()
     */
    public int getWidth() {
        checkPicked();

        return picked.getWidth();
    }

    /**
     * @see org.newdawn.slick.opengl.LoadableImageData#configureEdging(boolean)
     */
    public void configureEdging(boolean edging) {
        for (int i=0;i<sources.size();i++) {
            sources.get(i).configureEdging(edging);
        }
    }

}
