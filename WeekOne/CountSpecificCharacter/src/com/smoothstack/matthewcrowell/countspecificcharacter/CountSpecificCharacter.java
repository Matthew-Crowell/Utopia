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

	private Integer occurrences;
	private String triggerSequence;
	private Boolean caseSensitive;
	private String filename;

	/**
	 * Default constructor, initializes variables to default values.
	 */
	CountSpecificCharacter() {
		this.triggerSequence = null;
		this.occurrences = 0;
		this.caseSensitive = false;
		this.filename = null;
	}

	/**
	 * Getter for occurrence counter.
	 * @return Integer, value of occurrence counter
	 */
	public Integer getOccurrences() {
		return occurrences;
	}

	/**
	 * Setter for occurrence counter.
	 * @param occurrences Integer, number of occurrences
	 */
	public void setOccurrences(Integer occurrences) {
		this.occurrences = occurrences;
	}

	/**
	 * Getter for trigger sequence.
	 * @return String, value of trigger sequence
	 */
	public String getTriggerSequence() {
		return triggerSequence;
	}

	/**
	 * Setter for trigger sequence.
	 * @param triggerSequence String, value of trigger sequence
	 */
	public void setTriggerSequence(String triggerSequence) {
		this.triggerSequence = triggerSequence;
	}

	/**
	 * Getter for case sensitive flag.
	 * @return Boolean, flag set or not
	 */
	public Boolean getCaseSensitive() {
		return caseSensitive;
	}

	/**
	 * Setter for case sensitive flag.
	 *
	 * @param caseSensitive Boolean, whether or not to set flag
	 */
	public void setCaseSensitive(Boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	/**
	 * Getter for filename.
	 * @return String, name of file to search
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Setter for filename.
	 * @param filename String, name of file to search
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * Handles command line arguments and displays number of times character occurs.
	 *
	 * @param args String[] where first argument is the file to analyze
	 */
	public static void main(String[] args) {
		CountSpecificCharacter app = new CountSpecificCharacter();
		app.handleArguments(args);

		if (app.getTriggerSequence() != null) {
			System.out.println(app.countOccurrences());
		}
	}

	/**
	 * Evaluates command line arguments.
	 *
	 * @param args String[] of command line arguments
	 */
	private void handleArguments(String[] args){
		if(args.length > 0){
			for(int i = 0; i < args.length; i++){
				switch (args[i]) {
					case ("--case-sensitive"):
					case ("-c"):
						caseSensitive=Boolean.valueOf(args[i+1]);
						i++;
						break;
					case ("--file"):
					case ("-f"):
						filename = args[i+1];
						i++;
						break;
					case ("--help"):
					case ("-h"):
						printHelp();
						i=args.length;
						break;
					case ("--string"):
					case ("-s"):
						triggerSequence = args[i+1];
						i++;
						break;
					default:
						triggerSequence = args[0];
						break;
				}
			}
		}
	}

	/**
	 * Scans a file to count occurrences of a specified character and handles FileNotFoundExceptions.
	 *
	 * @return Integer representing number of occurences of character in file or -1 if no file was searched
	 */
	private Integer countOccurrences() {
		try {
			if(filename != null){
				if(caseSensitive == true){
					SearchFileCaseSensitive();
				} else {
					searchFile();
				}
			} else {
				throw new FileNotFoundException();
			}
		} catch (FileNotFoundException e) {
			System.out.print("File not found.  Enter 'q' to exit.\nFile to be searched: ");
			Scanner scanner = new Scanner(System.in);
			filename = scanner.nextLine().stripTrailing();
			if (filename.equals("q")) {
				occurrences = -1;
			} else {
				countOccurrences();
			}
		}
		return occurrences;
	}

	/**
	 * Search for the number of times a string sequence occurs in a file (not case sensitive).
	 * @throws FileNotFoundException if no file is found
	 */
	private void searchFile() throws FileNotFoundException {
		Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename)));
		StringBuilder line = new StringBuilder();
		setTriggerSequence(triggerSequence.toUpperCase(Locale.ROOT));
		while(scanner.hasNextLine()){
			line.replace(0, line.length(), scanner.nextLine().toUpperCase(Locale.ROOT));
			int index = line.toString().indexOf(triggerSequence);
			while (index != -1) {
				occurrences++;
				line.replace(0, line.length(), line.substring(index + 1));
				index = line.toString().indexOf(triggerSequence);
			}
		}
	}

	/**
	 * Case sensitive search for the number of times a string sequence occurs in a file.
	 * @throws FileNotFoundException if no file is found
	 */
	private void SearchFileCaseSensitive() throws FileNotFoundException {
		Scanner scanner = new Scanner(new BufferedReader(new FileReader(filename)));
		StringBuilder line = new StringBuilder();
		setTriggerSequence(triggerSequence);
		while(scanner.hasNextLine()){
			line.replace(0, line.length(), scanner.nextLine());
			int index = line.toString().indexOf(triggerSequence);
			while (index != -1) {
				occurrences++;
				line.replace(0, line.length(), line.substring(index + 1));
				index = line.toString().indexOf(triggerSequence);
			}
		}
	}

	/**
	 * Print command line help to terminal on standard output.
	 */
	private void printHelp() {
		System.out.println("Class to count the number of times a character appears in a given file.");
		System.out.println("Usage:");
		System.out.println("\tjava com.smoothstack.matthewcrowell.countspecificcharacter.CountSpecificCharacter " +
				"<sequence of characters to seek>");
		System.out.println("\tjava com.smoothstack.matthewcrowell.countspecificcharacter.CountSpecificCharacter " +
				"<flag> <value>");
		System.out.println("Flags:");
		System.out.println("\t--case-sensitive, -c\t\tif search should be case sensitive");
		System.out.println("\t--file, -f\t\t\tname of file to be searched");
		System.out.println("\t--help, -h\t\t\tdisplays this help screen");
		System.out.println("\t--string, -s\t\t\tsequence of characters to seek");
	}
}