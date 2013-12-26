package com.hmart.common.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * 
 * XML데이터 분석하여 데이터를 도출한다.
 * XML데이터를 DOM방식으로 데이터를 파싱하여 데이터를 추출한다.
 * 
 * @author hongmin.an
 *
 */
public class XmlParseData {
	private Document document=null;
	private StringBuffer msg=new StringBuffer();
	private boolean isValidate=false;
	
	public XmlParseData() {
		isValidate=false;
	}
	
	public XmlParseData(String data) {
		isValidate=false;
		parse(data);
	}
	
	public XmlParseData(Document document) {
		setDocument(document);
	}
	
	public void parse(String xmlData) {	
		isValidate=false;
		try{
      		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      		factory.setIgnoringElementContentWhitespace(true);
      		DocumentBuilder builder = factory.newDocumentBuilder();
  			document = builder.parse(new InputSource(new StringReader(xmlData)));
  			isValidate=true;
		}catch(Exception e) {
			isValidate=false;
			setErrLog(e.toString());
		}
	}
	
	public boolean isValidate() {
		return isValidate;
	}
	
	public String getValue(String tagName) {
		String rtnValue=null;
		if(document!=null) {
			try{
				rtnValue=document.getElementsByTagName(tagName).item(0).getFirstChild().getNodeValue();
				if(rtnValue.equals("")) {
					rtnValue=null;
				}
			}catch(Exception e) {
				setErrLog(e.toString());
				rtnValue=null;
			}
		}
		return rtnValue;
	}
	
	public String []getValues(String tagName) {
		String []rtnValue=null;
		if(document!=null) {
			try{
				NodeList nodeList=document.getElementsByTagName(tagName);
				rtnValue=new String[nodeList.getLength()];
				for(int i=0; i<nodeList.getLength(); i++) {
					if(nodeList.item(i).getFirstChild()!=null) {
						rtnValue[i]=nodeList.item(i).getFirstChild().getNodeValue();
					}else{
						rtnValue[i]="";
					}
				}
				if(nodeList.getLength()<=0) {
					rtnValue=null;
				}
			}catch(Exception e) {
				setErrLog(e.toString());
				rtnValue=null;
			}
		}
		return rtnValue;
	}
	
	public ArrayList<HashMap<String, String>> getItemValues(String tagName) {
		ArrayList<HashMap<String, String>> rtnList = new ArrayList<HashMap<String, String>>();
		if(document!=null) {
			try{ 
				NodeList itmes=document.getElementsByTagName(tagName);

				for(int i=0; i<itmes.getLength(); i++) {
					Node item = itmes.item(i);// item
					NodeList itemList = item.getChildNodes();
					HashMap<String, String> map = new HashMap<String, String>();
					for(int j=0; j < itemList.getLength(); j++){
						Node subNode = itemList.item(j);
						if(subNode.getNodeType() == Node.ELEMENT_NODE){
							if(subNode.getFirstChild()!=null && subNode.getFirstChild().getNodeValue()!=null && subNode.getNodeName() !=null){
								map.put(subNode.getNodeName(), subNode.getFirstChild().getNodeValue());
							}
						}
					}
					rtnList.add(map);					
				}
			}catch(Exception e) {
				setErrLog(e.toString());
				rtnList=null;
			}
		}
		return rtnList;
	}
	
	public int getIntValue(String tagName) {
		int rtnValue=-1;
		if(document!=null) {
			try{
				String temp=document.getElementsByTagName(tagName).item(0).getFirstChild().getNodeValue();
				if(temp.equals("")) {
					rtnValue=-1;
				}else{
					rtnValue=Integer.parseInt(temp);
				}
			}catch(Exception e) {
				setErrLog(e.toString());
				rtnValue=-1;
			}
		}
		return rtnValue;
	}
	
	public String getAttrValue(String tagName, String attr) {
		String rtnValue=null;
		if(document!=null) {
			try{
				Node node=document.getElementsByTagName(tagName).item(0);
				if(node.hasAttributes()) {
					rtnValue=node.getAttributes().getNamedItem(attr).getNodeValue();
				}else{
					rtnValue=null;
				}				
				if(rtnValue.equals("")) {
					rtnValue=null;
				}
			}catch(Exception e) {
				setErrLog(e.toString());
				rtnValue=null;
			}
		}
		return rtnValue;
	}
	
	public String []getAttrValues(String tagName, String attr) {
		String []rtnValue=null;
		if(document!=null) {
			try{
				NodeList nodeList=document.getElementsByTagName(tagName);
				rtnValue=new String[nodeList.getLength()];
				for(int i=0; i<nodeList.getLength(); i++) {
					Node node=nodeList.item(i);
					if(node!=null && node.hasAttributes()) {
						rtnValue[i]=node.getAttributes().getNamedItem(attr).getNodeValue();
					}else{
						rtnValue[i]="";
					}
				}
				if(nodeList.getLength()<=0) {
					rtnValue=null;
				}
			}catch(Exception e) {
				setErrLog(e.toString());
				rtnValue=null;
			}
		}
		return rtnValue;
	}
	
	private void setErrLog(String errMsg) {
		msg.append(errMsg+"\r\n");
	}
	
	public String getErrLog() {
		return msg.toString();
	}
	
	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
}