<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title><c:out value="${formrubrik}"/></title>
   <link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  <script language="javascript" src="basic.js"></script>
</head>
<body>
<h1><c:out value="${formrubrik}"/></h1>
 
<form:form name="tackform" method="post" commandName="tackConfig">
	<div id="menu">
    <ul>
	   	<li><a href="kurstyper.htm" target="_self" title="avbryt"><span>Avbryt</span></a></li>
    	<li><a href="javascript:tackform.submit();" target="_self" title="reset"><span>Återställ</span></a></li>
       	<li><a href="javascript:tackform.submit();" target="_self" title="spara"><span>Spara</span></a></li>
       
    </ul>
	</div><br><br><br>
  <table width="60%" bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
    <tr>
      <td align="right">Rubrik:</td>
        <td colspan="2">
          <form:input size="60" path="header"/>
        </td>             
    </tr>
    <tr>
      <td align="right">Text:</td>
        <td colspan="2">
          <form:textarea rows="5" cols="80" path="body"/>
        </td>               
    </tr>
    <tr>
      <td align="right">Sluttext:</td>
        <td colspan="2">
          <form:textarea rows="3" cols="80" path="footer"/>
        </td>               
    </tr>
    <tr>
      <td align="right">Action:</td>
        <td colspan="2">
          <form:input size="90"  path="actionUrl"/>
        </td>               
    </tr> 
    <tr>
      <td align="right">Logga:</td>
        <td colspan="2">
          <form:input size="90"  path="logga"/>
        </td>               
    </tr>
    <tr>
      <td align="right">loggans höjd</td>
        <td colspan="2">
          <form:input size="6"  path="loggaH"/>&nbsp;& bredd:<form:input size="6"  path="loggaW"/>
        </td>                       
    </tr>
    <tr>
      <td align="right">Stylesheet:</td>
        <td colspan="2">
          <form:input size="90"  path="style"/>
        </td>               
    </tr>
    <tr><td colspan="3"><br></td></tr>
    <tr><td></td><td colspan="2" class="tableheader">Följande symboler kan användas de tre första fälten</td></tr>
    <tr>
      <td>&nbsp;</td>
      <td>#kurstyp</td>
      <td>Ersätts med kurstypen t.ex Linedance</td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>#kursniva</td>
      <td>Ersätts kursens nivå t.ex newcomer1</td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>#beteckning</td>
      <td>Ersätts med beteckningen på aktuell kurs</td>
    </tr>
    <tr> 
      <td>&nbsp;</td> 
      <td>#kursnamn</td>
      <td>Ersätts med namnet på aktuell kurs</td>
    </tr>
    <tr> 
      <td>&nbsp;</td> 
      <td>#startdag</td>
      <td>Ersätts med startdatumet för aktuell kurs</td>
    </tr>
    <tr> 
      <td>&nbsp;</td>  
      <td>#starttid</td>
      <td>Ersätts med starttiden för aktuell kurs</td>
    </tr>
    <tr>  
      <td>&nbsp;</td>	
      <td>#veckodag</td>
      <td>Ersätts med veckodag för aktuell kurs</td>
    </tr>    
    <tr>
      <td>&nbsp;</td>  
      <td>#plats</td>
      <td>Ersätts med kurslokalens namn</td>
    </tr>
    <tr>
      <td>&nbsp;</td>  
      <td>#fornamn</td>
      <td>Ersätts med elevens förnamn</td>
    </tr>
    <tr>
      <td>&nbsp;</td>  
      <td>#efternamn</td>
      <td>Ersätts med elevens efternamn</td>
    </tr> 
     <tr>
      <td>&nbsp;</td>  
      <td>#email</td>
      <td>Ersätts med elevens emailadress</td>
    </tr> 
    <tr>
      <td>&nbsp;</td>  
      <td>&lt;b&gt;<b>Text</b>&lt;/b&gt;</td>
      <td>Texten mellan symbolerna &lt;b&gt; och &lt;/b&gt; skrivs med fetstil</td>
    </tr>       
    <tr>
      <td>&nbsp;</td>  
      <td>&lt;i&gt;<i>Text</i>&lt;/i&gt;</td>
      <td>Texten mellan symbolerna &lt;i&gt; och &lt;/i&gt; skrivs med kursiv stil</td>
    </tr>
    <tr>
      <td>&nbsp;</td>  
      <td>&lt;i&gt;<u>Text</u>&lt;/u&gt;</td>
      <td>Texten mellan symbolerna &lt;u&gt; och &lt;/u&gt; skrivs med understruken stil</td>
    </tr> 
    <tr>
      <td>&nbsp;</td>  
      <td>&lt;br&gt;</td>
      <td>Ger en radframmatning, efterföljande text börjar på ny rad</td>
    </tr>
  </table>
  <br>
</form:form>
</body>
</html>
