package com.kps.epm.dao

import _root_.scala.collection.jcl.Conversions
import com.ibatis.sqlmap.client.SqlMapClient
import com.kps.epm.util._

class MeasDevDao {
  val sqlMapClient = EpdaDevDBHelper.getSqlMapClient   
  def insertMeas(meas:java.util.HashMap[String, Object]):Unit = {
    sqlMapClient.insert("insert", meas)    
  }
  
  def insertList(list:List[Object]) = {
    //val array = java.util.Arrays.asList(jlist).toArray
    //val list = List.fromArray(array)
    //list.map(o => println(o.toString))
    
    sqlMapClient.startTransaction
    try {
      sqlMapClient.startBatch
        for(meas <- list) {
          val m = meas.asInstanceOf[java.util.HashMap[String,Object]]
          println(m.get("POINT_SEQ") + " => " + m.get("MEAS_SPT").toString)
          sqlMapClient.insert("insert", meas)  
        }
      sqlMapClient.executeBatch
      sqlMapClient.commitTransaction      
    } finally {
      sqlMapClient.endTransaction
    }
  }
  
  def insertCgitemList(list:List[Object]) = {
       sqlMapClient.startTransaction
    try {
      sqlMapClient.startBatch
        for(cgitem <- list) {
          //val m = meas.asInstanceOf[java.util.HashMap[String,Object]]
          //println(m.get("POINT_SEQ") + " => " + m.get("MEAS_SPT").toString)
          sqlMapClient.insert("insertCgitem", cgitem)  
        }
      sqlMapClient.executeBatch
      sqlMapClient.commitTransaction      
    } finally {
      sqlMapClient.endTransaction
    }
  }
  
  def insertCsgrpList(list:List[Object]) = {
       sqlMapClient.startTransaction
    try {
      sqlMapClient.startBatch
        for(csgrp <- list) {
          //val m = meas.asInstanceOf[java.util.HashMap[String,Object]]
          //println(m.get("POINT_SEQ") + " => " + m.get("MEAS_SPT").toString)
          sqlMapClient.insert("insertCsgrp", csgrp)  
        }
      sqlMapClient.executeBatch
      sqlMapClient.commitTransaction      
    } finally {
      sqlMapClient.endTransaction
    }
  }
  
}
