package org.mercury.gfx;
import java.util.HashMap;
import java.util.ArrayList;
/**
 * Container class for entities that can perform multiple animations. Implementation
 * allows users to set the animation by assigned name and orientation.
 * @author Wyatt Bertorelli
 *
 */
public class AnimationList {
	private final ArrayList<HashMap<String, Animation>> mapList = new ArrayList<HashMap<String, Animation>>(4);
	private Animation liveAnimation;
	private int orientation;

	public static final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;

	public AnimationList(){
		// generate map in each direction
		HashMap<String, Animation> north = new HashMap<String, Animation>();
		HashMap<String, Animation> south = new HashMap<String, Animation>();
		HashMap<String, Animation> east = new HashMap<String, Animation>();
		HashMap<String, Animation> west = new HashMap<String, Animation>();
		// Add maps to list so they remain on the class.
		mapList.add(0, north);
		mapList.add(1, east);
		mapList.add(2, south);
		mapList.add(3, west);
	}

	/**
	 * @param animationName
	 * Name of the animation.
	 * @param animation
	 * new animation to add.
	 * @param orientation
	 * Direction the animation is oriented.
	 */
	public AnimationList(String animationName, Animation animation, Integer orientation){
		// generate map in each direction
		HashMap<String, Animation> north = new HashMap<String, Animation>();
		HashMap<String, Animation> south = new HashMap<String, Animation>();
		HashMap<String, Animation> east = new HashMap<String, Animation>();
		HashMap<String, Animation> west = new HashMap<String, Animation>();
		// Add maps to list so they remain on the class.
		mapList.add(0, north);
		mapList.add(1, east);
		mapList.add(2, south);
		mapList.add(3, west);

		// Add animation for correct direction.
		mapList.get(orientation).put(animationName, animation);
	}

	/**
	 * Overloaded constructor that allows for multiple animations to be on object initialization.
	 * @param animationName
	 * Array of animation names to add into the list
	 * @param animation
	 * Array of animations to add to the list
	 * @param orientation
	 * Array of directions to set animation orientation
	 */
	public AnimationList(String [] animationName, Animation [] animation, Integer [] orientation){

		if (animation.length != animationName.length || animation.length != orientation.length){
			// throw exception in case class was set up improperly.
			throw new IndexOutOfBoundsException("Input arrays are not same length.");
		}
		// generate map in each direction
		HashMap<String, Animation> north = new HashMap<String, Animation>();
		HashMap<String, Animation> south = new HashMap<String, Animation>();
		HashMap<String, Animation> east = new HashMap<String, Animation>();
		HashMap<String, Animation> west = new HashMap<String, Animation>();
		// Add maps to list so they remain on the class.
		mapList.add(0, north);
		mapList.add(1, east);
		mapList.add(2, south);
		mapList.add(3, west);
		for (int i = 0; i < animation.length; i++){
			mapList.get(orientation[i]).put(animationName[i], animation[i]);
		}
	}

	/**
	 * Add additional animations to the list.
	 *
	 * @param animationName
	 * name of the animation.
	 * @param animation
	 * new animation to add.
	 * @param orientation
	 * Direction the animation is oriented.
	 */
	public void add(String animationName, Animation animation, Integer orientation){
		// Add a unique id for the animation.
		mapList.get(orientation).put(animationName, animation);
	}

	/**
	 * Set new live animation to render.
	 * @param animationName
	 * The name of the action to set as active. Throws an exception if the animation does
	 * not exist.
	 * @param orientation
	 * direction to orient animation.
	 */
	public void set(String animationName, Integer orientation){
		liveAnimation = mapList.get(orientation).get(animationName);
		this.orientation = orientation;
		if (liveAnimation == null){
			// Throw exception if no animation is returned.
			throw new IndexOutOfBoundsException("Animation not in list!");
		}
		// Make sure animation starts at first frame.
		liveAnimation.initializeAnimation();
	}
	public Animation getLive(){
		return liveAnimation;
	}
	public Integer getOrientation(){
		return orientation;
	}
}
