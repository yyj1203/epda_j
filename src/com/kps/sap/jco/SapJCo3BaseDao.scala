package com.kps.sap.jco

import java.util.{Map,HashMap,WeakHashMap}

import com.sap.conn.jco.AbapException
import com.sap.conn.jco.JCoException
import com.sap.conn.jco.JCoFunction
import com.sap.conn.jco.JCoParameterList
import com.sap.conn.jco.JCoTable
import com.sap.mw.jco.JCO

import com.kps.common.dao.OrderedPropertyFactory

class SapJCo3BaseDao {
  val SAP = "SAP_SERVER"
  
  protected def getJCoConnection() = {
    new Connection(OrderedPropertyFactory.getInstance)
  }

  // todo change parameters to Object
  protected def execute(functionName:String, importData:java.util.HashMap[String,Object],
              importParamName:List[Pair[String,String]], tableNameIn:String, inputDataList:List[java.util.HashMap[String,String]],
              inputParamName:List[Pair[String,String]], 
              exportName:List[Pair[String,String]], tableNameOut:String,
              outputParamName:List[Pair[String,String]]) = {    
    val resultMap = new java.util.WeakHashMap[String,Object]
    try {
      val connect = getJCoConnection()
      val function = connect.getFunction(functionName)    
      
      // Setting import parameter
      val jcoImport = function.getImportParameterList
      importParamName.foreach(name => {
        jcoImport.setValue(name._2, importData.get(name._1))
      })
      
      // Setting import TableParameter 
      var tableIn:JCoTable = null
      if (tableNameIn != null) {
        tableIn = function.getTableParameterList.getTable(tableNameIn)    
        inputDataList.foreach(data => {      
          tableIn.appendRow
          inputParamName.foreach(paramName => {
            tableIn.setValue(paramName._2, data.get(paramName._1))
          })
        })
      }
      
      connect.execute(function)
      
      // Setting export parameter
      val jcoExport = function.getExportParameterList
      val exportMap = new java.util.HashMap[String,Object]
      exportName.foreach(name => {     
        exportMap.put(name._1, jcoExport.getValue(name._2))
      })
      resultMap.put("export", exportMap)   
      
      // Setting output table parameter
      var tableOut: JCoTable = null
      if (tableNameOut != null) {
        tableOut = function.getTableParameterList.getTable(tableNameOut)
        //println(tableOut)
        //val tableAdapter = new TableAdapterReader(tableOut)
        //var resultList:List[HashMap[String,Object]] = List(new HashMap[String,Object])
        var resultList:List[HashMap[String,Object]] = null
        var list = new java.util.ArrayList[HashMap[String,Object]]
        if (tableOut.getNumRows > 0) {
          val rangeM = for (i <- 1 to tableOut.getNumRows) yield getOutputTableMap(tableOut, i, outputParamName)            
          resultList = rangeM.toList                 
          resultList.foreach(map => {
            list.add(map)
          })   
        }      
        resultMap.put(tableNameOut, list) 
      }
    } catch {
      case e:AbapException => println("Abap error")
      case e:JCoException => println("JCo error")
      case e => {
        e.printStackTrace
        throw new Exception("Business Exception")
      }
    }  
    resultMap
       
  }
  
  private def getOutputTableMap(tableOut:JCoTable, rowIndex:Int, outputParamName:List[Pair[String,String]]) = {
    val tempMap = new HashMap[String,Object]   
    tableOut.setRow(rowIndex)
    outputParamName.foreach(paramName => {
      tempMap.put(paramName._1, tableOut.getString(paramName._2))
    })
    tempMap
  }
}
