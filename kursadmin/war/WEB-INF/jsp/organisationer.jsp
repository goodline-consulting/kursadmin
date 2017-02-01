<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Organisationer</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />  	
  	<script language="javascript" src="basic.js"></script>
 </head>
  <body>
  

   <h1>Organisationer</h1>  
   <form name="searchform"> 
   <div id="menu">
    <ul>
       <li><a href="config.htm" target="_self" title="stang"><span>Stäng</span></a></li>	
       <li><a href="organisationedit.htm?recid=0" target="_self" title="ny"><span>Ny</span></a></li>              
    </ul>
	</div>
	<br>
	<br>
	</form>	
	<form name="organisationform">    
    
	<table>
		<tr>
			<th align="left">Organisation</th><th align="left">Organisationsnr.</th><th align="left">Bankgiro</th><th align="left" colspan=4>Plusgiro</th>
		</tr>	    
    	<c:forEach items="${modelMap.orgs}" var="org" varStatus="cnt">
    		<c:if test="${cnt.count % 2 == 0}">
				<tr class="jamnrad"  onmouseover="this.className = 'highrow';" onmouseout="this.className = 'jamnrad';">
    		</c:if>
    		<c:if test="${cnt.count % 2 != 0}">
    			<tr class="uddarad"  onmouseover="this.className = 'highrow';" onmouseout="this.className = 'uddarad';">    		
   			</c:if>      			
      			<td><c:out value="${org.orgnamn}"/></td>  
      			<td><c:if test="${!empty org.orgnr}">
      					<c:out value="${org.orgnr}"/>
      				</c:if>
      				<c:if test="${empty org.orgnr}">
      					&nbsp
      				</c:if>
      		    </td> 
      		    <td><c:if test="${!empty org.bankgiro}">
      					<c:out value="${org.bankgiro}"/>
      				</c:if>
      				<c:if test="${empty org.bankgiro}">
      					&nbsp
      				</c:if>
      		    </td>   
      		    <td><c:if test="${!empty org.plusgiro}">
      					<c:out value="${org.plusgiro}"/>
      				</c:if>
      				<c:if test="${empty org.plusgiro}">
      					&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
      				</c:if>
      		    </td>			
      			<td><input type="button" 
      			           name="V<c:out value="${org.oid}"/>" 
      			           class="tablebutton" 
      			           value="Visa"
   					       title="Visar all information om <c:out value="${org.orgnamn}"/>"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="openWindow('organisationer.htm', '<c:out value="${org.oid}"/>', 400, 360);">
   				</td>	
   			    <td><input type="button" 
      			           name="A<c:out value="${org.oid}"/>" 
      			           class="tablebutton" 
      			           value="Ändra"
   					       title="Ändrar informationen i registret"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="window.location = 'organisationedit.htm?recid=<c:out value="${org.oid}"/>';">
   						   
   			    </td>
   			    <td><input type="button" 
      			           name="R<c:out value="${org.oid}"/>" 
      			           class="tablebutton" 
      			           value="Ta bort"
   					       title="Tar bort <c:out value="${org.orgnamn}"/> ur registret"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="if (confirm('Skall du verkligen radera <c:out value="${org.orgnamn}"/> ur registret?') == 1) window.location = 'organisationer.htm?radera=<c:out value="${org.oid}"/>';">   						   
   			    </td>		   	
      		</tr>
    	</c:forEach>
    </table>    
   </form>	
  </body>
</html>

