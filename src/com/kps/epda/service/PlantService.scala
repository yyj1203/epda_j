package com.kps.epda.service

import java.util.{Map, HashMap}

import com.kps.common.dao.SqlMapBaseDao
import com.kps.epda.dao.CodeSetObject
import com.kps.epda.dao.PlantDao
import com.kps.pm.saprfc.Z_PM_GET_TPLNRLIST
import com.kps.common.dto.CodeMap

import com.kps.jruby.SuperLogin

class PlantService {
  var plantDao: PlantDao = _    
  var sqlMapBaseDao: SqlMapBaseDao = _
  var superLogin: SuperLogin = _
  def setSuperLogin(login: SuperLogin) = this.superLogin = login
  def setPlantDao(plantDao: PlantDao) = this.plantDao = plantDao
  def setSqlMapBaseDao(sqlMapBaseDao: SqlMapBaseDao) = this.sqlMapBaseDao = sqlMapBaseDao
    
  def getPlantCodeList = plantDao.getPlantCodeList("")  
  def getPlantCodeList(devSrc:String) = plantDao.getPlantCodeList(devSrc)
  
  def getMPlantCodeList(splant:String) = plantDao.getMPlantCodeList(splant)
  def getSECList(hashMap:HashMap[String, Object]) = plantDao.getSECList(hashMap)
  def getGroupCodeList(hashMap:HashMap[String, Object]) = plantDao.getGroupCodeList(hashMap)  
  def getCommonCode(hashMap:HashMap[String, Object]) = sqlMapBaseDao.queryForList("getCommonCode", hashMap)  
  def getEquipObjectCode() = CodeSetObject.getCodeListById("CS4100", "CODE_ENGLISH_NM", "CODE_NM")
 
  def getTplnrList(hashMap:HashMap[String, Object]) = {     
    println(hashMap)
    val rfc = new Z_PM_GET_TPLNRLIST
    val result = rfc.execute(hashMap)  
    val output:List[java.util.HashMap[String,Object]] = result.get("T_OUTPUT").asInstanceOf[List[java.util.HashMap[String,Object]]]
    println(output)
    var list = new java.util.ArrayList[CodeMap]
    /*if (output != null) {
      output.foreach(s => {
        list.add(new CodeMap(s.get("TPLNR").toString, s.get("TPLNR").toString))
      })
    }*/
    //val codeList = output.map(s => new CodeMap(s.get("TPLNR").toString, s.get("TPLNR").toString))
    list
  }
  

  
}
