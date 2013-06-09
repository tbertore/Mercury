package org.mercury;

import org.lwjgl.input.Keyboard;
import org.mercury.entity.Hero;
import org.mercury.gfx.Camera;

public class Player {
	private Hero hero;
	private Camera camera;

	public Player() {
		
	}
	
	public Player(Camera camera) {
		
	}
	
	public Player(Hero hero, Camera camera) {
		this.hero = hero;
		this.camera = camera;
	}
	
	public void setCamera(Camera c) {
		camera = c;
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
	
	private void update() {
		// Get current state of keyboard.
		Keyboard.poll();
		// Update position.
		hero.update();
	}
}
