package com.kps.epda.dao

import java.util.{Map, HashMap}
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport

class PlantDao extends SqlMapClientDaoSupport {
  def getPlantCodeList(devSrc:String) = { 
    var hashMap = new HashMap[String, Object]
    hashMap.put("DEV_SRC", devSrc)
    getSqlMapClientTemplate().queryForList("getPlantCodeList", hashMap)    
  }
  
  def getMPlantCodeList(splant:String) = {
    getSqlMapClientTemplate().queryForList("getMPlantCodeList", splant)    
  }
  
  def getSECList(hashMap:HashMap[String, Object]) = {
    getSqlMapClientTemplate().queryForList("getSECList", hashMap)
  }
  
  def getGroupCodeList(hashMap:HashMap[String, Object]) = {
    getSqlMapClientTemplate().queryForList("getGroupCodeList", hashMap)
  }
}

