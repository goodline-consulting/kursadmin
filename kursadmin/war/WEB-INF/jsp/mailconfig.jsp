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
 
<form:form name="mailform" method="post" commandName="mailConfig">
	<div id="menu">
    <ul>
	   	<li><a href="kurstyper.htm" target="_self" title="avbryt"><span>Avbryt</span></a></li>
    	<li><a href="javascript:mailform.submit();" target="_self" title="reset"><span>Återställ</span></a></li>
       	<li><a href="javascript:mailform.submit();" target="_self" title="spara"><span>Spara</span></a></li>
       
    </ul>
	</div><br><br><br>
  <table width="60%" bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
    <tr>
      <td align="right">Rubrik:</td>
        <td colspan="2">
          <form:input size="60" path="rubrik"/>
        </td>             
    </tr>
    <tr>
      <td align="right">Inledning:</td>
        <td colspan="2">
          <form:textarea rows="3" cols="80" path="ingress"/>
        </td>               
    </tr>
    <tr>
      <td align="right">Meddelande:</td>
        <td colspan="2">
          <form:textarea rows="3" cols="80" path="body"/>
        </td>               
    </tr>
    <tr>
      <td align="right">Avslutning:</td>
        <td colspan="2">
          <form:textarea rows="3" cols="80" path="finish"/>
        </td>               
    </tr>
     <tr>
      <td align="right">Sluttext:</td>
        <td colspan="2">
          <form:textarea rows="3" cols="80" path="footer"/>
        </td>               
    </tr> 
    <tr><td colspan="3"><br></td></tr>
    <tr><td></td><td colspan="2" class="tableheader">Följande symboler kan användas i samtliga fält</td></tr>
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
      <td>#kursavgift</td>
      <td>Ersätts med kursavgiften för aktuell kurs</td>
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
      <td>#betalning</td>
      <td>Ersätts med den summa som är inbetald</td>
    </tr>
     <tr>
      <td>&nbsp;</td>  
      <td>#refnr</td>
      <td>Ersätts med ett referensnummer för inbetalning</td>
    </tr>
     <tr>
      <td>&nbsp;</td>  
      <td>#restbelopp</td>
      <td>Ersätts med den summa som är kvar att betala</td>
    </tr>
    <tr>
      <td>&nbsp;</td>  
      <td>#ombetalt text #slut</td>
      <td>Är kursavgiften fullt betald så skrivs texten mellan symbolerna #ombetalt och #slut ut</td>
    </tr> 
    <tr>
      <td>&nbsp;</td>  
      <td>#annars text #slut</td>
      <td>Är kursavgiften inte fullt betald så skrivs texten mellan symbolerna #annars och #slut ut</td>
    </tr> 
    <tr>
      <td>&nbsp;</td>  
      <td>#&lt;singularis-pluralis&gt;</td>
      <td>Finns flera kurser så används "pluralis" annars "singularis", tex kursplats#&lt;en-erna&gt;</td>
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
