package org.mercury;

/**
 * A class which represents the In-Game state. This class is responsible for
 * ticking the world's logic and rendering the current state of the world when
 * requested by the Game class via a call to execute.
 * 
 * @author tbertore
 * 
 */
public class GameplayState implements GameState {
	private ResourceManager res;
	@Override
	public void onFrameRender() {
		res.getAnimation("walk_test").getCurrentFrame().render(50, 50);
	}

	@Override
	public void onInitDone() {
		res = new ResourceManager();
		res.load("resources.xml");
		
	}

	@Override
	public void onWindowClosed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void enter(Game e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute(Game e) {
		res.getAnimation("walk_test").update();

	}

	@Override
	public void exit(Game e) {
		// TODO Auto-generated method stub

	}

}
