<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title>Ladda bankgirofil</title>
   <link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  <script language="javascript" src="basic.js"></script>
</head>
<body<c:if test="${!empty modelMap.error}"> onload="javascript:alert('${modelMap.error}');"</c:if>>
 <c:if test="${empty modelMap.betalningar}">
	<h1>Ladda Bankgiroinbetalningar</h1>
 </c:if>
  <c:if test="${!empty modelMap.betalningar}">
  	<c:if test="${empty modelMap.close}">
		<h1>${modelMap.antal} Bankgiroinbetalningar laddade</h1>
	</c:if>
	<c:if test="${!empty modelMap.close}">
		<h1>${modelMap.antal} Bankgiroinbetalningar registrerade</h1>
	</c:if>	
 </c:if>
 
<form name="insform" method="post" action="bgc.htm" enctype="multipart/form-data">
	<div id="menu">
    <ul>    
	   <c:if test="${empty modelMap.betalningar}">
	   		<li><a href="ekonomi.htm" target="_self" title="Stäng"><span>Stäng</span></a></li>
       		<li>&nbsp;&nbsp;Filnamn   <input type="file" size="60" name="bgcfil" accept=".txt">&nbsp;</li>
       		<li><a href="javascript:insform._action.value='reload';showPageElement('div1');insform.submit();" title="Ladda inbetalningar"><span>Ladda inbetalningar</span></a></li>
       </c:if>
       <c:if test="${!empty modelMap.betalningar}">
	   		<li><a href="ekonomi.htm" target="_self" title="Avbryt"><span>Stäng</span></a></li>
	   		 <c:if test="${empty modelMap.close}">
       			<li><a href="javascript:insform._action.value='exekvera';showPageElement('div1');insform.submit();" title="registrera inbetalningar"><span>Registrera inbetalningar</span></a></li>
       		</c:if>	
       </c:if>
    </ul>
	</div><br><br>
    <input type="hidden" name="_action">
</form>

<div id="div1" style="background: none repeat scroll 0% 0% white; padding: 8px; position: fixed; left: 25%; top:40%; margin-left:10%; border: 2px solid rgb(34, 102, 170); display: none;">
	<font color="grey" size="4">Vänta medan betalningarna läses in... </font><img src="images/wait30trans.gif"></img>
</div>
<c:if test="${!empty modelMap.betalningar}">
<form name="bgcform">   
	<!-- scrollable area -->
	<div style="height:510px; padding:6px; border-style:solid; border-width:1; overflow:auto; direction:ltr;">
	<div style="direction:ltr;"> 
	<table>
		<tr>
			<th align="left">Betaldatum&nbsp;</th><th align="left">Referens&nbsp;</th><th align="left">Inbetalare&nbsp;</th><th align="left">Summa&nbsp;</th><th align="left">Betalningssätt&nbsp;</th><c:if test="${!empty modelMap.close}"><th align="left">placerad&nbsp;</th><th align="left">Info</th></c:if>
		</tr>	    
    	<c:forEach items="${modelMap.betalningar}" var="betalning" varStatus="cnt">
    		<c:if test="${cnt.count % 2 == 0}">
    		<tr class="jamnrad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'jamnrad';">
    		</c:if>
    		<c:if test="${cnt.count % 2 != 0}">
    		<tr class="uddarad" onmouseover="this.className = 'highrow';" onmouseout="this.className = 'uddarad';">
    		</c:if>       			
      			<td><fmt:formatDate pattern="yyyy-MM-dd" value="${betalning.betaldatum}"/></td>
      			<td align="right"><c:out value="${betalning.refnr}"/></td>
      			<td><c:out value="${betalning.name}"/></td>
      			<td align="right"><fmt:formatNumber value="${betalning.amount}" type="currency"/></td>
      			
      			<td><c:out value="${betalning.betalkanal}"/></td>
      			 <c:if test="${!empty modelMap.close}">
      				<td><input type="checkbox" <c:if test="${betalning.fakturanr != 0}">checked</c:if>/></td>
      				<td><c:out value="${betalning.info}"/></td>
      			</c:if>		
      		</tr>
       </c:forEach>
    </table> 
    </div>
    </div>
 </form>
 </c:if>	
</body>
</html>
