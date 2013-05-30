package org.mercury.gfx;

/**
 * Interface which allows a class to respond to events from the game's display.
 * 
 * @author tbertorelli
 * 
 */
public interface DisplayListener {
	public void onWindowClosed();

	public void onFrameRender();

	public void onInitDone();
}
