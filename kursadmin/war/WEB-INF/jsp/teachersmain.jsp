<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Instruktörssidor</title>
  	<link rel="stylesheet" href="kursadmin_style.css" type="text/css" />
  	<script language="javascript" src="basic.js"></script>
 </head>
  <body>
  <script type="text/javascript">
	
	function doChange(varde)
	{
		if (varde == "0")
			alert("nada") 
	    else
			window.location = 'nvaro.htm?kid=' + varde + '&homepage="yes"';	
	}
	
  </script>

   <h1>Lucky Lines Instruktörssida</h1>  
   	
   	<h2>Just nu har vi ${modelMap.antElever} elever anmälda till våra kurser</h2>
	 
	<table>
		<tr><th align="left" colspan=4>Kursnivåer och beteckningar</th></tr>
		<tr><td>Fr.o m VT2009 så har vi nya kursbeteckningar uppbyggda enligt följande:</td></tr>
		<tr><td><i>Årtal Termin Lokal - nivå grupp</i></td></tr>
		<tr><td>Ex <b>2011HTPU-1A </b>är nybörjarkursen i Pumpan, hösterminen 2011.</td></tr>
		<tr><td>Hade vi haft två nybörjarkurser i Pumpan så hade den andra hetat 2011HTPU-1B</td></tr>
		<tr><td>Lokalernas förkortning är: AR = Årsta,  PU = Pumpan, SB = Sumpan, NA = Orminge, AS = Hägerstensåsen, GB = Gubbängen, SK = Sköndal</td></tr>
		<tr><td>Nivåerna är:</td></tr>
		<tr><td>1 = Newcomer I, </td></tr>
		<tr><td>2 = Newcomer II, </td></tr>
		<tr><td>3 = Newcomer III,</td></tr>
	    <tr><td>4 = Improver, </td></tr>
	    <tr><td>5 = Improver Plus</td></tr>
	    <tr><td>6 = Novice</td></tr>
	    <tr><td>7 = Novice Plus</td></tr>
	    <tr><td>8 = Intermediate</td></tr>
	    <tr><td>9 = Intermediate Plus</td></tr>
	    <tr><td>10 = Advanced</td></tr>
		<tr><th align="left" colspan=4>Viktiga nummer</th></tr>
		<tr><td>Lucky Lines organisationnummer: 8024106596, Klubbens Plusgiro: 406801-1</td></tr>
		<tr><td>GoodLines organisationsnummer: 556793-8716, Bankgiro: 471-7575</td></tr>
		<tr><td>Moms på kursavgifter: <b>1200:-</b> (67,92:-) <b>1250:-</b> (70,75:-) För andra belopp ta kursavgiften multiplicerat med 0,0566 så får ni momsbeloppet</td></tr>
		<tr><th align="left" colspan=4>Dokument</th></tr>	
		<tr><td> <a href="../docs/kurshafte.pdf">Kurshäftet</a> för nybörjarkurserna</td></tr>
		<tr><td> <a href="../docs/kursflyer.pdf">Kursflyer</a> för nybörjarkurserna</td></tr>
		<tr><td>Så här lägger man in <a href="../docs/instruktorer.htm">utlärda danser i dansjournalen</a></td></tr>
		<tr><td>Så här skapar man en <a href="../docs/skapadanslista.htm">danslista</a></td></tr>
		<tr><td>Grunddanser <a href="../docs/grunddanser_newcomer2.htm">newcomer 2</a></td></tr>
		<tr><td>Grunddanser <a href="../docs/grunddanser_newcomer3.htm">newcomer 3</a></td></tr>
		<tr><td>Grunddanser <a href="../docs/grunddanser_improver.htm">Improver</a></td></tr>
		<tr><th align="left" colspan=4>Elevregister</th></tr>
		<tr><td colspan=4><a href="/kursadmin/personer.htm?homepage=yes">Här</a> kan du bl.a se vilka kurser en elev har gått</td></tr>
		<tr><th align="left" colspan=4>Tollare, Julfest mm</th></tr>
		<tr><td>
		    <a href="/kursadmin/nvaro.htm?kid=632&homepage=yes">LuckyLines 15 års Jubileum 19 - 21 Aug 2016</a>, &nbsp;
		    <a href="/kursadmin/nvaro.htm?kid=631&homepage=yes">Tollare Kursgård 30:e April 2016</a>, &nbsp;
			<a href="/kursadmin/nvaro.htm?kid=615&homepage=yes">Julfesten 2015</a>,	&nbsp;
		    <a href="/kursadmin/nvaro.htm?kid=614&homepage=yes">Tollare Kursgård 17 Okt 2015</a>, &nbsp;	
			<a href="/kursadmin/nvaro.htm?kid=598&homepage=yes">Alviks Kulturhus 2015</a>, &nbsp;
			<a href="/kursadmin/nvaro.htm?kid=583&homepage=yes">Julfesten 2014</a>, &nbsp;
			<a href="/kursadmin/nvaro.htm?kid=582&homepage=yes">Tollare Kursgård 25 Okt 2014</a>, &nbsp;
			<a href="/kursadmin/nvaro.htm?kid=563&homepage=yes">Tollare Kursgård 1:a Maj 2014</a>, &nbsp;
			<a href="/kursadmin/nvaro.htm?kid=542&homepage=yes">Julfesten 2013</a></br>	
		    
						
		</tr>
		<tr><th align="left" colspan=4>Närvarolistor Årsta</th></tr>
		<tr><td><select name="arsta" id="arsta" onchange="javascript:doChange(this.value);">
			<option value="0" selected>&lt;Välj kurs&gt;</option>	
			<c:forEach items="${modelMap.arsta}" var="kurs" varStatus="cnt">
		    		<option value="${kurs.kid}">${kurs.beteckning}</option>		    	
		    </c:forEach>		
	    </select></td></tr> 
		<!--
	    <tr><th align="left" colspan=4>Närvarolistor Pumpan</th></tr>
		<tr><td><select name="pumpan" id="pumpan" onchange="javascript:doChange(this.value);">
			<option value="0" selected>&lt;Välj kurs&gt;</option>	
			<c:forEach items="${modelMap.pumpan}" var="kurs" varStatus="cnt">
		    		<option value="${kurs.kid}">${kurs.beteckning}</option>		    	
		    </c:forEach>		
	    </select></td></tr>
	    -->   
	      
	    <tr><th align="left" colspan=4>Närvarolistor Sundbyberg</th></tr>
		<tr><td><select name="sumpan" id="sumpan" onchange="javascript:doChange(this.value);">
			<option value="0" selected>&lt;Välj kurs&gt;</option>	
			<c:forEach items="${modelMap.sumpan}" var="kurs" varStatus="cnt">
		    		<option value="${kurs.kid}">${kurs.beteckning}</option>		    	
		    </c:forEach>		
	    </select></td></tr>
	      <!--  
	    <tr><th align="left" colspan=4>Närvarolistor Älta</th></tr>
		<tr><td><select name="alta" id="alta" onchange="javascript:doChange(this.value);">
			<option value="0" selected>&lt;Välj kurs&gt;</option>	
			<c:forEach items="${modelMap.alta}" var="kurs" varStatus="cnt">
		    		<option value="${kurs.kid}">${kurs.beteckning}</option>		    	
		    </c:forEach>		
	    </select></td></tr>
	   
	    <tr><th align="left" colspan=4>Närvarolistor Hägerstensåsen</th></tr>
		<tr><td><select name="hagersten" id="hagersten" onchange="javascript:doChange(this.value);">
			<option value="0" selected>&lt;Välj kurs&gt;</option>	
			<c:forEach items="${modelMap.hagersten}" var="kurs" varStatus="cnt">
		    		<option value="${kurs.kid}">${kurs.beteckning}</option>		    	
		    </c:forEach>		
	    </select></td></tr>
	     -->  
	    <tr><th align="left" colspan=4>Närvarolistor Gubbängen</th></tr>
		<tr><td><select name="gubben" id="gubben" onchange="javascript:doChange(this.value);">
			<option value="0" selected>&lt;Välj kurs&gt;</option>	
			<c:forEach items="${modelMap.gubben}" var="kurs" varStatus="cnt">
		    		<option value="${kurs.kid}">${kurs.beteckning}</option>		    	
		    </c:forEach>		
	    </select></td></tr>
	    <!--      
		<tr><th align="left" colspan=4>Närvarolistor Sköndal</th></tr>
		<tr><td><select name="skondal" id="skondal" onchange="javascript:doChange(this.value);">
			<option value="0" selected>&lt;Välj kurs&gt;</option>	
			<c:forEach items="${modelMap.skondal}" var="kurs" varStatus="cnt">
		    		<option value="${kurs.kid}">${kurs.beteckning}</option>		    	
		    </c:forEach>		
	    </select></td></tr>
	    -->    
		<tr><th align="left" colspan=4>Senaste musiken</th></tr>
		<tr><td>Här kan du hitta <a href="../docs/newmusic.htm">musiken</a> till de senaste danserna</td></tr>		       
	</table> 
  </body>
</html>
