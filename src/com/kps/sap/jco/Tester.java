/*******************************************************************
 * ÇÑÀüKPS WPS
 *
 * Copyright (c) 2009 by KPS.
 * All rights reserved. 
 */
package com.kps.sap.jco;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kps.common.dao.JCo3TestDao;

// jco.client.client=350
// jco.client.user=PM-IF
// jco.client.passwd=KPSPM00
// jco.client.ashost=10.11.11.111
// jco.client.sysnr=00

public class Tester {
    static String SAP = "SAP_SERVER";

    public static void main(String[] args) {
        // SAP System
//        SapSystem system = new SapSystem();
//        system.setClient("350");
//        system.setHost("10.11.11.111");
//        system.setLanguage("en");
//        system.setSystemNumber("00");
//        system.setUser("PM-IF");
//        system.setPassword("KPSPM00");
//
//        Connection connect = new Connection(system);
//
//        JCoFunction function = connect.getFunction("BAPI_USER_GETLIST");
//        function.getImportParameterList().setValue("MAX_ROWS", 10);
//        connect.execute(function);
//        JCoTable table = function.getTableParameterList().getTable("USERLIST");
//        System.out.println(table.isEmpty());
//
//        TableAdapterReader tableAdapter = new TableAdapterReader(table);
//        System.out.println("Number of Users: " + tableAdapter.size());
//        for (int i = 0; i < tableAdapter.size(); i++) {
//            // USERNAME is on the fields in the structure user
//            System.out.println(tableAdapter.get("USERNAME"));
//            tableAdapter.next();
//        }
        
        System.out.println("################################");
        
        JCo3TestDao dao = new JCo3TestDao();
        try {
            Map m = dao.getUserList("BAPI_USER_GETLIST");
            List list = (List)m.get("list");
            for (int i=0; i < list.size(); i++) {
                Set keySet = null;
                Iterator iterator = null;
                Map map = (Map)list.get(i);
                keySet = map.keySet();
                iterator = keySet.iterator();
                String param = null;
                while (iterator.hasNext()) {
                    String paramName = (String)iterator.next();
                    System.out.println(paramName + " => " + map.get(paramName) );                  
                }
            }
          
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
