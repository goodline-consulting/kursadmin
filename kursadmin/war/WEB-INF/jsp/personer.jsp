<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Personregister</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
 </head>
  <body>
  

   <h1>Personer</h1>  
   <form name="searchform" method="post"> 
   <div id="menu">
    <ul>
       <c:if test="${modelMap.fromHome == false}">
       	<li><a href="main.htm" target="_self" title="stang"><span>Stäng</span></a></li>
       </c:if>	
       <c:if test="${modelMap.fromHome == true}">
       	<li><a href="teachers.htm" target="_self" title="stang"><span>Stäng</span></a></li>
       </c:if>	
       <c:if test="${modelMap.fromHome == false}">
       	<li><a href="personedit.htm?recid=0" target="_self" title="ny"><span>Ny</span></a></li>
       		<li><a href="personer.htm?obetalda=yes" target="_self" title="Visar person med obetalda fakturor"><span>Obet.Fakt</span></a></li>
       		<li><a href="personer.htm?ofakt=yes" target="_self" title="Visar person med ofakturerade kurser"><span>OFakt.kurser</span></a></li>
       </c:if>	       
       <li><a href="javascript:searchform.submit();" target="_self" title="sok"><span>Sök</span></a></li>
       <li>&nbsp&nbsp</li>
       <li>Förnamn <input type="text" name="fnamn" value="${modelMap.fnamn}" onKeyPress="return submitenter(this,event)"></li>       
       <li>Efternamn <input type="text" name="enamn" value="${modelMap.enamn}" onKeyPress="return submitenter(this,event)"></li>
       
    </ul>
	</div>
	<br>
	<br>
	</form>
	<form name="personform">   
	<!-- scrollable area -->
	<div style="height:510px; padding:6px; border-style:solid; border-width:1; overflow:auto; direction:ltr;">
	<div style="direction:ltr;"> 
	<table>

		<tr>
			<th align="left">Förnamn</th><th align="left">Efternamn</th><th align="left">Kundnr</th><th align="left">Email</th><th align="left" colspan=4>Telefon</th>
		</tr>	    
    	<c:forEach items="${modelMap.persons}" var="person" varStatus="cnt">
    		<c:if test="${cnt.count % 2 == 0}">
    		<tr class="jamnrad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'jamnrad';">
    		</c:if>
    		<c:if test="${cnt.count % 2 != 0}">
    		<tr class="uddarad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'uddarad';">
    		</c:if>       			
      			<td><c:out value="${person.fnamn}"/></td>
      			<td><c:out value="${person.enamn}"/></td>
      			<td><c:out value="${person.pid}"/></td>
      			<td><c:if test="${!empty person.email}">
      					<a href="mailto:<c:out value="${person.email}"/>"><c:out value="${person.email}"/></a>
      				</c:if>
      				<c:if test="${empty person.email}">
      					&nbsp
      				</c:if>
      		    </td>
      			<td><c:if test="${!empty person.telefon}">
      					<c:out value="${person.telefon}"/>
      				</c:if>
      				<c:if test="${empty person.telefon}">
      					&nbsp
      				</c:if>
      		    </td>
      			<td><input type="button" 
      			           name="V<c:out value="${person.pid}"/>" 
      			           class="tablebutton" 
      			           value="Visa"
   					       title="Visar all information om <c:out value="${person.fnamn}"/> <c:out value="${person.enamn}"/>"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="openWindow('personer.htm', '<c:out value="${person.pid}"/>', 600, 600);">
   				</td>	
   			    <td><input type="button" 
      			           name="A<c:out value="${person.pid}"/>" 
      			           class="tablebutton" 
      			           value="Ändra"
   					       title="Ändrar informationen i registret"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="window.location = 'personedit.htm?recid=<c:out value="${person.pid}"/>';">
   						   
   			    </td>
   			    <c:if test="${modelMap.fromHome == false}">
   			    <td><input type="button" 
      			           name="F<c:out value="${person.pid}"/>" 
      			           class="tablebutton" 
      			           value="Fakturor"
   					       title="Visar <c:out value="${person.fnamn}"/> <c:out value="${person.enamn}"/>:s fakturor"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="window.location = 'faktura.htm?person=<c:out value="${person.pid}"/>';">  						   
   			    </td>	
   			    <td><input type="button" 
      			           name="R<c:out value="${person.pid}"/>" 
      			           class="tablebutton" 
      			           value="Ta bort"
   					       title="Tar bort <c:out value="${person.fnamn}"/> <c:out value="${person.enamn}"/> ur registret"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="if (confirm('Skall du verkligen radera <c:out value="${person.fnamn}"/> <c:out value="${person.enamn}"/> ur registret?') == 1) window.location = 'personer.htm?radera=<c:out value="${person.pid}"/>';">
   						   
   			    </td>	
   			    	   
   			    </c:if>	
      		</tr>
    	</c:forEach>
    	</table> 
    	</div>
    	</div>
    </form>	
      
  </body>
</html>

