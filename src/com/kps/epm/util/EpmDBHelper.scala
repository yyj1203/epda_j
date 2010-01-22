package com.kps.epm.util

import java.io.Reader

import com.ibatis.common.resources.Resources
import com.ibatis.sqlmap.client.SqlMapClient
import com.ibatis.sqlmap.client.SqlMapClientBuilder

object EpmDBHelper {
  val helper = new EpmDBHelper
  def getSqlMapInstance:SqlMapClient = {
    helper.getSqlMapClient
  }
}

class EpmDBHelper() {
  var globalReader:Reader = null
  var sqlMapClient:SqlMapClient = null
  try {
    globalReader = Resources.getResourceAsReader("sqlmap_epm/sqlmap-epm.xml")
    sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(globalReader)
  }	finally {
    if (globalReader != null) globalReader.close()   
  }	
  
  def getSqlMapClient = sqlMapClient
}
