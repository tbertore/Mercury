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
	private boolean loop;
	/**
	 * @param frames
	 * An array of frames that compose an entity animation.
	 * @param ticksPerFrame
	 * The speed at which the animation will update.
	 * @param loop
	 * boolean that indicates if the animation should repeat.
	 */
	public Animation(Sprite [] frames, int ticksPerFrame, boolean loop) {
		this.frames = frames;
		this.ticksPerFrame = ticksPerFrame;
		this.framesPerAnimation = frames.length;
		this.loop = loop;
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
		// return early if the animation has died.
		if (frameIdx == framesPerAnimation && !loop){
			return;
		}
		// Increment tick.
		tickIdx++;
		// Update frame if enough time has elapsed.
		if (tickIdx == ticksPerFrame){
			tickIdx = 0;

			frameIdx++;
			// Wrap the animation if it's repeatable.
			if (frameIdx == framesPerAnimation){
				if (loop){
					frameIdx = 0;
				}
				else{
					return;
				}
			}
			// Increment to next frame in animation.
			currentFrame = frames[frameIdx];
		}
	}
	public Sprite getCurrentFrame(){
		return currentFrame;
	}
	/**
	 * Reset animation. Should be called every time new animation becomes live.
	 */
	public void initializeAnimation(){
		tickIdx = 0;
		frameIdx = 0;
		currentFrame = frames[0];
	}
}
