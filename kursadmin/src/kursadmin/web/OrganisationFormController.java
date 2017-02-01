package kursadmin.web;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kursadmin.service.OrganisationManager;
import kursadmin.domain.Organisation;

public class OrganisationFormController extends SimpleFormController 
{
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private OrganisationManager orgManager;

    public ModelAndView onSubmit(Object command)
            throws ServletException 
    {
        
        if (((Organisation) command).getOid() == 0)
        	orgManager.insertOrganisation((Organisation) command);
        else
        	orgManager.updateOrganisation((Organisation) command);        
        return new ModelAndView(new RedirectView(getSuccessView()));
    }

    protected Object formBackingObject(HttpServletRequest request) throws ServletException 
    {
    	int oid = Integer.parseInt(request.getParameter("recid"));
    	if (oid == 0)
    		return new Organisation();
    	else
        	return orgManager.getOrganisation(oid);        
        
    }

    public void setOrganisationManager(OrganisationManager orgManager) 
    {
        this.orgManager = orgManager;
    }

    public OrganisationManager getOrganisationManager() 
    {
        return orgManager;
    }

}
