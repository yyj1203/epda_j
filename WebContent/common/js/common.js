function fncClearSelectObj(objSelect, firstOption)
{
	var objOption = document.createElement("OPTION");

	//이전 선택 목록 전체 삭제
	while(objSelect.length > 0)
	{
		objSelect.remove(objSelect.length - 1);
	}
	
	//중분류에 전체 또는 선택  항목 추가 ( 옵션이 없으면 데이터만 출력) 
	if ( firstOption != "" && firstOption.length > 0)
	{	
		objOption.value = "";
		objOption.text = firstOption;
		
		objSelect.add(objOption);
	}
}

/**
 *	XML 문서에서 정보를 추출하여 해당 select 목록을 채운다.
 */
function fncFillSelectObj(objSelect, node, valueName, tagName, firstOption)
{
	//이전 선택 목록 전제 삭제
	
	fncClearSelectObj(objSelect, firstOption);
	
	if(node == null) 
	{
		return;
	}
	
	//목록 채우기
	
	for(i=0; i<node.childNodes.length; i++)
	{
		var objOption = document.createElement("OPTION");
		var child = node.childNodes[i];

		if ( child.getElementsByTagName(tagName)[0] != null)
		{
			objOption.value = child.getElementsByTagName(valueName)[0].childNodes[0].nodeValue;
			objOption.text = child.getElementsByTagName(tagName)[0].childNodes[0].nodeValue;
			
			objSelect.add(objOption);
		}
	}
}

/**
 * 선택된 라디오 버튼의 인덱스를 구한다.
 */
function fncGetCheckdIndex_R(objRadio)
{
	if(objRadio == null || objRadio.length == null || objRadio.length == 1)
	{
		return -1;
	}

	for(var i=0; i<objRadio.length; i++)
	{
		if(objRadio[i].checked) 
		{
			return i;
		}
	}
		
	return -1;	//선택이 없는 경우
}

/**
 * 선택된 라디오 버튼의 값을 구한다.
 */
function fncGetCheckdValue_R(objRadio)
{
	if(objRadio == null)
	{
		return null;
	}
	
	if(objRadio.length == null || objRadio.length == 1)
	{
		if(objRadio.checked) return objRadio.value;
		else return null;
	}

	for(var i=0; i<objRadio.length; i++)
	{
		if(objRadio[i].checked) 
		{
			return objRadio[i].value;
		}
	}
		
	return null;	//선택이 없는 경우
}

/**
 * Select 선택항목 값 가져오기
 */
function fncGetSelectValue(selectObj) 
{
	return selectObj.options[selectObj.selectedIndex].value;
}

/**
 * Radio 선택항목 값 가져오기
 */
function fncGetRadioValue(radioObj) {
	if (radioObj.length) {
		for (var i = 0; i < radioObj.length; i++ ) {
			if ( radioObj[i].checked ) {
				return (radioObj[i].value);
			}
		}
		
		return ""; 
	} else {
		if (radioObj.checked) {
			return radioObj.value;
		} else {
			return "";
		} 
	}
}

/**
 * CheckBox 선택항목 값 가져오기
 */
function fncGetCheckBoxValue(checkBoxObj) {
	if (checkBoxObj.length) {
		for (var i = 0; i < checkBoxObj.length; i++ ) {
			if ( checkBoxObj[i].checked ) {
				return (checkBoxObj[i].value);
			}
		}
		return "";
	} else {
		if (checkBoxObj.checked) {
			return checkBoxObj.value;
		} else {
			return "";
		}
	}
}

/**
 * CheckBox 전체 선택 또는 해제
 * choice: 1 선택 , 아니면 해제
 */
function fncSetCheckBoxChoice(checkBoxObj, choice) {
	if (checkBoxObj.length) {
		if ( choice == "1")	{
			for (var i = 0; i < checkBoxObj.length; i++ ) {
				checkBoxObj[i].checked=true;
			}		
		}
		else {
			for (var i = 0; i < checkBoxObj.length; i++ ) {
				checkBoxObj[i].checked=false;
			}		
		}		
	} else {
		if ( choice == "1")	{
			checkBoxObj.checked = true;
		}
		else {
			checkBoxObj.checked = false;
		}
	}
}

/**
 * Excel 파일을 저장할 폴더 선택 화면 호출
 */
