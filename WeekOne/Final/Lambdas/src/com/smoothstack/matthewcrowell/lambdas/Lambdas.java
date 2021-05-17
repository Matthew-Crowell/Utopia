package com.smoothstack.matthewcrowell.lambdas;

/**
 * Functional interface for creating function objects as
 * specified in instruction packet.
 */
interface PerformOperation {
	String operation(Integer number);
}

/**
 * Class to demonstrate the use of a functional interface.
 */
public class Lambdas {

	/**
	 * Create an object that complies with PerformOperation
	 * interface and returns the rightmost digit of a
	 * provided Integer object.
	 *
	 * @return PerformOperation, function object for operation
	 */
	public PerformOperation isOdd() {
		return (Integer number) -> number % 2 != 0 ? "ODD" : "EVEN";
	}

	/**
	 * Create an object that complies with PerformOperation
	 * interface and returns a String indicating whether or
	 * not the specified number is a prime number.
	 *
	 * @return PerformOperation, function object for operation
	 */
	public PerformOperation isPrime() {
		return (Integer number) -> {
			Boolean isAPrime = true;
			for (int i = 2; i < Math.sqrt(number); i++) {
				isAPrime = number % i != 0 && isAPrime;
			}
			return isAPrime ? "PRIME" : "COMPOSITE";
		};
	}

	/**
	 * Create an object that complies with PerformOperation
	 * interface and returns a String indicating whether or
	 * not a given number is a palindrome.
	 *
	 * @return PerformObject, function object for operation
	 */
	public PerformOperation isPalindrome() {
		return (Integer number) -> {
			StringBuilder str = new StringBuilder();
			str.append(number.toString());
			return str.toString().equals(str.reverse().toString()) ? "PALINDROME" : "NOT PALINDROME";
		};
	}
}