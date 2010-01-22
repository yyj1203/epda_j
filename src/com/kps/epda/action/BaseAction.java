package com.kps.epda.action;

import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * ActionSupport���
 * ParameterAware����
 * SessionAware����
 * @author kps
 *
 */
public class BaseAction extends ActionSupport implements ParameterAware, SessionAware{
	
	protected Map parameterMap; /**�Ķ���� ��*/
	
	protected Map session; /**����*/
	
	protected InputStream inputStream; /**��ǲ ��Ʈ��*/	
	
	protected String paging; /**����¡ */
	
	
	/*************�������̽� �����޼ҵ�*****************/
	/**
	 * extractParameterMap()�޼ҵ�� �Ķ���� �ʱ�ȭ�۾�
	 */
	public void setParameters(Map parameterMap) {
		this.parameterMap = extractAndDecodeParameterMap(parameterMap);
	}
	
	//@Override
	public void setSession(Map session) {
		this.session = session;
	}
	
	
	/****************getter, setter********************/
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	/**
	 * �Ķ���͸� ��ü�� ���� �����ϱ����� �޼ҵ�
	 * @return
	 */
	public Map getParameterMap(){
		return parameterMap;
	}

	public String getPaging() {
		return paging;
	}
	
	
	/*****************��Ÿ����********************/
	
	/**
	 * setParameters()�޼ҵ� ����ÿ� �ڵ�ȣ���
	 * parameterAware�� ������ �Ķ���͵��� String[]Ÿ������ �޾ƿ��⶧����
	 * �ʿ��� �� �迭�� ù��° ���ڸ� ��� �ٽ� parameterMap�� ����ֱ� ���� �޼ҵ�
	 */
	public Map extractParameterMap(Map parameterMap){
		if(parameterMap.size()>0){
			Set keys = parameterMap.keySet();
			
			Iterator it = keys.iterator();
			
			while(it.hasNext()){
				String key = (String)it.next();
				String[] values = (String[])parameterMap.get(key);
				//�Ķ���ͷ� ���� ���ڰ��� �Ѱ� �����϶� ��Ʈ���迭���� ��Ʈ�������� �־��ش�
				if(values.length <= 1){
					parameterMap.put(key, values[0]);
				}
			}
		}
		return parameterMap;
		
	}
	
	/**
	 * setParameters()�޼ҵ� ����ÿ� �ڵ�ȣ���
	 * parameterAware�� ������ �Ķ���͵��� String[]Ÿ������ �޾ƿ��⶧����
	 * �ʿ��� �� �迭�� ù��° ���ڸ� ��� �ٽ� parameterMap�� ����ֱ� ���� �޼ҵ�
	 * �߰��� UTF-8 ���� Decode����
	 */
	public Map extractAndDecodeParameterMap(Map parameterMap){
		if(parameterMap.size()>0){
			Set keys = parameterMap.keySet();
			
			Iterator it = keys.iterator();
			
			while(it.hasNext()){
				String key = (String)it.next();
				String[] values = (String[])parameterMap.get(key);
				//UTF-8�� ���ڵ��Ѵ�
				decodeUTF8( values );
				//�Ķ���ͷ� ���� ���ڰ��� �Ѱ� �����϶� ��Ʈ���迭���� ��Ʈ�������� �־��ش�
				if(values.length <= 1){
					parameterMap.put(key, values[0]);
				}
			}
		}
		return parameterMap;
		
	}
	
	/**
	 * String[]�� �ִ°����� UTF-8�������� ���ڵ��Ѵ�
	 */
	public void decodeUTF8( String[] array) {
		try{
			for(int i=0; i<array.length; i++){
				array[i] = URLDecoder.decode(array[i], "UTF-8");
			}
		}catch( Exception e){
			System.out.println(e);
		}
	}
	
	/**
	 * �Ķ���͸��� UTF-8�������� ���ڵ��Ѵ�
	 */
	public void decodeParameterMapUTF8() throws Exception{
		if(parameterMap.size()>0){
			Set keys = parameterMap.keySet(); //Ű���� �����Ѵ�
			
			Iterator it = keys.iterator();
			
			while(it.hasNext()){
				String key = (String)it.next();
				if( parameterMap.get(key) instanceof String[]){ //��Ʈ���迭�̸�
					String[] values = (String[])parameterMap.get(key);
					for(int i=0; i<values.length; i++){
						values[i] = URLDecoder.decode(values[i], "UTF-8");
					}
				}else if( parameterMap.get(key) instanceof String ){ //��Ʈ���̸�
					String value = (String)parameterMap.get(key);
					parameterMap.put(key, URLDecoder.decode(value, "UTF-8"));
				}
			}
		}
	}

	/**
	 * parameterAware�� ������ �Ķ���͵��� String[]Ÿ������ �޾ƿ��⶧����
	 * �ʿ��� �� �迭�� ù��° ���ڸ� ��� �ٽ� parameterMap�� ����ֱ� ���� �޼ҵ�
	 */
	public void extractParameterMap(){
		if(parameterMap.size()>0){
			Set keys = parameterMap.keySet();
			
			Iterator it = keys.iterator();
			
			while(it.hasNext()){
				String key = (String)it.next();
				String[] values = (String[])parameterMap.get(key);
				//�Ķ���ͷ� ���� ���ڰ��� �Ѱ� �����϶� ��Ʈ���迭���� ��Ʈ�������� �־��ش�
				if(values.length <= 1){
					parameterMap.put(key, values[0]);
				}
			}
		}
		
	}
	
	/**
	 * �Ķ���ͷ� ���� ������ ����Ѵ�
	 */
	public void printParameterMap(){
		System.out.println("--------------------------------");
		System.out.println("parameterMap size: "+parameterMap.size());
		if(parameterMap.size()>0){
			Set keys = parameterMap.keySet();
			
			Iterator it = keys.iterator();
			
			while(it.hasNext()){
				String key = (String)it.next();
				//��Ʈ�� �迭�� ���ڸ� ������
				if(parameterMap.get(key) instanceof String[]){
					String[] values = (String[])parameterMap.get(key);
					for(int i=0; i<values.length; i++){
						System.out.println(key+"["+i+"]: "+values[i]);
					}
				//�׿� ���� ���ڷ� ������
				}else {
					System.out.println(key+": "+parameterMap.get(key));
				}
				
			}
		}
		System.out.println("--------------------------------");
	}

}