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
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import kursadmin.domain.Elev;
import kursadmin.domain.KursAll;
import kursadmin.domain.Person;
import kursadmin.repository.ElevDao;
import kursadmin.repository.KursDao;
import kursadmin.service.ElevManagerInterface;
import kursadmin.service.KursManagerInterface;
import kursadmin.service.LokalManagerInterface;
import kursadmin.service.InstruktorManagerInterface;
import kursadmin.service.PersonManagerInterface;
public class FaktureraController implements Controller 
{

    protected final Log logger = LogFactory.getLog(getClass());
    private KursManagerInterface kursManager;
    private ElevManagerInterface elevManager;
    private PersonManagerInterface personManager;
    private int pid = 0;
    private int kid = 0;
    private String kidArr = null;
    private String target = null;
    private Person person = null;    
    private List<KursAll> kurser = new ArrayList<KursAll>();
    
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {   
    	Map<String, Object> modelMap = new HashMap<String, Object>();   
    	// AnvŠndaren har valt
    	if (request.getMethod().equals("POST"))
    	{
    		String returKids = "";
    		// GŒ igenom kurslistan och se om kursens checkbox finns med
    		for (KursAll kurs: kurser) 
    		{
    			if (request.getParameter("kurs" + kurs.getKid()) != null)
    				returKids += kurs.getKid() + ",";
    		}
    		if (!returKids.equals(""))
    			returKids = returKids.substring(0, returKids.length() - 1);
    		if (kid != 0)
    		{
    			String msg;
    			if (returKids.equals(""))
    				msg = "AnmŠlningsbekrŠftelse men ingen faktura har skickats till " + person.getFnamn() + " " + person.getEnamn();
    			else
    				msg = "AnmŠlningsbekrŠftelse och faktura har skickats till " + person.getFnamn() + " " + person.getEnamn();
    			return new ModelAndView(new RedirectView("elever.htm?kid=" + kid + "&fakturaretur=" + msg + "&kurser=" + returKids  + "&pid=" + pid));
    		}	
    		else if (kidArr != null)
    		{
    			return new ModelAndView(new RedirectView("anmalningar.htm?fakturaretur=yes&kurser=" + returKids  + "&pid=" + pid));
    		}
    		else
    			return new ModelAndView(new RedirectView("personer.htm"));
    	}
    	// Detta Šr fšrsta anropet
    	else    		
    	{
    		pid = Integer.parseInt(request.getParameter("pid"));
    		kurser = this.kursManager.getOfakturerade(pid);
    		if (request.getParameter("kid") != null) // Vi kommer ifrŒn kursdeltagarbilden
    		{	
    			kid = Integer.parseInt(request.getParameter("kid"));
    			target = "elever.htm?kid=" + kid;
    			kidArr = null;
    		}
    		else if (request.getParameter("kidArr") != null) // Vi kommer ifrŒn kursanmŠlninsbilden
    		{	
    			kid = 0;
    			kidArr = request.getParameter("kidArr");
    			target = "anmalningar.htm";
    		}
    		else 
    		{    			
    			kid = 0;
    			kidArr = null;
    			target = "personer.htm";
    		}
    		for (KursAll kurs: kurser)
    		{
    			// OBS vi lŒnar fŠltet typavrabatt fšr att signalera om kursens checkbox skall vara vald.
    			kurs.setTypavrabatt(0);
    			if (kurs.getKid() == kid)
    				kurs.setTypavrabatt(1);
    			else if (kidArr != null)
    			{
    				if (kidArr.indexOf("" + kurs.getKid()) > -1)
    					kurs.setTypavrabatt(1);
    			}
    		}
    		
    		person = personManager.getPerson(pid);
    		modelMap.put("target", target);
    	    modelMap.put("kurser", kurser);   		
    		modelMap.put("person", person);
    	}
    	return new ModelAndView("WEB-INF/jsp/fakturera.jsp", "modelMap", modelMap);
    }
    public void setKursManager(KursManagerInterface kursManager) 
    {
        this.kursManager = kursManager;
    }
    public void setElevManager(ElevManagerInterface elevManager) 
    {
        this.elevManager = elevManager;
    }
    public void setPersonManager(PersonManagerInterface personManager) 
    {
        this.personManager = personManager;
    }
}

