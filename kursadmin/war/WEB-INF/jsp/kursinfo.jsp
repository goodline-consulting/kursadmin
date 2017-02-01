<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>KursInfo</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
  </head>
  <body>
  <h2>KursInfo</h2>  
  <table>
     
    <tr>
  		<td>Kurstyp:</td><td class="infocell"><c:out value="${modelMap.kursAll.kursTyp}"/></td>
 	</tr>
 	<tr>
  		<td>Niv�:</td><td class="infocell"><c:out value="${modelMap.kursAll.kursNiva}"/></td>
 	</tr>
 	<tr>
  		<td>Status:</td><td class="infocell"><c:out value="${modelMap.kursAll.statusText}"/></td>
 	</tr>
  	<tr>
  		<td>Beteckning</td><td class="infocell"><c:out value="${modelMap.kursAll.beteckning}"/> </td>
  	</tr>
  	<tr>
  		<td>Databas Id</td><td class="infocell"><c:out value="${modelMap.kursAll.kid}"/> </td>
  	</tr>
  	<tr>
  		<td>Kursnamn:</td><td class="infocell"><c:out value="${modelMap.kursAll.kursnamn}"/></td>
 	</tr> 	
 	<tr>
  		<td>Instrukt�r:</td><td class="infocell"><c:out value="${modelMap.kursAll.instruktorNamn}"/></td>
 	</tr> 	
 	<tr>
  		<td>Plats:</td><td class="infocell"><c:out value="${modelMap.kursAll.lokalNamn}"/></td>
 	</tr> 			
 	<tr>
  		<td>Veckodag:</td><td class="infocell"><c:out value="${modelMap.kursAll.veckodag}"/></td>
 	</tr> 			
  	<tr>
  		<td>Tid:</td><td class="infocell"><c:out value="${modelMap.kursAll.starttid}"/></td>
 	</tr> 			
  	<tr>
  		<td>L�ngd:</td><td class="infocell"><c:out value="${modelMap.kursAll.lengd}"/> minuter</td>
 	</tr>
 	<tr>
  		<td>Startdatum:</td><td class="infocell"><c:out value="${modelMap.kursAll.startdatum}"/></td>
 	</tr> 	
 	<tr>
  		<td>Pris:</td><td class="infocell"><c:out value="${modelMap.kursAll.pris}"/>:-</td>
 	</tr> 	
 	<tr>
  		<td>Lokalkostnad:</td><td class="infocell"><c:out value="${modelMap.kursAll.lkost}"/>:-</td>
 	</tr> 
 	<tr>
  		<td>Instrukt�rskostnad:</td><td class="infocell"><c:out value="${modelMap.kursAll.ikost}"/>:-</td>
 	</tr>
 	<tr>
  		<td>Materialkostnad:</td><td class="infocell"><c:out value="${modelMap.kursAll.mkost}"/>:-</td>
 	</tr> 
 	<tr>
  		<td>�vriga kostnader:</td><td class="infocell"><c:out value="${modelMap.kursAll.okost}"/>:-</td>
 	</tr>
 	<tr>
  		<td>Antal elever:</td><td class="infocell"><c:out value="${modelMap.ekonomi.elever}"/></td>
 	</tr> 	
   </table>
   <h3>KursTillf�llen</h3>  
   <table><tr>
   <c:forEach items="${modelMap.kursAll.kurstillf}" var="kurstillf" varStatus="cnt">
   		<td class="infocell">
   		<fmt:formatDate value="${kurstillf}" pattern="dd MMM yyyy"/>
   		</td>   		
   		<c:if test="${cnt.count % 4 == 0}">
   			</tr>
   			<tr>
   		</c:if>	
   </c:forEach>	
  </tr></table>
  <h3>Ekonomi</h3>  
   <table>
   
    <tr>
  		<td>Lokalkostnader:</td><td class="infocell"><c:out value="${modelMap.ekonomi.lkost}"/>:-</td>
 	</tr> 
 	<tr>
  		<td>Instrukt�rskostnader:</td><td class="infocell"><c:out value="${modelMap.ekonomi.ikost}"/>:-</td>
 	</tr>
 	<tr>
  		<td>Materialkostnader:</td><td class="infocell"><c:out value="${modelMap.ekonomi.mkost}"/>:-</td>
 	</tr> 
 	<tr>
  		<td>�vriga kostnader:</td><td class="infocell"><c:out value="${modelMap.ekonomi.okost}"/>:-</td>
 	</tr> 	
 	<tr>
  		<td>kostnader totalt:</td><td class="infocell"><c:out value="${modelMap.ekonomi.totkost}"/>:-</td>
 	</tr>
 	<tr>
  		<td>Break even:</td><td class="infocell"><c:out value="${modelMap.ekonomi.breakeven}"/></td>
 	</tr>
  	<tr>
  		<td>Int�ckter, <c:out value="${modelMap.ekonomi.betalande}"/> betalande:&nbsp;</td><td class="infocell"><c:out value="${modelMap.ekonomi.inkomster}"/>:-</td>
 	</tr>
 	<tr><td colspan="2"><hr></td></tr> 
 	<tr>
  		<td>Netto:</td><td class="infocell"><c:out value="${modelMap.ekonomi.netto}"/>:-</td>
 	</tr> 	 	
  </tr></table>
  <form name="shutdown">
  <div align=right><input type="button" 
      			           name="close" 
      			           class="tablebutton" 
      			           value="St�ng"
   					       title="St�nger f�nstret"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="window.close();">
     						   	 
  </div>
  </form>   						   			
  </body>
</html>