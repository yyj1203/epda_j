package com.kps.epda.util;

public class Constants 
{
	/**
	 * xwork.xml에 정의된 문자열과 맵핑됨
	 * 사용자 인증 또는 세션아웃에 의해 처음 로그인 페이지로 분기 시킬 JSP 페이지 링크함
	 */
	public static String LOGIN_PAGE = "unauthorized";
	/**
	 * 세션에 저장된 UserSession(사용자 정보를 담고 있는 bean 객체) 객체 키값
	 */
	public static String LOGIN_USER = "loginuser";
	
	/**
	 * 개인 소속정보를 설정하는 페이지
	 */
	public static String PERSONAL_PAGE = "initEmpForm";
	
	/**
	 * SAP EP 로그인하기 위한 환경파일 패스 및 정보
	 */
	public static String MYSAPCOOKIENAME 	= "com.kps.mysapcookiename";
	public static String KEYSTORE_PATH   	= "com.kps.keystorepath";
	public static String KEYSTORE_PASSWORD 	= "com.kps.keystorepassword";
	
	public static String UPLOAD_UNIX_PATH = "/oracle/EPM/bin/tomcat6/webapps/epda/uploads/";
    public static String UPLOAD_WINDOWS_PATH = "C://epda/uploads/";
    
    
    public static final String FN_NAME_HR_INFO = "ZHR_PERNR_INFORMATION2";      //사원 인사정보 조회 함수
    public static final String TB_NAME_HR_INFO = "ITAB1";                       //사원 인사정보 조회 테이블
    
    public static final String FN_NAME_ZHR_GET_IBSALIST = "ZHR_GET_IBSALIST";   //사원 인사정보 신규등록자 리스트 조회 함수
    public static final String TB_NAME_ZHR_GET_IBSALIST = "ITAB1";              //사원 인사정보 신규등록자 리스트 조회 테이블
    
	
}	
