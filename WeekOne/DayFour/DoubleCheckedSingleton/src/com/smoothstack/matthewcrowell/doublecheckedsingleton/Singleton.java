package com.smoothstack.matthewcrowell.doublecheckedsingleton;

/**
 * Singleton class with double checking and synchronization in its
 * getInstance() method.
 *
 * @author matthew.crowell
 */
class DoubleCheckedSingleton {

	private static volatile DoubleCheckedSingleton instance;
	private static boolean instanceExists = false;

	/**
	 * Private constructor.
	 */
	private DoubleCheckedSingleton() {
		instance = this;
		instanceExists = true;
	}

	/**
	 * Gets the current singleton instance or creates one if none exists.
	 *
	 * @return DoubleCheckedSingleton instance
	 */
	public static DoubleCheckedSingleton getInstance() {
		if (!instanceExists) {
			synchronized (DoubleCheckedSingleton.class) {
				if (!instanceExists) {
					instance = new DoubleCheckedSingleton();
				}
			}
		}
		return instance;
	}

	public void printMe() {
		System.out.println("I am " + this + " and I'm running on " + Thread.currentThread().getName() + ".");
	}
}

/**
 * Class to demonstrate the functionality of the DoubleCheckedSingleton
 * class.
 *
 * @author matthew.crowell
 */
public class Singleton {

	/**
	 * Creates two threads which call the DoubleCheckedSingleton
	 * class's getInstance() method.
	 *
	 * @param args String[] of command line arguments
	 */
	public static void main(String[] args) {

		Thread threadOne = new Thread(() -> {
			DoubleCheckedSingleton singletonOne = DoubleCheckedSingleton.getInstance();
			singletonOne.printMe();
		});
		threadOne.setName("Thread One");


		Thread threadTwo = new Thread(() -> {
			DoubleCheckedSingleton singletonTwo = DoubleCheckedSingleton.getInstance();
			singletonTwo.printMe();
		});
		threadTwo.setName("Thread Two");

		threadOne.start();
		threadTwo.start();
	}
}