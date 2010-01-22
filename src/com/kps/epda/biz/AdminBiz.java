/*******************************************************************************
 * 한전KPS WPS Copyright (c) 2007 by LG CNS, Inc. All rights reserved.
 * ****************************************************************** $Id:
 * CatalogBiz.java,v 1.4 2008/03/10 05:43:04 ksm Exp $
 * 
 * @author $Author: ksm $
 * @version $Revision: 1.4 $
 */
package com.kps.epda.biz;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import com.kps.common.dao.AdminDao;
import com.kps.epda.util.Constants;


public class AdminBiz extends BaseBiz {

    private AdminDao adminDao = null;

    public Map getPlantListFromSAP() throws Exception{
        adminDao = new AdminDao();
        Map map = adminDao.getPlantListFromSAP();        
        return map;
    }

    public String getPLGRPListFromSAP() throws Exception{
        adminDao = new AdminDao();
        Map map = adminDao.getPLGRPListFromSAP();
        //System.out.println("############################    getPlantListFromSAP      #############################");
        return map.get("xml").toString();
    }

    public Map getHR_IBSALIST(HashMap hashMap) throws Exception{
        AdminDao adminDao = null;      
        Map resultMap = null;
        Map returnMap = null;
        String[][] paramName = { { "I_BEGDA", "I_BEGDA" } //기준일
        };
        String[][] resultName = { 
                { "PERNR", "PERNR" }, //사번              
                { "ENAME", "ENAME" } //이름
        };
        String[][] exportName = { { "RETURN", "RETURN" } //호출결과코드
        };        
        adminDao = new AdminDao();
        resultMap = adminDao.getHR_IBSALIST(hashMap, paramName, exportName, resultName);
        //검색결과 반환
        returnMap = new WeakHashMap();
        returnMap.put(Constants.TB_NAME_ZHR_GET_IBSALIST, resultMap.get(Constants.TB_NAME_ZHR_GET_IBSALIST));
        return returnMap;
    }
    
    @SuppressWarnings("unchecked")
    public Map getTplnrList(HashMap map) throws Exception {
        String[][] paramName = {                
               { "I_GUBUN", "I_GUBUN" },
               { "I_IWERK", "I_IWERK" },
               { "I_SWERK", "I_SWERK" },
               { "I_BEBER", "I_BEBER" },
               { "I_FLTYP", "I_FLTYP" },
               { "I_EQART", "I_EQART" }
        };
        String[][] resultName = {
               { "TPLNR"   , "TPLNR"    }, 
               { "PLTXT"   , "PLTXT"    }
//               { "IWERK"   , "IWERK"    },
//               { "IWTXT"   , "IWTXT"    },
//               { "SWERK"   , "SWERK"    },
//               { "INGRP"   , "INGRP"    },
//               { "INNAM"   , "INNAM"    },
//               { "GEWRK"   , "GEWRK"    },
//               { "GETXT"   , "GETXT"    },
//               { "BEBER"   , "BEBER"    },               
//               { "FLTYP"   , "FLTYP"    },
//               { "EQART"   , "EQART"    },
//               { "EARTX"   , "EARTX"    },
//               { "Z_EQCLS1", "Z_EQCLS1" },
//               { "Z_EQCLS2", "Z_EQCLS2" },
//               { "Z_EQSYS" , "Z_EQSYS"  }
        };
        
        String[][] exportName = { { "RETURN", "RETURN" } }; 
        adminDao = new AdminDao();
        Map resultMap = adminDao.getTplnrList(map, paramName, null, resultName);       
        Map returnMap = new WeakHashMap();
        returnMap.put("T_OUTPUT", resultMap.get("T_OUTPUT"));
        return returnMap;
    }

}
