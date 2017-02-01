package kursadmin.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


import kursadmin.domain.Elev;
import kursadmin.domain.Instruktor;
import kursadmin.domain.KursAll;
import kursadmin.domain.KursProgram;
import kursadmin.domain.PathConfig;
import kursadmin.domain.ProgramPunkt;
import kursadmin.repository.ProgramDao;
import kursadmin.service.KursManagerInterface;
import kursadmin.service.KursProgramManagerInterface;


public class ProgramController implements Controller 
{

    protected final Log logger = LogFactory.getLog(getClass());
    private KursManagerInterface kursManager;
    private KursProgramManagerInterface  programDao;
    private int kid = 0;
    private KursAll kursAll = null;
    private ArrayList<KursProgram> progList = null;
    private ArrayList<ProgramPunkt> punktList = null;
    private ArrayList<Date> kursTillf = null;
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {   
    	Map<String, Object> modelMap = new HashMap<String, Object>();   
    	if (request.getMethod() == "POST")
    	{	    		
    		if (progList.size() > 0)
    			programDao.setKursProgram(progList);
    		return new ModelAndView(new RedirectView("kurser.htm"));
    	}
    	if (request.getParameter("publicera") != null)
    	{    		    		
    		return new ModelAndView(new RedirectView(programDao.genKursProgram(kursAll, progList)));    		
    	}    	
    	if (request.getParameter("kopiera") != null)
    	{
    		
    		if (request.getParameter("kopiera").equals("yes"))
    		{
    			
    			// Plocka fram en lista på kandidater att kopiera till
        		modelMap.put("kurser", kursManager.getKopieKandidater(kursAll.getTid(), kursAll.getNiva()));
        		modelMap.put("skippa", kursAll.getKid());
    			return new ModelAndView("WEB-INF/jsp/kopieraprogram.jsp", "modelMap", modelMap);
    		}
    		else // Vi är tillbaka från frågan om till vilka kurser som programmet skall kopieras
    		{
    			for (Enumeration<String> pars = request.getParameterNames(); pars.hasMoreElements();)
    			{	    		
    				String par = pars.nextElement();    				
    				if (par.startsWith("copy"))
    				{
    					//logger.info("Par: " + par + " Värde: " + request.getParameter(par));
    					ArrayList<KursProgram> listKopia = new ArrayList<KursProgram>();
    					KursAll k = kursManager.getKursAll(Integer.parseInt(request.getParameter(par)));
    					int size = k.getAntKursTillf();
    					for (KursProgram kp: progList)
    					{
    						if (kp.getSeq() <= size)
    						{
    							KursProgram kpn = new KursProgram();
    							kpn.setKid(k.getKid());
    							kpn.setSeq(kp.getSeq());
    							kpn.setPpid(kp.getPpid());
    							listKopia.add(kpn);
    						}
    						// Kolla om vi skall publicera också.
    						if ((request.getParameter("kopiera").equals("publicera")))
    							programDao.genKursProgram(k, listKopia);
    		    			
    					}
    					programDao.setKursProgram(listKopia);
    					//logger.info("Par: " + par + " Värde: " + request.getParameter(par));
    				}	
    			}
    			setModelMap(modelMap);
    			modelMap.put("msg", "Kursprogrammet har kopierats till valda kurser");
    			return new ModelAndView("WEB-INF/jsp/kopieraprogram.jsp", "modelMap", modelMap);
    		}
    		
    	}
    	if (request.getParameter("recid") != null)    		
    	{
    		kid = Integer.parseInt(request.getParameter("recid"));
    		kursAll = kursManager.getKursAll(kid);
    		progList = (ArrayList<KursProgram>) programDao.getKursProgram(kid);
    		punktList = (ArrayList<ProgramPunkt>)programDao.getProgramPunktList(kursAll.getTid(), "", true);
    		kursTillf = (ArrayList<Date>) kursAll.getKurstillf();
    		setModelMap(modelMap);
    		modelMap.put("senastvald", 0);
    		return new ModelAndView("WEB-INF/jsp/kursprogram.jsp", "modelMap", modelMap);
    	}	
    	if (request.getParameter("add") != null)
    	{
    		
    		int seq = Integer.parseInt(request.getParameter("add"));    		
    		int ppid = Integer.parseInt(request.getParameter("ppid"));
    		boolean dublett = false;
    		KursProgram kp = new KursProgram();
    		kp.setKid(kid);
    		kp.setPpid(ppid);
    		kp.setSeq(seq);
    		for (ProgramPunkt pp: punktList)
    			if (pp.getPpid() == ppid)
    			{
    				kp.setNamn(pp.getNamn());
    				break;
    			}
    		for (KursProgram kpTest: progList)
    			if (kpTest.getPpid() == kp.getPpid() &&
    			    kpTest.getSeq()  == kp.getSeq())
    			{
    				dublett = true;
    				break;
    			}
    		if (dublett)
    			modelMap.put("msg", "Samma programpunkt får ej förekomma flera ggr vid samma kurstillfälle!");
    		else
    			progList.add(kp);
    		setModelMap(modelMap);
    		modelMap.put("senastvald", ppid);
    		return new ModelAndView("WEB-INF/jsp/kursprogram.jsp", "modelMap", modelMap);
    		
    	}	
    	if (request.getParameter("upp") != null)
    	{
    		int seq = Integer.parseInt(request.getParameter("upp"));    		   		
    		int idx  = 0;
    		for (KursProgram kpTest: progList)
    		{	
    			if (kpTest.getSeq()  == seq)
    				kpTest.setSeq(seq - 1);
    			else if (kpTest.getSeq()  == seq - 1)
    				kpTest.setSeq(seq);
    		} 
    		sortera();
    		setModelMap(modelMap);
    	    return new ModelAndView("WEB-INF/jsp/kursprogram.jsp", "modelMap", modelMap);
    	}
    	if (request.getParameter("ned") != null)
    	{
    		int seq = Integer.parseInt(request.getParameter("ned"));    		   		
    		int idx  = 0;
    		for (KursProgram kpTest: progList)
    		{	
    			if (kpTest.getSeq()  == seq)
    				kpTest.setSeq(seq + 1);
    			else if (kpTest.getSeq()  == seq + 1)
    				kpTest.setSeq(seq);
    		} 
    		sortera();
    		setModelMap(modelMap);
    	    return new ModelAndView("WEB-INF/jsp/kursprogram.jsp", "modelMap", modelMap);
    	}
    	if (request.getParameter("radera") != null)
    	{
    		int seq = Integer.parseInt(request.getParameter("radera"));    		
    		int ppid = Integer.parseInt(request.getParameter("ppid"));
    		int idx  = 0;
    		for (KursProgram kpTest: progList)
    			if (kpTest.getPpid() == ppid &&
    			    kpTest.getSeq()  == seq)
    			{
    				idx = progList.indexOf(kpTest);
    				break;
    			} 
    		progList.remove(idx);
    	    setModelMap(modelMap);
    	    return new ModelAndView("WEB-INF/jsp/kursprogram.jsp", "modelMap", modelMap);
    	}
    	return new ModelAndView("WEB-INF/jsp/kursprogram.jsp", "modelMap", modelMap);
    }
    
    public void setKursManager(KursManagerInterface kursManager) 
    {
        this.kursManager = kursManager;
    }
    public void setKursProgramManager(KursProgramManagerInterface KursProgramManager) 
    {
        this.programDao = KursProgramManager;        
    }
    private void sortera()
    {
    	// Sortera listan  så att vi får de dyraste kurserna först
    	Collections.sort(progList, new Comparator<KursProgram>()
    	{    		
            public int compare(KursProgram k1, KursProgram k2) 
            {
               
            	if (k1.getSeq() > k2.getSeq())
                	return -1;
                if (k1.getSeq() == k2.getSeq())
                	return 0;
                return 1;               
            }
        });
    }
    
    private void setModelMap(Map<String, Object> modelMap)
    {
    	modelMap.put("kurs", kursAll);
		modelMap.put("program", progList);
		modelMap.put("punkter", punktList);
		modelMap.put("kurstillf", kursTillf);	
		modelMap.put("antal", kursTillf.size()); 
		modelMap.put("antalpp", progList.size()); 
    }
}