function fncExcelForm()
{
	launchCenter('/epm/requestExcelForm.action', '정비포탈', '150', '600');
}

/**
 * 팝업을 띠운다.
 */
function launchCenter(url, name, height, width) 
{
	var str = "height=" + height + ", innerHeight=" + height;

	str += ", width=" + width + ", innerWidth=" + width;
	
	if (window.screen) 
	{
		var ah = screen.availHeight - 30;
		var aw = screen.availWidth - 10;
		var xc = (aw - width) / 2;
		var yc = (ah - height) / 2;

		str += ", left=" + xc + ", screenX=" + xc;
		str += ", top=" + yc + ", screenY=" + yc;
		str += "toolbar=no,menubar=no,titlebar=no, status=no";
	}

	return window.open(url, name, str);
}

/**
 * 팝업을 띠운다.
 */
function launchCenterByOption(url, name, height, width, option) 
{
	var str = "height=" + height + ", innerHeight=" + height;

	str += ", width=" + width + ", innerWidth=" + width;
	
	if (window.screen) 
	{
		var ah = screen.availHeight - 30;
		var aw = screen.availWidth - 10;
		var xc = (aw - width) / 2;
		var yc = (ah - height) / 2;

		str += ", left=" + xc + ", screenX=" + xc;
		str += ", top=" + yc + ", screenY=" + yc;
		str += option;
	}

	return window.open(url, name, str);
}

//SAP GUI를 실행시킨다.

function fncExecuteSapGui()
{
	var args = null;			//함수 호출시 전달된 파라메터
	var params = null;
	var formObj = null;

	args = fncExecuteSapGui.arguments;
	formObj = eval(args[0]);
	
	if(args.length == 2)
	{
		params = "P_TCODE=" + args[1];
	}
	else if(args.length == 3)
	{
		params = "P_TCODE=" + args[1] + "&P_CON1=" + args[2];
	}
	else if(args.length == 4)
	{
		params = "P_TCODE=" + args[1] + "&P_CON1=" + args[2] + "&P_CON2=" + args[3];
	}	
	else if(args.length == 5)
	{
		params = "P_TCODE=" + args[1] + "&P_CON1=" + args[2] + "&P_CON2=" + args[3]
			   + "&P_CON3=" + args[4];
	}	
	else if(args.length == 6)
	{
		params = "P_TCODE=" + args[1] + "&P_CON1=" + args[2] + "&P_CON2=" + args[3]
			   + "&P_CON3=" + args[4] + "&P_CON4=" + args[5];
	}	
	else if(args.length == 7)
	{
		params = "P_TCODE=" + args[1] + "&P_CON1=" + args[2] + "&P_CON2=" + args[3]
			   + "&P_CON3=" + args[4] + "&P_CON4=" + args[5] + "&P_CON5=" + args[6];
	}	
	else
	{
		return;
	}
	
	//SAP GUI 실행 후 엔터 누름 효과 적용
	
	params += ("&P_CON99=X");
	
	formObj.target = "about:blank";
	formObj.ApplicationParameter.value = params;
	formObj.submit();
}

/**
* SAP GUI 호출 Tcode와 Value가 하나일 경우
*/
function fncSAPGuiCallByOneParam(formObj, tcode, p_value)
{
	var arrParam = null;
	arrParam = new Array(2);
	arrParam[0] = new Array(2);
	arrParam[1] = new Array(2);
	
	arrParam[0][0]="P_CON1";
	arrParam[0][1]="P_CON99";
	
	arrParam[1][0]=p_value;
	arrParam[1][1]="X";
	
	fncExecuteSapGuiByParam(formObj,tcode,arrParam);
}
  		
