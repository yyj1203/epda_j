/*******************************************************************
 * ÇÑÀüKPS WPS
 *
 * Copyright (c) 2009 by KPS.
 * All rights reserved. 
 */
package com.kps.sap.jco;

import java.util.Properties;

import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;


/**
 * @author user
 *
 */
public class KPSDestinationDataProvider implements DestinationDataProvider{
    static String SAP_SERVER = "SAP_SERVER";
    private DestinationDataEventListener eventListener;
    private Properties ABAP_AS_properties;

    //@Override
    public Properties getDestinationProperties(String arg0) {
        return ABAP_AS_properties;
    }

    public void setDestinationDataEventListener(
            DestinationDataEventListener eventListener) {
        this.eventListener = eventListener;
    }

    //@Override
    public boolean supportsEvents() {
        return true;
    }

    public void changePropertiesForABAP_AS(Properties properties) {
        if (properties == null) {
            eventListener.deleted("SAP_SERVER");
            ABAP_AS_properties = null;
        } else {
            if (ABAP_AS_properties != null
                    && !ABAP_AS_properties.equals(properties))
                eventListener.updated("SAP_SERVER");
            ABAP_AS_properties = properties;
        }
    }
}
