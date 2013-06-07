package org.mercury.world;

import java.util.ArrayList;

import org.mercury.Player;
import org.mercury.entity.EntityManager;

public class World {
	private EntityManager entityManager;
	private ArrayList<Terrain> tiles;
	private int tilesX, tilesY;
	public World(int tilesX, int tilesY) {
		tiles = new ArrayList<Terrain>(tilesX * tilesY);
		this.tilesX = tilesX;
		this.tilesY = tilesY;
		entityManager = new EntityManager(this);
	}
	public int getWidth() {
		return Terrain.SIZE * tilesX;
	}
	
	public int getHeight() {
		return Terrain.SIZE * tilesY;
	}
	public void addPlayer(Player player) {
		
	}
	public void update() {
		entityManager.update();
	}
}
