/*******************************************************************************
 * 한전KPS WPS Copyright (c) 2007 by LG CNS, Inc. All rights reserved.
 * ****************************************************************** $Id:
 * RfcDao.java,v 1.4 2008/03/10 05:43:04 ksm Exp $
 * 
 * @author $Author: cvsjch2 $
 * @version $Revision: 1.15 $
 */
package com.kps.common.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.WeakHashMap;

import org.apache.log4j.Logger;

import com.kps.util.DateUtil;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;

public class RfcDao {

    static final String POOL_NAME = "KPS_RFC_Pool"; // 커넥션 풀 이름

    private static final Logger logger = Logger.getLogger(RfcDao.class);

    /**
     * 새로운 JCO 커넥션을 커넥션 풀에서 가져온다. 
     * 커넥션 풀이 존재하지 않으면 새로 커넥션 풀을 생성한다.
     * 
     * @throws Exception
     */
    protected JCO.Client getJcoConnection() throws Exception{
        JCO.Pool jcoPool = null;

        jcoPool = JCO.getClientPoolManager().getPool(POOL_NAME);

        if (jcoPool == null) {
            JCO.addClientPool(POOL_NAME, 100, OrderedPropertyFactory.getInstance()); // pool name, maximum number of connections, properties
            jcoPool = JCO.getClientPoolManager().getPool(POOL_NAME);
        }

        return JCO.getClient(POOL_NAME);
    }

    /**
     * JCO.Function 객체를 생성하여 반환한다.
     * 
     * @param jcoClient
     * @param name
     * @return
     * @throws Exception
     */
    protected JCO.Function createFunction(JCO.Client jcoClient, String functionName) throws Exception{
        JCO.Repository mRepository = null;
        IFunctionTemplate ft = null;

        mRepository = new JCO.Repository("KPS_Repository", jcoClient);
        ft = mRepository.getFunctionTemplate(functionName.toUpperCase());

        if (ft == null) {
            return null;
        }

        return ft.getFunction();
    }

    /**
     * RFC 호출결과 코드와 결과값을 반환한다.
     * 호출결과코드는 프로퍼티로, 호출결과값은 테이블명의 리스트로 반환된다.
     *   
     * @param functionName
     * @param tableName
     * @param param
     * @param paramName
     * @param exportName
     * @param resultName
     * @return
     * @throws Exception
     */
    protected HashMap executeExportReturnFunction(String functionName, String tableName, HashMap param,
            String[][] paramName, String[][] exportName, String[][] resultName) throws Exception{
        JCO.Client jcoClient = null;
        JCO.Function jcoFunction = null;
        JCO.Table jcoTable = null;
        JCO.ParameterList jcoParam = null;
        List resultList = null;
        HashMap resultMap = null;
        HashMap tempMap = null;
        Properties properties = null;

        try {
            //커넥션 생성 및 Function 객체를 생성한 후 Function 호출
            jcoClient = getJcoConnection();
            jcoFunction = createFunction(jcoClient, functionName);
            jcoParam = jcoFunction.getExportParameterList();

            //RFC 파라메터 설정
            logger.debug(" ******** RFC 입력 파라메터 Start ********* ");
            for (int i = 0; i < paramName.length; i++) {
                if (param.get(paramName[i][0]) != null && !param.get(paramName[i][0]).equals("")) {
                    jcoFunction.getImportParameterList().setValue(param.get(paramName[i][0]), paramName[i][1]);
                    logger.debug(paramName[i][1] + ": " + param.get(paramName[i][0]));
                }
            }
            logger.debug(" ******** RFC 입력 파라메터 End ********* ");

            //Function 실행
            jcoTable = jcoFunction.getTableParameterList().getTable(tableName);
            jcoClient.execute(jcoFunction);

            //RFC 호출결과 코드를 프로퍼티에 담는다.
            properties = new Properties();
            for (int j = 0; j < exportName.length; j++) {
                properties.put(exportName[j][0], jcoParam.getValue(exportName[j][1]));
            }

            //호출결과값을 리스트에 담는다.
            resultList = new ArrayList();
            for (int i = 0; i < jcoTable.getNumRows(); i++) {
                tempMap = new HashMap();
                jcoTable.setRow(i);

                for (int j = 0; j < resultName.length; j++) {
                    tempMap.put(resultName[j][0], jcoTable.getString(resultName[j][1]));
                    if (logger.isDebugEnabled())
                        logger.debug(resultName[j][0] + ": " + jcoTable.getString(resultName[j][1]));
                }

                resultList.add(tempMap);
            }

            //호출결과 코드 및 호출결과값 반한
            logger.debug(" ******** RFC 결과 건수********* " + resultList.size());
            resultMap = new HashMap();
            resultMap.put("export", properties);
            resultMap.put(tableName, resultList);

            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (jcoClient != null)
                JCO.releaseClient(jcoClient);
        }
    }

