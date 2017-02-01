
package kursadmin.service;

import java.util.GregorianCalendar;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import kursadmin.domain.Kalender;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress; 
public class KalenderValidator implements Validator 
{
  

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    public boolean supports(Class clazz) 
    {
        return Kalender.class.equals(clazz);
    }

    public void validate(Object obj, Errors errors) 
    {
    	Kalender kalender = (Kalender) obj;
        if (kalender == null) 
        {
            errors.rejectValue("Kalender", "not-specified", null, "Value required.");
        }
        else 
        {

            if (kalender.getRubrik().length() == 0) 
            {
                errors.rejectValue("rubrik", "Du måste välja en rubrik",
                    new Object[] {new String("")}, "Rubriken får inte vara tom");
            }
            if (kalender.getInfo().length() > 10000)
            	errors.rejectValue("info", "texten är för lång, max 10 000 tecken",
                        new Object[] {new String("")}, "texten är för lång, max 10 000 tecken");
            // check the date and time
            GregorianCalendar cal =  new GregorianCalendar(kalender.getAr(), 
            							     			   kalender.getMan(),
            							     			   kalender.getDag(),
            							     			   kalender.getTim(),
            							     			   kalender.getMin());
                                 
        }
    }    
}
