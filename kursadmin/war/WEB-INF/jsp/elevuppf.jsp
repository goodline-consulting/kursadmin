<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  	<head>
  		<title>Elevuppf�ljning</title>
  		<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  		<script language="javascript" src="basic.js"></script>
  	</head>
  	<body <c:if test="${!empty modelMap.error}"> onload="javascript:alert('${modelMap.error}');"</c:if>>
  		<h1>Elevuppf�ljning</h1> 
  		<form name="searchform" method="post" action="rapporter.htm"> 
   			<div id="menu">
    		<ul>
    		<li><a href="rapporter.htm" target="_self" title="stang"><span>St�ng</span></a></li>       		     			                     
    		</ul>
			</div>
			<input type="hidden" name="_action">
		</form>
		<h3>Av <c:out value="${modelMap.Antal}"/> Elever p� <c:out value="${modelMap.kursAll.beteckning}"/> har <c:out value="${modelMap.Vidare}"/> st (<c:out value="${modelMap.Procent}"/>%) g�tt vidare </h3>
  		<table> 	
  			<tr>
				<th align="left">Namn</th><th align="left">Kurser efter <c:out value="${modelMap.kursAll.beteckning}"/></th>
			</tr>		
   			<c:forEach items="${modelMap.elever}" var="elev" varStatus="cnt">
   				<c:if test="${cnt.count % 2 == 0}">
    				<tr class="jamnrad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'jamnrad';">
    			</c:if>
    			<c:if test="${cnt.count % 2 != 0}">
    				<tr class="uddarad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'uddarad';">
    			</c:if>    				
   				<td><c:out value="${elev.namn}"/></td> 
   				<td><c:out value="${elev.info}"/></td>   				
   				</tr>   		
   			</c:forEach>  			
		</table>    					   		
  </body>
</html>
