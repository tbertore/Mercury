package org.mercury.entity;

import org.mercury.Game;
import org.mercury.gfx.AnimationList;

/**
 * A controllable entity through keyboard input.
 *
 * @author Wyatt Bertorelli
 *
 */
public class Hero extends Entity {

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
	}

	/**
	 * Render the hero onto the screen.
	 */
	public void render() {
		animations.render(getX(), getY());
	}

}
