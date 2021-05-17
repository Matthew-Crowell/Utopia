package com.smoothstack.matthewcrowell.printpatterns;

/**
 * Class to print specified patterns.
 *
 * @author matthew.crowell
 */
public class Main {

	public static void main(String[] args) {
		System.out.println("1)");
		PrintPattern1(4);
		System.out.println();
		System.out.println("2)");
		PrintPattern2(4);
		System.out.println();
		System.out.println("3)");
		PrintPattern3(4);
		System.out.println();
		System.out.println("4)");
		PrintPattern4(4);
	}

	/**
	 * Print a triangle.
	 *
	 * @param iterations Integer, number of rows
	 */
	public static void PrintPattern1(Integer iterations) {
		StringBuilder base = new StringBuilder(".");
		StringBuilder pattern = new StringBuilder("*");

		for (int i = 0; i < iterations; i++) {
			base.append("..");

			System.out.println(pattern);

			pattern.append("*");
		}

		System.out.println(base);
	}

	/**
	 * Print inverted triangle by column.
	 *
	 * @param iterations Integer, number of rows
	 */
	public static void PrintPattern2(Integer iterations) {
		StringBuilder pattern = new StringBuilder("*");

		System.out.println(".." + "..".repeat(iterations));

		for (int i = iterations; i >= 0; i--) {
			for (int j = 0; j < i; j++) {
				System.out.print(pattern);
			}

			System.out.println();
		}
	}

	/**
	 * Print a pyramid.
	 *
	 * @param iterations Integer, number of rows
	 */
	public static void PrintPattern3(Integer iterations) {
		StringBuilder base = new StringBuilder("...");
		StringBuilder pattern = new StringBuilder("*");

		for (int i = iterations; i > 0; i--) {
			for (int j = i; j >= 0; j--) {
				System.out.print(" ");
			}

			System.out.println(pattern);

			pattern.append("**");
			base.append("..");
		}

		System.out.println(base);
	}

	/**
	 * Print inverted pyramid.
	 *
	 * @param iterations Integer, number of rows
	 */
	public static void PrintPattern4(Integer iterations) {
		StringBuilder pattern = new StringBuilder("*");

		int patternCounter = 2 * iterations - 1;

		System.out.println("...." + "..".repeat(iterations));

		for (int i = iterations - 1; i >= 0; i--) {
			System.out.print(" ");

			for (int j = i; j < iterations; j++) {
				System.out.print(" ");
			}

			for (int j = 1; j <= patternCounter; j++) {
				System.out.print(pattern);
			}
			System.out.println();

			patternCounter -= 2;
		}
	}
}