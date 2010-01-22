package com.kps.epda.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

class CommonDao extends SqlMapClientDaoSupport {  
  public List queryForList(String statementName, Map parameterObject) {
    return getSqlMapClientTemplate().queryForList(statementName, parameterObject);
  } 
  
  public Object queryForObject(String statementName, Map parameterObject) {
    return getSqlMapClientTemplate().queryForObject(statementName, parameterObject);
  }
  
  public int update(String statementName, Map parameterObject) throws SQLException {   
    return getSqlMapClientTemplate().update(statementName, parameterObject);
  }
}
