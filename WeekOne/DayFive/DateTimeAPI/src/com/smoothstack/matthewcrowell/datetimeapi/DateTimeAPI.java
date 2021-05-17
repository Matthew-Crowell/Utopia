package com.smoothstack.matthewcrowell.datetimeapi;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to demonstrate use of Java's DateTime APIs.
 *
 * @author matthew.crowell
 */
public class DateTimeAPI {

	/**
	 * Get the LocalDateTime object for my birthday.
	 *
	 * @return LocalDateTime object for April 15, 1983 at 10:43am.
	 */
	public LocalDateTime getBirthday() {
		return LocalDateTime.of(1983, Month.APRIL, 15, 10, 43);
	}

	/**
	 * Get LocalDateTime object for Thursday preceding a given
	 * LocalDateTime object.
	 *
	 * @param ldt LocalDateTime object
	 * @return LocalDateTime object for Thursday before ldt
	 */
	public LocalDateTime getPreviousThursday(LocalDateTime ldt) {
		return ldt.with(TemporalAdjusters.previous(DayOfWeek.THURSDAY));
	}

	/**
	 * Compare ZoneId to ZoneOffset and print overview of differences.
	 */
	public String compareTimeZoneObjects(ZoneId zi, ZoneOffset zo) {
		System.out.println("ZoneId: " + zi);
		System.out.println("Zoned Date Time: " + zo);

		return "The difference is that ZoneId tracks the rules that regulate " +
				"a time zone and is used to assist in converting between an Instant and a LocalDateTime, " +
				"while a ZoneOffset is a modifier from the UTC time zone.";
	}

	/**
	 * Convert ZonedDateTime object to Instant object.
	 *
	 * @param zdt ZonedDateTime object to be converted
	 * @return Instant object created from zdt
	 */
	public Instant zdtToInstant(ZonedDateTime zdt) {
		return zdt.toInstant();
	}

	/**
	 * Convert Instant object to ZonedDateTime object.
	 *
	 * @param instant Instant object to convert
	 * @return ZonedDateTime created from instant
	 */
	public ZonedDateTime instantToZDT(Instant instant) {
		return ZonedDateTime.ofInstant(instant, ZoneId.of("America/Chicago"));
	}

	/**
	 * Print the length of each month for a given year.
	 *
	 * @param year Year for which each month's length will be calculated
	 */
	public List<String> printMonthLength(Year year) {
		StringBuilder verb = new StringBuilder();
		List<String> stringList = new ArrayList<>();

		for (Month month : Month.values()) {
			verb.setLength(0);
			YearMonth yearMonth = YearMonth.of(year.getValue(), month);
			verb.append(month.name() + " of " + year + " ");
			if (yearMonth.isBefore(YearMonth.now())) {
				verb.append("had");
			} else if (yearMonth.isAfter(YearMonth.now())) {
				verb.append("will have");
			} else {
				verb.append("has");
			}
			verb.append(" " + yearMonth.lengthOfMonth() + " days.");
			System.out.println(verb);

			stringList.add(verb.toString());
		}
		return stringList;
	}

	/**
	 * Display all the Mondays in a given month.
	 *
	 * @param month Month for which all the Mondays will be displayed
	 */
	public List<LocalDate> printMondays(Month month) {
		List<LocalDate> mondayList = new ArrayList<>();

		YearMonth yearMonth = YearMonth.of(Year.now().getValue(), month);
		for (int i = 1; i < yearMonth.lengthOfMonth(); i += 7) {
			LocalDate day = YearMonth.now().atDay(i);
			day = day.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
			mondayList.add(day);
			System.out.println(day);
		}
		return mondayList;
	}

	/**
	 * Check if a given LocalDateTime occurs on a Friday on the thirteenth
	 * day of the month.
	 *
	 * @param ldt LocalDateTime object to test
	 * @return Boolean true if ldt falls on Friday the 13th, false if not
	 */
	public Boolean isFridayTheThirteenth(LocalDateTime ldt) {
		Boolean result = false;
		if (ldt.getDayOfMonth() == 13 && ldt.getDayOfWeek() == DayOfWeek.FRIDAY) {
			result = true;
		}
		return result;
	}
}
