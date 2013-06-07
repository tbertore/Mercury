package org.mercury;

import org.mercury.entity.Entity;
import org.mercury.gfx.Camera;

public class Player {
	private Entity hero;
	private Camera camera;
	
	public Player(Entity hero, Camera camera) {
		this.hero = hero;
		this.camera = camera;
	}
}
