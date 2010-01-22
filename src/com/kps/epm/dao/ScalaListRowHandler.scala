package com.kps.epm.dao

import scala.collection.mutable.ListBuffer;

import com.ibatis.sqlmap.client.event.RowHandler;

class ScalaListRowHandler extends RowHandler {
  val list = new ListBuffer[Object]
  override def handleRow(obj:Object) = {
    //val map:java.util.HashMap[String,Object] = obj.asInstanceOf[java.util.HashMap[String,Object]]
    //list += map.get("COLUMN_NAME").toString
    list += obj
  } 
  
  def getList:List[Object] = list.toList    
}
