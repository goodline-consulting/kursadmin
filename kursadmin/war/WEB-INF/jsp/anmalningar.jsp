<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Kursanmälningar</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
  	<script language="javascript">	
  		var ozzArr = new Array();
  	  	  
  	   <c:forEach items="${modelMap.anmalningar}" var="anm" varStatus="cnt">
	    	var ozz = new Object();		    
	    	ozz.aid = "${anm.aid}";		    
	    	ozz.email = "${anm.email}";
			ozzArr[${cnt.count - 1}] = ozz;
	   </c:forEach>
	
	
  	function checkFlera(email, aid)
  	{
  	  	var parStr = aid;
  	  	var dublett = false;
  		for (var i = 0; i < ozzArr.length; i++)
		{		    
			var ozz = ozzArr[i];
			
			if ((ozz.email == email) && (ozz.aid != aid))
			{	
				dublett = true;
				parStr += ',' + ozz.aid 				
			}	
		}	
		if (dublett)
		{
			if (confirm("Det finns fler anmälningar ifrån samma person. Skall dessa samfaktureras?"))
				return parStr;
		}
		return aid; 	  	
  	}
  	</script>
 </head>
  
  <body<c:if test="${!empty modelMap.error}"> onload="javascript:alert('${modelMap.error}');"</c:if>>        	  
   <h1>Kursanmälningar</h1>  
   <form name="searchform"> 
   <div id="menu">
    <ul>
       <li><a href="main.htm" target="_self" title="Stänger och återgår till huduvmenyn"><span>Stäng</span></a></li>	              
    </ul>
	</div><br><br>
	</form>
	<form name="anmform">    
	<table>
		<tr>
			<th align="left">Tidpunkt</th><th align="left">Kursbeteckning</th><th align="left">Namn</th><th align="left">Telefon</th><th colspan="4" align="left">Email</th>
		</tr>	    
    	<c:forEach items="${modelMap.anmalningar}" var="anm" varStatus="cnt">
    		<c:if test="${cnt.count % 2 == 0}">
    		<tr class="jamnrad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'jamnrad';">
    		</c:if>
    		<c:if test="${cnt.count % 2 != 0}">
    		<tr class="uddarad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'uddarad';">
    		</c:if>      			
      			<td><fmt:formatDate value="${anm.tidpunkt}" pattern="dd MMM HH:mm"/></td>  
      			<td><c:out value="${anm.beteckning}"/></td> 
      			<td><c:out value="${anm.fnamn}"/>  <c:out value="${anm.enamn}"/></td>
      			<td><c:out value="${anm.telefon}"/></td>
      			<td><c:out value="${anm.email}"/></td> 
   			    <td><input type="button" 
      			           name="A<c:out value="${anm.aid}"/>" 
      			           class="tablebutton" 
      			           value="Lägg till"
   					       title="Lägger till kursdeltagaren"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="window.location = 'anmalningar.htm?add=' + checkFlera('${anm.email}', ${anm.aid});">   						   
   			    </td>
   			     <td><input type="button" 
      			           name="M<c:out value="${anm.aid}"/>" 
      			           class="tablebutton" 
      			           value="Manuell"
   					       title="Hantera manuellt"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="window.location = 'anmalningar.htm?add=<c:out value="${anm.aid}"/>&manuell=yes';">   						   
   			    </td>
   			    <td><input type="button" 
      			           name="R<c:out value="${anm.aid}"/>" 
      			           class="tablebutton" 
      			           value="Ta bort"
   					       title="Tar bort denna kursanmälan"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="if (confirm('Skall du verkligen radera kursanmälan?') == 1) window.location = 'anmalningar.htm?radera=<c:out value="${anm.aid}"/>';">   						   
   			    </td>		   	
      		</tr>
    	</c:forEach>
    </form>	
    </table>    
  </body>
</html>

