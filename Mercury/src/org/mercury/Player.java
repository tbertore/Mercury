package org.mercury;

import org.mercury.entity.Hero;
import org.mercury.gfx.Camera;

public class Player {
	private Hero hero;
	private Camera camera;
	private InputHandler input;

	public Player() {

	}

	public Player(Camera camera) {

	}

	public Player(Hero hero, Camera camera) {
		this.hero = hero;
		this.camera = camera;
		input = new InputHandler(camera);
	}

	public void setCamera(Camera c) {
		camera = c;
		input = new InputHandler(c);
	}

	public void setHero(Hero h) {
		hero = h;
	}
	public Camera getCamera() {
		return camera;
	}
	public Hero getHero() {
		return hero;
	}

	public void update() {
		// Get issued commands
		input.update();
		// Issue move direction.
		hero.setPropagationVector(input.deltaEast, input.deltaNorth);
	}
}
