package com.smoothstack.matthewcrowell.producerconsumer;

/**
 * Class to consume Integers from an Integer[] data object.
 *
 * @author matthew.crowell
 */
public class Consumer implements Runnable {

	private final Integer[] array;

	/**
	 * Constructor that initializes Integer[] data object, array.
	 *
	 * @param array Integer[] data object, shared with paired Producer(s)
	 */
	public Consumer(Integer[] array) {
		this.array = array;
	}

	/**
	 * Executes the readFromArray() method when start() is called
	 * on its thread object.
	 */
	@Override
	public void run() {
		readFromArray();
	}

	/**
	 * Reads Integers from an Integer[] data object, replaces
	 * them with -1 to indicate the Integer has been consumed,
	 * and adjusts consumption rate when an already-consumed
	 * value is encountered.
	 */
	private void readFromArray() {
		System.out.println("Consumption started.");
		for (int i = 0; i < 1000; i++) {
			synchronized (array[i % array.length]) {
				if (array[i % array.length] != -1) {
					array[i % array.length] = -1;
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
		System.out.println("Consumption complete.");
	}
}