package com.smoothstack.matthewcrowell.appendtofile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Application to append text to an existing file
 * called outFile.txt.
 *
 * @author matthew.crowell
 */
public class Main {

	/**
	 * Appends text to file (creates file if file does not exist).
	 *
	 * @param args String[] of command line arguments to be appended to file
	 */
	public static void main(String[] args) {
		try (BufferedWriter outFile = new BufferedWriter(new FileWriter("outFile.txt", true))) {
			StringBuilder addendum = new StringBuilder();
			for (String arg : args) {
				addendum.append(arg);
				addendum.append(" ");
			}
			outFile.write(addendum.toString());
			outFile.newLine();
		} catch (IOException | RuntimeException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
