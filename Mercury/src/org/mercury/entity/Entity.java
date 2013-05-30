package org.mercury.entity;

/**
 * An top level class which represents a game object that has a position in the
 * world. All entities have a unique id and are organized by an Entity Manager.
 * 
 * @author tbertorelli
 * 
 */
public class Entity {
	private int id;
	public int x, y;

	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int id() {
		return id;
	}

	public void update() {

	}

	public String toString() {
		return super.toString() + " at " + x + ", " + y;
	}
}
