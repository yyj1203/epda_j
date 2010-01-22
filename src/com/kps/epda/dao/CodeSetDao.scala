package com.kps.epda.dao

//import java.util.{Map, HashMap}
import scala.collection.mutable.{Map, SynchronizedMap, HashMap, ListBuffer}

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport

import com.kps.common.dto.CodeMap

object MapMaker {
  def makeMap: Map[String, Object] = {
    new HashMap[String, Object] with SynchronizedMap[String, Object] {
      override def default(key: String) = "?"
    }
  }
}

object CodeSetObject {
  val codeMap:Map[String, Object] = MapMaker.makeMap
  val codeSetIds:Map[String, Object] = MapMaker.makeMap
  val codeSetNames:Map[String, Object] = MapMaker.makeMap 
  val codesByCodeSetName:Map[String, Object] = MapMaker.makeMap
  
  def init() = {
    codeMap.clear
    codeSetNames.clear
  }
  
  def start() = {    
    println("started")
  }
  
  def getCodesByCodeSetName(name:String) = {
    val codeSetId:Option[Object] = codeSetNames.get(name)
    getCodesByCodeSetId(codeSetId.get + "")
  }
  
  def getCodesByCodeSetId(id:String) = {
    //val codeList:Option[Object] = codeMap.get(id) 
    //codeList.get
    codeMap(id)
  }
  
    
  def addCodeMap(key:String, value:List[Object]) = {
    //codeMap.put(key, value);      
    codeMap += (key->value)
  }
  
  def addCodeSetNameMap(key:String, value:Object) = {
    codeSetNames.put(key, value)
  }
  
  def convertCodeList(source:List[Object], dataField:String, labelField:String) = {
    var list = new java.util.ArrayList[CodeMap]
    source.foreach(code => {
      val obj = code.asInstanceOf[java.util.HashMap[String,String]]         
      //val map = Map("data" -> obj.get(labelField), "label" -> obj.get(labelField))
      val map = new CodeMap()
      map.setData(obj.get(dataField))
      map.setLabel(obj.get(labelField))      
      list.add(map)
    })
    list    
  }
  
  def getCodeListById(id:String, labelField:String, dataField:String) = {
    val code = codeMap(id)
    convertCodeList(code.asInstanceOf[List[Object]], labelField, dataField)
  }  
}

class CodeSetDao extends SqlMapClientDaoSupport with ListHandle {
  def init() = {
    CodeSetObject.init
    val codeSets = getCodeSets
    codeSets.foreach(codeSet=> {
      val obj = codeSet.asInstanceOf[java.util.HashMap[String,Object]]
      val codeSetId = obj.get("CODE_SET_ID")
      val codeSetName = obj.get("CODE_SET_NAME")
      val codeList = getCodesByCodeSetId(obj.get("CODE_SET_ID"))      
      CodeSetObject.addCodeMap(codeSetId+"", codeList)
      CodeSetObject.addCodeSetNameMap(codeSetName+"", codeSetId)       
    }) 
        
  }
  
  def getCodesByCodeSetName(codeSetName:String) = {
    CodeSetObject.getCodesByCodeSetName(codeSetName)
  }
  
  def getCodesByCodeSetId(id:String) = {
    CodeSetObject.getCodesByCodeSetId(id)
  }
    
  private def getCodeSets() = {    
    getList(getSqlMapClientTemplate(), "selectAllCodeSet")    
  }
  
  private def getCodesByCodeSetId(codeSetId:Object) = {
    val map = new java.util.HashMap[String, Object]
    map.put("CODE_SET_ID", codeSetId)
    getList(getSqlMapClientTemplate(), "selectCodeByCodeSetID", map) 
  }
  
}
