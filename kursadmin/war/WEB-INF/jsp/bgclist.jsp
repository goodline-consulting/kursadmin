<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title>Inbetalningar</title>
   <link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  <script language="javascript" src="basic.js"></script>
  <script type="text/javascript">
	
	function validate()
	{
		var str1 = document.getElementById('fromdat').value;
		var str2 = document.getElementById("tomdat").value;
		var yr1  = parseInt(str1.substring(0,4),10);
		var mon1 = parseInt(str1.substring(5,7),10);
		var dt1  = parseInt(str1.substring(8,10),10);
		
		var yr2  = parseInt(str2.substring(0,4),10);
		var mon2 = parseInt(str2.substring(5,7),10);
		var dt2  = parseInt(str2.substring(8,10),10);
		
		var date1 = new Date(yr1, mon1, dt1);
		var date2 = new Date(yr2, mon2, dt2);
		if (date2 < date1)
		{
			alert("OBS! Fr.o.m datum måste vara större än T.o.m datum");
			return false;
	    }
	    return true; 		
	}
	
  </script>
</head>
<body<c:if test="${!empty modelMap.error}"> onload="javascript:alert('${modelMap.error}');"</c:if>>
<h1>Betalningar</h1>
 
<form name="searchform" method="post" action="bgclist.htm">
	<div id="menu">
    <ul>
	   <li><a href="ekonomi.htm" target="_self" title="Stäng"><span>Stäng</span></a></li>
	   <li><a href="javascript:searchform._action.value='oplacerade';searchform.submit();" title="Sök oplacerade"><span>Sök oplacerade</span></a></li>
	   <li><a href="javascript:if (validate()){searchform._action.value='leta';searchform.submit();}" title="Sök"><span>Sök</span></a></li>
       <li>&nbsp;&nbsp;
       	    <select name="fromdat" id="fromdat">
       	    	<option value="" selected>Fr.o.m Datum</option>				
				<c:forEach items="${modelMap.dagar}" var="dag" varStatus="cnt">										
					<option value="<c:out value="${dag}"/>"> <c:out value="${dag}"/></option>
				</c:forEach>
			</select>      
       </li>
       <li>&nbsp;&nbsp;
       	    <select name="tomdat" id="tomdat">
       	    	<option value="" selected>T.o.m Datum</option>				
				<c:forEach items="${modelMap.dagar}" var="dag" varStatus="cnt">										
					<option value="<c:out value="${dag}"/>"> <c:out value="${dag}"/></option>
				</c:forEach>
			</select>      
       </li>     
       <li>&nbsp;&nbsp;Namn: <input type="text" size="12" name="namn" value="" onKeyPress="return submitenter(this,event)"></li>
    </ul>
	</div><br><br>
    <input type="hidden" name="_action">
</form>
<c:if test="${!empty modelMap.betalningar}">
<form name="bgcform">   
	<!-- scrollable area -->
	<div style="height:510px; padding:6px; border-style:solid; border-width:1; overflow:auto; direction:ltr;">
	<div style="direction:ltr;"> 
	<table>
		<tr>
			<th align="left">Löpnr&nbsp;</th><th align="left">Betaldatum&nbsp;</th><th align="left">Fakturanr&nbsp;</th><th align="left">Inbetalare&nbsp;</th><th align="left">Summa&nbsp;</th><th align="left">Betalningssätt&nbsp;</th><th align="left">Placerad&nbsp;</th><th colspan="2" align="left">Info</th>
		</tr>	    
    	<c:forEach items="${modelMap.betalningar}" var="betalning" varStatus="cnt">
    		<c:if test="${cnt.count % 2 == 0}">
    		<tr class="jamnrad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'jamnrad';">
    		</c:if>
    		<c:if test="${cnt.count % 2 != 0}">
    		<tr class="uddarad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'uddarad';">
    		</c:if>       			
    			<td align="left"><c:out value="${betalning.id}"/></td>
      			<td><fmt:formatDate pattern="yyyy-MM-dd" value="${betalning.betaldatum}"/></td>
      			<td align="right"><c:out value="${betalning.fakturanr}"/></td>
      			<td><c:out value="${betalning.name}"/></td>
      			<td align="right"><fmt:formatNumber value="${betalning.amount}" type="currency"/></td>
      			
      			<td><c:out value="${betalning.betalkanal}"/></td>
      			<td><input type="checkbox" <c:if test="${betalning.placerad}">checked </c:if>disabled/>
      			<c:if test="${!betalning.placerad && betalning.amount > 0}">
      				<input type="button" 
      			           name="placera<c:out value="${betalning.id}"/>"     			            
      			           value="Placera"
   					       title="Placerar betalningen på en faktura"   						   
   						   onClick="window.location = 'betalning.htm?id=${betalning.id}&';"></input>      			
   				</c:if>
   				<c:if test="${!betalning.placerad && betalning.amount < 0}">
      				<input type="button" 
      			           name="betala<c:out value="${betalning.id}"/>"     			            
      			           value="Betala"
   					       title="Markerar betalningen som utbetalad till kund"   						   
   						   onClick="window.location = 'betalning.htm?id=${betalning.id}&';"></input>      			
   				</c:if>
   				</td>
   				
      			
      			<td><c:out value="${betalning.info}"/></td>	
      			<td><input type="button" 
      			           name="R<c:out value="${betalning.id}"/>" 
      			           class="tablebutton" 
      			           value="Ta bort"
   					       title="Tar bort betalningen ur registret"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="if (confirm('Skall du verkligen ta bort betalningen') == 1) window.location = 'bgclist.htm?radera=<c:out value="${betalning.id}"/>';">   						   
   			    </td>
      		</tr>
       </c:forEach>
    </table> 
    </div>
    </div>
 </form>
 </c:if>	
</body>
</html>
