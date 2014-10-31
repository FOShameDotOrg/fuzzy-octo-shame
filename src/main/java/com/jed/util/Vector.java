package com.jed.util;

public class Vector {

	public float x;
	public float y;

	public Vector() {
	}

	public Vector(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}

	public double distance(Vector o) {
		double axBx = (x - o.x);
		axBx *= axBx;
		double ayBy = (y - o.y);
		ayBy *= ayBy;

		return Math.sqrt(axBx + ayBy);
	}

	public Vector add(Vector o) {
		return new Vector(this.x + o.x, this.y + o.y);
	}

	public Vector subtract(Vector o) {
		return new Vector(o.x - this.x, o.y - this.y);
	}

	public double magnitude() {
		return Math.sqrt((this.x * this.x) + (this.y * this.y));
	}

	public double angle() {
		return Math.atan2(this.y, this.x);
	}

	public double dotProduct(Vector v1) {
		return x * v1.x + y * v1.y;
	}

	public Vector normalize() {
		Vector v2 = new Vector();

		double length = magnitude();
		if (length != 0) {
			v2.x = x / (float) length;
			v2.y = y / (float) length;
		}

		return v2;
	}

	public Vector scale(float scaleFactor) {
		Vector v2 = new Vector(x * scaleFactor, y * scaleFactor);
		return v2;
	}

	public Vector copy() {
		return new Vector(x, y);
	}

}
