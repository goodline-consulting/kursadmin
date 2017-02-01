package kursadmin.service.htmlgen;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import kursadmin.domain.Instruktor;
import kursadmin.domain.KursAll;
import kursadmin.domain.KursProgram;
import kursadmin.domain.PathConfig;
import kursadmin.domain.ProgramPunkt;
import kursadmin.service.GenProgInterface;

public class GenProgImp implements GenProgInterface {

	@Override
	public String GenProgWeb(KursAll kursAll, List<KursProgram> progLista, Map<Integer, ProgramPunkt> punktMap, Instruktor instr, PathConfig paths) 
	{
		String[] months = {"Jan","Feb","Mars","April","Maj","Juni","Juli","Aug","Sept","Okt","Nov","Dec"};
    	int i = 0;
    	int j = 0;    	
    	StringBuffer htmlBuff = new StringBuffer();
    	htmlBuff.append("<html>\n\t<head>" + "<script type='text/javascript' src='basic.js'></script>\n" + 
    			        "<link rel=\"stylesheet\" href=\"../luckyline.css\" type=\"text/css\"/>\n" +
    			        "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>\n" + 
            			"<title>Kursprogram för " + kursAll.getBeteckning() + "</title>\n\t</head>\n\t<body>");
    	htmlBuff.append("<h2>" + kursAll.getKursnamn() + " " + kursAll.getVeckodag() + "ar " + kursAll.getStarttid() + "</h2>\n");
    	htmlBuff.append("<table class=\"proginfo\">");
    	htmlBuff.append("<tr><td valign=\"top\"><b>Instruktör:</b></td><td valign=\"top\">" + 
    			         kursAll.getInstruktorNamn() + 
    			         "</td><td rowspan=\"5\"><img width=\"" +
    			         instr.getBildx() + "\" height=\"" +
    			         instr.getBildy() +  "\" src=\"/bilder/" +
    			         instr.getBild() + "\"/></td></tr>\n");
    	htmlBuff.append("<tr><td valign=\"top\"><b>Nivå:</b></td><td valign=\"top\">" + kursAll.getKursNiva() + "</td></tr>\n");
        htmlBuff.append("<tr><td valign=\"top\"><b>Plats:</b></td><td valign=\"top\">" + kursAll.getLokalNamn() + "</td></tr>\n");
        htmlBuff.append("<tr><td valign=\"top\"><b>Kursstart:</b></td><td valign=\"top\">" +	kursAll.getStartdatum() +
        		        " klockan " + kursAll.getStarttid() +
        		        "</td></tr></table><br>\n");	
       
        GregorianCalendar greg = new GregorianCalendar();
        
        
        htmlBuff.append("<h3><font color=\"#006633\">Preliminärt kursprogram </font></h3>\n");
        htmlBuff.append("<table border='1' cellspacing='2' cellpadding='4'>\n");
        htmlBuff.append("<tr>\n");
        htmlBuff.append("<th>Datum</th>\n");
        htmlBuff.append("<th align=\"left\" colspan==\"2\">Dans</th>\n");
        htmlBuff.append("</tr>\n");
        i = 0;
        for (Date dat : kursAll.getKurstillf())
        {     
        	i++;
        	j = 0;
        	greg.setTime(dat);
        	// extra snurra för att se hur många programpunkter det är på dagen ifråga
        	for (KursProgram kp: progLista)
        	{
        		if (kp.getSeq() == i)
        		{       
        			j++;
        		}
        	}
        	
        	htmlBuff.append("<tr><td rowspan='" + j + "' valign=\"top\">&nbsp;");
        	j = 0;
        	int dag = greg.get(GregorianCalendar.DAY_OF_MONTH);
        	htmlBuff.append((dag < 10 ? "0" + dag : dag) + " " + 
        			        months[greg.get(GregorianCalendar.MONTH)]);
        	htmlBuff.append("&nbsp;</td>\n");
        	for (KursProgram kp: progLista)
        	{
        		if (kp.getSeq() == i)
        		{   
        			if (j > 1)
        				htmlBuff.append("<tr>\n");
        			htmlBuff.append("<td>\n");
        			ProgramPunkt pp = punktMap.get(kp.getPpid());
        			htmlBuff.append(fixUrl(pp.getHeader(), paths.getProgrampunkter(), pp.getUrl()));
        			htmlBuff.append("</td><td>\n");
        			if (pp.getUrl2().length() > 0)
        				htmlBuff.append(fixUrl2(pp.getUrl2()));
        			else
        				htmlBuff.append("&nbsp;");
        			htmlBuff.append("</td></tr>\n");
        			j++;
        		}
        	}
        	if (j == 0)
        	   	htmlBuff.append("&nbsp;</td></tr>\n");
        }
        htmlBuff.append("</table></body></html>\n");	
        /* används för närvarande inte. Snygg teknink för att bädda in video på sidan
        htmlBuff.append("<div id='video' style='position:absolute; left:433px; top:914px; width:182px; height:48px; z-index:99; display:none; layer-background-color: #FFFFFF; border: 1px none #000000;'>\n");
        htmlBuff.append("<div class='videowindow' id='videowindow'>\n"); 
        htmlBuff.append("	<table width='300' border='1' cellspacing='0' cellpadding='0'>\n");
        htmlBuff.append("	  	<tr><td width='300'><div align='right'><a href='javascript:closevideo();'>Stäng</a>&nbsp;</div></td></tr>\n");
        htmlBuff.append("	  	<tr><td> <div id='videocontent'> Movie Here</div></td></tr>\n");
        htmlBuff.append("	</table></div></div>\n");
        */
        writeToDisk(htmlBuff, paths.getProgrampath() + "/" + kursAll.getBeteckning() + ".htm");
    	return paths.getProgramurl() + "/" + kursAll.getBeteckning() + ".htm";	
    }
	private String fixUrl(String head, String path, String url)
    {
    	StringBuffer sb = new StringBuffer();
    	String urlPath = null;
    	int idx;
    	if ((idx = head.indexOf('|')) != -1)
    	{
    		if (idx > 0)
    			sb.append(head.substring(0, idx));
    		if (url.startsWith("http://"))
    			urlPath = url;
    		else
    			urlPath =  path + "/" + url; 
    		sb.append("<a href='" + urlPath + "'>");
    		sb.append(head.substring(idx + 1, head.indexOf('|', idx + 1)));
    		sb.append("<a/>");
    		sb.append(head.substring(head.indexOf('|', idx + 1) + 1));
    		return sb.toString();
    	}
    	else 
    		return head;
    }
	private String fixUrl2(String url)
    {
    	StringBuffer sb = new StringBuffer();    	
    	//sb.append("&nbsp;&nbsp;");
    	if (url.startsWith("http://"))
    		sb.append("<a href='javascript:openWindow2(\"" + url + "\", 650, 520)'>");
    	else
    		// sb.append("<a href='javascript:openvideo(\"" + url + "\");'>"); inbäddat på sidan i videodiv ovan
    		sb.append("<a href='javascript:openWindow2(\"http://www.youtube.com/watch_popup?v=" + url + "&vq=medium\", 430, 340)'>");
		sb.append("<img src='../images/filmcamera.jpg' width='59' height='33' border='0'/>");
		sb.append("<a/>");    		
		return sb.toString();    	
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
