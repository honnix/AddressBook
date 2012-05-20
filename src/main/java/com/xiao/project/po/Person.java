/**
 * 
 */
package com.xiao.project.po;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author XiaoTing
 *
 */
public class Person {

	private String m_name;
	private String m_phone;
	private String m_address;
	
	public Person(){}
	
	public Person(String name,String phone,String address) {
		m_name = name;
		m_phone = phone;
		m_address = address;
	}
	
	public String validatePersonAttributes() {
		if(!validatePhone(this.m_phone))
			return "invalid phone no!";
		return null;
	}
	
	private boolean validatePhone(String phone) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(phone);
		return m.matches();
	}
	
	public String getName(){
		return m_name;
	}
	public void setName(String name){
		m_name = name;
	}
	public String getPhone(){
		return m_phone;
	}
	public void setPhone(String phone){
		m_phone = phone;
	}
	public String getAddress(){
		return m_address;
	}
	public void setAddress(String address){
		m_address = address;
	}

}
