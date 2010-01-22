/*******************************************************************
 * ÇÑÀüKPS WPS
 *
 * Copyright (c) 2009 by KPS.
 * All rights reserved. 
 */
package com.kps.pm.saprfc;

import java.util.HashMap;
import java.util.Map;

import com.kps.sap.jco.SapJCo3Dao;


/**
 * @author user
 *
 */
public class Z_PM_GET_TPLNRLIST extends SapJCo3Dao{
    String functionName = "Z_PM_GET_TPLNRLIST";
    String tableOutName = "T_OUTPUT";  
    
    String[][] importParamName = {
            { "I_GUBUN", "I_GUBUN" },
            { "I_IWERK", "I_IWERK" },
            { "I_SWERK", "I_SWERK" },
            { "I_BEBER", "I_BEBER" },
            { "I_FLTYP", "I_FLTYP" },
            { "I_EQART", "I_EQART" },
            { "I_TPLNR", "I_TPLNR" }
    };
    String[][] resultName = {
            { "TPLNR", "TPLNR" },
            { "PLTXT", "PLTXT" },
            { "BEBER", "BEBER" }
            };
    String[][] exportParamName = {{ "E_SUBRC", "E_SUBRC" }};
    

    public Map execute(HashMap importParam) throws Exception  {
        return super.queryForList(functionName, importParam, importParamName, null, null, null,
                exportParamName, tableOutName, resultName);   
    }    
} 
