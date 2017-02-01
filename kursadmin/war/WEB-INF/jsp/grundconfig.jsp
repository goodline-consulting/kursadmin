<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title>Grundinst�llningar</title>
   <link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  <script language="javascript" src="basic.js"></script>
</head>
<body>
<h1>Grundinst�llningar</h1>
 
<form:form name="grundform" method="post" commandName="grundConfig">
	<div id="menu">
    <ul>
	   	<li><a href="config.htm" target="_self" title="avbryt"><span>Avbryt</span></a></li>
    	<li><a href="javascript:grundform.reset();" target="_self" title="reset"><span>�terst�ll</span></a></li>
       	<li><a href="javascript:grundform.submit();" target="_self" title="spara"><span>Spara</span></a></li>
       
    </ul>
	</div><br><br><br>
  <table width="60%" bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
  	<tr>
      <th align="center" colspan="3" >Logga och stylesheet</th>                     
    </tr>
    <tr>
      <td align="right">Loggans URL:</td>
        <td colspan="2">
          <form:input size="60" path="logga"/>
        </td>             
    </tr>
    <tr>
      <td align="right">Bredd:</td>
        <td colspan="2">
          <form:input size="6" path="loggaW"/>
        </td>               
    </tr>
    <tr>
      <td align="right">H�jd:</td>
        <td colspan="2">
          <form:input size="6" path="loggaH"/>
        </td>               
    </tr>
    <tr>
      <td align="right">Stylesheet URL:</td>
        <td colspan="2">
          <form:input size="60" path="style"/>
        </td>               
    </tr>
    <tr>
      <th align="center" colspan="3" >Mailadresser</th>                     
    </tr>
    <tr>
      <td align="right">Fakturamail:</td>
       <td colspan="2">
          <form:input size="60" path="fakturaMail"/>
       </td>
     <tr>  
       <td align="right">Betalmail:</td>
       <td colspan="2">
          <form:input size="60" path="betalMail"/>          
       </td>
      </tr>
      <tr>                 
       <td align="right">Anv�nd:</td>
       <td colspan="2">
          <form:checkbox path="fakeMail"/>          
       </td>
    </tr>
    <tr>
      <th align="center" colspan="3" >Kursanm�lan generator</th>                     
    </tr>
    <tr>
      <td align="right">FormAction:</td>
       <td colspan="2">
          <form:input size="60" path="anmActionUrl"/>
       </td>
     <tr>  
       <td align="right">Mailmottagare:</td>
       <td colspan="2">
          <form:input size="60" path="anmalanMail"/>          
       </td>
      </tr>
  </table>
  <br>
</form:form>
</body>
</html>
