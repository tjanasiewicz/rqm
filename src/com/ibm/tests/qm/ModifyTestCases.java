package com.ibm.tests.qm;

import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * 
 * This application is only for a test purposes, you should not use it on a
 * production server.
 * 
 * @author TadeuszJanasiewicz
 *
 * 
 */

public class ModifyTestCases {

	/**
	 * @param args
	 * @throws ParseException
	 * 
	 * 
	 */
	public static void main(String[] args) throws ParseException {

		boolean readOnlyMode = true;
		Tools tools = new Tools();

		Options options = new Options();
		options.addRequiredOption("u", "user", true, "User name, for example: -u bob")
				.addRequiredOption("P", "pass", true, "user password, for example: -P bob1")
				.addRequiredOption("r", "repos", true, "PublicURI, for example: -r https://domain.com:9443/qm/")
				.addRequiredOption("pa", "project", true, "Project Area name.")
				.addRequiredOption("rx", "regexPattern", true, "Regex pattern to be search.")
				.addRequiredOption("rep", "replacement", true, "String which will replaced the searched pattern.")
				.addOption("x", "Disable read only mode, when added synchronization with repository is enabled.");

		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("ModifyTestCases", options);

		// Create a parser
		CommandLineParser parser = new DefaultParser();

		// parse the options passed as command line arguments
		CommandLine cmd = parser.parse(options, args);

		// ***Interrogation Stage***
		// hasOptions checks if option is present or not
		if (cmd.hasOption("x")) {
			System.out.println("==========================***NOTE***============================");
			System.out.println("ReadOnlyMode was disabled, changes will be reflected in the repository!");
			System.out.println("================================================================");

			readOnlyMode = false;
		}

		String userName = cmd.getOptionValue("u");
		String userPass = cmd.getOptionValue("P");
		String publicURI = cmd.getOptionValue("r");
		String projectAreaName = cmd.getOptionValue("pa");
		String pattern = cmd.getOptionValue("rx");
		String replacement = cmd.getOptionValue("rep");

		String fileName = "results.xml";
		String testCasesURL = tools.prepareRequestGETTestCases(publicURI, projectAreaName);
	
		// Set repository user and password
		UrlUtility rqmUtility = new UrlUtility(userName, userPass);

		// Get a list of Test Cases (it will get the list of only first X Test Cases as
		// paging is not implemented!!)
		String fileWithTestCases = rqmUtility.getTestCases(testCasesURL, fileName, true);

		// Retrieve URLs of those Test Cases
		ArrayList<String> testCasesIDs = tools.retriveTestCasesIDs(fileWithTestCases);

		int counter = 1;
		String newFile = "";

		// For each Test Case
		for (String testCase : testCasesIDs) {

			// Get Test Case XML representation
			newFile = rqmUtility.getTestCase(testCase, fileName, true);

			// Replace all elements that match to pattern
			newFile = tools.replaceXMLElements(newFile, pattern, replacement);

			// Update Test Case(s) on a server
			if (!readOnlyMode && (newFile != null))
				rqmUtility.setTestCase(testCase, newFile, true);

			System.out.println("Updated Test Case: " + counter++ + " from " + testCasesIDs.size());
		}

	}

}
