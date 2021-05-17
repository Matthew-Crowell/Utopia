package com.smoothstack.matthewcrowell.datetimeapi;

import org.junit.Before;
import org.junit.Test;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Class to test the DateTimeAPI class.
 *
 * @author matthew.crowell
 */
public class DateTimeAPITest {
	private DateTimeAPI app;

	/**
	 * Setup the fixtures for the test cases.
	 */
	@Before
	public void setUp() {
		try {
			app = new DateTimeAPI();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test the DateTimeAPI class's getBirthday method.
	 */
	@Test
	public void getBirthdayTest() {
		LocalDateTime testday = LocalDateTime.of(1983, Month.APRIL, 15, 10, 43);
		LocalDateTime birthday = app.getBirthday();

		assertEquals(testday, birthday);
	}

	/**
	 * Test the DateTimeAPI class's getPreviousThursday method.
	 */
	@Test
	public void getPreviousThursdayTest() {
		LocalDateTime testday = LocalDateTime.of(1983, Month.APRIL, 14, 10, 43);
		LocalDateTime thursday =
				app.getPreviousThursday(LocalDateTime.of(1983, Month.APRIL, 15, 10, 43));

		assertEquals(testday, thursday);
	}

	/**
	 * Test the DateTimeAPI class's compareTimeZoneObjects method.
	 */
	@Test
	public void compareTimeZoneObjectsTest() {
		ZoneId zi = ZoneId.systemDefault();
		ZoneOffset zo = ZonedDateTime.now().getOffset();

		assertNotEquals(zi, zo);
		assertEquals("The difference is that ZoneId tracks the rules that regulate " +
				"a time zone and is used to assist in converting between an Instant and a LocalDateTime, " +
				"while a ZoneOffset is a modifier from the UTC time zone.", app.compareTimeZoneObjects(zi, zo));
	}

	/**
	 * Test the DateTimeAPI class's zdtToInstant method.
	 */
	@Test
	public void zdtToInstantTest() {
		ZonedDateTime zdt = ZonedDateTime.now();
		ZoneOffset zo = zdt.getOffset();
		Instant instant = zdt.toInstant();
		assertEquals(instant.atOffset(zo) + "[America/Chicago]", app.zdtToInstant(zdt).atZone(ZoneId.of("America/Chicago")).toString());

	}

	/**
	 * Test the DateTimeAPI class's instantToZDT method.
	 */
	@Test
	public void instantToZDTTest() {
		ZonedDateTime zdt = ZonedDateTime.now();
		ZoneOffset zo = zdt.getOffset();
		Instant instant = zdt.toInstant();

		assertEquals(zdt, app.instantToZDT(instant));
	}

	/**
	 * Test the DateTimeAPI class's printMonthLength method.
	 */
	@Test
	public void printMonthLengthTest() {
		List<String> testList = new ArrayList<>();
		testList.add("JANUARY of 2021 had 31 days.");
		testList.add("FEBRUARY of 2021 had 28 days.");
		testList.add("MARCH of 2021 had 31 days.");
		testList.add("APRIL of 2021 had 30 days.");
		testList.add("MAY of 2021 has 31 days.");
		testList.add("JUNE of 2021 will have 30 days.");
		testList.add("JULY of 2021 will have 31 days.");
		testList.add("AUGUST of 2021 will have 31 days.");
		testList.add("SEPTEMBER of 2021 will have 30 days.");
		testList.add("OCTOBER of 2021 will have 31 days.");
		testList.add("NOVEMBER of 2021 will have 30 days.");
		testList.add("DECEMBER of 2021 will have 31 days.");

		assertEquals(testList, app.printMonthLength(Year.of(2021)));
	}

	/**
	 * Test the DateTimeAPI class's printMondays method.
	 */
	@Test
	public void printMondaysTest() {
		List<LocalDate> testList = new ArrayList<>();
		testList.add(LocalDate.parse("2021-05-03"));
		testList.add(LocalDate.parse("2021-05-10"));
		testList.add(LocalDate.parse("2021-05-17"));
		testList.add(LocalDate.parse("2021-05-24"));
		testList.add(LocalDate.parse("2021-05-31"));
		assertEquals(testList, app.printMondays(Month.MAY));
	}

	/**
	 * Test the DateTimeAPI class's isFridayTheThirteenth method.
	 */
	@Test
	public void isFridayTheThirteenthTest() {
		LocalDateTime ldtFalse = LocalDateTime.of(2021, 5, 17, 0, 7);
		assertFalse(app.isFridayTheThirteenth(ldtFalse));
		LocalDateTime ldtTrue = LocalDateTime.of(2020, 11, 13, 12, 0);
		assertTrue(app.isFridayTheThirteenth(ldtTrue));
	}
}