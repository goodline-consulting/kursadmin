<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title>AnvändarInfo</title>
   <link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  <script language="javascript" src="basic.js"></script>
</head>
<body>
<h1>AnvändarInfo</h1>
 
<form:form name="lokalForm" method="post" commandName="anvandare">
	<div id="menu">
    <ul>
       <li><a href="javascript:history.go(-1)" target="_self" title="avbryt"><span>Avbryt</span></a></li>
       <li><a href="javascript:lokalForm.submit();" target="_self" title="spara"><span>Spara</span></a></li>
       <li><a href="javascript:lokalForm.reset();" target="_self" title="reset"><span>Återställ</span></a></li>
    </ul>
	</div><br><br><br>
  <table width="95%" bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
    <tr>
      <td align="right" width="10%">Användarnamn:</td>
        <td width="20%">
          <c:if test="${param.username != '0'}">
          <form:input size="30" path="namn" disabled="true"/>
           </c:if> 
           <c:if test="${param.username == '0'}">
          <form:input size="30" path="namn" />
           </c:if> 
        </td>
        <td width="60%">
          <form:errors path="namn" cssClass="error"/>
        </td>       
    </tr>
    <tr>
      <td align="right" width="10%">Passord:</td>
        <td width="20%">
          <form:input size="30" path="passord"/>
        </td>
        <td width="60%">
          <form:errors path="passord" cssClass="error"/>
        </td>       
    </tr>
    <tr>
      <td align="right" width="10%">Roll:</td>
        <td width="20%">
	        <form:select path="roll">
				<form:option value="ROLE_PUBLIC" label="PUBLIC"/>
			    <form:option value="ROLE_USER" label="USER"/>
			    <form:option value="ROLE_MANAGER" label="MANAGER"/>      
				<form:option value="ROLE_ADMIN" label="ADMIN"/>	
			</form:select>          
        </td>               
    </tr>
  </table>
  <br>
</form:form>
</body>
</html>
