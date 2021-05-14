package com.smoothstack.matthewcrowell.shapes;

/**
 * Class to demonstrate implementation of Shape interface.
 *
 * @author matthew.crowell
 */
public class Triangle implements Shape {

	private Integer base;
	private Integer height;

	/**
	 * Default constructor for Triangle objects; base and height
	 * default to 0.
	 */
	public Triangle() {
		this(0, 0);
	}

	/**
	 * Constructor for Triangle objects.
	 *
	 * @param base   int representing length of the base of the triangle
	 * @param height int representing the height of the triangle
	 */
	public Triangle(int base, int height) {
		this.base = base;
		this.height = height;
	}

	/**
	 * Calculate area of Triangle objects.
	 *
	 * @return Integer representing area of object
	 */
	@Override
	public Integer calculateArea() {
		return (base * height) / 2;
	}

	/**
	 * Display base, height, and area to standard output.
	 */
	@Override
	public void display() {
		System.out.println("Base: " + base + "\nHeight: " + height + "\nArea: " + calculateArea());

	}
}
