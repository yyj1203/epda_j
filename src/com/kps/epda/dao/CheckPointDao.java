/*******************************************************************
 * ÇÑÀüKPS WPS Copyright (c) 2009 by KPS. All rights reserved.
 */
package com.kps.epda.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class CheckPointDao extends SqlMapClientDaoSupport {
    public Object getItemCount(Map map) throws SQLException{
        return getSqlMapClientTemplate().queryForObject("getItemCount", map);
    }
    
    public List getPmtList() throws SQLException{
        return getSqlMapClientTemplate().queryForList("getPmtList");
    }
    
    public List getItemSeq(Map map) throws SQLException{
        return getSqlMapClientTemplate().queryForList("getItemSeq", map);
    }
    
    public List getCheckPositions(Map map) throws SQLException{
        return getSqlMapClientTemplate().queryForList("getCheckPositions", map);        
    } 
    
    public List getSystems(Map map) throws SQLException{
        return getSqlMapClientTemplate().queryForList("getSystems", map);        
    } 
    
    public List getBeberList(Map map) throws SQLException{
        return getSqlMapClientTemplate().queryForList("getBeberList", map);
        //return getXml("getBeberList", map);
    } 
    
    public List getOrgList(Map map) throws SQLException{
        return getSqlMapClientTemplate().queryForList("getOrgList", map);
        
    } 
    
    public String getXml(String statementName, Map parameterObject) throws SQLException {
        XmlRowHandler rowHandler = new XmlRowHandler();
        getSqlMapClientTemplate().queryWithRowHandler(statementName, parameterObject, rowHandler);
        return rowHandler.getListXml();
    }
            
    public List getPointSeq(Map map) throws SQLException{
        return getSqlMapClientTemplate().queryForList("getPointSeq", map);
    }
    
    public Object getPointCount(Map map) throws SQLException{
        return getSqlMapClientTemplate().queryForObject("getPointCount", map);
    }
    
    public Object getEpiCount(Map map) throws SQLException{
        return getSqlMapClientTemplate().queryForObject("getEpiCount", map);
    }
    
    public Object getSystemCount(Map map) throws SQLException{
        return getSqlMapClientTemplate().queryForObject("getSystemCount", map);
    }
    
    public Integer insertPoint(Map hashMap) throws SQLException{
        return (Integer)getSqlMapClientTemplate().insert("insertPoint", hashMap);
    }
    
    public void insertItem(Map hashMap) throws SQLException{
        getSqlMapClientTemplate().insert("insertItem", hashMap);
    }
    
    public void mergePmt(Map hashMap) throws SQLException{
        getSqlMapClientTemplate().update("mergePmt", hashMap);
    }
    
    public void merge(String statementName, Map parameterObject) throws SQLException{
        getSqlMapClientTemplate().update(statementName, parameterObject);
    }
    
    public void insertEpi(Map hashMap) throws SQLException{
        getSqlMapClientTemplate().insert("insertEpi", hashMap);
    }
    
    public void updateMinMax(Map map) throws SQLException{
        getSqlMapClientTemplate().update("updateMinMax", map);
    }
    
    public void insertSystem(Map hashMap) throws SQLException{
        getSqlMapClientTemplate().insert("insertSystem", hashMap);
    }
    
    public Object select(String statementName, Map parameterObject) throws Exception {
        return getSqlMapClientTemplate().queryForObject(statementName, parameterObject);
    }

}
