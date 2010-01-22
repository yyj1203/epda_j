package com.kps.epda.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.kps.epda.dao.CommonDao;
import com.kps.epda.dao.MeasurementDao;
import com.kps.epda.util.PoiExcel;

public class MeasurementService {
    @Autowired
    private MeasurementDao measurementDao;
    @Autowired
    private CommonDao commonDao;
    
    public void test(Map map) throws Exception {
        System.out.println(map);
    }
    
    public List getGroupMeasDate(Map map) throws Exception{
        List list = null;        
        list = commonDao.queryForList("getGroupMeasDate", map);
        return list;
    }
    
    public List getMeasForConfirm(Map map) throws Exception{
        List list = null;
        System.out.println(map);
        list = commonDao.queryForList("getMeasForConfirm", map);
        return list;
    }
    
    public List getMeasureValues(Map map) throws Exception {
        List list = new ArrayList();
        List epis = (List)map.get("epi");        
        for (Object epi : epis ) {
            Map parameterObject = (Map)epi;
            parameterObject.put("START_DATE", map.get("START_DATE"));
            parameterObject.put("END_DATE", map.get("END_DATE"));            
            
            List tempList = measurementDao.getMeasureValues(parameterObject);
            for (Object temp : tempList) {
                Map meas = (Map)temp;
                list.add(meas);
            }            
        }   
        
        return list;
    }
    
    public List getMeasureValueHistory(Map map) throws Exception {
        List list = null;
        
        list = measurementDao.getMeasureValueHistory(map);          
        return list;
    }
    
    public Map getMultiHistory(Map map) throws Exception {
        List list = null;
        List paramList = (List)map.get("measureValueAC");        
        Map resultMap = new HashMap();
        for (int i = 0; i < paramList.size(); i++) {
            Map param = (Map)paramList.get(i);
            param.put("START_DATE", map.get("startDate"));
            param.put("END_DATE", map.get("endDate"));
            param.put("TREND_NUM", "" + i);
            list = measurementDao.getMeasureValueHistory(param);
            resultMap.put("trend" + (i+1), list);
        }       
        return resultMap;
    }
    
    public void insertMeas(Map map) throws Exception {
        map.put("MEAS_USER", "1932452");        
        measurementDao.insertMeas(map);
    }
    
    public List getMeasureResultCodes(String ccod_cd1) throws Exception {
        Map parameterObject = new HashMap();
        parameterObject.put("CCOD_CD1", ccod_cd1);
        return measurementDao.queryForList("getEvibCode", parameterObject);
    }
    
    public List getConfirmTypeCodes() throws Exception {
        Map parameterObject = new HashMap();
        parameterObject.put("CCOD_CD1", "AE");
        return measurementDao.queryForList("getEvibCode", parameterObject);
    }
    
    public int updateMeasForConfirm(Map map) {
        int rval = 0;
        try {             
            rval = measurementDao.updateMeasForConfirm(map);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rval;
    }
    
    public void updateMeasValue(Map map) throws Exception  {
        commonDao.update("updateMeasValue", map);
       
    }
    
    public void updateConfirm(Map map) throws Exception {
        List list = (List)map.get("list");
        for (Object obj : list) {
            Map meas = (Map)obj;
            System.out.println(map);
            updateMeasForConfirm(meas);            
        }
    }
    
    public byte[] getExcelFromArray(Map map) throws Exception {
        
        FileInputStream fis;  
        byte[] data =null;  
        FileChannel fc;  
        String fileName = null;
        String filePath = null;
        //List headerInfo = null;
        List list = null;
        System.out.println(map);
        PoiExcel poiExcel = null;
        HSSFWorkbook workbook = null;
        FileOutputStream fileOut = null;
        String[][] headerInfo = { { "작업연월", "TPLNR" } };
        try {
            fileName = (String)map.get("fileName");
            filePath = System.getProperty("java.io.tmpdir") + "/" + fileName;         
            fileOut = new FileOutputStream(filePath);
            poiExcel = new PoiExcel();
            //headerInfo = (List)map.get("headerInfo");
            list = (List)map.get("list");
            workbook = poiExcel.writeExcel(headerInfo, list);
            workbook.write(fileOut);            
            fis = new FileInputStream(filePath);  
            fc = fis.getChannel();  
            data = new byte[(int)(fc.size())];  
            ByteBuffer bb = ByteBuffer.wrap(data);  
            fc.read(bb);  
        } catch (FileNotFoundException e) {  
            // TODO  
        } catch (IOException e) {  
            // TODO  
        }  
        catch (Exception e) {  
            e.printStackTrace();  
        }  
        return data; 
    }
   
   
   
}
