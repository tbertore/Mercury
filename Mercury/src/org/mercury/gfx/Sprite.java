package org.mercury.gfx;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;
import org.mercury.util.ImageUtils;

/**
 * Class which represents a 2D image. Once loaded, each sprite maps to its
 * corresponding OpenGL texture via its texture ID.
 * 
 * @author tbertorelli
 * 
 */
public class Sprite {
	private int textureID = -1;
	private int w, h;

	public Sprite(int w, int h) {
		this.w = w;
		this.h = h;
	}

	public void load(String filename) {
		try {
			textureID = ImageUtils
					.loadTexture(ImageIO.read(new File(filename)));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load(SpriteSheet sheet, int x, int y) {
		textureID = ImageUtils.loadTexture(ImageUtils.extractSubArray(
				sheet.pixels, sheet.SIZE, x, y, w, h), w, h);
	}

	public void unload() {
		if (textureID != -1)
			GL11.glDeleteTextures(textureID);
	}

	public void render(int x, int y) {
		glBindTexture(GL_TEXTURE_2D, textureID);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2i(x, y);
		glTexCoord2f(1, 0);
		glVertex2i(x + w, y);
		glTexCoord2f(1, 1);
		glVertex2i(x + w, y + h);
		glTexCoord2f(0, 1);
		glVertex2i(x, y + h);
		glEnd();
	}

	public void render(Camera c, int xWorld, int yWorld) {
		glBindTexture(GL_TEXTURE_2D, textureID);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2i(xWorld - c.x, yWorld - c.y);
		glTexCoord2f(1, 0);
		glVertex2i(xWorld - c.x + w, yWorld - c.y);
		glTexCoord2f(1, 1);
		glVertex2i(xWorld - c.x + w, yWorld - c.y + h);
		glTexCoord2f(0, 1);
		glVertex2i(xWorld - c.x, yWorld - c.y + h);
		glEnd();
	}
}
