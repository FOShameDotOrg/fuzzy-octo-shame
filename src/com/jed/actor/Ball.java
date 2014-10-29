package com.jed.actor;

import com.jed.util.BasicShapeRenderer;
import com.jed.util.Vector;

public class Ball extends PhysicsEntity {
	
	private int segments;
	float r,g,b;
	
	public Ball(Vector displacement, Vector movement, Boundary bounds, int segments,float r,float g, float b){
		super(displacement, movement, bounds);
		
		this.r=r;
		this.g = g;
		this.b = b;
		
		this.segments = segments;
	}
	
	@Override
	public double mass(){
		return getRadius();
	}
	
	public int getRadius(){
		return ((CircleBoundary)bounds).radius;
	}
	
	@Override
	public void draw() {
		position = position.add(movement);
		BasicShapeRenderer.drawFilledCircle(
				position.x,
				position.y, 
				getRadius(), 
				segments,
				r,g,b);
		
	}
}
