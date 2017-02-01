<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Programpunkter</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
 </head>
  
  <body<c:if test="${!empty modelMap.error}"> onload="javascript:alert('${modelMap.error}');"</c:if>>        	  
   <h1>Programpunkter</h1>  
   <form name="searchform" method="post"> 
   <div id="menu">
    <ul>
       <li><a href="config.htm" target="_self" title="Stänger och återgår till huduvmenyn"><span>Stäng</span></a></li>	
       <li><a href="programpunktedit.htm?recid=0" target="_self" title="Skapar en ny programpunkt"><span>Ny</span></a></li>  
       <li><a href="javascript:searchform.submit();" target="_self" title="sok"><span>Sök</span></a></li>
       <li>&nbsp;</li>
       <li><input type="text" size="12" name="sokmall" onKeyPress="return submitenter(this,event)"></li>
       <li>&nbsp;&nbsp;
       	    <select name="kurstyp">
       	    	<option value="0" selected>Välj kurstyp</option>				
				<c:forEach items="${modelMap.typer}" var="typ" varStatus="cnt">										
					<option value="<c:out value="${typ.tid}"/>"><c:out value="${typ.namn}"/></option>
				</c:forEach>
			</select>      
       </li>                   
       <li><input type="checkbox" name="aktuella" <c:if test="${modelMap.aktuella}"> checked </c:if>>Aktuella</li>
    </ul>
	</div><br><br>
	</form>
	<form name="progform">    
	<table>
		<tr>
			<th align="left">Programpunkt</th><th align="left">Kurstyp</th><th colspan="4" align="left">Beskrivning</th>
		</tr>	    
    	<c:forEach items="${modelMap.programPunkter}" var="punkt" varStatus="cnt">
    		<c:if test="${cnt.count % 2 == 0}">
    		<tr class="jamnrad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'jamnrad';">
    		</c:if>
    		<c:if test="${cnt.count % 2 != 0}">
    		<tr class="uddarad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'uddarad';">
    		</c:if>      			
      			<td><c:out value="${punkt.namn}"/></td>  
      			<td><c:out value="${punkt.kurstyp}"/></td> 
      			<td><c:out value="${punkt.info}"/></td>
      			<td><input type="button" 
      			           name="V<c:out value="${punkt.ppid}"/>" 
      			           class="tablebutton" 
      			           value="Visa"
   					       title="Visar all information om <c:out value="${punkt.namn}"/>"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="openWindow('programpunkter.htm', '<c:out value="${punkt.ppid}"/>', 550, 280);">
   				</td>	
   			    <td><input type="button" 
      			           name="A<c:out value="${punkt.ppid}"/>" 
      			           class="tablebutton" 
      			           value="Ändra"
   					       title="Ändrar informationen i registret"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="window.location = 'programpunktedit.htm?recid=<c:out value="${punkt.ppid}"/>';">   						   
   			    </td>
   			    <td><input type="button" 
      			           name="R<c:out value="${punkt.ppid}"/>" 
      			           class="tablebutton" 
      			           value="Ta bort"
   					       title="Tar bort <c:out value="${punkt.namn}"/> ur registret"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="if (confirm('Skall du verkligen radera programpunkten?') == 1) window.location = 'programpunkter.htm?radera=<c:out value="${punkt.ppid}"/>';">   						   
   			    </td>		   	
      		</tr>
    	</c:forEach>
    </form>	
    </table>    
  </body>
</html>