    /**
     * 입력 다건, 검색결과 다건
     * @param functionName
     * @param tableName
     * @param dataList
     * @param paramName
     * @param exportName
     * @param resultName
     * @return
     * @throws Exception
     */
    protected List executeExportReturnFunctionMulti(String functionName, String tableNameIn, String tableNameOut,
            List dataList, String[][] paramName, String[][] resultName) throws Exception{
        JCO.Client jcoClient = null;
        JCO.Function jcoFunction = null;
        JCO.Table jcoTableIn = null;
        JCO.Table jcoTableOut = null;
        List resultList = null;
        HashMap tempMap = null;
        HashMap param = null;

        try {
            //커넥션 생성 및 Function 객체를 생성한 후 Function 호출
            jcoClient = getJcoConnection();
            jcoFunction = createFunction(jcoClient, functionName);

            //RFC 파라메터 설정(다건)
            jcoTableIn = jcoFunction.getTableParameterList().getTable(tableNameIn);
            for (int k = 0; k < dataList.size(); k++) {
                param = (HashMap)dataList.get(k);
                jcoTableIn.appendRow();

                logger.debug(" ******** RFC 입력 파라메터 Start ********* ");
                for (int i = 0; i < paramName.length; i++) {
                    if (param.get(paramName[i][0]) != null && !param.get(paramName[i][0]).equals("")) {
                        jcoTableIn.setValue(param.get(paramName[i][0]), paramName[i][1]);
                        logger.debug(paramName[i][1] + ": " + param.get(paramName[i][0]));
                    }
                }
                logger.debug(" ******** RFC 입력 파라메터 Start ********* ");
            }

            //Function 실행
            jcoTableOut = jcoFunction.getTableParameterList().getTable(tableNameOut);
            jcoClient.execute(jcoFunction);

            //호출결과값을 리스트에 담는다.
            resultList = new ArrayList();
            for (int i = 0; i < jcoTableOut.getNumRows(); i++) {
                tempMap = new HashMap();
                jcoTableOut.setRow(i);

                for (int j = 0; j < resultName.length; j++) {
                    tempMap.put(resultName[j][0], jcoTableOut.getString(resultName[j][1]));
                }

                resultList.add(tempMap);
            }

            return resultList;
        } finally {
            if (jcoClient != null)
                JCO.releaseClient(jcoClient);
        }
    }
    
    @SuppressWarnings("unchecked")
    protected List queryForList(String functionName, 
                                String tableNameIn, 
                                String tableNameOut,
                                List dataList, 
                                String[][] paramName, 
                                String[][] resultName,
                                Class dtoClass) throws Exception{
        
        JCO.Client jcoClient = null;
        JCO.Function jcoFunction = null;
        JCO.Table jcoTableIn = null;
        JCO.Table jcoTableOut = null;
        List resultList = null;
        HashMap tempMap = null;
        HashMap param = null;
        try {            
            jcoClient = getJcoConnection();
            jcoFunction = createFunction(jcoClient, functionName);
            jcoTableIn = jcoFunction.getTableParameterList().getTable(tableNameIn);
            for (int k = 0; k < dataList.size(); k++) {
                param = (HashMap)dataList.get(k);
                jcoTableIn.appendRow();
                for (int i = 0; i < paramName.length; i++) {
                    if (param.get(paramName[i][0]) != null && !param.get(paramName[i][0]).equals("")) {
                        jcoTableIn.setValue(param.get(paramName[i][0]), paramName[i][1]);
                        //logger.debug(paramName[i][1] + ": " + param.get(paramName[i][0]));
                    }
                }               
            }            
            jcoTableOut = jcoFunction.getTableParameterList().getTable(tableNameOut);
            jcoClient.execute(jcoFunction);

            //호출결과값을 리스트에 담는다.
            resultList = new ArrayList();
            for (int i = 0; i < jcoTableOut.getNumRows(); i++) {
                jcoTableOut.setRow(i);               
                Object dto = dtoClass.newInstance();
                
                for (int j = 0; j < resultName.length; j++) {
                    setFieldValue(dto, resultName[j][1], jcoTableOut.getString(resultName[j][1]));
                }
                
                resultList.add(dto);
            }
            return resultList;
        } finally {
            if (jcoClient != null)
                JCO.releaseClient(jcoClient);
        }
    }
    

