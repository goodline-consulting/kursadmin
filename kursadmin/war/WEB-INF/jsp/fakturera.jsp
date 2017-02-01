<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Fakturera</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />  	
  	<script language="javascript" src="basic.js"></script>
 </head>
  <body>
  

   <h1>Fakturera ${modelMap.person.fnamn} ${modelMap.person.enamn}</h1>  
   <form name="searchform"> 
   <div id="menu">
    <ul>
       <li><a href="${modelMap.target}" target="_self" title="avbryt"><span>Avbryt</span></a></li>	
       <li><a href="javascript:fakturaform.submit();" target="_self" title="fakturera"><span>Fakturera</span></a></li>              
    </ul>
	</div>
	<br>
	<br>
	<h3><i>${modelMap.person.fnamn} ${modelMap.person.enamn}</i> har följande ofakturerade kurser:<br></h3>
	(Markera de kurser som skall ingå i denna faktura)
	</form>	
	<form name="fakturaform" method="post">       
	<table>
		<tr>
			<th align="left">Beteckning</th><th align="left">Kurs</th></th><th align="left">Starttid</th><th align="left">Lokal</th><th align="left" colspan=4>Fakturera</th>
		</tr>	    
    	<c:forEach items="${modelMap.kurser}" var="kurs" varStatus="cnt">
    		<c:if test="${cnt.count % 2 == 0}">
				<tr class="jamnrad"  onmouseover="this.className = 'highrow';" onmouseout="this.className = 'jamnrad';">
    		</c:if>
    		<c:if test="${cnt.count % 2 != 0}">
    			<tr class="uddarad"  onmouseover="this.className = 'highrow';" onmouseout="this.className = 'uddarad';">    		
   			</c:if>      			
      			<td><c:out value="${kurs.beteckning}"/></td>  
      			<td><c:out value="${kurs.kursnamn}"/></td>  
      			<td><c:out value="${kurs.veckodag}"/> <c:out value="${kurs.startdatum}"/> <c:out value="${kurs.starttid}"/></td>
      			<td><c:out value="${kurs.lokalNamn}"/></td>    
      		    <td><input type="checkbox" value="<c:out value="${kurs.kid}"/>" name="kurs<c:out value="${kurs.kid}"/>" <c:if test="${kurs.typavrabatt == 1}">checked</c:if>></td>	   			    	   	
      		</tr>
    	</c:forEach>
    </table>    
   </form>	
  </body>
</html>

