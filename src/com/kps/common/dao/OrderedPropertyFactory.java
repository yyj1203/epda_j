package com.kps.common.dao;

/*******************************************************************
 * ����KPS WPS
 * - JCO ������Ƽ ������ �ε��Ѵ�.
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
 * jco.properties ������ �ε��Ѵ�.
 *  
 * @author hwlee
 *
 */
public class OrderedPropertyFactory 
{
	private static OrderedProperties jcoProperties = null;
	
	/**
	 * jco.properties ���� �ε��Ͽ� ��������� �Ҵ�
	 * 
	 * @throws Exception
	 */
	private OrderedPropertyFactory() throws Exception
	{
		jcoProperties = OrderedProperties.load("/jco.properties");
	}
	
	/**
	 * jco.properties ������ �ε�Ǿ����� �ε�� ��ü�� ��ȯ�ϰ�, �ε���� �ʾ����� �ε��Ͽ� ��ȯ�Ѵ�.
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
