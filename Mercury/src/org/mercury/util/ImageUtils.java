package org.mercury.util;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

import static org.lwjgl.opengl.GL11.*;

/**
 * Contains various static utility methods for working with Images in the game
 * and OpenGL.
 * 
 * @author tbertorelli
 * 
 */
public class ImageUtils {
	private static int ARGB_BYTE_SIZE = 4, RGB_BYTE_SIZE = 3;

	/**
	 * Returns a new int array extracted from the specified array. Useful for
	 * pulling smaller 2d images from a larger one.
	 * 
	 * @param src
	 *            The source pixel array.
	 * @param srcWidth
	 *            The width of the source image in pixels.
	 * @param ulx
	 *            The upper left x-coordinate of the sub image.
	 * @param uly
	 *            The upper left y-coordinate of the sub image.
	 * @param w
	 *            The width of the sub image.
	 * @param h
	 *            The height of the sub image.
	 * @return A new int array containing the specified region of pixels.
	 */
	public static int[] extractSubArray(int[] src, int srcWidth, int ulx,
			int uly, int w, int h) {
		int[] pixels = new int[w * h];
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				pixels[x + y * w] = src[(ulx + x) + (uly + y) * srcWidth];
			}
		}

		return pixels;
	}

	/**
	 * Converts the specified int array to a ByteBuffer.
	 * 
	 * @param pixels
	 *            The array to convert.
	 * @return A new ByteBuffer containing the data in the specified array.
	 */
	public static ByteBuffer toByteBuffer(int[] pixels) {
		ByteBuffer buffer = BufferUtils.createByteBuffer(pixels.length
				* ARGB_BYTE_SIZE);

		for (int idx = 0; idx < pixels.length; idx++) {
			int pixel = pixels[idx];
			buffer.put((byte) ((pixel >> 16) & 0xFF));
			buffer.put((byte) ((pixel >> 8) & 0xFF));
			buffer.put((byte) ((pixel) & 0xFF));
			buffer.put((byte) ((pixel >> 24) & 0xFF));
		}

		buffer.flip();
		return buffer;
	}

	/**
	 * Converts the specified BufferedImage into a ByteBuffer. ByteBuffers are
	 * used for various OpenGL methods.
	 * 
	 * @param image
	 *            The image to convert.
	 * @return A ByteBuffer containing the pixel data of the image.
	 */
	public static ByteBuffer toByteBuffer(BufferedImage image) {
		int[] pixels = new int[image.getWidth() * image.getHeight()];

		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0,
				image.getWidth());
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth()
				* image.getHeight() * ARGB_BYTE_SIZE);

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = pixels[x + y * image.getWidth()];
				buffer.put((byte) ((pixel >> 16) & 0xFF));
				buffer.put((byte) ((pixel >> 8) & 0xFF));
				buffer.put((byte) ((pixel) & 0xFF));
				buffer.put((byte) ((pixel >> 24) & 0xFF));
			}
		}
		buffer.flip();

		return buffer;
	}

	/**
	 * Loads a specified image into OpenGL as a new texture.
	 * 
	 * @param image
	 *            The image to load as a texture.
	 * @return The texture ID of the texture that was loaded into OpenGL. Use
	 *         this ID to specify OpenGL to draw this texture.
	 */
	public static int loadTexture(BufferedImage image) {
		ByteBuffer buffer = toByteBuffer(image);
		int textureID = glGenTextures();

		glBindTexture(GL_TEXTURE_2D, textureID);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(),
				image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

		return textureID;
	}

	/**
	 * /** Loads a specified pixel data into OpenGL as a new texture.
	 * 
	 * @param pixels
	 *            The array of pixel data to generate the texture from.
	 * @param w
	 *            The height of the image represented by the pixel array.
	 * @param h
	 *            The width of the image represented by the pixel array.
	 * @return The texture ID of the texture that was loaded into OpenGL. Use
	 *         this ID to specify OpenGL to draw this texture.
	 */
	public static int loadTexture(int[] pixels, int w, int h) {
		ByteBuffer buffer = toByteBuffer(pixels);
		int textureID = glGenTextures();

		glBindTexture(GL_TEXTURE_2D, textureID);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, w, h, 0, GL_RGBA,
				GL_UNSIGNED_BYTE, buffer);

		return textureID;
	}
}
