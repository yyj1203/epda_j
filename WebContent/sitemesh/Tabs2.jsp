<?xml version="1.0" encoding="EUC-KR" ?>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<% String root = request.getContextPath(); %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR" />
<title>�ڰ�����</title>
<style type="text/css">
/************��� �� ul************/
#tabs{
	float: left;
	width: 100%;
	margin-top: 10px;
	margin-bottom: 10px;
	padding-left: 10px;
	padding-top: 5px;
	background: #EEE;
	border: 1px solid #236597;
	list-style-type: none;
	display: inline;
}


/**��� �� li*/
#tabs li{
	float: left;
	margin-right: 3px;
	list-style-type: none;
	display: inline;
}

/**����� span*/
#tabs li span{
	float: left;
	padding: 7px;
	font-size: 12px;
	font-weight: 400;
	color: #333;
	background: #E7EFF7;
	border: 1px solid #7BB6C6;
	border-bottom: none;
	cursor: hand;
}

/**�����  ���缱�õ� span*/
#tabs li span.checked{
	background: #FFF;
	border: 1px solid #236597;
	border-bottom: none;
	position: relative;
	top: 1px;
	font-weight: 800;
	color: #333;
}

/**�����  ���þȵ� span:hover*/
#tabs li span.unchecked:hover{
	background: #7BB6C6;
}

</style>
<script type="text/javascript"><!--
//�� ��ũ
function selectTab(obj){
	var tabCate = obj.getAttribute("tabCate");
	var tabName = obj.getAttribute("tabName");
	var tabLink = obj.getAttribute("tabLink");

	var url = tabLink;
	url += "?tabCate="+tabCate;
	url += "&tabName="+tabName;
	
	location.href = url;
}

--></script>

</head>
<body>	
	<ul id="tabs">
	<c:forEach items="${requestScope.tabs}" var="tabMap">
		<li><span onclick="javascript:selectTab(this);" tabCate="${tabMap.tabCate }" tabName="${tabMap.tabName }" 
			tabLink="${tabMap.tabLink }" class="${tabMap.checked}">${tabMap.tabName}</span></li>
	</c:forEach>
	</ul>
	
		<!-- 
	<ul id="tabs">
		<li><a class="checked" href="listNsgain.action"><span>�ڰ�����</span></a></li>
		<li><a class="unchecked" href="listNsgainMaintenance.action"><span>���������</span></a></li>
	</ul>
	 -->

</body>
</html>