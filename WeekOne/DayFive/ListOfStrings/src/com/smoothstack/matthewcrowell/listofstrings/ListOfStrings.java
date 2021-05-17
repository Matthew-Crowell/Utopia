package com.smoothstack.matthewcrowell.listofstrings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListOfStrings {

	public static void main(String[] args) {
		ListOfStrings app = new ListOfStrings();
		System.out.println("List of strings having a length of three and beginning with 'a':");
		System.out.println(app.listStrings("All", "these", "are", "okay", "and", "some", "even", "match."));
		System.out.println();

		List<String> strings = new ArrayList<>();
		strings.add("All");
		strings.add("these");
		strings.add("are");
		strings.add("okay");
		strings.add("and");
		strings.add("some");
		strings.add("even");
		strings.add("match");

		System.out.println("With argument of List<String>: List of strings having a length of three and beginning with 'a':");
		System.out.println(app.listStrings(strings));
		System.out.println();

	}

	public List<String> listStrings(String... strings) {
		List<String> matchingStrings = new ArrayList<>();
		Arrays.stream(strings).forEach((string) -> {
			if (string.length() == 3 && string.charAt(0) == 'a') {
				matchingStrings.add(string);
			}
		});
		return matchingStrings;
	}

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
