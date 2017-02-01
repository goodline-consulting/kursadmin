<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Kursprogram</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
 </head>
  
  <body<c:if test="${!empty modelMap.msg}"> onload="javascript:alert('${modelMap.msg}');"</c:if>>        	  
   <h1>Kursprogram <c:out value="${modelMap.kurs.beteckning}"/></h1>  
   <form name="searchform" method="post"> 
   <div id="menu">
    <ul>
       <li><a href="kurser.htm" target="_self" title="stang"><span>Avbryt</span></a></li>
       <c:if test="${modelMap.antalpp > 0}">
       <li><a href="javascript:openWindow2('kursprogram.htm?kopiera=yes', 480, 450);" target="_self" title="Kopiera"><span>Kopiera</span></a></li>
       </c:if>
       <li><a href="javascript:openWindow2('kursprogram.htm?publicera=yes', 640, 800);" target="_self" title="Publicera"><span>Publicera</span></a></li>	
       <li><a href="javascript:programform.submit();" target="_self" title="Spara"><span>Spara</span></a></li>
       <li>&nbsp;&nbsp;Programpunkter:
            <select name="punkter">       
					<option value="" <c:if test="${modelMap.senastvald == 0}">selected</c:if>>Välj Programpunkt</option>
			<c:forEach items="${modelMap.punkter}" var="punkt">						
					<option value="<c:out value="${punkt.ppid}"/>" <c:if test="${modelMap.senastvald == punkt.ppid}">selected</c:if>><c:out value="${punkt.namn}"/></option>
			</c:forEach>		
			</select>      
       </li>              
    </ul>
	</div>
	<br><br>
	</form>
	<form name="programform" method="post" action="kursprogram.htm">    
	<table class="tiny">
		<tr>
			<th colspan="2" align="left">Kurstillfällen</th><th colspan="4" align="left">Programpunkter</th>
		</tr>	
		    
    	<c:forEach items="${modelMap.kurstillf}" var="dag" varStatus="cnt">
    		<tr>   
    		<td><b>
    		<fmt:formatDate value="${dag}" pattern="dd MMM yyyy"/>
    		</b></td>
    		<td>
    		
    			<input type="button" 
      			           name="A<c:out value="${cnt.count}"/>" 
      			           class="tablebutton" 
      			           value="Lägg till"
   					       title="lägger till vald programpunkt"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="if (searchform.punkter.value != '') window.location = 'kursprogram.htm?add=<c:out value="${cnt.count}"/>&ppid=' + searchform.punkter.value; else alert('du måste först välja en programpunkt!');">
   				
	   								      			    	
    		</td>
    		<c:set var="ppcnt" value="0"/>
    		<c:forEach items="${modelMap.program}" var="program" varStatus="idx">
    			<c:if test="${cnt.count == program.seq}">    				
    				<c:set var="ppcnt" value="${ppcnt + 1}"/>
    				<c:if test="${ppcnt > 1}">
    					<tr>
    				</c:if>	
    				<td align="right" <c:if test="${ppcnt > 1}"> colspan="3"</c:if>>
    				<c:out value="${program.namn}"></c:out>
    				</td>
    				<td align="right">
    				<c:if test="${ppcnt == 1 && modelMap.antal > 1 && modelMap.antalpp > 1}">
    					<input type="image" 
      			           name="U<c:out value="${cnt.count}"/>" 
      			           src="images/u_hand.gif"
      			           title="flyttar vald programpunkt uppåt"
   						   <c:if test="${cnt.count == 1}"> disabled</c:if>		
   						   onClick="window.location = 'kursprogram.htm?upp=${program.seq}';return false;"
   						   >
   							&nbsp;   
   						<input type="image" 
      			           name="D<c:out value="${cnt.count}"/>" 
      			           src="images/d_hand.gif"
      			           title="flyttar vald programpunkt nedåt"
   						    <c:if test="${cnt.count == modelMap.antal}"> disabled</c:if>		
   						   onClick="window.location = 'kursprogram.htm?ned=${program.seq}';return false;"
   						   ></input>	
   					</c:if>	   
    				<input type="button" 
      			           name="R<c:out value="${program.ppid}"/>R<c:out value="${program.seq}"/>" 
      			           class="tablebutton" 
      			           value="Ta bort"
   					       title="Tar bort <c:out value="${program.namn}"/> från programmet"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="window.location = 'kursprogram.htm?radera=<c:out value="${program.seq}"/>&ppid=<c:out value="${program.ppid}"/>';">   						   
   			    	</td>
   			    	</tr>   			    		
    			</c:if>
    		</c:forEach>
    		</td></tr>	
    	</c:forEach>    	
    	<input type="hidden" name="_action">
    </form>	
    </table>    
  </body>
</html>