    /**
     * RFC 호출 후에 Export 파라메터를 반환한다.
     * 주로 SAP 서버에 RFC를 이용하여 사용자가 입력한 데이터를 저장하는 경우 사용한다.
     * 
     * @param functionName
     * @param tableName
     * @param param
     * @param paramName
     * @param exportName
     * @return
     * @throws Exception
     */
    protected Properties executeExportFunction(String functionName, String tableName, HashMap param,
            String[][] paramName, String[][] exportName) throws Exception{
        JCO.Client jcoClient = null;
        JCO.Function jcoFunction = null;
        JCO.Table jcoTable = null;
        JCO.ParameterList jcoParam = null;
        Properties properties = null;

        try {
            //커넥션 생성 및 Function 객체를 생성한 후 Function 호출
            jcoClient = getJcoConnection();
            jcoFunction = createFunction(jcoClient, functionName);
            jcoTable = jcoFunction.getTableParameterList().getTable(tableName);
            jcoTable.appendRow();
            jcoParam = jcoFunction.getExportParameterList();

            //RFC 파라메터 설정
            logger.debug(" ******** RFC 입력 파라메터 Start ********* ");
            for (int i = 0; i < paramName.length; i++) {
                if (param.get(paramName[i][0]) != null && !param.get(paramName[i][0]).equals("")) {
                    jcoTable.setValue(param.get(paramName[i][0]), paramName[i][1]);
                    logger.debug(paramName[i][1] + ": " + param.get(paramName[i][0]));
                }
            }
            logger.debug(" ******** RFC 입력 파라메터 End ********* ");

            logger.debug(" Time Confirm Start Time: "
                    + DateUtil.convertDate(new Date(), DateUtil.SDF_YYYYMMDDHHMMSS_SSS_DASH));
            jcoClient.execute(jcoFunction);
            logger.debug(" Time Confirm Finish Time: "
                    + DateUtil.convertDate(new Date(), DateUtil.SDF_YYYYMMDDHHMMSS_SSS_DASH));

            //RFC 호출결과 코드를 프로퍼티에 담는다.
            properties = new Properties();
            for (int j = 0; j < exportName.length; j++) {
                properties.put(exportName[j][0], jcoParam.getValue(exportName[j][1]));
            }
        } finally {
            if (jcoClient != null)
                JCO.releaseClient(jcoClient);
            //jcoClient.disconnect();
        }

        return properties;
    }

    /**
     * RFC 호출 후에 Export 파라메터를 반환한다.
     * Import 파라미터와 Export 파라미터를 이용해서 처리
     * 
     * @param functionName
     * @param tableName
     * @param param
     * @param paramName
     * @param exportName
     * @return
     * @throws Exception
     */
    protected Properties executeExportFunction(String functionName, HashMap param, String[][] paramName,
            String[][] exportName) throws Exception{
        JCO.Client jcoClient = null;
        JCO.Function jcoFunction = null;
        JCO.ParameterList jcoParam = null;
        JCO.ParameterList jcoIParam = null;
        Properties properties = null;

        try {
            //커넥션 생성 및 Function 객체를 생성한 후 Function 호출
            jcoClient = getJcoConnection();
            jcoFunction = createFunction(jcoClient, functionName);
            jcoParam = jcoFunction.getExportParameterList();
            jcoIParam = jcoFunction.getImportParameterList();

            //RFC 파라메터 설정			
            logger.debug(" ******** RFC 입력 파라메터 Start ********* ");
            for (int i = 0; i < paramName.length; i++) {
                if (param.get(paramName[i][0]) != null && !param.get(paramName[i][0]).equals("")) {
                    jcoIParam.setValue(param.get(paramName[i][0]), paramName[i][1]);
                    logger.debug(paramName[i][1] + ": " + param.get(paramName[i][0]));
                }
            }
            logger.debug(" ******** RFC 입력 파라메터 End ********* ");

            jcoFunction.setImportParameterList(jcoIParam);
            jcoClient.execute(jcoFunction);

            //RFC 호출결과 코드를 프로퍼티에 담는다.
            properties = new Properties();
            for (int j = 0; j < exportName.length; j++) {
                properties.put(exportName[j][0], jcoParam.getValue(exportName[j][1]));
            }
        } finally {
            if (jcoClient != null)
                JCO.releaseClient(jcoClient);
        }

        return properties;
    }

