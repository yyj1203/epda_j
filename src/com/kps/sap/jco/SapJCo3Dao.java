/*******************************************************************
 * 한전KPS WPS
 *
 * Copyright (c) 2009 by KPS.
 * All rights reserved. 
 */
package com.kps.sap.jco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import com.kps.common.dao.OrderedPropertyFactory;
import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import com.sap.mw.jco.JCO;


/**
 * @author user
 *
 */
public class SapJCo3Dao {
    static final String SAP = "SAP_SERVER";
    
    protected Connection getJcoConnection() throws Exception {
        Connection connect = new Connection(OrderedPropertyFactory.getInstance());
        return connect;
    }
    
    protected Map queryForList(String functionName) throws Exception{
        Connection connect = null;
        JCO.Function jcoFunction = null;              
        Map resultMap = null;
        try {
            connect = getJcoConnection();
            JCoFunction function = connect.getFunction(functionName);
            connect.execute(function);     
            com.sap.conn.jco.JCoParameterList tableList = function.getTableParameterList();
            System.out.println(tableList.toString());
            JCoTable resultTable = function.getTableParameterList().getTable(0);
            //String tName = resultTable.
            int fieldSize = resultTable.getFieldCount();              
            resultMap = new WeakHashMap();
            List list = new ArrayList();
            String xml = resultTable.toString();   
            System.out.println(xml);
            Map out = null;
            if (resultTable.getNumRows() > 0)
                do {
                    out = new HashMap();                    
                    for(com.sap.conn.jco.JCoFieldIterator ele = resultTable.getFieldIterator(); ele.hasNextField();) {
                        com.sap.conn.jco.JCoField field = ele.nextField();
                        out.put(field.getName(), field.getString());
                    }                  
                    list.add(out);
                } while (resultTable.nextRow());
            resultMap.put("list", list);
            
        } catch (AbapException ex) {
            System.out.println(ex.getMessage());
        } catch (JCoException ex) {
            System.out.println(ex.getMessage());
        }
        return resultMap;
    }
    
    @SuppressWarnings("unchecked")
    protected Map queryForList(String functionName, HashMap importData, String[][] importParamName,
            String tableNameIn, List inputDataList, String[][] inputParamName, String[][] exportName,
            String tableNameOut, String[][] outputParamName) throws Exception{
      
        List resultList = null;
        Map tempMap = null;
        HashMap inputParam = null;
        Map exportMap = null;
        Map resultMap = null;
        Connection connect = null;
        try {
            System.out.println(functionName);
            connect = getJcoConnection();
            JCoFunction function = connect.getFunction(functionName);
            
            System.out.println(functionName);
            
            //RFC - import 데이터가 있으면 import 파라메터 테이블에 값 설정
            if (importData != null && importParamName != null) {
                JCoParameterList jcoImport = function.getImportParameterList();
                for (int i = 0; i < importParamName.length; i++) {
                    if (importData.get(importParamName[i][0]) != null && !importData.get(importParamName[i][0]).equals("")) {
                        jcoImport.setValue(importParamName[i][1], importData.get(importParamName[i][0]));                      
                    }
                }
            }

            //RFC - 입력 테이블 값 설정 - 벌크
            if (inputDataList != null && inputParamName != null) {                
                JCoTable tableIn = function.getTableParameterList().getTable(tableNameIn);
                for (int k = 0; k < inputDataList.size(); k++) {
                    inputParam = (HashMap)inputDataList.get(k);
                    tableIn.appendRow();               
                    for (int i = 0; i < inputParamName.length; i++) {
                        if (inputParam.get(inputParamName[i][0]) != null && !inputParam.get(inputParamName[i][0]).equals("")) {
                            tableIn.setValue(inputParamName[i][1], inputParam.get(inputParamName[i][0]));                           
                        }
                    }
                }
            }
            connect.execute(function);

            //RFC - export 값 반환
            if (exportName != null) {
                exportMap = new HashMap();
                JCoParameterList jcoExport = function.getExportParameterList();
                for (int j = 0; j < exportName.length; j++) {
                    exportMap.put(exportName[j][0], jcoExport.getValue(exportName[j][1]));                 
                }
            }

            //RFC - 실행결과 테이블 값 반환
            if (tableNameOut != null && outputParamName != null) {
                resultList = new ArrayList();               
                JCoTable tableOut = function.getTableParameterList().getTable(tableNameOut);
                if (tableOut != null) {
                    for (int i = 0; i < tableOut.getNumRows(); i++) {
                        tempMap = new WeakHashMap();
                        tableOut.setRow(i);
                        for (int j = 0; j < outputParamName.length; j++) {
                            tempMap.put(outputParamName[j][0], tableOut.getString(outputParamName[j][1]));                           
                        }
                        resultList.add(tempMap);
                    }
                }
            }

            //호출결과 코드 및 호출결과값 반한
            resultMap = new WeakHashMap();

            //export 결과
            if (exportMap != null) {
                resultMap.put("export", exportMap);
            }

            //결과 테이블
            if (tableNameOut != null) {
                resultMap.put(tableNameOut, resultList);
            }

           
        } catch (AbapException ex) {
            System.out.println(ex.getMessage());
        } catch (JCoException ex) {
            System.out.println(ex.getMessage());
        }
        
        return resultMap;
    }
    
