/*******************************************************************
 * ÇÑÀüKPS WPS Copyright (c) 2009 by KPS. All rights reserved.
 */
package com.kps.epda.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class CheckGroupDao extends SqlMapClientDaoSupport {
    
    public List getGroups(Map map) throws SQLException{
        return getSqlMapClientTemplate().queryForList("getGroups", map);
    }
    
    public List getGroupItems(Map map) throws SQLException{
        return getSqlMapClientTemplate().queryForList("getGroupItems", map);
    }
       
    public String getXml(String statementName, Map parameterObject) throws SQLException {
        XmlRowHandler rowHandler = new XmlRowHandler();
        getSqlMapClientTemplate().queryWithRowHandler(statementName, parameterObject, rowHandler);
        return rowHandler.getListXml();
    }
    
    public void insertGroup(Map groupInfo, List groupItems) throws SQLException {
        System.out.println(groupInfo);
        System.out.println(groupItems);
        long cg_seq;
        try {
            cg_seq = insertCsgrp(groupInfo);
            for (Object item : groupItems) {
                Map groupItem = (Map)item;
                groupItem.put("CG_SEQ", cg_seq);
                getSqlMapClientTemplate().insert("insertCgitem", groupItem);
            }           
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        
    }
          
    public Long insertCsgrp(Map hashMap) throws SQLException{
        return (Long)getSqlMapClientTemplate().insert("insertCsgrp", hashMap);
    }
    
    public void insertCgitem(Map hashMap) throws SQLException{
        getSqlMapClientTemplate().insert("insertCgitem", hashMap);
    }
    
    public void mergePmt(Map hashMap) throws SQLException{
        getSqlMapClientTemplate().update("mergePmt", hashMap);
    }
          
    public Object select(String statementName, Map parameterObject) throws Exception {
        return getSqlMapClientTemplate().queryForObject(statementName, parameterObject);
    }
    
    public List queryForList(String statementName, Map parameterObject) throws SQLException{        
        return getSqlMapClientTemplate().queryForList(statementName, parameterObject);
    }
    
    public void deleteGroup(Map parameterObject) throws SQLException {
        getSqlMapClientTemplate().delete("deleteCgitem", parameterObject);
        getSqlMapClientTemplate().delete("deleteCsgrp", parameterObject);        
    }
    

}
