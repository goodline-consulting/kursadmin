package kursadmin.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Date;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Map;
import java.util.HashMap;

import kursadmin.domain.KursAll;
import kursadmin.domain.MailMottagare;
import kursadmin.repository.KursDao;
import kursadmin.service.KursManagerInterface;
import kursadmin.service.KursTypManagerInterface;
import kursadmin.service.LokalManagerInterface;
import kursadmin.service.InstruktorManagerInterface;
public class KursController implements Controller 
{

    protected final Log logger = LogFactory.getLog(getClass());
    private KursManagerInterface kursManager;
    private KursTypManagerInterface kursTypManager;
    private LokalManagerInterface lokalManager;
    private InstruktorManagerInterface instruktorManager;
    private ArrayList<Integer> kidLista = new ArrayList<Integer>();
    private String stBeteckning = "", stInstructor = "", stLokal = "", stType = "", stLevel = "";
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {   
    	
    	// logger.info("Method: " + request.getMethod() + " stBeteckning = " + stBeteckning + " stInstructor = " + stInstructor + " stLokal = " + stLokal + " stType = " + stType + " stLevel = " + stLevel);    	
    	Map<String, Object> modelMap = new HashMap<String, Object>();   
    	modelMap.put("lokaler", lokalManager.getLokaler());
    	modelMap.put("instruktorer", instruktorManager.getInstruktors());
    	modelMap.put("typer", kursTypManager.getKursTypList());
    	modelMap.put("nivaer", kursTypManager.getKursNivaList());
    	kidLista.clear();
    	if (request.getParameter("recid") != null)
    	{    		
    		int kid = Integer.parseInt(request.getParameter("recid"));
    		KursAll ka = this.kursManager.getKursAll(kid);        		
    		modelMap.put("kursAll", ka);
    		modelMap.put("ekonomi", kursManager.getEkonomi(kid));
    		return new ModelAndView("WEB-INF/jsp/kursinfo.jsp", "modelMap", modelMap);
    	}	
    	
    	for (Enumeration<String> pars = request.getParameterNames(); pars.hasMoreElements();)
		{	    		
			String par = pars.nextElement();
			// logger.info("Parameter: " + par + " Value: " + request.getParameter(par));
			if (par.startsWith("rapport"))		
				kidLista.add(Integer.parseInt(par.substring(7)));			
		}
    	if (request.getParameter("_action") != null && request.getParameter("_action").equals("rapporter"))
	    	if (kidLista.size() > 0)
	    	{
	    		StringBuffer sb = new StringBuffer();
	    		int i = 0;
	    		for (Integer kid: kidLista)
	    		{	
	    			if (i > 0)
	    				sb.append(",");
	    			sb.append(kid.intValue());
	    			i++;
	    		}    		
	    		return new ModelAndView(new RedirectView("rapporter.htm?kidlist=" + sb.toString()));
	    	}
	    	else
	    	{
	    		modelMap.put("error", "Minst en kurs mŒste vŠljas fšr rapport");
	    	}
	   	if (request.getParameter("radera") != null)
    	{
    		this.kursManager.deleteKurs(Integer.parseInt(request.getParameter("radera")));
    		// modelMap.put("kurser", this.kursManager.getKurser());
            // return new ModelAndView("WEB-INF/jsp/kurser.jsp", "modelMap", modelMap);            
    	}
    	/*
		 * filtertypes:
		 * 1 = Beteckning
		 * 2 = Kurstyp
		 * 3 = Kurstyp och nivå
		 * 4 = lokal
		 * 5 = instruktor
		 */
    	if ((request.getParameter("beteckning") != null && 
    		request.getParameter("beteckning").length() > 0) || 
    		(stBeteckning != "" && request.getMethod().equals("GET")))
    	{
    		if (request.getParameter("beteckning") != null)
    		{	
    			clearStatus();
    			stBeteckning = request.getParameter("beteckning");
    		}	
    		this.putStatus(modelMap);
    		modelMap.put("kurser", this.kursManager.getKurser(stBeteckning, "", 1));
            return new ModelAndView("WEB-INF/jsp/kurser.jsp", "modelMap", modelMap);            
    	}
    	if ((request.getParameter("niva") != null && request.getParameter("niva").length() > 0) ||
    			(stLevel != "" && request.getMethod().equals("GET")))
    	{	
    		if (request.getParameter("niva") != null)
    		{
    			clearStatus();
    			stType = request.getParameter("kurstyp");
    			stLevel = request.getParameter("niva");
    		}	
    		this.putStatus(modelMap);           
    		modelMap.put("kurser", this.kursManager.getKurser(stType, stLevel, 3));
    		return new ModelAndView("WEB-INF/jsp/kurser.jsp", "modelMap", modelMap);
    	}	  	
    	if ((request.getParameter("lokal") != null && request.getParameter("lokal").length() > 0) ||
    		(stLokal != "" && request.getMethod().equals("GET")))
    	{
    		if (request.getParameter("lokal") != null)
    		{
    			clearStatus();
    			stLokal = request.getParameter("lokal");
    		}	
    		putStatus(modelMap);    
    		modelMap.put("kurser", this.kursManager.getKurser(stLokal, "", 4));    	
            return new ModelAndView("WEB-INF/jsp/kurser.jsp", "modelMap", modelMap);            
    	}
      	if ((request.getParameter("instruktor") != null && request.getParameter("instruktor").length() > 0) ||
      		(stInstructor != "" && request.getMethod().equals("GET")))
    	{     
      		if (request.getParameter("instruktor") != null)
    		{
      			clearStatus();
      			stInstructor = request.getParameter("instruktor");
    		}	
    		this.putStatus(modelMap);   
      		modelMap.put("kurser", this.kursManager.getKurser(stInstructor, "", 5));    		
            return new ModelAndView("WEB-INF/jsp/kurser.jsp", "modelMap", modelMap);            
    	}   
      	/*
    	if (request.getParameter("kurstyp") != null && request.getParameter("kurstyp").length() > 0)
    	{
    			modelMap.put("kurser", this.kursManager.getKurser(request.getParameter("kurstyp"), "", 2));
    			return new ModelAndView("WEB-INF/jsp/kurser.jsp", "modelMap", modelMap);
    	}
    	*/
      	putStatus(modelMap);
      	clearStatus();
		modelMap.put("kurser", this.kursManager.getKurser(calcStartDate()));
        return new ModelAndView("WEB-INF/jsp/kurser.jsp", "modelMap", modelMap);
        
    } 
    private java.sql.Date calcStartDate()
    {
    	Calendar gregCal = Calendar.getInstance();    	
		int year  = gregCal.get(Calendar.YEAR);
		int month = gregCal.get(Calendar.MONTH);
		if (month > 5)
			month = 6;
		else
			month = 0;
		gregCal.set(year - 1, month, 1);
    	return new java.sql.Date(gregCal.getTimeInMillis());
    }
    private void putStatus(Map<String, Object> modelMap)
    {
    	modelMap.put("stBeteckning", stBeteckning);
    	modelMap.put("stInstructor", stInstructor);
    	modelMap.put("stLokal", stLokal);
        modelMap.put("stType", stType);
        modelMap.put("stLevel", stLevel);
    }
    private void clearStatus()
    {
    	stBeteckning = "";
    	stInstructor = "";
    	stLokal = "";
    	stType = "";
    	stLevel = "";
    }
    public void setKursManager(KursManagerInterface kursManager) 
    {
        this.kursManager = kursManager;
    }
    
    public void setKursTypManager(KursTypManagerInterface kursTypManager) 
    {
        this.kursTypManager = kursTypManager;
    }
    
    public void setLokalManager(LokalManagerInterface lokalManager) 
    {
        this.lokalManager = lokalManager;
    }
    
    public void setInstruktorManager(InstruktorManagerInterface instruktorManager) 
    {
        this.instruktorManager = instruktorManager;
    }
}

