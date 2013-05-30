package org.mercury;

import org.mercury.gfx.DisplayListener;
import org.mercury.util.State;

/**
 * A class which implements behavior for the Game's state, e.g. Paused States,
 * Gameplay States, or Main Menu states. This class is intended to be used in
 * conjunction with the top level Game finite state machine.
 * 
 * @author tbertorelli
 * 
 */
public abstract class GameState implements DisplayListener, State<Game> {

	public abstract void onFrameRender();

	public abstract void onInitDone();

	public abstract void onWindowClosed();

	public abstract void enter(Game e);

	public abstract void execute(Game e);

	public abstract void exit(Game e);
}
