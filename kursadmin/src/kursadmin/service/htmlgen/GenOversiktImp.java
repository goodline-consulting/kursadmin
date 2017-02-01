package kursadmin.service.htmlgen;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import kursadmin.domain.KursAll;
import kursadmin.domain.Lokal;
import kursadmin.domain.PathConfig;
import kursadmin.service.GenAnmInterface;
import kursadmin.service.GenOversiktInterface;

public class GenOversiktImp implements GenOversiktInterface 
{

	@Override
	public String GenOversiktWeb(List<KursAll> kursLista, Map<Integer, Lokal> lokalMap, PathConfig paths) 
	{
		boolean jamn = true;
		StringBuffer htmlBuff = new StringBuffer();
	
		htmlBuff.append("<html>\n\t<head>\n"); 
		htmlBuff.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>\n"); 
		htmlBuff.append("<link rel='stylesheet' href='luckyline.css' type='text/css'/>\n");
     	htmlBuff.append("<script language='javascript' src='basic.js'></script>\n");
     	htmlBuff.append("<title>Kursöversikt</title>\n\t</head>\n\t<body>");
     	htmlBuff.append("<table border='1' cellpadding='10' cellspacing='10'><tr>\n");
		htmlBuff.append("<th class='yellow'>Beskrivning</th>");
		htmlBuff.append("<th class='yellow'>Dag/Tid/Plats</th>");
		htmlBuff.append("<th class='yellow'>Startdatum</th>");
		htmlBuff.append("<th class='yellow'>Kurstillfällen</th>");
		htmlBuff.append("<th class='yellow'>Pris&nbsp;</th></tr>");
	  

		for (KursAll ka: kursLista)
        {	
			Lokal lokal = lokalMap.get(ka.getLokal());
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(ka.getStartdatum());
			gc.add(Calendar.HOUR_OF_DAY, Integer.parseInt(ka.getStarttid().substring(0, 2)));
			gc.add(Calendar.MINUTE, Integer.parseInt(ka.getStarttid().substring(3)));
			gc.add(Calendar.MINUTE, ka.getLengd());
			String tidStr = ka.getStarttid() + " - " + gc.get(Calendar.HOUR_OF_DAY) + ":" + (gc.get(Calendar.MINUTE) == 0 ? "00" : gc.get(Calendar.MINUTE));
        	htmlBuff.append("<tr valign='top' class='" + (jamn ? "jamnrad" : "uddarad") + "'>" +
        					"<td valign='top'><h4>" + ka.getKursnamn() + "</h4>" +        					
        					"Kursbeteckning:<i>" + ka.getBeteckning() + "</i>" + 
        			        "<br>Kursledare är <b>"+  ka.getInstruktorNamn() + "</b><br>" + 
        			        "Se <a href='kurs/" + ka.getBeteckning() + ".htm' target='_blank'>kursprogram</a></td>" + 
        			        "<td valign='top' class='storre'>" + ka.getVeckodag() + "ar "  + tidStr + "<br>" +
        			        "<b>" + ka.getLokalNamn() + 
        			        "</b>" + (lokal.getVagbesk() == null || lokal.getVagbesk().equals("") ? "" : "<br><a href='" + lokal.getVagbesk() + "' target='_blank'>Se vägbeskrivning</a>") + "</td>" +		
        			        "<td valign='top' class='storre'>" +  new SimpleDateFormat("yyyy-MM-dd").format(ka.getStartdatum()) + " kl " + ka.getStarttid() + 
        			        (ka.getStatus() > 0 ? "<font color='darkred' size='4'><br><b>" + ka.getStatusText() + "</b></font><br>": "")  + "</td>" +
        			        "<td valign='top' class='storre'>" + ka.getAntKursTillf()  + "</td>" + 
        			        "<td valign='top' class='storre'>" + ka.getPris() + ":-</td></tr>\n");
        	jamn = !jamn;	
        }
        
        
        htmlBuff.append("</table></body></htlm>\n"); 
      
        writeToDisk(htmlBuff, paths.getProgrampath() + "/oversikt.htm");
    	return paths.getProgramurl() + "/oversikt.htm";
 	 }
	
	
	 private void writeToDisk(StringBuffer buff, String fileName)
	 {
		 FileOutputStream out; 
		 PrintStream p;
	     try
	     {
	    	 out = new FileOutputStream(fileName);
	         p = new PrintStream( out );
	         p.println (HTMLEncode(buff.toString()));
	         p.close();
	     }
	     catch (Exception e)
	     {
	    	 System.err.println ("Error writing to file");
	     }    	
	 }
     
     
     public static String HTMLEncode(String s) 
     {
    	 final char c[] = { 'å', 'ä', 'ö', 'Å', 'Ä', 'Ö'};       
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

}
