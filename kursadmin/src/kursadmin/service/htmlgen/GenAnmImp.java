package kursadmin.service.htmlgen;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import kursadmin.domain.GrundConfig;
import kursadmin.domain.KursAll;
import kursadmin.domain.Lokal;
import kursadmin.domain.PathConfig;
import kursadmin.service.GenAnmInterface;

public class GenAnmImp implements GenAnmInterface 
{


	public String GenAnmWeb(List<KursAll> kursLista, List<Lokal> lokalLista, PathConfig paths, GrundConfig grundConfig) 
	{
		StringBuffer htmlBuff = new StringBuffer();
		htmlBuff.append("<html>\n\t<head>\n"); 
		
		// Workaround för att det inte blir svenska tecken på inmatade data:
		// Plocka in filen i dreamweaver och ändra charset=utf-8 till IS0-8859-1
		htmlBuff.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>\n"); 
		//htmlBuff.append("<meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>\n"); 
		if (grundConfig.getStyle() != null && !grundConfig.getStyle().equals(""))
			htmlBuff.append("<link rel='stylesheet' href='" + grundConfig.getStyle() + "' type='text/css'/>\n");
			//htmlBuff.append("<link rel='stylesheet' href='kursadmin_style.css' type='text/css'/>\n");
     	htmlBuff.append("<script language='javascript' src='basic.js'></script>\n");
     	htmlBuff.append("<script language='javascript'>\n");
     	htmlBuff.append("function validate()\n");
     	htmlBuff.append("{\n");
     	htmlBuff.append("	if (document.anmalan.fnamn.value == '')\n");
     	htmlBuff.append("	{\n");
     	htmlBuff.append("		alert('Du måste fylla i förnamn');\n");
     	htmlBuff.append("		document.anmalan.fnamn.focus();\n");
     	htmlBuff.append("		return false;\n");
     	htmlBuff.append("	}\n");
     	htmlBuff.append("   if (document.anmalan.enamn.value == '')\n");
     	htmlBuff.append("	{\n");
     	htmlBuff.append("		alert('Du måste fylla i efternamn');\n");
     	htmlBuff.append("		document.anmalan.enamn.focus();\n");
     	htmlBuff.append("		return false;\n");
     	htmlBuff.append("	}\n");
     	htmlBuff.append("	if (document.anmalan.email.value == '')\n");
     	htmlBuff.append("	{\n");
     	htmlBuff.append("		alert('Du måste ange en mailadress');\n");
     	htmlBuff.append("		document.anmalan.email.focus();\n");
     	htmlBuff.append("		return false;\n");
     	htmlBuff.append("	}\n");	
     	htmlBuff.append("	else if (!echeck(document.anmalan.email.value))\n");
     	htmlBuff.append("	{\n");
     	htmlBuff.append("		alert('Felaktigt angiven emailadress!');\n");
     	htmlBuff.append("		return false;\n");
     	htmlBuff.append("	}\n");
     	htmlBuff.append("   var i = 0;\n");
     	htmlBuff.append("   var J;\n");
     	htmlBuff.append("   anmalan.valdkurs.value = '';");
     	htmlBuff.append("	for (j = 0; j < document.anmalan.kursval.length; j++)\n");
     	htmlBuff.append("	{\n");
     	htmlBuff.append("		if (document.anmalan.kursval[j].checked)\n");	
     	htmlBuff.append("		{\n");
     	htmlBuff.append("			i++;\n");
     	htmlBuff.append("			anmalan.valdkurs.value += document.anmalan.kursval[j].value + ',';");
     	htmlBuff.append("		}\n");
     	htmlBuff.append("	}\n");
     	htmlBuff.append("  	if (i == 0)\n");
     	htmlBuff.append("   {\n");
     	htmlBuff.append("		alert('Du måste välja en kurs');\n");
     	htmlBuff.append("		return false;\n");
     	htmlBuff.append("	}\n");
     	htmlBuff.append("   if (i > 1)	\n");
     	htmlBuff.append("	{\n");
     	htmlBuff.append("		if (confirm('Skall du verkligen anmäla dig till ' + i + ' kurser?') == 0)\n");		
     	htmlBuff.append("			return false;");
     	htmlBuff.append("	}\n"); 
     	htmlBuff.append("	anmalan.valdkurs.value = anmalan.valdkurs.value.substring(0, anmalan.valdkurs.value.length - 1);\n");
     	htmlBuff.append("	hidePageElement('rubbet')\n");
     	htmlBuff.append("	showPageElement('div1')\n");	
     	htmlBuff.append("	return true;\n");	
     	htmlBuff.append("}\n");
        htmlBuff.append("</script>\n");
     	htmlBuff.append("<title>Kursanmälan</title>\n\t</head>\n\t<body>");
     	htmlBuff.append("<div id='div1' style='background: none repeat scroll 0% 0% white; padding: 8px; position: fixed; left: 25%; top:40%; margin-left:10%; border: 2px solid rgb(34, 102, 170); display: none;'>");
     	htmlBuff.append("<font color='grey' size='4'>Vänta medan din anmälan behandlas... </font><img src='/images/wait30trans.gif'></div><div id='rubbet'>");
     	htmlBuff.append("<form method='POST' name='anmalan' action='" + grundConfig.getAnmActionUrl() +"'>\n");
     	//htmlBuff.append("<form method='POST' name='anmalan' action='/kursadmin/anmalan.htm'>\n");	
		//htmlBuff.append("<form method='POST' name='anmalan' action='http://localhost:8080//kursadmin/anmalan.htm'>\n");
		htmlBuff.append("<table  style='border:2px #000000 solid;' border='0' width='100%' id='table1'>\n");
		htmlBuff.append("	<tr>\n");
		htmlBuff.append("	<td align='center' valign='top'><b><font face='Verdana' size='5' color='darkblue'>\n");
		htmlBuff.append("<img src='../images/kuvert.jpg' width='100' height='100' align='left'> KURSANMÄLAN</font></b><p><font face='Verdana' size='2'>Fyll i alla oblligatoriska uppgifter (markerade med *) och tryck sedan på skicka. <br>När kursavgiften är inbetald får du en bekräftan skickad till den email adress som du angett.<br>\n");
		htmlBuff.append("OBS! Anmälan är endast preliminär tills kursavgiften betalas till<br><strong>Bankgiro 471-7575 </strong> (GoodLine Consulting AB)</font>\n");            
		htmlBuff.append("		<p><font face='Verdana' size='2' color='darkblue'><strong>OBS! Det dröjer en liten stund när du tryckt på &quot;Skicka&quot; knappen.<br> Ha tålamod och vänta tills en skylt med &quot;Tack för din anmälan&quot; dyker upp.</strong></font></td>\n");
		htmlBuff.append("</tr></table>\n");
		htmlBuff.append("<table border='0' width='100%'><tr>\n");
		htmlBuff.append("		<td width='13%'>&nbsp;</td>\n");
		htmlBuff.append("		<td width='87%'>&nbsp;</td>\n");
		htmlBuff.append("	</tr>\n");
		htmlBuff.append("	<tr>\n");
	    htmlBuff.append("		<td width='13%'><font face='Verdana' size='2'>*Förnamn:</font></td>\n");
   		htmlBuff.append("		<td width='87%'><input type='text' name='fnamn' size='37'></td>\n");
		htmlBuff.append("</tr>\n");
		htmlBuff.append("	<tr>\n");
		htmlBuff.append("		<td width='13%'><font face='Verdana' size='2'>*Efternamn:</font></td>\n");
		htmlBuff.append("		<td width='87%'><input type='text' name='enamn' size='37'></td>\n");
	    htmlBuff.append("	</tr>\n");
	    htmlBuff.append("		<td width='13%'><font face='Verdana' size='2'>Telefon</font></td>\n");
	    htmlBuff.append("		<td width='87%'><input type='text' name='telefon' size='20'></td>\n");
        htmlBuff.append("	</tr>\n");
	    htmlBuff.append("	<tr>\n");
	    htmlBuff.append("		<td width='13%'><font face='Verdana' size='2'>*E-mail</font></td>\n");
	    htmlBuff.append("		<td width='87%'><input type='text' name='email' size='37'></td>\n");
	    htmlBuff.append("	</tr>\n");
	    htmlBuff.append("	<tr>\n");
	    htmlBuff.append("		<td width='13%'>&nbsp;</td>\n");
	    htmlBuff.append("		<td width='87%'><font face='Verdana' size='2'><br>\n");
        htmlBuff.append("		Frågor eller information:<br>\n");
	    htmlBuff.append("		</font><textarea rows='5' name='info' cols='72'></textarea></td>\n");
	    htmlBuff.append("	</tr><tr><td colspan='2'>&nbsp;</td></tr>\n");

        for (Lokal lokal : lokalLista)
        {     
        	htmlBuff.append("<tr><td bgcolor='#C0C0C0' colspan='2'><b> <font face='Verdana' size='2'>");
        	htmlBuff.append(lokal.getLokalnamn());
        	htmlBuff.append("</font></b></td></tr>\n");
        	htmlBuff.append("<tr><td><font face='Verdana' size='2'>V&auml;lj kurs</font></td>\n");
        	
        	for (KursAll ka: kursLista)
        	{
        		if (ka.getLokal() == lokal.getLid())
        		{	
        			//htmlBuff.append("<option value='" + ka.getKid() + "'>" + ka.getKursnamn() + " (Start " + new SimpleDateFormat("d/M").format(ka.getStartdatum()) + "), " + ka.getVeckodag() + "ar " + ka.getStarttid() + "</option>\n");
        			if (ka.getStatus() == 0)
            		{	
            			htmlBuff.append("<tr><td>&nbsp;</td><td><input type='checkbox' name='kursval' value='" + ka.getKid() + "'>" + ka.getKursnamn() + ", " + ka.getLokalNamn() + " (Start " + new SimpleDateFormat("d/M").format(ka.getStartdatum()) + "), " + ka.getVeckodag() + "ar " + ka.getStarttid() + "</td></tr>\n");
            			
            		}	
        		}	
        	}
        	//htmlBuff.append("</select></td></tr><tr><td>&nbsp;</td><td>&nbsp;</td></tr>\n");
        }
        //htmlBuff.append("</table><input type=hidden name='recipient' value='dance@luckyline.se'>\n");
        htmlBuff.append("</table><input type=hidden name='recipient' value='" + grundConfig.getAnmalanMail()+ "'>\n");
        htmlBuff.append("<input type=hidden name='subject' value='Kursanmälan auto'>\n");
        htmlBuff.append("<input type=hidden name='valdkurs'>\n");  
        htmlBuff.append("<p><input type='submit' value='Skicka' name='Knapp' onClick='return validate();'></p>\n");
        htmlBuff.append("</div></form></body></htlm>\n");	
        writeToDisk(htmlBuff, paths.getProgrampath() + "/anmalan.htm");
    	return paths.getProgramurl() + "/anmalan.htm";
 	 }
	
	 private void writeToDisk(StringBuffer buff, String fileName)
	 {
		 FileOutputStream out; 
		 PrintStream p;
	     try
	     {
	    	 out = new FileOutputStream(fileName);
	         p = new PrintStream( out );
	         p.println (buff.toString());
	         p.close();
	     }
	     catch (Exception e)
	     {
	    	 System.err.println ("Error writing to file");
	     }    	
	 }
}
