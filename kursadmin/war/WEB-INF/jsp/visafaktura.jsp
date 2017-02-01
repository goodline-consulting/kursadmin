<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Faktura</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />  	
  	<script language="javascript" src="basic.js"></script>
 </head>
  <body <c:if test="${!empty modelMap.msg}"> onload="javascript:alert('${modelMap.msg}');window.close();"</c:if>>
<h2><c:if test="${modelMap.faktura.fakturatyp == 0}">Faktura</c:if><c:if test="${modelMap.faktura.fakturatyp == 1}">Tilläggsfaktura</c:if><c:if test="${modelMap.faktura.fakturatyp == 2}">Kreditfaktura</c:if> ${modelMap.faktura.fakturanr}</h2>
<h3><c:out value="${modelMap.person.fnamn}"/> <c:out value="${modelMap.person.enamn}"/></h3>  
	<c:if test="${!empty modelMap.faktura.info}">
		<table>
  		<tr>
  			<td colspan="2"><b>${modelMap.faktura.info}</b></td>
  		</tr>
  		<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
  		</table>
  	</c:if>	
  	<c:if test="${modelMap.faktura.kopplad != 0}">
		<table>
  		<tr>
  			<td colspan="2"><b>Se faktura ${modelMap.faktura.kopplad}</b></td>
  		</tr>
  		<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
  		</table>
  	</c:if>	
  <table>
  	<c:if test="${!empty modelMap.kurser}">
	  	<tr><th align="left">Beteckning</th><th align="left">Benämning</th><th align="left">Startdatum</th><th align="left">Pris</th></tr>
	 	<c:forEach items="${modelMap.kurser}" var="kurs" varStatus="cnt"> 		
	 		<tr> 			
	 			<td>&nbsp;<c:out value="${kurs.beteckning}"/></td>
	 			<td><c:out value="${kurs.kursnamn}"/></td>
	 			<td><c:out value="${kurs.startdatum}"/></td>
	 			<td><c:out value="${kurs.pris}"/>:-</td>
	 		</tr>
	 	</c:forEach>
 	</c:if>
 	<tr>
  		<td>&nbsp;</td><td>&nbsp;</td>
  	</tr>  	
  	<tr>
  		<td>Fakturadatum:</td><td>${modelMap.faktura.skapad}</td>
  	</tr>
  	<tr>
  		<td>Förfallodatum:</td><td>${modelMap.faktura.betalas}</td>
  	</tr>
  	<tr>
  		<td>OCR:</td><td>${modelMap.faktura.ocr}<td></td>
  	</tr> 	
  	<tr>
  		<td>Belopp:</td><td><b><fmt:formatNumber value="${modelMap.faktura.belopp}" type="currency"/></b></td>
 	</tr>
 	<tr>
  		<td>Varav moms:</td><td><fmt:formatNumber value="${modelMap.faktura.moms}" type="currency"/></td>
 	</tr>
 	<tr>
  		<td>Rabatt:</td><td><fmt:formatNumber value="${modelMap.faktura.rabatt}" type="currency"/></td>
 	</tr>
 	<c:if test="${modelMap.faktura.skuld > 0}">	
 		<tr>
  			<td>Kundfordran:</td><td><fmt:formatNumber value="${modelMap.faktura.skuld}" type="currency"/></td>
 		</tr>
 	</c:if>
 	<c:if test="${modelMap.faktura.tillgodo > 0}">
 		<tr>
  			<td>Kund tillgodo:</td><td><fmt:formatNumber value="${modelMap.faktura.tillgodo}" type="currency"/></td>
 		</tr>
 	</c:if>	
 	<tr> 	
  		<td>Betalt:</td><td><fmt:formatNumber value="${modelMap.faktura.betalt}" type="currency"/> (${modelMap.faktura.betald})</td>
 	</tr>	
  	<tr> 	
  		<td>Restbelopp:</td><td><b><fmt:formatNumber value="${modelMap.faktura.belopp - modelMap.faktura.betalt}" type="currency"/></b> </td>
 	</tr>	
 	
 	<tr>
  		<td>&nbsp;</td><td>&nbsp;</td>
  	</tr>
  	<tr>
		<th align="left">Bet.datum&nbsp;</th><th align="left">Bet.av</th><th align="left">Summa</th><th align="left">Bet.sätt&nbsp;</th></th><th align="left">Info</th>
	</tr>	
 	<c:forEach items="${modelMap.betalningar}" var="betalning" varStatus="cnt">
    	<tr>	      			
      		<td><fmt:formatDate pattern="yyyy-MM-dd" value="${betalning.betaldatum}"/></td>
      		
      		<td><c:out value="${betalning.name}"/></td>
      		<td align="right"><fmt:formatNumber value="${betalning.amount}" type="currency"/></td>
      		
      		<td><c:out value="${betalning.betalkanal}"/></td>
      		
      		<td><c:out value="${betalning.info}"/></td>	
      	</tr>
   </c:forEach>
 	
 	
 	</table>
 	
 	
	
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

