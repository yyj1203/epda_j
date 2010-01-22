/*******************************************************************
 * ÇÑÀüKPS WPS Copyright (c) 2009 by KPS. All rights reserved.
 */
package com.kps.epda.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class MeasurementDao extends SqlMapClientDaoSupport {
       
    public List getMeasureValues(Map map) throws SQLException{
        return getSqlMapClientTemplate().queryForList("getMeasureValues", map);
    }
    
    public List getMeasureValueHistory(Map map) throws SQLException{
        return getSqlMapClientTemplate().queryForList("getMeasureValueHistory", map);
    }
    
    public List getMeasureValueReport(Map map) throws SQLException{
        return getSqlMapClientTemplate().queryForList("getMeasureValueHistory", map);
    }
    
    public List queryForList(String statementName, Map parameterObject) throws SQLException{        
        return getSqlMapClientTemplate().queryForList(statementName, parameterObject);
    }
    
    public void insertMeas(Map map) throws SQLException{
        getSqlMapClientTemplate().insert("insertMeas", map);
    }
    
    public int updateMeasForConfirm(Map map) throws SQLException{
        return getSqlMapClientTemplate().update("updateMeasForConfirm", map);
    }
    
  

}
