package com.smoothstack.matthewcrowell.countspecificcharacter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Class to count the occurrences of a given character in a given file.
 *
 * @author matthew.crowell
 */
public class CountSpecificCharacter {

	private Integer characterCount;
	private Character triggerCharacter;

	/**
	 * Default constructor, sets trigger character to 'a'.
	 */
	CountSpecificCharacter() {
		this('a');
	}

	/**
	 * Constructor for CountSpecificCharacter objects.
	 *
	 * @param triggerCharacter Character to count occurences of.
	 */
	CountSpecificCharacter(Character triggerCharacter) {
		this.triggerCharacter = triggerCharacter;
		this.characterCount = 0;
	}

	/**
	 * Instantiates CountSpecificCharacter object, calls countOccurences
	 * on object, and handles FileNotFoundExceptions.
	 *
	 * @param args String[] where first argument is the file to analyze
	 */
	public static void main(String[] args) {
		CountSpecificCharacter count = new CountSpecificCharacter('e');
		try {
			System.out.println(count.countOccurrences(args));
		} catch (FileNotFoundException e) {
			System.out.println("Unable to locate file, " + args[0] + ", please try again.");
		}

	}

	/**
	 * Scans a file, args[0], for a specified character.
	 *
	 * @param args String[] where first argument is file to be scanned
	 * @return Integer representing number of occurences of character in file
	 * @throws FileNotFoundException if unable to locate specified file
	 */
	public Integer countOccurrences(String[] args) throws FileNotFoundException {
		StringBuilder line = new StringBuilder();
		Scanner scanner = new Scanner(new BufferedReader(new FileReader(args[0])));
		while (scanner.hasNext()) {
			line.append(scanner.next());
			char[] letters = line.toString().toCharArray();
			for (char letter : letters) {
				if (letter == triggerCharacter) {
					characterCount++;
				}
			}
			line.setLength(0);
		}
		return characterCount;
	}
}
