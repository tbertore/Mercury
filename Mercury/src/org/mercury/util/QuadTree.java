package org.mercury.util;

import java.util.ArrayList;
import java.util.Iterator;

import org.mercury.entity.Entity;

/**
 * Data structure which allows efficient O(log(n)) lookup of entities in the
 * world. This structure must have its entries kept up to date with any moving
 * entities each game tick in order for spatial queries to be correct.
 * 
 * @author tbertorelli
 * 
 */
public class QuadTree {
	// A QuadTree which can subdivide will have no more than this many entities
	// in its array.
	private static int NODE_CAPACITY = 4;

	private QuadTree head;
	private QuadTree nw, ne, sw, se;
	private ArrayList<Entity> entities;
	private BoundingBox boundary;

	/**
	 * Creates a new QuadTree spanning over the specified rectangular area. A
	 * QuadTree is a data structure which allows efficient O(log(n)) spatial
	 * queries of entities.
	 * 
	 * @param bounds
	 *            The area for this QuadTree to span.
	 * @param head
	 *            The head (top level) of the entire QuadTree.
	 */
	private QuadTree(BoundingBox bounds, QuadTree head) {
		this.head = head;
		boundary = bounds;
		entities = new ArrayList<Entity>(NODE_CAPACITY);
	}

	/**
	 * Creates a new QuadTree spanning over the specified rectangular area. A
	 * QuadTree is a data structure which allows efficient O(log(n)) spatial
	 * queries of entities.
	 * 
	 * @param bounds
	 *            The area for this QuadTree to span.
	 */
	public QuadTree(BoundingBox bounds) {
		boundary = bounds;
		entities = new ArrayList<Entity>(NODE_CAPACITY);
	}

	/**
	 * Creates a new QuadTree spanning over the specified rectangular area. A
	 * QuadTree is a data structure which allows efficient O(log(n)) spatial
	 * queries of entities.
	 * 
	 * @param centerX
	 *            The x coordinate of the center of the rectangle to span.
	 * @param centerY
	 *            The y coordinate of the center of the rectangle to span.
	 * @param xRadius
	 *            Half of the width of the rectangle to span.
	 * @param yRadius
	 *            Half of the height of the rectangle to span.
	 * 
	 * @throws IllegalArgumentException
	 *             If xRadius or yRadius are less than 1.
	 */
	public QuadTree(int centerX, int centerY, int xRadius, int yRadius) {
		if (xRadius <= 0 || yRadius <= 0)
			throw new IllegalArgumentException("Radius must be > 0!");
		head = this;
		boundary = new BoundingBox(centerX, centerY, xRadius, yRadius);
		entities = new ArrayList<Entity>(NODE_CAPACITY);
	}

	/**
	 * Inserts an entity into this QuadTree, using its x and y position.
	 * 
	 * @param e
	 *            The entity to insert.
	 * @return True if the entity was successfully added to this QuadTree, or
	 *         false if the entity's position is not contained by this QuadTree.
	 */
	public boolean insert(Entity e) {
		if (!boundary.contains(e.x, e.y))
			return false;

		// Add the entity to this level if there is room, or if we cannot divide
		// anymore.
		if (entities.size() < NODE_CAPACITY || boundary.xhd == 1
				|| boundary.yhd == 1) {
			entities.add(e);
			return true;
		}

		if (nw == null)
			subDivide();
		if (nw.insert(e))
			return true;
		if (ne.insert(e))
			return true;
		if (sw.insert(e))
			return true;
		if (se.insert(e))
			return true;

		throw new RuntimeException("Could not fit entity?!");
	}

	/**
	 * Returns all the entities contained in a given rectangle.
	 * 
	 * @param rect
	 *            The rectangular area to search in.
	 * @return An ArrayList containing the entities found.
	 */
	public ArrayList<Entity> searchRect(BoundingBox rect) {
		ArrayList<Entity> found = new ArrayList<Entity>();

		if (!boundary.intersects(rect))
			return found;

		for (int idx = 0; idx < entities.size(); idx++) {
			if (rect.contains(entities.get(idx).x, entities.get(idx).y))
				found.add(entities.get(idx));
		}

		if (nw != null) {
			found.addAll(nw.searchRect(rect));
			found.addAll(ne.searchRect(rect));
			found.addAll(sw.searchRect(rect));
			found.addAll(se.searchRect(rect));
		}
		return found;

	}

