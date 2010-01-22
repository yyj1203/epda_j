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
 * ActionSupport상속
 * ParameterAware구현
 * SessionAware구현
 * @author kps
 *
 */
public class BaseAction extends ActionSupport implements ParameterAware, SessionAware{
	
	protected Map parameterMap; /**파라미터 맵*/
	
	protected Map session; /**세션*/
	
	protected InputStream inputStream; /**인풋 스트림*/	
	
	protected String paging; /**페이징 */
	
	
	/*************인터페이스 구현메소드*****************/
	/**
	 * extractParameterMap()메소드로 파라미터 초기화작업
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
	 * 파라미터맵 객체에 직접 접근하기위한 메소드
	 * @return
	 */
	public Map getParameterMap(){
		return parameterMap;
	}

	public String getPaging() {
		return paging;
	}
	
	
	/*****************기타로직********************/
	
	/**
	 * setParameters()메소드 실행시에 자동호출용
	 * parameterAware로 구현시 파라미터들이 String[]타입으로 받아오기때문에
	 * 필요한 각 배열의 첫번째 인자를 얻고 다시 parameterMap에 집어넣기 위한 메소드
	 */
	public Map extractParameterMap(Map parameterMap){
		if(parameterMap.size()>0){
			Set keys = parameterMap.keySet();
			
			Iterator it = keys.iterator();
			
			while(it.hasNext()){
				String key = (String)it.next();
				String[] values = (String[])parameterMap.get(key);
				//파라미터로 받은 인자값이 한개 이하일때 스트링배열에서 스트링값으로 넣어준다
				if(values.length <= 1){
					parameterMap.put(key, values[0]);
				}
			}
		}
		return parameterMap;
		
	}
	
	/**
	 * setParameters()메소드 실행시에 자동호출용
	 * parameterAware로 구현시 파라미터들이 String[]타입으로 받아오기때문에
	 * 필요한 각 배열의 첫번째 인자를 얻고 다시 parameterMap에 집어넣기 위한 메소드
	 * 추가로 UTF-8 형식 Decode수행
	 */
	public Map extractAndDecodeParameterMap(Map parameterMap){
		if(parameterMap.size()>0){
			Set keys = parameterMap.keySet();
			
			Iterator it = keys.iterator();
			
			while(it.hasNext()){
				String key = (String)it.next();
				String[] values = (String[])parameterMap.get(key);
				//UTF-8로 디코드한다
				decodeUTF8( values );
				//파라미터로 받은 인자값이 한개 이하일때 스트링배열에서 스트링값으로 넣어준다
				if(values.length <= 1){
					parameterMap.put(key, values[0]);
				}
			}
		}
		return parameterMap;
		
	}
	
	/**
	 * String[]에 있는값들을 UTF-8형식으로 디코드한다
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
	 * 파라미터맵을 UTF-8형식으로 디코드한다
	 */
	public void decodeParameterMapUTF8() throws Exception{
		if(parameterMap.size()>0){
			Set keys = parameterMap.keySet(); //키값을 추출한다
			
			Iterator it = keys.iterator();
			
			while(it.hasNext()){
				String key = (String)it.next();
				if( parameterMap.get(key) instanceof String[]){ //스트링배열이면
					String[] values = (String[])parameterMap.get(key);
					for(int i=0; i<values.length; i++){
						values[i] = URLDecoder.decode(values[i], "UTF-8");
					}
				}else if( parameterMap.get(key) instanceof String ){ //스트링이면
					String value = (String)parameterMap.get(key);
					parameterMap.put(key, URLDecoder.decode(value, "UTF-8"));
				}
			}
		}
	}

	/**
	 * parameterAware로 구현시 파라미터들이 String[]타입으로 받아오기때문에
	 * 필요한 각 배열의 첫번째 인자를 얻고 다시 parameterMap에 집어넣기 위한 메소드
	 */
	public void extractParameterMap(){
		if(parameterMap.size()>0){
			Set keys = parameterMap.keySet();
			
			Iterator it = keys.iterator();
			
			while(it.hasNext()){
				String key = (String)it.next();
				String[] values = (String[])parameterMap.get(key);
				//파라미터로 받은 인자값이 한개 이하일때 스트링배열에서 스트링값으로 넣어준다
				if(values.length <= 1){
					parameterMap.put(key, values[0]);
				}
			}
		}
		
	}
	
	/**
	 * 파라미터로 받은 값들을 출력한다
	 */
	public void printParameterMap(){
		System.out.println("--------------------------------");
		System.out.println("parameterMap size: "+parameterMap.size());
		if(parameterMap.size()>0){
			Set keys = parameterMap.keySet();
			
			Iterator it = keys.iterator();
			
			while(it.hasNext()){
				String key = (String)it.next();
				//스트링 배열로 인자를 받을때
				if(parameterMap.get(key) instanceof String[]){
					String[] values = (String[])parameterMap.get(key);
					for(int i=0; i<values.length; i++){
						System.out.println(key+"["+i+"]: "+values[i]);
					}
				//그외 단일 인자로 받을때
				}else {
					System.out.println(key+": "+parameterMap.get(key));
				}
				
			}
		}
		System.out.println("--------------------------------");
	}

}