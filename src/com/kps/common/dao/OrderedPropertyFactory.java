package com.kps.common.dao;

/*******************************************************************
 * 한전KPS WPS
 * - JCO 프로퍼티 파일을 로드한다.
 * 
 * Copyright (c) 2007 by LG CNS, Inc.
 * All rights reserved.
 *******************************************************************
 * $Id: OrderedPropertyFactory.java,v 1.4 2008/03/10 05:43:04 ksm Exp $
 * 
 * @author $Author: ksm $
 * @version $Revision: 1.4 $
 */

import com.kps.util.OrderedProperties;

/**
 * jco.properties 파일을 로드한다.
 *  
 * @author hwlee
 *
 */
public class OrderedPropertyFactory 
{
	private static OrderedProperties jcoProperties = null;
	
	/**
	 * jco.properties 파일 로드하여 멤버변수에 할당
	 * 
	 * @throws Exception
	 */
	private OrderedPropertyFactory() throws Exception
	{
		jcoProperties = OrderedProperties.load("/jco.properties");
	}
	
	/**
	 * jco.properties 파일이 로드되었으면 로드된 객체를 반환하고, 로드되지 않았으면 로드하여 반환한다.
	 * 
	 * @return
	 * @throws Exception
	 */
	public static synchronized OrderedProperties getInstance() throws Exception
	{
    	if ( jcoProperties == null ) 
    	{
    		new OrderedPropertyFactory() ;
    	}
		
    	return jcoProperties ;
	}		
}
