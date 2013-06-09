package org.mercury.entity;

import java.util.HashMap;

import org.lwjgl.util.Point;
import org.mercury.gfx.Camera;
import org.mercury.util.BoundingBox;
import org.mercury.util.QuadTree;
import org.mercury.world.World;

/**
 * A class which organizes entities and contains a mapping of unique entitiy
 * id's to their respective entity.
 *
 * @author tbertore
 *
 */
public class EntityManager {
	private HashMap<Integer, Entity> idToEntity;
	private QuadTree tree;
	public EntityManager(World world) {
		idToEntity = new HashMap<Integer, Entity>();
		tree = new QuadTree(new BoundingBox(new Point(0,0), world.getWidth(), world.getHeight()));
	}

	/**
	 * Registers the specified entity with this EntityManager and creates a new
	 * id mapping for it.
	 *
	 * @param e
	 *            The entity the register.
	 * @throws IllegalArgumentException
	 *             If an entity with this entities id was already registered.
	 */
	public void register(Entity e) {
		if (idToEntity.containsKey(e.id()))
			throw new IllegalArgumentException("Duplicate entity ids!");
		idToEntity.put(e.id(), e);
		tree.insert(e);
	}

	/**
	 * Removes the specified entity from this EntityManager and deletes its id
	 * mapping.
	 *
	 * @param e
	 *            The entity to remove.
	 */
	public void remove(Entity e) {
		idToEntity.remove(e);
		tree.remove(e);
	}

	/**
	 * Returns the registered entity with the specified id.
	 *
	 * @param id
	 *            The id of the requested entity.
	 * @return The entity registered with the specified id.
	 */
	public Entity getEntityFromId(int id) {
		return idToEntity.get(idToEntity);
	}

	public void update() {
		for (Entity e : idToEntity.values()) {
			int x = e.getX();
			int y = e.getY();
			e.update();
			if (e.getX() != x || e.getY() != y) {
				tree.reindex(e, x, y);
				System.out.println("Reindexin");
			}
		}

	}
	public void render(Camera c) {
		for (Entity e : idToEntity.values()) {
			e.render(c);
		}
	}
}
