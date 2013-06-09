package org.mercury;

import org.mercury.entity.Hero;
import org.mercury.gfx.Camera;
import org.mercury.world.World;

/**
 * A class which represents the In-Game state. This class is responsible for
 * ticking the world's logic and rendering the current state of the world when
 * requested by the Game class via a call to execute.
 *
 * @author tbertore
 *
 */
public class GameplayState implements GameState {
	private World world;
	private Game game;
	@Override
	public void onFrameRender() {
		world.render(game.getPlayer().getCamera());
	}

	@Override
	public void onInitDone() {
		game.resources.load("resources/resources.xml");
		game.getPlayer().setCamera(new Camera());
		game.getPlayer().setHero(new Hero(0, 0));
		world.addPlayer(game.getPlayer());
	}

	@Override
	public void onWindowClosed() {

	}

	@Override
	public void enter(Game game) {
		this.game = game;
		world = new World(100, 100);
	}

	@Override
	public void execute(Game e) {
		world.update();
	}

	@Override
	public void exit(Game e) {

	}

}
