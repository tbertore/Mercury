package org.mercury.entity;

import java.util.HashMap;

/**
 * A class which organizes entities and contains a mapping of unique entitiy
 * id's to their respective entity.
 * 
 * @author tbertore
 * 
 */
public class EntityManager {
	private HashMap<Integer, Entity> idToEntity;

	public EntityManager() {
		idToEntity = new HashMap<Integer, Entity>();
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
}
