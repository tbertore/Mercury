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
	protected double x, y;
	protected int w, h;
	protected AnimationList animations;
	protected double speed = 1;
	protected EntityManager owner;
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
		animations.update();
	}

	public void render(Camera c) {
		if (animations != null)
			animations.render(c, getX(), getY());
	}

	/**
	 * Primitive collision algorithm that calculates a bounding box of all
	 * entities from last update to determine potential collision partners. In a
	 * moving collision, the entity that is ticked first receives precedence.
	 *
	 * @param deltaEast
	 *            vector determining movement across east - west axis. can be 3
	 *            values: 1, 0, -1.
	 * @param deltaNorth
	 *            vector determining movement across north - south axis. can be
	 *            3 values: 1, 0, -1.
	 */
	public void requestMove(int deltaEast, int deltaNorth) {
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
			xVals[j] = (deltaEast > 0) ? Math.abs(collisions.get(j).getX() - w
					- getX()) : Math.abs(collisions.get(j).getX()
					- collisions.get(j).getW() - getX());
			yVals[j] = (deltaNorth > 0) ? Math.abs(collisions.get(j).getY() - h
					- getY()) : Math.abs(collisions.get(j).getY()
					- collisions.get(j).getH() - getY());
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
