<%@ include file="/WEB-INF/jsp/include.jsp" %>

<head>
<title>Secured application - Login with username and password</title>
<style type="text/css">
<!--
table {
	border-top-color: #FFF;
	border-right-color: #666;
	border-bottom-color: #999;
	border-left-color: #FFF;
}
td {
	font-family: Verdana, Geneva, sans-serif;
}
-->
</style>
</head>

<body>
<div align="center">
  	<p><img src="images/logga.jpg" width="148" height="149" /></p>
  	<c:if test="${not empty param.quit}">
  		<br/><font face="Verdana">
  		Du är nu utloggad ifrån applikationen<br>
  		Klicka här för att <a href="login.jsp">logga in</a> igen</font>        		        
	</c:if>
	<c:if test="${not empty param.denied}">
		<font face="Verdana" color="red" size="4"><b>Ej Behörig!</b></font><br>
  		<br/><font face="Verdana" color="red">
  		Du saknar behörighet för operationen<br></font>
  		<font face="Verdana"><br>
  		Klicka här för att <a href="main.htm">återvända</a></font>        		        
	</c:if>
</div>
<c:if test="${empty param.quit and empty param.denied}">
<div align="center">
  <table width="354" border="2">
    <tr>
      <td width="344"><div align="center">
      <form name='f' action='/kursadmin/j_spring_security_check' method='post'>
        <table width="342" border="0">
          <tr>
            <td colspan="2" bgcolor="#000099"><font color="white">Logga in</font></td>
            </tr>
          <tr>
            <td height="67" >&nbsp;</td>
            <td width="194"><img src="images/lock.gif" /></td>
          </tr>
          <tr>
            <td >Användarnamn:</td>
            <td><input type="text" width= "194" name="j_username" id="j_username" /></td>
          </tr>
          <tr>
            <td >Lösenord:</td>
            <td><input type="password" width= "194" name="j_password" id="j_password" /></td>
          </tr>
          <tr>
            <td colspan="2">&nbsp;</td>
            </tr>
          <tr>
            <td width="138" align="right"><div align="left">
                  <input type="checkbox" name="_spring_security_remember_me" id="_spring_security_remember_me" />Kom ihåg mig
              </div></td>
            <td align="right"><input type="submit" name="button" id="button" value="Logga in" /></td>
            </tr>
        </table>                
        </form>
      </div></td>
    </tr>
  </table>
  <c:if test="${not empty param.login_error}">
  	<br/>
  	<font color="red">Din inloggning misslyckades, försök igen.        		      
     </font>
  </c:if>
</div>
</c:if>
</body>
</html>
