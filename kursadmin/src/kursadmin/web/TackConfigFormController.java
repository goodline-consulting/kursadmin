package kursadmin.web;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kursadmin.service.ConfigManagerInterface;
import kursadmin.domain.TackConfig;

public class TackConfigFormController extends SimpleFormController 
{
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private ConfigManagerInterface configManager;
    private int    kurstyp;
    
    public ModelAndView onSubmit(Object command)
            throws ServletException 
    {
        
        configManager.setTackConfig(kurstyp, (TackConfig) command);        
        return new ModelAndView(new RedirectView(getSuccessView()));
    }
    protected Map referenceData(HttpServletRequest request) throws Exception
    {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	String formRubrik;
    	String kurstypsNamn = request.getParameter("kurstypsnamn");
    	formRubrik = "Konfiguration av Tack för anmälan - " + kurstypsNamn;
    	map.put("formrubrik", formRubrik);	
    	return map;
    }
    protected Object formBackingObject(HttpServletRequest request) throws ServletException 
    {    	
    	 
    	 kurstyp = Integer.parseInt(request.getParameter("kurstyp"));
    	 return configManager.getTackConfig(kurstyp);    	                
    }

    public void setConfigManager(ConfigManagerInterface configManager) 
    {
        this.configManager = configManager;
    }
    
}