	private void subDivide() {
		int cx = boundary.cx;
		int cy = boundary.cy;
		int xhd = boundary.xhd;
		int yhd = boundary.yhd;
		nw = new QuadTree(new BoundingBox((cx + (cx - xhd)) / 2,
				(cy + (cy - yhd)) / 2, xhd / 2, yhd / 2), head);
		sw = new QuadTree(new BoundingBox((cx + (cx - xhd)) / 2,
				(cy + (cy + yhd)) / 2, xhd / 2, yhd / 2), head);
		ne = new QuadTree(new BoundingBox((cx + (cx + xhd)) / 2,
				(cy + (cy - yhd)) / 2, xhd / 2, yhd / 2), head);
		se = new QuadTree(new BoundingBox((cx + (cx + xhd)) / 2,
				(cy + (cy + yhd)) / 2, xhd / 2, yhd / 2), head);

	}

	/**
	 * Reindexes the specified entity in the QuadTree to its current location if
	 * it exists. This is an O(log(n)) operation.
	 * 
	 * @param e
	 *            The entity to update the indexing for.
	 * @param prevX
	 *            The last x position of e before it was changed.
	 * @param prevY
	 *            The last y position of e before it was changed.
	 */
	public void reindex(Entity e, int prevX, int prevY) {
		if (!boundary.contains(prevX, prevY))
			return;
		Iterator<Entity> it = entities.iterator();
		while (it.hasNext()) {
			Entity found = it.next();
			if (e.equals(found)) {
				it.remove();
				head.insert(e);
				return;
			}
		}
		if (nw != null) {
			nw.reindex(e, prevX, prevY);
			ne.reindex(e, prevX, prevY);
			sw.reindex(e, prevX, prevY);
			se.reindex(e, prevX, prevY);
		}
	}

	public boolean remove(Entity e) {
		if (!boundary.contains(e.x, e.y))
			return false;
		Iterator<Entity> it = entities.iterator();
		while (it.hasNext()) {
			Entity found = it.next();
			if (e.equals(found)) {
				it.remove();
				return true;
			}
		}
		if (nw != null) {
			if (nw.remove(e))
				return true;
			if (ne.remove(e))
				return true;
			if (sw.remove(e))
				return true;
			if (se.remove(e))
				return true;
		}
		return false;
	}

	/**
	 * Checks if the specified entity is contained in this QuadTree.
	 * 
	 * @param e
	 *            The Entity to find.
	 * @return true if this QuadTree contains the specified entity, otherwise
	 *         false.
	 */
	public boolean contains(Entity e) {
		if (!boundary.contains(e.x, e.y))
			return false;
		if (entities.contains(e))
			return true;
		if (nw != null) {
			if (nw.contains(e))
				return true;
			if (ne.contains(e))
				return true;
			if (sw.contains(e))
				return true;
			if (se.contains(e))
				return true;
		}
		return false;
	}

	/**
	 * Reindexes all entities in this QuadTree based on their current positions.
	 * This is an O(nlog(n)) operation.
	 * 
	 */
	public void reindexAll() {
		Iterator<Entity> it = entities.iterator();
		ArrayList<Entity> removed = new ArrayList<Entity>();
		while (it.hasNext()) {
			Entity e = it.next();
			if (!boundary.contains(e.x, e.y)) {
				it.remove();
				System.out.println(e + " is in the wrong place!");
				removed.add(e);
			}
		}
		for (Entity e : removed) {
			System.out.println("Reinserting: " + e);
			head.insert(e);
		}
		if (nw != null) {
			nw.reindexAll();
			ne.reindexAll();
			sw.reindexAll();
			se.reindexAll();
		}
	}

	/**
	 * Debug method which prints the contents of this QuadTree.
	 * 
	 */
	public void print() {
		System.out.println("Quadtree cx: " + boundary.cx + ", cy: "
				+ boundary.cy + ", xhd: " + boundary.xhd + ", yhd: "
				+ boundary.yhd);
		for (Entity e : entities)
			System.out.println(e);
		if (nw != null)
			nw.print();
		if (ne != null)
			ne.print();
		if (sw != null)
			sw.print();
		if (se != null)
			se.print();
	}

	public String toString() {
		return "QuadTree cx: " + boundary.cx + ", cy: " + boundary.cy
				+ ", xhd: " + boundary.xhd + ", yhd: " + boundary.yhd;
	}
}
