package com.kps.epda.util;

import java.net.URLDecoder;
import java.util.Arrays;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amplusc.ep.ticket.SAPTicketVerifier;


public class KpsSSOHandler {  
    
    private String mysapcookiename;    
    private String keystore_path;   
    private String keystore_password;
    
    
    /**
     * @param mysapcookiename the mysapcookiename to set
     */
    public void setMysapcookiename(String mysapcookiename){
        this.mysapcookiename = mysapcookiename;
    }

    
    /**
     * @param keystorePath the keystore_path to set
     */
    public void setKeystore_path(String keystorePath){
        keystore_path = keystorePath;
    }

    
    /**
     * @param keystorePassword the keystore_password to set
     */
    public void setKeystore_password(String keystorePassword){
        keystore_password = keystorePassword;
    }

    public String getUserIdFromEP(HttpServletRequest request, HttpServletResponse response){
        String userId = null;
        char passwd[] = null;
        SAPTicketVerifier SAPVerifier = null;
        SAPTicketVerifier.SAPTicketInfo info = null;
        String base64Value = null;
        String cookieValue = null;

        //HttpServletRequest의 쿠키정보를 사용하여 SSO를 검증한다. 
        try {
            passwd = keystore_password.toCharArray();
            SAPVerifier = SAPTicketVerifier.getInstance();
            info = null;
            base64Value = null;
            cookieValue = null;
            SAPVerifier.setCertificatesFromKeyStoreFile(keystore_path, passwd);
            Arrays.fill(passwd, ' ');
            //Get Logon Ticket from Cookie
            if (request.getCookies() == null) {               
                return null;
            } else {
                cookieValue = getSsoCookieName(request, mysapcookiename);
                //Verify Logon Ticket   
                if (!SAPTicketVerifier.isNullOrEmptyString(cookieValue)) {
                    base64Value = URLDecoder.decode(cookieValue, "UTF-8");
                    info = SAPVerifier.verifyTicket(base64Value);
                    //사용자ID 셋팅
                    if (info != null) {
                        userId = info.getUser();                     
                    }
                }
            }
        } catch (Exception e) {          
            return null;
        }    
        return userId;
    }
    
    private String getSsoCookieName(HttpServletRequest request, String cookieName){
        Cookie[] cookies = request.getCookies();
        String cookieValue = null;
        try {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().trim().equals(cookieName)) {
                    cookieValue = cookies[i].getValue();
                    break;
                } else {
                    continue;
                }
            }
        } catch (Exception e) {
            //writeLog(e);
        }

        return cookieValue;
    }
    
}
