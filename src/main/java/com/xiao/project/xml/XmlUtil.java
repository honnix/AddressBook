/**
 * 
 */
package com.xiao.project.xml;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author XiaoTing
 * 2012-5-19
 */
public class XmlUtil {
    
	/**
	 * query the first correct result
	 * @param express
	 * @param source
	 * @return
	 */
//	public static Node querySingleNode(String express,Object source){
//		Node result = null;
//		XPathFactory xpfac = XPathFactory.newInstance();
//		XPath xp = xpfac.newXPath();
//		try{
//			result = (Node)xp.evaluate(express, source, XPathConstants.NODE);
//		}catch(XPathExpressionException e){
//			e.printStackTrace();
//		}
//		return result;
//	}
	
	/**
	 * query correct records
	 * @param express
	 * @param source
	 * @return
	 */
	public static NodeList queryNodes(String express,Object source){
		NodeList result = null;
		XPathFactory xpfac = XPathFactory.newInstance();
		XPath xp = xpfac.newXPath();
		try{
			result = (NodeList)xp.evaluate(express, source, XPathConstants.NODESET);
		}catch(XPathExpressionException e){
			e.printStackTrace();
		}
		return result;
	}

}
