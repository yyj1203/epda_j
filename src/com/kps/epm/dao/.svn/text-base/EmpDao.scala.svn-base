package com.kps.epm.dao

import com.ibatis.sqlmap.client.SqlMapClient

class EmpDao extends XmlHandle {  
  def getEmpList(sqlMapClient:SqlMapClient) = sqlMapClient.queryForList("getEmpList")
  def getEmpListAsXML(sqlMapClient:SqlMapClient) = getXml(sqlMapClient, "getEmpList", null)    
}
