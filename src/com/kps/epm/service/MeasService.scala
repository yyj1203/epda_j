package com.kps.epm.service

import com.kps.epm.dao.{MeasRealDao,MeasDevDao}

class MeasService {
  
    
  val meas = new MeasRealDao
  
  def getCsgrpList = {
    val list = meas.getCsgrpList(null)    
    println(list.size)
    val measDev = new MeasDevDao    
    measDev.insertCsgrpList(list)
    "<csgrp/>"
  }
  
  def getCgitemList = {
    val list = meas.getCgitemList(null)    
    println(list.size)
    val measDev = new MeasDevDao    
    measDev.insertCgitemList(list)
    "<cgitem/>"
  }
  
  def getMeasList = {
    val list = meas.getMeasList(null)    
    println(list.size)
    val measDev = new MeasDevDao    
    measDev.insertList(list)
    "<root/>"
  }
  
  def getColumnListAsXML = {
    var param = new java.util.HashMap[String, Object]()
    param.put("table_name","TB_MEAS")
    meas.getColumnListAsXML(param)
  }
  
  def getCodegen = {
    val insert = "INSERT TNTO (\r\n"
    var buffer = new StringBuffer
    buffer.append(insert)
    var param = new java.util.HashMap[String, Object]()
    param.put("table_name","TB_MEAS")
    val list = meas.getColumnScalaList(param)
    //val xml = list.map(s => "\t" +  s+",\r\n").mkString
    val columns = list.map(s => "\t" + s).mkString(",\r\n")
    buffer.append(columns + "\r\n) VALUES (\r\n")
    //val values = list.map(s => "#" + s.toLowerCase + "#").mkString(",")
    
    //buffer.append(values + ")\r\n")
   
    val insertxml = <insert id="insert" parameterClass="map">
      {buffer.toString}
</insert>    
    //select(list) + insertxml.toString + "\r\n" +resultMap(list)   
    null
  }
  
  private def select(list:List[String]) = {
    var buffer = new StringBuffer
    buffer.append("SELECT\r\n")
    val columns = list.map(s => "\t" + s).mkString(",\r\n")
    buffer.append(columns + "\r\n    FROM TB_MEAS\r\n")
    val selectxml = <select id="select">
    {buffer.toString}
</select>
    selectxml.toString + "\r\n\r\n"
  }
    
  private def resultMap(list:List[String]) = {
    val p = list.map(s => <result property={s} column={s} />)
	var result =   <resultMap class="java.util.HashMap" id="joinMap">
	  {p}	
	</resultMap>   
    result.toString 
  }
  
}
