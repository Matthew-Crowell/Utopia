package com.smoothstack.matthewcrowell.linetest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LineTestClass {

	@Test
	public void lineTest(){
		Line line1 = new Line(1, 2, 4, 8);
		assertEquals(2.0d, line1.getSlope(), .0001d);
	}
}