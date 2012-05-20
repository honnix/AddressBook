package com.xiao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.xiao.project.controller.AddressBookController;
import com.xiao.project.po.Person;
import com.xiao.project.worker.Worker;

public class AddressBook {
	
	private static ExecutorService getExecutorService() {
		return (ExecutorService) Executors.newFixedThreadPool(5);
	}
	
	private static AddressBookController getAddressBookController() {
		return AddressBookController.getInstance();
	}
	
	public static void main(String[] args) {
		ExecutorService executorService = getExecutorService();
		byte[] b = new byte[1024];
		int n = 0;
		try {
			while (true) {
				System.out.print("ab> ");
				n = System.in.read(b);
				String s = new String(b, 0, n - 2);
				if (s.equalsIgnoreCase("!quit")) {
					break;
				}
				if (s.equalsIgnoreCase("add")) {

					System.out.print("name: ");
					n = System.in.read(b);
					String name = new String(b, 0, n - 2);

					System.out.print("mobile: ");
					n = System.in.read(b);
					String phone = new String(b, 0, n - 2);

					System.out.print("address: ");
					n = System.in.read(b);
					String address = new String(b, 0, n - 2);

					Person ps = new Person(name, phone, address);
					String msg;
					if((msg = ps.validatePersonAttributes()) != null) {
						System.out.println(msg);
						continue;
					}
					// ¥Ê»Îxml
					List<Object> params = new ArrayList<Object>();
					params.add(ps);
					Future<Object> future = executorService.submit(new Worker(getAddressBookController(), Worker.ADD, params));
					future.get();
				} else if (s.equalsIgnoreCase("search")) {
					System.out.print("by(name|mobile|address): ");
					n = System.in.read(b);
					String input = new String(b, 0, n - 2);

					if (input.equalsIgnoreCase("name")) {
						System.out.print("name: ");
					} else if (input.equalsIgnoreCase("mobile")) {
						System.out.print("mobile: ");
					} else if (input.equalsIgnoreCase("address")) {
						System.out.print("address: ");
					} else {
						System.out.println("Unvariable Command!");
						continue;
					}
					n = System.in.read(b);
					String data = new String(b, 0, n - 2);
					List<Object> params = new ArrayList<Object>();
					params.add(input);
					params.add(data);
					Future<Object> future = executorService.submit(new Worker(getAddressBookController(), Worker.QUERY, params));
					future.get();
				} else if (s.equalsIgnoreCase("delete")) {
					System.out.print("by(name|mobile|address): ");
					n = System.in.read(b);
					String input = new String(b, 0, n - 2);

					if (input.equalsIgnoreCase("name")) {
						System.out.print("name: ");
					} else if (input.equalsIgnoreCase("mobile")) {
						System.out.print("mobile: ");
					} else if (input.equalsIgnoreCase("address")) {
						System.out.print("address: ");
					} else {
						System.out.println("Unvariable Command!");
						continue;
					}
					n = System.in.read(b);
					String data = new String(b, 0, n - 2);
					List<Object> params = new ArrayList<Object>();
					params.add(input);
					params.add(data);
					Future<Object> future = executorService.submit(new Worker(getAddressBookController(), Worker.DELETE, params));
					future.get();
				} else if (s.equalsIgnoreCase("!help")) {
					getAddressBookController().printHelpInfo();
				} else {
					System.out.println("Unvariable Command!");
					getAddressBookController().printHelpInfo();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			executorService.shutdown();
		}
	}
}
