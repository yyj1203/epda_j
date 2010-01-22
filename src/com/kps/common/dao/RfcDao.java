/*******************************************************************************
 * ����KPS WPS Copyright (c) 2007 by LG CNS, Inc. All rights reserved.
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

    static final String POOL_NAME = "KPS_RFC_Pool"; // Ŀ�ؼ� Ǯ �̸�

    private static final Logger logger = Logger.getLogger(RfcDao.class);

    /**
     * ���ο� JCO Ŀ�ؼ��� Ŀ�ؼ� Ǯ���� �����´�. 
     * Ŀ�ؼ� Ǯ�� �������� ������ ���� Ŀ�ؼ� Ǯ�� �����Ѵ�.
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
     * JCO.Function ��ü�� �����Ͽ� ��ȯ�Ѵ�.
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
     * RFC ȣ���� �ڵ�� ������� ��ȯ�Ѵ�.
     * ȣ�����ڵ�� ������Ƽ��, ȣ�������� ���̺���� ����Ʈ�� ��ȯ�ȴ�.
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
            //Ŀ�ؼ� ���� �� Function ��ü�� ������ �� Function ȣ��
            jcoClient = getJcoConnection();
            jcoFunction = createFunction(jcoClient, functionName);
            jcoParam = jcoFunction.getExportParameterList();

            //RFC �Ķ���� ����
            logger.debug(" ******** RFC �Է� �Ķ���� Start ********* ");
            for (int i = 0; i < paramName.length; i++) {
                if (param.get(paramName[i][0]) != null && !param.get(paramName[i][0]).equals("")) {
                    jcoFunction.getImportParameterList().setValue(param.get(paramName[i][0]), paramName[i][1]);
                    logger.debug(paramName[i][1] + ": " + param.get(paramName[i][0]));
                }
            }
            logger.debug(" ******** RFC �Է� �Ķ���� End ********* ");

            //Function ����
            jcoTable = jcoFunction.getTableParameterList().getTable(tableName);
            jcoClient.execute(jcoFunction);

            //RFC ȣ���� �ڵ带 ������Ƽ�� ��´�.
            properties = new Properties();
            for (int j = 0; j < exportName.length; j++) {
                properties.put(exportName[j][0], jcoParam.getValue(exportName[j][1]));
            }

            //ȣ�������� ����Ʈ�� ��´�.
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

            //ȣ���� �ڵ� �� ȣ������ ����
            logger.debug(" ******** RFC ��� �Ǽ�********* " + resultList.size());
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
     * �Է� �ٰ�, �˻���� �ٰ�
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
            //Ŀ�ؼ� ���� �� Function ��ü�� ������ �� Function ȣ��
            jcoClient = getJcoConnection();
            jcoFunction = createFunction(jcoClient, functionName);

            //RFC �Ķ���� ����(�ٰ�)
            jcoTableIn = jcoFunction.getTableParameterList().getTable(tableNameIn);
            for (int k = 0; k < dataList.size(); k++) {
                param = (HashMap)dataList.get(k);
                jcoTableIn.appendRow();

                logger.debug(" ******** RFC �Է� �Ķ���� Start ********* ");
                for (int i = 0; i < paramName.length; i++) {
                    if (param.get(paramName[i][0]) != null && !param.get(paramName[i][0]).equals("")) {
                        jcoTableIn.setValue(param.get(paramName[i][0]), paramName[i][1]);
                        logger.debug(paramName[i][1] + ": " + param.get(paramName[i][0]));
                    }
                }
                logger.debug(" ******** RFC �Է� �Ķ���� Start ********* ");
            }

            //Function ����
            jcoTableOut = jcoFunction.getTableParameterList().getTable(tableNameOut);
            jcoClient.execute(jcoFunction);

            //ȣ�������� ����Ʈ�� ��´�.
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

            //ȣ�������� ����Ʈ�� ��´�.
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
     * RFC ȣ�� �Ŀ� Export �Ķ���͸� ��ȯ�Ѵ�.
     * �ַ� SAP ������ RFC�� �̿��Ͽ� ����ڰ� �Է��� �����͸� �����ϴ� ��� ����Ѵ�.
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
            //Ŀ�ؼ� ���� �� Function ��ü�� ������ �� Function ȣ��
            jcoClient = getJcoConnection();
            jcoFunction = createFunction(jcoClient, functionName);
            jcoTable = jcoFunction.getTableParameterList().getTable(tableName);
            jcoTable.appendRow();
            jcoParam = jcoFunction.getExportParameterList();

            //RFC �Ķ���� ����
            logger.debug(" ******** RFC �Է� �Ķ���� Start ********* ");
            for (int i = 0; i < paramName.length; i++) {
                if (param.get(paramName[i][0]) != null && !param.get(paramName[i][0]).equals("")) {
                    jcoTable.setValue(param.get(paramName[i][0]), paramName[i][1]);
                    logger.debug(paramName[i][1] + ": " + param.get(paramName[i][0]));
                }
            }
            logger.debug(" ******** RFC �Է� �Ķ���� End ********* ");

            logger.debug(" Time Confirm Start Time: "
                    + DateUtil.convertDate(new Date(), DateUtil.SDF_YYYYMMDDHHMMSS_SSS_DASH));
            jcoClient.execute(jcoFunction);
            logger.debug(" Time Confirm Finish Time: "
                    + DateUtil.convertDate(new Date(), DateUtil.SDF_YYYYMMDDHHMMSS_SSS_DASH));

            //RFC ȣ���� �ڵ带 ������Ƽ�� ��´�.
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
     * RFC ȣ�� �Ŀ� Export �Ķ���͸� ��ȯ�Ѵ�.
     * Import �Ķ���Ϳ� Export �Ķ���͸� �̿��ؼ� ó��
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
            //Ŀ�ؼ� ���� �� Function ��ü�� ������ �� Function ȣ��
            jcoClient = getJcoConnection();
            jcoFunction = createFunction(jcoClient, functionName);
            jcoParam = jcoFunction.getExportParameterList();
            jcoIParam = jcoFunction.getImportParameterList();

            //RFC �Ķ���� ����			
            logger.debug(" ******** RFC �Է� �Ķ���� Start ********* ");
            for (int i = 0; i < paramName.length; i++) {
                if (param.get(paramName[i][0]) != null && !param.get(paramName[i][0]).equals("")) {
                    jcoIParam.setValue(param.get(paramName[i][0]), paramName[i][1]);
                    logger.debug(paramName[i][1] + ": " + param.get(paramName[i][0]));
                }
            }
            logger.debug(" ******** RFC �Է� �Ķ���� End ********* ");

            jcoFunction.setImportParameterList(jcoIParam);
            jcoClient.execute(jcoFunction);

            //RFC ȣ���� �ڵ带 ������Ƽ�� ��´�.
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
     * RFC Function�� ȣ���Ѵ�.
     * ��κ��� RFC Function�� ������ �� �ְ� import �Ķ���� �� �Է����̺�(�ٰ�) ���� �Է��ϰ�, 
     * export �Ķ���� �� ������̺��� ���� ��ȯ�Ѵ�. 
     *  
     * @param functionName : ȣ���� RFC Function �̸�
     * @param importData : import �Ķ���Ϳ� ������ ������
     * @param importParamName : import�� �Ķ���� �̸�
     * @param tableNameIn : �Է� ���̺� �̸�
     * @param inputDataList : �Է� ���̺� ������ ������ ����Ʈ 
     * @param inputParamName : �Է� ���̺� ���� ������ �ʵ� �̸�
     * @param exportName : export�� �Ķ���� �̸�
     * @param tableNameOut : ��� ���̺� �̸�
     * @param outputParamName : ��� ���̺��� ���� ������ �ʵ� �̸�
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
            logger.debug(" ******** RFC Function - " + functionName + " ����  ********* ");

            //Ŀ�ؼ� �� Function ��ü ����
            jcoClient = getJcoConnection();
            jcoFunction = createFunction(jcoClient, functionName);

            //RFC - import �����Ͱ� ������ import �Ķ���� ���̺� �� ����
            if (importData != null && importParamName != null) {
                logger.debug(" ******** RFC - import �Ķ���� ");
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

            //RFC - �Է� ���̺� �� ���� - ��ũ
            if (inputDataList != null && inputParamName != null) {
                jcoTableIn = jcoFunction.getTableParameterList().getTable(tableNameIn);

                for (int k = 0; k < inputDataList.size(); k++) {
                    inputParam = (HashMap)inputDataList.get(k);
                    jcoTableIn.appendRow();

                    logger.debug(" ******** RFC �Է����̺� �Ķ���� Start (Row " + k + ") ********* ");
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

            //RFC ���� 
            jcoClient.execute(jcoFunction);

            //RFC - export �� ��ȯ
            if (exportName != null) {
                exportMap = new HashMap();
                jcoParamExport = jcoFunction.getExportParameterList();

                logger.debug(" ******** RFC Export �� ********* ");
                for (int j = 0; j < exportName.length; j++) {
                    exportMap.put(exportName[j][0], jcoParamExport.getValue(exportName[j][1]));
                    if (logger.isDebugEnabled())
                        logger.debug(exportName[j][1] + ": " + jcoParamExport.getValue(exportName[j][1]));
                }
            }

            //RFC - ������ ���̺� �� ��ȯ
            if (tableNameOut != null && outputParamName != null) {
                resultList = new ArrayList();
                jcoTableOut = jcoFunction.getTableParameterList().getTable(tableNameOut);
                if (jcoTableOut != null) {
                    for (int i = 0; i < jcoTableOut.getNumRows(); i++) {
                        tempMap = new HashMap();
                        jcoTableOut.setRow(i);

                        logger.debug(" ******** RFC ��� ���̺� �� (Row " + i + ") ********* ");
                        for (int j = 0; j < outputParamName.length; j++) {
                            tempMap.put(outputParamName[j][0], jcoTableOut.getString(outputParamName[j][1]));
                            if (logger.isDebugEnabled())
                                logger.debug(outputParamName[j][1] + ": " + jcoTableOut.getString(outputParamName[j][1]));
                        }

                        resultList.add(tempMap);
                    }
                }
            }

            //ȣ���� �ڵ� �� ȣ������ ����
            resultMap = new HashMap();

            //export ���
            if (exportMap != null) {
                resultMap.put("export", exportMap);
            }

            //��� ���̺�
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

            //Ŀ�ؼ� �� Function ��ü ����
            jcoClient = getJcoConnection();
            jcoFunction = createFunction(jcoClient, functionName);

            //RFC - import �����Ͱ� ������ import �Ķ���� ���̺� �� ����
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

            //RFC - �Է� ���̺� �� ���� - ��ũ
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

            //RFC - ������ ���̺� �� ��ȯ
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

            //ȣ���� �ڵ� �� ȣ������ ����
            resultMap = new WeakHashMap();

            //export ���
            if (exportMap != null) {
                resultMap.put("export", exportMap);
            }

            //��� ���̺�
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

            //Ŀ�ؼ� �� Function ��ü ����
            jcoClient = getJcoConnection();
            jcoFunction = createFunction(jcoClient, functionName);

            //RFC - import �����Ͱ� ������ import �Ķ���� ���̺� �� ����
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

            //RFC - �Է� ���̺� �� ���� - ��ũ
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

            //RFC - ������ ���̺� �� ��ȯ
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
                        logger.debug(functionName + " ��ȸ�Ǽ�: " + resultList.size());
                    }
                }
            }

            //ȣ���� �ڵ� �� ȣ������ ����
            resultMap = new WeakHashMap();

            //export ���
            if (exportMap != null) {
                resultMap.put("export", exportMap);
            }

            //��� ���̺�
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

            //Ŀ�ؼ� �� Function ��ü ����
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

            //Ŀ�ؼ� �� Function ��ü ����
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
            logger.debug(" ******** RFC Function - " + functionName + " ����  ********* ");

            //Ŀ�ؼ� �� Function ��ü ����
            jcoClient = getJcoConnection();
            jcoFunction = createFunction(jcoClient, functionName);

            //RFC - import �����Ͱ� ������ import �Ķ���� ���̺� �� ����
            if (importData != null && importParamName != null) {
                logger.debug(" ******** RFC - import �Ķ���� ");
                jcoParamImport = jcoFunction.getImportParameterList();

                for (int i = 0; i < importParamName.length; i++) {
                    if (importData.get(importParamName[i][0]) != null
                            && !importData.get(importParamName[i][0]).equals("")) {
                        jcoParamImport.setValue(importData.get(importParamName[i][0]), importParamName[i][1]);
                        logger.debug(importParamName[i][1] + ": " + importData.get(importParamName[i][0]));
                    }
                }
            }

            //RFC - �Է� ���̺� �� ���� - ��ũ
            if (inputDataList != null && inputParamName != null) {
                jcoTableIn = jcoFunction.getTableParameterList().getTable(tableNameIn);

                for (int k = 0; k < inputDataList.size(); k++) {
                    inputParam = (HashMap)inputDataList.get(k);
                    jcoTableIn.appendRow();

                    logger.debug(" ******** RFC �Է����̺� �Ķ���� Start (Row " + k + ") ********* ");
                    for (int i = 0; i < inputParamName.length; i++) {
                        if (inputParam.get(inputParamName[i][0]) != null
                                && !inputParam.get(inputParamName[i][0]).equals("")) {
                            jcoTableIn.setValue(inputParam.get(inputParamName[i][0]), inputParamName[i][1]);
                            logger.debug(inputParamName[i][1] + ": " + inputParam.get(inputParamName[i][0]));
                        }
                    }
                }
            }

            //RFC ���� 
            jcoClient.execute(jcoFunction);

            //RFC - export �� ��ȯ
            if (exportName != null) {
                exportMap = new HashMap();
                jcoParamExport = jcoFunction.getExportParameterList();

                logger.debug(" ******** RFC Export �� ********* ");
                for (int j = 0; j < exportName.length; j++) {
                    exportMap.put(exportName[j][0], jcoParamExport.getValue(exportName[j][1]));
                    logger.debug(exportName[j][1] + ": " + jcoParamExport.getValue(exportName[j][1]));
                }
            }

            //RFC - ������ ���̺� �� ��ȯ
            if (tableNameOut != null && outputParamName != null) {
                resultList = new ArrayList();
                jcoTableOut = jcoFunction.getTableParameterList().getTable(tableNameOut);
                if (jcoTableOut != null) {
                    for (int i = 0; i < jcoTableOut.getNumRows(); i++) {
                        tempMap = new WeakHashMap();
                        jcoTableOut.setRow(i);

                        //Class klass = ZSPM001.class;
                        //Object dto = klass.newInstance();   

                        logger.debug(" ******** RFC ��� ���̺� �� (Row " + i + ") ********* ");
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

            //ȣ���� �ڵ� �� ȣ������ ����
            resultMap = new WeakHashMap();

            //export ���
            if (exportMap != null) {
                resultMap.put("export", exportMap);
            }

            //��� ���̺�
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