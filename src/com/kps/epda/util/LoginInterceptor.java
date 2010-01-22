package com.kps.epda.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amplusc.ep.ticket.SAPTicketVerifier;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class LoginInterceptor implements Interceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String MYSAPCOOKIENAME = "MYSAPSSO2";

    private String KEYSTORE_PATH = "C:/webAS/sso/portal.store";

    private String KEYSTORE_PASSWORD = "kps-ep";
    
    public void init(){}

    public void destroy(){}

    /**
     * 인터셉터 메인
     */
    public String intercept(ActionInvocation invocation) throws Exception{
        String userID = null;
        String resultPage = null;
        Map session = null;
        UserSession user = null;
        String invokeStr = null;

        logger.error("##################start EP");

        HttpServletRequest request = (HttpServletRequest)invocation.getInvocationContext().get(
                ServletActionContext.HTTP_REQUEST);
        HttpServletResponse response = (HttpServletResponse)invocation.getInvocationContext().get(
                ServletActionContext.HTTP_RESPONSE);
        //userID = getUserIdFromEP(request, response);

        //System.out.println("userID: " + userID);

        invokeStr = invocation.invoke();

        return invokeStr;
    }

    /**
     * SSO 티켓에서 사용자 정보를 추출한다.
     * 
     * @param request
     * @param response
     * @return
     */
    private String getUserIdFromEP(HttpServletRequest request, HttpServletResponse response){
        String userId = null;
        char passwd[] = null;
        SAPTicketVerifier SAPVerifier = null;
        SAPTicketVerifier.SAPTicketInfo info = null;
        String base64Value = null;
        String cookieValue = null;

        //try {
        //HttpServletRequest의 쿠키정보를 사용하여 SSO를 검증한다. 
        try {
            passwd = KEYSTORE_PASSWORD.toCharArray();
            SAPVerifier = SAPTicketVerifier.getInstance();
            info = null;
            base64Value = null;
            cookieValue = null;
            SAPVerifier.setCertificatesFromKeyStoreFile(KEYSTORE_PATH, passwd);
            Arrays.fill(passwd, ' ');
            String mysapsso2 = request.getParameter("MYSAPSSO2");
            //Get Logon Ticket from Cookie
            if (request.getCookies() == null) {
                if (mysapsso2.length() > 0) {
                    String cookieval = request.getParameter("MYSAPSSO2");
                    String server = request.getServerName();
                    int dotPos = server.indexOf(".");
                    if (cookieval != null && !cookieval.equals("")) {
                        Cookie cookie = new Cookie("MYSAPSSO2", SAPTicketVerifier.PartEncoder(URLEncoder.encode(
                                cookieval, "UTF-8")));
                        if (dotPos != -1) {
                            cookie.setDomain(server.substring(dotPos + 1));
                        }
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                } else {
                    return null;
                }
            }
            
            String ssoCookie = getSsoCookieName(request, MYSAPCOOKIENAME);
            System.out.println("ssocookie value: " + ssoCookie);
            cookieValue = (ssoCookie == null) ? request.getParameter("MYSAPSSO2") : request.getParameter("MYSAPSSO2");
            System.out.println("cookie value: " + MYSAPCOOKIENAME + "-->" + cookieValue);
            //Verify Logon Ticket	
            if (!SAPTicketVerifier.isNullOrEmptyString(cookieValue)) {
                base64Value = URLDecoder.decode(cookieValue, "UTF-8");
                info = SAPVerifier.verifyTicket(base64Value);

                //사용자ID 셋팅
                if (info != null) {
                    userId = info.getUser();
                    logger.debug("### SAP User Check ############################");
                    logger.debug("User_ID is " + info.getUser());
                    logger.debug("System is " + info.System());
                    logger.debug("Client is " + info.Client());
                    logger.debug("Expiration is " + info.Expiration());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        //} catch (Exception ex) {
        //    writeLog(ex);
        //    return null;
        //}

        return userId;
    }

    /**
     * HttpServletRequest에서 SSO 쿠키 이름을 추출하여 반환한다.
     * 
     * @param request
     * @return
     */
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

        }

        return cookieValue;
    }

}
