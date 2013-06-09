package org.mercury.entity;

import org.mercury.gfx.AnimationList;
import org.mercury.gfx.Camera;

/**
 * An top level class which represents a game object that has a position in the
 * world. All entities have a unique id and are organized by an Entity Manager.
 *
 * @author tbertore
 *
 */
public class Entity {
	private static int idCount = 0;
	private final int id;
	protected double x, y;
	protected AnimationList animations;

	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
		id = idCount++;
	}
	
	public Entity(int x, int y, AnimationList animations) {
		this.x = x;
		this.y = y;
		id = idCount++;
		this.animations = animations;
	}
	
	protected void init(AnimationList animations) {
		this.animations = animations;
	}

	public void update() {
		animations.update();
	}
	
	public void render(Camera c) {
		if (animations != null)
			animations.render(c, getX(), getY());
	}
	
	
	public int id() {
		return id;
	}

	public int getX() {
		return (int) (x + 0.5);
	}

	public int getY() {
		return (int) (y + 0.5);
	}
	@Override
	public String toString() {
		return super.toString() + " at " + x + ", " + y;
	}
}
