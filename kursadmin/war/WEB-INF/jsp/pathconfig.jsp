<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title>Sökvägar</title>
   <link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  <script language="javascript" src="basic.js"></script>
</head>
<body>
<h1>Inställning av sökvägar</h1>
 
<form:form name="pathform" method="post" commandName="pathConfig">
	<div id="menu">
    <ul>
	   	<li><a href="config.htm" target="_self" title="avbryt"><span>Avbryt</span></a></li>
    	<li><a href="javascript:pathform.reset();" target="_self" title="Återställer formuläret"><span>Återställ</span></a></li>
       	<li><a href="javascript:pathform.submit();" target="_self" title="spara"><span>Spara</span></a></li>
   </ul>
	</div><br><br><br>
  <table width="60%" bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
    <tr>
      <td align="right">Bilagor:</td>
        <td colspan="2">
          <form:input size="60" path="bilagor"/>
        </td>             
    </tr>
    <tr>
      <td align="right">Bilder:</td>
        <td colspan="2">
         <form:input size="60" path="bilder"/>
        </td>               
    </tr>
     <tr>
      <td align="right">ProgramUrl:</td>
        <td colspan="2">
          <form:input size="60" path="programurl"/>
        </td>               
    </tr>
    <tr>
      <td align="right">ProgramPath:</td>
        <td colspan="2">
          <form:input size="60" path="programpath"/>
        </td>               
    </tr>         
    <tr>
      <td align="right">Programpunkter:</td>
        <td colspan="2">
          <form:input size="60" path="programpunkter"/>
        </td>               
    </tr>    
  </table>
  <br>
</form:form>
</body>
</html>
