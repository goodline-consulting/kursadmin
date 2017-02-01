<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Kursdeltagare</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
<script type = "text/javascript">


  	function validateForm()
  	{  	 
  		<c:forEach items="${modelMap.elever}" var="elev" varStatus="cnt"> 	
  		<c:if test="${elev.fakturanr == ''}">		
  			if (!isNumber(elevform.pris${cnt.count}, "Felaktigt angivet belopp!"))
  				return false;	
  		</c:if>		
  		</c:forEach>	
  		return true;
  	}
  	function setAndSubmit(_action, _item)
  	{		  	  		  		
  		elevform._item.value   = _item; 
  		elevform._action.value = _action; 	  			
  		elevform.submit();
  	}
  	</script>
 </head>
  <body <c:if test="${!empty modelMap.msg}"> onload="javascript:alert('${modelMap.msg}');"</c:if>>

   <h1>Deltagare på kurs <c:out value="${modelMap.beteckning}"/></h1>  
   <form name="searchform" method="post"> 
   <div id="menu">
    <ul>
       <li><a href="kurser.htm" target="_self" title="stang"><span>Stäng</span></a></li>	
       <li><a href="javascript:elevform.reset();" target="_self" title="reset"><span>Återställ</span></a></li>    
       <li><a href="javascript:elevform._action.value='addfirst';elevform.submit();"><span>Lägg till</span></a></li>   
       <li><a href="javascript:if (validateForm()){elevform._action.value='spara';elevform.submit();}" target="_self" title="spara"><span>Spara</span></a></li>
      
       	<c:if test="${modelMap.mailstatus}">&nbsp;&nbsp;&nbsp;OBS! mailfunktionen är <span title="Klicka för att aktivera mailfunktionen" onclick="window.location = 'elever.htm?mailstatus=on'"><font color="darkblue"><u>avstängd</u></span></font></c:if>
        <c:if test="${!modelMap.mailstatus}">&nbsp;&nbsp;&nbsp;Mailfunktionen är <span title="Klicka för att stänga av mailfunktionen" onclick="window.location = 'elever.htm?mailstatus=off'"><font color="darkblue"><u>aktiverad</u></span></font></c:if>  
                    
    </ul>
	</div>
	<br><br>
	</form>
	<form name="elevform" method="post" action="elever.htm">    
	 
	<div style="height:510px; padding:6px; border-style:solid; border-width:1; overflow:auto; direction:ltr;">
	<div style="direction:ltr;"> 
	<table>
		<tr>
			<th></th>
			<th align="left">Deltagare</th>
			<th align="left">Faktura</th>
			<th align="left">Anmäld</th>
			<th>M</th>
			<th align="left">Pris</th>
			<th align="left">Betalt</th>
			<th align="left">Datum</th>
			<th align="left">Aktiv</th>
			<c:if test="${modelMap.kursAll.resehantering}">
				<th align="left">${modelMap.reseRubrik}</th>
			</c:if>
			<c:if test="${modelMap.kursAll.rumshantering}">
				<th align="left">${modelMap.logiRubrik}</th>
				<th align="left">${modelMap.logi2Rubrik}</th>
			</c:if>
			<c:if test="${modelMap.kursAll.mathantering}">
				<th align="left">${modelMap.matRubrik}</th>
			</c:if>
			<th align="left" colspan=5>Info</th>
		</tr>	    
    	<c:forEach items="${modelMap.elever}" var="elev" varStatus="cnt">    		
    		<tr class="jamnrad" onmouseover="this.className = 'highrow';" onmouseout="this.className = '';">	
    			<td align="right"><c:out value="${cnt.count}"/></td>			
      			<td><c:out value="${elev.namn}"/></td>
      			<td bgcolor="lightgrey"><a style="color:darkblue;" href="javascript:openWindow2('faktura.htm?visa=${elev.fakturanr}', 500, 550);" >${elev.ocr}</a></td>
      			<td><c:out value="${elev.anmald}"/></td>
      			<td><input type="checkbox" value="${elev.manpris}" name="manpris${cnt.count}" <c:if test="${elev.manpris == true}"> checked</c:if><c:if test="${!elev.enabled}"> disabled</c:if>></td>
      			
      			<c:if test="${elev.enabled}">
      				<td><input size=4 type="text" value="<c:out value="${elev.pris}"/>" name="pris<c:out value="${cnt.count}"/>">
      				    
      				</td>
      			</c:if>	
      			<c:if test="${!elev.enabled}">
      				<td><c:out value="${elev.pris}"/>:-</td>
      			</c:if>
      			<td><input type="text" size=4 disabled readonly value="${elev.betalt}:-">
      			<c:if test="${(elev.pris != elev.betalt) and elev.fakturanr != 0}">
      				<input type="button" 
      			           name="betala<c:out value="${elev.pid}"/>"     			            
      			           value="Reg"
   					       title="Registrerar en betalning"   						   
   						   onClick="window.location = 'betalning.htm?refnr=<c:out value="${elev.ocr}"/>&fromelev=${elev.kid}';"      					  
      				>	
      			</c:if>
      				
      			</td>
      			<td><c:out value="${elev.betaldatum}"/></td>
      			<td><input type="checkbox" value="<c:out value="${elev.aktiv}"/>" name="aktiv<c:out value="${cnt.count}"/>" <c:if test="${elev.aktiv}">checked</c:if>></td>     			
      			<c:if test="${modelMap.kursAll.resehantering}">
					<td align="left">
						<select name="resa${cnt.count}" <c:if test="${!elev.enabled}">disabled</c:if>>       	    								
							<c:forEach items="${modelMap.reseLista}" var="resa" varStatus="idx">										
								<option value="${idx.count}" <c:if test="${elev.resa == idx.count}">selected</c:if>><c:out value="${resa}"/></option>
							</c:forEach>
						</select>
					</td>	 
				</c:if>
				<c:if test="${modelMap.kursAll.rumshantering}">
					<td align="left">
						<select name="logi${cnt.count}" <c:if test="${!elev.enabled}">disabled</c:if>>       	    								
							<c:forEach items="${modelMap.logiLista}" var="logi" varStatus="idx">										
								<option value="${idx.count}" <c:if test="${elev.logi == idx.count}">selected</c:if>><c:out value="${logi}"/></option>
							</c:forEach>
						</select> 
					</td>
					<td align="left"><input type="text" name="rum${cnt.count}" value="${elev.rum}" size="6" ></input></td>
				</c:if>
				<c:if test="${modelMap.kursAll.mathantering}">
					<td align="left">
						<select name="mat${cnt.count}" <c:if test="${!elev.enabled}">disabled</c:if>>       	    								
							<c:forEach items="${modelMap.matLista}" var="mat" varStatus="idx">										
								<option value="${idx.count}" <c:if test="${elev.mat == idx.count}">selected</c:if>><c:out value="${mat}"/></option>
							</c:forEach>
						</select> 
					</td>
				</c:if>
      			<td title="<c:out value="${elev.info}"/>"><input type="text" value="<c:out value="${elev.info}"/>" name="info<c:out value="${cnt.count}"/>"></td>
      			<td>
      			 <c:if test="${elev.ny == false}">
      			
	      			<input type="button" 
	      		           name="A<c:out value="${elev.pid}"/>" 
	      		           <c:if test="${!empty elev.email}">class="tablebutton"</c:if>  
	      		           <c:if test="${empty elev.email}">class="tablebuttonobs"</c:if>   
	      		           value="<c:if test="${elev.enabled && elev.fakturanr == 0}">Fakturera</c:if><c:if test="${elev.enabled && elev.fakturanr != 0}">Omfakt.</c:if><c:if test="${!elev.enabled}">Ändra</c:if>"
	   				       <c:if test="${elev.enabled && elev.fakturanr == 0}">title="Fakturerar kunden för kursen och ev andra ofakturerade kurser"</c:if>
	   				       <c:if test="${elev.enabled && elev.fakturanr != 0}">title="Omfakturerar kunden"</c:if>
	   				       <c:if test="${!elev.enabled}">title="Ger möjlighet att göra ändringar och omfakturera kunden"</c:if>
	   					   onMouseOver="goLite(this.form.name, this.name)"
	   					   <c:if test="${!empty elev.email}">onMouseOut="goDim(this.form.name, this.name)"</c:if>  
		      		       <c:if test="${empty elev.email}">onMouseOut="goDimObs(this.form.name, this.name)"</c:if>   
	   					   <c:if test="${!elev.enabled}">
	      						onClick="if (validateForm()) {setAndSubmit('open', <c:out value="${cnt.count - 1}"/>)};"
	      				   </c:if>
	   					   <c:if test="${elev.enabled && elev.fakturanr == 0}">
	      						onClick="if (validateForm()) {setAndSubmit('faktura', <c:out value="${cnt.count - 1}"/>)};"
	      				   </c:if>
	      				   <c:if test="${elev.enabled && elev.fakturanr != 0}">
	      						onClick="if (validateForm()) {setAndSubmit('omfaktura', <c:out value="${cnt.count - 1}"/>)};"
	      				   </c:if>
	      				   >   				   			     					   		   
	   			    </td>        	
	   			    <td>
	   			    <c:if test="${!empty elev.email && elev.fakturanr != '' && elev.betalt > 0}">
	   			    	<input type="button" 
	      			           name="B<c:out value="${elev.pid}"/>" 
	      			           class="tablebutton" 
	      			           value="Bet bekr"
	   					       title="Skickar en betalningsbekräftan till kursdeltagaren"
	   						   onMouseOver="goLite(this.form.name, this.name)"
	   						   onMouseOut="goDim(this.form.name, this.name)"
	   						   <c:if test="${elev.bbekr}">
	      							onClick="if (validateForm()){ if (confirm('Betalningsbekräftan är redan skickad, skall du skicka en ny?') == 1)  setAndSubmit('bbekr', <c:out value="${cnt.count - 1}"/>)};"
	      					   </c:if>
	   						   <c:if test="${!elev.bbekr}">
	      							onClick="if (validateForm()){alert('Betalningbekräftan skickas, nu till <c:out value="${elev.namn}"/>');  setAndSubmit('bbekr', <c:out value="${cnt.count - 1}"/>)};"
	      					   </c:if>
	      					   >
	      			</c:if>
	      			<c:if test="${empty elev.email}">
	      					&nbsp
	      			</c:if>		
	      			      						   
	   			    </td>       
	   			     <td><input type="button" 
	      			           name="F<c:out value="${elev.pid}"/>" 
	      			           class="tablebutton" 
	      			           value="Byt kurs"
	   					       title="Flyttar kursdeltagaren till en annan kurs"
	   						   onMouseOver="goLite(this.form.name, this.name)"
	   						   onMouseOut="goDim(this.form.name, this.name)"
	   						   onClick="openWindow2('bytkurs.htm?kid=<c:out value="${elev.kid}"/>&pid=<c:out value="${elev.pid}"/>&namn=<c:out value="${elev.namn}"/>&beteckning=<c:out value="${modelMap.beteckning}"/>', 650, 450);elevform.submit()"      					  
	      					   >   						   
	   			    </td>
   			    </c:if>      
   			    <c:if test="${elev.ny == true}">
   			    	<td>&nbsp;</td><td>&nbsp;</td>
   			    </c:if> 			      			
      			<td><input type="button" 
      			           name="R<c:out value="${elev.pid}"/>" 
      			           class="tablebutton" 
      			           value="Ta bort"
   					       title="Tar bort <c:out value="${elev.namn}"/> ur registret"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="if (confirm('Skall du verkligen ta bort <c:out value="${elev.namn}"/> från kursen?') == 1) window.location = 'elever.htm?radera=<c:out value="${cnt.count - 1} "/>';">   						   
   			    </td>
   			    <td class="error"></td>		   	
      		</tr>
    	</c:forEach>
    	 
    </table>
    <input type="hidden" name="_action" value="">    
    <input type="hidden" name="_item" value="">      
    </form>	
    </div></div>
  </body>
</html>

