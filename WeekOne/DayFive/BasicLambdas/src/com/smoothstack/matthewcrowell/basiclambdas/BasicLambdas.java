package com.smoothstack.matthewcrowell.basiclambdas;

import java.util.Arrays;
import java.util.Locale;

/**
 * Class to demonstrate the use of lambda functions.
 *
 * @author matthew.crowell
 */
public class BasicLambdas {

	/**
	 * Sort a String[] by length.
	 *
	 * @param strArray String[], array to be sorted
	 */
	public void sortByLength(String[] strArray) {
		Arrays.sort(strArray, (strOne, strTwo) -> strOne.length() - strTwo.length());
	}

	/**
	 * Sort a String[] by length, descending.
	 *
	 * @param strArray String[], array to be sorted
	 */
	public void reverseSortByLength(String[] strArray) {
		Arrays.sort(strArray, (strOne, strTwo) -> strTwo.length() - strOne.length());
	}

	/**
	 * Sort a String[] alphabetically by only the first character.
	 *
	 * @param strArray String[], array to be sorted
	 */
	public void sortByFirstCharacter(String[] strArray) {
		Arrays.sort(strArray, (strOne, strTwo) -> strOne.charAt(0) - strTwo.charAt(0));
	}

	/**
	 * Sort a String[] by the presence of the letter "e" in its elements.
	 *
	 * @param strArray String[], array to be sorted
	 */
	public void sortByPresenceOfE(String[] strArray) {
		Arrays.sort(strArray, (String strOne, String strTwo) -> {
			int result = 0;

			if (strOne.toUpperCase(Locale.ROOT).contains("E")) {
				result--;
			}

			if (strTwo.toUpperCase(Locale.ROOT).contains("E")) {
				result++;
			}

			return result;
		});
	}

	/**
	 * Sort a String[] only by the presence of the letter 'e' using a helper function.
	 *
	 * @param strArray String[], array to be sorted
	 */
	public void sortByPresenceOfEWithHelper(String[] strArray) {
		Arrays.sort(strArray, (strOne, strTwo) -> Utils.sortByPresenceOfE(strOne, strTwo));
	}
}

/**
 * Helper class for BasicLambdas.
 */
class Utils {

	/**
	 * Helper function to sort a String[] by the presence of
	 * the letter 'e'.
	 *
	 * @param strOne String, object to compare strTwo against
	 * @param strTwo String, object to compare strOne against
	 * @return int, result of comparison between strOne and strTwo
	 */
	public static int sortByPresenceOfE(String strOne, String strTwo) {
		int result = 0;

		if (strOne.toUpperCase(Locale.ROOT).contains("E")) {
			result--;
		}

		if (strTwo.toUpperCase(Locale.ROOT).contains("E")) {
			result++;
		}

		return result;
	}
}