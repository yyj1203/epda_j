/*******************************************************************
 * ÇÑÀüKPS WPS
 *
 * Copyright (c) 2010 by KPS.
 * All rights reserved. 
 */
package com.kps.epda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;

import com.kps.epda.dao.CommonDao; 
import com.kps.epda.util.FlexSessionHandler;


@Service("commonService")
@RemotingDestination(channels=["my-amf"])
class CommonService {
  @Autowired
  private FlexSessionHandler flexSessionHandler
  
  @Autowired
  private CommonDao commonDao
  
  def ips = ["172.16.77.157", "127.0.0.1"]
  
  public Map getUserSession() throws Exception {    
    def userSession = flexSessionHandler.getUserSession()   
    if (userSession == null) {
      if (flexSessionHandler.getRemoteIP() in ips) {
        Map parameterObject = [EMP_NO:'1932452']
        userSession = commonDao.queryForObject("getEmpById", parameterObject)
      }
    }
    println("kkkk");
    return userSession
  }
}
