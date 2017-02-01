
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

import kursadmin.service.FakturaManager;
import kursadmin.service.KursManager;
import kursadmin.service.PersonManager;
import kursadmin.domain.Kurs;
import kursadmin.domain.KursAll;
import kursadmin.domain.Person;

public class PersonFormController extends SimpleFormController 
{
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private PersonManager personManager;
    private KursManager   kursManager;
    private FakturaManager fakturaManager;
    
    private int anslutTillKurs = 0;
    public ModelAndView onSubmit(Object command)
            throws ServletException 
    {
    	Person person = (Person) command;     	
    	if (person.getSaldo() < 0 && !person.getForminput1().equals("behall"))
		{
			if (person.getForminput1().equals("utbetala"))
				personManager.betalaSaldo((Person) command);
			else if (person.getForminput1().equals("person"))
			{
				personManager.flyttaSaldo(person, Integer.parseInt(person.getForminput2()));
			}
			else if (person.getForminput1().equals("faktura"))
			{
				double saldo = fakturaManager.betalaFakturaMedSaldo(person, Integer.parseInt(person.getForminput2()));
				person.setSaldo(- saldo);
			}
		}	
    	
    	if (((Person) command).getPid() == 0)
        {	
        	if (anslutTillKurs > 0)
        		personManager.insertPerson((Person) command, anslutTillKurs);
        	else
        		personManager.insertPerson((Person) command);
        }	
        else
        {	
        	if (anslutTillKurs > 0)
        		personManager.updatePerson((Person) command, anslutTillKurs);
        	else
        		personManager.updatePerson((Person) command);
        }	
        //logger.info("kursMAP " + anslutTillKurs);	
        // logger.info("returning from personEditForm view to " + getSuccessView());

        return new ModelAndView(new RedirectView(getSuccessView()));
    }
    protected boolean isFormChangeRequest(HttpServletRequest request, Object command)
    {    	
    	if (!request.getParameter("kurs").equals("0"))
    	{
    		anslutTillKurs = Integer.parseInt(request.getParameter("kurs"));    		
    	}    	
    	return false;
    }
    protected Map referenceData(HttpServletRequest request) throws Exception
    {    	    	
    	Map<String, Object> map = new HashMap<String, Object>();   	    	    	
    	map.put("kurser", kursManager.getCurrentKursList());    	
    	return map;
    }
    protected Object formBackingObject(HttpServletRequest request) throws ServletException 
    {
    	int pid = Integer.parseInt(request.getParameter("recid"));
    	anslutTillKurs = 0;
    	Person person;
    	if (pid == 0)
    		person = new Person();
    	else
        	person =  personManager.getPerson(pid);
    	person.setForminput1("behall");
        return person;
    }
    public void setKursManager(KursManager kursManager) 
    {
        this.kursManager = kursManager;
    }

    public KursManager getKursManager() 
    {
        return kursManager;
    }
    public void setPersonManager(PersonManager personManager) 
    {
        this.personManager = personManager;
    }

    public PersonManager getPersonManager() 
    {
        return personManager;
    }
    public void setFakturaManager(FakturaManager fakturaManager)
    {
    	this.fakturaManager = fakturaManager;
    }
}
