package com.kps.epda.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.kps.epda.dao.CheckGroupDao;
import com.kps.epda.dao.CommonDao;

public class CheckGroupService {    
    @Autowired
    private CheckGroupDao checkGroupDao;
    @Autowired
    private CommonDao commonDao;
     

    public List getGroups(Map map) throws Exception {
        List list = null;        
        System.out.println(map);
        try {
            list = checkGroupDao.getGroups(map);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }          
        return list;
    }
    
    public List getMyGroups(Map map) throws Exception {
        return getGroups(map);
    }
    
    public List getGroupItems(Map map) throws Exception {
        List list = null;        
        
        try {
            list = checkGroupDao.getGroupItems(map);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }          
        return list;
    }
    
    public List getCheckTypeCodes() throws Exception {
        return getEvibCodes("AB");
    }
      
    public List getEvibCodes(String ccod_cd1) throws Exception {
        Map parameterObject = new HashMap();
        parameterObject.put("CCOD_CD1", ccod_cd1);
        return checkGroupDao.queryForList("getEvibCode", parameterObject);
    }
    
    public String insertGroup(Map groupInfo, List groupItems) throws Exception {
        checkGroupDao.insertGroup(groupInfo, groupItems);
        return "success";
    }
    
    public String deleteGroup(Map map) throws Exception {
        System.out.println("delete=" + map);
        checkGroupDao.deleteGroup(map);
        return "success";
    }
   
   
}
