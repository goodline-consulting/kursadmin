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

import kursadmin.repository.InstruktorDao;
import kursadmin.service.InstruktorManagerInterface;

public class InstruktorController implements Controller 
{

    protected final Log logger = LogFactory.getLog(getClass());
    private InstruktorManagerInterface instruktorManager;
    
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {   
    	Map<String, Object> modelMap = new HashMap<String, Object>();   
    	
    	if (request.getParameter("recid") != null)
    	{
    		return new ModelAndView("WEB-INF/jsp/instruktorinfo.jsp", "instruktor", this.instruktorManager.getInstruktor(Integer.parseInt(request.getParameter("recid"))));
    	}	
    	if (request.getParameter("radera") != null)
    	{
    		this.instruktorManager.deleteInstruktor(Integer.parseInt(request.getParameter("radera")));
    		modelMap.put("instruktors", this.instruktorManager.getInstruktors());
            return new ModelAndView("WEB-INF/jsp/instruktorer.jsp", "modelMap", modelMap);
    	}
    	//logger.info("Localadress:" + request.getContextPath() + " " + request.getServerName());
           
        modelMap.put("instruktors", this.instruktorManager.getInstruktors());
        return new ModelAndView("WEB-INF/jsp/instruktorer.jsp", "modelMap", modelMap);
        
    }
    public void setInstruktorManager(InstruktorManagerInterface instruktorManager) 
    {
        this.instruktorManager = instruktorManager;
    }


}

