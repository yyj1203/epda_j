package com.kps.epda.service

import com.kps.epda.dao.MeasDao

class MeasService {

  var measDao: MeasDao = _
  def setMeasDao(measDao: MeasDao) = this.measDao = measDao
  
  def getMeasList_paged(param:java.util.Map[String,Object]) = {      
    measDao.getMeasList_paged(param)    
  }
  
  def getCount(param:java.util.Map[String,Object]) = {    
    //println(param + " :  "+  measDao.getMeasList_count(param)  )
    measDao.getMeasList_count(param) 
  }
  
  def getMeasTrend(param:java.util.Map[String,Object]) = {
    println(param)
    println(measDao.getMeasTrend(param))
    measDao.getMeasTrend(param)
  }
}
