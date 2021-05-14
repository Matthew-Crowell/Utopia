package com.smoothstack.matthewcrowell.directorycontents;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;


/**
 * Class to recursively explore subdirectories and files.
 *
 * @author matthew.crowell
 */
public class DirectoryContents {

	/**
	 * Handle command line arguments and list contents of given directory.
	 *
	 * @param args String[] of command line arguments
	 */
	public static void main(String[] args) {
		DirectoryContents app = new DirectoryContents();

		switch(args.length > 0 ? args[0] : ""){
			case("--help"):
			case("-h"):
				app.printHelp();
				break;
			case("--directory"):
			case("-d"):
				app.listAll(args[1]);
				break;
			default:
				app.listAll("");
				break;
		}

	}

	/**
	 * Find and list all directories and files subsequent to a given directory.
	 *
	 * @param filepath String filepath to directory to be searched
	 * @throws IOException if traversal fails
	 */
	private void listAll(String filepath) {
		try{
			Files.walk(Paths.get(filepath).toAbsolutePath()).forEach(System.out::println);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * Print command line help to the standard output.
	 */
	private void printHelp(){
		System.out.println("Application to list all files and directories subsequent to specified directory.\n"
				+ "Usage:\n\tjava com.smoothstack.matthewcrowell.directorycontents.DirectoryContents " +
				"<--directory | -d> <path/to/directory>\n");
	}
}