<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Kursregister</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
  	<script type="text/javascript">
	var multiArr = new Array();
  	var cntArr = new Array();  
  
  	function doLoad()
	{	
		<c:if test="${!empty modelMap.error}"> 
			alert('${modelMap.error}');
		</c:if>	
		<c:forEach items="${modelMap.typer}" var="typ" varStatus="cnt">
         	multiArr["A${typ.tid}"] = new Array();
         	cntArr["A${typ.tid}"] = 0;
         	multiArr.length = ${cnt.count};          	       				            	
		</c:forEach>
		<c:forEach items="${modelMap.nivaer}" var="niva" varStatus="cnt">
			if (${niva.nid != 0})
			{	
			    var ozz = new Object();		    
			    ozz.namn = "${niva.namn}";		    
			    ozz.varde = "${niva.nid}";
         		multiArr["A${niva.tid}"][cntArr["A${niva.tid}"]] = ozz;  	
         		cntArr["A${niva.tid}"]++;
         	}	         				            	
		</c:forEach>			
	}
	
	function doChange(varde)
	{
		searchform.niva.length = 0;
		searchform.niva.options.add(new Option('Välj nivå', '', true));
		searchform.beteckning.value = "";	
		for (var i = 0; i < multiArr["A" + varde].length; i++)
		{		    
			var ozz = multiArr["A" + varde][i];
			searchform.niva.options.add(new Option(ozz.namn, ozz.varde, false));
		}	
	}
	
  </script>
 </head>
  <body onload="doLoad();">
  

   <h1>Kurser</h1>  
   <form name="searchform" action="kurser.htm" method="post"> 
   <div id="menu">
    <ul>
       <li><a href="main.htm" target="_self" title="stang"><span>Stäng</span></a></li>	
       <li><a href="kursedit.htm?recid=0" target="_self" title="ny"><span>Ny</span></a></li>
       <li><a href="javascript:kursform._action.value='rapporter';kursform.submit();" target="_self" title="Rapporter"><span>Rapporter</span></a></li>       
       <li><a href="javascript:searchform.submit();" target="_self" title="sok"><span>Sök</span></a></li>
       <li>&nbsp;&nbsp;</li>
       <li>Beteckning <input type="text" size="12" name="beteckning" value="<c:out value="${modelMap.stBeteckning}"/>" onKeyPress="return submitenter(this,event)"></li>
       <li>&nbsp;&nbsp;
       	    <select name="kurstyp" onchange="javascript:doChange(this.value);">
       	    	<option value="" selected>Välj kurstyp</option>				
				<c:forEach items="${modelMap.typer}" var="typ" varStatus="cnt">										
					<option value="<c:out value="${typ.tid}"/>" <c:if test="${modelMap.stType == typ.tid}">selected</c:if>><c:out value="${typ.namn}"/></option>
				</c:forEach>
			</select>      
       </li>       
       <li>&nbsp;&nbsp;
       	    <select name="niva" onchange="javascript:searchform.beteckning.value = '';searchform.lokal.value = '';searchform.instruktor.value = '';searchform.submit();">
				<option value="" selected>Välj Nivå</option>
				 
				<c:forEach items="${modelMap.nivaer}" var="niva">	
					<c:if test="${niva.tid == modelMap.stType}">					
						<option value="<c:out value="${niva.nid}"/>" <c:if test="${modelMap.stLevel == niva.nid}">selected</c:if>><c:out value="${niva.namn}"/></option>
					</c:if>
				</c:forEach>
				
			</select>      
       </li>
       <li>&nbsp;&nbsp;
            <select name="lokal" onchange="javascript:searchform.beteckning.value = '';searchform.instruktor.value = '';searchform.niva.value = '';searchform.kurstyp.value = '';searchform.submit();">       
				<option value="" selected>Välj Lokal</option>
				<c:forEach items="${modelMap.lokaler}" var="lokal">						
					<option value="<c:out value="${lokal.lid}"/>" <c:if test="${modelMap.stLokal == lokal.lid}">selected</c:if>><c:out value="${lokal.lokalnamn}"/></option>
				</c:forEach>		
			</select>      
       </li>
       <li>&nbsp;&nbsp;
            <select name="instruktor" onchange="javascript:searchform.beteckning.value = '';searchform.lokal.value = '';searchform.niva.value = '';searchform.kurstyp.value = '';searchform.submit();">       
					<option value="" selected>Välj Instruktor</option>
			<c:forEach items="${modelMap.instruktorer}" var="instruktor">						
					<option value="<c:out value="${instruktor.iid}"/>" <c:if test="${modelMap.stInstructor == instruktor.iid}">selected</c:if>><c:out value="${instruktor.namn}"/></option>
			</c:forEach>		
			</select>      
       </li>
    </ul>
	</div>
	<br>
	<br>
	</form>
	<form name="kursform">    
	<!-- scrollable area -->
	<div style="height:510px; padding:6px; border-style:solid; border-width:1; overflow:auto; direction:ltr;">
	<div style="direction:ltr;">
	<table>
		<tr>
			<th align="left">Beteckning</th><th align="left">Kursnamn</th><th align="left">Plats</th><th align="left">Veckodag</th><th align="left">Tid</th><th align="left">Startdatum</th><th align="left">Status</th><th align="left" colspan=7>Instruktör</th>
		</tr>	    
    	<c:forEach items="${modelMap.kurser}" var="kursAll" varStatus="cnt">
    		<c:if test="${cnt.count % 2 == 0}">
    		<tr class="jamnrad" onDblClick="openWindow('kurser.htm', '<c:out value="${kursAll.kid}"/>', 400, 685);" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'jamnrad';">
    		</c:if>
    		<c:if test="${cnt.count % 2 != 0}">
    		<tr class="uddarad" onDblClick="openWindow('kurser.htm', '<c:out value="${kursAll.kid}"/>', 400, 685);" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'uddarad';">
    		</c:if>    			
      			<td><c:out value="${kursAll.beteckning}"/></td>
      			<td><c:out value="${kursAll.kursnamn}"/></td>
      			<td><c:out value="${kursAll.lokalNamn}"/></td>
      			<td><c:out value="${kursAll.veckodag}"/>ar</td>
      			<td><c:out value="${kursAll.starttid}"/></td>
      			<td><c:out value="${kursAll.startdatum}"/></td>
      			<td><c:out value="${kursAll.statusText}"/></td>
      			<td><c:out value="${kursAll.instruktorNamn}"/></td>
      			<td><input type="checkbox" name="rapport<c:out value="${kursAll.kid}"/>" <c:if test="${kursAll.status != 1}"> checked</c:if>></td>
   			    <td><input type="button" 
      			           name="A<c:out value="${kursAll.kid}"/>" 
      			           class="tablebutton" 
      			           value="Ändra"
   					       title="Ändrar informationen i registret"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="window.location = 'kursedit.htm?recid=<c:out value="${kursAll.kid}"/>';">
   						   
   			    </td>
   			     <td><input type="button" 
      			           name="D<c:out value="${kursAll.kid}"/>" 
      			           class="tablebutton" 
      			           value="Deltagare"
   					       title="Hanterar deltagare på kursen"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="window.location = 'elever.htm?kid=<c:out value="${kursAll.kid}"/>&bet=<c:out value="${kursAll.beteckning}"/>';">   						   
   			    </td>	
   			    <td><input type="button" 
      			           name="N<c:out value="${kursAll.kid}"/>" 
      			           class="tablebutton" 
      			           value="Närvaro"
   					       title="Visar närvaron för <c:out value="${kursAll.beteckning}"/>"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="window.location = 'nvaro.htm?kid=<c:out value="${kursAll.kid}"/>&bet=<c:out value="${kursAll.beteckning}"/>';">
   				</td>	
   				<td><input type="button" 
      			           name="P<c:out value="${kursAll.kid}"/>" 
      			           class="tablebutton" 
      			           value="Program"
   					       title="Kursprogram för <c:out value="${kursAll.beteckning}"/>"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="window.location='kursprogram.htm?recid=<c:out value="${kursAll.kid}"/>';">
   				</td>	
   			    <td><input type="button" 
      			           name="R<c:out value="${kursAll.kid}"/>" 
      			           class="tablebutton" 
      			           value="Ta bort"
   					       title="Tar bort <c:out value="${kursAll.beteckning}"/> ur registret"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="if (confirm('Skall du verkligen radera <c:out value="${kursAll.beteckning}"/> ur registret?') == 1) window.location = 'kurser.htm?radera=<c:out value="${kursAll.kid}"/>';">   						   
   			    </td>		   	
   			    
      		</tr>
    	</c:forEach>
    	<input type="hidden" name="_action">
    </form>	
    </table>    
    </div></div>
  </body>
</html>

