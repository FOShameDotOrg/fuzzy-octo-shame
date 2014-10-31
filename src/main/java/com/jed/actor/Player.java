package com.jed.actor;

import com.jed.util.MapLoader;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import com.jed.state.GameMap;
import com.jed.state.State;
import com.jed.state.StateManager;
import com.jed.util.Util;
import com.jed.util.Vector;

public class Player extends Entity implements StateManager {
	
	public int height, width;
	public int xDir;
	
	//Texture(s)
	private final String TEXTURE_PATH = MapLoader.RESOURCES_DIRECTORY + "MEGA_MAN_SH.png";
	private Texture texture;
	
	//Player Direction
	public final int PLAYER_RIGHT = 1;
	public final int PLAYER_LEFT = 0;

	//Player States
	private PlayerState currentState;
	private PlayerState fallingState;
	private PlayerState idleState;
	private PlayerState walkingState;
	private PlayerState jumpingState;
	
	//Indicates the player is currently colliding with a map tile below it
	private boolean collideDown = false;
	
	//TODO: Friction should come from the individual map tiles or from the tileset
	private float friction = .046875f;
	private int jumpCount=0;
	
	private GameMap map;
	
	public Player(Vector position, int height, int width, GameMap map){
		
		//TODO: The Bounds should be scaled to the size of the player sprite so that
		//it can be scaled
		super(
			position, 
			new Vector(0,0), 
			new PolygonBoundary(
				new Vector(110,130), 
				new Vector[] {
					new Vector(0,0),
					new Vector(40,0),
					new Vector(40,120),
					new Vector(0,120)
				})
			);
		
		this.acceleration = .046875f;
		this.height = height;
		this.width = width;
		this.map = map;
		entered();
	}
	
	
	public void changeState(State state){
		currentState = (PlayerState) state;
		currentState.entered();
	}
	
	@Override
	public void entered(){
		
		texture = Util.loadTexture(TEXTURE_PATH);
		
		fallingState = new Falling();
		idleState = new Idle();
		walkingState = new Walking();
		jumpingState = new Jumping();
		
		changeState(fallingState);
	}
	
	@Override
	public void leaving() {}

	//Key press events
	public void keyPressEvent(){
		if(Keyboard.getEventKey()==Keyboard.KEY_SPACE && Keyboard.getEventKeyState()){
			if(jumpCount < 2|| position.y+height==map.height*map.tileHeight){
				movement.y = -8;
				jumpCount++;
				changeState(jumpingState);				
			}
		}
	}
	
