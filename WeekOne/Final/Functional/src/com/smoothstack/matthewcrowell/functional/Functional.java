package com.smoothstack.matthewcrowell.functional;

import java.util.List;

/**
 * Class to demonstrate solutions for assignments 2, 3, and 4
 * from the final assignment for week one.
 *
 * @author matthew.crowell
 */
public class Functional {

	/**
	 * Replace each element in a List of Integers with its
	 * rightmost digit.
	 *
	 * @param list List<Integer>, list to be modified
	 */
	public void rightMostDigit(List<Integer> list) {
		list.replaceAll(number -> number % 10);
	}

	/**
	 * Replace each element in a List of Integers with an
	 * Integer equal to the element multiplied by two.
	 *
	 * @param list, List<Integer>, list to be modified
	 */
	public void doubleEach(List<Integer> list) {
		list.replaceAll(number -> number * 2);
	}

	/**
	 * Replace any occurrence of the letter "x" in any
	 * String in a provided List of Strings.
	 *
	 * @param list, List<String>, list to be modified
	 */
	public void removeX(List<String> list) {
		list.replaceAll(string -> string.replace("x", ""));
		list.replaceAll(string -> string.replace("X", ""));
	}
}
