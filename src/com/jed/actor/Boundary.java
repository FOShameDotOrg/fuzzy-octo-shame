package com.jed.actor;

import com.jed.core.Displayable;
import com.jed.util.Vector;

public abstract class Boundary implements Displayable{
	
	public Entity owner;
	public Vector position;
	public Vector[] verticies;
	
	public abstract double getRightBound();
	public abstract double getLeftBound();
	public abstract double getUpperBound();
	public abstract double getLowerBound();
	public abstract int getWidth();
	public abstract int getHeight();

	public Boundary(Vector position, Vector[] verticies){
		this.verticies = verticies;
		this.position = position;
	}
	
	public Vector getWorldPosition(){
		return owner.position.add(position);
	}
	
	public Vector getNextWorldPosition(){
		return getWorldPosition().add(owner.movement);
	}
	
	@Override
	public void draw() {}
	
	@Override
	public void drawChildVertex2f(float x, float y) {}
}
