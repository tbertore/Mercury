package org.mercury;

import org.mercury.util.StateMachine;
import org.mercury.gfx.GameDisplay;

/**
 * A top level finite state machine class which controls the states that the
 * game is in, e.g. Pause states, Gameplay states, or Menu states.
 * 
 * @author tbertorelli
 * 
 */
public class Game extends StateMachine<Game> {
	private static final int width = 800;
	private static final int height = 600;
	private static final String name = "Mercury";
	private GameDisplay display;

	/**
	 * Creates a new Game and GameDisplay object according to static parameters.
	 * 
	 */
	public Game() {
		super();
		super.setOwner(this);
		display = new GameDisplay();
		display.setIcon("test16.png", "test32.png");
		display.setTitle(name);
		display.setResolution(width, height);
		display.startRendering();
	}

	public static void main(String[] args) {
		new Game();
	}

}
