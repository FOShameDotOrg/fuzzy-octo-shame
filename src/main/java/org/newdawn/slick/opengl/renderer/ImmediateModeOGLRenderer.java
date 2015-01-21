package org.newdawn.slick.opengl.renderer;

import org.lwjgl.opengl.EXTSecondaryColor;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

/**
 * The default OpenGL renderer, uses immediate mode for everything.
 * 
 * @author kevin
 */
public class ImmediateModeOGLRenderer implements SGL {
    /** The width of the display. */
    private int width;
    /** The height of the display. */
    private int height;
    /** The current colour. */
    @Nonnull
    private final float[] current = new float[] {1,1,1,1};
    /** The global colour scale. */
    float alphaScale = 1;

    @Override
    public void initDisplay(int width, int height) {
        this.width = width;
        this.height = height;

        //String extensions = GL11.glGetString(GL11.GL_EXTENSIONS);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClearDepth(1);                                       
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        GL11.glViewport(0,0,width,height);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    @Override
    public void enterOrtho(int xsize, int ysize) {
        this.width = xsize;
        this.height = ysize;
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glViewport(0,0,width,height);
    }

    @Override
    public void glBegin(int geomType) {
        GL11.glBegin(geomType);
    }

    @Override
    public void glBindTexture(int target, int id) {
        GL11.glBindTexture(target, id);
    }

    @Override
    public void glBlendFunc(int src, int dest) {
        GL11.glBlendFunc(src, dest);
    }

    @Override
    public void glCallList(int id) {
        GL11.glCallList(id);
    }

    @Override
    public void glClear(int value) {
        GL11.glClear(value);
    }

    @Override
    public void glClearColor(float red, float green, float blue, float alpha) {
        GL11.glClearColor(red, green, blue, alpha);
    }

    @Override
    public void glClipPlane(int plane, DoubleBuffer buffer) {
        GL11.glClipPlane(plane, buffer);
    }

    @Override
    public void glColor4f(float r, float g, float b, float a) {
        a *= alphaScale;

        current[0] = r;
        current[1] = g;
        current[2] = b;
        current[3] = a;

        GL11.glColor4f(r, g, b, a);
    }

    @Override
    public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        GL11.glColorMask(red, green, blue, alpha);
    }

    @Override
    public void glCopyTexImage2D(int target, int level, int internalFormat, int x, int y, int width, int height, int border) {
        GL11.glCopyTexImage2D(target, level, internalFormat, x, y, width, height, border);
    }

    @Override
    public void glDeleteTextures(IntBuffer buffer) {
        GL11.glDeleteTextures(buffer);
    }

    @Override
    public void glDisable(int item) {
        GL11.glDisable(item);
    }

    @Override
    public void glEnable(int item) {
        GL11.glEnable(item);
    }

    @Override
    public void glEnd() {
        GL11.glEnd();
    }

    @Override
    public void glEndList() {
        GL11.glEndList();
    }

    @Override
    public int glGenLists(int count) {
        return GL11.glGenLists(count);
    }

    @Override
    public void glGetFloat(int id, FloatBuffer ret) {
        GL11.glGetFloat(id, ret);
    }

    @Override
    public void glGetInteger(int id, IntBuffer ret) {
        GL11.glGetInteger(id, ret);
    }

    @Override
    public void glGetTexImage(int target, int level, int format, int type, ByteBuffer pixels) {
        GL11.glGetTexImage(target, level, format, type, pixels);
    }

    @Override
    public void glLineWidth(float width) {
        GL11.glLineWidth(width);
    }

    @Override
    public void glLoadIdentity() {
        GL11.glLoadIdentity();
    }

    @Override
    public void glNewList(int id, int option) {
        GL11.glNewList(id, option);
    }

    @Override
    public void glPointSize(float size) {
        GL11.glPointSize(size);
    }

    @Override
    public void glPopMatrix() {
        GL11.glPopMatrix();
    }

    @Override
    public void glPushMatrix() {
        GL11.glPushMatrix();
    }

    @Override
    public void glReadPixels(int x, int y, int width, int height, int format, int type, ByteBuffer pixels) {
        GL11.glReadPixels(x, y, width, height, format, type, pixels);
    }

    @Override
    public void glRotatef(float angle, float x, float y, float z) {
        GL11.glRotatef(angle, x, y, z);
    }

    @Override
    public void glScalef(float x, float y, float z) {
        GL11.glScalef(x, y, z);
    }

    @Override
    public void glScissor(int x, int y, int width, int height) {
        GL11.glScissor(x, y, width, height);
    }

    @Override
    public void glTexCoord2f(float u, float v) {
        GL11.glTexCoord2f(u, v);
    }

    @Override
    public void glTexEnvi(int target, int mode, int value) {
        GL11.glTexEnvi(target, mode, value);
    }

    @Override
    public void glTranslatef(float x, float y, float z) {
        GL11.glTranslatef(x, y, z);
    }

    @Override
    public void glVertex2f(float x, float y) {
        GL11.glVertex2f(x, y);
    }

    @Override
    public void glVertex3f(float x, float y, float z) {
        GL11.glVertex3f(x, y, z);
    }

    @Override
    public void flush() {
    }

    @Override
    public void glTexParameteri(int target, int param, int value) {
        GL11.glTexParameteri(target, param, value);
    }

    @Override
    @Nonnull
    public float[] getCurrentColor() {
        return Arrays.copyOf(current, current.length);
    }

    @Override
    public void glDeleteLists(int list, int count) {
        GL11.glDeleteLists(list, count);
    }

    @Override
    public void glClearDepth(float value) {
        GL11.glClearDepth(value);
    }

    @Override
    public void glDepthFunc(int func) {
        GL11.glDepthFunc(func);
    }

    @Override
    public void glDepthMask(boolean mask) {
        GL11.glDepthMask(mask);
    }

    @Override
    public void setGlobalAlphaScale(float alphaScale) {
        this.alphaScale = alphaScale;
    }

    @Override
    public void glLoadMatrix(FloatBuffer buffer) {
        GL11.glLoadMatrix(buffer);
    }

    @Override
    public void glGenTextures(IntBuffer ids) {
        GL11.glGenTextures(ids);
    }

    @Override
    public void glGetError() {
        GL11.glGetError();
    }

    @Override
    public void glTexImage2D(int target, int i, int dstPixelFormat,
            int width, int height, int j, int srcPixelFormat,
            int glUnsignedByte, ByteBuffer textureBuffer) {
        GL11.glTexImage2D(target, i, dstPixelFormat, width, height, j, srcPixelFormat,glUnsignedByte,textureBuffer);
    }

    @Override
    public void glTexSubImage2D(int glTexture2d, int i, int pageX, int pageY,
            int width, int height, int glBgra, int glUnsignedByte,
            ByteBuffer scratchByteBuffer) {
        GL11.glTexSubImage2D(glTexture2d, i, pageX, pageY, width, height, glBgra, glUnsignedByte, scratchByteBuffer);
    }

    @Override
    public boolean canTextureMirrorClamp() {
        return GLContext.getCapabilities().GL_EXT_texture_mirror_clamp;
    }

    @Override
    public boolean canSecondaryColor() {
        return GLContext.getCapabilities().GL_EXT_secondary_color;
    }

    @Override
    public void glSecondaryColor3ubEXT(byte b, byte c, byte d) {
        EXTSecondaryColor.glSecondaryColor3ubEXT(b,c,d);
    }

}
