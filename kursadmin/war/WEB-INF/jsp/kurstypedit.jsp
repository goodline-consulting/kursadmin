<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title>Kurstyp</title>
   <link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  <script language="javascript" src="basic.js"></script>
  <script language="javascript">
  	
  	function doSubmit()
  	{  		
  		for (var i = 0; i < typform.niva.length; i++)
  		{
  			var inp = document.createElement("input");  			
  			inp.type  = "hidden";
  			inp.name  = "humle" + (i + 1);  			
  			inp.value = typform.niva.options[i].text;  			
  			typform.appendChild(inp);	
  		}  	  		
  		if (!isNumber(document.typform.momssats, "felaktigt angivet momsformat"))
  	  		return;
  		if (!isNumber(document.typform.momsbak, "felaktigt angiven momsberäkning"))
  	  		return;
  		
  		typform.submit();
  	}  	
  </script>
</head>
<body>
<h1>Kurstyp och nivåer</h1>
 
<form name="typform" method="post">
	<div id="menu">
    <ul>
	   	<li><a href="kurstyper.htm" target="_self" title="avbryt"><span>Avbryt</span></a></li>
       	<li><a href="javascript:doSubmit();" target="_self" title="spara"><span>Spara</span></a></li>
   </ul>
   </div><br><br><br>
  <table  width="70%" border=0> 
  <tr><td valign="top" width="50%">
  	<table bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
    <tr>
      <td colspan="2" width="100%" align="left"><b>Kurstyp</b></td>
    </tr>
    <tr>   
    <td>Beteckning:</td>
    <td>
    	<input type="text" name="namn" value="${modelMap.kurstyp.namn}"> 
    </td>
    <tr>
    <td>Organisation:</td>
    <td>
    	 <select name="organisation">
         <c:forEach items="${modelMap.orgs}" var="org" varStatus="cnt">
         	<option value="<c:out value="${org.oid}"/>" <c:if test="${org.oid == modelMap.kurstyp.oid}">selected</c:if>> 
         	<c:out value="${org.orgnamn}"/>        				   
         	</option>
         </c:forEach>
    	 </select>    	
    </td>
    </tr>
    <tr>
    <td>Fakturaklass:</td>
    <td>
    	<input type="text" name="fakturaklass" value="${modelMap.kurstyp.fakturaklass}"> 
    </td>         
    </tr>
    <tr>
    <td>Momssats:</td>
    <td>
    	<input type="text" name="momssats" value="${modelMap.kurstyp.momssats}"> 
    </td>        
    </tr>
    <tr>
    <td>Momsberäkning:</td>
    <td>
    	<input type="text" name="momsbak" value="${modelMap.kurstyp.momsbak}"> 
    </td>        
    </tr>
    <td>Fakturatyp:</td>
    <td>
		<input type="radio" name="fakturatyp" value="0" <c:if test="${modelMap.kurstyp.fakturatyp == '0'}">checked</c:if>>Ingen
		<input type="radio" name="fakturatyp" value="1" <c:if test="${modelMap.kurstyp.fakturatyp == '3'}">checked</c:if>> Text	
		<input type="radio" name="fakturatyp" value="2" <c:if test="${modelMap.kurstyp.fakturatyp == '2'}">checked</c:if>> Html
		<input type="radio" name="fakturatyp" value="3" <c:if test="${modelMap.kurstyp.fakturatyp == '1'}">checked</c:if>> Pdf
		
			         	 
    </td>        
    </tr>
    <tr>
    <tr>
    <td>Rabattyp:</td>
    <td>
    	<input type="radio" name="typavrabatt" value="ingen" <c:if test="${modelMap.kurstyp.typavrabatt == 0}">checked</c:if>> Ingen         	 
		<input type="radio" name="typavrabatt" value="procent" <c:if test="${modelMap.kurstyp.typavrabatt == 1}">checked</c:if>> Procent
		<input type="radio" name="typavrabatt" value="kronor" <c:if test="${modelMap.kurstyp.typavrabatt == 2}">checked</c:if>> Kronor         	 
    </td>        
    </tr>
    <tr>
    <td>Rabatter:</td>
    <td>
		<input type="text" size="46" name="rabatter" value="${modelMap.kurstyp.rabatter}">         	 
    </td>        
    </tr>
    <tr>
    <td>Extra:</td>
    <td>
		Resa <input type="checkbox" name="resa" value="yes" <c:if test="${modelMap.kurstyp.resehantering == true}">checked</c:if>>
		Boende <input type="checkbox" name="boende" value="yes" <c:if test="${modelMap.kurstyp.rumshantering == true}">checked</c:if>>
		Mat <input type="checkbox" name="mat" value="yes" <c:if test="${modelMap.kurstyp.mathantering == true}">checked</c:if>>         	 
    </td>        
    </tr>
    <tr>
    <td valign="top">Beskrivning:</td>
    <td>
    	<textarea rows="5" cols="40" name="info"><c:out value="${modelMap.kurstyp.info}"/></textarea> 
    </td>        
    </tr>
    <tr><td><br><br></td><td></td></tr>
  </table>
  </td><td width="50%" valign="top">
  <table bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
    <tr>
      <td colspan="2" width="100%" align="left"><b>Kursnivåer</b></td>
    </tr>
    <tr><td width="40%">
    	 <select name="niva" size="7" onchange="javascript:typform.nivavarde.value=this.options[this.selectedIndex].text">
         <c:forEach items="${modelMap.nivaer}" var="niva" varStatus="cnt">
         	<option value="<c:out value="${niva.nid}"/>" <c:if test="${cnt.count == 1}">selected</c:if>> 
         	<c:out value="${niva.namn}"/>        				   
         	</option>
         </c:forEach>
         </select>
        </td>
        <td align="left" valign="bottom">
        <input type="button" 
      			           name="nivany" 
      			           class="tablebutton" 
      			           value="Ny"
   					       title="Lägger till en ny aktivitetskod"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="if (this.value=='Lägg till') 
   						   			{
   						   				typform.niva.options.add(new Option(typform.nivavarde.value, typform.niva.length, false)); 
   						   				this.value='Ny'; 
   						   				typform.niva.disabled = false;
   						   				typform.nivavarde.disabled = true;
   						   				typform.nivaandra.disabled = false;
   						   				typform.nivaradera.value = 'Ta bort';
   						   				typform.nivaandra.disabled = false;
   						   				typform.niva.selectedIndex = typform.niva.options.size - 1;
   						   				typform.niva.focus();
   						   			} 
   						   			else 
   						   			{
   						   				typform.nivavarde.disabled=false;
   						   				typform.nivavarde.value='';
   						   				this.value='Lägg till';
   						   				typform.niva.disabled=true;
   						   				typform.nivaandra.disabled = true;
   						   				typform.nivaradera.value='Avbryt';   						   				
   						   				typform.nivavarde.focus();
   						   			}">
	   	<br>			
        <input type="button" 
      			           name="nivaandra" 
      			           class="tablebutton" 
      			           value="Ändra"
   					       title="Ändrar markerad aktivitetskod"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="if (this.value=='Spara') 
   						   			{
   						   				typform.niva.options[typform.niva.selectedIndex].text = typform.nivavarde.value; 
   						   				this.value='Ändra'; 
   						   				typform.niva.disabled = false;
   						   				typform.nivavarde.disabled = true;
   						   				typform.nivaradera.value = 'Ta bort';
   						   				typform.nivany.disabled = false;
   						   			} 
   						   			else 
   						   			{
   						   				typform.nivavarde.disabled=false;
   						   				this.value='Spara';
   						   				typform.niva.disabled=true;
   						   				typform.nivaradera.value='Avbryt';
   						   				typform.nivany.disabled = true;
   						   				typform.nivavarde.focus();
   						   			}">
	   	<br>					   
        <input type="button" 
      			           name="nivaradera" 
      			           class="tablebutton" 
      			           value="Ta bort"
   					       title="Tar bort aktivitetskoden"
   						   onMouseOver="goLite(this.form.name, this.name)"
   						   onMouseOut="goDim(this.form.name, this.name)"
   						   onClick="if (this.value == 'Avbryt')
   						   			{
   						   				typform.niva.disabled = false; 
   						   				this.value='Ta bort'; 
   						   				typform.nivaandra.value='Ändra';
   						   				typform.nivany.value='Ny';
   						   				typform.nivaandra.disabled =false;
   						   				typform.nivany.disabled =false;
   						   				typform.nivavarde.disabled = true;
   						   				
   						   			} 
   						   			else 
   						   			if (confirm('Skall du verkligen ta bort ' + typform.niva.options[typform.niva.selectedIndex].text) == 1) 
   						   			{	
   						   				typform.niva.options[typform.niva.selectedIndex] = null;
   						   				typform.niva.selectedIndex=0;
   						   				typform.niva.focus();
   						   				typform.nivavarde.value = typform.niva.options[0].text;	
   						   			}">
   						   				 
    </td></tr>
    <tr><td colspan="2">
    	<input disabled type="text" name="nivavarde" size="35" value="${modelMap.nivaer[0].namn}">
    </td></tr>
  </table>
  </td></tr>
  </table>  
</form>

</body>
</html>
