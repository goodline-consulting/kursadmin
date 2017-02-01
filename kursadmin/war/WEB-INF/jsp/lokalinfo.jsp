<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>LokalInfo</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
  </head>
  <body>
  <h2>LokalInfo</h2>  
  <table>
  	<tr>
  		<td>Lokalnamn:</td><td><c:out value="${lokal.lokalnamn}"/></td>
  	</tr>
  	<tr>
  		<td>Kontaktperson:</td><td><c:out value="${lokal.kontakt}"/></td>
 	</tr>
 	<tr>
  		<td>Timpris:</td><td><c:out value="${lokal.timpris}"/></td>
 	</tr>
  	<tr>
  		<td>Adress:</td><td><c:out value="${lokal.adress}"/></td>
 	</tr>
 	<tr>
  		<td>Postnr:</td><td><c:out value="${lokal.postnr}"/></td>
 	</tr> 	
 	<tr>
  		<td>PostAdress:</td><td><c:out value="${lokal.postadress}"/></td>
 	</tr> 			
 	<tr>
  		<td>Telefon:</td><td><c:out value="${lokal.telefon}"/></td>
 	</tr> 			
  	<tr>
  		<td>Mobil:</td><td><c:out value="${lokal.mobil}"/></td>
 	</tr> 			
  	<tr>
  		<td>Email:</td><td><c:out value="${lokal.email}"/></td>
 	</tr> 
 	<tr>
  		<td>Vägbeskrivning:</td><td><a href='<c:out value="${lokal.vagbesk}"/>'>Klicka här</a></td>
 	</tr> 			
  	<tr>
  		<td>Info:</td><td><c:out value="${lokal.info}"/></td>
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