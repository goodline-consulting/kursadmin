package kursadmin.web;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kursadmin.service.LokalManager;
import kursadmin.domain.Lokal;

public class LokalFormController extends SimpleFormController 
{
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private LokalManager lokalManager;

    public ModelAndView onSubmit(Object command)
            throws ServletException 
    {
        
        if (((Lokal) command).getLid() == 0)
        	lokalManager.insertLokal((Lokal) command);
        else
        	lokalManager.updateLokal((Lokal) command);        
        return new ModelAndView(new RedirectView(getSuccessView()));
    }

    protected Object formBackingObject(HttpServletRequest request) throws ServletException 
    {
    	int lid = Integer.parseInt(request.getParameter("recid"));
    	if (lid == 0)
    		return new Lokal();
    	else
        	return lokalManager.getLokal(lid);        
        
    }

    public void setLokalManager(LokalManager lokalManager) 
    {
        this.lokalManager = lokalManager;
    }

    public LokalManager getLokalManager() 
    {
        return lokalManager;
    }

}
