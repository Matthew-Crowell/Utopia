package com.smoothstack.matthewcrowell.listofstrings;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ListOfStringsTest {
	private ListOfStrings app;
	private List<String> stringList;
	private String[] stringArray;
	private List<String> testList;

	@Before
	public void setUp() {
		try {
			app = new ListOfStrings();
			stringList = new ArrayList<>();
			Collections.addAll(stringList, "All", "these", "are", "okay", "and", "some", "even", "match.");
			stringArray = new String[]{"All", "these", "are", "okay", "and", "some", "even", "match."};
			testList = new ArrayList<>();
			Collections.addAll(testList, "are", "and");
		} catch (Exception e) {

		}
	}

	@Test
	public void listStringsArrayTest() {
		assertTrue(testList.equals(app.listStrings(stringArray)));
	}

	@Test
	public void ListStringsListTest() {
		assertEquals(testList, app.listStrings(stringList));
	}
}