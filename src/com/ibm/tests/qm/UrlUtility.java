package com.ibm.tests.qm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * This application is only for a test purposes, you should not use it on a
 * production server.
 * 
 * @author TadeuszJanasiewicz
 *
 * 
 */

public class UrlUtility {

	private String user;
	private String password;

	public UrlUtility(String user, String password) {
		this.user = user;
		this.password = password;
	}

	/**
	 * This function is not supporting pagging, which means if there are more then
	 * 50 Test Cases in results an XML will be return with next page
	 * 
	 * @param url - full REST URL
	 */

	public String getTestCases(String url, String fileToSave, boolean TLS12) {

		System.out.println();
		System.out.println("RQMUrlUtilrity.getTestCases() ===> STARTED");
		System.out.println();

		String arguments = ((!TLS12) ? "java -jar RQMUrlUtility.jar"
				: "java -Dcom.ibm.team.repository.transport.client.protocol=\"TLSv1.2\" -jar RQMUrlUtility.jar")
				+ " -command GET" + " -user " + user + " -password " + password + " -url " + url + " -filepath "
				+ fileToSave;

		try {

			// Run a java app in a separate system process
			Process proc = Runtime.getRuntime().exec(arguments);

			// Then retrieve the process output
			BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println();
		System.out.println("RQMUrlUtilrity.getTestCases() ===> FINISHED");
		System.out.println();

		return fileToSave;
	}

	public String getTestCase(String url, String fileName, boolean TLS12) {

		System.out.println();
		System.out.println("RQMUrlUtilrity.getTestCase() ===> STARTED");
		System.out.println();

		String arguments = ((!TLS12) ? "java -jar RQMUrlUtility.jar"
				: "java -Dcom.ibm.team.repository.transport.client.protocol=\"TLSv1.2\" -jar RQMUrlUtility.jar")
				+ " -command GET" + " -user " + user + " -password " + password + " -url " + url + " -filepath "
				+ fileName;

		try {

			// Run a java app in a separate system process
			Process proc = Runtime.getRuntime().exec(arguments);

			// Then retrieve the process output
			BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println();
		System.out.println("RQMUrlUtilrity.getTestCase() ===> FINISHED");
		System.out.println();

		return fileName;
	}

	public void setTestCase(String url, String sourceFile, boolean TLS12) {
		System.out.println();
		System.out.println("RQMUrlUtilrity.setTestCase() ===> STARTED");
		System.out.println();

		String arguments = ((!TLS12) ? "java -jar RQMUrlUtility.jar"
				: "java -Dcom.ibm.team.repository.transport.client.protocol=\"TLSv1.2\" -jar RQMUrlUtility.jar")
				+ " -command PUT" + " -user " + user + " -password " + password + " -url " + url + " -filepath "
				+ sourceFile;

		try {

			// Run a java app in a separate system process
			Process proc = Runtime.getRuntime().exec(arguments);

			// Then retrieve the process output
			BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println();
		System.out.println("RQMUrlUtilrity.getTestCase() ===> FINISHED");
		System.out.println();

	}
}
