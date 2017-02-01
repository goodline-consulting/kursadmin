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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import kursadmin.service.KalenderManagerInterface;
import kursadmin.service.ConfigManagerInterface;
import kursadmin.domain.Config;
import kursadmin.domain.Kalender;

public class KalenderConfigController implements Controller 
{

    protected final Log logger = LogFactory.getLog(getClass());   
    private ConfigManagerInterface configManager;
    private ArrayList<Config> viktlista;
    private ArrayList<Config> aktlista;
  
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {   
    	Map<String, Object> modelMap = new HashMap<String, Object>();   
    	
    	if (request.getMethod().equals("GET"))
    	{
    		viktlista = (ArrayList<Config>) this.configManager.getConfigList("kalvikt");
            aktlista = (ArrayList<Config>) this.configManager.getConfigList("kalakt");
            modelMap.put("aktlista", aktlista);
        	modelMap.put("viktlista", viktlista);
    		return new ModelAndView("WEB-INF/jsp/kalenderconfig.jsp", "modelMap", modelMap);
    	}	
    	aktlista.clear();
    	viktlista.clear();
    	for (Enumeration<String> pars = request.getParameterNames(); pars.hasMoreElements();)
		{	    		
			String par = pars.nextElement();
			if (par.startsWith("humle"))
			{
				Config cfg = new Config();
				cfg.setKategori("kalvikt");
				cfg.setNamn(par.substring(5));
                cfg.setVarde(request.getParameter(par));
                viktlista.add(cfg);
			}
			else if (par.startsWith("dumle"))
			{
				Config cfg = new Config();
				cfg.setKategori("kalakt");
				cfg.setNamn(par.substring(5));
                cfg.setVarde(request.getParameter(par));
                aktlista.add(cfg);
			}					
			
		}   
    	configManager.setConfigList(aktlista);
		configManager.setConfigList(viktlista);
        return new ModelAndView("WEB-INF/jsp/config.jsp");  
    }
    
    public void setConfigManager(ConfigManagerInterface configManager) 
    {
        this.configManager = configManager;
        
    }
   
   

}

