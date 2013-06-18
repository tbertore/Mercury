package org.mercury.util;

import java.awt.geom.Point2D;

public class Vector2D {
	double x, y;

	public Vector2D() {

	}

	public Vector2D(Point2D.Float p1, Point2D.Float p2) {
		x = p2.x - p1.x;
		y = p2.y - p1.y;
	}

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public boolean isZero() {
		return x == 0d && y == 0d;
	}

	public double length() {
		return Math.sqrt(x * x + y * y);
	}

	public double lengthSq() {
		return x * x + y * y;
	}

	public void normalize() {
		double length = length();
		x /= length;
		y /= length;
	}

	public double dot(Vector2D other) {
		return x * other.x + y * other.y;
	}

	/**
	 * Returns 1 if other in front of this vector, 0 if perpendicular, or -1 if
	 * behind.
	 */
	public int sign(Vector2D other) {
		double dot = dot(other);
		return dot > 0 ? 1 : dot == 0 ? 0 : -1;
	}

	public Vector2D perp() {
		return new Vector2D(-y, x);
	}

	public double Distance(Vector2D other) {
		double xd = other.x - x;
		double yd = other.y - y;
		return Math.sqrt(xd * xd + yd * yd);
	}

	public double DistanceSq(Vector2D other) {
		double xd = other.x - x;
		double yd = other.y - y;
		return xd * xd + yd * yd;
	}

	public Vector2D reverse() {
		return new Vector2D(-x, -y);
	}
}
