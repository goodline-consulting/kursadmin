<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Organisation</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
  </head>
  <body>
  <h2>Organisation</h2>  
  <table>
   	<tr>
  		<td>Organisation:</td><td><c:out value="${org.orgnamn}"/></td>
  	</tr>
  	<tr>
  		<td>DatabasId:</td><td><c:out value="${org.oid}"/></td>
  	</tr> 
  	<tr>
  		<td>Organisationstyp:</td><td><c:out value="${org.orgtypsnamn}"/></td>
  	</tr>
 	<tr>
  		<td>Organisationsnr:</td><td><c:out value="${org.orgnr}"/></td>
  	</tr>
  	<tr>
  		<td>Momsredovisar:</td><td><c:out value="${org.momsredovisar}"/></td>
  	</tr>
  	<tr>
  		<td>Momsnr:</td><td><c:out value="${org.momsnr}"/></td>
  	</tr>
  	<tr>
  		<td>Bankgiro:</td><td><c:out value="${org.bankgiro}"/></td>
  	</tr>
  	<tr>
  		<td>Plusgiro:</td><td><c:out value="${org.plusgiro}"/></td>
  	</tr>
  	<tr>
  		<td>Kontaktperson:</td><td><c:out value="${org.kontaktperson}"/></td>
 	</tr>
 	<tr>
  		<td>Adress:</td><td><c:out value="${org.adress}"/></td>
 	</tr>
 	<tr>
  		<td>Postnr:</td><td><c:out value="${org.postnr}"/></td>
 	</tr> 	
 	<tr>
  		<td>PostAdress:</td><td><c:out value="${org.postadress}"/></td>
 	</tr> 			
 	<tr>
  		<td>Telefon:</td><td><c:out value="${org.telefon}"/></td>
 	</tr> 			
  	<tr>
  		<td>Email:</td><td><c:out value="${org.email}"/></td>
 	</tr> 
 	<tr>
  		<td>Info:</td><td><c:out value="${org.info}"/></td>
 	</tr> 			
  </table>
  <br>
  <br>
  <br>
  <br>
  <br>
  <br>
  <form name="shutdown">
  <div align=right><input type="button" 
      			           name="close" 
      			           class="tablebutton" 
      			           value="Stäng"
   					       title="Stänger fönstret"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="window.close();">
     						   	 
  </div>
  </form>   						   			
  </body>
</html>