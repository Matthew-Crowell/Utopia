package com.smoothstack.matthewcrowell.doublecheckedsingleton;

/**
 * Singleton class with double checking and synchronization in its
 * getInstance() method.
 *
 * @author matthew.crowell
 */
public class DoubleCheckedSingleton {

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

	/**
	 * returns a String identifying the object and the thread it is running on.
	 *
	 * @return String identifying the object and the thread it is running on
	 */
	public String printMe() {
		return "I am " + this + " and I'm running on " + Thread.currentThread().getName() + ".";
	}
}