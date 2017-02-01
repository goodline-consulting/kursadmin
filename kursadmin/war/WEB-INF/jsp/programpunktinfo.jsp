<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Programpunkt</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
  </head>
  <body>
  <h2>Programpunkt</h2>  
  <table>
  	<tr>
  		<td>Programpunkt:</td><td><c:out value="${programpunkt.namn}"/></td>
  	</tr>  	
	<tr>
  		<td>Rubrik:</td><td><c:out value="${programpunkt.header}"/></td>
  	</tr>
  	<tr>
  		<td>Url:</td><td><c:out value="${programpunkt.url}"/></td>
  	</tr>  			
  	<tr>
  		<td valign="top">Info:</td>
  		<td>
  			<textarea rows="5" cols="50"><c:out value="${programpunkt.info}"/></textarea>
  		</td>
 	</tr> 			
  </table>
  <br>  
  <br>
  <form name="shutdown">
  <div align=right><input type="button" 
      			           name="close" 
      			           class="tablebutton" 
      			           value="Stäng"
   					       title="Stänger fönstret"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="window.close();">
     						   	 
  </div>
  </form>   						   			
  </body>
</html>