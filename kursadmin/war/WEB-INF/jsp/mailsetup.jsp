<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title>Mail</title>
   <link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  <script language="javascript" src="basic.js"></script>
</head>
<body>
<h1>Inställning av mail</h1>
 
<form:form name="mailform" method="post" commandName="mailSetup">
	<div id="menu">
    <ul>
	   	<li><a href="config.htm" target="_self" title="avbryt"><span>Avbryt</span></a></li>
    	<li><a href="javascript:mailform.submit();" target="_self" title="reset"><span>Återställ</span></a></li>
       	<li><a href="javascript:mailform.submit();" target="_self" title="spara"><span>Spara</span></a></li>
   </ul>
	</div><br><br><br>
  <table width="60%" bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
    <tr>
      <td align="right">Avsändare:</td>
        <td colspan="2">
          <form:input size="60" path="sender"/>
        </td>             
    </tr>
    <tr>
      <td align="right" valign="top">Sluttext:</td>
        <td colspan="2">
          <form:textarea rows="6" cols="80" path="footer"/>
        </td>               
    </tr> 
   
  </table>
  <br>
</form:form>
</body>
</html>
