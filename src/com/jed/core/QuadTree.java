package com.jed.core;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.jed.actor.Entity;
import com.jed.util.Rectangle;
import com.jed.util.Vector;

public class QuadTree implements Displayable{

	private final int MAX_OBJECTS=2;
	private final int MAX_LEVELS = 5;
	
	private int level;
	private List<Entity> objects;
	private Rectangle rectangle;
	private QuadTree[] nodes;
	
	private Displayable parent;
	
	private Vector position;
	
	public QuadTree(Vector position, int level, Rectangle rectangle, Displayable parent){
		this.position = position;
		this.level = level;
		this.objects = new ArrayList<Entity>();
		this.rectangle = rectangle;
		this.nodes = new QuadTree[4];
		this.parent = parent;
	}
	
	public void clear(){
		objects.clear();
		for(int i = 0; i<nodes.length; i++){
			if(nodes[i]!=null){
				nodes[i].clear();
			}
			nodes[i] = null;
		}
	}
	
	public void retrieve(List<Entity> returnObjects, Entity o){
		int index = getIndex(o);
		if(index != -1){
			if(nodes[0]!=null){
				nodes[index].retrieve(returnObjects, o);
			}
			returnObjects.addAll(objects);
		}else{
			returnObjects.addAll(getObjects());
		}
	}
	
	public List<Entity> getObjects(){
		List<Entity> ret = new ArrayList<Entity>();
		ret.addAll(objects);
		if(nodes[0]!=null){
			ret.addAll(nodes[0].getObjects());
			ret.addAll(nodes[1].getObjects());
			ret.addAll(nodes[2].getObjects());
			ret.addAll(nodes[3].getObjects());
		}
		return ret;
	}
	
	private int getIndex(Entity o){
		int verticalMidpoint = (int) (position.x + (rectangle.getWidth()/2));
		int horizontalMidpoint = (int) (position.y + (rectangle.getHeight()/2));

		boolean topQuadrant = o.bounds.getNextWorldPosition().y + o.bounds.getHeight() < horizontalMidpoint;
		boolean bottomQuadrant = o.bounds.getNextWorldPosition().y > horizontalMidpoint;
		
		if(o.bounds.getNextWorldPosition().x + o.bounds.getWidth() < verticalMidpoint){
			if(topQuadrant){
				return 1;
			}else if(bottomQuadrant){
				return 2;
			}
		}else if(o.bounds.getNextWorldPosition().x > verticalMidpoint){
			if(topQuadrant){
				return 0;
			}else if(bottomQuadrant){
				return 3;
			}
		}
		
		return -1;
	}
	
	private void split(){
		int subWidth = Math.round(rectangle.getWidth()/2);
		int subHeight = Math.round(rectangle.getHeight()/2);
		int x = Math.round(position.x);
		int y = Math.round(position.y);
		
		Rectangle rect = new Rectangle(subWidth, subHeight);
		
		this.nodes[0] = new QuadTree(new Vector(x+subWidth,y), this.level+1,rect, parent);
		this.nodes[1] = new QuadTree(new Vector(x, y), this.level+1, rect, parent);
		this.nodes[2] = new QuadTree(new Vector(x, y + subHeight), this.level+1, rect, parent);
		this.nodes[3] = new QuadTree(new Vector(x + subWidth, y + subHeight), this.level+1, rect, parent);
	}
	
	public void insert(Entity o){
		if(nodes[0]!=null){
			int index = getIndex(o);
			
			if(index!=-1){
				this.nodes[index].insert(o);
				return;
			}
		}
		
		objects.add(o);
		
		if(objects.size()>MAX_OBJECTS && level < MAX_LEVELS){
			if(nodes[0]==null){
				split();
			}
			
			int i = 0;
			while(i<objects.size()){
				int index = getIndex(objects.get(i));
				if(index!=-1){
					nodes[index].insert(objects.remove(i));
				}else{
					i++;
				}
			}
		}
	}
	
	@Override
	public void draw(){
	    GL11.glColor3f(0.5f,0.5f,1.0f);
	    
	    GL11.glBegin(GL11.GL_LINE_LOOP);
	    
	    parent.drawChildVertex2f(position.x,position.y);
	    parent.drawChildVertex2f(position.x +rectangle.getWidth(),position.y);
	    parent.drawChildVertex2f(position.x+rectangle.getWidth(),position.y+rectangle.getHeight());
	    parent.drawChildVertex2f(position.x+rectangle.getWidth(),position.y+rectangle.getHeight());
	    parent.drawChildVertex2f(position.x,position.y+rectangle.getHeight());
		
	    GL11.glEnd();
	    
	    if(nodes[0]!=null){
	    	for(QuadTree eachNode: nodes){
	    		eachNode.draw();
	    	}
	    }
	}

	@Override
	public void drawChildVertex2f(float x, float y) {}
}
