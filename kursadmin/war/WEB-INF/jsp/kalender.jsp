<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Kalender</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
  	
 </head>
  <body>
  

   <h1>Dagens datum <fmt:formatDate value="${modelMap.datum}" pattern="dd MMM yyyy"/></h1>  
   <form name="searchform"> 
   <div id="menu">
    <ul>
       <li><a href="main.htm" target="_self" title="stang"><span>Stäng</span></a></li>	
       <li><a href="kalenderedit.htm?recid=0" target="_self" title="ny"><span>Ny aktivitet</span></a></li>                     
    </ul>
	</div>
	<br><br>
	</form>
	<form name="kalenderform">    
	<table>
		<tr>
			<th align="left">Tidpunkt</th><th align="left">Aktivitet</th><th align="left">Status</th><th colspan="4" align="left">Info</th>
		</tr>	    
    	<c:forEach items="${modelMap.kalender}" var="kalender" varStatus="cnt">
    		<c:if test="${cnt.count % 2 == 0}">
    		<tr ondblClick="openWindow('kalender.htm', '<c:out value="${kalender.cid}"/>', 750, 380);" class="jamnrad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'jamnrad';">
    		</c:if>
    		<c:if test="${cnt.count % 2 != 0}">
    		<tr ondblClick="openWindow('kalender.htm', '<c:out value="${kalender.cid}"/>', 750, 380);" class="uddarad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'uddarad';">
    		</c:if>      			
	    		<td><fmt:formatDate value="${kalender.tidpunkt}" pattern="yyyy dd MMM HH:mm"/></td>      			
                <td><c:out value="${kalender.rubrik}"/></td>
                <td><c:out value="${kalender.altrubrik}"/></td>
                <td><c:out value="${kalender.summering}"/></td>           			      			
      			<td><input type="button" 
      			           name="V<c:out value="${kalender.cid}"/>" 
      			           class="tablebutton" 
      			           value="Visa"
   					       title="Visar all information om aktiviteten"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="openWindow('kalender.htm', '<c:out value="${kalender.cid}"/>', 750, 380);">
   				</td>	
   			    <td><input type="button" 
      			           name="A<c:out value="${kalender.cid}"/>" 
      			           class="tablebutton" 
      			           value="Ändra"
   					       title="Ändrar informationen i registret"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="window.location = 'kalenderedit.htm?recid=<c:out value="${kalender.cid}"/>';">
   						   
   			    </td>
   			    <td><input type="button" 
      			           name="R<c:out value="${kalender.cid}"/>" 
      			           class="tablebutton" 
      			           value="Ta bort"
   					       title="Tar bort akriviteten ur kalendern"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="if (confirm('Skall du verkligen radera aktiviteten ur registret?') == 1) window.location = 'kalender.htm?radera=<c:out value="${kalender.cid}"/>';">   						   
   			    </td>		   	
      		</tr>
    	</c:forEach>
    </form>	
    </table>    
  </body>
</html>

