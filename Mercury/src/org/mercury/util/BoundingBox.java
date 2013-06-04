package org.mercury.util;

import org.lwjgl.util.Point;

/**
 * A class which represents a rectangular spatial region.
 * 
 * @author tbertore
 * 
 */
public class BoundingBox {
	public final int cx, cy;
	public final int xhd, yhd;

	/**
	 * Creates a new BoundBox which represents a rectangular spatial region.
	 * 
	 * @param cx
	 *            The x coordinate of the center of the region.
	 * @param cy
	 *            The y coordinate of the center of the region.
	 * @param xhd
	 *            Half of the width of the rectangle to span.
	 * @param yhd
	 *            Half of the height of the rectangle to span.
	 */
	public BoundingBox(int cx, int cy, int xhd, int yhd) {
		this.cx = cx;
		this.cy = cy;
		this.xhd = xhd;
		this.yhd = yhd;
		if (xhd <= 0 || yhd <= 0)
			throw new IllegalArgumentException("xhd and yhd must be > 0!");
	}

	public BoundingBox(Point nw, int width, int height) {
		this.cx = nw.getX() + width / 2;
		this.cy = nw.getY() + height / 2;
		this.xhd = width / 2;
		this.yhd = height / 2;
	}

	/**
	 * Checks if this BoundingBox contains the specified point.
	 * 
	 * @param x
	 *            The x coordinate of the point.
	 * @param y
	 *            The y coordinate of the point.
	 * @return true if the point is contained, otherwise false.
	 */
	public boolean contains(int x, int y) {
		return !(x < cx - xhd || x > cx + xhd || y < cy - yhd || y > cy + yhd);
	}

	/**
	 * Checks if this BoundingBox intersects another BoundingBox.
	 * 
	 * @param other
	 *            The BoundingBox to check for collisions against.
	 * @return true if the other BoundingBox intersects this BoundingBox,
	 *         otherwise false.
	 */
	public boolean intersects(BoundingBox other) {
		int x1 = cx - xhd;
		int x2 = cx + xhd;
		int y1 = cy - yhd;
		int y2 = cy + yhd;
		int ox1 = other.cx - other.xhd;
		int ox2 = other.cx + other.xhd;
		int oy1 = other.cy - other.yhd;
		int oy2 = other.cy + other.yhd;

		return !(x1 > ox2 || x2 < ox1 || y1 > oy2 || y2 < oy1);
	}
}