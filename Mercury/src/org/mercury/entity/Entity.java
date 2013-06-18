package org.mercury.entity;

import java.util.ArrayList;
import java.util.Arrays;

import org.mercury.gfx.AnimationList;
import org.mercury.gfx.Camera;
import org.mercury.util.BoundingBox;

/**
 * An top level class which represents a game object that has a position in the
 * world. All entities have a unique id and are organized by an Entity Manager.
 *
 * @author tbertore
 *
 */
public class Entity {
	private static int idCount = 0;
	private final int id;
	// The action entity is performing.
	protected String currentState = "walk_test";
	protected double x, y;
	protected int w, h, deltaNorth, deltaEast;
	protected double speed = 1;
	protected EntityManager owner;
	protected AnimationList animations;
	public Entity(int x, int y) {
		this.x = x;
		this.y = y;
		id = idCount++;
	}

	public Entity(int x, int y, AnimationList animations) {
		this.x = x;
		this.y = y;
		id = idCount++;
		this.animations = animations;
	}

	protected void init(AnimationList animations) {
		this.animations = animations;
	}

	public void update() {
		// Update to next frame if necessary.
		animations.update();
		// Move as far as possible
		propagatePosition();
	}

	public void render(Camera c) {
		if (animations != null)
			animations.render(c, getX(), getY());
	}

	/**
	 * Primitive collision algorithm that calculates farthest possible valid
	 * location in the direction of vectors deltaEast and deltaNorth. In a
	 * moving collision, the entity that is ticked first receives precedence.
	 *
	 * @author Wyatt Bertorelli
	 *
	 */
	private void propagatePosition() {
		// Calculate magnitude
		double magnitude = (Math.sqrt(Math.abs(deltaEast)
				+ Math.abs(deltaNorth)));
		// Return early if no direction specified.
		if (magnitude == 0)
			return;
		// Calculate velocity vectors(unit vector * speed).
		double deltaX = deltaEast * speed / magnitude;
		// y axis points towards south.
		double deltaY = -deltaNorth * speed / magnitude;
		// Calculate propagation centroid. +0.5 for round on cast.
		int propCX = (int) (x + (w / 2) + deltaX + 0.5);
		int propCY = (int) (y + (h / 2) + deltaY + 0.5);
		// Generate a bounding box at propagation dimensions.
		BoundingBox propBounds = new BoundingBox(propCX, propCY, w / 2, h / 2);
		// Return entities from that are inside propagation window.
		ArrayList<Entity> collisions = owner.findCollisions(propBounds);
		if (collisions == null) {
			// No collisions were detected in the delta, propagate freely.
			x = x + deltaX;
			y = y + deltaY;
		}
		// Preallocate temp arrays for all lower bounds.
		int[] xVals = new int[collisions.size()];
		int[] yVals = new int[collisions.size()];
		for (int j = 0; j < collisions.size(); j++) {
			// Get scalar distances between faces that intersect first.
			xVals[j] = (deltaEast > 0) ? Math.abs(collisions.get(j).getX() - (getX() + w)) :
				Math.abs(getX() - (collisions.get(j).getX() - collisions.get(j).getW()));
			// +y points towards south, so logic flips here.
			yVals[j] = (deltaNorth <= 0) ? Math.abs(collisions.get(j).getY() - (getY() + h)) :
				Math.abs(getY() - (collisions.get(j).getY() - collisions.get(j).getH()));
		}
		// Nearest values are our closest intersection.
		Arrays.sort(xVals);
		Arrays.sort(yVals);
		// Max move distance is delta or nearest object along axis,
		// whichever comes first. Revert signs post decision.
		x = (Math.abs(deltaX) > xVals[0]) ? (double) xVals[0] * deltaEast + x
				: deltaX + x;
		y = (Math.abs(deltaY) > yVals[0]) ? (double) yVals[0] * -deltaNorth + y
				: deltaY + y;
	}

	/**
	 * Sets the entities direction request on next update. Also sets animation
	 * if necessary. See propagatePosition.
	 *
	 * @param deltaEast
	 *            vector indicating direction across east - west axis.
	 * @param deltaNorth
	 *            vector indicating direction across north - south axis.
	 */
	public void setPropagationVector(int deltaEast, int deltaNorth) {
		this.deltaEast = deltaEast;
		this.deltaNorth = deltaNorth;
		// Find position entity is pointing towards.
		int orientation = calculatePointingAngle();
		// Update animations if necessary.
		if (orientation != animations.getOrientation()
				|| !currentState.equals(animations.getLiveName()))
			animations.setLive(currentState, orientation);
	}

	/**
	 * Calculate the pointing angle for proper animation update.
	 *
	 * @return orientation, in format of AnimationList
	 */
	private int calculatePointingAngle() {
		// If all tests fail, orientation is previous state.
		int orientation = animations.getOrientation();

		if (deltaNorth == 1)
			orientation = 1;
		if (deltaNorth == -1)
			orientation = 3;
		// east - west axis receives precedence if two direction commands are
		// issued.
		if (deltaEast == 1)
			orientation = 2;
		if (deltaEast == -1)
			orientation = 4;
		return orientation;
	}
	public int id() {
		return id;
	}

	public int getX() {
		return (int) (x + 0.5);
	}

	public int getY() {
		return (int) (y + 0.5);
	}

	public int getH() {
		return h;
	}

	public int getW() {
		return w;
	}
	@Override
	public String toString() {
		return super.toString() + " at " + x + ", " + y;
	}

	/**
	 * Set the owner for collision checking.
	 *
	 * @param e
	 *            Owner of this entity
	 */
	public void setOwner(EntityManager e) {
		owner = e;
	}
}
