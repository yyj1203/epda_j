<%@ page import = "java.net.URLEncoder" %>
<%@ page import = "com.amplusc.ep.ticket.*"%>

<%
	String cookieval = request.getParameter("MYSAPSSO2");	
	String server = request.getServerName();	
	int dotPos = server.indexOf(".");
	if (cookieval != null && !cookieval.equals("")) {
		Cookie cookie = new Cookie ("MYSAPSSO2", SAPTicketVerifier.PartEncoder(URLEncoder.encode(cookieval)));
		if (dotPos != -1) {
			// domain 3자리 이상인경우 
			cookie.setDomain(server.substring(dotPos+1));

			// domain 2자리, ip일 경우
			//cookie.setDomain(server);
		}
		cookie.setPath("/");
		response.addCookie(cookie);
		
		
	}
	
	else {
		response.setStatus(403);
	}
%>

