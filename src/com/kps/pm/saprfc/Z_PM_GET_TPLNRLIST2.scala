package com.kps.pm.saprfc

import com.kps.sap.jco.SapJCo3BaseDao

object Z_PM_GET_TPLNRLIST {
  val tableOutName = "T_OUTPUT"  
}

class Z_PM_GET_TPLNRLIST2 extends SapJCo3BaseDao {
  
  val functionName = "Z_PM_GET_TPLNRLIST"
  val tableOutName = "T_OUTPUT"
  val importParamName = List(
                    ( "I_GUBUN", "I_GUBUN" ),
                    ( "I_IWERK", "I_IWERK" ),
                    ( "I_SWERK", "I_SWERK" ),
                    ( "I_BEBER", "I_BEBER" ),
                    ( "I_FLTYP", "I_FLTYP" ),
                    ( "I_EQART", "I_EQART" ),
                    ( "I_TPLNR", "I_TPLNR" )
                    )
  val resultName = List(
                    ( "TPLNR", "TPLNR" ),
                    ( "PLTXT", "PLTXT" ),
                    ( "BEBER", "BEBER" )
                    )
  val exportParamName = List(( "E_SUBRC", "E_SUBRC" ) 
                    )
  
  def execute(importParam:java.util.HashMap[String,Object]) = {
    super.execute(functionName, importParam, importParamName, null, null, null,
                exportParamName, tableOutName, resultName)   
                
  } 
    
}
