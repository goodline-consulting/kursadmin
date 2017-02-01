<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title>PersonInfo</title>
   <link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  <script language="javascript" src="basic.js"></script>
</head>
<body <c:if test="${person.saldo < 0 && (person.forminput1 == 'behall' || person.forminput1 == 'utbetala')}"> onload="kalle.forminput2.style.visibility='hidden';" </c:if>
	  <c:if test="${person.saldo < 0 && (person.forminput1 == 'faktura' || person.forminput1 == 'person')}"> onload="kalle.forminput2.style.visibility='visible';" </c:if>>
<h1>PersonInfo</h1>
 
<form:form name="kalle" method="post" commandName="person">
	<input type="hidden" name="kid">
	<div id="menu">
    <ul>
       <li><a href="javascript:history.go(-1)" target="_self" title="avbryt"><span>Avbryt</span></a></li>
       <li><a href="javascript:kalle.reset();" target="_self" title="reset"><span>Återställ</span></a></li>
       <li><a href="javascript:kalle.submit();" target="_self" title="spara"><span>Spara</span></a></li>
    </ul>
	</div>
	<br><br>
  <table width="95%" bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
    <tr>
      <td align="right" width="20%">Förnamn:</td>
        <td width="20%">
          <form:input path="fnamn"/>&nbsp;&nbsp;<form:errors path="fnamn" cssClass="error"/>
        </td>
        <td width="60%">Anmäl	
        <select name="kurs">       
			<option value="0" selected>till kurs</option>
			<c:forEach items="${kurser}" var="kurs">						
				<option value="<c:out value="${kurs.kid}"/>"><c:out value="${kurs.beteckning}"/></option>
			</c:forEach>		
		</select> 
		
     </td>                  
    </tr>
    <tr>
      <td align="right" width="20%">Efternamn:</td>
        <td width="20%">
          <form:input path="enamn"/>
        </td>
        <td width="60%">
          <form:errors path="enamn" cssClass="error"/>
        </td>       
    </tr>
    <tr>
      <td align="right" width="20%">Adress:</td>
        <td width="30%">
          <form:input size="50" path="adress"/>
        </td>
               
    </tr>    
    <tr>
      <td align="right" width="20%">Postnr:</td>
        <td width="20%">
          <form:input size="6" path="postnr"/>
        </td>
        <td width="60%">
          <form:errors path="postnr" cssClass="error"/>
        </td>       
    </tr>
     <tr>
      <td align="right" width="20%">Postadress:</td>
        <td width="30%">
          <form:input  path="postadress"/>
        </td>
               
    </tr>
    <tr>
      <td align="right" width="20%">Telefon:</td>
        <td width="20%">
          <form:input path="telefon"/>
        </td>
        <td width="60%">
          <form:errors path="telefon" cssClass="error"/>
        </td>       
    </tr>
    <tr>
      <td align="right" width="20%">Mobil:</td>
        <td width="20%">
          <form:input path="mobil"/>
        </td>
        <td width="60%">
          <form:errors path="mobil" cssClass="error"/>
        </td>       
    </tr>
    <tr>
      <td align="right" width="20%">Email:</td>
        <td width="20%">
          <form:input size="50" path="email"/>
        </td>
        <td width="60%">
          <form:errors path="email" cssClass="error"/>
        </td>       
    </tr>
     <tr>
      <td align="right" width="20%">Saldo:</td>
        <td width="20%">
          <input size="8" path="saldo" value="<fmt:formatNumber value="${person.saldo}" type="currency"/>" readonly/>
          <c:if test="${person.saldo < 0}">
          	<form:radiobutton path="forminput1" value="behall" onchange="kalle.forminput2.style.visibility='hidden'"/>Behåll
          	<form:radiobutton path="forminput1" value="utbetala" onchange="kalle.forminput2.style.visibility='hidden';"/>Utbetala	   		
			<form:radiobutton path="forminput1" value="faktura" onchange="kalle.forminput2.style.visibility='visible';kalle.forminput2.title='Ange fakturanummer';kalle.forminput2.value='';kalle.forminput2.focus();"/>Placera Faktura
			<form:radiobutton path="forminput1" value="person" onchange="kalle.forminput2.style.visibility='visible';kalle.forminput2.title='Ange kundnummer';kalle.forminput2.value='';kalle.forminput2.focus();"/>Annan Kund
	   		<form:input path="forminput2" size="8" />
          </c:if>
        </td>
        <td width="60%">
          <form:errors path="forminput2" cssClass="error"/>     
        </td>       
    </tr>
    <tr>
      <td align="right" valign="top" width="20%">Info:</td>
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