    @SuppressWarnings("unchecked")
    protected Map executeWithTwoImportTables(
            String functionName, 
            HashMap importData, String[][] importParamName,
            String tableNameIn1, List importTableDataList1,
            String tableNameIn2, List importTableDataList2, 
            String[][] inputParamName1,
            String[][] inputParamName2,
            String[][] exportName,
            String tableNameOut, String[][] outputParamName) throws Exception{
      
        List resultList = null;
        Map tempMap = null;
        HashMap inputTableParam1 = null;
        HashMap inputTableParam2 = null;
        Map exportMap = null;
        Map resultMap = null;
        Connection connect = null;
        try {
            connect = getJcoConnection();
            JCoFunction function = connect.getFunction(functionName);

            //RFC - import 데이터가 있으면 import 파라메터 테이블에 값 설정
            if (importData != null && importParamName != null) {
                JCoParameterList jcoImport = function.getImportParameterList();
                for (int i = 0; i < importParamName.length; i++) {
                    if (importData.get(importParamName[i][0]) != null && !importData.get(importParamName[i][0]).equals("")) {
                        jcoImport.setValue(importParamName[i][1], importData.get(importParamName[i][0]));                      
                    }
                }
            }
            
            //RFC - 입력 테이블 값 설정 1
            if (importTableDataList1 != null && importTableDataList1 != null) {                
                JCoTable tableIn1 = function.getTableParameterList().getTable(tableNameIn2);
                for (int k = 0; k < importTableDataList1.size(); k++) {
                    inputTableParam1 = (HashMap)importTableDataList1.get(k);
                    tableIn1.appendRow();               
                    for (int i = 0; i < inputParamName1.length; i++) {
                        if (inputTableParam1.get(inputParamName1[i][0]) != null && !inputTableParam1.get(inputParamName1[i][0]).equals("")) {
                            tableIn1.setValue(inputParamName1[i][1], inputTableParam1.get(inputParamName1[i][0]));                           
                        }
                    }
                }
            }

            //RFC - 입력 테이블 값 설정 2
            if (importTableDataList2 != null && importTableDataList2 != null) {                
                JCoTable tableIn2 = function.getTableParameterList().getTable(tableNameIn2);
                for (int k = 0; k < importTableDataList2.size(); k++) {
                    inputTableParam2 = (HashMap)importTableDataList2.get(k);
                    tableIn2.appendRow();               
                    for (int i = 0; i < inputParamName2.length; i++) {
                        if (inputTableParam2.get(inputParamName2[i][0]) != null && !inputTableParam2.get(inputParamName2[i][0]).equals("")) {
                            tableIn2.setValue(inputParamName2[i][1], inputTableParam2.get(inputParamName2[i][0]));                           
                        }
                    }
                }
            }
            
            connect.execute(function);

            //RFC - export 값 반환
            if (exportName != null) {
                exportMap = new HashMap();
                JCoParameterList jcoExport = function.getExportParameterList();
                for (int j = 0; j < exportName.length; j++) {
                    exportMap.put(exportName[j][0], jcoExport.getValue(exportName[j][1]));                 
                }
            }

            //RFC - 실행결과 테이블 값 반환
            if (tableNameOut != null && outputParamName != null) {
                resultList = new ArrayList();               
                JCoTable tableOut = function.getTableParameterList().getTable(tableNameOut);
                if (tableOut != null) {
                    for (int i = 0; i < tableOut.getNumRows(); i++) {
                        tempMap = new WeakHashMap();
                        tableOut.setRow(i);
                        for (int j = 0; j < outputParamName.length; j++) {
                            tempMap.put(outputParamName[j][0], tableOut.getString(outputParamName[j][1]));                           
                        }
                        resultList.add(tempMap);
                    }
                }
            }

            //호출결과 코드 및 호출결과값 반한
            resultMap = new WeakHashMap();

            //export 결과
            if (exportMap != null) {
                resultMap.put("export", exportMap);
            }

            //결과 테이블
            if (tableNameOut != null) {
                resultMap.put(tableNameOut, resultList);
            }

           
        } catch (AbapException ex) {
            System.out.println(ex.getMessage());
        } catch (JCoException ex) {
            System.out.println(ex.getMessage());
        }
        
        return resultMap;
    }
    
    protected Map queryForXML(String functionName) throws Exception{
       return null;
    }

}
