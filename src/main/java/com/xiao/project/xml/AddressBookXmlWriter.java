/**
 * 
 */
package com.xiao.project.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.xiao.project.controller.AddressBookController;
import com.xiao.project.po.Person;


/**
 * @author XiaoTing
 *
 */
public class AddressBookXmlWriter {

	private static AddressBookXmlWriter addressBookXmlWriter;
	
	public static AddressBookXmlWriter getInstance() {
		if(addressBookXmlWriter == null) {
			addressBookXmlWriter = new AddressBookXmlWriter();
		}
		return addressBookXmlWriter;
	}
	
	private AddressBookXmlWriter() {
		
	}
	
	private String m_fileName = "./AddressBook.xml";
	
	/**
	 * get a document root.If not found file,new a document
	 * @param filename
	 * @return
	 */
	private Document openXml(){
		Document doc=null;
		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder dombuilder = domfac.newDocumentBuilder();
			File file = new File(m_fileName);
			if(file.exists()){
				doc = dombuilder.parse(file);
			}else{
				doc= dombuilder.newDocument();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return doc;
	}
	
	/**
	 * add a node to file
	 * @param p
	 */
	public void addToXml(Person p){
		try{
		 Document doc = openXml();
		 Element root = doc.getDocumentElement();
		 if(root == null){
			 root = doc.createElement(AddressBookXmlConst.ADDRESSBOOK_XML_ROOT);
			 doc.appendChild(root);
		 }
		 
		 Element ePerson = doc.createElement(AddressBookXmlConst.ADDRESSBOOK_XML_PERSON);
		 Element eName = doc.createElement(AddressBookXmlConst.ADDRESSBOOK_XML_NAME);
		 Element eMobile = doc.createElement(AddressBookXmlConst.ADDRESSBOOK_XML_MOBILE);
		 Element eAddress = doc.createElement(AddressBookXmlConst.ADDRESSBOOK_XML_ADDRESS);
		 Text txtName = doc.createTextNode(p.getName());
		 Text txtMobile = doc.createTextNode(p.getPhone());
		 Text txtAddress = doc.createTextNode(p.getAddress());
		
		 eName.appendChild(txtName);
		 eMobile.appendChild(txtMobile);
		 eAddress.appendChild(txtAddress);
		
		 ePerson.appendChild(eName);
		 ePerson.appendChild(eMobile);
		 ePerson.appendChild(eAddress);
		
		 root.appendChild(ePerson);
		
		 saveXml(doc);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * save to file
	 * @param doc
	 */
	private void saveXml(Document doc)throws Exception{
		PrintWriter pw = null;
		try{
		 DOMSource source = new DOMSource(doc);
		 Transformer t = TransformerFactory.newInstance().newTransformer();
		 t.setOutputProperty(OutputKeys.INDENT,"yes");
		 t.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
		 t.setOutputProperty(OutputKeys.METHOD,"xml");
		 t.setOutputProperty(OutputKeys.STANDALONE,"yes");
		 t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		 pw = new PrintWriter(new FileOutputStream(m_fileName));
		 StreamResult result = new StreamResult(pw);
		 t.transform(source, result);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			pw.close();
		}
	}
	
	/**
	 * query nodes by conditions
	 * @param con
	 * @param data
	 * @return
	 */
	public List<Person> queryNodes(String con,String data){
		
		List<Person> persons = new ArrayList<Person>();
		try{
		Document doc = openXml();
		Element root = doc.getDocumentElement();
//		Element root = (Element)doc.getElementsByTagName(AddressBookXmlConst.ADDRESSBOOK_XML_ROOT);
		String exp = "/AddressBook//person["+con+"='"+data+"']";
		NodeList nl = XmlUtil.queryNodes(exp, root);
		initNodeList(nl,persons);
		}catch(Exception e){
			e.printStackTrace();
		}
		return persons;
	}
	
	private void initNodeList(NodeList persons,List<Person> tranList){
		if(persons == null){
			return;
		}
		for(int i=0;i<persons.getLength();i++){
			Element person = (Element)persons.item(i);
			NodeList nodeName = person.getElementsByTagName(AddressBookXmlConst.ADDRESSBOOK_XML_NAME);
			NodeList nodeMobile = person.getElementsByTagName(AddressBookXmlConst.ADDRESSBOOK_XML_MOBILE);
			NodeList nodeAddress = person.getElementsByTagName(AddressBookXmlConst.ADDRESSBOOK_XML_ADDRESS);
			
			String name = nodeName.item(0).getFirstChild().getNodeValue();
			String mobile = nodeMobile.item(0).getFirstChild().getNodeValue();
			String address = nodeAddress.item(0).getFirstChild().getNodeValue();
			
			tranList.add(new Person(name,mobile,address));
		}
	}

	/**
	 * delete nodes by express
	 * @param exp
	 */
	public void deleteNodes(String condition,String data,boolean isLike){
		String exp="";
		if(isLike){
			exp= "/AddressBook//person[contains("+condition+",'"+data+"')]";
		}else{
			exp = "/AddressBook//person["+condition+"='"+data+"']";
		}
		NodeList nl;
		try{
		Document doc = openXml();
		Element root = doc.getDocumentElement();
//		System.out.println(root.getChildNodes().getLength());
		nl = XmlUtil.queryNodes(exp, root);
		for(int i=0;i<nl.getLength();i++){
			nl.item(i).getParentNode().removeChild(nl.item(i));
		}
		if(nl.getLength()>0){
		  saveXml(doc);
		}
		System.out.println(nl.getLength()+" address entries deleted");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
