<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title>LokalInfo</title>
   <link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  <script language="javascript" src="basic.js"></script>
</head>
<body>
<h1>LokalInfo</h1>
 
<form:form name="lokalForm" method="post" commandName="lokal">
	<div id="menu">
    <ul>
       <li><a href="javascript:history.go(-1)" target="_self" title="avbryt"><span>Avbryt</span></a></li>
       <li><a href="javascript:lokalForm.submit();" target="_self" title="spara"><span>Spara</span></a></li>
       <li><a href="javascript:lokalForm.reset();" target="_self" title="reset"><span>Återställ</span></a></li>
    </ul>
	</div><br><br><br>
  <table width="95%" bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
    <tr>
      <td align="right" width="10%">Lokalnamn:</td>
        <td width="20%">
          <form:input size="60" path="lokalnamn"/>
        </td>
        <td width="60%">
          <form:errors path="lokalnamn" cssClass="error"/>
        </td>       
    </tr>
    <tr>
      <td align="right" width="10%">Kontaktperson:</td>
        <td width="20%">
          <form:input size="60" path="kontakt"/>
        </td>
        <td width="60%">
          <form:errors path="kontakt" cssClass="error"/>
        </td>       
    </tr>
     <tr>
      <td align="right" width="10%">Timpris:</td>
        <td width="20%">
          <form:input size="50" path="timpris"/>
        </td>
        <td width="60%">
          <form:errors path="timpris" cssClass="error"/>
        </td>       
    </tr>
    <tr>
      <td align="right" width="10%">Adress:</td>
        <td width="30%">
          <form:input size="50" path="adress"/>
        </td>
               
    </tr>    
    <tr>
      <td align="right" width="10%">Postnr:</td>
        <td width="20%">
          <form:input size="6" path="postnr"/>
        </td>
        <td width="60%">
          <form:errors path="postnr" cssClass="error"/>
        </td>       
    </tr>
     <tr>
      <td align="right" width="10%">Postadress:</td>
        <td width="30%">
          <form:input  path="postadress"/>
        </td>
               
    </tr>
    <tr>
      <td align="right" width="10%">Telefon:</td>
        <td width="20%">
          <form:input path="telefon"/>
        </td>
        <td width="60%">
          <form:errors path="telefon" cssClass="error"/>
        </td>       
    </tr>
    <tr>
      <td align="right" width="10%">Mobil:</td>
        <td width="20%">
          <form:input path="mobil"/>
        </td>
        <td width="60%">
          <form:errors path="mobil" cssClass="error"/>
        </td>       
    </tr>
    <tr>
      <td align="right" width="10%">Email:</td>
        <td width="20%">
          <form:input size="50" path="email"/>
        </td>
        <td width="60%">
          <form:errors path="email" cssClass="error"/>
        </td>       
    </tr>
    <tr>
      <td align="right" width="10%">Vägbeskrivning</td>
        <td width="20%">
          <form:input size="50" path="vagbesk"/>
        </td>
        <td width="60%">
          <form:errors path="vagbesk" cssClass="error"/>
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
