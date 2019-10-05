package com.ibm.tests.qm;

import java.util.ArrayList;

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
	 * 
	 * 
	 */
	public static void main(String[] args) {

		boolean ReadOnlyMode = true;

		String userName = "jazzadmin";
		String userPass = "jazzadmin";
		String fileName = "results.xml";
		String testCasesURL = "https://clm.606.local.com:9443/qm/service/com.ibm.rqm.integration.service.IIntegrationService/resources/JKE%20Banking%2007-19%20%28Quality%20Management%29/testcase";

		String pattern = "([12][09][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9])";
		String replacment = "<<replacment>>";

		// Set repository user, password and a temp file
		UrlUtility rqmUtility = new UrlUtility(userName, userPass);
		Tools tools = new Tools();

		// Get a list of Test Cases (it will get the list of only first X Test Cases as
		// paging is not implemented)
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
			newFile = tools.replaceXMLElements(newFile, pattern, replacment);

			// Update Test Case(s) on a server
			if (!ReadOnlyMode && (newFile != null))
				rqmUtility.setTestCase(testCase, newFile, true);

			System.out.println("Updated Test Case: " + counter++ + " from " + testCasesIDs.size());
		}

	}

}
