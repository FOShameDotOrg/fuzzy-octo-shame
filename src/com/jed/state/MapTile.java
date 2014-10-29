package com.jed.state;

import org.lwjgl.opengl.GL11;

import com.jed.actor.Boundary;
import com.jed.actor.Entity;
import com.jed.util.Vector;

public class MapTile extends Entity {

	public static final int NO_TILE = 0;
	
	float glTexX, glTexY, glTexWidth, glTexHeight;
	int tileId;
	
	GameMap map;
	
	//TODO: TEMPORARY!
	public boolean colliding;
	public boolean evaluating;
	
	public MapTile(Vector position, Boundary bounds, float glTexX, float glTexY, float glTexWidth, float glTexHeight, int tileId, GameMap map) {
		super(position, new Vector(0,0), bounds);
		
		this.glTexX = glTexX;
		this.glTexY = glTexY;
		this.tileId = tileId;
		
		this.glTexWidth = glTexWidth;
		this.glTexHeight = glTexHeight;
		
		this.map = map;
	}
	
	public float getGlTexX() {
		return glTexX;
	}

	public void setGlTexX(float glTexX) {
		this.glTexX = glTexX;
	}

	public float getGlTexY() {
		return glTexY;
	}

	public void setGlTexY(float glTexY) {
		this.glTexY = glTexY;
	}

	public int getTileId() {
		return tileId;
	}

	public void setTileId(int tileId) {
		this.tileId = tileId;
	}
	
	@Override
	public void update() {}

	@Override
	public void draw() {
		//TODO: Tile collision coloring is temporary...
		if(colliding){
			GL11.glColor3f(0,0,1f);
		}else if(evaluating){
			GL11.glColor3f(1f,0,1f);
		}else{
			GL11.glColor3f(1,1,1);
		}
		

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(glTexX,glTexY);
			map.drawChildVertex2f(position.x, position.y);
			
			GL11.glTexCoord2f(glTexX+glTexWidth,glTexY);
			map.drawChildVertex2f(position.x+bounds.getWidth(),position.y);
			
			GL11.glTexCoord2f(glTexX+glTexWidth,glTexY+glTexHeight);
			map.drawChildVertex2f(position.x+bounds.getWidth(), position.y+bounds.getHeight());
			
			GL11.glTexCoord2f(glTexX,glTexY+glTexHeight);
			map.drawChildVertex2f(position.x,position.y+bounds.getHeight());
			
		GL11.glEnd();
//		}
	}

}
