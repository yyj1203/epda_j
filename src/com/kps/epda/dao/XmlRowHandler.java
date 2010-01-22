package com.kps.epda.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.ibatis.sqlmap.client.event.RowHandler;

public class XmlRowHandler implements RowHandler {
	private StringBuffer xmlDocument = new StringBuffer();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //날짜포맷
	private String name = null;
	private String returnValue = null;
	
	/** 기본 생성자 */
	public XmlRowHandler(){
		this.name = "data";
		initXmlDocument();
	}
	
	/** xmlDocument의 이름을 지정하는 생성자 */
	public XmlRowHandler(String name){
		this.name = name;
		initXmlDocument();
	}
	
	/** dateFormat형식을 새로 지정한다 */
	public void setDateFormat(String dateFormat){
		this.sdf = new SimpleDateFormat(dateFormat);
	}
	
	private void initXmlDocument(){
		xmlDocument.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xmlDocument.append("<root>");
	}

	//@Override
	public void handleRow(Object valueObject) {
		HashMap hashMap = (HashMap)valueObject;
		Object[] keys = hashMap.keySet().toArray();
		
		xmlDocument.append("<"+name+">");
		for(int j=0; j<keys.length; j++){
			xmlDocument.append("<"+keys[j]+">");
			if(hashMap.get(keys[j]) != null){
				if( hashMap.get(keys[j]) instanceof Date){
					xmlDocument.append( sdf.format(hashMap.get(keys[j])) );
				}else{
					xmlDocument.append(hashMap.get(keys[j]));
				}
			}
			xmlDocument.append("</"+keys[j]+">");
		}
		xmlDocument.append("</"+name+">");
	}
	
	/** xmlList를 얻는다 */
	public String getListXml(){
		if( returnValue == null ){
			xmlDocument.append("</root>");
			returnValue = xmlDocument.toString();
		}
		return returnValue;
	}
	
	/** xmlList를 얻는다 */
	public String getListXml(int totNum){
		if( returnValue == null ){
			xmlDocument.append("<totNum>"+totNum+"</totNum>");
			xmlDocument.append("</root>");
			returnValue = xmlDocument.toString();
		}
		return returnValue;
	}

}