/**
* 어플리케이션 파라미터가 있을 경우 호출
* 파라미터 정보 : form객체, TCode, arrParam 순으로 전달
* arrParam의 내부 파라미터명칭은 "P_CON1~P_CON99"를 사용
* 파라미터갯수가 2일 경우 : 해당 TCode 화면을 바로 수행 ==> "P_CON99=X"로 설정  
* 파라미터갯수가 3일 경우 : 파라미터 처리 대상으로 판단.
*/
function fncExecuteSapGuiByParam()
{
	var args = null;
	var params = null;
	var arrParam = null;
	
	args = fncExecuteSapGuiByParam.arguments;

	formObj = eval(args[0]);
	params = "P_TCODE=" + args[1];
		
	if(args.length == 2)
	{
		params = params + "&P_CON99=X"
	}
	else if(args.length == 3)
	{
		arrParam = args[2];
		
		for( i=0; i < arrParam[0].length; i++)
		{
			params=params+"&" + arrParam[0][i] + "=" + arrParam[1][i];
		}			
	}
	else
	{
		alert("전달된 파라미터 갯수가 맞지 않습니다.");
		return;
	}

	//새창먼저 호출
	launchCenter("http://${header.Host}:9035/epm/common/blank.htm", "SAPGUI", 700, 900);
	
	formObj.target = "SAPGUI";
	formObj.ApplicationParameter.value = params;
	formObj.submit();			
	
	

}
/****************************************************************************
 *************************** 문자열 조작 관련 ***********************************
 ****************************************************************************/

/**
* UTF-8로 변환
* CV03N에서 문서번호가 한글로 되어 있어 추가
* SAP에 "%"를 제외한 문자열로 전달해야 정상적으로 처리됨.
*/
function toUTF8(szInput)
{
	var wch,x,uch="",szRet="";
	for (x=0; x<szInput.length; x++)
	{
		wch=szInput.charCodeAt(x);
		if (!(wch & 0xFF80)) 
		{
			szRet += "%" + wch.toString(16);
		}
		else if (!(wch & 0xF000)) 
		{
			uch = "%" + (wch>>6 | 0xC0).toString(16) + "%" + (wch & 0x3F | 0x80).toString(16);
			szRet += uch;
		}
		else 
		{
			uch = "%" + (wch >> 12 | 0xE0).toString(16) +
			"%" + (((wch >> 6) & 0x3F) | 0x80).toString(16) +
			"%" + (wch & 0x3F | 0x80).toString(16);
			szRet += uch;
		}
	}

	return(szRet);
}		

/**
* 문자열 대치
* examples : var str = replace("2006-01-01", "-", "");
*/
function replace(str,s,d)
{
	var i=0;

 	while(i > -1)
 	{
  		i = str.indexOf(s);
  		str = str.substr(0,i) + d + str.substr(i+1,str.length);
 	}
 	
 	return str;
}
/**
 * 문자열의 앞 뒤 공백을 제거한다.
 * str : 공백을 제거할 대상 문자열
 * rex : 제거할 문자에 대한 정규식, 정규식이 없으면 기본 화이트 스페이스 문자를 제거하고 
 *       있으면 정규식에 해당되는 문자를 제거한다.        
 */
function trim(str, rex)
{
	var args = trim.arguments;
	
	if(args.length == 1)
	{
		return str.replace(/(^\s+)|(\s+$)/, "")
	}
	else if(args.length == 2)
	{
		return str.replace(rex, "");
	}

	return str;
}

/****************************************************************************
 *************************** 시스템         관련 ***********************************
 ****************************************************************************/

/**
 * 클라이언트(로컬) PC에 지정한 경로에 파일이 존재하는지 체크한다.
 * 존재하면 참, 그렇지 않으면 거짓을 반환한다. 
 */
function fncExistFile(fileName)
{
	var fso = new ActiveXObject("Scripting.FileSystemObject");

	if(fso.FileExists(fileName)) 
	{
	return true;
	}
	else
	{
		return false;
	}
}

/**
 * 로컬 컴퓨터에 설치된 프로그램을 실행시킨다.
 * fileName : 실행할 프로그램 : 전체 경로 또는 path가 설정된 경우 실행파일명
 */
function fncExecute(fileName)
{
	var WshShell = new ActiveXObject("WScript.Shell"); 
	WshShell.Run(fileName); 
} 

/**
 * XMLHttpRequest 초기화
 */
function initRequest(url) 
{
	if (window.XMLHttpRequest) 
	{
		return new XMLHttpRequest();
	} 
	else if (window.ActiveXObject) 
	{
		isIE = true;
		return new ActiveXObject("Microsoft.XMLHTTP");
	}
}

String.prototype.dbLength = function() {
    var result = 0;
    for (var i = 0; i < this.length; i++) {
        var c = this.charAt(i);
        var enc = encodeURIComponent(c);
        result++;
        if (enc.length > 3) result++;
    }
    return result;
}