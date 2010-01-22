/*******************************************************************
 * ÇÑÀüKPS WPS
 *
 * Copyright (c) 2010 by KPS.
 * All rights reserved. 
 */
package com.kps.epda.service

import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired 
import org.springframework.flex.remoting.RemotingDestination
import org.springframework.stereotype.Service 

import com.kps.epda.dao.CheckPointDao 
import com.kps.epda.util.Constants;
import com.kps.epda.util.ExcelProcessor;



@Service("pmtService")
@RemotingDestination(channels=["my-amf"])
class PmtService {
  private final Logger logger = LoggerFactory.getLogger(this.getClass())


  public String uploadPmt() {    
    return "d"  
  }
  
  
}
