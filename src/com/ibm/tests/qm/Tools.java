package com.ibm.tests.qm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * This application is only for a test purposes, you should not use it on a
 * production server.
 * 
 * @author TadeuszJanasiewicz
 *
 * 
 */

public class Tools {

	public List<String> retriveTestCases() {
		List<String> listOfTestCases = null;

		return listOfTestCases;
	}

	public String replaceXMLElements(String filename, String regexPattern, String replacement) {

		String newFile = "replaced.xml";
		Pattern pattern = Pattern.compile(regexPattern);
		Matcher matcher = null;
		boolean matchWasFound = false;

		try {

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setNamespaceAware(true);
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document document = docBuilder.parse(new File(filename));

			String title = null;
			String description = null;

			NodeList nl = document.getElementsByTagName("ns3:title");
			if (nl != null)
				title = nl.item(0).getChildNodes().item(0).getNodeValue();

			Node node = document.getElementsByTagName("ns3:description").item(0).getChildNodes().item(0);
			if (node != null)
				description = node.getNodeValue();

			if (title != null) {
				// check with the pattern
				matcher = pattern.matcher(title);

				// if there is a match replace it
				if (matcher.find()) {
					matchWasFound = true;

					System.out.println("Title: ");
					System.out.println(title);

					document.getElementsByTagName("ns3:title").item(0).getChildNodes().item(0)
							.setNodeValue(title.replaceAll(regexPattern, replacement));
					System.out.println("Replaced with:");
					System.out.println(title.replaceAll(regexPattern, replacement));
				}
			}

			if (description != null) {
				// check with the pattern
				matcher = pattern.matcher(description);

				// if there is a match replace it
				if (matcher.find()) {
					matchWasFound = true;

					System.out.println("Description: " + description);

					document.getElementsByTagName("ns3:description").item(0).getChildNodes().item(0)
							.setNodeValue(description.replaceAll(regexPattern, replacement));
					System.out.println("Replaced with");
					System.out.println(description.replaceAll(regexPattern, replacement));
				}
			}

			if (matchWasFound) {
				// write the updated document to file
//				document.getDocumentElement().normalize();
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(document);
				StreamResult result = new StreamResult(new File(newFile));
				transformer.transform(source, result);
				System.out.println("XML file updated successfully");
			} else
				newFile = null;

		} catch (SAXException | ParserConfigurationException | IOException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newFile;
	}

	/*
	 * TODO This has to be finished
	 */
	public String replaceAllXMLElements(String filename, String regexPattern, String replacement) {

		String newFile = "replaced.xml";
//		Pattern pattern = Pattern.compile(regexPattern);
//		Matcher matcher = null;
		boolean matchWasFound = false;

		try {

			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setNamespaceAware(true);
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document document = docBuilder.parse(new File(filename));

			// Find all XML elements
			NodeList nodeList = document.getElementsByTagName("*");

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {

					// do something with the current element
					// for example replace a substring

					// TODO check attribute one by one

				}
			}

			if (matchWasFound) {
				// write the updated document to file
				document.getDocumentElement().normalize();
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(document);
				StreamResult result = new StreamResult(new File(newFile));
				transformer.transform(source, result);
				System.out.println("XML file updated successfully");
			} else
				newFile = null;

		} catch (SAXException | ParserConfigurationException | IOException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newFile;
	}

	public ArrayList<String> retriveTestCasesIDs(String filename) {

		System.out.println();
		System.out.println("Tools.retriveTestCasesIDs() ===> STARTED");

		ArrayList<String> IDs = new ArrayList<String>();

		try {

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse(filename);

			Element docEle = dom.getDocumentElement();
			NodeList nl = docEle.getChildNodes();
			int length = nl.getLength();
			for (int i = 0; i < length; i++) {
				if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element el = (Element) nl.item(i);
					if (el.getNodeName().contains("entry")) {
						IDs.add(el.getElementsByTagName("id").item(0).getTextContent());
					}
				}
			}

		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("There were " + IDs.size() + " Test Cases found.");
		System.out.println("Tools.retriveTestCasesIDs() ===> FINISHED");
		System.out.println();

		return IDs;
	}
}
