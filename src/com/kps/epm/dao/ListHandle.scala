package com.kps.epm.dao

import com.ibatis.sqlmap.client.SqlMapClient

trait ListHandle {
  def getList(sqlMapClient:SqlMapClient, sqlCommand:String, param:java.util.HashMap[String, Object]):List[Object] = {
    val listHandler = new ScalaListRowHandler    
    sqlMapClient.queryWithRowHandler(sqlCommand, param, listHandler)
    listHandler.getList
  }
}
