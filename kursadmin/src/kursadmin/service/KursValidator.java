
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
                errors.rejectValue("beteckning", "Beteckning f�r inte vara blank",
                    new Object[] {new String("")}, "Beteckning f�r inte vara blank");
            }    
            if (kursAll.getKursnamn().length() == 0)
            {	
	            
	            errors.rejectValue("kursnamn", "Kursnamn f�r inte vara blankt",
	                new Object[] {new String("")}, "Kursnamn f�r inte vara blankt");				
            }    
            if (kursAll.getInstruktor() == 0)
            	errors.rejectValue("instruktor", "",
    	                new Object[] {new String("")}, "Du m�ste v�lja en instrukt�r eller 'ej best�md'");
            if (kursAll.getLokal() == 0)
            	errors.rejectValue("lokal", "",
    	                new Object[] {new String("")}, "Du m�ste v�lja en lokal eller 'ej best�md'");
            if (kursAll.getTid() == 0)
            	errors.rejectValue("tid", "",
    	                new Object[] {new String("")}, "Du m�ste v�lja en kurstyp'");
        }
    }    
}
