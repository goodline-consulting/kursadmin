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
import kursadmin.domain.MailConfig;

public class MailConfigFormController extends SimpleFormController 
{
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private ConfigManagerInterface configManager;
    private String configType;
    private int    kursType;
    
    public ModelAndView onSubmit(Object command)
            throws ServletException 
    {
        
        configManager.setMailConfig(configType, kursType, (MailConfig) command);        
        return new ModelAndView(new RedirectView(getSuccessView()));
    }
    protected Map referenceData(HttpServletRequest request) throws Exception
    {
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	String formRubrik;
    	if (configType.equals("anmbekr"))
    		formRubrik = "Konfiguration av anmälnings bekräftelse";
    	else if (configType.equals("betbekr"))
    		formRubrik = "Konfiguration av betalnings bekräftelse";
    	else
    		formRubrik = "";
    	map.put("formrubrik", formRubrik);	
    	return map;
    }
    protected Object formBackingObject(HttpServletRequest request) throws ServletException 
    {    	
    	 configType = request.getParameter("mailtyp");
    	 kursType = Integer.parseInt(request.getParameter("kurstyp"));
    	 return configManager.getMailConfig(configType, kursType);    	                
    }

    public void setConfigManager(ConfigManagerInterface configManager) 
    {
        this.configManager = configManager;
    }
    
}
