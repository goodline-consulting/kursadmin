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

public class GenAnmSLImp implements GenAnmInterface 
{

	@Override
	public String GenAnmWeb(List<KursAll> kursLista, List<Lokal> lokalLista, PathConfig paths, GrundConfig grundConfig) 
	{
		StringBuffer htmlBuff = new StringBuffer();
		htmlBuff.append("<html>\n\t<head>\n");
		//htmlBuff.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>\n"); 
		if (grundConfig.getStyle() != null && !grundConfig.getStyle().equals(""))
			htmlBuff.append("<link rel='stylesheet' href='" + grundConfig.getStyle() + "' type='text/css'/>\n");
     	htmlBuff.append("<script language='javascript' src='basic.js'></script>\n");
     	htmlBuff.append("<script language='javascript'>\n");
     	htmlBuff.append("function validate()\n");
     	htmlBuff.append("{\n");
     	htmlBuff.append("	if (document.anmalan.fnamn.value == '')\n");
     	htmlBuff.append("	{\n");
     	htmlBuff.append("		alert('Du m�ste fylla i f�rnamn');\n");
     	htmlBuff.append("		document.anmalan.fnamn.focus();\n");
     	htmlBuff.append("		return false;\n");
     	htmlBuff.append("	}\n");
     	htmlBuff.append("   if (document.anmalan.enamn.value == '')\n");
     	htmlBuff.append("	{\n");
     	htmlBuff.append("		alert('Du m�ste fylla i efternamn');\n");
     	htmlBuff.append("		document.anmalan.enamn.focus();\n");
     	htmlBuff.append("		return false;\n");
     	htmlBuff.append("	}\n");
     	htmlBuff.append("	if (document.anmalan.email.value == '')\n");
     	htmlBuff.append("	{\n");
     	htmlBuff.append("		alert('Du m�ste ange en mailadress');\n");
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
     	htmlBuff.append("		alert('Du m�ste v�lja en kurs');\n");
     	htmlBuff.append("		return false;\n");
     	htmlBuff.append("	}\n");
     	htmlBuff.append("   if (i > 1)	\n");
     	htmlBuff.append("	{\n");
     	htmlBuff.append("		if (confirm('Skall du verkligen anm�la dig till ' + i + ' kurser?') == 0)\n");		
     	htmlBuff.append("			return false;");
     	htmlBuff.append("	}\n"); 
     	htmlBuff.append("	anmalan.valdkurs.value = anmalan.valdkurs.value.substring(0, anmalan.valdkurs.value.length - 1);\n");
     	htmlBuff.append("	hidePageElement('rubbet')\n");
     	htmlBuff.append("	showPageElement('div1')\n");	
     	htmlBuff.append("	return true;\n");	
     	htmlBuff.append("}\n");
        htmlBuff.append("</script>\n");
     	htmlBuff.append("<title>Kursanm�lan</title>\n\t</head>\n\t<body>");
     	htmlBuff.append("<div id='div1' style='background: none repeat scroll 0% 0% white; padding: 8px; position: fixed; left: 25%; top:40%; margin-left:10%; border: 2px solid rgb(34, 102, 170); display: none;'>");
     	htmlBuff.append("<font color='grey' size='4'>V�nta medan din anm�lan behandlas... </font><img src='/images/wait30trans.gif'></div><div id='rubbet'>\n");
     	htmlBuff.append("<form method='POST' name='anmalan' action='" + grundConfig.getAnmActionUrl() +"'>\n");
		//htmlBuff.append("<form method='POST' name='anmalan' action='/sl/anmalan.htm'>\n");
     	//htmlBuff.append("<form method='POST' name='anmalan' action='http://localhost:8080//kursadmin/anmalan.htm'>\n");
		htmlBuff.append("<H1>Stockholm LineDancers</H1>\n");
		htmlBuff.append("<table><tr><td colspan='2'>KURSANM�LAN - BOKNING<br><br></td></tr>\n");
		htmlBuff.append("<tr><td colspan='2'>Fyll i dina uppgifter och tryck sedan p� skicka.</td></tr>\n");
		htmlBuff.append("<tr><td colspan='2'>Vi tar emot bokningar s� l�ngt platserna r�cker!</td></tr>\n");
		htmlBuff.append("<tr><td colspan='2'>Du kommer att f� ett bekr�ftelsemail med betalningsanvisningar till den mailadress som du angivit.</td></tr>\n");
		htmlBuff.append("<tr><td colspan='2'>Anm�lan �r endast prelimin�r tills kursavgiften betalats till v�rt <strong>Bankgiro 5245-4378 </strong><br></td></tr>\n");
		htmlBuff.append("<tr><td colspan='2'><br>G�r du flera kurser hos oss f�r du 400:- rabatt p� till�ggskurserna<br><br></td></tr>\n");
		htmlBuff.append("<tr><td>Plats:</td><td>Stockholm LineDancers dansstudio, Luntmakargatan 94-96 (ing�ng direkt fr�n gatan)</td></tr>\n");
		htmlBuff.append("<tr><td>�vrigt:</td><td>Inga ute-skor i danssalen (tag med ombyte)</td></tr>\n");
		htmlBuff.append("<tr><td colspan='2'><br>V�LKOMMEN<br><br></td></tr>\n");		
		htmlBuff.append("<tr><td colspan='2'><font color='red'><br>*Obligatorisk<br></font></td></tr>\n");
		htmlBuff.append("</table>\n");
		htmlBuff.append("<table border='0'><tr>\n");
		htmlBuff.append("		<td>&nbsp;</td>\n");
		htmlBuff.append("		<td>&nbsp;</td>\n");
		htmlBuff.append("	</tr>\n");
		htmlBuff.append("	<tr>\n");
	    htmlBuff.append("		<td>*F�rnamn:</td>\n");
   		htmlBuff.append("		<td><input type='text' name='fnamn' size='37'></td>\n");
		htmlBuff.append("</tr>\n");
		htmlBuff.append("	<tr>\n");
		htmlBuff.append("		<td>*Efternamn:</td>\n");
		htmlBuff.append("		<td><input type='text' name='enamn' size='37'></td>\n");
	    htmlBuff.append("	</tr>\n");
	    htmlBuff.append("		<td>Telefon</td>\n");
	    htmlBuff.append("		<td><input type='text' name='telefon' size='20'></td>\n");
        htmlBuff.append("	</tr>\n");
	    htmlBuff.append("	<tr>\n");
	    htmlBuff.append("		<td>*E-mail</td>\n");
	    htmlBuff.append("		<td><input type='text' name='email' size='37'></td>\n");
	    htmlBuff.append("	</tr>\n");
	    htmlBuff.append("	<tr>\n");
	    htmlBuff.append("		<td>&nbsp;</td>\n");
	    htmlBuff.append("		<td><br>\n");
        htmlBuff.append("		Fr�gor eller information:<br>\n");
	    htmlBuff.append("		<textarea rows='5' name='info' cols='72'></textarea></td>\n");
	    htmlBuff.append("	</tr><tr><td colspan='2'>&nbsp;</td></tr>\n");
	    htmlBuff.append("	<tr>\n");
	    htmlBuff.append("		<td valign='top'>Kurs:</td>\n");
	    htmlBuff.append("		<td valign='top'>H�r v�ljer du vilken/vilka kurser du �nskar g� p�. Aktuell kursavgift ser du p� hemsidan.</td></tr>");
	    htmlBuff.append("	<tr><td>&nbsp;</td><td>&nbsp;</td></tr>\n");
        for (KursAll ka: kursLista)
        {
        		if (ka.getStatus() == 0)
        		{	
        			htmlBuff.append("<tr><td>&nbsp;</td><td><input type='checkbox' name='kursval' value='" + ka.getKid() + "'>" + ka.getKursnamn() + "," + ka.getLokalNamn() + " (Start " + new SimpleDateFormat("d/M").format(ka.getStartdatum()) + "), " + ka.getVeckodag() + "ar " + ka.getStarttid() + "</td></tr>\n");
        			
        		}	
        }
          
        //htmlBuff.append("</table><input type=hidden name='recipient' value='stockholm@linedancers.com'>\n");
        htmlBuff.append("</table><input type=hidden name='recipient' value='" + grundConfig.getAnmalanMail()+ "'>\n");
        htmlBuff.append("<input type=hidden name='subject' value='Kursanm�lan auto'>\n");
        htmlBuff.append("<input type=hidden name='valdkurs'>\n");  
        htmlBuff.append("<input type='submit' value='Skicka' name='Knapp' onClick='return validate();'>\n");
        htmlBuff.append("<strong><br><br>OBS! Det kan dr�ja en liten stund n�r du tryckt p� &quot;Skicka&quot; knappen.<br> Ha t�lamod och v�nta tills en skylt med &quot;Tack f�r din anm�lan&quot; dyker upp.</strong>\n");
        htmlBuff.append("</div></form></body></htlm>\n");	
        writeToDisk(htmlBuff, paths.getProgrampath() + "/anmalan.htm");
    	return paths.getProgramurl() + "/anmalan.htm";
 	 }
	
	
	public static String HTMLEncode(String s) 
    {
   	 final char c[] = { '�', '�', '�', '�', '�', '�'};       
        final String expansion[] = {"&aring;", "&auml;", "&ouml;","&Aring;", "&Auml;","&Ouml;"};

        StringBuffer st = new StringBuffer();
        for (int i = 0; i < s.length(); i++) 
        {
       	 boolean copy = true;
            char ch = s.charAt(i);
            for (int j = 0; j < c.length ; j++) 
            {
           	 if (c[j]==ch) 
           	 {
           		 st.append(expansion[j]);
                    copy = false;
                    break;
                }
             }
                    if (copy) st.append(ch);
        }
        return st.toString();
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
