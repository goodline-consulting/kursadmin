
package kursadmin.service;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import kursadmin.domain.Instruktor;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress; 
public class InstruktorValidator implements Validator 
{
  

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    public boolean supports(Class clazz) 
    {
        return Instruktor.class.equals(clazz);
    }

    public void validate(Object obj, Errors errors) 
    {
        Instruktor instruktor = (Instruktor) obj;
        if (instruktor == null) 
        {
            errors.rejectValue("instruktor", "not-specified", null, "Value required.");
        }
        else 
        {
            logger.info("Validating with " + instruktor + ": " + instruktor.getIid());
            if (instruktor.getNamn().length() == 0) 
            {
                errors.rejectValue("namn", "Namn får inte vara blankt",
                    new Object[] {new String("")}, "Namn får inte vara blankt");
            }    
            if (instruktor.getEmail().length() > 0)
            {	
	            try 
	            {
					new javax.mail.internet.InternetAddress(instruktor.getEmail()).validate();
				} 
	            catch (AddressException e) 
	            {
	                errors.rejectValue("email", "felaktig emailadress",
	                        new Object[] {new String("kalle")}, "Felaktig emailadress");
				}
            }    
            if (instruktor.getPostnr().length() > 0)
            {
            	if(!instruktor.getPostnr().matches("[0-9][0-9][0-9][ ][0-9][0-9]"))
            		errors.rejectValue("postnr", "",
                        new Object[] {new String("")}, "Postnummer skall skrivas i formatet '123 45'");
            		
            }           
        }
    }    
}
