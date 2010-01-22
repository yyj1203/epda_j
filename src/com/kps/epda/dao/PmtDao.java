/*******************************************************************
 * ÇÑÀüKPS WPS
 *
 * Copyright (c) 2010 by KPS.
 * All rights reserved. 
 */
package com.kps.epda.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Service;


public class PmtDao extends SqlMapClientDaoSupport {
    public List getPmtList() throws SQLException{
        return getSqlMapClientTemplate().queryForList("getPmtList");
    }    

    public void mergePmt(Map hashMap) throws SQLException{
        getSqlMapClientTemplate().update("mergePmt", hashMap);
    }
    
}
