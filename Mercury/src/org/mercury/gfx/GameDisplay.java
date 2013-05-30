package org.mercury.gfx;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.mercury.util.ImageUtils;

public class GameDisplay {
	private String title;
	private int w, h;
	private int scale = 1;
	
	private ByteBuffer[] icon;
	private ArrayList<DisplayListener> listeners;

	/**
	 * Creates a new GameDisplay with no listeners.
	 * 
	 */
	public GameDisplay() {
		listeners = new ArrayList<DisplayListener>();
	}

	/**
	 * Adds a listener which will be notified of any events performed by this
	 * GameDisplay.
	 * 
	 * @param listener
	 *            The listener to add.
	 */
	public void addDisplayListener(DisplayListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes a listener which will be notified of any events performed by this
	 * GameDisplay.
	 * 
	 * @param listener
	 *            The listener to remove.
	 */
	public void removeDisplayListener(DisplayListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Initialization method. Sets the resolution of this GameDisplay to the
	 * specified width and height. This method should be called before
	 * startRendering is called.
	 * 
	 * @param w
	 *            The width, in pixels, of the display.
	 * @param h
	 *            The height, in pixels, of the display.
	 */
	public void setResolution(int w, int h) {
		this.w = w;
		this.h = h;
	}

	/**
	 * Initialization method. Scales all pixel sizes by a constant factor. This
	 * method should be called before startRendering is called.
	 * 
	 * @param scale
	 *            The scaling of rendered pixels. (Default 1).
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}

	/**
	 * Initialization method. Sets the title of the window which will be created
	 * upon a call to startRendering.
	 * 
	 * @param title
	 *            A String specifying the title.
	 */
	public void setTitle(String title) {
		this.title = title;
		if (Display.isCreated()) {
			Display.setTitle(title);
		}
	}

	/**
	 * Sets the icons of the created window upon a call to startRendering.
	 * 
	 * @param path16
	 *            The path to an image to use for the window title bar's icon.
	 *            The image should be 16x16 pixels.
	 * @param path32
	 *            The path to an image to use for the taskbar's icon. The image
	 *            should be 32x32 pixels.
	 */
	public void setIcon(String path16, String path32) {
		ByteBuffer[] buffers = new ByteBuffer[2];
		try {
			buffers[0] = ImageUtils
					.toByteBuffer(ImageIO.read(new File(path16)));
			buffers[1] = ImageUtils
					.toByteBuffer(ImageIO.read(new File(path32)));
			icon = buffers;
		} catch (IOException e) {
			buffers = null;
			e.printStackTrace();
		}
	}

	/**
	 * Initializes OpenGL, creates a new window, and begins rendering. Upon
	 * finishing, any listeners will receive a callback to their onInitDone
	 * method.
	 * 
	 */
	public void launch() {
		try {
			Display.setDisplayMode(new DisplayMode(w, h));
			Display.setTitle(title);
			if (icon != null)
				Display.setIcon(icon);
			Display.create();
			glInit();
			for (DisplayListener listener : listeners) {
				listener.onInitDone();
			}
		} catch (LWJGLException e) {
			Display.destroy();
			for (DisplayListener listener : listeners) {
				listener.onWindowClosed();
			}
			e.printStackTrace();
		}
	}

	/**
	 * Destroys the current GameDisplay, terminating the active window.
	 * 
	 */
	public void destroy() {
		Display.destroy();
		for (DisplayListener listener : listeners) {
			listener.onWindowClosed();
		}
	}

	private void glInit() {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, w / scale, h / scale, 0, -1, 1);
	}

	public boolean render() {
		if (!Display.isCloseRequested()) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();
			for (DisplayListener listener : listeners) {
				listener.onFrameRender();
			}
			Display.update();
			return true;
		}
		else {
			return false;
		}
		
	}
}
