<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Lokaler</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />  	
  	<script language="javascript" src="basic.js"></script>
 </head>
  <body>
  

   <h1>Lokaler</h1>  
   <form name="searchform"> 
   <div id="menu">
    <ul>
       <li><a href="main.htm" target="_self" title="stang"><span>Stäng</span></a></li>	
       <li><a href="lokaledit.htm?recid=0" target="_self" title="ny"><span>Ny</span></a></li>              
    </ul>
	</div>
	<br>
	<br>
	</form>	
	<form name="lokalform">    
    
	<table>
		<tr>
			<th align="left">Lokal</th><th align="left">Kontakt</th><th align="left">Telefon</th><th align="left" colspan=4>Email</th>
		</tr>	    
    	<c:forEach items="${modelMap.lokaler}" var="lokal" varStatus="cnt">
    		<c:if test="${cnt.count % 2 == 0}">
				<tr class="jamnrad"  onmouseover="this.className = 'highrow';" onmouseout="this.className = 'jamnrad';">
    		</c:if>
    		<c:if test="${cnt.count % 2 != 0}">
    			<tr class="uddarad"  onmouseover="this.className = 'highrow';" onmouseout="this.className = 'uddarad';">    		
   			</c:if>      			
      			<td><c:out value="${lokal.lokalnamn}"/></td>  
      			<td><c:if test="${!empty lokal.kontakt}">
      					<c:out value="${lokal.kontakt}"/>
      				</c:if>
      				<c:if test="${empty lokal.kontakt}">
      					&nbsp
      				</c:if>
      		    </td>    
      		    <td><c:if test="${!empty lokal.telefon}">
      					<c:out value="${lokal.telefon}"/>
      				</c:if>
      				<c:if test="${empty lokal.telefon}">
      					&nbsp
      				</c:if>
      		    </td>			
      			<td><c:if test="${!empty lokal.email}">
      					<a href="<c:out value="${lokal.email}"/>"><c:out value="${lokal.email}"/></a>
      				</c:if>
      				<c:if test="${empty lokal.email}">
      					&nbsp
      				</c:if>
      		    </td>      			
      			<td><input type="button" 
      			           name="V<c:out value="${lokal.lid}"/>" 
      			           class="tablebutton" 
      			           value="Visa"
   					       title="Visar all information om <c:out value="${lokal.lokalnamn}"/>"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="openWindow('lokaler.htm', '<c:out value="${lokal.lid}"/>', 400, 360);">
   				</td>	
   			    <td><input type="button" 
      			           name="A<c:out value="${lokal.lid}"/>" 
      			           class="tablebutton" 
      			           value="Ändra"
   					       title="Ändrar informationen i registret"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="window.location = 'lokaledit.htm?recid=<c:out value="${lokal.lid}"/>';">
   						   
   			    </td>
   			    <td><input type="button" 
      			           name="R<c:out value="${lokal.lid}"/>" 
      			           class="tablebutton" 
      			           value="Ta bort"
   					       title="Tar bort <c:out value="${lokal.lokalnamn}"/> ur registret"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="if (confirm('Skall du verkligen radera <c:out value="${lokal.lokalnamn}"/> ur registret?') == 1) window.location = 'lokaler.htm?radera=<c:out value="${lokal.lid}"/>';">   						   
   			    </td>		   	
      		</tr>
    	</c:forEach>
    </table>    
   </form>	
  </body>
</html>

