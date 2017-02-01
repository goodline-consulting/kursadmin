<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title>Programpunkt</title>
   <link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  <script language="javascript" src="basic.js"></script>
</head>
<body>
<h1>Programpunkt</h1>
 
<form:form name="progform" method="post" commandName="programPunkt">
	<div id="menu">
    <ul>
       <li><a href="javascript:history.go(-1)" target="_self" title="avbryt"><span>Avbryt</span></a></li>
       <li><a href="javascript:progform.submit();" target="_self" title="spara"><span>Spara</span></a></li>
       <li><a href="javascript:progform.reset();" target="_self" title="reset"><span>Återställ</span></a></li>
    </ul>
	</div><br><br><br>
  <table width="95%" bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
   <tr>    
        <td align="right" width="10%">KursTyp:</td>
        <td width="20%">
        <form:select path="tid" onchange="javascript:doChange(this.value);">				
				<form:option value="0" label="Alla"/>				          
				<form:options items="${typer}" itemValue="tid" itemLabel="namn"/>
		 </form:select>
		 &nbsp;Aktuell <form:checkbox  path="aktuell"/>
		 </td>
        <td width="60%">
          
        </td>       
    </tr>
    <tr>    
      <td align="right" width="10%">Programpunkt:</td>
        <td width="20%">
          <form:input size="60" path="namn"/>
        </td>
        <td width="60%">
          <form:errors path="namn" cssClass="error"/>
        </td>       
    </tr>
    <tr>
      <td align="right" width="10%">Rubrik:</td>
        <td width="20%">
          <form:input size="60" path="header"/>
        </td>
        <td width="60%">
          <form:errors path="header" cssClass="error"/>
        </td>       
    </tr>
     <tr>
      <td align="right" width="10%">Url:</td>
        <td width="20%">
          <form:input size="60" path="url"/>
        </td>
        <td width="60%">
          <form:errors path="url" cssClass="error"/>
        </td>       
    </tr>    
     <tr>
      <td align="right" width="10%">VideoUrl:</td>
        <td width="20%">
          <form:input size="60" path="url2"/>
        </td>
        <td width="60%">
          <form:errors path="url2" cssClass="error"/>
        </td>       
    </tr>    
    <tr>
      <td align="right" valign="top" width="10%">Info:</td>
        <td width="20%">
          <form:textarea rows="5" cols="60" path="info"/>
        </td>
        <td width="60%">
          <form:errors path="info" cssClass="error"/>
        </td>       
    </tr>    
  </table>
  <br>
</form:form>
</body>
</html>
