<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title>Inställningar för programpunkter</title>
   <link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  <script language="javascript" src="basic.js"></script>
</head>
<body<c:if test="${!empty modelMap.error}"> onload="javascript:alert('${modelMap.error}');"</c:if>>
<h1>Programpunkter</h1>
 
<form name="insform" method="post" action="programpunktconfig.htm" enctype="multipart/form-data">
	<div id="menu">
    <ul>
	   <li><a href="config.htm" target="_self" title="Stäng"><span>Stäng</span></a></li>
       <li>&nbsp;&nbsp;
       	    <select name="kurstyp">				
				<c:forEach items="${modelMap.typer}" var="typ" varStatus="cnt">										
					<option value="<c:out value="${typ.tid}"/>"<c:if test="${cnt.count == 1}">selected</c:if>><c:out value="${typ.namn}"/></option>
				</c:forEach>
			</select>      
       </li>    
    </ul>
	</div><br><br><br>
  <table width="70%" bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="4">
    <tr><td align="right" valign="top" width="10%">Filnamn</td>     
    <td colspan="2"><input type="file" size="60" name="punktfil" accept=".txt">&nbsp;<a href="javascript:insform._action.value='reload';insform.submit();">Ladda programpunkter</a></td>
  </table>
  <input type="hidden" name="_action">
  <br>
</form>
</body>
</html>
