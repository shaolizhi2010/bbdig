package com.bbdig.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.springframework.security.access.method.P;


//xml utils
public class X {
	// get value from xml by key
	public static String getValue(Object xml, String key) {
		// //L.trace("XmlUtil", "method --- getValue begin");
		if (key == null || key.trim().length() == 0 || key.contains("null")) {
			// //L.trace("XmlUtil", "getValue() : key is null, method return "
			// );
			return "";
		}

		String value = "";
		try {

			Object oValue = X.selectSingleNode(xml, key);
			if (oValue != null) {
				value = ((Element) oValue).getValue().trim();
				// //L.trace("XmlUtil", "key = " + key + " --- value = " +
				// value);
			} else {
				// //L.trace("XmlUtil", "key = " + key + " --- value = null");
			}

		} catch (java.lang.ClassCastException e) {
			printXml(xml);
		}
		// //L.trace("XmlUtil", "method --- getValue end");
		return value;
	}

	// get attribut value from xml by key and attribute name
	public static String getAttrValue(Object xml, String key, String attr) {
		// //L.trace("XmlUtil", "method --- getAttr begin");
		if (key == null || key.trim().length() == 0 || key.contains("null")) {
			// //L.trace("XmlUtil", "getAttr() : key is null, method return");
			return "";
		}
		String value = "";
		try {

			Object oValue = X.selectSingleNode(xml, key);
			if (oValue != null) {
				value = ((Element) oValue).getAttributeValue(attr);
				if (value != null)
					value = value.trim();

				// //L.trace("XmlUtil", "key = " + key + " --- attr = " + attr
				// + " --- value = " + value);
			} else {
				// //L.trace("XmlUtil", "can not find value, key = " + key);
			}

		} catch (Exception e) {
			printXml(xml);
		}
		// //L.trace("XmlUtil", "method --- getAttr end");
		return value;
	}

	// key 要带 @, 如 @src，查询到的节点是一个属性节点
	public static String getAttrValue(Object xml, String key) {
		if (key == null || key.trim().length() == 0 || key.contains("null")) {
			// //L.trace("XmlUtil", "getAttr() : key is null, method return");
			return "";
		}
		Object attrObject = selectSingleNode(xml, key);
		if (attrObject instanceof Attribute) {
			Attribute attr = (Attribute) attrObject;
			return attr.getValue();
		}
		return "";
	}

	// print xml element
	public static void printXml(Object xml) { // print xml

		try {
			// System.out.println( toString(xml));

		} catch (Exception e) {
		}
	}

	// xml to string
	public static String toString(Object xml) { // print xml
		// //L.trace("XmlUtil", "method --- toString begin");
		XMLOutputter xmlOut = new XMLOutputter();
		xmlOut.setFormat(Format.getPrettyFormat().setEncoding("UTF-8")); // set
																			// encoding
		try {
			if (xml instanceof Document) {
				return xmlOut.outputString((Document) xml);
			} else if (xml instanceof Element) {
				return xmlOut.outputString((Element) xml);
			}
		} catch (Exception e) {
		}
		// //L.trace("XmlUtil", "method --- toString return empty");
		return "";
	}

	// save xml element
	public static void saveXml(Object xml) { // save xml
		try {
			//if (P.getProperty("isProduct").equals("false")) {
				IOUtils.write(toString(xml), new FileOutputStream("/out.txt"));
			//}

		} catch (IOException e) {
			// //L.trace("XmlUtil",
			// "xml can not be save, exception is --- " + e.getMessage());
		}
	}

	public static List selectNodes(Object doc, String key) {
		return XPathFactory.instance().compile(key).evaluate(doc);
	}

	public static Object selectSingleNode(Object doc, String key) {
		return XPathFactory.instance().compile(key).evaluateFirst(doc);
	}

	/**
	 * 去元素全路径，去掉class中包含的数字
	 * 
	 * @param e
	 * @return
	 */
	public static String getPath(Element e) {

		Element p = null;
		String path = e.getName();
		String attr = e.getAttributeValue("class");
		if (attr != null) {
			attr = attr.trim();
			/* eg: class="line21" , 去掉这种形式的class中的数字 */
			attr = attr.replaceAll("(\\d)+.*$", "");
			path = path + "[contains(@class,'" + attr + "')]";
		}

		if (e.getParentElement() != null) {
			path = getPath(e.getParentElement()) + "/" + path;
		}

		return path;
	}

	/**
	 * 
	 * @param e
	 * @param level
	 *            向上取几层,如取 1 层，则结果xpath会是类似 //aaa/bbb 的形式
	 * @return
	 */
	public static String getPath(Element e, int level) {

		Element p = null;
		String path = e.getName();
		String attr = e.getAttributeValue("class");/*
													 * TODO
													 * 可以多加一些属性标记，便于判断？比如name
													 * id等
													 */
		if (attr != null) {
			attr = attr.trim();
			/* eg: class="line21" , 去掉这种形式的class中的数字 */
			attr = attr.replaceAll("(\\d)+.*$", "");
			path = path + "[contains(@class,'" + attr + "')]";
		}

		if (e.getParentElement() != null && level > 0) {
			path = getPath(e.getParentElement(), level - 1) + "/" + path;
		}

		return path;
	}

	public static String getPathWithIndex(Element curElement,
			Element rootElement, String path) {
		List<Element> list = X.getSubElementList(rootElement, path);
		if (list.size() > 1) {
			String curStr = StringUtils.normalizeSpace(curElement.getValue());
			int i = 1;
			for (Element subElement : list) {
				String subValueStr = StringUtils.normalizeSpace(subElement
						.getValue());
				if (StringUtils.isNotBlank(subValueStr)
						&& subValueStr.equals(curStr)) {
					path = "(" + path + ")[" + i + "]";
					return path;

				}
				i++;
			}
		}
		return path;
	}

	public static String getSubPath(String path, String parentPath) {

		String returnString = "."
				+ StringUtils.substringAfter(path, parentPath);

		// //System.out.println("path         "+ path);
		// //System.out.println("parentPath   "+ parentPath);
		// //System.out.println("returnString "+ returnString);

		return returnString;
	}

	public static List<Element> getSubElementList(Object root, String xpath) {
		if (root == null) {
			return new ArrayList<Element>();
		}

		XPathFactory xpfac = XPathFactory.instance();
		XPathExpression xp = xpfac.compile(xpath);
		List list = xp.evaluate(root);
		if (list != null) {
			return (List<Element>) list;
		}

		return new ArrayList<Element>();
	}
}
