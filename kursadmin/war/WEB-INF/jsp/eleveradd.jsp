<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Lägg till deltagare</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
 </head>
  <body>
  

   <h1>Lägg till deltagare på kurs <c:out value="${modelMap.beteckning}"/></h1>  
   <form name="searchform" action="elever.htm"> 
   <div id="menu">
    <ul>      
       <li><a href="elever.htm" title="stang"><span>Avbryt</span></a></li>
       <li><a href="javascript:personform._action.value='add';personform.submit();" target="_top" "title="spara"><span>Lägg till</span></a></li>
       <li><a href="javascript:searchform.submit();" target="_self" title="sok"><span>Sök</span></a></li>
       <li>&nbsp&nbsp</li>
       <li>Förnamn <input type="text" name="fnamn" onKeyPress="return submitenter(this,event)"></li>       
       <li>Efternamn <input type="text" name="enamn" onKeyPress="return submitenter(this,event)"></li>
       
    </ul>
	</div>
	<br><br>
	</form>
	<form name="personform">    
	<!-- scrollable area -->
	<div style="height:510px; width:520px; padding:6px; border-style:solid; border-width:1; overflow:auto; direction:ltr;">
	<div style="direction:ltr;"> 
	<table>

		<tr>
			<th align="left">Namn</th><th align="left" colspan="3">Email</th>
		</tr>	    
    	<c:forEach items="${modelMap.personer}" var="person" varStatus="cnt">
    		<c:if test="${cnt.count % 2 == 0}">
    		<tr class="jamnrad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'jamnrad';">
    		</c:if>
    		<c:if test="${cnt.count % 2 != 0}">
    		<tr class="uddarad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'uddarad';">
    		</c:if>      			
      			<td><c:out value="${person.fnamn}"/> <c:out value="${person.enamn}"/></td>
      			<td><c:out value="${person.email}"/></td>
      			<td>
      			<input <c:if test="${person.info == 'elev'}"> disabled </c:if> type="checkbox" name="person<c:out value="${person.pid}"/>" value="<c:out value="${person.fnamn}"/> <c:out value="${person.enamn}"/>">      			
      			</td> 
      			<td><c:if test="${person.info != 'elev'}">
      			<input type="button" 
      			       name="D<c:out value="${person.pid}"/>" 
      			       class="tablebutton" 
      			       value="Lägg till"
   				       title="Lägger till vald deltagare"
   					   onMouseOver="goLite(this.form.name, this.name)"
   					   onMouseOut="goDim(this.form.name, this.name)"
   					   onClick="javascript:personform.person<c:out value="${person.pid}"/>.checked=true;personform.submit();">
   				</c:if>	        			      			      		
  		    </td>		   	
      		</tr>
    	</c:forEach>
    	<input type="hidden" name="_action" value="add">
     </table>
     </form>
     </div></div>	    
  </body>
</html>

