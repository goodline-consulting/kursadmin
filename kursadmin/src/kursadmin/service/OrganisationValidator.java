
package kursadmin.service;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import kursadmin.domain.Lokal;
import kursadmin.domain.Organisation;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress; 
public class OrganisationValidator implements Validator 
{
  

    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    public boolean supports(Class clazz) 
    {
        return Organisation.class.equals(clazz);
    }

    public void validate(Object obj, Errors errors) 
    {
        Organisation org = (Organisation) obj;
        if (org == null) 
        {
            errors.rejectValue("Organisation", "not-specified", null, "Value required.");
        }
        else 
        {

            if (org.getOrgnamn().length() == 0) 
            {
                errors.rejectValue("orgnamn", "Organisationsnamn fŒr inte vara blankt",
                    new Object[] {new String("")}, "Lokalnamn fŒr inte vara blankt");
            }    
            if (org.getEmail().length() > 0)
            {	
	            try 
	            {
					new javax.mail.internet.InternetAddress(org.getEmail()).validate();
				} 
	            catch (AddressException e) 
	            {
	                errors.rejectValue("email", "felaktig emailadress",
	                        new Object[] {new String("kalle")}, "Felaktig emailadress");
				}
            }    
            if (org.getPostnr().length() > 0)
            {
            	if(!org.getPostnr().matches("[0-9][0-9][0-9][ ][0-9][0-9]"))
            		errors.rejectValue("postnr", "",
                        new Object[] {new String("")}, "Postnummer skall skrivas i formatet '123 45'");
            		
            }           
        }
    }    
}


