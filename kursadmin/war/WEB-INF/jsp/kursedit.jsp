<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.*"%>
<%@ page import="kursadmin.domain.Lokal"%>


<html>
<head>
  <title>KursInfo</title>
   <link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  <script language="javascript" src="basic.js"></script>
  <script type="text/javascript">
	var multiArr = new Array();
  	var cntArr = new Array();  
  
  	function doLoad()
	{		
		<c:forEach items="${typer}" var="typ" varStatus="cnt">
         	multiArr["A${typ.tid}"] = new Array();
         	cntArr["A${typ.tid}"] = 0;
         	multiArr.length = ${cnt.count};          	       				            	
		</c:forEach>
		<c:forEach items="${nivaer}" var="niva" varStatus="cnt">
		    var ozz = new Object();		    
		    ozz.namn = "${niva.namn}";		    
		    ozz.varde = "${niva.nid}";
         	multiArr["A${niva.tid}"][cntArr["A${niva.tid}"]] = ozz;  	
         	cntArr["A${niva.tid}"]++;         				            	
		</c:forEach>			
	}
	
	function doChange(varde)
	{
		kursForm.niva.length = 0;
		kursForm.niva.options.add(new Option('Välj nivå', '0', true));
			
		for (var i = 0; i < multiArr["A" + varde].length; i++)
		{		    
			var ozz = multiArr["A" + varde][i];
			kursForm.niva.options.add(new Option(ozz.namn, ozz.varde, false));
		}	
	}
	
  </script>
</head>
<body onload="doLoad();">
<h1>KursInfo</h1>
 
