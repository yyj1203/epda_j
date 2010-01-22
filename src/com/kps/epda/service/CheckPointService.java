package com.kps.epda.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kps.epda.dao.CheckPointDao;
import com.kps.epda.dao.CommonDao;
import com.kps.epda.util.Constants;
import com.kps.epda.util.ExcelProcessor;
import com.kps.pm.saprfc.Z_PM_GET_TPLNRLIST;


public class CheckPointService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private CheckPointDao checkPointDao;
    private CommonDao commonDao;
    public CommonDao getCommonDao(){
        return commonDao;
    }

    public void setCommonDao(CommonDao commonDao){
        this.commonDao = commonDao;
    }

    private static int systemLen = 8;

    public CheckPointDao getCheckPointDao(){
        return checkPointDao;
    }

    public void setCheckPointDao(CheckPointDao checkPointDao){
        this.checkPointDao = checkPointDao;
    }

    public List getPmtList() throws Exception{
        List list = null;
        list = checkPointDao.getPmtList();
        return list;
    }

    public List getItemList(Map map) throws Exception{
        List list = null;
        list = checkPointDao.getItemSeq(map);
        return list;
    }

    public List getCheckPositions(Map map) throws Exception{
        List list = null;
        System.out.println(map);
        list = checkPointDao.getCheckPositions(map);
        return list;
    }

    public void updateMinMax(Map map) throws Exception{        
        try {
            checkPointDao.updateMinMax(map);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public List getSystems(Map map) throws Exception{
        List list = null;        
        list = checkPointDao.getSystems(map);        
        return list;
    }

    public List getOrganization(Map map) throws Exception{
        List list = null;
        list = commonDao.queryForList("getOrganization", map);
        return list;
    }

    public List getPmtTree(Map map) throws Exception{
        List list = null;
        list = commonDao.queryForList("getPmtTree", map);
        return list;
    }
    
    public List getINGRPCodeList(Map map) throws Exception {
        List list = null;
        list = commonDao.queryForList("getINGRPCodeList", map);
        return list;
    }
    
    public List getGWERKCodeList(Map map) throws Exception {
        List list = null;
        list = commonDao.queryForList("getGWERKCodeList", map);
        return list;
    }
    
    public List getSearchResult(Map map) throws Exception{
        List list = null;
        try {
            list = commonDao.queryForList("getSearchResult", map);            
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return list;
    }

    public List getBeberList(Map map) throws Exception{
        List list = checkPointDao.getBeberList(map);
        System.out.println("  123 한글 테스트 dd");             
        return list;
    }

    public List getOrgList(Map map) throws Exception{
        List list = null;
        list = checkPointDao.getOrgList(map);
        return list;
    }

    public List getPmtsForPlant(Map map) throws Exception{
        List list = null;
        list = commonDao.queryForList("getPmtsForPlant", map);
        return list;
    }
    
    //PMT Upload start 
    
    @SuppressWarnings("unchecked")
    public Map doUploadPMTItem(byte[] bytes, String fileName){
        Map resultMap = null;
        List excelList = null;
        List itemList = new ArrayList();
        String rootPath = Constants.UPLOAD_WINDOWS_PATH;
        fileName = rootPath + "pmt/" + fileName;
        logger.error("#Upload PMT File: " + fileName);
        
        try {
            File f = new File(fileName);
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bytes);
            fos.close();
            ExcelProcessor ep = new ExcelProcessor(f);
            excelList = ep.toListWithHeader(0, 2);
            for (Object row : excelList) {
                Map bundleMap = parsingItemResult((Map)row);
                itemList.add(bundleMap.get("itemMap"));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } 
        resultMap = new HashMap();
        resultMap.put("excelList", excelList);
        resultMap.put("itemList", itemList);
        return resultMap;                
    }

    @SuppressWarnings("unchecked")
    private Map parsingItemResult(Map row){
        int len1 = 8;
        Map bundleMap = new HashMap();
        Map itemMap = new HashMap();
        Map pmtMap = new HashMap();
        String pmtCode = (String)row.get("PDM_PMT_CODE");
        if (pmtCode == null)
            return bundleMap;

        pmtMap.put("PDM_PMT_CODE", row.get("PDM_PMT_CODE"));
        pmtMap.put("PDM_PMT_DETAIL", row.get("PDM_PMT_DETAIL"));

        try {
            checkPointDao.mergePmt(pmtMap);
        } catch (SQLException e1) {
            logger.error(e1.getMessage());
        }

        String atnam = (String)row.get("ATNAM");
        Object unitSeq = getUnitSeq(atnam);
        String itemCode = getItemCode((String)row.get("ITEM_KIND"));

        itemMap.put("ITEM_NAME", row.get("PTTXT"));
        itemMap.put("UNIT_SEQ", unitSeq);
        itemMap.put("ITEM_KIND", itemCode);
        itemMap.put("ITEM_USER", "system");
        itemMap.put("ITEM_STS", "Y");
        itemMap.put("PDM_WATCH", row.get("PDM_WATCH"));
        itemMap.put("PDM_PMT_CODE", row.get("PDM_PMT_CODE"));
        itemMap.put("PDM_INSP_SEQ", row.get("PDM_INSP_SEQ"));

        try {
            int count = (Integer)checkPointDao.getItemCount(itemMap);
            if (count == 0) {
                checkPointDao.insertItem(itemMap);
                itemMap.put("insertResult", "success");
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
            itemMap.put("insertResult", "fail");
        }
        bundleMap.put("itemMap", itemMap);
        return bundleMap;
    }

    private List byteStreamToExcelList(byte[] bytes, String fileName) throws Exception{
        File f = new File(fileName);
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bytes);
        fos.close();
        ExcelProcessor ep = new ExcelProcessor(f);
        return ep.toListWithHeader(0, 2);
    }

    /*
     * Excel File upload Main Method
     */
    @SuppressWarnings("unchecked")
    public Map doUploadPoint(byte[] bytes, String fileName) throws Exception{
        List excelList = null;
        List errorList = new ArrayList();
        List successList = new ArrayList();
        Map resultMap = new HashMap();
        String rootPath = Constants.UPLOAD_WINDOWS_PATH;
        fileName = rootPath + "point/" + fileName;
        excelList = byteStreamToExcelList(bytes, fileName);
        int count = 0;
        for (Object row : excelList) {
            count++;
            //if (count > 150) break;
            Map resultRow = parseResult((Map)row);
            String error = (String)resultRow.get("error");
            if (error != null && error.length() > 0) {
                errorList.add(resultRow);
            } else {
                successList.add(resultRow);
            }
        }
        resultMap.put("totalCount", excelList.size());
        resultMap.put("errorList", errorList);
        resultMap.put("successList", successList);
        return resultMap;
    }

    private String validateRow(Map row){
        String tplnr = (String)row.get("TPLNR");
        if (tplnr == null || tplnr.length() < systemLen)
            return "부적합한 기능위치 코드";
        return "success";
    }

    @SuppressWarnings("unchecked")
    private Map parseResult(Map row) {
        Map sysMap = new HashMap();
        Map pointMap = new HashMap();
        Map epiMap = new HashMap();

        long pointSeq = 0;
        String validate = validateRow(row);
        if (!validate.equals("success")) {
            row.put("error", validate);
            return row;
        }
        
        try {
        
            String tplnr = (String)row.get("TPLNR");
            String system = tplnr.substring(0, systemLen);
            Map systemInfo = getTplnrInfo(system);
            Map tplnrInfo = getTplnrInfo(tplnr);
    
            String systemCode = system;
            String systemName = (String)systemInfo.get("PLTXT");
            String tplnrName = (String)tplnrInfo.get("PLTXT");
            String beber = (String)systemInfo.get("BEBER");
    
            pointMap.put("IWERK", row.get("IWERK"));
            pointMap.put("SWERK", row.get("IWERK"));
            pointMap.put("BEBER", beber);
            pointMap.put("TPLNR", row.get("TPLNR"));
            pointMap.put("WKCNT", row.get("PDM_GWERK"));
            pointMap.put("POINT_NAME", row.get("PSORT"));
            pointMap.put("POINT_STS", "Y");
            pointMap.put("POINT_USER", "system");
            pointMap.put("SYSTEM_CODE", systemCode);

        
            List points = checkPointDao.getPointSeq(pointMap);
            if (points != null && points.size() > 0) {
                Map pointResult = (Map)points.get(0);
                pointSeq = ((BigDecimal)pointResult.get("POINT_SEQ")).intValue();
            }

            if (pointSeq == 0) {
                sysMap.put("TPLNR", row.get("TPLNR"));
                sysMap.put("IWERK", row.get("IWERK"));
                sysMap.put("BEBER", beber);
                sysMap.put("SYSTEM_CODE", systemCode);
                sysMap.put("SYSTEM_NAME", systemName);
                sysMap.put("PARENT_CODE", "");

                // 계통 저장
                int systemCount = (Integer)checkPointDao.getSystemCount(sysMap);
                if (systemCount == 0) {
                    checkPointDao.insertSystem(sysMap);
                }
                //기능위치 저장
                sysMap.put("SYSTEM_CODE", tplnr);
                sysMap.put("SYSTEM_NAME", tplnrName);
                sysMap.put("PARENT_CODE", systemCode);
                systemCount = (Integer)checkPointDao.getSystemCount(sysMap);
                if (systemCount == 0) {
                    checkPointDao.insertSystem(sysMap);
                }

                //측정위치 저장
                pointSeq = checkPointDao.insertPoint(pointMap);

            }
            epiMap.put("PDM_PMT_CODE", row.get("PDM_PMT_CODE"));
            epiMap.put("PDM_INSP_SEQ", row.get("PDM_INSP_SEQ"));

            List items = checkPointDao.getItemSeq(epiMap);
            long itemSeq = 0;
            Map itemResult = null;
            if (items != null && items.size() > 0) {
                itemResult = (Map)items.get(0);
                itemSeq = ((BigDecimal)itemResult.get("ITEM_SEQ")).intValue();
            }

            if (itemSeq > 0) {
                epiMap.put("POINT_SEQ", pointSeq);
                epiMap.put("ITEM_SEQ", itemSeq);
                epiMap.put("UNIT_SEQ", itemResult.get("UNIT_SEQ"));
                epiMap.put("OIL_CD", "NON");
                epiMap.put("ITEM_TARG", row.get("DESIC"));
                epiMap.put("ITEM_MAX", row.get("MRMAC"));
                epiMap.put("ITEM_MIN", row.get("MRMIC"));
                epiMap.put("MPTYP", row.get("MPTYP"));
                epiMap.put("CLASS", row.get("CLASS"));
                epiMap.put("DECIM", row.get("DECIM"));
                epiMap.put("CODGR", row.get("CODGR"));
                epiMap.put("PDM_WEEKDAY", row.get("PDM_WEEKDAY"));
                epiMap.put("PDM_WATCH", row.get("PDM_WATCH"));
                epiMap.put("PDM_INGRP", row.get("PDM_INGRP"));
                epiMap.put("PDM_GWERK", row.get("PDM_GWERK"));
                epiMap.put("PDM_CATEGORY", row.get("PDM_CATEGORY"));
                epiMap.put("EQART", row.get("EQART"));
                epiMap.put("PDM_POINT_DETAIL", row.get("PDM_POINT_DETAIL"));
                epiMap.put("PDM_TERM", row.get("PDM_TERM"));
                String termCode = "" + row.get("PDM_TERM") + row.get("PDM_TERM_MON") + row.get("PDM_TERM_WEEK")
                        + row.get("PDM_WEEKDAY");
                epiMap.put("PDM_TERM_CODE", termCode);
                epiMap.put("PDM_TERM_MON", row.get("PDM_TERM_MON"));
                epiMap.put("PDM_TERM_WEEK", row.get("PDM_TERM_WEEK"));
                epiMap.put("PDM_TERM_START", row.get("PDM_TERM_START"));
                epiMap.put("PDM_TERM_USE_START", row.get("PDM_TERM_USE_START"));
                epiMap.put("PDM_TERM_USE_END", row.get("PDM_TERM_USE_END"));

                //측정위치 측정내용 연계 저장
                int epiCount = (Integer)checkPointDao.getEpiCount(epiMap);
                if (epiCount == 0) {
                    System.out.println(epiMap);
                    checkPointDao.insertEpi(epiMap);
                } else {
                    row.put("error", "측정위치 측정내용 연계가 이미 존재합니다.");
                }
            } else {
                row.put("error", "측정내용이 없습니다. PMT번호와 점검순번을 확인하세요.");
            }
        } catch (Exception e) {
            row.put("error", "미확인 에러: " + e.toString());
        }

        return row;
    }

    @SuppressWarnings("unchecked")
    private Object getUnitSeq(String atnam){
        Object unitSeq = 0L;
        Map parameterObject = new HashMap();
        parameterObject.put("UNIT_SPEC_CODE", atnam);
        try {
            Object obj = checkPointDao.select("getUnitSeq", parameterObject);
            if (obj != null) {
                Map unitObj = (Map)obj;
                unitSeq = unitObj.get("UNIT_SEQ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unitSeq;
    }

    @SuppressWarnings("unchecked")
    private String getItemCode(String value){
        String itemSeq = "";
        Map parameterObject = new HashMap();
        parameterObject.put("CCOD_DES", value);
        try {
            Object obj = checkPointDao.select("getItemCode", parameterObject);
            if (obj != null) {
                Map unitObj = (Map)obj;
                itemSeq = (String)unitObj.get("CCOD_CD2");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemSeq;
    }

    @SuppressWarnings("unchecked")
    private Map getTplnrInfo(String tplnr) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("I_TPLNR", tplnr);
        Z_PM_GET_TPLNRLIST rfc = new Z_PM_GET_TPLNRLIST();
        Map result = rfc.execute((HashMap<String, Object>)map);
        Map export = (Map)result.get("export");
        Map tplnrMap = new HashMap();
        if (export.get("E_SUBRC").toString().equalsIgnoreCase("0")) {
            List output = (List)result.get("T_OUTPUT");
            if (output.size() > 0) {
                tplnrMap = (Map)output.get(0);
            }
        } else {
            System.out.println("Fail!");

        }
        return tplnrMap;
    }
}
