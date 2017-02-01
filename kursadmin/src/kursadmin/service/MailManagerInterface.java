package kursadmin.service;

import kursadmin.domain.Config;
import kursadmin.domain.KursAll;
import kursadmin.domain.MailConfig;
import java.io.Serializable;
import java.util.List;

public interface MailManagerInterface extends Serializable
{	
	public void sendMailKurs (MailConfig mc, String[] to, String fakturaPath);
	public void sendMail (MailConfig mc, String[] to, String[] attach);	
	public void sendSimpleMail (MailConfig mc, String[] to);	
}    










    
    
    
    

