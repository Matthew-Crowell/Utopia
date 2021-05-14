package com.smoothstack.matthewcrowell.sumfromterminal;

/**
 * Application to add a list of integers from the command line.
 *
 * @author matthew.crowell
 */

import java.util.Scanner;

/**
 * Class to calculate sum of command line arguments
 */
public class SumFromTerminal {

	/**
	 * Entry point into the application.
	 *
	 * @param args arguments passed via the command line
	 */
	public static void main(String[] args) {
		SumFromTerminal sft = new SumFromTerminal();
		System.out.println(sft.calculateSum(args));
	}

	/**
	 * Calculate sum of arbitrary number of command line arguments.
	 *
	 * @param args arguments passed via the command line
	 * @return Integer, sum of integer arugments
	 * @throws NumberFormatException if any argument isn't an integer
	 */
	public Integer calculateSum(String[] args) throws NumberFormatException {
		Integer sum = null;

		try {
			for (String argument : args) {
				//
				sum = sum == null ? Integer.valueOf(argument) : sum + Integer.valueOf(argument);
			}
		} catch (NumberFormatException e) {
			// build a new array of arguments and try again
			sum = 0;
			Scanner input = new Scanner(System.in);
			System.out.print("One or more arguments were not integers.\nPlease enter a list of integers: ");
			StringBuilder argString = new StringBuilder();
			argString.append(input.nextLine());
			String[] arguments = argString.toString().split("\\s");
			sum = this.calculateSum(arguments);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return sum;
		}
	}
}
