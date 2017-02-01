<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Lägg till personer</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
 </head>
  <body>  
   <h1>Lägg till Personer</h1>  
   <form name="searchform" method="post" action="mailurval.htm?personer=no" enctype="multipart/form-data"> 
   <div id="menu">
    <ul>
       <li><a href="mailurval.htm" target="_self" title="stang"><span>Avbryt</span></a></li>
       <li><a href="javascript:document.personform.submit();" target="_self" title="kalle"><span>Lägg till markerade</span></a></li>              
       <li><a href="javascript:searchform.submit();" target="_self" title="sok"><span>Sök</span></a></li>
       <li>&nbsp&nbsp</li>
       <li>Förnamn <input type="text" name="fnamn" onKeyPress="return submitenter(this,event)"></li>       
       <li>Efternamn <input type="text" name="enamn" onKeyPress="return submitenter(this,event)"></li>       
    </ul>
	</div><br><br>
	</form>
	<form name="personform" method="post" action="mailurval.htm" enctype="multipart/form-data">   
	<!-- scrollable area -->
	<div style="height:510px; padding:6px; border-style:solid; border-width:1; overflow:auto; direction:ltr;">
	<div style="direction:ltr;"> 
	<table>
		<tr>
    	<c:forEach items="${modelMap.personer}" var="person" varStatus="cnt">  		
    		<c:if test="${(cnt.count - 1) % 4 == 0}"></tr><tr></c:if>           			
      			<td onmouseover="this.className = 'highrow';" onmouseout="this.className = '';">      			      			      			
      				<input type="checkbox" name="add<c:out value="${person.pid}"/>">
					<c:out value="${person.namn}"/>
					<input type="hidden" name="N<c:out value="${person.pid}"/>" value="<c:out value="${person.namn}"/>">
   					<input type="hidden" name="E<c:out value="${person.pid}"/>" value="<c:out value="${person.email}"/>">                   
      		    </td>      		     
   		</c:forEach>       		
    	</tr>
    	</table>     	
    	</div>
    	</div>    		   
    </form>	      
  </body>
</html>

