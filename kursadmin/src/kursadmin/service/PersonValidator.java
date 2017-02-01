
package kursadmin.service;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kursadmin.domain.Faktura;
import kursadmin.domain.Person;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress; 
public class PersonValidator implements Validator 
{
    
    private PersonManager personManager;
    private FakturaManager fakturaManager;
    
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    public boolean supports(Class clazz) 
    {
        return Person.class.equals(clazz);
    }

    public void validate(Object obj, Errors errors) 
    {
        Person person = (Person) obj;
        if (person == null) 
        {
            errors.rejectValue("person", "error.not-specified", null, "Value required.");
        }
        else 
        {
            // logger.info("Validating with " + person + ": " + person.getPid());
            if (person.getFnamn().length() == 0) 
            {
                errors.rejectValue("fnamn", "", null, "   Fšrnamn fŒr inte vara blankt");
                    
            }    
            if (person.getEmail().length() > 0)
            {	
	            try 
	            {
					new javax.mail.internet.InternetAddress(person.getEmail()).validate();
				} 
	            catch (AddressException e) 
	            {
	                errors.rejectValue("email", "felaktig emailadress",
	                        new Object[] {new String("")}, "Felaktig emailadress");
				}
            }    
            if (person.getPostnr().length() > 0)
            {
            	if(!person.getPostnr().matches("[0-9][0-9][0-9][ ][0-9][0-9]"))
            		errors.rejectValue("postnr", "",
                        new Object[] {new String("")}, "Postnummer skall skrivas i formatet '123 45'");
            		
            }
            if (person.getForminput1().equals("faktura"))
            {
            	try 
            	{
					int fakturanr = Integer.parseInt(person.getForminput2());
					Faktura faktura = fakturaManager.getFaktura(fakturanr); 
					if (faktura == null)
						errors.rejectValue("forminput2", "",
	                            new Object[] {new String("")}, "Ogiltigt fakturanummer, angiven faktura saknas");
					else if (faktura.getBetald() != null)
						errors.rejectValue("forminput2", "",
	                            new Object[] {new String("")}, "Angiven faktura Šr fullt betald");
				} 
            	catch (NumberFormatException e) 
            	{
            		errors.rejectValue("forminput2", "",
                            new Object[] {new String("")}, "Ogiltiga tecken i fakturanummer");
                		
				}
            }
            else if (person.getForminput1().equals("person"))
            {
            	try 
            	{
					int pid = Integer.parseInt(person.getForminput2());
					if (personManager.getPerson(pid) == null)
						errors.rejectValue("forminput2", "",
	                            new Object[] {new String("")}, "Ogiltigt kundnummer, angiven kund saknas");
				} 
            	catch (NumberFormatException e) 
            	{
            		errors.rejectValue("forminput2", "",
                            new Object[] {new String("")}, "Ogiltiga tecken i kundnummer");
				}
            }
        }    
    }

    public void setPersonManager(PersonManager personManager) 
    {
        this.personManager = personManager;
    }
    public void setFakturaManager(FakturaManager fakturaManager)
    {
    	this.fakturaManager = fakturaManager;
    }
}
