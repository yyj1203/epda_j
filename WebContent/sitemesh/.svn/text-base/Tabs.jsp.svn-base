<?xml version="1.0" encoding="EUC-KR" ?>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<% String root = request.getContextPath(); %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR" />
<title>자격종합</title>
<link type="text/css" rel="stylesheet" href="<%=root %>/sitemesh/tab1.css" />
<!-- <link type="text/css" rel="stylesheet" href="<%=root %>/sitemesh/tab2.css" />  -->
<!-- <link type="text/css" rel="stylesheet" href="<%=root %>/sitemesh/tab3.css" />  -->
<!-- <link type="text/css" rel="stylesheet" href="<%=root %>/sitemesh/tab4.css" />  -->

<style type="text/css"><!--

--></style>

<script type="text/javascript"><!--
//탭 링크
function selectTab(tabCate, tabName, tabLink){

	//var tabCate = obj.getAttribute("tabCate");
	//var tabName = obj.getAttribute("tabName");
	//var tabLink = obj.getAttribute("tabLink");

	var url = tabLink;
	url += "?tabCate="+tabCate;
	url += "&tabName="+tabName;
	
	location.href = url;
}

--></script>

</head>

<body>
	<div id="navcontainer">
		<ul id="navlist">
		<c:forEach items="${tabs}" var="tabMap">
			<li class="${tabMap.active }">
				<a onclick="javascript:selectTab('${tabMap.tabCate}','${tabMap.tabName}','${tabMap.tabLink}' );">
				<span>${tabMap.tabName}</span></a></li>
		</c:forEach>
		</ul>
		<span>sID: ${sessionScope.sID }</span>
	</div>
</body>
</html>