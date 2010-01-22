/*******************************************************************
 * ÇÑÀüKPS WPS
 *
 * Copyright (c) 2009 by KPS.
 * All rights reserved. 
 */
package com.kps.sap.jco;

import java.util.Iterator;

import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoTable;

public class TableAdapterReader {
    protected JCoTable table;

    public TableAdapterReader(JCoTable table) {
        this.table = table;
    }

    public String get(String s) {
        return table.getValue(s).toString();
    }

    public Boolean getBoolean(String s) {
        String value = table.getValue(s).toString();
        return value.equals("X");
    }

    public String getMessage() {
        return table.getString("MESSAGE");
    }

    public int size() {
        return table.getNumRows();
    }

    public void next() {
        table.nextRow();       
    }
    
    public Iterator<JCoField> iterator() {
        return table.iterator();
    }    
    
    
}
