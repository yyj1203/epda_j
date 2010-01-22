package com.kps.epm.dao

import com.ibatis.sqlmap.client.SqlMapClient

trait XmlHandle {
  def getXml(sqlMapClient:SqlMapClient, sqlCommand:String, param:java.util.HashMap[String, Object]) = {
    val xmlRowHandler = new XmlRowHandler    
    sqlMapClient.queryWithRowHandler(sqlCommand, param, xmlRowHandler)
    xmlRowHandler.getXml     
  }
}
