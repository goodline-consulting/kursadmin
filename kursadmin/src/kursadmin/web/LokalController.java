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
import kursadmin.service.LokalManagerInterface;

public class LokalController implements Controller 
{

    protected final Log logger = LogFactory.getLog(getClass());
    private LokalManagerInterface lokalManager;
    
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {   
    	Map<String, Object> modelMap = new HashMap<String, Object>();   
    	
    	if (request.getParameter("recid") != null)
    	{
    		return new ModelAndView("WEB-INF/jsp/lokalinfo.jsp", "lokal", this.lokalManager.getLokal(Integer.parseInt(request.getParameter("recid"))));
    	}	
    	if (request.getParameter("radera") != null)
    	{
    		this.lokalManager.deleteLokal(Integer.parseInt(request.getParameter("radera")));
    		modelMap.put("lokaler", this.lokalManager.getLokaler());
            return new ModelAndView("WEB-INF/jsp/lokaler.jsp", "modelMap", modelMap);
    	}    	           
        modelMap.put("lokaler", this.lokalManager.getLokaler());
        return new ModelAndView("WEB-INF/jsp/lokaler.jsp", "modelMap", modelMap);
        
    }
    public void setLokalManager(LokalManagerInterface lokalManager) 
    {
        this.lokalManager = lokalManager;
    }


}

