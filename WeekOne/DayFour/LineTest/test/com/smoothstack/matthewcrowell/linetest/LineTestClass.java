package com.smoothstack.matthewcrowell.linetest;

import org.junit.Test;

import static org.junit.Assert.*;

public class LineTestClass {
	private Line firstLine = new Line(0, 0, 10, 10);
	private Line secondLine = new Line(5, 5, 25, 25);
	private Line thirdLine = new Line(-5, -5, 10, 25);

	@Test
	public void lineGetSlopeTest(){

		assertEquals(1.0d, firstLine.getSlope(), .0001d);
		assertNotEquals(2.0d, firstLine.getSlope(), .0001d);
	}

	@Test
	public void lineGetDistanceTest(){
		Double distanceOfFirstLine = Math.sqrt((10 - 0) * (10 - 0) + (10 - 0) * (10 - 0));

		assertEquals(distanceOfFirstLine, firstLine.getDistance(), .0001d);
		assertNotEquals(Math.sqrt(2 * distanceOfFirstLine),
				firstLine.getDistance(), .0001d);
	}

	@Test
	public void lineParallelToTest(){
		assertTrue(firstLine.parallelTo(secondLine));
		assertFalse(firstLine.parallelTo(thirdLine));
	}
}