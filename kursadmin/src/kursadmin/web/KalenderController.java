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
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import kursadmin.service.KalenderManagerInterface;
import kursadmin.service.ConfigManagerInterface;
import kursadmin.domain.Config;
import kursadmin.domain.Kalender;

public class KalenderController implements Controller 
{

    protected final Log logger = LogFactory.getLog(getClass());
    private KalenderManagerInterface kalenderManager;
    private ConfigManagerInterface configManager;
    private ArrayList<Kalender> lista;
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {   
    	Map<String, Object> modelMap = new HashMap<String, Object>();   
    	
    	if (request.getParameter("recid") != null)
    	{
    		for (Kalender kal: lista)
    			if (Integer.parseInt(request.getParameter("recid")) == kal.getCid())
    				return new ModelAndView("WEB-INF/jsp/kalenderinfo.jsp", "kalender", kal);

    	}	
    	if (request.getParameter("radera") != null)
    	{
    		this.kalenderManager.deleteKalender(Integer.parseInt(request.getParameter("radera")));
    		modelMap.put("kalender", this.kalenderManager.getKalender());
            return new ModelAndView("WEB-INF/jsp/kalender.jsp", "modelMap", modelMap);
    	}    	           
    	lista = (ArrayList<Kalender>) kalenderManager.getKalender();
    	setVikt();
    	modelMap.put("kalender", lista);
    	modelMap.put("datum", getDate());
        return new ModelAndView("WEB-INF/jsp/kalender.jsp", "modelMap", modelMap);
        
    }
    public void setKalenderManager(KalenderManagerInterface kalenderManager) 
    {
        this.kalenderManager = kalenderManager;
    }
    public void setConfigManager(ConfigManagerInterface configManager) 
    {
        this.configManager = configManager;
    }
    private Date getDate()
    {
    	return new Date();
    }
    private void setVikt()
    {
    	ArrayList<Config> cfglist = (ArrayList<Config>) configManager.getConfigList("kalvikt");
    	for (Kalender kal : lista)
    		for (Config cfg: cfglist)
    			if (Integer.parseInt(cfg.getNamn()) == kal.getVikt())
    			{
    				kal.setAltrubrik(cfg.getVarde());
    				break;
    			}
    }

}

