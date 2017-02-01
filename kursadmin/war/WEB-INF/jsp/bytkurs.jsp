<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Byt Kurs</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
 </head>
  <body <c:if test="${modelMap.klar == 0}">
  		onload="window.opener.document.elevform._action.value='move';window.opener.document.elevform._item.value=<c:out value="${modelMap.pid}"/>;window.opener.document.elevform.submit();window.close();"
  		</c:if>
  >
  

   <h1>Byt kurs på <c:out value="${modelMap.namn}"/></h1>  
   <form name="searchform"> 
   <div id="menu">
    <ul>

       <li><a href="javascript:window.close()" target="_self" title="stang"><span>Avbryt</span></a></li>       
    </ul>
	</div>
	<br><br>
	</form>
	<form name="bytkursform">   
	<!-- scrollable area -->
	<div style="height:310px; padding:6px; border-style:solid; border-width:1; overflow:auto; direction:ltr;">
	<div style="direction:ltr;">  
	<table>
		<tr>
			<th align="left">Beteckning</th><th align="left">Kursnamn</th><th align="left">Plats</th><th align="left" colspan="2">Veckodag</th>
		</tr>	    
    	<c:forEach items="${modelMap.kurser}" var="kursAll">
    		<c:if test="${modelMap.klar != kursAll.kid}">
    		<tr onmouseover="this.className = 'highrow';" onmouseout="this.className = '';">    			
      			<td><c:out value="${kursAll.beteckning}"/></td>  
      			<td><c:out value="${kursAll.kursnamn}"/></td>
      			<td><c:out value="${kursAll.lokalNamn}"/></td>
      			<td><c:out value="${kursAll.veckodag}"/></td>			    			      			
      			<td><input type="button" 
      			           name="V<c:out value="${kursAll.kid}"/>" 
      			           class="tablebutton" 
      			           value="Välj"
   					       title="Flyttar kursdeltagaren till angiven kurs"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="javascript:bytkursform._item.value=<c:out value="${kursAll.kid}"/>;bytkursform.submit();">
   				</td>	   			    		   	
      		</tr>
      		</c:if>
    	</c:forEach>
    	
    </table>
    </div></div>      		
    	<input type="hidden" name="_item" value="">   
    </form> 
  </body>
</html>

