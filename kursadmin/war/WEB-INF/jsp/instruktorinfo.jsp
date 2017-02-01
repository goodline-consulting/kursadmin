<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>InstruktörsInfo</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
  </head>
  <body>
  <h2>InstruktörsInfo</h2>  
  <table>
  	<tr>
  		<td>Namn:</td><td><c:out value="${instruktor.namn}"/></td>
  		<td rowspan="5" valign="top">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <c:if test="${!empty instruktor.bild}">
          <img src="bilder/${instruktor.bild}" width="${instruktor.bildx}" height="${instruktor.bildy}"/>          
        </c:if>
        <c:if test="${empty instruktor.bild}">
          <img src="bilder/bildsaknas.jpg" width="160" height="124"/>          
        </c:if>  
        </td>  
  	</tr>
  	<tr>
  		<td>Adress:</td><td><c:out value="${instruktor.adress}"/></td>
 	</tr>
 	<tr>
  		<td>Postnr:</td><td><c:out value="${instruktor.postnr}"/></td>
 	</tr> 
 	<tr>
  		<td>PostAdress:</td><td><c:out value="${instruktor.postadress}"/></td>
 	</tr>
 				
 	<tr>
  		<td>Telefon:</td><td><c:out value="${instruktor.telefon}"/></td>
 	</tr> 			
  	<tr>
  		<td>Mobil:</td><td><c:out value="${instruktor.mobil}"/></td>
 	</tr> 			
  	<tr>
  		<td>Email:</td><td><c:out value="${instruktor.email}"/></td>
 	</tr> 			
  	<tr>
  		<td>Info:</td><td><c:out value="${instruktor.info}"/></td>
 	</tr> 			
  </table>
 
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