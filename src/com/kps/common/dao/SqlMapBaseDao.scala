package com.kps.common.dao

import java.util.{Map, HashMap}
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport

class SqlMapBaseDao extends SqlMapClientDaoSupport {
  def queryForList(id:String, hashMap:HashMap[String, Object]) = {
    getSqlMapClientTemplate().queryForList(id, hashMap)
  }  
}
