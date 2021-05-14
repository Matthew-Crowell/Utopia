package com.smoothstack.matthewcrowell.shapes;

/**
 * Application to demonstrate use of interfaces.
 *
 * @author matthew.crowell
 */
public class Main {

	/**
	 * Instantiate classes implementing shapes and demonstrate
	 * their compliance with the contract of the shape
	 * interface.
	 *
	 * @param args string arguments received from the command line
	 */
	public static void main(String[] args) {
		try {
			Rectangle rect = new Rectangle(5);
			rect.display();

			Triangle tri = new Triangle(10, 8);
			tri.display();

			Circle circ = new Circle(7);
			circ.display();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}