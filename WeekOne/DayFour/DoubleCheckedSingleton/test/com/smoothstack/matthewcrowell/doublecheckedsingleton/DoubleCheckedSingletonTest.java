package com.smoothstack.matthewcrowell.doublecheckedsingleton;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class to test the DoubleCheckedSingleton class.
 */
public class DoubleCheckedSingletonTest {
	private DoubleCheckedSingleton singletonOne;
	private DoubleCheckedSingleton singletonTwo;
	private Thread threadOne;
	private String threadOneReport;
	private Thread threadTwo;
	private String threadTwoReport;

	/**
	 * Function to prepare fixtures for test cases.
	 */
	@Before
	public void setUp() {
		try {
			threadOne = new Thread(() -> {
				singletonOne = DoubleCheckedSingleton.getInstance();
				threadOneReport = singletonOne.printMe();
			});
			threadOne.setName("Thread One");


			threadTwo = new Thread(() -> {
				singletonTwo = DoubleCheckedSingleton.getInstance();
				threadTwoReport = singletonTwo.printMe();
			});
			threadTwo.setName("Thread Two");

			threadOne.start();
			threadTwo.start();
			threadOne.join();
			threadTwo.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function to test the DoubleCheckedSingleton class's
	 * getInstance method.
	 */
	@Test
	public void getInstanceTest() {
		assertTrue(singletonOne == singletonTwo);
	}

	/**
	 * Function to test the DoubleCheckedSingleton class's
	 * printMe method.
	 */
	@Test
	public void printMeTest() {
		assertEquals(singletonOne.printMe(), singletonTwo.printMe());
		assertNotEquals(threadOneReport, threadTwoReport);
	}
}