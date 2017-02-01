<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Kopiera program</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
 </head>
  <body<c:if test="${!empty modelMap.msg}"> onload="javascript:alert('${modelMap.msg}');window.close();"</c:if>>  
 

   <h1>Kopiera kursprogram</h1>  
   <form name="searchform"> 
   <div id="menu">
    <ul>
          
       <li><a href="javascript:window.close()" target="_self" title="stang"><span>Avbryt</span></a></li> 
       <li><a href="javascript:if (searchform.pub.checked)kopierakursform.kopiera.value='publicera';kopierakursform.submit();" target="_self" title="kopiera"><span>Kopiera</span></a></li>
        <li>&nbsp;<input type="checkbox" name="pub" value="publicera"><span>Publicera</span></li>    
    </ul>
	</div>
	<br><br>
	</form>
	<form name="kopierakursform">   
	<!-- scrollable area -->
	<div style="height:310px; padding:6px; border-style:solid; border-width:1; overflow:auto; direction:ltr;">
	<div style="direction:ltr;">  
	<table>
		<tr>
			<th align="left">Beteckning</th><th align="left">Kursnamn</th><th align="left">Plats</th><th align="left">Veckodag</th><th>Kopiera</th>
		</tr>	    
    	<c:forEach items="${modelMap.kurser}" var="kursAll">
    		<c:if test="${kursAll.kid != modelMap.skippa}">
    		<tr onmouseover="this.className = 'highrow';" onmouseout="this.className = '';">    			
      			<td><c:out value="${kursAll.beteckning}"/></td>  
      			<td><c:out value="${kursAll.kursnamn}"/></td>
      			<td><c:out value="${kursAll.lokalNamn}"/></td>
      			<td><c:out value="${kursAll.veckodag}"/></td>
      			<td><input type="checkbox" value="<c:out value="${kursAll.kid}"/>" name="copy<c:out value="${kursAll.kid}"/>" checked></input></td>	   			    		   	
      		</tr>
      		</c:if>
    	</c:forEach>    	
    </table>
    </div></div>      		
    	<input type="hidden" name="kopiera" value="">   
    </form> 
  </body>
</html>