    /**
     * RFC Function을 호출한다.
     * 대부분의 RFC Function에 적용할 수 있게 import 파라메터 및 입력테이블(다건) 값을 입력하고, 
     * export 파라메터 및 출력테이블의 값을 반환한다. 
     *  
     * @param functionName : 호출할 RFC Function 이름
     * @param importData : import 파라메터에 설정할 데이터
     * @param importParamName : import할 파라메터 이름
     * @param tableNameIn : 입력 테이블 이름
     * @param inputDataList : 입력 테이블에 설정할 데이터 리스트 
     * @param inputParamName : 입력 테이블에 값을 설정할 필드 이름
     * @param exportName : export할 파라메터 이름
     * @param tableNameOut : 출력 테이블 이름
     * @param outputParamName : 출력 테이블에서 값을 가져올 필드 이름
     * @return
     * @throws Exception
     */
    protected HashMap executeRFC(String functionName, HashMap importData, String[][] importParamName,
            String tableNameIn, List inputDataList, String[][] inputParamName, String[][] exportName,
            String tableNameOut, String[][] outputParamName) throws Exception{
        JCO.Client jcoClient = null;
        JCO.Function jcoFunction = null;
        JCO.Table jcoTableIn = null;
        JCO.Table jcoTableOut = null;
        JCO.ParameterList jcoParamExport = null;
        JCO.ParameterList jcoParamImport = null;
        List resultList = null;
        HashMap tempMap = null;
        HashMap inputParam = null;
        HashMap exportMap = null;
        HashMap resultMap = null;

        try {
            logger.debug(" ******** RFC Function - " + functionName + " 실행  ********* ");

            //커넥션 및 Function 객체 생성
            jcoClient = getJcoConnection();
            jcoFunction = createFunction(jcoClient, functionName);

            //RFC - import 데이터가 있으면 import 파라메터 테이블에 값 설정
            if (importData != null && importParamName != null) {
                logger.debug(" ******** RFC - import 파라메터 ");
                jcoParamImport = jcoFunction.getImportParameterList();

                for (int i = 0; i < importParamName.length; i++) {
                    if (importData.get(importParamName[i][0]) != null
                            && !importData.get(importParamName[i][0]).equals("")) {
                        jcoParamImport.setValue(importData.get(importParamName[i][0]), importParamName[i][1]);
                        if (logger.isDebugEnabled())
                            logger.debug(importParamName[i][1] + ": " + importData.get(importParamName[i][0]));
                    }
                }
            }

            //RFC - 입력 테이블 값 설정 - 벌크
            if (inputDataList != null && inputParamName != null) {
                jcoTableIn = jcoFunction.getTableParameterList().getTable(tableNameIn);

                for (int k = 0; k < inputDataList.size(); k++) {
                    inputParam = (HashMap)inputDataList.get(k);
                    jcoTableIn.appendRow();

                    logger.debug(" ******** RFC 입력테이블 파라메터 Start (Row " + k + ") ********* ");
                    for (int i = 0; i < inputParamName.length; i++) {
                        if (inputParam.get(inputParamName[i][0]) != null
                                && !inputParam.get(inputParamName[i][0]).equals("")) {
                            jcoTableIn.setValue(inputParam.get(inputParamName[i][0]), inputParamName[i][1]);
                            if (logger.isDebugEnabled())
                                logger.debug(inputParamName[i][1] + ": " + inputParam.get(inputParamName[i][0]));
                        }
                    }
                }
            }

            //RFC 실행 
            jcoClient.execute(jcoFunction);

            //RFC - export 값 반환
            if (exportName != null) {
                exportMap = new HashMap();
                jcoParamExport = jcoFunction.getExportParameterList();

                logger.debug(" ******** RFC Export 값 ********* ");
                for (int j = 0; j < exportName.length; j++) {
                    exportMap.put(exportName[j][0], jcoParamExport.getValue(exportName[j][1]));
                    if (logger.isDebugEnabled())
                        logger.debug(exportName[j][1] + ": " + jcoParamExport.getValue(exportName[j][1]));
                }
            }

            //RFC - 실행결과 테이블 값 반환
            if (tableNameOut != null && outputParamName != null) {
                resultList = new ArrayList();
                jcoTableOut = jcoFunction.getTableParameterList().getTable(tableNameOut);
                if (jcoTableOut != null) {
                    for (int i = 0; i < jcoTableOut.getNumRows(); i++) {
                        tempMap = new HashMap();
                        jcoTableOut.setRow(i);

                        logger.debug(" ******** RFC 출력 테이블 값 (Row " + i + ") ********* ");
                        for (int j = 0; j < outputParamName.length; j++) {
                            tempMap.put(outputParamName[j][0], jcoTableOut.getString(outputParamName[j][1]));
                            if (logger.isDebugEnabled())
                                logger.debug(outputParamName[j][1] + ": " + jcoTableOut.getString(outputParamName[j][1]));
                        }

                        resultList.add(tempMap);
                    }
                }
            }

            //호출결과 코드 및 호출결과값 반한
            resultMap = new HashMap();

            //export 결과
            if (exportMap != null) {
                resultMap.put("export", exportMap);
            }

            //결과 테이블
            if (tableNameOut != null) {
                resultMap.put(tableNameOut, resultList);
            }

            return resultMap;
        } finally {
            if (jcoClient != null)
                JCO.releaseClient(jcoClient);
        }
    }

