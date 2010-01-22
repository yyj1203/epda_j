/*******************************************************************
 * 한전KPS WPS Copyright (c) 2007 by LG CNS, Inc. All rights reserved. $Id:
 * PoiExcel.java,v 1.4 2008/03/10 05:43:04 ksm Exp $
 * 
 * @author $Author: epm $
 * @version $Revision: 1.5 $
 */
package com.kps.epda.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class PoiExcel {

    /**
     * 엑셀파일을 생성한다.
     * @param title 	- 타이틀헤더 
     * @param result 	- 출력할데이터
     * 
     * @return
     */
    public HSSFWorkbook writeExcel(String[][] headerInfo, List result) throws Exception{
        //엑셀 워크북을 생성
        HSSFWorkbook workbook = new HSSFWorkbook();
        //시트를 생성합니다.
        HSSFSheet sheet = workbook.createSheet("DATA");
        //Cell 스타일을 지정
        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setLeftBorderColor(HSSFColor.GREEN.index);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setRightBorderColor(HSSFColor.BLUE.index);
        style.setTopBorderColor(HSSFColor.BLACK.index);

        //타이틀 로우를 하나 생성
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headerInfo.length; i++) {
            HSSFCell cell = row.createCell((short)i);
            cell.setCellStyle(style);
            HSSFRichTextString hssfRichTextString = new HSSFRichTextString(headerInfo[i][0]);
            cell.setCellValue(hssfRichTextString);
            
        }

        if (result != null) {
            //데이터 로우 생성 및 저장		
            for (int i = 0; i < result.size(); i++) {
                //데이터 로우 생성
                HSSFRow dataRow = sheet.createRow(i + 1);
                HashMap hMap = (HashMap)result.get(i);

                for (int j = 0; j < headerInfo.length; j++) {
                    String value = "";

                    if (hMap.get(headerInfo[j][1]) != null) {
                        value = hMap.get(headerInfo[j][1]).toString();                        
                    } else {
                        String info = headerInfo[j][1];
                        String[] infoArr = info.split(":");                       
                        if (infoArr.length > 1) {                            
                            switch (infoArr[0].charAt(0)) {
                                case '*':
                                    String s1 = (String)hMap.get(infoArr[1]);
                                    String s2 = (String)hMap.get(infoArr[2]);    
                                    value = "" + (parseInt(s1) * parseInt(s2)); 
                                    break;

                                default:
                                    break;
                            }
                        }
                    }

                    HSSFCell cell = dataRow.createCell((short)j);
                    HSSFRichTextString hssfRichTextString = new HSSFRichTextString(value);
                    cell.setCellValue(hssfRichTextString);
                }
            }
        }

        return workbook;
    }
    
    public HSSFWorkbook writeExcel2(List headerInfo, List result) throws Exception{
        //엑셀 워크북을 생성
        HSSFWorkbook workbook = new HSSFWorkbook();
        //시트를 생성합니다.
        HSSFSheet sheet = workbook.createSheet("DATA");
        //Cell 스타일을 지정
        HSSFCellStyle style = workbook.createCellStyle();
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setLeftBorderColor(HSSFColor.GREEN.index);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setRightBorderColor(HSSFColor.BLUE.index);
        style.setTopBorderColor(HSSFColor.BLACK.index);

        //타이틀 로우를 하나 생성
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headerInfo.size(); i++) {
            HSSFCell cell = row.createCell((short)i);
            cell.setCellStyle(style);
            Map map = (Map)headerInfo.get(i);
            String label = (String)map.get("label");
            HSSFRichTextString hssfRichTextString = new HSSFRichTextString(label);
            cell.setCellValue(hssfRichTextString);       
        }

        if (result != null) {
            //데이터 로우 생성 및 저장        
            for (int i = 0; i < result.size(); i++) {
                //데이터 로우 생성
                HSSFRow dataRow = sheet.createRow(i + 1);
                HashMap hMap = (HashMap)result.get(i);

                for (int j = 0; j < headerInfo.size(); j++) {
                    String value = "";
                    Map map = (Map)headerInfo.get(j);
                    String label = (String)map.get("data");
                    if (hMap.get(map.get("data")) != null) {
                        value = hMap.get(map.get("data")).toString();                        
                    } else {
                        String info = (String)map.get("data");
                        String[] infoArr = info.split(":");                       
                        if (infoArr.length > 1) {                            
                            switch (infoArr[0].charAt(0)) {
                                case '*':
                                    String s1 = (String)hMap.get(infoArr[1]);
                                    String s2 = (String)hMap.get(infoArr[2]);    
                                    value = "" + (parseInt(s1) * parseInt(s2)); 
                                    break;

                                default:
                                    break;
                            }
                        }
                    }
                    HSSFCell cell = dataRow.createCell((short)j);
                    HSSFRichTextString hssfRichTextString = new HSSFRichTextString(value);
                    cell.setCellValue(hssfRichTextString);
                }
            }
        }
        return workbook;
    }
    
    public int parseInt(String value) {
        return Integer.parseInt(nvl(value, "0"));
    }

    public <T> T nvl(T a, T b){
        return (a == null) ? b : a;
    }
}
