package com.smoothstack.matthewcrowell.twodimensionalarray;

/**
 * Application demonstrating use of exceptions.
 *
 * @author matthew.crowell
 */
public class Main {

	/**
	 * Entry point to application creates array and calls FindLargest.
	 *
	 * @param args String[] of arguments from command line
	 */
	public static void main(String[] args) {
		// initialize array
		Integer[][] array2D = new Integer[10][10];

		// assign values to array
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				array2D[i][j] = (int) (Math.random() * 1000 + 1);
			}
		}

		FindLargest(array2D);
	}

	/**
	 * Find and print identifying information about largest number in 2D array.
	 *
	 * @param array2D Integer[][]
	 */
	public static void FindLargest(Integer[][] array2D) {
		Integer largestNumber = 0;
		Integer row = 0;
		Integer column = 0;

		try {
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					if (array2D[i][j] > largestNumber) {
						row = i;
						column = j;
						largestNumber = array2D[row][column];
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Invalid value encountered.  Aborting.");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		System.out.println("Largest number: " + largestNumber + "\nLocation in array: [" + row + "][" + column + "]");
	}
}