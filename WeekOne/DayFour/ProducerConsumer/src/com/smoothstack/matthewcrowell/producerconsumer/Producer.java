package com.smoothstack.matthewcrowell.producerconsumer;

/**
 * Class to producer Integers for an Integer[].
 *
 * @author matthew.crowell
 */
public class Producer implements Runnable {

	private final Integer[] array;

	/**
	 * Constructor that initializes Integer[] array.
	 *
	 * @param array Integer[] data object, shared with paired Consumer(s)
	 */
	public Producer(Integer[] array) {
		this.array = array;
	}

	/**
	 * Executes the writeToArray( method when start()
	 * is called on its thread object.
	 */
	@Override
	public void run() {
		writeToArray();
	}

	/**
	 * Fills the data object, array, with Integers,
	 * and adjusts production rate when an unconsumed
	 * value is encountered.
	 */
	public void writeToArray() {
		System.out.println("Production started.");
		for (int i = 0; i < 1000; i++) {
			synchronized (array[i % array.length]) {
				if (array[i % array.length] == -1) {
					array[i % array.length] = i;
				} else {
					i--;
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

		System.out.println("Production complete.");
	}
}
