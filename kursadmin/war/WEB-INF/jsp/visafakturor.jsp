<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Fakturor</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
 </head>
    <body <c:if test="${not empty modelMap.msg}"> onload="alert('${modelMap.msg}');"</c:if>>

   <h1>Fakturor ${modelMap.person.fnamn} ${modelMap.person.enamn}</h1>  
   <form name="searchform" method="post"> 
   <div id="menu">
    <ul>      
       	<li><a href="personer.htm" target="_self" title="stang"><span>Stäng</span></a></li>
    </ul>
	</div>
	<br>
	<br>
	</form>
	<form name="fakturaform">   
	<!-- scrollable area -->
	<div style="height:510px; padding:6px; border-style:solid; border-width:1; overflow:auto; direction:ltr;">
	<div style="direction:ltr;"> 
	<table>

		<tr>
			<th align="left">Fakturanr</th><th align="left">Datum</th><th align="left">Kurser</th><th align="left">Belopp</th><th align="left">Förfallodag</th><th align="left">Betalt</th><th align="left">Datum</th><th align="left">Restbelopp</th>
		</tr>	    
    	<c:forEach items="${modelMap.fakturor}" var="faktura" varStatus="cnt">
    		<c:if test="${cnt.count % 2 == 0}">
    		<tr class="jamnrad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'jamnrad';">
    		</c:if>
    		<c:if test="${cnt.count % 2 != 0}">
    		<tr class="uddarad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'uddarad';">
    		</c:if>       			
      			<td><c:out value="${faktura.fakturanr}"/></td>
      			<td><c:out value="${faktura.skapad}"/></td>
      			<td><c:out value="${faktura.kids}"/></td>
      			<td><fmt:formatNumber value="${faktura.belopp}" type="currency"/></td>
      			<td><c:out value="${faktura.betalas}"/></td>
      			<td><fmt:formatNumber value="${faktura.betalt}" type="currency"/></td>
      			<td><c:out value="${faktura.betald}"/></td>
      			<td><fmt:formatNumber value="${faktura.belopp - faktura.betalt}" type="currency"/></td>
      			<td><input type="button" 
      			           name="V<c:out value="${faktura.fakturanr}"/>" 
      			           class="tablebutton" 
      			           value="Visa"
   					       title="Visar all information om fakturan"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="openWindow2('faktura.htm?visa=${faktura.fakturanr}', 500, 650);">
   				</td>	
   				
   				
   					<c:if test="${faktura.fakturatyp != 2 && faktura.krediterat < faktura.belopp}">
						<td>   					
   						<input type="button" 
      			           name="K<c:out value="${faktura.fakturanr}"/>" 
      			           class="tablebutton" 
      			           value="Kreditera"
   					       title="Krediterar fakturan"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="window.location='faktura.htm?kreditera=${cnt.count -1}';">
   						 </td>  
   						 <td>&nbsp;</td>
   					</c:if>
   					<c:if test="${faktura.fakturatyp != 2 && faktura.krediterat >= faktura.belopp}">
						<td>&nbsp;</td><td>&nbsp;</td>
					</c:if>	   		
   					<c:if test="${faktura.fakturatyp == 2 && faktura.belopp != faktura.betalt}">
 						<td>  					
   						<input type="button" 
      			           name="T<c:out value="${faktura.fakturanr}"/>" 
      			           class="tablebutton" 
      			           value="Tillgodo"
   					       title="Lägger det krediterade beloppet kunden tillgodo"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="window.location='faktura.htm?tillgodo=${cnt.count -1}';">
   						</td>   
   						<td>   
   						<input type="button" 
      			           name="A<c:out value="${faktura.fakturanr}"/>" 
      			           class="tablebutton" 
      			           value="Återbetala"
   					       title="Markerar det krediterade beloppet som återbetalat"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="window.location='faktura.htm?utbetala=${cnt.count -1}';">
   						</td>   	   
   					</c:if>	
   					<c:if test="${faktura.fakturatyp == 2 && faktura.belopp == faktura.betalt}">
   						<td>&nbsp;</td><td>&nbsp;</td>
   					</c:if>		   
   				</td>	   
   				<c:if test="${faktura.fakturatyp != 2 && faktura.betalt == 0}">			    
   			    <td><input type="button" 
      			           name="R<c:out value="${person.pid}"/>" 
      			           class="tablebutton" 
      			           value="Ta bort"
   					       title="Tar bort faktura  <c:out value="${faktura.fakturanr}"/> ur registret"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="if (confirm('Skall du verkligen radera faktura <c:out value="${faktura.fakturanr}"/> ur registret?') == 1) window.location = 'faktura.htm?radera=<c:out value="${faktura.fakturanr}"/>';">   						   
   			    </td>		      			    	
   			    </c:if>
      		</tr>
    	</c:forEach>
    	</table> 
    	</div>
    	</div>
    </form>	
      
  </body>
</html>

