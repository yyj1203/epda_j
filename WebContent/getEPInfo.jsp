
<%@ page import = "java.io.IOException"%>
<%@ page import = "java.io.*"%>
<%@ page import = "java.net.URLEncoder" %>
<%@ page import = "java.net.URLDecoder"%>
<%@ page import = "java.util.Arrays"%>
<%@ page import = "com.amplusc.ep.ticket.*"%>

<%
    
%>




    <form name="epinfo" action="sendSSO2Cookie.jsp" method="post">
    	<input type="hidden" name="MYSAPSSO2" value="<%=request.getParameter("MYSAPSSO2")%>">
    	
    </form>
<%
	//Declare & Initialize Variables
	String	MYSAPCOOKIENAME 				= "MYSAPSSO2";
	String	KEYSTORE_PATH 					= "/webAS/sso/portal.store";
	String	KEYSTORE_PASSWORD 				= "kps-ep";
	boolean  trysetCookie                   = (request.getParameter("MYSAPSSO2")!=null && (request.getParameter("MYSAPSSO2")).equals("true") )?true:false;	
	
	
		
	String	RequestPage						= null;
	
	char passwd[] 							= KEYSTORE_PASSWORD.toCharArray();
	SAPTicketVerifier SAPVerifier			= SAPTicketVerifier.getInstance();
	SAPTicketVerifier.SAPTicketInfo	info	= null;

	Cookie SAPCookie 						= null;
	Cookie cookies[] 						= request.getCookies();
	String base64Value 						= null;
	String cookieValue 						= null;
	Cookie setcookie 						= null;
	String EPID								= "";

	
	

	String MYSAPSSO2 = (request.getParameter("MYSAPSSO2")!=null)?request.getParameter("MYSAPSSO2"):"";	
	boolean havesapcookie = false;
	String cookieval = request.getParameter("MYSAPSSO2");	
	String server = request.getServerName();	
	int dotPos = server.indexOf(".");
	if (havesapcookie == false) {
		Cookie cookie = new Cookie("MYSAPSSO2", SAPTicketVerifier.PartEncoder(URLEncoder.encode(cookieval, "UTF-8")));
		if (dotPos != -1) {
			// domain 3자리 이상인경우 
			cookie.setDomain(server.substring(dotPos+1));

			// domain 2자리, ip일 경우
			//cookie.setDomain(server);
		}
		cookie.setPath("/");
		response.addCookie(cookie);
    }
	for(int i = 0;  cookies !=null && i < cookies.length; i++) {
		Cookie cook	= cookies[i];
		out.println(cook.getName() + ", " + cook.getValue());
		if(cook.getName().trim().equals(MYSAPCOOKIENAME))
		{
			havesapcookie = true;		
			cookieValue = cook.getValue();
			
			//cook.setMaxAge(0);
			break;
		}		
	}
	
		havesapcookie = true;
		if(havesapcookie) {
		    
		    //Initialize TicketVerifier	
        	SAPVerifier.setCertificatesFromKeyStoreFile(KEYSTORE_PATH, passwd);
        	Arrays.fill(passwd, ' ');
        	RequestPage		=	request.getParameter("page");
				
			//cookieValue = cookieval;
			//Verify Logon Ticket	
			if(!SAPTicketVerifier.isNullOrEmptyString(cookieValue)){
				try {
					base64Value	= URLDecoder.decode(cookieValue, "UTF-8");
					info = SAPVerifier.verifyTicket(base64Value);
					EPID = info.getUser();
					
				} catch(UnsupportedEncodingException useex) {
					out.println("UTF-8 encoding not supported");
				}
			}
		}		
		else 
		//Get Logon Ticket from Cookie
		if(!MYSAPSSO2.equals("") && !havesapcookie && !trysetCookie) {
		// 쿠키에 값이 없으므로 쿠키에 MYSAPSSO2을 셋팅해주는 page로 이동
		%>
			<script language="javascript">
				epinfo.submit();
			</script>
		<%
		}			
%>
