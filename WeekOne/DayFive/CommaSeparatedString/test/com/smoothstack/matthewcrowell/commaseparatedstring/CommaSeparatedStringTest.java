package com.smoothstack.matthewcrowell.commaseparatedstring;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Class to test CommaSeparatedString class methods.
 *
 * @author matthew.crowell
 */
public class CommaSeparatedStringTest {

	private CommaSeparatedString app;
	private int[] myInts;

	/**
	 * Setup the fixtures for the test cases.
	 */
	@Before
	public void setUpTest() {
		try {
			app = new CommaSeparatedString();
			myInts = new int[]{4, 5, 6, 7, 8, 9};
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test the CommaSpearatedString buildSpring method.
	 */
	@Test
	public void buildStringTest() {
		assertEquals("e4, o5, e6, o7, e8, o9", app.buildString(myInts));
	}
}