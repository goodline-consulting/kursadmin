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
import kursadmin.service.OrganisationManagerInterface;

public class OrganisationController implements Controller 
{

    protected final Log logger = LogFactory.getLog(getClass());
    private OrganisationManagerInterface orgManager;
    
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {   
    	Map<String, Object> modelMap = new HashMap<String, Object>();   
    	
    	if (request.getParameter("recid") != null)
    	{
    		return new ModelAndView("WEB-INF/jsp/organisationinfo.jsp", "org", this.orgManager.getOrganisation(Integer.parseInt(request.getParameter("recid"))));
    	}	
    	if (request.getParameter("radera") != null)
    	{
    		this.orgManager.deleteOrganisation(Integer.parseInt(request.getParameter("radera")));
    		modelMap.put("orgs", this.orgManager.getOrgList());
            return new ModelAndView("WEB-INF/jsp/organisationer.jsp", "modelMap", modelMap);
    	}    	           
        modelMap.put("orgs", this.orgManager.getOrgList());
        return new ModelAndView("WEB-INF/jsp/organisationer.jsp", "modelMap", modelMap);
        
    }
    public void setOrganisationManager(OrganisationManagerInterface orgManager) 
    {
        this.orgManager = orgManager;
    }


}

