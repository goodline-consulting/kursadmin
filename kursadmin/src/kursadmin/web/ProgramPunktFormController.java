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

import kursadmin.repository.ProgramDao;
import kursadmin.service.KursTypManagerInterface;
import kursadmin.domain.ProgramPunkt;

public class ProgramPunktFormController extends SimpleFormController 
{
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private ProgramDao programManager;
    private KursTypManagerInterface kursTypManager;
    
    public ModelAndView onSubmit(Object command)
            throws ServletException 
    {
        
        if (((ProgramPunkt) command).getPpid() == 0)
        	programManager.insertProgramPunkt((ProgramPunkt) command);
        else
        	programManager.updateProgramPunkt((ProgramPunkt) command);        
        return new ModelAndView(new RedirectView(getSuccessView()));
    }

    protected Object formBackingObject(HttpServletRequest request) throws ServletException 
    {
    	int ppid = Integer.parseInt(request.getParameter("recid"));
    	if (ppid == 0)
    	{	
    		ProgramPunkt pp = new ProgramPunkt();
    		pp.setTid(1);
    		return pp; 
    	}	
    	else
        	return programManager.getProgramPunkt(ppid);        
        
    }
    protected Map referenceData(HttpServletRequest request) throws Exception
    {    	
    	Map<String, Object> map = new HashMap<String, Object>();    	    	
    	map.put("typer", kursTypManager.getKursTypList());    	
    	return map;
    }
    
    public void setProgramDao(ProgramDao programManager) 
    {
        this.programManager = programManager;
    }

    public ProgramDao getProgramDao() 
    {
        return programManager;
    }
    
    public void setKursTypManager(KursTypManagerInterface kursTypManager) 
    {
        this.kursTypManager = kursTypManager;
    }

    public KursTypManagerInterface getkursTypManager() 
    {
        return kursTypManager;
    }

}
