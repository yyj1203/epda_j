package com.kps.epm.util

import com.ibatis.sqlmap.client.SqlMapClient

object SqlClientManager {
	def queryForList(sqlMapClient:SqlMapClient, sqlCommand:String, param:java.util.HashMap[String, Object]) = {
	  sqlMapClient.queryForList(sqlCommand, param)   
	} 
}

/*
object InTransaction {

    def txn[T](block: => T): T = {
        val entityMan = getEntityManager()  // Obtain the JPA Entity Manager
                                            // The code is not shown.

        EmThreadLocal.set(entityMan)        // Set the entity manager in
                                            // a threadlocal variable so that 
                                            // it is available to the code
                                            // performed under the transaction.
        val txn = entityMan.getTransaction()
        try {
            txn.begin()
            val res = block                 // Execute the block passed in as 
            txn.commit()                    // a parameter. 
            res                             // Return the results
        }
        finally {
            if (entityMan.getTransaction().isActive())
                entityMan.getTransaction().rollback()
            if (entityMan.isOpen())
                entityMan.close()
            EmThreadLocal.remove()          // Remove the entity manager from 
                                            // the threadlocal.
        }	
     }
}
*/