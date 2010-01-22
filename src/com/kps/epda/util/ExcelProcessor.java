/**
 * 
 */
package com.kps.epda.util;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;


public class ExcelProcessor {
    
    List regions;
    Short[] mergerdCol;
    int regionSize = 0;
    HSSFSheet sheet;
    HSSFWorkbook wb;
    

    public ExcelProcessor(File excelFile) {
        regions = new ArrayList();
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(excelFile));
            // 워크북 오브젝트의 취득
            wb = new HSSFWorkbook(fs);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setRegion(HSSFSheet sheet){
        this.sheet = sheet;
        this.regionSize = sheet.getNumMergedRegions();

        for (int i = 0; i < regionSize; i++) {
            this.regions.add(sheet.getMergedRegionAt(i));
        }
    }

    private int isMergedCell(int rowIdx, short colIdx){

        for (int i = 0; i < regions.size(); i++) {
            Region region = (Region)regions.get(i);

            if (region.contains(rowIdx, colIdx))
                return i;
        }
        return -1;
    }

    private int getMergedRowIdx(int regionIndex){
        return ((Region)regions.get(regionIndex)).getRowFrom();
    }

    /**
     * <pre> 
     * Excel 파일 Workbook을 List<HashMap<String,String>> 형태로 리턴한다.    
     * 
     * </pre>
     * @param  sheetName Excel Sheet 
     * @return List
     */
    
    public List toHashMapList() {
        List<Map<String,String>> hashMapList = new ArrayList<Map<String,String>>();
        int sheetNum = wb.getNumberOfSheets();        
        for (int i = 0; i < sheetNum; i++) {
            HSSFSheet sheet = wb.getSheetAt(i);
            for (Iterator<HSSFRow> rit = (Iterator<HSSFRow>)sheet.rowIterator(); rit.hasNext();) {
                HSSFRow row = rit.next();
                Map<String,String> rowMap = new HashMap<String,String>();
                for (Iterator<HSSFCell> cit = (Iterator<HSSFCell>)row.cellIterator(); cit.hasNext(); ) {
                    HSSFCell cell = cit.next();                    
                    if (cell != null) {                       
                        int type = cell.getCellType();
                        String value = null;
                        // 숫자를 스트링으로 변환 보여주기
                        DecimalFormat dcf = new DecimalFormat();
                        
                        switch (type) {
                            case HSSFCell.CELL_TYPE_BOOLEAN:
                                //boolean bdata = cell.getBooleanCellValue();
                                value = "..";
                                break;

                            case HSSFCell.CELL_TYPE_NUMERIC:
                                //String tDouble = cell.getNumericCellValue() + "";
                                //String[] slice = StringUtil.split(tDouble, '.');
                                //if (slice.length == 2 && slice[1].equals("0")) {
                                //    tDouble = slice[0];
                                //}
                                value = dcf.format(cell.getNumericCellValue());
                                break;

                            case HSSFCell.CELL_TYPE_STRING:
                                value = cell.getRichStringCellValue().toString();
                                break;

                            case HSSFCell.CELL_TYPE_BLANK:
                            case HSSFCell.CELL_TYPE_ERROR:   
                            default:
                                value = "---";
                        } // switch                        
                        rowMap.put("Excel_" + cell.getCellNum(), value);
                    } // if
                } // for cell          
                hashMapList.add(rowMap);
            } // for row
        } // for sheet
        
        return hashMapList;
    }
    
    public List toList(int sheetIndex){
        if  (sheetIndex > wb.getNumberOfSheets() ) {
            return null;
        }        
        HSSFSheet sheet = wb.getSheetAt(sheetIndex);
        List<Map<String, String>> hashMapList = new ArrayList<Map<String, String>>();
        for (Iterator<HSSFRow> rit = (Iterator<HSSFRow>)sheet.rowIterator(); rit.hasNext();) {
            HSSFRow row = rit.next();
            Map<String, String> rowMap = new LinkedHashMap<String, String>();
            for (Iterator<HSSFCell> cit = (Iterator<HSSFCell>)row.cellIterator(); cit.hasNext();) {
                HSSFCell cell = cit.next();
                if (cell != null) {
                    int type = cell.getCellType();
                    String value = null;
                    // 숫자를 스트링으로 변환 보여주기
                    DecimalFormat dcf = new DecimalFormat();

                    switch (type) {
                        case HSSFCell.CELL_TYPE_BOOLEAN:
                            //boolean bdata = cell.getBooleanCellValue();
                            value = "..";
                            break;

                        case HSSFCell.CELL_TYPE_NUMERIC:
                            //String tDouble = cell.getNumericCellValue() + "";
                            //String[] slice = StringUtil.split(tDouble, '.');
                            //if (slice.length == 2 && slice[1].equals("0")) {
                            //    tDouble = slice[0];
                            //}
                            value = dcf.format(cell.getNumericCellValue());
                            break;

                        case HSSFCell.CELL_TYPE_STRING:
                            value = cell.getRichStringCellValue().toString();
                            break;

                        case HSSFCell.CELL_TYPE_BLANK:
                        case HSSFCell.CELL_TYPE_ERROR:
                        default:
                            value = "---";
                    } // switch                        
                    rowMap.put("Excel_" + cell.getCellNum(), value);
                } // if
            } // for cell          
            hashMapList.add(rowMap);
        } // for row

        return hashMapList;
    }
    
