<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Registrera Betalnig</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
 </head>
 	<body <c:if test="${not empty param.ok}"> onload="alert('${modelMap.msg}');searchform.refnr.focus()"</c:if>
 		  <c:if test="${not empty param.klar}"> onload="alert('${modelMap.msg}');window.location='${modelMap.target}'"</c:if>
 		  <c:if test="${not empty param.search and empty param.ok}"> onload="searchform.refnr.focus()"</c:if>
 	      <c:if test="${not empty param.no_found}"> onload="alert('${modelMap.msg}');searchform.refnr.focus()"</c:if>
 	      <c:if test="${not empty param.betald_error}"> onload="alert('${modelMap.msg}');searchform.refnr.focus()"</c:if>
 	      <c:if test="${not empty param.error}"> onload="alert('${modelMap.msg}');betalningform.betalt.focus()"</c:if>
 	      <c:if test="${not empty param.ref_error}"> onload="alert('${modelMap.msg}');searchform.refnr.focus()"</c:if>>
   <h1>Registrera Betalning</h1>  
   <form name="searchform"> 
   <div id="menu">
    <ul>
       <li><a href="${modelMap.target}" target="_self" title="stang"><span>Stäng</span></a></li>
       
	   <li><a href="javascript:searchform.submit()" target="_self" title="sub"><span>Sök</span></a></li>      
	   <li><span><input type="text" name="refnr"><span></li>  
	   <c:if test="${not empty modelMap.placering}">
	   		<input type="radio" name="placering" value="faktura" <c:if test="${modelMap.placering == 'faktura'}"> checked</c:if>>Faktura
	   		<input type="radio" name="placering" value="person" <c:if test="${modelMap.placering == 'person'}"> checked</c:if>>Person
	   </c:if>		             
    </ul>
	</div>
	</form>
	<br><br>
	<c:if test="${empty param.search and empty param.no_found and empty param.ref_error and empty param.klar}">	
		 <form name="betalningform" method="post">    
			<table>
				<tr>
					<td>Namn:</td><td><c:out value="${modelMap.person.fnamn}"></c:out>  <c:out value="${modelMap.person.enamn}"></c:out></td>			
				<tr>
				<c:if test="${!empty modelMap.faktura}">	
					<c:forEach items="${modelMap.kurser}" var="kurs" varStatus="cnt">  
						<tr>
							<td>Kurs:</td><td><c:out value="${kurs.beteckning}"></c:out>,  
							                  <c:out value="${kurs.kursNiva}"></c:out>
							                  <c:out value="${kurs.veckodag}"></c:out>
							                  <c:out value="${kurs.lokalNamn}"></c:out>
							                   <c:out value="${kurs.pris}"></c:out>:-
							              </td>			
						</tr>
					</c:forEach>
					<tr>
						<td>Rabatt:</td><td><fmt:formatNumber value="${modelMap.faktura.rabatt}" type="currency"/></td>
					</tr>
	      			<tr>
						<td>Fakturabelopp:</td><td><fmt:formatNumber value="${modelMap.faktura.belopp}" type="currency"/></td>
					</tr>	
					<tr>
						<td>Förfallodag:</td><td><fmt:formatDate pattern="yyyy-MM-dd" value="${modelMap.faktura.betalas}"/></td>
					</tr>			
					<tr>
						<td>Saldo:</td><td><fmt:formatNumber value="${modelMap.faktura.betalt}" type="currency"/></td>
					</tr>				
					
					<tr>
						<td>Betalt nu:</td><td><input type="text" name="betalt" value="${modelMap.betalt}"/></td>			
					</tr>
					<tr>
						<td>Betaltdatum:</td><td><input type="text" size=8 name="betaldatum" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${modelMap.betaldatum}"/>"></td>			
					</tr>
					<tr>
						<td>Betalninssätt:</td>
						<td>
							<input type="radio" name="betalsatt" value="Kontant" <c:if test="${modelMap.betalsatt == 'Kontant'}">checked</c:if>>Kontant
							<input type="radio" name="betalsatt" value="Plusgiro" <c:if test="${modelMap.betalsatt == 'Plusgiro'}">checked</c:if>> Plusgiro
							<input type="radio" name="betalsatt" value="Bgmax" <c:if test="${modelMap.betalsatt == 'Bgmax'}">checked</c:if>> Bgmax
						</td>
					</tr>
				</c:if>	
				<c:if test="${empty modelMap.faktura}">
					<tr>
						<td>Kund tillgodo:</td><td><input type="text" name="betalt" value="${modelMap.betalt}"/></td>			
					</tr>
				</c:if>	
				<tr>
					<td>Kommentar:</td><td><input type="text" size="40" name="info" value="${modelMap.info}"/></td>			
				</tr>	
				<c:if test="${not empty modelMap.person.email}">
					<tr>
					<td>Betalningsbekräftelse:</td><td><input type="checkbox" <c:if test="${not modelMap.elev.bbekr}">checked</c:if> name="maila" value="yes"><c:if test="${modelMap.elev.bbekr}"> (Betalningbekräftelse har skickats tidigare)</c:if></td>			
					</tr>
				</c:if>
				<tr><td colspan=2>&nbsp;</td></tr>
				<tr><td colspan=2>&nbsp;</td></tr>	
				<c:if test="${empty param.betald_error}">
				<tr>
	   				<td></td>
	   				<td><input type="submit" 
	   					<c:if test="${empty modelMap.faktura}"> name="placerakund"</c:if>
	   					<c:if test="${!empty modelMap.faktura}">name="registrera"</c:if>
	   				 	class="tablebutton" value="Registrera" title="Registrerar betalningen">
	   					<input type="button" name="avbryt" class="tablebutton" value="Avbryt" title="Avbryter registreringen" ONCLICK="window.location.href='betalning.htm'">
	   				</td>		   	
      			</tr>
      			</c:if>
    		</table>    
    	</form>	  
    </c:if>    
  </body>
</html>

