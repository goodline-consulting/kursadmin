<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  	<head>
  		<title>KursInfo</title>
  		<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  		<script language="javascript" src="basic.js"></script>
  	</head>
  	<body>
  		<h1>Ekonomirapport</h1> 
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
		    <td>&nbsp;</td>
   			<c:forEach items="${modelMap.ekonomi}" var="ekonomi" varStatus="cnt">
   				<td valign="top">
   				<b><font color="darkblue"><c:out value="${ekonomi.beteckning}"/></font></b>   				
   				</td>   		
   			</c:forEach>
   			</tr>
   			<tr>
   			<td><i>Antal elever</i></td>
   			<c:forEach items="${modelMap.ekonomi}" var="ekonomi" varStatus="cnt">
   				<td><c:if test="${cnt.count == 1}"><b></c:if><c:out value="${ekonomi.elever}"/><c:if test="${cnt.count == 1}"></b></c:if></td>   		
   			</c:forEach>
   			</tr>
   			<tr class="uddarad">
   			<td><i>Betalande elever</i></td>
   			<c:forEach items="${modelMap.ekonomi}" var="ekonomi" varStatus="cnt">
   				<td><c:if test="${cnt.count == 1}"><b></c:if><c:out value="${ekonomi.betalande}"/><c:if test="${cnt.count == 1}"></b></c:if></td>   		
   			</c:forEach>
   			</tr>
   			<tr>
   			<td><i>Break even</i></td>
   			<c:forEach items="${modelMap.ekonomi}" var="ekonomi" varStatus="cnt">
   				<td><c:if test="${cnt.count == 1}"><b></c:if><c:out value="${ekonomi.breakeven}"/><c:if test="${cnt.count == 1}"></b></c:if></td>   		
   			</c:forEach>
   			</tr>
   			<tr class="uddarad">
   			<td><i>Lokalkostnader</i></td>
   			<c:forEach items="${modelMap.ekonomi}" var="ekonomi" varStatus="cnt">
   				<td><c:if test="${cnt.count == 1}"><b></c:if><c:out value="${ekonomi.lkost}"/>:-<c:if test="${cnt.count == 1}"></b></c:if></td>   		
   			</c:forEach>
   			</tr>
   			<tr>
   			<td><i>Instruktörskostnader</i></td>
   			<c:forEach items="${modelMap.ekonomi}" var="ekonomi" varStatus="cnt">
   				<td><c:if test="${cnt.count == 1}"><b></c:if><c:out value="${ekonomi.ikost}"/>:-<c:if test="${cnt.count == 1}"></b></c:if></td>   		
   			</c:forEach>
   			</tr>
   			<tr class="uddarad">
   			<td><i>Materialkostnader</i></td>
   			<c:forEach items="${modelMap.ekonomi}" var="ekonomi" varStatus="cnt">
   				<td><c:if test="${cnt.count == 1}"><b></c:if><c:out value="${ekonomi.mkost}"/>:-<c:if test="${cnt.count == 1}"></b></c:if></td>   		
   			</c:forEach>
   			</tr>
   			<tr>
   			<td><i>Övriga kostnader</i></td>
   			<c:forEach items="${modelMap.ekonomi}" var="ekonomi" varStatus="cnt">
   				<td><c:if test="${cnt.count == 1}"><b></c:if><c:out value="${ekonomi.okost}"/>:-<c:if test="${cnt.count == 1}"></b></c:if></td>   		
   			</c:forEach>
   			</tr>
   			<tr class="uddarad">
   			<td><i>Kostnader Totalt</i></td>
   			<c:forEach items="${modelMap.ekonomi}" var="ekonomi" varStatus="cnt">
   				<td><c:if test="${cnt.count == 1}"><b></c:if><c:out value="${ekonomi.totkost}"/>:-<c:if test="${cnt.count == 1}"></b></c:if></td>   		
   			</c:forEach>
   			</tr>
   			<tr>
   			<td><i>Intäckter</i></td>
   			<c:forEach items="${modelMap.ekonomi}" var="ekonomi" varStatus="cnt">
   				<td><c:if test="${cnt.count == 1}"><b></c:if><c:out value="${ekonomi.inkomster}"/>:-<c:if test="${cnt.count == 1}"></b></c:if></td>   				   		
   			</c:forEach>
   			</tr>	
   			<tr><td colspan="<c:out value="${modelMap.antal + 1}}"/>"><hr></td></tr> 
   			<tr class="uddarad">
   			<td><i>Netto</i></td>
   			<c:forEach items="${modelMap.ekonomi}" var="ekonomi" varStatus="cnt">
   				<td><c:if test="${cnt.count == 1}"><b></c:if><c:out value="${ekonomi.netto}"/>:-<c:if test="${cnt.count == 1}"></b></c:if></td>   		
   			</c:forEach>
   			</tr>	
  </table>    					   		
  </body>
</html>