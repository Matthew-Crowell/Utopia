package com.smoothstack.matthewcrowell.countspecificcharacter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Locale;
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
	 * Default constructor, sets member variables to null.
	 */
	CountSpecificCharacter() {
		this.triggerCharacter = null;
		this.characterCount = null;
	}

	/**
	 * Constructor for CountSpecificCharacter objects.
	 *
	 * @param triggerCharacter Character to count occurences of.
	 */
	CountSpecificCharacter(Character triggerCharacter) {
		this.triggerCharacter = triggerCharacter.toString().toUpperCase(Locale.ROOT).toCharArray()[0];
		this.characterCount = 0;
	}

	/**
	 * Handles command line arguments and displays number of times character occurs.
	 *
	 * @param args String[] where first argument is the file to analyze
	 */
	public static void main(String[] args) {
		CountSpecificCharacter app = null;
		String filename = null;

		switch (args.length > 0 ? args[0] : "--help") {
			case ("--help"):
			case ("-h"):
				app = new CountSpecificCharacter();
				app.printHelp();
				break;
			case ("--character"):
			case ("-c"):
				app = new CountSpecificCharacter(args[1].toCharArray()[0]);
				if (args.length > 2) {
					filename = new String(args[3]);
				} else {
					System.out.print("File to be searched: ");
					Scanner scanner = new Scanner(System.in);
					filename = new String(scanner.next());
				}
				break;
			case ("--file"):
			case ("-f"):
				filename = new String(args[1]);
				if (args.length > 2) {
					app = new CountSpecificCharacter(args[3].charAt(0));
				} else {
					Scanner scanner = new Scanner(System.in);
					System.out.print("Character to seek: ");
					Character c = scanner.next().charAt(0);
					app = new CountSpecificCharacter(c);
				}
				break;
			default:
				app = new CountSpecificCharacter();
				app.printHelp();
				break;
		}
		if (app != null && filename != null) {
			System.out.println(app.countOccurrences(filename));
		}
	}

	/**
	 * Scans a file to count occurrences of a specified character.
	 *
	 * @param filename String filename of file to be searched
	 * @return Integer representing number of occurences of character in file or -1 if no file was searched
	 */
	private Integer countOccurrences(String filename) {
		try {
			Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename)));
			while (scanner.hasNext()) {
				for (char letter : scanner.next().toUpperCase(Locale.ROOT).toCharArray()) {
					if (letter == triggerCharacter) {
						characterCount++;
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.print("File not found.  Enter 'q' to exit.\nFile to be searched: ");
			Scanner scanner = new Scanner(System.in);
			filename = scanner.next();
			if(filename.equals("q")){
				characterCount = -1;
			}
			else{
				countOccurrences(filename);
			}
		}
		return characterCount;
	}

	/**
	 * Print command line help to terminal on standard output.
	 */
	private void printHelp() {
		System.out.println("Class to count the number of times a character appears in a given file.\n" +
				"Usage:\n\tjava com.smoothstack.matthewcrowell.countspecificcharacter.CountSpecificCharacter " +
				"<--file | -f> <path/to/file> <--character | -c> <character>\n\tjava com.smoothstack.matthew" +
				"crowell.countspecificcharacter.CountSpecificCharacter <--character | -c> <character> " +
				"<--file | -f> <path/to/file>");
	}
}
