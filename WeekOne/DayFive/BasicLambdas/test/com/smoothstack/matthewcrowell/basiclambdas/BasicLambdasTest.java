package com.smoothstack.matthewcrowell.basiclambdas;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Class to test BasicLambdas class.
 *
 * @author matthew.crowell
 */
public class BasicLambdasTest {
	private BasicLambdas app;
	private String[] strArray;

	/**
	 * Prepares the fixtures for the test sequences.
	 */
	@Before
	public void setUp() {
		try {
			strArray = new String[5];
			app = new BasicLambdas();
			strArray[0] = "Rurik";
			strArray[1] = "Zahir al-Din Muhammad Babur";
			strArray[2] = "Thucydides";
			strArray[3] = "Cináed mac Ailpin";
			strArray[4] = "Ardashir V";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test BasicLambdas sortByLength() method.
	 */
	@Test
	public void sortByLengthTest() {
		String[] adjustedArray = new String[]{strArray[0], strArray[2], strArray[4], strArray[3], strArray[1]};

		app.sortByLength(strArray);

		assertArrayEquals(strArray, adjustedArray);
	}

	/**
	 * Test BasicLambdas reverseSortByLength() method.
	 */
	@Test
	public void reverseSortByLengthTest() {
		String[] adjustedArray = new String[]{strArray[1], strArray[3], strArray[2], strArray[4], strArray[0]};

		app.reverseSortByLength(strArray);

		assertArrayEquals(strArray, adjustedArray);
	}

	/**
	 * Test BasicLambdas sortByFirstCharacter() method.
	 */
	@Test
	public void sortByFirstCharacterTest() {
		String[] adjustedArray = new String[]{strArray[4], strArray[3], strArray[0], strArray[2], strArray[1]};

		app.sortByFirstCharacter(strArray);

		assertArrayEquals(strArray, adjustedArray);
	}

	/**
	 * Test BasicLambdas sortByPresenceOfE() method.
	 */
	@Test
	public void sortByPresenceOfETest() {
		String[] adjustedArray = new String[]{strArray[2], strArray[3], strArray[0], strArray[1], strArray[4]};

		app.sortByPresenceOfE(strArray);

		assertArrayEquals(strArray, adjustedArray);
	}

	/**
	 * Test BasicLambdas sortByPresenceOfEWithHelper() method.
	 */
	@Test
	public void sortByPresenceOfEWithHelperTest() {
		String[] adjustedArray = new String[]{strArray[2], strArray[3], strArray[0], strArray[1], strArray[4]};

		app.sortByPresenceOfEWithHelper(strArray);

		assertArrayEquals(strArray, adjustedArray);
	}
}