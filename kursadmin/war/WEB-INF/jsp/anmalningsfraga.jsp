<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Personfråga</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
 </head>
  
  <body>        	  
   <h1>Välj person</h1>  
   <form name="searchform"> 
   <div id="menu">
    <ul>
       <li><a href="javascript:window.location='anmalningar.htm'" target="_self" title="Avbryter kursanmälan"><span>Avbryt</span></a></li>	              
    </ul>
	</div><br><br>
	<h3>Det finns <c:if test="${modelMap.antal > 1}">flera personer</c:if> <c:if test="${modelMap.antal == 1}">en annan person</c:if> med samma namn. <br>
	    Om aktuell person är någon av nedanstående så välj denna <br>
	    annars välj "skapa ny person"</h3>
	</form>
	<form name="anmform" method="post">    
	<table>
		<tr>
			<th align="left">Förnamn</th><th align="left">Efternamn</th><th align="left">Inskriven</th><th align="left">Telefon</th><th colspan="3" align="left">Email</th>
		</tr>	    
    	<c:forEach items="${modelMap.personer}" var="person" varStatus="cnt">
    		<c:if test="${cnt.count % 2 == 0}">
    		<tr class="jamnrad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'jamnrad';">
    		</c:if>
    		<c:if test="${cnt.count % 2 != 0}">
    		<tr class="uddarad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'uddarad';">
    		</c:if>      			
      			<td><c:out value="${person.fnamn}"/></td>  
      			<td><c:out value="${person.enamn}"/></td> 
      			<td><fmt:formatDate value="${person.inskriven}" dateStyle="full"/></td> 
      			<td><c:out value="${person.telefon}"/>&nbsp;</td>
      			<td><c:out value="${person.email}"/>&nbsp;</td>
      			
   			    <td><input type="button" 
      			           name="A<c:out value="${person.pid}"/>" 
      			           class="tablebutton" 
      			           value="Välj"
   					       title="Väljer denna person aom aktuell kursdeltagare"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="window.location = 'anmalningar.htm?vald=<c:out value="${person.pid}"/>&aid=<c:out value="${modelMap.kursanmalan.aid}"/>';">   						   
   			    </td>
      		</tr>
    	</c:forEach>
    	<tr><td colspan="4">Skapa ny person</td>
            <td><input type="button" 
      			           name="A0" 
      			           class="tablebutton" 
      			           value="Välj"
   					       title="Välj att skapa en ny kursdeltagare"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="window.location = 'anmalningar.htm?vald=0&aid=<c:out value="${modelMap.kursanmalan.aid}"/>';">   						   
   		    </td>
    </form>	
    </table>    
  </body>
</html>

