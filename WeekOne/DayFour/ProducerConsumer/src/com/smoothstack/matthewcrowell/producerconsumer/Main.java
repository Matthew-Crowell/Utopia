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