package com.smoothstack.matthewcrowell.commaseparatedstring;

import java.util.Arrays;

/**
 * Class to build a comma-separated String from an array of ints.
 *
 * @author matthew.crowell
 */
public class CommaSeparatedString {

	/**
	 * Take an array of ints and return a comma-separated String
	 * with 'e' prepended to even numbers and 'o' prepended to
	 * odd numbers.
	 *
	 * @param myInts int[], array to construct String from
	 * @return String, a comma-separated String
	 */
	public String buildString(int... myInts) {
		StringBuilder str = new StringBuilder();
		Arrays.stream(myInts).forEach(number -> {
			if (number % 2 == 0) {
				str.append("e" + number + ", ");
			} else {
				str.append("o" + number + ", ");
			}
		});
		str.setLength(str.length() - 2);
		return str.toString();
	}
}
