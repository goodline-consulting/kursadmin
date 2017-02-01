<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Närvaro</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
  	
 </head>
  <body>
  

   <h1>Närvarolista på kurs <c:out value="${modelMap.kursAll.beteckning}"/><br> (<c:out value="${modelMap.kursAll.lokalNamn}"/> <c:out value="${modelMap.kursAll.veckodag}"/>ar <c:out value="${modelMap.kursAll.starttid}"/>)</h1>  
   <form name="searchform"> 
   <div id="menu">
    <ul>       
       <li><a href="javascript:nvaroform.reset();" target="_self" title="reset"><span>Återställ</span></a></li>
       <li><a href="javascript:nvaroform._action.value='backa';nvaroform.submit();" target="_self" title="stang"><span>Avbryt</span></a></li>
       <li><a href="javascript:nvaroform._action.value='spara';nvaroform.submit();" target="_self" title="spara"><span>Spara</span></a></li>
              
    </ul>
	</div>
	<br><br>
	</form>
	<form name="nvaroform"  method="post" action="nvaro.htm">    
	
	<table border=1>
		<tr>
		<th align="left">${modelMap.antal} deltagare</th>
		<c:forEach items="${modelMap.kursTillf}" var="kursTillf" varStatus="cnt">
			<th align="right"><u><c:out value="${kursTillf.day}"/></u><br><c:out value="${kursTillf.month}"/></th>
		</c:forEach>	
		</tr>	    
    	<c:forEach items="${modelMap.elever}" var="elev" varStatus="cnt">
    		<tr onmouseover="this.className = 'highrow';" onmouseout="this.className = '';">        				
      			<td><c:out value="${elev.namn}"/>
      			<c:if test="${elev.betalt == elev.pris}"> *</c:if>
      			<c:if test="${(elev.pris == 1) && (elev.betalt == 0)}"> $</c:if>		
      			</td>
      			<c:forEach items="${modelMap.kursTillf}" varStatus="idx" var="kursTillf">
      				<td><input 
      				<c:if test="${elev.nvaro[idx.count - 1] == true}"> checked </c:if> 
      				type="checkbox" name="T<c:out value="${idx.count}"/>:<c:out value="${elev.pid}"/>" value="<c:out value="${elev.pid}"/>"></td>
      			</c:forEach> 
      		</tr>
    	</c:forEach>    
    </table>    
    <input type="hidden" name="_action" value="">
   </form>	
  </body>
</html>

