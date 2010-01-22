package com.kps.epda.dao

import java.util.{Map, HashMap}

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport

class EmpDao extends SqlMapClientDaoSupport {  
  def getEmpList() = {    
    getSqlMapClientTemplate().queryForList("getEmpList")    
  }
  
  def getEmpList_paged(param:java.util.Map[String,Object]) = {     
    getSqlMapClientTemplate().queryForList("getEmpList_paged", param)
  }
  
  def getEmpList_count(param:java.util.Map[String,Object]) = {     
    getSqlMapClientTemplate().queryForObject("getEmpList_count", param)
  }
  
        
}
