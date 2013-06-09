package org.mercury;

import org.lwjgl.input.Keyboard;
import org.mercury.entity.Hero;
import org.mercury.gfx.Camera;

public class Player {
	private Hero hero;
	private Camera camera;

	public Player(Hero hero, Camera camera) {
		this.hero = hero;
		this.camera = camera;
	}

	private void update() {
		// Get current state of keyboard.
		Keyboard.poll();
		// Update position.
		hero.update();
	}
}
