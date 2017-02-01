package kursadmin.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Date;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import kursadmin.repository.LokalDao;
import kursadmin.repository.ProgramDao;
import kursadmin.service.KursTypManagerInterface;
import kursadmin.service.LokalManagerInterface;

public class ProgramPunktController implements Controller 
{

    protected final Log logger = LogFactory.getLog(getClass());
    private ProgramDao programManager;
    private KursTypManagerInterface kursTypManager;
    
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {   
    	Map<String, Object> modelMap = new HashMap<String, Object>();
    	String sokMall = null;
    	boolean aktuella;
    	int kursTyp;    	
    	if (request.getMethod().equals("POST"))
    	{
    		
    		if (request.getParameter("sokmall") != null)
    			sokMall = request.getParameter("sokmall");
    		aktuella = request.getParameter("aktuella") != null;
    		kursTyp = Integer.parseInt(request.getParameter("kurstyp"));
    		modelMap.put("aktuella", aktuella);
    		if (sokMall.length() > 0 || aktuella || kursTyp != 0)    			
    			modelMap.put("programPunkter", this.programManager.getProgramPunktList(kursTyp, sokMall, aktuella));
    		else
    			modelMap.put("programPunkter", this.programManager.getProgramPunktList());
        	modelMap.put("typer", this.kursTypManager.getKursTypList());
            return new ModelAndView("WEB-INF/jsp/programpunkter.jsp", "modelMap", modelMap);
    	}
    	if (request.getParameter("recid") != null)
    	{
    		return new ModelAndView("WEB-INF/jsp/programpunktinfo.jsp", "programpunkt", this.programManager.getProgramPunkt(Integer.parseInt(request.getParameter("recid"))));
    	}	
    	if (request.getParameter("radera") != null)
    	{
    		if (!this.programManager.deleteProgramPunkt(Integer.parseInt(request.getParameter("radera"))))
    			modelMap.put("error", "Programpunkten anvŠnds, radering ej tillåten");
    		modelMap.put("programPunkter", this.programManager.getProgramPunktList());
    		modelMap.put("typer", this.kursTypManager.getKursTypList());
            return new ModelAndView("WEB-INF/jsp/programpunkter.jsp", "modelMap", modelMap);
    	}    	           
    	modelMap.put("programPunkter", this.programManager.getProgramPunktList(0, "", true));
    	modelMap.put("typer", this.kursTypManager.getKursTypList());
    	modelMap.put("aktuella", true);
        return new ModelAndView("WEB-INF/jsp/programpunkter.jsp", "modelMap", modelMap);       
    }
    public void setProgramDao(ProgramDao programManager) 
    {
        this.programManager = programManager;
    }
    public void setKursTypManager(KursTypManagerInterface kursTypManager) 
    {
        this.kursTypManager = kursTypManager;
    }
}

