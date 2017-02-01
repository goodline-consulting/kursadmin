package kursadmin.service;

import kursadmin.domain.Instruktor;
import kursadmin.domain.KursAll;
import kursadmin.domain.KursProgram;
import kursadmin.domain.PathConfig;
import kursadmin.domain.ProgramPunkt;
import kursadmin.repository.InstruktorDao;
import kursadmin.repository.KursDao;
import kursadmin.repository.ProgramDao;


import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KursProgramManager implements KursProgramManagerInterface
{
	private ProgramDao programDao;
	private KursDao kursDao;
	private InstruktorDao instruktorDao;
	private ConfigManagerInterface configManager;
	private GenProgInterface progGen;
	
	public List<ProgramPunkt> getProgramPunktList()
	{
		return programDao.getProgramPunktList();
	}
    public List<ProgramPunkt> getProgramPunktList(int tid)
    {
    	return programDao.getProgramPunktList(tid);
    }
    public List<ProgramPunkt> getProgramPunktList(int tid, String filter, boolean aktuella)
    {
    	return programDao.getProgramPunktList(tid, filter, aktuella);
    }
    public ProgramPunkt getProgramPunkt(int ppid)
    {
    	return programDao.getProgramPunkt(ppid);
    }
    public void insertProgramPunkt(ProgramPunkt programPunkt)
    {
    	programDao.insertProgramPunkt(programPunkt);
    }    
    public void loadProgramPunkter(List<ProgramPunkt> punktList)
    {
    	programDao.loadProgramPunkter(punktList);
    }
    public boolean deleteProgramPunkt(int ppid)
    {
    	return programDao.deleteProgramPunkt(ppid);
    }
    public void updateProgramPunkt(ProgramPunkt programPunkt)
    {
    	programDao.updateProgramPunkt(programPunkt);
    }
    public List<KursProgram> getKursProgram(int kid)
    {
    	return programDao.getKursProgram(kid);
    }
    public void setKursProgram(List<KursProgram> progList)
    {
    	programDao.setKursProgram(progList);
    }
    
    public String genKursProgram(KursAll kursAll, List<KursProgram> progLista)
    {    	
    	
    	Instruktor instr = instruktorDao.getInstruktor(kursAll.getInstruktor());
    	PathConfig pc = configManager.getPathConfig();
    	Map<Integer, ProgramPunkt> punktMap = new HashMap<Integer, ProgramPunkt>();
    	for (KursProgram kp: progLista)
    	{
    		ProgramPunkt pp = getProgramPunkt(kp.getPpid());
    		punktMap.put(kp.getPpid(), pp);
    	} 
    	return progGen.GenProgWeb(kursAll, progLista,  punktMap, instr, pc);
    	/*
    	// ArrayList<KursProgram> progLista = (ArrayList<KursProgram>)getKursProgram(kursAll.getKid());
    	int i = 0;
    	int j = 0;
    	
    	StringBuffer htmlBuff = new StringBuffer();
    	htmlBuff.append("<html>\n\t<head>" + 
    			       // "<link rel=\"stylesheet\" href=\"kursadmin_style.css\" type=\"text/css\"/>\n" +
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
        			ProgramPunkt pp = getProgramPunkt(kp.getPpid());
        			htmlBuff.append(fixUrl(pp.getHeader(), pc.getProgrampunkter(), pp.getUrl()));
        			j++;
        		}
        	}
        	if (j == 0)
        		htmlBuff.append("&nbsp;");
        	htmlBuff.append("</td></tr>\n");
        }
        htmlBuff.append("</table>\n");	
        writeToDisk(htmlBuff, pc.getProgrampath() + "/" + kursAll.getBeteckning() + ".htm");
    	return pc.getProgramurl() + "/" + kursAll.getBeteckning() + ".htm";
    	*/
    }
    
    public String genKursUtbud(int[] kids)
    {
    	return null;
    }
    
    
    public void setProgramDao(ProgramDao programDao)
    {
    	this.programDao = programDao;
    }
    public void setKursDao(KursDao kursDao)
    {
    	this.kursDao = kursDao;
    }
    public void setInstruktorDao(InstruktorDao instruktorDao)
    {
    	this.instruktorDao = instruktorDao;
    }
    public void setConfigManager(ConfigManagerInterface configManager)
    {
    	this.configManager = configManager; 
    }
    public void setProgGen(GenProgInterface progGen)
    {
    	this.progGen = progGen;
    }
    
}
