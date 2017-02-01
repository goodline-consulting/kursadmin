<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Användarregister</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
 </head>
  <body>
  
   <h1>Anändare</h1>  
   <form name="searchform"> 
   <div id="menu">
    <ul>
       <li><a href="config.htm" target="_self" title="stang"><span>Stäng</span></a></li>
       <li><a href="anvandaredit.htm?username=0" target="_self" title="ny"><span>Ny</span></a></li>              
    </ul>
	</div>
	<br><br>
	</form>
	<form name="personform">    
	<table>
		<tr>
			<th align="left">Användarnamn</th><th align="left">Roll</th><th colspan=2></th>
		</tr>	    
    	<c:forEach items="${modelMap.anvandare}" var="anvandare" varStatus="cnt">
    		<c:if test="${cnt.count % 2 == 0}">
    		<tr class="jamnrad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'jamnrad';">
    		</c:if>
    		<c:if test="${cnt.count % 2 != 0}">
    		<tr class="uddarad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'uddarad';">
    		</c:if>      			
      			<td><c:out value="${anvandare.namn}"/></td>      			
				<td><c:out value="${anvandare.roll}"/></td>      		
      		    </td>      			
   			    <td><input type="button" 
      			           name="A<c:out value="${anvandare.namn}"/>" 
      			           class="tablebutton" 
      			           value="Ändra"
   					       title="Ändrar informationen i registret"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="window.location = 'anvandaredit.htm?username=<c:out value="${anvandare.namn}"/>';">
   						   
   			    </td>
   			    <td><input type="button" 
      			           name="R<c:out value="${anvandare.namn}"/>" 
      			           class="tablebutton" 
      			           value="Ta bort"
   					       title="Tar bort <c:out value="${anvandare.namn}"/> ur registret"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="if (confirm('Skall du verkligen radera <c:out value="${anvandare.namn}"/> ur registret?') == 1) window.location = 'anvandare.htm?radera=<c:out value="${anvandare.namn}"/>';">
   						   
   			    </td>		   	
      		</tr>
    	</c:forEach>
    </form>	
    </table>    
  </body>
</html>

