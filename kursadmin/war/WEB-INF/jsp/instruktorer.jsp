<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Instruktörsregister</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
 </head>
  <body>
  
   <h1>Instruktörer</h1>  
   <form name="searchform"> 
   <div id="menu">
    <ul>
       <li><a href="main.htm" target="_self" title="stang"><span>Stäng</span></a></li>
       <li><a href="instruktoredit.htm?recid=0" target="_self" title="ny"><span>Ny</span></a></li>
              
    </ul>
	</div>
	<br><br>
	</form>
	<form name="personform">    
	<table>
		<tr>
			<th align="left">Namn</th><th align="left">Email</th><th align="left"> Telefon</th><th colspan=3>
		</tr>	    
    	<c:forEach items="${modelMap.instruktors}" var="instruktor" varStatus="cnt">
    		<c:if test="${cnt.count % 2 == 0}">
    		<tr class="jamnrad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'jamnrad';">
    		</c:if>
    		<c:if test="${cnt.count % 2 != 0}">
    		<tr class="uddarad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'uddarad';">
    		</c:if>      			
      			<td><c:out value="${instruktor.namn}"/></td>      			
      			<td><c:if test="${!empty instruktor.email}">
      					<a href="mailto:<c:out value="${instruktor.email}"/>"><c:out value="${instruktor.email}"/></a>
      				</c:if>
      				<c:if test="${empty instruktor.email}">
      					&nbsp
      				</c:if>
      		    </td>
      			<td><c:if test="${!empty instruktor.telefon}">
      					<c:out value="${instruktor.telefon}"/>
      				</c:if>
      				<c:if test="${empty instruktor.telefon}">
      					&nbsp
      				</c:if>
      		    </td>
      			<td><input type="button" 
      			           name="V<c:out value="${instruktor.iid}"/>" 
      			           class="tablebutton" 
      			           value="Visa"
   					       title="Visar all information om <c:out value="${instruktor.namn}"/>"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="openWindow('instruktorer.htm', '<c:out value="${instruktor.iid}"/>', 450, 330);">
   				</td>	
   			    <td><input type="button" 
      			           name="A<c:out value="${instruktor.iid}"/>" 
      			           class="tablebutton" 
      			           value="Ändra"
   					       title="Ändrar informationen i registret"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="window.location = 'instruktoredit.htm?recid=<c:out value="${instruktor.iid}"/>';">
   						   
   			    </td>
   			    <td><input type="button" 
      			           name="R<c:out value="${instruktor.iid}"/>" 
      			           class="tablebutton" 
      			           value="Ta bort"
   					       title="Tar bort <c:out value="${instruktor.namn}"/> ur registret"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="if (confirm('Skall du verkligen radera <c:out value="${instruktor.namn}"/> ur registret?') == 1) window.location = 'instruktorer.htm?radera=<c:out value="${instruktor.iid}"/>';">
   						   
   			    </td>		   	
      		</tr>
    	</c:forEach>
    </form>	
    </table>    
  </body>
</html>