	//Key Hold Events (walking etc)
	private void keyHoldEvent(){
		
		//Constant key "hold" events
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			if(movement.x<0){
				movement.x+=.5;
			}else{
				movement.x+=acceleration;
				xDir = PLAYER_RIGHT;
			}
		}else if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			if(movement.x > 0){
				movement.x -=.5;
			}else{
				movement.x-=acceleration;
				xDir = PLAYER_LEFT;
			}
		}else if(movement.x !=0){
			movement.x = movement.x - Math.min(Math.abs(movement.x), friction) * Math.signum(movement.x);
		}
		
		if(!Keyboard.isKeyDown(Keyboard.KEY_SPACE) && !currentState.falling){
			jumpCount=0;
		}
	}
	
	@Override
	public void update() {
		currentState.handleInput();
		
		if(!currentState.falling && !collideDown){
			changeState(fallingState);
		}
		collideDown = false;
		
		if(currentState.falling){
			movement.y += map.gravity;
		}
		
		position.x=this.position.x+movement.x;
		position.y=position.y + movement.y;
		
		currentState.update();
	}
	
	@Override
	public void draw() {
		currentState.draw();
		bounds.draw();
	}

	//TODO: Name this something else or refactor... indicates player has just landed on a tile
	//and to stop "Falling" (changes animation)
	@Override
	public void collideDown(Entity sEntity){
		collideDown = true;

	    if(currentState.falling){
			changeState(idleState);
		}
	}
	
	private abstract class PlayerState implements State{
		protected boolean falling;
		
		public PlayerState(){
			falling = false;
		}

		public abstract void handleInput();
		
		@Override
		public void drawChildVertex2f(float x, float y) {}
	}
	
	private class Falling extends PlayerState{

		public Falling() {
			this.falling = true;
		}

		@Override
		public void entered() {}
		
		@Override
		public void update() {
			//Player Landed on something
			if(movement.y==0){
				if(movement.x!=0){
					changeState(walkingState);
				}else{
					changeState(idleState);
				}
			}
		}
		
		@Override
		public void leaving() {}


		@Override
		public void handleInput() {
			keyHoldEvent();
		}
		
		@Override
		public void draw() {
			Color.white.bind();
			texture.bind();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBegin(GL11.GL_QUADS);
			if(xDir==PLAYER_LEFT){
				GL11.glTexCoord2f(.25f,.5f);map.drawChildVertex2f(position.x+width, position.y);
				GL11.glTexCoord2f(.3125f,.5f);map.drawChildVertex2f(position.x,position.y);
				GL11.glTexCoord2f(.3125f,1);map.drawChildVertex2f(position.x,position.y+height);
				GL11.glTexCoord2f(.25f,1);map.drawChildVertex2f(position.x+width,position.y+height);
			}else{
				GL11.glTexCoord2f(.25f,.5f);map.drawChildVertex2f(position.x,position.y);
				GL11.glTexCoord2f(.3125f,.5f);map.drawChildVertex2f(position.x+width,position.y);
				GL11.glTexCoord2f(.3125f,1);map.drawChildVertex2f(position.x+width,position.y+height);
				GL11.glTexCoord2f(.25f,1);map.drawChildVertex2f(position.x,position.y+height);
			}
			GL11.glEnd();
			GL11.glDisable(GL11.GL_TEXTURE_2D);
		}

	}
	
	private class Jumping extends Falling{
		
		float[] animation = {.0625f, .125f, .1875f, .25f, .3125f, .375f, .4375f};
		float frameWidth = .0625f;
		int frame, ticks;
		
		public Jumping() {
			this.falling = true;
		}
		
		@Override
		public void entered() {
			frame = 0;
			ticks = 0;
		}
		
		@Override
		public void update() {
			ticks++;
			if(ticks%16==0){
				frame=frame==animation.length-1?frame:frame+1;
			}
			
			super.update();
		}

		@Override
		public void draw() {
			Color.white.bind();
			texture.bind();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBegin(GL11.GL_QUADS);
			if(xDir!=PLAYER_LEFT){
				GL11.glTexCoord2f(animation[frame]-frameWidth,.5f);map.drawChildVertex2f(position.x,position.y);
				GL11.glTexCoord2f(animation[frame],.5f);map.drawChildVertex2f(position.x+width,position.y);
				GL11.glTexCoord2f(animation[frame],1);map.drawChildVertex2f(position.x+width,position.y+height);
				GL11.glTexCoord2f(animation[frame]-frameWidth,1);map.drawChildVertex2f(position.x,position.y+height);
			}else{
				GL11.glTexCoord2f(animation[frame]-frameWidth,.5f);map.drawChildVertex2f(position.x+width,position.y);
				GL11.glTexCoord2f(animation[frame],.5f);map.drawChildVertex2f(position.x,position.y);
				GL11.glTexCoord2f(animation[frame],1);map.drawChildVertex2f(position.x,position.y+height);
				GL11.glTexCoord2f(animation[frame]-frameWidth,1);map.drawChildVertex2f(position.x+width,position.y+height);
			}
			GL11.glEnd();
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			
		}

	}

	private class Idle extends PlayerState{
		
		@Override
		public void entered() {}
		
		@Override
		public void update() {
			if(movement.y!=0){
				changeState(fallingState);
			}else if(movement.x!=0){
				changeState(walkingState);
			}
		}
		
		@Override
		public void leaving() {}

		@Override
		public void handleInput() {
			keyHoldEvent();
		}
		
		@Override
		public void draw() {
			Color.white.bind();
			texture.bind();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBegin(GL11.GL_QUADS);

			if(xDir==PLAYER_LEFT){
				GL11.glTexCoord2f(0,0);map.drawChildVertex2f(position.x+width,position.y);
				GL11.glTexCoord2f(.0625f,0);map.drawChildVertex2f(position.x,position.y);
				GL11.glTexCoord2f(.0625f,.5f);map.drawChildVertex2f(position.x,position.y+height);
				GL11.glTexCoord2f(0,.5f);map.drawChildVertex2f(position.x+width,position.y+height);
			}else{
				GL11.glTexCoord2f(0,0);map.drawChildVertex2f(position.x,position.y);
				GL11.glTexCoord2f(.0625f,0);map.drawChildVertex2f(position.x+width,position.y);
				GL11.glTexCoord2f(.0625f,.5f);map.drawChildVertex2f(position.x+width,position.y+height);
				GL11.glTexCoord2f(0,.5f);map.drawChildVertex2f(position.x,position.y+height);
			}
			GL11.glEnd();
			GL11.glDisable(GL11.GL_TEXTURE_2D);
		}

	}	
	
	private class Walking extends PlayerState{

		float[] animation = {.125f, .1875f, .25f, .3125f, .375f, .4375f, .5f, .5625f, .625f, .6875f, .75f};
		float frameWidth = .0625f;
		int frame, ticks;
		
		@Override
		public void entered() {
			frame = 0;
			ticks = 0;
		}
		
		@Override
		public void leaving() {}	
		
		@Override
		public void handleInput() {
			keyHoldEvent();
		}	
		
		@Override
		public void update() {
			ticks++;
			if(ticks%8==0){
				frame=frame==animation.length-1?1:frame+1;
			}
			
			if(movement.x==0){
				changeState(idleState);
			}
		}

		@Override
		public void draw() {
			Color.white.bind();
			texture.bind();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBegin(GL11.GL_QUADS);
			if(movement.x>0){
				GL11.glTexCoord2f(animation[frame]-frameWidth,0);map.drawChildVertex2f(position.x,position.y);
				GL11.glTexCoord2f(animation[frame],0);map.drawChildVertex2f(position.x+width,position.y);
				GL11.glTexCoord2f(animation[frame],.5f);map.drawChildVertex2f(position.x+width,position.y+height);
				GL11.glTexCoord2f(animation[frame]-frameWidth,.5f);map.drawChildVertex2f(position.x,position.y+height);
			}else{
				GL11.glTexCoord2f(animation[frame]-frameWidth,0);map.drawChildVertex2f(position.x+width,position.y);
				GL11.glTexCoord2f(animation[frame],0);map.drawChildVertex2f(position.x,position.y);
				GL11.glTexCoord2f(animation[frame],.5f);map.drawChildVertex2f(position.x,position.y+height);
				GL11.glTexCoord2f(animation[frame]-frameWidth,.5f);map.drawChildVertex2f(position.x+width,position.y+height);
			}
			GL11.glEnd();
			GL11.glDisable(GL11.GL_TEXTURE_2D);
		}
	}

	@Override
	public void drawChildVertex2f(float x, float y) {
		map.drawChildVertex2f(position.x+x,position.y+y);
	}

}
