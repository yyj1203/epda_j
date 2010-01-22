package com.kps.epda.dao

import java.util.{Map, HashMap}

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport

class MeasDao extends SqlMapClientDaoSupport { 
  
  def getMeasList_paged(param:java.util.Map[String,Object]) = {     
    getSqlMapClientTemplate().queryForList("getMeasList_paged", param)
  }
  
  def getMeasList_count(param:java.util.Map[String,Object]) = {     
    getSqlMapClientTemplate().queryForObject("getMeasList_count", param)
  }
  
  def getMeasTrend(param:java.util.Map[String,Object]) = {
    getSqlMapClientTemplate().queryForObject("getMeasTrend", param)
  }
}

