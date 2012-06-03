/**
 * 
 */
package com.xiao.project.controller;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.xiao.project.po.Person;
import com.xiao.project.xml.AddressBookXmlWriter;

/**
 * @author XiaoTing 2012-5-19
 */
public class AddressBookController {

	private static AddressBookController addressBookController;

	public static AddressBookController getInstance() {
		if (addressBookController == null) {
			addressBookController = new AddressBookController();
		}
		return addressBookController;
	}

	private AddressBookController() {
	}

	private AddressBookXmlWriter getAddressBookXmlWriter() {
		return AddressBookXmlWriter.getInstance();
	}

	public void savePerson(Person obj) {
		getAddressBookXmlWriter().addToXml(obj);
	    System.out.println("address entry added successfully");
	}

	public void queryPerson(String condition, String data) {
		List<Person> persons = getAddressBookXmlWriter().queryNodes(condition,
				data);
		printPersons(persons);
	}

	public void deletePerson(String condition, String data) {
		boolean isLike = false;
		if (data.indexOf(".*") != -1) {
			data = data.replaceAll(".\\*", "");
			isLike = true;
		}
		getAddressBookXmlWriter().deleteNodes(condition, data, isLike);
	}

	private void printPersons(List<Person> persons) {
		if (persons != null && persons.size() > 0) {
			for (Person p : persons) {
				System.out.println("name:" + p.getName());
				System.out.println("mobile:" + p.getPhone());
				System.out.println("address:" + p.getAddress());
				System.out.println("----------------------");
			}
		} else {
			System.out.println("No Result");
		}
	}

	public void printHelpInfo() {
		System.out.println("Help Message:");
		System.out.println("add: add new address entry");
		System.out.println("search: get one or more address entries");
		System.out.println("delete: remove one or more address entries");
	}
}
