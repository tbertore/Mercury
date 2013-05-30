package org.mercury.entity;


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