<form:form name="kursForm" method="post" commandName="kursAll">
	<div id="menu">
    <ul>
       <li><a href="javascript:window.location='kurser.htm'" target="_self" title="avbryt"><span>Avbryt</span></a></li>	
       <li><a href="javascript:kursForm.reset();" target="_self" title="reset"><span>Återställ</span></a></li>
       <li><a href="javascript:kursForm.submit();" target="_self" title="spara"><span>Spara</span></a></li>
       
        <c:if test="${kursAll.antKursTillf > 1}">
        	<li><a href="javascript:kursForm._action.value='del-all';kursForm.submit();" target="_self" title="spara"><span>Radera kurstillfällen</span></a></li>
        </c:if>       	     
       <li><a href="javascript:kursForm._action.value='create';kursForm.submit();" target="_self" title="skapa"><span>Skapa kurstillfällen</span></a></li>
       <li>&nbsp;</li>
       <li>Antal: <select name="antal" onchange="javascript:kursForm._action.value='create';">
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
					<option value="7">7</option>
					<option value="8">8</option>
					<option value="9">9</option>
					<option value="10">10</option>
					<option value="11">11</option>
					<option value="12">12</option>
					<option value="13">13</option>
					<option value="14">14</option>
					<option value="15">15</option>
				</select>          		
       </li>
    </ul>
	</div>
	<br><br>
  <table border="0" ><tr><td valign="top">		
  <table bgcolor="f8f8ff" border="0" cellspacing="0" cellpadding="5">
    <tr>
      <td width="20%" align="right">Beteckning:</td>
        <td width="20%">
          <form:input size="11" path="beteckning"/>
        </td>
        <td>
          <form:errors path="beteckning" cssClass="error"/>
        </td>       
    </tr>
      
    <tr>
      <td align="right">Kursnamn:</td>
        <td>
          <form:input size="40" path="kursnamn"/>
        </td>
        <td>
          <form:errors path="kursnamn" cssClass="error"/>
        </td>       
    </tr>   
     <tr>
     <td align="right">Kurstyp</td>
        <td>
          <form:select path="tid" onchange="javascript:doChange(this.value);">
				<c:if test="${kursAll.tid == 0}">
					<form:option value="0" label="Välj kurstyp"/>
				</c:if>          
				<form:options items="${typer}" itemValue="tid" itemLabel="namn"/>
		  </form:select>
        </td>
        <td><form:errors path="tid" cssClass="error"/></td> </td>  
    </tr>   
     <tr>           
      <td align="right">Nivå:</td>
        <td>
        	<form:select  path="niva">
        		<c:if test="${kursAll.niva == 0}">
					<form:option value="0" label="Välj nivå"/>
				</c:if>
				<c:forEach items="${nivaer}" var="niva" varStatus="cnt">
					<c:if test="${niva.tid == kursAll.tid}">
						<form:option value="${niva.nid}" label="${niva.namn}"/>
					</c:if>
				</c:forEach>
			</form:select>
        </td>  
        <td><form:errors path="niva" cssClass="error"/></td> </td>                
    </tr>   
     <tr>           
      <td align="right">Status:</td>
        <td>
        	<form:select  path="status">
        		<form:option value="0" label="Bokningsbar" />
				<form:option value="1" label="Ej Bokningsbar"/>
				<form:option value="2" label="Fullsatt"/>
			</form:select>
        </td>  
        <td><form:errors path="niva" cssClass="error"/></td> </td>                
    </tr>   
    <tr>
      <td align="right">Instruktör</td>
        <td>
          <form:select path="instruktor">
          		<c:if test="${kursAll.instruktor == 0}">
					<form:option value="0" label="Välj instruktör"/>
				</c:if>      
				<form:options items="${instruktorer}" itemValue="iid" itemLabel="namn"/>
			</form:select>
        </td>
        <td><form:errors path="instruktor" cssClass="error"/></td>       
    </tr>
    <tr>
      <td align="right">Plats:</td>
        <td>
          <form:select path="lokal">
          		<c:if test="${kursAll.lokal == 0}">
					<form:option value="0" label="Välj lokal"/>
				</c:if>      
				<form:options items="${lokaler}" itemValue="lid" itemLabel="lokalnamn"/>
			</form:select>
        </td>
        <td><form:errors path="lokal" cssClass="error"/></td></td>       
    </tr>
    <tr>
      <td align="right">Veckodag:</td>
        <td>
          <form:select path="veckodag">
				<form:option value="Söndag" label="Söndag"/>
				<form:option value="Måndag" label="Måndag"/>
				<form:option value="Tisdag" label="Tisdag"/>
				<form:option value="Onsdag" label="Onsdag"/>
				<form:option value="Torsdag" label="Torsdag"/>
				<form:option value="Fredag" label="Fredag"/>
				<form:option value="Lördag" label="Lördag"/>
			</form:select>
        </td>
        <td></td>       
    </tr>  
     <tr>
      <td align="right">Starttid:</td>
        <td>
          <form:input size="4" path="starttid"/>
        </td>
        <td>
          <form:errors path="starttid" cssClass="error"/>
        </td>       
    </tr>
     <tr>
      <td align="right">Längd:</td>
        <td>
          <form:input size="4" path="lengd"/> minuter 
        </td>
        <td>
          <form:errors path="lengd" cssClass="error"/>
        </td>       
     </tr>     
     <tr>
      <td align="right">Pris:</td>
        <td>
          <form:input size="4" path="pris"/> Kr
        </td>
        <td>
          <form:errors path="pris" cssClass="error"/>
        </td>       
     </tr>
     <tr>
      <td align="right">Lokalkostnad:</td>
        <td>
          <form:input size="4" path="lkost"/> Kr
        </td>
        <td>
          <form:errors path="lkost" cssClass="error"/>
        </td>       
     </tr>
     <tr>
      <td align="right">Instruktörskostnad:</td>
        <td>
          <form:input size="4" path="ikost"/> Kr
        </td>
        <td>
          <form:errors path="ikost" cssClass="error"/>
        </td>       
     </tr>
     <tr>
      <td align="right">Materialkostnad:</td>
        <td>
          <form:input size="4" path="mkost"/> Kr
        </td>
        <td>
          <form:errors path="mkost" cssClass="error"/>
        </td>       
     </tr>
     <tr>
      <td align="right">Övriga kostnader:</td>
        <td>
          <form:input size="4" path="okost"/> Kr
        </td>
        <td>
          <form:errors path="okost" cssClass="error"/>
        </td>       
     </tr>
     <c:if test="${kursAll.rumshantering}"> 
	   <tr>
	      <td align="right">Boendealternativ:</td>
	        <td>
	          <form:input size="40" path="rumsalternativ"/>
	        </td>
	        <td>
	          <form:errors path="rumsalternativ" cssClass="error"/>
	        </td>       
     	</tr>
     </c:if>
     <c:if test="${kursAll.resehantering}"> 
	   <tr>
	      <td align="right">Resealternativ:</td>
	        <td>
	          <form:input size="40" path="resealternativ"/>
	        </td>
	        <td>
	          <form:errors path="resealternativ" cssClass="error"/>
	        </td>       
     	</tr>
     </c:if>
     <c:if test="${kursAll.mathantering}"> 
	   <tr>
	      <td align="right">Matalternativ:</td>
	        <td>
	          <form:input size="40" path="matalternativ"/>
	        </td>
	        <td>
	          <form:errors path="matalternativ" cssClass="error"/>
	        </td>       
     	</tr>
     </c:if>
    <tr><td colspan="3">&nbsp;</td></tr>
   </table></td><td valign="top">
    <!-- scrollable area -->
	<div style="height:80%;  width:100%; padding:6px; border-style:solid; border-width:1; overflow:auto; direction:ltr;">
	<div style="direction:ltr;"> 
   <table width="100%" border="0"> 
   <tr><td></td><td><div class="tableheader">KursTillfällen</div></td></tr>
  
   <c:forEach items="${kursAll.kurstillf}" var="kurstillf" varStatus="cnt">
   		<tr>		
   		<td align="right"><c:out value="${cnt.count}"/></td>
 	<td><form:input size="10" path='kurstillf[${cnt.count -1}]'/>
   		&nbsp   		
   		 <c:if test="${kursAll.antKursTillf > 1}">
   			<input type="button" 
      		       name="R<c:out value="${cnt.count}"/>" 
      		       class="tablebutton" 
      		       value="Ta bort"
   			       title="Tar bort kurstillfället"
   			       onMouseOver="goLite(this.form.name, this.name)"
   				   onMouseOut="goDim(this.form.name, this.name)"
   				   onClick="kursForm._action.value='del-item';kursForm._item.value='<c:out value="${cnt.count}"/>';kursForm.submit()">
        </c:if>
   	    </td>   				   
   		<td><form:errors path="kurstillf[${cnt.count -1}]" cssClass="error"/></td>					   
        </td>
        </tr>		   		
   </c:forEach>	
   </div></div>
  </table>
  </td></tr></table>
  <br>
  <input type="hidden" name="_action" value="submit">
  <input type="hidden" name="_item">
</form:form>
</body>
</html>
