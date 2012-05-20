package com.xiao.project.worker;

import java.util.List;
import java.util.concurrent.Callable;

import com.xiao.project.controller.AddressBookController;
import com.xiao.project.po.Person;

public class Worker implements Callable<Object>{
	
	private AddressBookController addressBookController;
	
	private List<Object> params;
	
	private String operation;
	
	public final static String ADD = "add";
	
	public final static String DELETE = "delete";
	
	public final static String QUERY = "query";
	
	public Worker(AddressBookController addressBookController, String operation, List<Object> params) {

		this.addressBookController = addressBookController;
		this.operation = operation;
		this.params = params;
	}
	
	public Object call() throws Exception {
		if( ADD.equals(operation)) {
			addressBookController.savePerson((Person)(params.get(0)));
		}
		else if( DELETE.equals(operation)) {
			addressBookController.deletePerson((String)params.get(0),(String)params.get(1));
		}
		else if( QUERY.equals(operation)) {
			addressBookController.queryPerson((String)params.get(0),(String)params.get(1));
		}
		return null;
	}
}
