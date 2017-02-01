<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
  <head>
  	<title>Instrukt�rssidor</title>
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

   <h1>Lucky Lines Instrukt�rssida</h1>  
   	
   	<h2>Just nu har vi ${modelMap.antElever} elever anm�lda till v�ra kurser</h2>
	 
	<table>
		<tr><th align="left" colspan=4>Kursniv�er och beteckningar</th></tr>
		<tr><td>Fr.o m VT2009 s� har vi nya kursbeteckningar uppbyggda enligt f�ljande:</td></tr>
		<tr><td><i>�rtal Termin Lokal - niv� grupp</i></td></tr>
		<tr><td>Ex <b>2011HTPU-1A </b>�r nyb�rjarkursen i Pumpan, h�sterminen 2011.</td></tr>
		<tr><td>Hade vi haft tv� nyb�rjarkurser i Pumpan s� hade den andra hetat 2011HTPU-1B</td></tr>
		<tr><td>Lokalernas f�rkortning �r: AR = �rsta,  PU = Pumpan, SB = Sumpan, NA = Orminge, AS = H�gerstens�sen, GB = Gubb�ngen, SK = Sk�ndal</td></tr>
		<tr><td>Niv�erna �r:</td></tr>
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
		<tr><td>Moms p� kursavgifter: <b>1200:-</b> (67,92:-) <b>1250:-</b> (70,75:-) F�r andra belopp ta kursavgiften multiplicerat med 0,0566 s� f�r ni momsbeloppet</td></tr>
		<tr><th align="left" colspan=4>Dokument</th></tr>	
		<tr><td> <a href="../docs/kurshafte.pdf">Kursh�ftet</a> f�r nyb�rjarkurserna</td></tr>
		<tr><td> <a href="../docs/kursflyer.pdf">Kursflyer</a> f�r nyb�rjarkurserna</td></tr>
		<tr><td>S� h�r l�gger man in <a href="../docs/instruktorer.htm">utl�rda danser i dansjournalen</a></td></tr>
		<tr><td>S� h�r skapar man en <a href="../docs/skapadanslista.htm">danslista</a></td></tr>
		<tr><td>Grunddanser <a href="../docs/grunddanser_newcomer2.htm">newcomer 2</a></td></tr>
		<tr><td>Grunddanser <a href="../docs/grunddanser_newcomer3.htm">newcomer 3</a></td></tr>
		<tr><td>Grunddanser <a href="../docs/grunddanser_improver.htm">Improver</a></td></tr>
		<tr><th align="left" colspan=4>Elevregister</th></tr>
		<tr><td colspan=4><a href="/kursadmin/personer.htm?homepage=yes">H�r</a> kan du bl.a se vilka kurser en elev har g�tt</td></tr>
		<tr><th align="left" colspan=4>Tollare, Julfest mm</th></tr>
		<tr><td>
		    <a href="/kursadmin/nvaro.htm?kid=632&homepage=yes">LuckyLines 15 �rs Jubileum 19 - 21 Aug 2016</a>, &nbsp;
		    <a href="/kursadmin/nvaro.htm?kid=631&homepage=yes">Tollare Kursg�rd 30:e April 2016</a>, &nbsp;
			<a href="/kursadmin/nvaro.htm?kid=615&homepage=yes">Julfesten 2015</a>,	&nbsp;
		    <a href="/kursadmin/nvaro.htm?kid=614&homepage=yes">Tollare Kursg�rd 17 Okt 2015</a>, &nbsp;	
			<a href="/kursadmin/nvaro.htm?kid=598&homepage=yes">Alviks Kulturhus 2015</a>, &nbsp;
			<a href="/kursadmin/nvaro.htm?kid=583&homepage=yes">Julfesten 2014</a>, &nbsp;
			<a href="/kursadmin/nvaro.htm?kid=582&homepage=yes">Tollare Kursg�rd 25 Okt 2014</a>, &nbsp;
			<a href="/kursadmin/nvaro.htm?kid=563&homepage=yes">Tollare Kursg�rd 1:a Maj 2014</a>, &nbsp;
			<a href="/kursadmin/nvaro.htm?kid=542&homepage=yes">Julfesten 2013</a></br>	
		    
						
		</tr>
		<tr><th align="left" colspan=4>N�rvarolistor �rsta</th></tr>
		<tr><td><select name="arsta" id="arsta" onchange="javascript:doChange(this.value);">
			<option value="0" selected>&lt;V�lj kurs&gt;</option>	
			<c:forEach items="${modelMap.arsta}" var="kurs" varStatus="cnt">
		    		<option value="${kurs.kid}">${kurs.beteckning}</option>		    	
		    </c:forEach>		
	    </select></td></tr> 
		<!--
	    <tr><th align="left" colspan=4>N�rvarolistor Pumpan</th></tr>
		<tr><td><select name="pumpan" id="pumpan" onchange="javascript:doChange(this.value);">
			<option value="0" selected>&lt;V�lj kurs&gt;</option>	
			<c:forEach items="${modelMap.pumpan}" var="kurs" varStatus="cnt">
		    		<option value="${kurs.kid}">${kurs.beteckning}</option>		    	
		    </c:forEach>		
	    </select></td></tr>
	    -->   
	      
	    <tr><th align="left" colspan=4>N�rvarolistor Sundbyberg</th></tr>
		<tr><td><select name="sumpan" id="sumpan" onchange="javascript:doChange(this.value);">
			<option value="0" selected>&lt;V�lj kurs&gt;</option>	
			<c:forEach items="${modelMap.sumpan}" var="kurs" varStatus="cnt">
		    		<option value="${kurs.kid}">${kurs.beteckning}</option>		    	
		    </c:forEach>		
	    </select></td></tr>
	      <!--  
	    <tr><th align="left" colspan=4>N�rvarolistor �lta</th></tr>
		<tr><td><select name="alta" id="alta" onchange="javascript:doChange(this.value);">
			<option value="0" selected>&lt;V�lj kurs&gt;</option>	
			<c:forEach items="${modelMap.alta}" var="kurs" varStatus="cnt">
		    		<option value="${kurs.kid}">${kurs.beteckning}</option>		    	
		    </c:forEach>		
	    </select></td></tr>
	   
	    <tr><th align="left" colspan=4>N�rvarolistor H�gerstens�sen</th></tr>
		<tr><td><select name="hagersten" id="hagersten" onchange="javascript:doChange(this.value);">
			<option value="0" selected>&lt;V�lj kurs&gt;</option>	
			<c:forEach items="${modelMap.hagersten}" var="kurs" varStatus="cnt">
		    		<option value="${kurs.kid}">${kurs.beteckning}</option>		    	
		    </c:forEach>		
	    </select></td></tr>
	     -->  
	    <tr><th align="left" colspan=4>N�rvarolistor Gubb�ngen</th></tr>
		<tr><td><select name="gubben" id="gubben" onchange="javascript:doChange(this.value);">
			<option value="0" selected>&lt;V�lj kurs&gt;</option>	
			<c:forEach items="${modelMap.gubben}" var="kurs" varStatus="cnt">
		    		<option value="${kurs.kid}">${kurs.beteckning}</option>		    	
		    </c:forEach>		
	    </select></td></tr>
	    <!--      
		<tr><th align="left" colspan=4>N�rvarolistor Sk�ndal</th></tr>
		<tr><td><select name="skondal" id="skondal" onchange="javascript:doChange(this.value);">
			<option value="0" selected>&lt;V�lj kurs&gt;</option>	
			<c:forEach items="${modelMap.skondal}" var="kurs" varStatus="cnt">
		    		<option value="${kurs.kid}">${kurs.beteckning}</option>		    	
		    </c:forEach>		
	    </select></td></tr>
	    -->    
		<tr><th align="left" colspan=4>Senaste musiken</th></tr>
		<tr><td>H�r kan du hitta <a href="../docs/newmusic.htm">musiken</a> till de senaste danserna</td></tr>		       
	</table> 
  </body>
</html>
