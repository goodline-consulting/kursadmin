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
import kursadmin.domain.GrundConfig;
import kursadmin.domain.MailConfig;

public class GrundConfigFormController extends SimpleFormController 
{
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private ConfigManagerInterface configManager;
    private String configType;
    private int    kursType;
    
    public ModelAndView onSubmit(Object command)
            throws ServletException 
    {
        
        configManager.setGrundConfig((GrundConfig) command);        
        return new ModelAndView(new RedirectView(getSuccessView()));
    }
    protected Map referenceData(HttpServletRequest request) throws Exception
    {
    	
    	Map<String, Object> map = new HashMap<String, Object>(); 	
    	return map;
    }
    protected Object formBackingObject(HttpServletRequest request) throws ServletException 
    {    	
    
    	 return configManager.getGrundConfig();    	                
    }

    public void setConfigManager(ConfigManagerInterface configManager) 
    {
        this.configManager = configManager;
    }
    
}
