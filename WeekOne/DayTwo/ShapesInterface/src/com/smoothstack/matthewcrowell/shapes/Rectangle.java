package com.smoothstack.matthewcrowell.shapes;

/**
 * Class to demonstrate implementation of Shape interface.
 *
 * @author matthew.crowell
 */
public class Rectangle implements Shape {

	private Integer length;
	private Integer width;

	/**
	 * Default constructor for Rectangle objects; length
	 * and width both default to 0.
	 */
	public Rectangle() {
		this(0);
	}

	/**
	 * Constructor for square Rectangle objects.
	 *
	 * @param size int represents length and width
	 */
	public Rectangle(int size) {
		this(size, size);
	}

	/**
	 * Constructor for Rectangle objects.
	 *
	 * @param length int representing length
	 * @param width  int representing width
	 */
	public Rectangle(int length, int width) {
		this.length = length;
		this.width = width;
	}


	/**
	 * Calculate the area of the Rectangle object.
	 *
	 * @return Integer representing the area of the rectangle object
	 */
	@Override
	public Integer calculateArea() {
		return length * width;
	}

	/**
	 * Display length, width, and area of the object to standard output.
	 */
	@Override
	public void display() {
		System.out.println("Length: " + length + "\nWidth: " + width + "\nArea: " + calculateArea());
	}
}
