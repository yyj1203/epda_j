/*******************************************************************
 * ÇÑÀüKPS WPS
 *
 * Copyright (c) 2009 by KPS.
 * All rights reserved. 
 */
package com.kps.common.dao;

import java.util.Map;

import com.kps.sap.jco.SapJCo3Dao;

public class JCo3TestDao extends SapJCo3Dao {
    public Map getUserList(String functionName) throws Exception {
        return queryForList(functionName);
    }
}
