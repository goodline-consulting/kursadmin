<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Mailutskick</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
  	<script language="javascript">
  		var multiArr = new Array();
     	var cntArr = new Array();  
  
  		function doLoad()
		{		
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
			mailurvalform.niva.length = 0;
			mailurvalform.niva.options.add(new Option('Välj nivå', '0', true));
				
			for (var i = 0; i < multiArr["A" + varde].length; i++)
			{		    
				var ozz = multiArr["A" + varde][i];
				mailurvalform.niva.options.add(new Option(ozz.namn, ozz.varde, false));
			}	
		}

  		function validateForm()
  		{
  			if (mailurvalform.rubrik.value == "")
  			{
  				alert("Rubriken får inte vara tom");
  				return false;
  			}
  			if (mailurvalform.body.value == "")
  			{
  				alert("Mailet måste innehålla någon text");
  				return false;
  			}
  			for (var i = 0; i < document.forms[0].elements.length; i++)
  			{
  				var e = document.forms[0].elements[i];  				
  				if (e.type == "checkbox")
  				{
  					hidePageElement('rubbet')
  					showPageElement('div_wait')	
					return true;
  				}	 				  			
  			}
  			alert("Du måste välja någon eller några mottagare av mailet");
  			return false;
		}  	
  	</script>
 </head>
  <body onload="doLoad();">
  
  <form name="mailurvalform" method="post" action='mailurval.htm' enctype="multipart/form-data"> 
   		<font color="darkblue" size="5"><b>Välj mottagare av mailutskick <c:if test="${modelMap.antal > 0}">(<c:out value="${modelMap.antal}"/> valda)</c:if></b>&nbsp;&nbsp;&nbsp; </font>  
   		<input type="radio" name="laggtill" value="laggtill" <c:if test="${modelMap.laggtill == true}"> checked</c:if>>Lägg till
		<input type="radio" name="laggtill" value="ersatt" <c:if test="${modelMap.laggtill == false}"> checked</c:if>>Ersätt
  		<br></br>
   
   <div id="menu">
    <ul>
       <li><a href="main.htm" target="_self" title="stang"><span>Avbryt</span></a></li>
       <li><a href="javascript:mailurvalform._action.value='bort';mailurvalform.submit()" target="_self" title="radera"><span>Ta bort markerade</span></a></li>
       <li><a href="javascript:mailurvalform.personer.value='yes';mailurvalform.submit()"><span>Personer</span></a></li>
       <li><a href="javascript:mailurvalform.personer.value='obetalda';mailurvalform.submit()"><span>Ej betalt</span></a></li>       
       <li><a href="javascript:if(validateForm()) {mailurvalform._action.value='submit';mailurvalform.submit();}" target="_self" title="skicka"><span>Skicka</span></a></li>
       <li>&nbsp;&nbsp;
       	    <select name="kurstyp" onchange="javascript:doChange(this.value);">	
       	    	<option value="" selected>Välj Kurstyp</option>			
				<c:forEach items="${modelMap.typer}" var="typ" varStatus="cnt">										
					<option value="<c:out value="${typ.tid}"/>"><c:out value="${typ.namn}"/></option>
				</c:forEach>
			</select>      
      </li>   
       <li>&nbsp;
       	    <select name="niva" onchange="mailurvalform.submit();">
				<option value="" selected>Välj Nivå</option>
				<!--  
				<c:forEach items="${modelMap.nivaer}" var="niva">						
						<option value="<c:out value="${niva.nid}"/>"><c:out value="${niva.namn}"/></option>
				</c:forEach>
				-->
			</select>      
       </li>
       <li>&nbsp;
            <select name="lokal" onchange="mailurvalform.submit();">       
					<option value="" selected>Välj Lokal</option>
			<c:forEach items="${modelMap.lokaler}" var="lokal">						
					<option value="<c:out value="${lokal.lid}"/>"><c:out value="${lokal.lokalnamn}"/></option>
			</c:forEach>		
			</select>      
       </li>
       <li>&nbsp;
            <select name="instruktor" onchange="mailurvalform.submit();">       
					<option value="" selected>Välj Instruktör</option>
					
			<c:forEach items="${modelMap.instruktorer}" var="instruktor">						
					<option value="<c:out value="${instruktor.iid}"/>"><c:out value="${instruktor.namn}"/></option>
			</c:forEach>
					<option value="0">Instruktörer</option>		
			</select>      
       </li>      
       <li>&nbsp;
            <select name="kurs" onchange="mailurvalform.submit();">       
					<option value="" selected>Välj Kurs</option>
					<option value="0">Ingen kurs</option>
			<c:forEach items="${modelMap.kurser}" var="kurs">						
					<option value="<c:out value="${kurs.kid}"/>"><c:out value="${kurs.beteckning}"/></option>
			</c:forEach>		
			</select>      
       </li>
       <li>&nbsp;       
			<input type="checkbox" name="obetalda" value="obetalda" <c:if test="${modelMap.obetalda == true}"> checked</c:if>>	Obetalda		
	  </li>	
       <li>&nbsp;       
			
	  </li>		
    </ul>
    
	</div><br><br>	
	<div id="div_wait" style="background: none repeat scroll 0% 0% white; padding: 8px; position: fixed; left: 25%; top:40%; margin-left:10%; border: 2px solid rgb(34, 102, 170); display: none;">
		<font color="grey" size="4">Vänta medan mailen skickas... </font><img src="images/wait30trans.gif"/>
	</div>
	<div id="rubbet">
	<div style="height:210px;  padding:6px; border-style:solid; border-width:1; overflow:auto; direction:ltr;">
	<div style="direction:ltr;"> 
	
	<table>
			
		<tr>	
    	<c:forEach items="${modelMap.mottagarLista}" var="mailMottagare" varStatus="cnt">
    		<c:if test="${(cnt.count - 1) % 6 == 0}">
    			</tr>
    			<tr>
    		</c:if>        						
      			<td onmouseover="this.className = 'highrow';" onmouseout="this.className = '';">
      			<input type="checkbox" name="radera<c:out value="${mailMottagare.pid}"/>">
      			<c:out value="${mailMottagare.namn}"/></td>
      		</c:forEach>
    	</tr> 
    </table>        
    </div></div>
    <table>
    <tr>
    	<td>
    	<table>
    		<tr></tr>
    		<tr>
    			<td>Rubrik:<br>
    				<input type="text" size="106" name="rubrik" value="<c:out value="${modelMap.mailConfig.rubrik}"/>">
    			</td>
    		</tr>
    		<tr>
    			<td>Meddelande:<br>
    			<textarea rows="8" cols="80" name="body"><c:out value="${modelMap.mailConfig.body}"/></textarea>
    			</td>
    		</tr>
    		<tr>	
    			<td>MeddelandeFot:<br>
    				<textarea rows="4" cols="80" name="footer"><c:out value="${modelMap.mailConfig.footer}"/></textarea>
    			</td>
    		</tr>
    	</table>	
    	</td>
    	<td width="100%" valign="top">
		<table class="olle" width="100%" background="images/gem.jpg">
		    	<tr><th width="100%" align="center">Bilagor</th></tr><tr><td>&nbsp;&nbsp;<br><br><br></td></tr>
		</table>
    	<div style="height:110px;  padding:6px; border-style:solid; border-width:1; overflow:auto; direction:ltr;">
		<div style="direction:ltr;">	
								
    	<table width="100%" >			    		
    		<c:forEach items="${modelMap.bilagor}" var="bilaga" varStatus="cnt">
    			<tr><td><c:out value="${bilaga}"/></td>
    				<td><input type="button" 
      			           name="R<c:out value="${bilaga}"/>" 
      			           class="tablebutton" 
      			           value="Ta bort"
   					       title="Tar bort bilagan"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
						   onClick="mailurvalform._action.value='remove';mailurvalform._item.value='${bilaga}';mailurvalform.submit();">   						   
   				    </td>	
    			</tr>
    		</c:forEach>	
    	</table>    	
    	</div></div>
    	<table>
    	<tr>
    	<td colspan="2">
    		<input type="file" size="40" name="bilaga">&nbsp;&nbsp;<a href="javascript:mailurvalform._action.value='reload';mailurvalform.submit();">Ladda bilaga</a>
    		</td>
    	</tr>
    	</table>
    	<input type="hidden" name="_action">
    	<input type="hidden" name="_item">
    	<input type="hidden" value="" name="personer">
    	</form>
    	</td>
    	</tr>
    	</table>
     </div>
  </body>
</html>

