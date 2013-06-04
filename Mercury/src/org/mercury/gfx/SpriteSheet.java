package org.mercury.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * A class which represents a 2D image which is intended to be sliced into
 * smaller, equally sized 2D sprites. Spritesheets must be equal width and
 * height in pixels, and are loaded into memory ONLY upon loading sprites via
 * the ResourceManager.
 * 
 * @author tbertore
 * 
 */
public class SpriteSheet {

	private String path;
	public final int SIZE;
	public int[] pixels;
	public final int spriteSize;

	public SpriteSheet(String path, int size, int spriteSize) {
		this.path = path;
		this.SIZE = size;
		this.spriteSize = spriteSize;
		pixels = new int[SIZE * SIZE];
		try {
			BufferedImage img = ImageIO.read(SpriteSheet.class
					.getResource(path));
			int w = img.getWidth();
			int h = img.getHeight();
			img.getRGB(0, 0, w, h, pixels, 0, w);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
