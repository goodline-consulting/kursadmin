<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  	<head>
  		<title>KursInfo</title>
  		<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  		<script language="javascript" src="basic.js"></script>
  	</head>
  	<body>
  		<h1>stimrapport</h1> 
  		<form name="searchform"> 
   			<div id="menu">
    		<ul>
       		<li><a href="rapporter.htm" target="_self" title="stang"><span>Stäng</span></a></li>	                     
    		</ul>
			</div>
		</form>
		<br><br>
  		<table> 
			<tr class="uddarad">		    
   			<td><i>Antal pass med < 20 deltagare:</i></td>
   			<td>${modelMap.stim.antUpptill20}</td> 
   			<td><i>Kostnad</td>
   			<td><fmt:formatNumber type="CURRENCY" value="${modelMap.stim.summaUpptill20}"/></td>  		   			
   			</tr>
   			<tr>		    
   			<td><i>Antal pass med < 40 deltagare:</i></td>
   			<td>${modelMap.stim.antUpptill40}</td>
   			<td><i>Kostnad</td>
   			<td><fmt:formatNumber type="CURRENCY" value="${modelMap.stim.summaUpptill40}"/></td>
   			<tr class="uddarad">		    
   			<td><i>Antal pass med > 40 deltagare:</i></td>
   			<td>${modelMap.stim.ant40Plus}</td>
   			<td><i>Kostnad</td>
   			<td><fmt:formatNumber type="CURRENCY" value="${modelMap.stim.summa40Plus}"/></td>
   			</tr>    		   			
   			<tr><td colspan="4""><hr></td></tr>
   			<tr>		    
   			<td><strong>Totalt:</strong></td>
   			<td>${modelMap.stim.antTot}</td>
   			<td>&nbsp;</td>
   			<td><fmt:formatNumber type="CURRENCY" value="${modelMap.stim.summaTot}"/></td>
   			</tr> 				
  </table>    					   		
  </body>
</html>