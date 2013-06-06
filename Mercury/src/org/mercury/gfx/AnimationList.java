package org.mercury.gfx;
import java.util.Map;
import java.util.HashMap;
/**
 * Container class for entities that can perform multiple animations. Implementation
 * allows users to set the animation by assigned name.
 * @author Wyatt Bertorelli
 *
 */
public class AnimationList {
	private Integer animationCount = 0;
	private Map<String, Animation> animationMap;
	private Animation liveAnimation;

	/**
	 * @param animationName
	 * Name of the animation.
	 * @param animation
	 * new animation to add.
	 */
	public AnimationList(String animationName, Animation animation){
		animationMap = new HashMap<String, Animation>();
		animationMap.put(animationName, animation);
		// Default size of animation list to 10.
		animationCount++;
	}

	/**
	 * Overloaded constructor that allows for multiple animations to be on object initialization.
	 * @param animationName
	 * List of animation names to add into the list
	 * @param animation
	 * List of animations to add to the list
	 */
	public AnimationList(String [] animationName, Animation [] animation){
		animationMap = new HashMap<String, Animation>();

		if (animation.length != animationName.length){
			// throw exception in case class was set up improperly.
			throw new IndexOutOfBoundsException("M:GFX:AL, Input arrays are not same length.");
		}
		for (int i = 0; i < animation.length; i++){
			animationMap.put(animationName[i], animation[i]);
			animationCount++;
		}
	}

	/**
	 * Add additional animations to the list.
	 *
	 * @param animationName
	 * name of the animation.
	 * @param animation
	 * new animation to add.
	 */
	public void addAnimation(String animationName, Animation animation){
		// Add a unique id for the animation.
		animationMap.put(animationName, animation);
		// Increment counter.
		animationCount++;
	}

	/**
	 * Set new live animation to render.
	 * @param animationName
	 * The name of the action to set as active. Throws an exception if the animation does
	 * not exist.
	 */
	public void setActiveAnimation(String animationName){
		liveAnimation = animationMap.get(animationName);
		if (liveAnimation == null){
			// Throw exception if no animation is returned.
			throw new IndexOutOfBoundsException("M:GFX:AL, Animation not in list!");
		}
		// Make sure animation starts at first frame.
		liveAnimation.initializeAnimation();
	}
	public Animation getActiveAnimation(){
		return liveAnimation;
	}
}
