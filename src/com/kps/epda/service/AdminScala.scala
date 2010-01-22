package com.kps.epda.service

import com.kps.epda.dao.EmpDao

class AdminScala  {      
  var empDao: EmpDao = _ 
  def setEmpDao(empDao: EmpDao) = this.empDao = empDao
  
  def getEmpList_paged(param:java.util.Map[String,Object]) = {  
    empDao.getEmpList_paged(param)
    
  }
  
  def count(param:java.util.Map[String,Object]) = empDao.getEmpList_count(param) 
}
