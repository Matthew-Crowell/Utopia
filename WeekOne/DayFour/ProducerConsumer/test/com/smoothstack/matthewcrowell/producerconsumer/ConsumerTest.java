package com.smoothstack.matthewcrowell.producerconsumer;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class to test the Consumer class.
 *
 * @author matthew.crowell
 */
public class ConsumerTest {
	private Integer[] myInts;
	private Runnable consumer;
	private Thread thread;

	/**
	 * Function to prepare the fixtures for the test cases.
	 */
	@Before
	public void setUp() {
		try {
			myInts = new Integer[10];
			Arrays.fill(myInts, 10);
			consumer = new Consumer(myInts);
			thread = new Thread(consumer);
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function to test the Consumer class's override of the
	 * run method.
	 */
	@Test
	public void runTest() {
		try {
			assertTrue(thread.isAlive());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function to test the Consumer class's writeToArray
	 * method.
	 */
	@Test
	public void readFromArrayTest() {
		try {
			Integer[] testArray = new Integer[10];
			Arrays.fill(testArray, -1);
			Thread.sleep(2000);
			assertArrayEquals(testArray, myInts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}