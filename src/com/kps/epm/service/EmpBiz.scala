package com.kps.epm.service

import com.kps.epm.dao.EmpDao;
import com.kps.epm.util._;
class EmpBiz {
  val sqlMapClient = EpmDevDBHelper.getSqlMapClient	
  val emp = new EmpDao
  def getEmpList = emp.getEmpList(sqlMapClient)   
  def getEmpListAsXML = {
    //val ops = new EmpOps
    //ops.list
    emp.getEmpListAsXML(sqlMapClient)
  }
}
