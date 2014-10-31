package com.jed.util;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Util {

	public static Texture loadTexture(String path){
		Texture texture = null;
		
		String type = path.substring(path.lastIndexOf('.')+1).toUpperCase();
		
		try {
			texture = TextureLoader.getTexture(type, ResourceLoader.getResourceAsStream(path));
			System.out.println("Texture loaded: "+texture);
			System.out.println(">> Image width: "+texture.getImageWidth());
			System.out.println(">> Image height: "+texture.getImageHeight());
			System.out.println(">> Texture width: "+texture.getTextureWidth());
			System.out.println(">> Texture height: "+texture.getTextureHeight());
			System.out.println(">> Texture ID: "+texture.getTextureID());
			System.out.println(">> Texture Alpha: " +texture.hasAlpha());
		} catch (IOException e) {
			System.out.println("An error occurred while loading texture");
			e.printStackTrace();
			System.exit(1);
		}
		return texture;
	}
	
	public static int getClosestPowerOfTwo(int value){
		int power = 2;
		while(true){
			if(value == power){
				return value;
			}else if(value > power){
				power*=2;
			}else{
				return power;
			}
		}
	}
	
}
