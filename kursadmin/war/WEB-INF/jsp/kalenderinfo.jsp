<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Aktivitet</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
  </head>
  <body>
  <h2>Aktivitet</h2>  
  <table>
  	<tr>
  		<td align="right">Tidpunkt:</td><td><fmt:formatDate value="${kalender.tidpunkt}" pattern="yyyy dd MMM HH:mm"/></td>
  	</tr>
  	<tr>
  		<td  align="right">Aktivitet:</td><td><c:out value="${kalender.rubrik}"/></td>
 	</tr>
 	<tr>
  		<td  align="right">Status:</td><td><c:out value="${kalender.altrubrik}"/></td>
 	</tr>
  	<tr>
  		<td align="right" valign="top">Info:</td>  		
  	 			
 	<td>
 		<textarea rows="12" cols="80" readonly="readonly"><c:out value="${kalender.info}"/></textarea>
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