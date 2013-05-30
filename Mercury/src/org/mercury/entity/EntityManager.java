package org.mercury.entity;

import java.util.HashMap;


public class EntityManager {
	private HashMap<Integer, Entity> idToEntity;
	
	public EntityManager() {
		idToEntity = new HashMap<Integer, Entity>();
	}
	
	public void register(Entity e) {
		if (idToEntity.containsKey(e.id()))
			throw new IllegalArgumentException("Duplicate entity ids!");
		idToEntity.put(e.id(), e);
	}
	
	public void remove(Entity e) {
		idToEntity.remove(e);
	}
	
	public Entity getEntityFromId(int id) {
		return idToEntity.get(idToEntity);
	}
}
