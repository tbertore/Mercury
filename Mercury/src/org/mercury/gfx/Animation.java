package org.mercury.gfx;

/**
 * Class that implements the functionality of entity animations.
 * Contains fields that manage when the current frame is updated.
 *
 * @author Wyatt Bertorelli
 *
 */
public class Animation {
	private int frameIdx = 0, framesPerAnimation, tickIdx = 0;
	private Sprite currentFrame;
	private int ticksPerFrame;
	private Sprite [] frames;
	
	/**
	 * @param frames
	 * An array of frames that compose an entity animation.
	 * @param ticksPerFrame
	 * The speed at which the animation will update.
	 */
	public Animation(Sprite [] frames, int ticksPerFrame) {
		this.frames = frames;
		this.ticksPerFrame = ticksPerFrame;
		this.framesPerAnimation = frames.length;
		// It is implied every animation starts at index zero.
		currentFrame = frames[0];
	}
	/**
	 * Handles frame animation.
	 *
	 * The current frame will update once tickIdx equals
	 * ticksPerFrame.
	 */
	public void update(){
		// Increment tick
		tickIdx++;
		// Update frame if enough time has elapsed.
		if (tickIdx == ticksPerFrame){
			tickIdx = 0;
			// Increment frameIdx and wrap if necessary
			frameIdx++;
			if (frameIdx == framesPerAnimation){
				frameIdx = 0;
			}
			// Increment to next frame in animation.
			currentFrame = frames[frameIdx];
			
		}
	}
	public Sprite getCurrentFrame(){
		return currentFrame;
	}
}
