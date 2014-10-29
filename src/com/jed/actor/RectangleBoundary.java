package com.jed.actor;

import org.lwjgl.opengl.GL11;

import com.jed.util.Rectangle;
import com.jed.util.Vector;

public class RectangleBoundary extends Boundary {

	private Rectangle rectangle;
	
	public RectangleBoundary(Rectangle rectangle, Vector position){
		super(position, new Vector[]{});
		this.rectangle = rectangle;
	}
	
	@Override
	public double getRightBound() {
		return owner.position.x + position.x + rectangle.getWidth();
	}

	@Override
	public double getLeftBound() {
		return owner.position.x+position.x;
	}

	@Override
	public double getUpperBound() {
		return owner.position.y+position.y;
	}

	@Override
	public double getLowerBound() {
		return owner.position.y+position.y+rectangle.getHeight();
	}

	@Override
	public int getWidth() {
		return rectangle.getWidth();
	}

	@Override
	public int getHeight() {
		return rectangle.getHeight();
	}

	@Override
	public void draw() {
		//Bounding Box
	    GL11.glColor3f(1f,0,0);
	    
	    
	    GL11.glBegin(GL11.GL_LINE_LOOP);
	    owner.drawChildVertex2f(position.x, position.y);
	    owner.drawChildVertex2f(position.x+getWidth(), position.y);
	    owner.drawChildVertex2f(position.x+getWidth(), position.y+getHeight());
	    owner.drawChildVertex2f(position.x, position.y+getHeight());
	    GL11.glEnd();
	}
}
