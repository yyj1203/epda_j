<?xml version="1.0" encoding="EUC-KR" ?>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
   
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR" />
<title><decorator:title default="사내자격" /></title>
<decorator:head />
<style type="text/css">
div#layoutTabs{
	float: left;
	width: 100%;
}
div#layoutBody{
	float: left;
	width: 100%;
}
</style>
</head>
<body>
	<div id="layoutTabs">
		<page:applyDecorator page="/sitemesh/Tabs.jsp" name="tabs" ></page:applyDecorator>
	</div>

	<div id="layoutBody">
		<decorator:body />
	</div>
	
</body>
</html>