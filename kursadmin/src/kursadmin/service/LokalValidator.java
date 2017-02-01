
package kursadmin.service;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import kursadmin.domain.Lokal;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress; 
public class LokalValidator implements Validator 
{
  

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    public boolean supports(Class clazz) 
    {
        return Lokal.class.equals(clazz);
    }

    public void validate(Object obj, Errors errors) 
    {
        Lokal lokal = (Lokal) obj;
        if (lokal == null) 
        {
            errors.rejectValue("Lokal", "not-specified", null, "Value required.");
        }
        else 
        {

            if (lokal.getLokalnamn().length() == 0) 
            {
                errors.rejectValue("lokalnamn", "Lokalnamn får inte vara blankt",
                    new Object[] {new String("")}, "Lokalnamn får inte vara blankt");
            }    
            if (lokal.getEmail().length() > 0)
            {	
	            try 
	            {
					new javax.mail.internet.InternetAddress(lokal.getEmail()).validate();
				} 
	            catch (AddressException e) 
	            {
	                errors.rejectValue("email", "felaktig emailadress",
	                        new Object[] {new String("kalle")}, "Felaktig emailadress");
				}
            }    
            if (lokal.getPostnr().length() > 0)
            {
            	if(!lokal.getPostnr().matches("[0-9][0-9][0-9][ ][0-9][0-9]"))
            		errors.rejectValue("postnr", "",
                        new Object[] {new String("")}, "Postnummer skall skrivas i formatet '123 45'");
            		
            }           
        }
    }    
}


