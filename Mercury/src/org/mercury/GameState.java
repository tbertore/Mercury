package org.mercury;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.util.Point;
import org.mercury.gfx.DisplayListener;
import org.mercury.gfx.GameDisplay;
import org.mercury.gfx.Sprite;
import org.mercury.util.State;

/**
 * A class which implements behavior for the Game's state, e.g. Paused States,
 * Gameplay States, or Main Menu states. This class is intended to be used in
 * conjunction with the Game finite state machine.
 * 
 * @author tbertorelli
 * 
 */
public abstract class GameState implements DisplayListener, State<Game> {
	private static long lastTime = 0;
	private static int frames = 0;
	private static int fps = 0;

	/**
	 * Returns the current FPS of the game's display.
	 * 
	 * @return
	 */
	public int getFPS() {
		return fps;
	}

	public abstract void onFrameRender();

	public abstract void onInitDone();

	public abstract void onWindowClosed();

	public abstract void enter(Game e);

	public abstract void execute(Game e);

	public abstract void exit(Game e);
}