    public List toListWithHeader(int sheetIndex, int skip){
        if  (sheetIndex > wb.getNumberOfSheets() ) {
            return null;
        }        
        HSSFSheet sheet = wb.getSheetAt(sheetIndex);
        List<Map<String, String>> hashMapList = new ArrayList<Map<String, String>>();
        
        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();
        
        HSSFRow headerRow = sheet.getRow(firstRowNum);
        int cellCount = headerRow.getLastCellNum();
        String[] headers = new String[cellCount];
        for (short col = 0; col < cellCount; col++) { 
            headers[col] = headerRow.getCell(col).getRichStringCellValue().toString();
        }
        
        for (int rownum = firstRowNum + skip; rownum < lastRowNum + 1; rownum++ ) {
            HSSFRow row = sheet.getRow(rownum);
            if (row == null) continue;
            Map<String, String> rowMap = new LinkedHashMap<String, String>();
            
            for (short cellnum = 0; cellnum < cellCount; cellnum++ ) {
                HSSFCell cell = row.getCell(cellnum);
                if (cell == null) {
                    
                } else {
                    int type = cell.getCellType();
                    String value = null;                    
                    DecimalFormat dcf = new DecimalFormat();
                    switch (type) {
                        case HSSFCell.CELL_TYPE_BOOLEAN:                           
                            value = "..";
                            break;
                        case HSSFCell.CELL_TYPE_NUMERIC:                            
                            value = dcf.format(cell.getNumericCellValue());
                            value = value.replace(",", "");                            
                            break;
                        case HSSFCell.CELL_TYPE_STRING:
                            value = cell.getRichStringCellValue().toString();
                            break;
                        case HSSFCell.CELL_TYPE_BLANK:                        
                        case HSSFCell.CELL_TYPE_ERROR:
                        default:
                            value = (type == 0) ? "0" : "";
                    } // switch  
                    rowMap.put(headers[cell.getCellNum()], value);
                } // if
            } // for cellnum
            hashMapList.add(rowMap);
            //System.out.println(rowMap);
        } // ro rownum        
                      
        return hashMapList;
    }
    
    
    /**
     * <pre> 
     * Excel 파일에 sheetName에 해당하는 Excel Sheet 정보를 LMultiData 형태로 리턴한다.
     * 이때 sheet의 병합된 정보는 모두 개별 셀에 데이터로 정리되어 리턴된다.
     * 
     * </pre>
     * @param  sheetName Excel Sheet 
     * @return LMultiData
     */
/*
    public List toHashMapList(String sheetName){

        List result = new ArrayList();

        HSSFSheet sheet = null;
        if (sheetName == null) {
            sheet = wb.getSheetAt(0); // 엑셀을 Sheet 단위로 리턴 받는다.
        } else {
            sheet = wb.getSheet(sheetName); // 엑셀을 Sheet 단위로 리턴 받는다.
        }
        setRegion(sheet);

        if (sheet != null && sheet.getLastRowNum() > 0) {

            int firstRowSize = sheet.getFirstRowNum();
            int lastRowSize = sheet.getPhysicalNumberOfRows();

            HSSFRow firstRow = sheet.getRow(firstRowSize);
            final int size = firstRow.getLastCellNum();

            for (int rowIdx = firstRowSize + 1; rowIdx < lastRowSize; rowIdx++) {
                // 행을 표시하는 오브젝트의 취득
                HSSFRow row = sheet.getRow(rowIdx);

                // 행에 데이터가 없는 경우
                if (row == null) {
                    continue;
                }

                for (short i = 0; i < size; i++) {
                    // 셀을 표시하는 오브젝트를 취득
                    HSSFCell cell = row.getCell(i);
                    if (cell == null)
                        result.addString(firstRow.getCell(i).getStringCellValue(), "");
                    else {
                        int type = cell.getCellType();
                        // 데이터 종류별로 데이터를 취득
                        switch (type) {
                            case HSSFCell.CELL_TYPE_BOOLEAN:
                                boolean bdata = cell.getBooleanCellValue();
                                result.addString(firstRow.getCell(i).getStringCellValue(), new Boolean(bdata)
                                        .toString());
                                break;

                            case HSSFCell.CELL_TYPE_NUMERIC:
                                String tDouble = cell.getNumericCellValue() + "";
                                String[] slice = StringUtil.split(tDouble, '.');
                                if (slice.length == 2 && slice[1].equals("0")) {
                                    tDouble = slice[0];
                                }
                                result.addString(firstRow.getCell(i).getStringCellValue(), tDouble);
                                break;

                            case HSSFCell.CELL_TYPE_STRING:
                                result.addString(firstRow.getCell(i).getStringCellValue(), cell.getStringCellValue());
                                break;

                            case HSSFCell.CELL_TYPE_BLANK:
                            case HSSFCell.CELL_TYPE_ERROR:
                            case HSSFCell.CELL_TYPE_FORMULA:
                                int checkMerge = isMergedCell(rowIdx, i);
                                if (checkMerge > -1) {
                                    result.addString(firstRow.getCell(i).getStringCellValue(), result.getString(
                                            firstRow.getCell(i).getStringCellValue(), getMergedRowIdx(checkMerge) - 1));
                                } else {
                                    result.addString(firstRow.getCell(i).getStringCellValue(), "");
                                }

                            default:
                                continue;
                        }

                    }
                }
            }
        }
        return result;
    }
*/
    
  

}
