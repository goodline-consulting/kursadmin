
package kursadmin.service;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import kursadmin.domain.KursAll;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress; 
public class KursValidator implements Validator 
{
  

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    public boolean supports(Class clazz) 
    {
        return KursAll.class.equals(clazz);
    }

    public void validate(Object obj, Errors errors) 
    {
        KursAll kursAll = (KursAll) obj;
        if (kursAll == null) 
        {
            errors.rejectValue("Lokal", "not-specified", null, "Value required.");
        }
        else 
        {

            if (kursAll.getBeteckning().length() == 0) 
            {
                errors.rejectValue("beteckning", "Beteckning får inte vara blank",
                    new Object[] {new String("")}, "Beteckning får inte vara blank");
            }    
            if (kursAll.getKursnamn().length() == 0)
            {	
	            
	            errors.rejectValue("kursnamn", "Kursnamn får inte vara blankt",
	                new Object[] {new String("")}, "Kursnamn får inte vara blankt");				
            }    
            if (kursAll.getInstruktor() == 0)
            	errors.rejectValue("instruktor", "",
    	                new Object[] {new String("")}, "Du måste välja en instruktör eller 'ej bestämd'");
            if (kursAll.getLokal() == 0)
            	errors.rejectValue("lokal", "",
    	                new Object[] {new String("")}, "Du måste välja en lokal eller 'ej bestämd'");
            if (kursAll.getTid() == 0)
            	errors.rejectValue("tid", "",
    	                new Object[] {new String("")}, "Du måste välja en kurstyp'");
        }
    }    
}
