package kursadmin.service;

import kursadmin.domain.Config;
import kursadmin.domain.KursAll;
import kursadmin.domain.MailConfig;
import kursadmin.domain.MailSetup;
import kursadmin.domain.PathConfig;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;



public class MailManager implements MailManagerInterface
{
	private JavaMailSenderImpl mailSender;
	private ConfigManagerInterface configManager;
	private PathConfig pathConfig;
	private MailSetup mailSetup;
	
	private Session session;
	
	public void sendMailKurs (final MailConfig mc, final String[] to, final String fakturaPath)
	{
		
		MimeMessagePreparator prep = new MimeMessagePreparator() 
		{
			   public void prepare(MimeMessage mimeMessage) throws MessagingException 
			   {
			     MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");			     
			     if (mc.getSender() == null)
			    	 message.setFrom(mailSetup.getSender());
			     else
			       	message.setFrom(mc.getSender());
			     message.setTo(to);			   
			     message.setSubject(mc.getRubrik());
			     String body = "";
			     if (!(mc.getIngress() == null) && !mc.getIngress().equals(""))
			    	 body = mc.getIngress().replaceAll("\n", "<br>") + "<br><br>";
			     body += mc.getBody().replaceAll("\n", "<br>") + "<br><br>";
			     if (!(mc.getFinish() == null) && !mc.getFinish().equals(""))
			    	 body += mc.getFinish().replaceAll("\n", "<br>") + "<br><br>";
			     message.setText(body + mc.getFooter().replaceAll("\n", "<br>"), true);
			     if (fakturaPath != null)
			    	 message.addAttachment(fakturaPath, new File(pathConfig.getBilagor() + "/" + fakturaPath));
			   }  
	    };	    
				 		
		try
	    {			
		    mailSender.send(prep);
        }
        catch(MailException ex) 
        {
            //log it and go on
            System.err.println(ex.getMessage());            
        } 
		
	}
	public void sendMail (final MailConfig mc, final String[] to, final String[] attach)
	{
		
		MimeMessagePreparator prep = new MimeMessagePreparator() 
		{
			   public void prepare(MimeMessage mimeMessage) throws MessagingException 
			   {
			     MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			     if (mc.getSender() == null)
			    	 message.setFrom(mailSetup.getSender());
			     else
			       	message.setFrom(mc.getSender());
			     message.setTo(to);
			     if (attach != null)
			     {	 
			    	 for (int i = 0; i < attach.length; i++)
			    		 message.addAttachment(attach[i], new File(pathConfig.getBilagor() + "/" + attach[i]));
			     }	 
			     message.setSubject(mc.getRubrik());
			     message.setText(mc.getBody().replaceAll("\n", "<br>") + "<br><br>" + mc.getFooter().replaceAll("\n", "<br>"), true);
			   }  
	    };	    				 		
		try
	    {
		    mailSender.send(prep);
        }
        catch(MailException ex) 
        {
            //log it and go on
            System.err.println(ex.getMessage());            
        } 
		
	}
	public void sendSimpleMail (MailConfig mc, final String[] to)
	{
		SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setText(mc.getBody() + "\n\n\n" + mc.getFooter());
        msg.setSubject(mc.getRubrik());
        if (mc.getSender() == null)
        	msg.setFrom(mailSetup.getSender());
        else
        	msg.setFrom(mc.getSender());
        try
        {
            this.mailSender.send(msg);
        }
        catch(MailException ex) 
        {        
            System.err.println(ex.getMessage());            
        }

	}
	public void setMailSender(JavaMailSender mailSender) 
	{
		final String host = "smtpauth.c04.levonline.com";
		final String user = "c0420900";
		final String passwd = "Alice00l";
		
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);       
        properties.setProperty("mail.smtp.auth", "true");
        
		this.mailSender = (JavaMailSenderImpl) mailSender;
        session = Session.getDefaultInstance(properties, new Authenticator() 
        {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() 
            {
                return new PasswordAuthentication(user, passwd);
            }    
        });
        this.mailSender.setSession(session);
    }
	
	public void setConfigManager(ConfigManagerInterface configManager) 
	{
	        this.configManager = configManager;
	        this.pathConfig    = this.configManager.getPathConfig();	
	        this.mailSetup     = this.configManager.getMailSetup();
    }
 
}    










    
    
    
    

