<%--*******************************************************************
 * Measure Result 
 *******************************************************************--%>

<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="application" value="ListMeasureResult" scope="page" />
<jsp:useBean id="now" class="java.util.Date" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>점검결과조회</title>
<style type="text/css" media="screen">
html
,
body
,
#${
application
}
_div {
		height: 100%;
}

body {
		margin: 0;
		padding: 0;
		overflow: hidden;
}
</style>

<script type="text/javascript" src="/epda/common/js/common.js"></script>
<script type="text/javascript" src="/epda/common/js/swfobject/swfobject.js"></script>
<script type="text/javascript">
	var flashvars = {
		month : "<fmt:formatDate pattern='MM' value='${now}'/>",
		date : "<fmt:formatDate pattern='yyyy-MM-dd' value='${now}'/>"

	};

	var params = {
		menu : "true"
	};
	var attributes = {
		id : "${application}",
		name : "${application}"
	};

	swfobject.embedSWF("/epda/flex/MeasureResult.swf", "${application}_div",
			"100%", "100%", "10.0.22",
			"/epda/common/js/swfobject/expressInstall.swf", flashvars, params,
			attributes);

	function viewSAPGuiTplnr(tplnr) {
		arrParam = new Array(2);
		arrParam[0] = new Array(2);
		arrParam[1] = new Array(2);

		arrParam[0][0] = "P_CON1";
		arrParam[0][1] = "P_CON99";

		arrParam[1][0] = tplnr;
		arrParam[1][1] = "X";

		fncExecuteSapGuiByParam('document.all.frmSapGui', 'ZPMR1044', arrParam);
		//fncSAPGuiCallByOneParam('document.all.frmSapGui','IW22', tplnr );
		//fncSAPGuiCallByOneParam('document.all.frmSapGui','ZPMR1044', tplnr );
	}
</script>

<SCRIPT Language="JavaScript1.2">
	var excel = null;
	function openExcel() {
		try {
			if (excel == null) {
				excel = new ActiveXObject("Excel.Application");
			}
			var workbook = excel.Workbooks.Add();
			workbook.Activate();
			var worksheet = workbook.Worksheets("Sheet1");
			worksheet.Activate();
			worksheet.Paste();
			excel.visible = true;
		} catch (exception) {
			window.alert("Now you may Paste into an Excel SpreadSheet");
		}
	}
</SCRIPT>

<style>
body {
		margin: 0px;
		overflow: hidden
}
</style>

</head>

<body scroll="no" >

<div id="${application}_div"></div>

<!-- SAPP GUI FORM 시작 -->
<form name="frmSapGui" method="post"
		action="http://epd.kps.co.kr:50000/irj/servlet/prt/portal/prtroot/com.sap.portal.appintegrator.sap.bwc.Transaction">
<input type="hidden" name="System" value="SAP_R3"> <input type="hidden" name="TCode"
		value="ZPMR9710"> <input type="hidden" name="GuiType" value="WinGui"> <input
		type="hidden" name="WinGui_Type" value="Shortcut"> <input type="hidden" name="Technique"
		value="SSD"> <input type="hidden" name="ApplicationParameter" value=""> <input
		type="hidden" name="OkCode" value="/00"></form>

</body>
</html>
