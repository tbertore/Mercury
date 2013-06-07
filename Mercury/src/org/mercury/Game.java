package org.mercury;

import org.mercury.util.StateMachine;
import org.mercury.gfx.GameDisplay;

/**
 * A top level finite state machine class which ticks the game's logic,
 * rendering, and controls the states that the game is in, e.g. Pause states,
 * Gameplay states, or Menu states.
 * 
 * @author tbertore
 * 
 */
public class Game extends StateMachine<Game> implements Runnable {
	private static final int width = 800;
	private static final int height = 600;
	private static int updatesPerSecond = 60;
	private static final String name = "Mercury";
	private GameDisplay display;
	private boolean running;

	/**
	 * Creates a new Game and GameDisplay object according to static parameters.
	 * 
	 */
	public Game() {
		super();
		setOwner(this);

		display = new GameDisplay();
		display.setIcon("resources/test16.png", "resources/test32.png");
		display.setTitle(name);
		display.setResolution(width, height);
		setCurrentState(new GameplayState());
		display.addDisplayListener((GameplayState) getCurrentState());
	}

	public void run() {
		gameLoop();
	}

	/**
	 * The main game loop. This loop is dynamic, it always attempts to achieve
	 * exactly 60 logical ticks per second. Any extra time is put into rendering
	 * the game. If the game cannot achieve 60 logical ticks per second,
	 * rendering ticks are sacrificed to attempt to make up the required time.
	 * 
	 */
	public void gameLoop() {
		final int nsPerSecond = 1000000000;
		final double nsPerTick = nsPerSecond / updatesPerSecond;
		
		long time;
		long lastTime = System.nanoTime();
		long lastFPSTime = System.nanoTime();
		double dt = 0;
		int frames = 0, updates = 0;
		
		display.launch();
		running = true;

		while (running) {
			time = System.nanoTime();
			dt += (time - lastTime) / nsPerTick;
			while (dt >= 1) {
				update();
				updates++;
				dt--;
			}
			render();
			frames++;

			if (time - lastFPSTime > nsPerSecond) {
				System.out
						.println("Updates: " + updates + " Frames: " + frames);
				updates = 0;
				frames = 0;
				lastFPSTime += nsPerSecond;
			}
			lastTime = time;
			try {
				Thread.sleep(1);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mercury.util.StateMachine#update() 
	 * Propagates the tick to the
	 * current State object of this game. This method attempts to always run 60
	 * times a second.
	 */
	public void update() {
		super.update();

	}

	/**
	 * Renders the game in its window. This method will run up to 1000 times a
	 * second (Because of a built in 1 ms thread sleep in the main game loop).
	 * The method may be called less than 1000 times a second if more time is
	 * needed to tick the games logic.
	 * 
	 */
	public void render() {
		// If the render method of the display returns false, the display window
		// was closed. For now, this terminates the game.
		if (!display.render()) {
			running = false;
		}
	}

	/**
	 * Creates a new Game and executes it in another thread.
	 * 
	 * @param args
	 *            No command line args yet.
	 */
	public static void main(String[] args) {
		new Thread(new Game()).start();
	}

}
