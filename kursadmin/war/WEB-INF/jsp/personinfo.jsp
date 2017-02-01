<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>PersonInfo</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
  </head>
  <body>
  <h2>PersonInfo</h2>  
  <table>
  	<tr>
  		<td>Namn:</td><td><c:out value="${modelMap.person.fnamn}"/> <c:out value="${modelMap.person.enamn}"/></td>
  	</tr>
  	<tr>
  		<td>Databas Id:</td><td><c:out value="${modelMap.person.pid}"/></td>
  	</tr>
  	<tr>
  		<td>Inskriven:</td><td><fmt:formatDate value="${modelMap.person.inskriven}" dateStyle="full"/></td>
  	</tr>
  	<tr>
  		<td>Adress:</td><td><c:out value="${modelMap.person.adress}"/></td>
 	</tr>
 	<tr>
  		<td>Postnr:</td><td><c:out value="${modelMap.person.postnr}"/></td>
 	</tr> 	
 	<tr>
  		<td>PostAdress:</td><td><c:out value="${modelMap.person.postadress}"/></td>
 	</tr>
 			
 	<tr>
  		<td>Telefon:</td><td><c:out value="${modelMap.person.telefon}"/></td>
 	</tr> 			
  	<tr>
  		<td>Mobil:</td><td><c:out value="${modelMap.person.mobil}"/></td>
 	</tr> 			
  	<tr>
  		<td>Email:</td><td><c:out value="${modelMap.person.email}"/></td>
 	</tr> 	
 	<tr>
  		<td>Saldo:</td><td><fmt:formatNumber value="${modelMap.person.saldo}" type="currency"/></td>
 	</tr> 				
  	<tr>
  		<td>Info:</td><td><c:out value="${modelMap.person.info}"/></td>
 	</tr> 
 	</table>
 	<h2>Kurser</h2>
 	<!-- scrollable area -->
	<div style="height:200px;padding:6px; border-style:solid; border-width:1; overflow:auto; direction:ltr;">
	<div style="direction:ltr;"> 
	<table>
	<tr><th align="left">Startdatum</th><th align="left">Benämning</th><th align="left">Plats</th></tr>
 	<c:forEach items="${modelMap.kurser}" var="kurs" varStatus="cnt">
 		
 		<tr><td><c:out value="${kurs.startdatum}"/></td><td>&nbsp;<c:out value="${kurs.kursnamn}"/>, <c:out value="${kurs.veckodag}"/> <c:out value="${kurs.starttid}"/></td><td><c:out value="${kurs.lokalNamn}"/></td></tr>
 	</c:forEach>			
  </table>
  </div></div> 
  <br>
  <br>
  <br>
  <form name="shutdown">
  <div align=right><input type="button" 
      			           name="close" 
      			           style="position:absolute;right:10px;bottom:15px;" 
      			           value="Stäng"
   					       title="Stänger fönstret"   						   
   						   onClick="window.close();">
     						   	 
  </div>
  </form>   						   			
  </body>
</html>