package com.kps.epm.dao

import com.ibatis.sqlmap.client.SqlMapClient
import com.kps.epm.util._
//import com.kps.epm.util.SqlClientManager._

class MeasRealDao extends ListHandle with XmlHandle {  
  val sqlMapClient = EpdaRealDBHelper.getSqlMapClient   
  def getColumnJavaList(param:java.util.HashMap[String, Object]) = sqlMapClient.queryForList("getColumnList", param)  
  def getColumnListAsXML(param:java.util.HashMap[String, Object]) = getXml(sqlMapClient, "getColumnList", param)
  def getColumnScalaList(param:java.util.HashMap[String, Object]) = getList(sqlMapClient, "getColumnList", param)
  
  def getMeasList(param:java.util.HashMap[String, Object]):List[Object] = getList(sqlMapClient, "select", param)
  def getCgitemList(param:java.util.HashMap[String, Object]):List[Object] = getList(sqlMapClient, "getCgitemList", param)
  def getCsgrpList(param:java.util.HashMap[String, Object]):List[Object] = getList(sqlMapClient, "getCsgrpList", param)
  
  /*
  def getCurrentInventory() = {
        txn[java.util.List[Book]] {
            val q = EmThreadLocal.get().createQuery("from Book as bk where bk.stock > 0")
            q.getResultList.asInstanceOf[java.util.List[Book]]
        }
    } 
  */
}
