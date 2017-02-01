<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Kreditera faktura</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
 </head>
 	<body>
   <h1>Kreditera faktura ${modelMap.fakturanr} (${modelMap.namn})</h1>  
   <form name="searchform" method="post"> 
   <div id="menu">
    <ul>
       <li><a href="faktura.htm" target="_self" title="stang"><span>Avbryt</span></a></li>       
	   <li><a href="javascript:searchform.submit()" target="_self" title="sub"><span>Kreditera</span></a></li>      	    	             
    </ul>
	</div><br><br>
	<table>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td>Kreditbelopp:</td><td><input type="text" name="belopp" value="${modelMap.belopp}" size="8"></td>			
		<tr>
		<tr>
			<td>Info:</td><td><input type="text" name="info" size="50"></input></td>			
		<tr>				
	</table>
	<input type="hidden" name="kreditfakturanr" value="${modelMap.fakturanr}"/>		                           		
	</form>
	  
  </body>
</html>

