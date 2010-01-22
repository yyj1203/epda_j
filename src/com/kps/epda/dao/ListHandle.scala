package com.kps.epda.dao

import com.ibatis.sqlmap.client.SqlMapClient
import org.springframework.orm.ibatis.SqlMapClientTemplate

trait ListHandle {
  def getList(sqlMapClient:SqlMapClientTemplate, sqlCommand:String):List[Object] = {
    getList(sqlMapClient, sqlCommand, null)
  }
  
  def getList(sqlMapClient:SqlMapClientTemplate, sqlCommand:String, param:java.util.HashMap[String, Object]):List[Object] = {
    val listHandler = new ScalaListRowHandler    
    sqlMapClient.queryWithRowHandler(sqlCommand, param, listHandler)
    listHandler.getList
  }
}
