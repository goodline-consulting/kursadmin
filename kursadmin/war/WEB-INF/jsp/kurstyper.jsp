<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Kurstyper</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
 </head>
  
  <body<c:if test="${!empty modelMap.error}"> onload="javascript:alert('${modelMap.error}');"</c:if>>        	  
   <h1>Kurstyper</h1>  
   <form name="searchform"> 
   <div id="menu">
    <ul>
       <li><a href="config.htm" target="_self" title="stang"><span>Stäng</span></a></li>	
       <li><a href="kurstyper.htm?edit=0" target="_self" title="ny"><span>Ny</span></a></li>              
    </ul>
	</div><br><br>
	</form>
	<form name="kurstypform">    
	<table>
		<tr>
			<th align="left">Kurstyp</th><th colspan="6" align="left">Beskrivning</th>
		</tr>	    
    	<c:forEach items="${modelMap.kurstyper}" var="typ" varStatus="cnt">
    		<c:if test="${cnt.count % 2 == 0}">
    		<tr class="jamnrad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'jamnrad';">
    		</c:if>
    		<c:if test="${cnt.count % 2 != 0}">
    		<tr class="uddarad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'uddarad';">
    		</c:if>      			
      			<td><c:out value="${typ.namn}"/></td>  
      			<td><c:out value="${typ.info}"/></td>
      			<td><input type="button" 
      			           name="V<c:out value="${typ.tid}"/>" 
      			           class="tablebutton" 
      			           value="Visa"
   					       title="Visar all information om <c:out value="${typ.namn}"/>"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="openWindow('kurstyper.htm', '<c:out value="${typ.tid}"/>', 400, 230);">
   				</td>	
   			    <td><input type="button" 
      			           name="A<c:out value="${typ.tid}"/>" 
      			           class="tablebutton" 
      			           value="Ändra"
   					       title="Ändrar informationen i registret"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="window.location = 'kurstyper.htm?edit=<c:out value="${typ.tid}"/>';">
   				<td><input type="button" 
      			           name="A<c:out value="${typ.tid}"/>" 
      			           class="tablebutton" 
      			           value="Anm Bekr"
   					       title="Mail vid anmälningsbekräftelse"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="window.location = 'mailconfig.htm?kurstyp=<c:out value="${typ.tid}"/>&mailtyp=anmbekr';">   						   		   
   			    </td>
   			    <td><input type="button" 
      			           name="A<c:out value="${typ.tid}"/>" 
      			           class="tablebutton" 
      			           value="Bet Bekr"
   					       title="Mail vid betalningsbekräftelse"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="window.location = 'mailconfig.htm?kurstyp=<c:out value="${typ.tid}"/>&mailtyp=betbekr';">   						   		   
   			    </td>
   			    <td><input type="button" 
      			           name="A<c:out value="${typ.tid}"/>" 
      			           class="tablebutton" 
      			           value="Tack"
   					       title="Tack för anmälan fönster"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="window.location = 'tackconfig.htm?kurstyp=<c:out value="${typ.tid}"/>&kurstypsnamn=<c:out value="${typ.namn}"/>';">   						   		   
   			    </td>
   			    <td><input type="button" 
      			           name="R<c:out value="${typ.tid}"/>" 
      			           class="tablebutton" 
      			           value="Ta bort"
   					       title="Tar bort <c:out value="${typ.namn}"/> ur registret"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="if (confirm('Skall du verkligen radera <c:out value="${typ.namn}"/> ur registret?') == 1) window.location = 'kurstyper.htm?radera=<c:out value="${typ.tid}"/>';">   						   
   			    </td>		   	
      		</tr>
    	</c:forEach>
    </form>	
    </table>    
  </body>
</html>

