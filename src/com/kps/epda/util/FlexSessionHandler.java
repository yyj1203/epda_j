package com.kps.epda.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import flex.messaging.FlexContext;

public class FlexSessionHandler {
    @Autowired
    private KpsSSOHandler kpsSSOHandler;
    private Map userSession;       
    
    /**
     * @return the remoteIP
     */
    public String getRemoteIP(){
        return FlexContext.getHttpRequest().getRemoteAddr();
    }   
    
    public void setUserSession(Map userSession) {
        FlexContext.getFlexSession().setAttribute(Constants.LOGIN_USER, userSession);
    }
    
    public String getUserIdFromEP() {
        return kpsSSOHandler.getUserIdFromEP(FlexContext.getHttpRequest(), FlexContext.getHttpResponse());
    }
    
    public Map getUserSession(){
        userSession = (Map)FlexContext.getFlexSession().getAttribute(Constants.LOGIN_USER);
        return userSession;
    }
    

}
