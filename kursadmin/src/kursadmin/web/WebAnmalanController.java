package kursadmin.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Date;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import kursadmin.domain.KursAll;
import kursadmin.domain.KursAnmalan;
import kursadmin.domain.MailConfig;
import kursadmin.domain.Person;
import kursadmin.domain.TackConfig;
import kursadmin.repository.LokalDao;
import kursadmin.service.ConfigManagerInterface;
import kursadmin.service.KursAnmalanManagerInterface;
import kursadmin.service.LokalManagerInterface;
import kursadmin.service.MailManagerInterface;

public class WebAnmalanController implements Controller 
{

    protected final Log logger = LogFactory.getLog(getClass());
    private KursAnmalanManagerInterface kursAnmalanManager;
    private MailManagerInterface mailManager;
    private ConfigManagerInterface configManager;
    
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {   
    	Map<String, Object> modelMap = new HashMap<String, Object>();
    	/*
    	for (Enumeration<String> pars = request.getParameterNames(); pars.hasMoreElements();)
		{	    		
			String par = pars.nextElement();
			logger.info("parameter: " + par + " värde " + request.getParameter(par));				
		}
		*/
    	String[] kids = request.getParameter("valdkurs").split(",");
    	String fnamn = request.getParameter("fnamn");
    	String enamn = request.getParameter("enamn");
    	String email = request.getParameter("email");
    	// Skapa för kursanmälan
    	List<KursAll> kursLista = new ArrayList<KursAll>();
    	for (int i = 0; i < kids.length; i++)
    	{	
    		KursAll kursAll = kursAnmalanManager.creKursAnmalan(Integer.parseInt(kids[i]), fnamn, enamn, 
	    			                          request.getParameter("telefon") == null ? "" : request.getParameter("telefon"),
	    			                          email,
	    			                          request.getParameter("info") == null ? "" : request.getParameter("info"));
    		kursLista.add(kursAll);
	       	// Skicka mail till kursadmin
	    	String toArr[] = {request.getParameter("recipient")};     	
	    	MailConfig mail = new MailConfig();        
	        mail.setRubrik(request.getParameter("subject") + " (" + kursAll.getBeteckning() + ")");
	        mail.setFooter("");
	        mail.setSender(request.getParameter("email"));
	        mail.setBody("Kursanm‰lan till " + 
	        		      kursAll.getKursNiva() + " i " +
	        		      kursAll.getLokalNamn() + ", " + 
	        		      kursAll.getVeckodag() + "ar kl " +
	        		      kursAll.getStarttid() + "\n" +
	        		      fnamn + " " + enamn + " \n" +
	                      "Info:\n" + request.getParameter("info") + "\n");                      
	    	mailManager.sendMail(mail, toArr, null);
        }
    	KursAll kursAll = kursLista.get(0);
		TackConfig tc = configManager.getTackConfig(kursAll.getTid());
		modelMap.put("style", tc.getStyle());
       	modelMap.put("logga", tc.getLogga());
       	modelMap.put("loggaW", tc.getLoggaW());
       	modelMap.put("loggaH", tc.getLoggaH());
       	modelMap.put("actionUrl", tc.getActionUrl());
       	modelMap.put("rubrik", parseConfig(tc.getHeader(), kursAll, fnamn, enamn, email));  
    	List<String> kursTexter = new ArrayList<String>();
    	for (KursAll ka:kursLista)
    	{
    		kursTexter.add(parseConfig(tc.getBody(), ka, fnamn, enamn, email));
    	}
    	modelMap.put("body", kursTexter);        	     	
       	modelMap.put("footer", parseConfig(tc.getFooter(), kursAll, fnamn, enamn, email));
        return new ModelAndView("WEB-INF/jsp/tack.jsp", "modelMap", modelMap);
        
    }
    private String parseConfig(String mall, KursAll kursAll, String fnamn, String enamn, String email)
    {
    	String rubrik = mall;
    	if (mall.indexOf('#') != -1)
		{
    		rubrik = rubrik.replaceAll("#kurstyp", kursAll.getKursTyp());
			rubrik = rubrik.replaceAll("#kursniva", kursAll.getKursNiva());
			rubrik = rubrik.replaceAll("#beteckning", kursAll.getBeteckning());
			rubrik = rubrik.replaceAll("#kursnamn", kursAll.getKursnamn());
			rubrik = rubrik.replaceAll("#startdag", kursAll.getStartdatum().toString());
			rubrik = rubrik.replaceAll("#starttid", kursAll.getStarttid());
			rubrik = rubrik.replaceAll("#veckodag", kursAll.getVeckodag());
			rubrik = rubrik.replaceAll("#plats", kursAll.getLokalNamn());
			rubrik = rubrik.replaceAll("#kursavgift", kursAll.getPris() + ":-");
			rubrik = rubrik.replaceAll("#fornamn", fnamn);
			rubrik = rubrik.replaceAll("#efternamn", enamn);
			rubrik = rubrik.replaceAll("#email", email);			
		}	
    	return rubrik;
    }
    
    
    public void setkursAnmalanManager(KursAnmalanManagerInterface kursAnmalanManager) 
    {
        this.kursAnmalanManager = kursAnmalanManager;
    }
    public void setMailManager(MailManagerInterface mailManager)
    {
    	this.mailManager = mailManager;
    }
    public void setConfigManager(ConfigManagerInterface configManager)
    {
    	this.configManager = configManager;
    }
}

