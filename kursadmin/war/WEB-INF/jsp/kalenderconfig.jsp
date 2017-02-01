<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title>Kalender</title>
   <link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  <script language="javascript" src="basic.js"></script>
  <script language="javascript">
  
	
  	function doSubmit()
  	{  		
  		alert("nivasel: " +  kalform.nivasel.value);
  		for (var i = 0; i < kalform.vikt.length; i++)
  		{
  			var inp = document.createElement("input");  			
  			inp.type  = "hidden";
  			inp.name  = "humle" + (i + 1);  			
  			inp.value = kalform.vikt.options[i].text;  			
  			kalform.appendChild(inp);	
  		}
  	
  		for (var i = 0; i < kalform.akt.length; i++)
  		{
  			var inp = document.createElement("input");  			
  			inp.type  = "hidden";
  			inp.name  = "dumle" + kalform.akt.options[i].text;  			
  			inp.value = kalform.akt.options[i].text;
  			kalform.appendChild(inp);
  		}  	
  		kalform.submit();
  	}  	
  </script>
</head>
<body>
<h1>Inställningar för Kalender</h1>
 
<form name="kalform" method="post" action="kalenderconfig.htm">
	<div id="menu">
    <ul>
	   	<li><a href="config.htm" target="_self" title="avbryt"><span>Avbryt</span></a></li>
       	<li><a href="javascript:doSubmit();" target="_self" title="spara"><span>Spara</span></a></li>
   </ul>
   </div><br><br><br>
  <table  width="70%" border=0> 
  <tr><td width="50%">
  	<table bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
    <tr>
      <td colspan="2" width="100%" align="left"><b>Aktivitetskoder</b></td>
    </tr>
    <tr>   
    <td width="40%">
    	 <select name="akt" size="7" onchange="javascript:kalform.aktvarde.value=this.options[this.selectedIndex].text">
         <c:forEach items="${modelMap.aktlista}" var="akt" varStatus="cnt">
         	<option value="<c:out value="${akt.namn}"/>" <c:if test="${cnt.count == 1}">selected</c:if>>
         	<c:out value="${akt.varde}"/>        				   
         	</option>
         </c:forEach>
    </td>    
    <td align="left" valign="bottom">
        <input type="button" 
      			           name="aktny" 
      			           class="tablebutton" 
      			           value="Ny"
   					       title="Lägger till en ny aktivitetskod"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="if (this.value=='Lägg till') 
   						   			{
   						   				kalform.akt.options.add(new Option(kalform.aktvarde.value, kalform.akt.length, false)); 
   						   				this.value='Ny'; 
   						   				kalform.akt.disabled = false;
   						   				kalform.aktvarde.disabled = true;
   						   				kalform.aktandra.disabled = false;
   						   				kalform.aktradera.value = 'Ta bort';
   						   				kalform.aktandra.disabled = false;
   						   				kalform.akt.selectedIndex = kalform.akt.options.size - 1;
   						   				kalform.akt.focus();
   						   			} 
   						   			else 
   						   			{
   						   				kalform.aktvarde.disabled=false;
   						   				kalform.aktvarde.value='';
   						   				this.value='Lägg till';
   						   				kalform.akt.disabled=true;
   						   				kalform.aktandra.disabled = true;
   						   				kalform.aktradera.value='Avbryt';   						   				
   						   				kalform.aktvarde.focus();
   						   			}">
	   	<br>			
        <input type="button" 
      			           name="aktandra" 
      			           class="tablebutton" 
      			           value="Ändra"
   					       title="Ändrar markerad aktivitetskod"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="if (this.value=='Spara') 
   						   			{
   						   				kalform.akt.options[kalform.akt.selectedIndex].text = kalform.aktvarde.value; 
   						   				this.value='Ändra'; 
   						   				kalform.akt.disabled = false;
   						   				kalform.aktvarde.disabled = true;
   						   				kalform.aktradera.value = 'Ta bort';
   						   				kalform.aktny.disabled = false;
   						   			} 
   						   			else 
   						   			{
   						   				kalform.aktvarde.disabled=false;
   						   				this.value='Spara';
   						   				kalform.akt.disabled=true;
   						   				kalform.aktradera.value='Avbryt';
   						   				kalform.aktny.disabled = true;
   						   				kalform.aktvarde.focus();
   						   			}">
	   	<br>					   
        <input type="button" 
      			           name="aktradera" 
      			           class="tablebutton" 
      			           value="Ta bort"
   					       title="Tar bort aktivitetskoden"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="if (this.value == 'Avbryt')
   						   			{
   						   				kalform.akt.disabled = false; 
   						   				this.value='Ta bort'; 
   						   				kalform.aktandra.value='Ändra';
   						   				kalform.aktny.value='Ny';
   						   				kalform.aktandra.disabled =false;
   						   				kalform.aktny.disabled =false;
   						   				kalform.aktvarde.disabled = true;
   						   				
   						   			} 
   						   			else 
   						   			if (confirm('Skall du verkligen ta bort ' + kalform.akt.options[kalform.akt.selectedIndex].text) == 1) 
   						   			{	
   						   				kalform.akt.options[kalform.akt.selectedIndex] = null;
   						   				kalform.akt.selectedIndex=0;
   						   				kalform.akt.focus();
   						   				kalform.aktvarde.value = kalform.akt.options[0].text;	
   						   			}">
   						   				 
    </td></tr>             
    <tr><td colspan="2"><input type="text" disabled name="aktvarde" size="35" value="${modelMap.aktlista[0].varde}">
    </td></tr>
  </table>
  </td><td width="50%">
  <table bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
    <tr>
      <td colspan="2" width="100%" align="left"><b>Statuskoder</b></td>
    </tr>
    <tr><td width="40%">
    	 <select name="vikt" size="7" onchange="javascript:kalform.viktvarde.value=this.options[this.selectedIndex].text">
         <c:forEach items="${modelMap.viktlista}" var="vikt" varStatus="cnt">
         	<option value="<c:out value="${vikt.namn}"/>" <c:if test="${cnt.count == 1}">selected</c:if>> 
         	<c:out value="${vikt.varde}"/>        				   
         	</option>
         </c:forEach>
         </select>
        </td>
        <td align="left" valign="bottom">
        <input type="button" 
      			           name="viktny" 
      			           class="tablebutton" 
      			           value="Ny"
   					       title="Lägger till en ny aktivitetskod"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="if (this.value=='Lägg till') 
   						   			{
   						   				kalform.vikt.options.add(new Option(kalform.viktvarde.value, kalform.vikt.length, false)); 
   						   				this.value='Ny'; 
   						   				kalform.vikt.disabled = false;
   						   				kalform.viktvarde.disabled = true;
   						   				kalform.viktandra.disabled = false;
   						   				kalform.viktradera.value = 'Ta bort';
   						   				kalform.viktandra.disabled = false;
   						   				kalform.vikt.selectedIndex = kalform.vikt.options.size - 1;
   						   				kalform.vikt.focus();
   						   			} 
   						   			else 
   						   			{
   						   				kalform.viktvarde.disabled=false;
   						   				kalform.viktvarde.value='';
   						   				this.value='Lägg till';
   						   				kalform.vikt.disabled=true;
   						   				kalform.viktandra.disabled = true;
   						   				kalform.viktradera.value='Avbryt';   						   				
   						   				kalform.viktvarde.focus();
   						   			}">
	   	<br>			
        <input type="button" 
      			           name="viktandra" 
      			           class="tablebutton" 
      			           value="Ändra"
   					       title="Ändrar markerad aktivitetskod"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="if (this.value=='Spara') 
   						   			{
   						   				kalform.vikt.options[kalform.vikt.selectedIndex].text = kalform.viktvarde.value; 
   						   				this.value='Ändra'; 
   						   				kalform.vikt.disabled = false;
   						   				kalform.viktvarde.disabled = true;
   						   				kalform.viktradera.value = 'Ta bort';
   						   				kalform.viktny.disabled = false;
   						   			} 
   						   			else 
   						   			{
   						   				kalform.viktvarde.disabled=false;
   						   				this.value='Spara';
   						   				kalform.vikt.disabled=true;
   						   				kalform.viktradera.value='Avbryt';
   						   				kalform.viktny.disabled = true;
   						   				kalform.viktvarde.focus();
   						   			}">
	   	<br>					   
        <input type="button" 
      			           name="viktradera" 
      			           class="tablebutton" 
      			           value="Ta bort"
   					       title="Tar bort aktivitetskoden"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="if (this.value == 'Avbryt')
   						   			{
   						   				kalform.vikt.disabled = false; 
   						   				this.value='Ta bort'; 
   						   				kalform.viktandra.value='Ändra';
   						   				kalform.viktny.value='Ny';
   						   				kalform.viktandra.disabled =false;
   						   				kalform.viktny.disabled =false;
   						   				kalform.viktvarde.disabled = true;
   						   				
   						   			} 
   						   			else 
   						   			if (confirm('Skall du verkligen ta bort ' + kalform.vikt.options[kalform.vikt.selectedIndex].text) == 1) 
   						   			{	
   						   				kalform.vikt.options[kalform.vikt.selectedIndex] = null;
   						   				kalform.vikt.selectedIndex=0;
   						   				kalform.vikt.focus();
   						   				kalform.viktvarde.value = kalform.vikt.options[0].text;	
   						   			}">
   						   				 
    </td></tr>
    <tr><td colspan="2">
    	<input disabled type="text" name="viktvarde" size="35" value="${modelMap.viktlista[0].varde}">
    </td></tr>
  </table>
  </td></tr>
  </table> 
</form>

</body>
</html>
