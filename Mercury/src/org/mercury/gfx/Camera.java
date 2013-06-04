package org.mercury.gfx;

/**
 * Implementation of the player's viewport in the world. Each player object has
 * exactly one camera.
 * 
 * @author tbertore
 * 
 */
public class Camera {
	public int x, y;

	public void setTarget(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void update() {

	}
}
