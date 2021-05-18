package com.smoothstack.matthewcrowell.listofstrings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class to create a list of strings matching specific criteria.
 *
 * @author matthew.crowell
 */
public class ListOfStrings {

	/**
	 * Create a list of strings that begin with 'a' and are exactly 3 characters in length from an
	 * existing String[].
	 *
	 * @param strings List<String> of strings to be sorted
	 * @return List<String> of Strings that match criteria
	 */
	public List<String> listStrings(String... strings) {
		List<String> matchingStrings = new ArrayList<>();
		Arrays.stream(strings).forEach((string) -> {
			if (string.length() == 3 && string.charAt(0) == 'a') {
				matchingStrings.add(string);
			}
		});
		return matchingStrings;
	}

	/**
	 * Create a list of strings that begin with 'a' and are exactly 3 characters in length from an
	 * existing list of Strings.
	 *
	 * @param strings List<String> of strings to be sorted
	 * @return List<String> of Strings that match criteria
	 */
	public List<String> listStrings(List<String> strings) {
		List<String> matchingStrings = new ArrayList<>();
		strings.forEach((string) -> {
			if (string.length() == 3 && string.charAt(0) == 'a') {
				matchingStrings.add(string);
			}
		});

		return matchingStrings;
	}
}
