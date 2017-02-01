<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title>Aktivitet</title>
   <link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  <script language="javascript" src="basic.js"></script>
</head>
<body>
<h1>Aktivitet</h1>
 
<form:form name="kalenderform" method="post" commandName="kalender">
	<div id="menu">
    <ul>
        <li><a href="kalender.htm" target="_self" title="avbryt"><span>Avbryt</span></a></li>	
       <li><a href="javascript:kalenderform.submit();" target="_self" title="spara"><span>Spara</span></a></li>
       <li><a href="javascript:kalenderform.reset();" target="_self" title="reset"><span>Återställ</span></a></li>
      
    </ul>
	</div>
	<br><br>
  <table width="95%" bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
    <tr>
    	
        <td align="right">År:</td>
        <td>
          	<form:select path="ar">
				<c:forEach items="${arlista}" var="ar">				
					<form:option value="${ar}" label="${ar}"/>
				</c:forEach>
			</form:select>        
           	Månad:        
          	<form:select path="man">
				<c:forEach items="${manlista}" var="man">				
					<form:option value="${man}" label="${man}"/>
				</c:forEach>
			</form:select>
			Dag:        
          	<form:select path="dag">
				<c:forEach items="${daglista}" var="dag">				
					<form:option value="${dag}" label="${dag}"/>
				</c:forEach>
			</form:select>
			Tid:
			<form:select path="tim">			   	
			    <form:option value="0" label="00"/>							
				<form:option value="1" label="01"/>
				<form:option value="2" label="02"/>
				<form:option value="3" label="03"/>
				<form:option value="4" label="04"/>
				<form:option value="5" label="05"/>
				<form:option value="6" label="06"/>
				<form:option value="7" label="07"/>
				<form:option value="8" label="08"/>
				<form:option value="9" label="09"/>
				<form:option value="10" label="10"/>
				<form:option value="11" label="11"/>
				<form:option value="12" label="12"/>
				<form:option value="13" label="13"/>
				<form:option value="14" label="14"/>
				<form:option value="15" label="15"/>
				<form:option value="16" label="16"/>
			    <form:option value="17" label="17"/>
				<form:option value="18" label="18"/>
				<form:option value="19" label="19"/>
				<form:option value="20" label="20"/>
				<form:option value="21" label="21"/>
				<form:option value="22" label="22"/>
				<form:option value="23" label="23"/>												
			</form:select>
			:
			<form:select path="min">								
				<form:option value="0" label="00"/>
				<form:option value="5" label="05"/>
				<form:option value="10" label="10"/>
				<form:option value="15" label="15"/>
				<form:option value="20" label="20"/>
				<form:option value="25" label="25"/>
				<form:option value="30" label="30"/>
				<form:option value="35" label="35"/>
				<form:option value="40" label="40"/>
				<form:option value="45" label="45"/>
				<form:option value="50" label="50"/>
				<form:option value="55" label="55"/>								
			</form:select>
        </td>
        	<form:errors path="tidpunkt" cssClass="error"/>
        <td>
        </td>              
    </tr>   
    <tr>        	
        <td align="right" width="20%">Aktivitet:</td>
        <td width="20%">
        	<form:select path="rubrik">
        		<form:option label="Välj aktivitet" value="-"></form:option>        		
				<form:options items="${aktlista}" itemValue="namn" itemLabel="varde"/>				
			</form:select>
          	annan <form:input size="50" path="altrubrik"/>
        </td>
        <td width="60%">
          	<form:errors path="rubrik" cssClass="error"/>
        </td>  
    </tr>    
    <tr>
	    <td align="right">Status:</td>
	    <td width="20%">
	    	<form:select path="vikt">
				<form:options items="${statlista}" itemValue="namn" itemLabel="varde"/>
			</form:select>
		</td>
	    <td width="60%">
	    	<form:errors path="rubrik" cssClass="error"/>
	    </td> 		
    </tr>
    <tr>
      <td align="right" valign="top" width="20%">Info:</td>
        <td width="20%">
          <form:textarea rows="10" cols="80" path="info"/>
        </td>
        <td width="60%">  
        	<form:errors path="info" cssClass="error"/>       
        </td>       
    </tr>    
  </table>
  <br>
</form:form>
</body>
</html>
