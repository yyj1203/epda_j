package com.kps.epm.util

import java.io.Reader

import com.ibatis.common.resources.Resources
import com.ibatis.sqlmap.client.SqlMapClient
import com.ibatis.sqlmap.client.SqlMapClientBuilder

object EpdaDevDBHelper {
  val sqlMapClient = (new DBHelper("sqlmap_epm/sqlmap-epda-dev.xml")).getSqlMapInstance
  def getSqlMapClient:SqlMapClient = sqlMapClient    
}

object EpdaRealDBHelper {
  val sqlMapClient = (new DBHelper("sqlmap_epm/sqlmap-epda-real.xml")).getSqlMapInstance
  def getSqlMapClient:SqlMapClient = sqlMapClient    
}

object EpmDevDBHelper {
  val sqlMapClient = (new DBHelper("sqlmap_epm/sqlmap-epm.xml")).getSqlMapInstance
  def getSqlMapClient:SqlMapClient = sqlMapClient    
}

class DBHelper(sqlMapFileName: String) {
  var globalReader:Reader = null
  var sqlMapClient:SqlMapClient = null
  try {
    globalReader = Resources.getResourceAsReader(sqlMapFileName)
    sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(globalReader)
  }	finally {
    if (globalReader != null) globalReader.close()   
  }	  
  def getSqlMapInstance:SqlMapClient = sqlMapClient  
}

