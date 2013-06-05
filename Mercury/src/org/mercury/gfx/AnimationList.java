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
	private Integer animationCount = 0, animationIdx = 0;
	private Map<String, Integer> animationMap;
	private Animation [] animationGroup;

	/**
	 * @param animationName
	 * Name of the animation.
	 * @param animation
	 * new animation to add.
	 */
	public AnimationList(String animationName, Animation animation){
		animationMap = new HashMap<String, Integer>();
		animationMap.put(animationName, animationCount);
		// Default size of animation list to 10.
		animationGroup = new Animation[10];
		animationGroup[animationCount] = animation;
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
		animationMap = new HashMap<String, Integer>();
		Integer animationsToAdd = animation.length;
		animationGroup = new Animation[animationsToAdd];
		for (int i = 0; i < animationsToAdd; i++){
			animationMap.put(animationName[i], animationCount);
			animationGroup[animationCount] = animation[i];
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
		animationMap.put(animationName, animationCount);
		// Add the animation to the list.
		animationGroup[animationCount] = animation;
		// Increment counter.
		animationCount++;
		if (animationCount == animationGroup.length){
			reallocateAnimationList();
		}
	}

	/**
	 * Make room for more entity animations.
	 */
	private void reallocateAnimationList(){
		Animation [] tempAnimationGroup = new Animation[animationCount];
		// Save animations already in list.
		for (int i = 0; i < animationCount; i++){
			 tempAnimationGroup[i] = animationGroup[i];
		}
		// reallocate space.
		animationGroup = new Animation[animationCount + 10];
		// Add animations back in.
		for (int i = 0; i < animationCount; i++){
			animationGroup[i] = tempAnimationGroup[i];
		}
	}

	/**
	 * Set new live animation to render.
	 * @param animationName
	 * The name of the action to set as active. Throws an exception if the animation does
	 * not exist.
	 */
	public void setActiveAnimation(String animationName){
		animationIdx = animationMap.get(animationName);
		if (animationIdx == null){
			throw new IndexOutOfBoundsException("MERCURY:ANIMATION, Animation not in list!");
		}
		// Make sure animation starts at first frame.
		animationGroup[animationIdx].initializeAnimation();
	}
	public Animation getActiveAnimation(){
		return animationGroup[animationIdx];
	}
}
