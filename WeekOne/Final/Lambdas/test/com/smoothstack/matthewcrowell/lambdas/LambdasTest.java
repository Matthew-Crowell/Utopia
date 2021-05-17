package com.smoothstack.matthewcrowell.lambdas;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LambdasTest {

	private Lambdas app;

	@Before
	public void setUp() {
		try {
			app = new Lambdas();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void isOdd() {
		assertEquals("EVEN", app.isOdd().operation(4));
		assertEquals("ODD", app.isOdd().operation(3));
	}

	@Test
	public void isPrime() {
		assertEquals("PRIME", app.isPrime().operation(5));
		assertEquals("COMPOSITE", app.isPrime().operation(12));
	}

	@Test
	public void isPalindrome() {
		assertEquals("PALINDROME", app.isPalindrome().operation(898));
	}
}