    protected Map queryForList(String functionName, HashMap importData, String[][] importParamName, String tableNameIn,
            List inputDataList, String[][] inputParamName, String[][] exportName, String tableNameOut,
            String[][] outputParamName, Class dtoClass) throws Exception{
        JCO.Client jcoClient = null;
        JCO.Function jcoFunction = null;
        JCO.Table jcoTableIn = null;
        JCO.Table jcoTableOut = null;
        JCO.ParameterList jcoParamExport = null;
        JCO.ParameterList jcoParamImport = null;
        List resultList = null;
        HashMap inputParam = null;
        HashMap exportMap = null;
        Map resultMap = null;
        Map tempMap = null;

        try {

            //커넥션 및 Function 객체 생성
            jcoClient = getJcoConnection();
            jcoFunction = createFunction(jcoClient, functionName);

            //RFC - import 데이터가 있으면 import 파라메터 테이블에 값 설정
            if (importData != null && importParamName != null) {
                jcoParamImport = jcoFunction.getImportParameterList();
                
                for (int i = 0; i < importParamName.length; i++) {
                    if (importData.get(importParamName[i][0]) != null && !importData.get(importParamName[i][0]).equals("")) {
                        jcoParamImport.setValue(importData.get(importParamName[i][0]), importParamName[i][1]);
                        if (logger.isDebugEnabled())
                            logger.debug(importParamName[i][1] + ": " + importData.get(importParamName[i][0]));
                    }
                }
            }

            //RFC - 입력 테이블 값 설정 - 벌크
            if (inputDataList != null && inputParamName != null) {
                jcoTableIn = jcoFunction.getTableParameterList().getTable(tableNameIn);

                for (int k = 0; k < inputDataList.size(); k++) {
                    inputParam = (HashMap)inputDataList.get(k);
                    jcoTableIn.appendRow();
                    for (int i = 0; i < inputParamName.length; i++) {
                        if (inputParam.get(inputParamName[i][0]) != null
                                && !inputParam.get(inputParamName[i][0]).equals("")) {
                            jcoTableIn.setValue(inputParam.get(inputParamName[i][0]), inputParamName[i][1]);
                            //logger.debug(inputParamName[i][1] + ": " + inputParam.get(inputParamName[i][0]));
                        }
                    }
                }
            }

            jcoClient.execute(jcoFunction);

            if (exportName != null) {
                exportMap = new HashMap();
                jcoParamExport = jcoFunction.getExportParameterList();
                for (int j = 0; j < exportName.length; j++) {
                    exportMap.put(exportName[j][0], jcoParamExport.getValue(exportName[j][1]));
                    //logger.debug(exportName[j][1] + ": " + jcoParamExport.getValue(exportName[j][1]));
                }
            }

            //RFC - 실행결과 테이블 값 반환
            if (tableNameOut != null && outputParamName != null) {
                resultList = new ArrayList();
                jcoTableOut = jcoFunction.getTableParameterList().getTable(tableNameOut);
                if (jcoTableOut != null) {
                    for (int i = 0; i < jcoTableOut.getNumRows(); i++) {
                        jcoTableOut.setRow(i);
                        Object dto = dtoClass.newInstance();
                        for (int j = 0; j < outputParamName.length; j++) {
                            setFieldValue(dto, outputParamName[j][1], jcoTableOut.getString(outputParamName[j][1]));
                        }
                        resultList.add(dto);
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

            return resultMap;
        } finally {
            if (jcoClient != null)
                JCO.releaseClient(jcoClient);
        }
    }

    protected Map queryForList(String functionName, HashMap importData, String[][] importParamName, String tableNameIn,
            List inputDataList, String[][] inputParamName, String[][] exportName, String tableNameOut,
            String[][] outputParamName, Class dtoClass, int limit) throws Exception{
        JCO.Client jcoClient = null;
        JCO.Function jcoFunction = null;
        JCO.Table jcoTableIn = null;
        JCO.Table jcoTableOut = null;
        JCO.ParameterList jcoParamExport = null;
        JCO.ParameterList jcoParamImport = null;
        List resultList = null;
        HashMap inputParam = null;
        HashMap exportMap = null;
        Map resultMap = null;
        Map tempMap = null;

        try {

            //커넥션 및 Function 객체 생성
            jcoClient = getJcoConnection();
            jcoFunction = createFunction(jcoClient, functionName);

            //RFC - import 데이터가 있으면 import 파라메터 테이블에 값 설정
            if (importData != null && importParamName != null) {
                jcoParamImport = jcoFunction.getImportParameterList();

                for (int i = 0; i < importParamName.length; i++) {
                    if (importData.get(importParamName[i][0]) != null
                            && !importData.get(importParamName[i][0]).equals("")) {
                        jcoParamImport.setValue(importData.get(importParamName[i][0]), importParamName[i][1]);
                        //logger.debug(importParamName[i][1] + ": " + importData.get(importParamName[i][0]));
                    }
                }
            }

            //RFC - 입력 테이블 값 설정 - 벌크
            if (inputDataList != null && inputParamName != null) {
                jcoTableIn = jcoFunction.getTableParameterList().getTable(tableNameIn);

                for (int k = 0; k < inputDataList.size(); k++) {
                    inputParam = (HashMap)inputDataList.get(k);
                    jcoTableIn.appendRow();
                    for (int i = 0; i < inputParamName.length; i++) {
                        if (inputParam.get(inputParamName[i][0]) != null
                                && !inputParam.get(inputParamName[i][0]).equals("")) {
                            jcoTableIn.setValue(inputParam.get(inputParamName[i][0]), inputParamName[i][1]);
                            //logger.debug(inputParamName[i][1] + ": " + inputParam.get(inputParamName[i][0]));
                        }
                    }
                }
            }

            jcoClient.execute(jcoFunction);

            if (exportName != null) {
                exportMap = new HashMap();
                jcoParamExport = jcoFunction.getExportParameterList();
                for (int j = 0; j < exportName.length; j++) {
                    exportMap.put(exportName[j][0], jcoParamExport.getValue(exportName[j][1]));
                    //logger.debug(exportName[j][1] + ": " + jcoParamExport.getValue(exportName[j][1]));
                }
            }

            //RFC - 실행결과 테이블 값 반환
            if (tableNameOut != null && outputParamName != null) {
                resultList = new ArrayList();
                jcoTableOut = jcoFunction.getTableParameterList().getTable(tableNameOut);
                if (jcoTableOut != null) {
                    for (int i = 0; i < jcoTableOut.getNumRows(); i++) {
                        jcoTableOut.setRow(i);
                        Object dto = dtoClass.newInstance();

                        Field[] fields = dtoClass.getDeclaredFields();

                        for (Field field : fields) {
                            field.setAccessible(true);
                            field.set(dto, jcoTableOut.getString(field.getName()));
                        }

                        resultList.add(dto);
                        if (i > limit)
                            break;
                    }
                    if (logger.isDebugEnabled()) {
                        logger.debug(functionName + " 조회건수: " + resultList.size());
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

            return resultMap;
        } finally {
            if (jcoClient != null)
                JCO.releaseClient(jcoClient);
        }
    }

    protected Map queryForXML(String functionName) throws Exception{
        JCO.Client jcoClient = null;
        JCO.Function jcoFunction = null;        
        com.sap.mw.jco.JCO.ParameterList tableList;
        Map resultMap = null;
        try {

            //커넥션 및 Function 객체 생성
            jcoClient = getJcoConnection();
            jcoFunction = createFunction(jcoClient, functionName);
            tableList = jcoFunction.getTableParameterList();
            jcoClient.execute(jcoFunction);

            com.sap.mw.jco.JCO.Table resultTable = tableList.getTable(0);
            String tName = resultTable.getName();
            int fieldSize = resultTable.getFieldCount();      
            com.sap.mw.jco.JCO.FieldIterator e = resultTable.fields(); 
            resultMap = new WeakHashMap();
            String xml = resultTable.toString();
            resultMap.put("xml", xml);
            /*
            if (resultTable.getNumRows() > 0)
                do {
                    for (com.sap.mw.jco.JCO.FieldIterator e = resultTable.fields(); e.hasMoreElements();) {
                        com.sap.mw.jco.JCO.Field field = e.nextField();
                       
                            field.getName(), field.getString()
                    }
                    for (com.sap.mw.jco.JCO.Field field; )
                } while (resultTable.nextRow());

           */        

            return resultMap;
        } finally {
            if (jcoClient != null)
                JCO.releaseClient(jcoClient);
        }
    }
    
    protected Map queryForList(String functionName) throws Exception{
        JCO.Client jcoClient = null;
        JCO.Function jcoFunction = null;        
        com.sap.mw.jco.JCO.ParameterList tableList;
        Map resultMap = null;
        try {

            //커넥션 및 Function 객체 생성
            jcoClient = getJcoConnection();
            jcoFunction = createFunction(jcoClient, functionName);
            tableList = jcoFunction.getTableParameterList();
            jcoClient.execute(jcoFunction);

            com.sap.mw.jco.JCO.Table resultTable = tableList.getTable(0);
            String tName = resultTable.getName();
            int fieldSize = resultTable.getFieldCount();      
            //com.sap.mw.jco.JCO.FieldIterator e = resultTable.fields(); 
            resultMap = new WeakHashMap();
            List list = new ArrayList();
            String xml = resultTable.toString();   
            Map o = null;
            if (resultTable.getNumRows() > 0)
                do {
                    o = new HashMap();
                    for (com.sap.mw.jco.JCO.FieldIterator e = resultTable.fields(); e.hasMoreElements();) {
                        com.sap.mw.jco.JCO.Field field = e.nextField();                        
                        o.put(field.getName(), field.getString());                                                    
                    }        
                    list.add(o);
                } while (resultTable.nextRow());
            resultMap.put("list", list);
            return resultMap;
        } finally {
            if (jcoClient != null)
                JCO.releaseClient(jcoClient);
        }
    }

    protected Map queryForList2(String functionName, HashMap importData, String[][] importParamName,
            String tableNameIn, List inputDataList, String[][] inputParamName, String[][] exportName,
            String tableNameOut, String[][] outputParamName) throws Exception{
        JCO.Client jcoClient = null;
        JCO.Function jcoFunction = null;
        JCO.Table jcoTableIn = null;
        JCO.Table jcoTableOut = null;
        JCO.ParameterList jcoParamExport = null;
        JCO.ParameterList jcoParamImport = null;
        List resultList = null;
        Map tempMap = null;
        HashMap inputParam = null;
        Map exportMap = null;
        Map resultMap = null;

        try {
            logger.debug(" ******** RFC Function - " + functionName + " 실행  ********* ");

            //커넥션 및 Function 객체 생성
            jcoClient = getJcoConnection();
            jcoFunction = createFunction(jcoClient, functionName);

            //RFC - import 데이터가 있으면 import 파라메터 테이블에 값 설정
            if (importData != null && importParamName != null) {
                logger.debug(" ******** RFC - import 파라메터 ");
                jcoParamImport = jcoFunction.getImportParameterList();

                for (int i = 0; i < importParamName.length; i++) {
                    if (importData.get(importParamName[i][0]) != null
                            && !importData.get(importParamName[i][0]).equals("")) {
                        jcoParamImport.setValue(importData.get(importParamName[i][0]), importParamName[i][1]);
                        logger.debug(importParamName[i][1] + ": " + importData.get(importParamName[i][0]));
                    }
                }
            }

            //RFC - 입력 테이블 값 설정 - 벌크
            if (inputDataList != null && inputParamName != null) {
                jcoTableIn = jcoFunction.getTableParameterList().getTable(tableNameIn);

                for (int k = 0; k < inputDataList.size(); k++) {
                    inputParam = (HashMap)inputDataList.get(k);
                    jcoTableIn.appendRow();

                    logger.debug(" ******** RFC 입력테이블 파라메터 Start (Row " + k + ") ********* ");
                    for (int i = 0; i < inputParamName.length; i++) {
                        if (inputParam.get(inputParamName[i][0]) != null
                                && !inputParam.get(inputParamName[i][0]).equals("")) {
                            jcoTableIn.setValue(inputParam.get(inputParamName[i][0]), inputParamName[i][1]);
                            logger.debug(inputParamName[i][1] + ": " + inputParam.get(inputParamName[i][0]));
                        }
                    }
                }
            }

            //RFC 실행 
            jcoClient.execute(jcoFunction);

            //RFC - export 값 반환
            if (exportName != null) {
                exportMap = new HashMap();
                jcoParamExport = jcoFunction.getExportParameterList();

                logger.debug(" ******** RFC Export 값 ********* ");
                for (int j = 0; j < exportName.length; j++) {
                    exportMap.put(exportName[j][0], jcoParamExport.getValue(exportName[j][1]));
                    logger.debug(exportName[j][1] + ": " + jcoParamExport.getValue(exportName[j][1]));
                }
            }

            //RFC - 실행결과 테이블 값 반환
            if (tableNameOut != null && outputParamName != null) {
                resultList = new ArrayList();
                jcoTableOut = jcoFunction.getTableParameterList().getTable(tableNameOut);
                if (jcoTableOut != null) {
                    for (int i = 0; i < jcoTableOut.getNumRows(); i++) {
                        tempMap = new WeakHashMap();
                        jcoTableOut.setRow(i);

                        //Class klass = ZSPM001.class;
                        //Object dto = klass.newInstance();   

                        logger.debug(" ******** RFC 출력 테이블 값 (Row " + i + ") ********* ");
                        for (int j = 0; j < outputParamName.length; j++) {

                            //setFieldValue(dto, outputParamName[j][1], jcoTableOut.getString(outputParamName[j][1]));

                            //tempMap.put(outputParamName[j][0], jcoTableOut.getString(outputParamName[j][1]));
                            logger.debug(outputParamName[j][1] + ": " + jcoTableOut.getString(outputParamName[j][1]));
                        }

                        //resultList.add(dto);
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

            return resultMap;
        } finally {
            if (jcoClient != null)
                JCO.releaseClient(jcoClient);
        }
    }
   

    public static Field getDeclaredField(Object object, String name) throws NoSuchFieldException{
        Field field = null;
        Class<?> clazz = object.getClass();
        do {
            try {
                field = clazz.getDeclaredField(name);
            } catch (Exception e) {
            }
        } while (field == null & (clazz = clazz.getSuperclass()) != null);

        if (field == null) {
            System.out.println("field: " + name);
            throw new NoSuchFieldException();
        }

        return field;
    }

    public static void setFieldValue(Object object, String fieldName, Object newValue) throws IllegalArgumentException,
            IllegalAccessException, NoSuchFieldException{

        Field field = getDeclaredField(object, fieldName);
        field.setAccessible(true);
        field.set(object, newValue);
    }

}