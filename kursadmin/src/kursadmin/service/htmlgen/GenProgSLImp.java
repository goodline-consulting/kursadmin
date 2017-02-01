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

public class GenProgSLImp implements GenProgInterface {

	@Override
	public String GenProgWeb(KursAll kursAll, List<KursProgram> progLista, Map<Integer, ProgramPunkt> punktMap, Instruktor instr, PathConfig paths) 
	{
		String[] months = {"Jan","Feb","Mars","April","Maj","Juni","Juli","Aug","Sept","Okt","Nov","Dec"};
    	int i = 0;
    	int j = 0;    	
    	StringBuffer htmlBuff = new StringBuffer();
    	htmlBuff.append("<html>\n\t<head>" + 
    			       // "<link rel=\"stylesheet\" href=\"kursadmin_style.css\" type=\"text/css\"/>\n" +
            			"<title>Kursprogram för " + kursAll.getBeteckning() + "</title>\n\t</head>\n\t<body bgcolor='Silver'>");
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
        		        "</td></tr>\n");	
        htmlBuff.append("<tr><td valign=\"top\"><b>Kurstillfällen:&nbsp;</b></td><td valign=\"top\">");
        GregorianCalendar greg = new GregorianCalendar();
        
        for (Date dat : kursAll.getKurstillf())
        {
        	if (i++ > 0)
        		htmlBuff.append(", ");
        	if (i % 9 == 0)
        		htmlBuff.append("<br>&nbsp;");
        	greg.setTime(dat);
        	htmlBuff.append(greg.get(GregorianCalendar.DAY_OF_MONTH) + "/" + 
        			        (greg.get(GregorianCalendar.MONTH) + 1));
        }
        htmlBuff.append("</td></tr></table><br>");	
        htmlBuff.append("<h3><font color=\"#006633\">Preliminärt kursprogram (Klicka på dansnamnet för dansbeskrivning)</font></p></h3>\n");
        htmlBuff.append("<table border='1' cellspacing='2' cellpadding='4'>\n");
        htmlBuff.append("<tr>\n");
        htmlBuff.append("<th>Datum</th>\n");
        htmlBuff.append("<th align=\"left\">Dans</th>\n");
        htmlBuff.append("</tr>\n");
        i = 0;
        for (Date dat : kursAll.getKurstillf())
        {     
        	i++;
        	j = 0;
        	greg.setTime(dat);
        	htmlBuff.append("<tr><td valign=\"top\">&nbsp;");
        	int dag = greg.get(GregorianCalendar.DAY_OF_MONTH);
        	htmlBuff.append((dag < 10 ? "0" + dag : dag) + " " + 
        			        months[greg.get(GregorianCalendar.MONTH)]);
        	htmlBuff.append("&nbsp;</td><td>\n");
        	for (KursProgram kp: progLista)
        	{
        		if (kp.getSeq() == i)
        		{       
        			if (j > 0)
        				htmlBuff.append("<br>\n");
        			ProgramPunkt pp = punktMap.get(kp.getPpid());
        			htmlBuff.append(fixUrl(pp.getHeader(), paths.getProgrampunkter(), pp.getUrl()));
        			j++;
        		}
        	}
        	if (j == 0)
        		htmlBuff.append("&nbsp;");
        	htmlBuff.append("</td></tr>\n");
        }
        htmlBuff.append("</table>\n");	
        writeToDisk(htmlBuff, paths.getProgrampath() + "/" + kursAll.getBeteckning() + ".htm");
    	return paths.getProgramurl() + "/" + kursAll.getBeteckning() + ".htm";	
    }
	private String fixUrl(String head, String path, String url)
    {
    	StringBuffer sb = new StringBuffer();
    	int idx;
    	if ((idx = head.indexOf('|')) != -1)
    	{
    		if (idx > 0)
    			sb.append(head.substring(0, idx));
    		sb.append("<a href='" + path + "/" + url + "'>");
    		sb.append(head.substring(idx + 1, head.indexOf('|', idx + 1)));
    		sb.append("<a/>");
    		sb.append(head.substring(head.indexOf('|', idx + 1) + 1));
    		return sb.toString();
    	}
    	else 
    		return head;
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
