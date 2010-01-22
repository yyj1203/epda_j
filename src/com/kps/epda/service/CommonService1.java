package com.kps.epda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.stereotype.Service;

import com.kps.epda.util.FlexSessionHandler;

import flex.messaging.FlexContext;

@Service("commonService1")
@RemotingDestination(channels={"my-amf"})
public class CommonService1 {
    @Autowired
    private FlexSessionHandler flexSessionHandler;
      
    public String getUserSession() throws Exception {        
        String userid = "";
        userid = flexSessionHandler.getUserIdFromEP();
        System.out.println(flexSessionHandler.getRemoteIP());
        System.out.println(FlexContext.getHttpRequest().getRemoteAddr());
        if (userid == null || userid.length() == 0) {
            if (flexSessionHandler.getRemoteIP().equals("172.16.77.157")) {
                userid = "1932452";
            }
        }
        return userid;
    }
}
