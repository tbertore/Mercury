package org.mercury.world;

import org.mercury.gfx.Camera;
import org.mercury.gfx.Sprite;

public class Terrain {
	public static final int SIZE = 16;
	private static final int PATHING_NONE = 1, PATHING_GROUND = 2,
			PATHING_AIR = 4, PATHING_WATER = 8;
	private int pathing;
	private int height;
	private int ulx, uly;
	private Sprite sprite;

	public Terrain(int ulx, int uly, int height, Sprite sprite) {
		this.ulx = ulx;
		this.uly = uly;
		this.height = height;
		this.sprite = sprite;
	}

	public int getHeight() {
		return height;
	}

	public void setPathing(int flags) {
		pathing = flags;
	}

	public void addPathing(int flag) {
		pathing |= flag;
	}

	public int ulx() {
		return ulx;
	}

	public int uly() {
		return uly;
	}

	public void render(Camera c, int xWorld, int yWorld) {
		sprite.render(c, xWorld, yWorld);
	}
}
