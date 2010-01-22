/*******************************************************************************
 * ÇÑÀüKPS WPS Copyright (c) 2007 by LG CNS, Inc. All rights reserved.
 * ****************************************************************** $Id:
 * SapCommonDao.java,v 1.7 2008/06/13 08:01:15 cvsjch2 Exp $
 * 
 * @author $Author: cvsjch2 $
 * @version $Revision: 1.7 $
 */
package com.kps.common.dao;

import java.util.HashMap;
import java.util.Map;

import com.kps.epda.util.Constants;
import com.kps.sap.jco.SapJCo3Dao;


public class AdminDao extends SapJCo3Dao {

    public Map getPlantListFromSAP() throws Exception{
        return super.queryForList("Z_PM_DWN_PLANT");
    }

    public Map getPLGRPListFromSAP() throws Exception{
        return super.queryForXML("Z_PM_DWN_PLGRP");
    }

    public Map getHR_IBSALIST(HashMap importParam, String[][] importParamName, String[][] exportParamName,
            String[][] outputParamName) throws Exception{
        return queryForList(Constants.FN_NAME_ZHR_GET_IBSALIST, importParam, importParamName, null, null, null,
                exportParamName, Constants.TB_NAME_ZHR_GET_IBSALIST, outputParamName);
    }
    
    public Map getTplnrList(HashMap importParam, String[][] importParamName, String[][] exportParamName,
            String[][] outputParamName) throws Exception{
        return queryForList("Z_PM_GET_TPLNRLIST", importParam, importParamName, null, null, null,
                exportParamName, "T_OUTPUT", outputParamName);
    }

}
