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
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import kursadmin.domain.Anvandare;
import kursadmin.domain.Nvaro;
import kursadmin.repository.AnvandareDao;
import kursadmin.service.AnvandareManagerInterface;

public class AnvandareController implements Controller 
{

    protected final Log logger = LogFactory.getLog(getClass());
    private AnvandareManagerInterface anvandareManager;
    
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {   
    	Map<String, Object> modelMap = new HashMap<String, Object>();   
    	
    	if (request.getParameter("radera") != null)
    	{
    		logger.info("raderar " + request.getParameter("radera") + " " + request.getContextPath() + " " + request.getServerName());
    		this.anvandareManager.deleteAnvandare(request.getParameter("radera"));
    		modelMap.put("anvandare", convertRoles());
            return new ModelAndView("WEB-INF/jsp/anvandare.jsp", "modelMap", modelMap);
    	}
    	//logger.info("Localadress:" + request.getContextPath() + " " + request.getServerName());
           
        modelMap.put("anvandare", convertRoles());
        return new ModelAndView("WEB-INF/jsp/anvandare.jsp", "modelMap", modelMap);
        
    }
    public void setAnvandareManager(AnvandareManagerInterface anvandareManager) 
    {
        this.anvandareManager = anvandareManager;
    }
    private List convertRoles()
    {
    	List<Anvandare> roles = this.anvandareManager.getAnvandareList();
    	for (Anvandare anv: roles)
		{
			if (anv.getRoll().equals("ROLE_PUBLIC"))
				anv.setRoll("PUBLIC");
			else if (anv.getRoll().equals("ROLE_USER"))
				anv.setRoll("USER");    			
			else if (anv.getRoll().equals("ROLE_MANAGER"))
				anv.setRoll("MANAGER");    			
			else if (anv.getRoll().equals("ROLE_ADMIN"))
				anv.setRoll("ADMIN");    			
		}    	
    	return roles;
    }

}

