package com.smoothstack.matthewcrowell.directorycontents;

import java.nio.file.*;
import java.io.IOException;


/**
 * Application to recursively list all children of a directory.
 *
 * @author matthew.crowell
 */
public class Main {
	/**
	 * Lists all children of a given directory.
	 *
	 * @param args String[] where first argument is directory to search
	 */
	public static void main(String[] args) {
		try {
			listAll(args[0]);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * Find and list all directories and files subsequent to a given directory.
	 *
	 * @param filepath String filepath to directory to be searched
	 * @throws IOException if traversal fails
	 */
	private static void listAll(String filepath) throws IOException {
		Path path = Paths.get(filepath);
		Files.walk(path.toAbsolutePath()).forEach(System.out::println);
	}
}