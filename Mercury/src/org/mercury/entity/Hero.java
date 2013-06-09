package org.mercury.entity;

import org.lwjgl.input.Keyboard;
import org.mercury.Game;
import org.mercury.gfx.Animation;
import org.mercury.gfx.AnimationList;

/**
 * A controllable entity through keyboard input.
 *
 * @author Wyatt Bertorelli
 *
 */
public class Hero extends Entity {
	protected double speed = 1;

	public Hero(int x, int y) {
		super(x, y);
		
		AnimationList animations = new AnimationList();
		String id = "walk_test";
		animations.add(id, Game.resouces().getAnimation(id),
				AnimationList.SOUTH);
		animations.add(id, Game.resouces().getAnimation(id),
				AnimationList.NORTH);
		animations.add(id, Game.resouces().getAnimation(id),
				AnimationList.WEST);
		animations.add(id, Game.resouces().getAnimation(id),
				AnimationList.EAST);
		animations.setLive(id, AnimationList.EAST);
		init(animations);
	}
	public Hero(int x, int y, AnimationList animations) {
		super(x, y, animations);
	}

	/**
	 * Update entity position from keyboard state.
	 */
	@Override
	public void update() {
		// Update animation frame.
		animations.update();
		// Check if keys are down. Expected that keyboard is polled before
		// function call.
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			y = y - speed;
			if (animations.shouldReinitialize(AnimationList.NORTH, "walk_test")) {
				animations.setLive("walk_test", AnimationList.NORTH);
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			x = x - speed;
			if (animations.shouldReinitialize(AnimationList.WEST, "walk_test")) {
				animations.setLive("walk_test", AnimationList.WEST);
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			y = y + speed;
			if (animations.shouldReinitialize(AnimationList.SOUTH, "walk_test")) {
				animations.setLive("walk_test", AnimationList.SOUTH);
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			x = x + speed;
			if (animations.shouldReinitialize(AnimationList.EAST, "walk_test")) {
				animations.setLive("walk_test", AnimationList.EAST);
			}
		}
	}

	/**
	 * Render the hero onto the screen.
	 */
	public void render() {
		animations.render(getX(), getY());
	}

}
