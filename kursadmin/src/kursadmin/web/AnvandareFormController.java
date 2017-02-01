package kursadmin.web;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kursadmin.service.AnvandareManager;
import kursadmin.domain.Anvandare;

public class AnvandareFormController extends SimpleFormController 
{
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private AnvandareManager anvandareManager;
    boolean ny;
    public ModelAndView onSubmit(Object command)
            throws ServletException 
    {
        
        if (ny)
        	anvandareManager.insertAnvandare((Anvandare) command);
        else
        	anvandareManager.updateAnvandare((Anvandare) command);        
        return new ModelAndView(new RedirectView(getSuccessView()));
    }

    protected Object formBackingObject(HttpServletRequest request) throws ServletException 
    {
    	String namn = request.getParameter("username");
    	if (namn.equals("0"))
    	{
    		ny = true;
    		return new Anvandare();
    	}    		
    	else
    	{	
    		ny = false;
    		return anvandareManager.getAnvandare(namn);
    	}
    }

    public void setAnvandareManager(AnvandareManager anvandareManager) 
    {
        this.anvandareManager = anvandareManager;
    }

    public AnvandareManager getAnvandareManager() 
    {
        return anvandareManager;
    }

}