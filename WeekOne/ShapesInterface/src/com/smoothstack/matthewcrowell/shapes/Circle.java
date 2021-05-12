package com.smoothstack.matthewcrowell.shapes;

/**
 * Class to demonstrate implementation of Shape interface.
 *
 * @author matthew.crowell
 */
public class Circle implements Shape {

	private Integer radius;

	/**
	 * Default constructor for Circle objects; radius defaults
	 * to 0.
	 */
	public Circle() {
		this(0);
	}

	/**
	 * Constructor for Circle objects.
	 *
	 * @param size integer representing the circle's radius
	 */
	public Circle(int size) {
		this.radius = size;
	}

	/**
	 * Calculate the area of the circle.
	 *
	 * @return Integer representing area of the object
	 */
	@Override
	public Integer calculateArea() {

		return (int) ((radius * radius) * Math.PI);
	}

	/**
	 * Display radius and area of the object to standard output.
	 */
	@Override
	public void display() {
		System.out.println("Radius:  " + radius + "\nArea: " + calculateArea());

	}
}
