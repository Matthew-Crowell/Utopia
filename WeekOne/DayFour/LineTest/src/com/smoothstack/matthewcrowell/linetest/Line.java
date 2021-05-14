package com.smoothstack.matthewcrowell.linetest;

/**
 * Class to represent a line on a graph.
 *
 * @author unattributed
 * @author matthew.crowell
 */
public class Line {

	/* section containing Line object's state */
	private double x0, y0, x1, y1;

	/**
	 * Constructor.
	 *
	 * @param x0 double, origin x-coordinate
	 * @param y0 double, origin y-coordinate
	 * @param x1 double, terminal x-coordinate
	 * @param y1 double, terminal y-coordinate
	 */
	public Line(double x0, double y0, double x1, double y1) {
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
	}

	/**
	 * Find the slope of the Line object.
	 *
	 * @return double representing the slope of the Line object.
	 */
	public double getSlope() {
		if (x1 == x0) {
			throw new ArithmeticException();
		}
		return (y1 - y0) / (x1 - x0);
	}

	/**
	 * Find the distance between the origin point and terminal point
	 * of the Line object.
	 *
	 * @return double, distance between origin and terminal point
	 */
	public double getDistance() {
		return Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0));
	}

	/**
	 * Find out if the Line object is parallel to another Line
	 * object.
	 *
	 * @param l Line, Line object to compare this Line to
	 * @return boolean, true if they are parallel
	 */
	public boolean parallelTo(Line l) {
		if (Math.abs(getSlope() - l.getSlope()) < .0001) {
			return true;
		} else {
			return false;
		}
	}
}