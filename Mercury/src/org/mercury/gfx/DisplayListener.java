package org.mercury.gfx;

/**
 * Interface which allows a class to respond to events from the game's display.
 * 
 * @author tbertore
 * 
 */
public interface DisplayListener {
	public void onWindowClosed();

	public void onFrameRender();

	public void onInitDone();
}
