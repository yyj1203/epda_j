
package com.kps.epm.dao

import scala.collection.mutable._
import scala.xml.{ UnprefixedAttribute, Elem, Node, Null, Text, TopScope } 
import com.ibatis.sqlmap.client.event.RowHandler


class XmlRowHandler extends RowHandler {
  //// Elem(null, "root", Null, TopScope)
  var xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
  xml.append("<root>")
  override def handleRow(mapObj:Object) = {
    addXml(mapObj)
  }  
  
  def getXml = {
    xml.append("</root>")
    xml.toString
  }
  
  private def addXml(obj:Object):Unit = {
    val map:java.util.HashMap[String,Object] = obj.asInstanceOf[java.util.HashMap[String,Object]]
    val keyset = map.keySet.iterator
    xml.append("<data>")
    while (keyset.hasNext) {
      var key = keyset.next;
      var value = (map.get(key)).asInstanceOf[String]  
      value = if (value == null) " " else value
      val row = Elem(null, key, Null, TopScope, Text(value))
      xml.append(" " + row)         
    }
    xml.append("</data>")  
  }
  
}
