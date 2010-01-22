/*******************************************************************
 * ÇÑÀüKPS WPS
 *
 * Copyright (c) 2010 by KPS.
 * All rights reserved. 
 */
package com.kps.epda.action;

/**
 * @author user
 *
 */

class EmpAction extends BaseAction {
  public String listEmp()  {    
    def xml = "<groot/>" 
    println xml
    setInputStream(new ByteArrayInputStream(xml.getBytes("UTF-8")));
    "success"
  }    
}
