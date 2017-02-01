<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  	<head>
  		<title>Rapporter</title>
  		<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  		<script language="javascript" src="basic.js"></script>
  	</head>
  	<body <c:if test="${!empty modelMap.error}"> onload="javascript:alert('${modelMap.error}');"</c:if>>
  		<h1>Rapporter</h1> 
  		<form name="searchform" method="post" action="rapporter.htm"> 
   			<div id="menu">
    		<ul>
    		<li><a href="kurser.htm" target="_self" title="stang"><span>Stäng</span></a></li>
       		<li><a href="javascript:searchform._action.value='ekonomi';searchform.submit();" target="_self" title="Ekonomirapport"><span>Ekonomi</span></a></li>
       		<li><a href="javascript:searchform._action.value='stim';searchform.submit();" target="_self" title="Ekonomirapport"><span>STIM</span></a></li>
       		<li><a href="javascript:searchform._action.value='elevuppf';searchform.submit();" target="_self" title="Elevuppföljning"><span>Elevuppföljning</span></a></li>
       		<li><a href="rapporter.htm?_action=kursanmalan" target="_blank" title="kursanmalan"><span>Kursanmälan Web</span></a></li>
       		<li><a href="rapporter.htm?_action=kursoversikt" target="_blank" title="kursoversikt"><span>Kursöversikt Web</span></a></li>         			                     
    		</ul>
			</div>
			<input type="hidden" name="_action">
		</form>
		<h3>Valda kurser för rapportering:</h3>
  		<table> 	
  			<tr>
				<th align="left">Beteckning</th><th align="left">Kursnamn</th><th align="left">Plats</th><th align="left">Veckodag</th><th align="left">Tid</th><th align="left">Startdatum</th><th align="left" colspan=7>Instruktör</th>
			</tr>		
   			<c:forEach items="${modelMap.kursLista}" var="kursAll" varStatus="cnt">
   				<c:if test="${cnt.count % 2 == 0}">
    				<tr class="jamnrad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'jamnrad';">
    			</c:if>
    			<c:if test="${cnt.count % 2 != 0}">
    				<tr class="uddarad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'uddarad';">
    			</c:if>    				
   				<td><c:out value="${kursAll.beteckning}"/></td>
   				<td><c:out value="${kursAll.kursnamn}"/></td>
   				<td><c:out value="${kursAll.lokalNamn}"/></td>
   				<td><c:out value="${kursAll.veckodag}"/>ar</td>
      			<td><c:out value="${kursAll.starttid}"/></td>
      			<td><c:out value="${kursAll.startdatum}"/></td>
      			<td><c:out value="${kursAll.instruktorNamn}"/></td>
   				</tr>   		
   			</c:forEach>  			
		</table>    					   		
  </body>
</html>
