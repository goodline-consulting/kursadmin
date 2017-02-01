<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Byt Kurs</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
 </head>
  <body>
  

   <h1>Flytta elev till ${modelMap.newKurs.beteckning}</h1>  
   <form name="searchform"> 
   <div id="menu">
    <ul>
       <li><a href="javascript:window.close()" target="_self" title="stäng"><span>Avbryt</span></a></li>       
       <li><a href="javascript:bytkursform.submit()" target="_self" title="utför"><span>Utför</span></a></li>       
    </ul>
	</div>
	<br><br>
	</form>
	<form name="bytkursform">
		 <c:if test="${not empty param.dyrare}">
		 		 <h2>Den kurs som <i>${modelMap.namn}</i> byter till är dyrare än befintlig kurs.</h2>
		 		 <h3>Befintlig kurs: ${modelMap.oldKurs.pris}:- Ny kurs: ${modelMap.newKurs.pris}:-</h3>
		 		 <h3>Vilken åtgärd skall vidtagas?</h3>
		 		<c:if test="${param.dyrare == 'obetald'}">
		 			<input type="radio" name="todo" value="omfakturera" checked>Räkna om befintlig faktura <c:if test="${not empty modelMap.email}">&nbsp;&nbsp;<input type="checkbox"  name="maila" value="maila"/>Maila kunden</c:if><br>
				</c:if>
				<c:if test="${param.dyrare == 'betald'}">
					<input type="radio" name="todo" value="tillagg" checked>Tilläggsfakturera med <input type="text" size="5" name="belopp" value="${modelMap.belopp}"></input>&nbsp;Kr<c:if test="${not empty modelMap.email}">&nbsp;&nbsp;<input type="checkbox"  name="maila" value="maila"/>Maila kunden</c:if><br>
				</c:if>
				<input type="radio" name="todo" value="kundf">Lägg beloppet <input type="text" size="5" name="belopp" value="${modelMap.belopp}"></input>&nbsp;Kr som kundfordran<br>	
				<input type="radio" name="todo" value="inget">Ingen åtgärd (Sänk priset eller hantera manuellt)
				
		 </c:if>
		 <c:if test="${not empty param.billigare}">
		 		 <h2>Den kurs som <i>${modelMap.namn}</i> byter till är billigare än befintlig kurs.</h2>
		 		 <h3>Befintlig kurs: ${modelMap.oldKurs.pris}:- Ny kurs: ${modelMap.newKurs.pris}:-</h3>
		 		 <h3>Vilken åtgärd skall vidtagas?</h3>
		 		<c:if test="${param.billigare == 'obetald'}">
		 			<input type="radio" name="todo" value="omfakturera" checked>Räkna om befintlig faktura <c:if test="${not empty modelMap.email}">&nbsp;&nbsp;<input type="checkbox"  name="maila" value="maila"/>Maila kunden</c:if><br>
				</c:if>
				<c:if test="${param.billigare == 'betald'}">
					<input type="radio" name="todo" value="kundt" checked>Lägg beloppet <input type="text" size="5" name="belopp" value="${modelMap.belopp}"></input>&nbsp;Kr som kund tillgodo<br>	
				</c:if>
				
				<input type="radio" name="todo" value="inget">Ingen åtgärd (höj priset eller hantera manuellt)
		 </c:if>  
    </form> 
  </body>
</html>

