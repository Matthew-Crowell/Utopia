package com.smoothstack.matthewcrowell.producerconsumer;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class to test the Producer class.
 *
 * @author matthew.crowell
 */
public class ProducerTest {
	Integer[] myInts;
	private Runnable producer;
	private Thread thread;

	/**
	 * Function to prepare the fixtures for the test cases.
	 */
	@Before
	public void setUp() {
		try {
			myInts = new Integer[10];
			Arrays.fill(myInts, -1);
			producer = new Producer(myInts);
			thread = new Thread(producer);
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function to test the Producer class's override of the
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
	 * Function to test the Producer class's writeToArray
	 * method.
	 */
	@Test
	public void writeToArrayTest() {
		try {
			Integer[] testArray = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
			Thread.sleep(2000);
			assertArrayEquals(testArray, myInts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}