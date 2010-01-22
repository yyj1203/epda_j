package com.kps.epda.dao;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport 

class CodeSetDao2 extends SqlMapClientDaoSupport {
  static def codeMap = [:]
  static def codeSetIds = [:]
  static def codeSetNames = [:]
  static def codeByCodeSetName = [:]
  static Object m_Object = new Object()

  def init() {
    
    List codeSets = getSqlMapClientTemplate().queryForList("getMeasureValues")
    
  }
  
}
