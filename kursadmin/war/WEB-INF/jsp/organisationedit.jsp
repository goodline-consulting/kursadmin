<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title>Organisation</title>
   <link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  <script language="javascript" src="basic.js"></script>
</head>
<body>
<h1>Organisation</h1>
 
<form:form name="orgForm" method="post" commandName="organisation">
	<div id="menu">
    <ul>
       <li><a href="javascript:history.go(-1)" target="_self" title="avbryt"><span>Avbryt</span></a></li>
       <li><a href="javascript:orgForm.submit();" target="_self" title="spara"><span>Spara</span></a></li>
       <li><a href="javascript:orgForm.reset();" target="_self" title="reset"><span>Återställ</span></a></li>
    </ul>
	</div><br><br><br>
  <table width="95%" bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
    <tr>
      <td align="right" width="10%">Organisation:</td>
        <td width="20%">
          <form:input size="60" path="orgnamn"/>
        </td>
        <td width="60%">
          <form:errors path="orgnamn" cssClass="error"/>
        </td>       
    </tr>
    <tr>
      <td align="right" width="10%">Organisationstyp:</td>
      <td width="20%">
          <form:select  path="orgtyp">
          		<form:option value="0" label="Ideell förening" />
        		<form:option value="1" label="Aktiebolag" />
				<form:option value="2" label="Enskild Firma"/>
				<form:option value="3" label="Handelsbolag"/>
				<form:option value="3" label="Ekonomisk förening"/>
		  </form:select>
      </td>            
    </tr>   
    <tr>
      <td align="right" width="10%">Organisationsnr:</td>
        <td width="20%">
          <form:input size="60" path="orgnr"/>
        </td>
        <td width="60%">
          <form:errors path="orgnr" cssClass="error"/>
        </td>       
    </tr>
    <tr>
      <td align="right" width="10%">Momsredovisar:</td>
        <td width="20%">
           <form:select  path="momsredo">
          		<form:option value="1" label="Ja" />
        		<form:option value="0" label="Nej" />
		  </form:select>
        </td>
    </tr>
    <tr>
      <td align="right" width="10%">Momsnummer:</td>
        <td width="20%">
          <form:input size="60" path="momsnr"/>
        </td>
        <td width="60%">
          <form:errors path="momsnr" cssClass="error"/>
        </td>       
    </tr> 
    <tr>
      <td align="right" width="10%">Bankgiro:</td>
        <td width="20%">
          <form:input size="60" path="bankgiro"/>
        </td>
        <td width="60%">
          <form:errors path="bankgiro" cssClass="error"/>
        </td>       
    </tr>
    <tr>
      <td align="right" width="10%">Plusgiro:</td>
        <td width="20%">
          <form:input size="60" path="plusgiro"/>
        </td>
        <td width="60%">
          <form:errors path="plusgiro" cssClass="error"/>
        </td>       
    </tr> 
    <tr>
      <td align="right" width="10%">Kontaktperson:</td>
        <td width="30%">
          <form:input size="50" path="kontaktperson"/>
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
      <td align="right" width="10%">Email:</td>
        <td width="20%">
          <form:input size="50" path="email"/>
        </td>
        <td width="60%">
          <form:errors path="email" cssClass="error"/>
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
