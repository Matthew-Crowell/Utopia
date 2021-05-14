package com.smoothstack.matthewcrowell.producerconsumer;

import java.util.Arrays;

/**
 * Class to coordinate the exchange of data between
 * a producer object and a consumer object.
 *
 * @author matthew.crowell
 */
public class Main {

	/**
	 * Instantiate data, producer, and consumer objects,
	 * and run producer and consumer on separate threads.
	 *
	 * @param args String[] of command line arguments
	 */
	public static void main(String[] args) {
		Integer[] myArray = new Integer[10];
		Arrays.fill(myArray, -1);

		Runnable producer = new Producer(myArray);
		Thread producerThread = new Thread(producer);

		Runnable consumer = new Consumer(myArray);
		Thread consumerThread = new Thread(consumer);

		producerThread.start();
		consumerThread.start();
		System.out.println("Main thread complete");
	}
}

/**
 * Class to producer Integers for an Integer[].
 */
class Producer implements Runnable {

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

/**
 * Class to consume Integers from an Integer[] data object.
 */
class Consumer implements Runnable {

	private final Integer[] array;

	/**
	 * Constructor that initializes Integer[] data object, array.
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