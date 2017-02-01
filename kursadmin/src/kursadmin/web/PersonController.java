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

import kursadmin.domain.Person;
import kursadmin.repository.PersonDao;
import kursadmin.service.KursManagerInterface;
import kursadmin.service.PersonManagerInterface;

public class PersonController implements Controller 
{

    protected final Log logger = LogFactory.getLog(getClass());
    private PersonManagerInterface personManager;
    private KursManagerInterface kursManager;
    private boolean fromHome = false;
    private String criteria[] = new String[]{"", ""};
    private String fnamn;
    private String enamn;
    
    
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {   
    	Map<String, Object> modelMap = new HashMap<String, Object>();    
    	if (request.getParameter("homepage") != null)
	    	fromHome = true;
    	else
    		fromHome = false;
    	modelMap.put("fromHome", fromHome);
    	// logger.info("Returning personer total view " + request.getMethod());
    	if (request.getParameter("recid") != null)
    	{
    		int pid = Integer.parseInt(request.getParameter("recid"));
    		modelMap.put("person", this.personManager.getPerson(pid));		
    		modelMap.put("kurser", this.kursManager.getKurser(pid));    		
    		return new ModelAndView("WEB-INF/jsp/personinfo.jsp", "modelMap", modelMap);
    	}	
    	else if (request.getParameter("radera") != null)
    	{
    		this.personManager.deletePerson(Integer.parseInt(request.getParameter("radera")));    		
    	}
    	else if (request.getParameter("obetalda") != null)
    	{  
    		fnamn = "";
    		enamn = "";
    		criteria = new String[]{"obetalda", ""};
    	}
    	else if (request.getParameter("ofakt") != null)
    	{   
    		fnamn = "";
    		enamn = "";
    		criteria = new String[]{"ofakt", ""};
    	}
    	else if (request.getParameter("enamn") != null && request.getParameter("enamn").length() > 0)
    	{
    		enamn = request.getParameter("enamn");
    		fnamn = "";
    		criteria = new String[]{"enamn", enamn};
    	}
    	else if (request.getParameter("fnamn") != null && request.getParameter("fnamn").length() > 0)
    	{
    		fnamn = request.getParameter("fnamn");
    		enamn = "";
    		criteria = new String[]{"fnamn", fnamn};
    	}
    	else if (request.getMethod().equals("GET") && criteria[0].equals(""))
        {	
        	enamn = "a";
			fnamn = "";
			criteria = new String[]{"enamn", enamn};
        }
    	else if (request.getMethod().equals("POST"))
    	{
    		criteria = new String[]{"alla", ""};
    	}
        if (criteria[0].equals("obetalda"))
        	modelMap.put("persons", this.personManager.getPersonsObet());
        else if (criteria[0].equals("ofakt"))
        	modelMap.put("persons", this.personManager.getPersonsOfakt());
        else if (criteria[0].equals("fnamn"))
        	modelMap.put("persons", this.personManager.getPersons(criteria[1], false));
        else if (criteria[0].equals("enamn"))
        	modelMap.put("persons", this.personManager.getPersons(criteria[1], true));
        else if (criteria[0].equals("alla"))
        	modelMap.put("persons", this.personManager.getPersons());
        modelMap.put("fnamn", fnamn);
        modelMap.put("enamn", enamn);
        return new ModelAndView("WEB-INF/jsp/personer.jsp", "modelMap", modelMap);
        
    }
    public void setPersonManager(PersonManagerInterface personManager) 
    {
        this.personManager = personManager;
    }
    public void setKursManager(KursManagerInterface kursManager) 
    {
        this.kursManager = kursManager;
    }

}

