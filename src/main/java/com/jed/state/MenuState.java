package com.jed.state;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import com.jed.util.Vector;

public class MenuState extends GameState {

	private UnicodeFont font;
	
	private String daString;
	private Vector coords;
	
	public MenuState(GameStateManager manager) {
		super(manager);
	}

	public String getDaString() {
		return daString;
	}

	public void setDaString(String daString) {
		this.daString = daString;
	}

	public Vector getCoords() {
		return coords;
	}

	public void setCoords(Vector coords) {
		this.coords = coords;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void entered() {
		java.awt.Font awtFont = new java.awt.Font("Arial", java.awt.Font.PLAIN, 24);
		font = new UnicodeFont(awtFont);
		font.getEffects().add(new ColorEffect(java.awt.Color.RED));
	    font.addAsciiGlyphs();
	    try {
			font.loadGlyphs();
		} catch (SlickException e) {
			System.out.println("Failed to load Font!");
			e.printStackTrace();
		}
	}

	@Override
	public void leaving() {}

	@Override
	public void update() {}

	@Override
	public void draw() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		font.drawString(coords.x, coords.y, daString);    
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

}
