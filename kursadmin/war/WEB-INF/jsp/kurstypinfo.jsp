<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Info om kurstyp</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
  </head>
  <body>
  <h2>Kurstyp</h2>  
  <table>
  	<tr>
  		<td>kurstyp:</td><td><c:out value="${kurstyp.namn}"/></td>
  	</tr>  	 			
  	<tr>
  		<td valign="top">Info:</td>
  		<td>
  			<textarea rows="5" cols="38"><c:out value="${kurstyp.info}"/></textarea>
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