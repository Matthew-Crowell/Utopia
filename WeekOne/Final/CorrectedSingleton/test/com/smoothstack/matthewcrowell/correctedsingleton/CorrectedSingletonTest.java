package com.smoothstack.matthewcrowell.correctedsingleton;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Class to test the Corrected Singleton object.
 *
 * @author matthew.crowell
 */
public class CorrectedSingletonTest {

	/**
	 * Test for CorrectedSingleton's getInstance method.
	 */
	@Test
	public void getInstance() {
		CorrectedSingleton mySingletonA = CorrectedSingleton.getInstance();
		CorrectedSingleton mySingletonB = CorrectedSingleton.getInstance();

		assertEquals(true, mySingletonB.equals(mySingletonA));
	}
}