package com.smoothstack.matthewcrowell.shapes;

/**
 * Class to demonstrate use of interface.
 *
 * @author matthew.crowell
 */
public interface Shape {
	/**
	 * Calculate the area covered by implementing object.
	 *
	 * @return Integer representing the area of the implementing object
	 */
	public Integer calculateArea();

	/**
	 * Display the area and contributing values to standard output.
	 */
	public void display();
}
