package kursadmin.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javazoom.upload.MultipartFormDataRequest;
import javazoom.upload.UploadBean;
import javazoom.upload.UploadException;
import javazoom.upload.UploadFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import kursadmin.service.ConfigManagerInterface;
import kursadmin.service.KursManagerInterface;
import kursadmin.service.KursTypManagerInterface;
import kursadmin.service.MailManagerInterface;
import kursadmin.service.LokalManagerInterface;
import kursadmin.service.InstruktorManagerInterface;
import kursadmin.repository.MailMottagarDao;
import kursadmin.domain.GrundConfig;
import kursadmin.domain.Instruktor;
import kursadmin.domain.Kurs;
import kursadmin.domain.KursNiva;
import kursadmin.domain.KursTyp;
import kursadmin.domain.Lokal;
import kursadmin.domain.MailConfig;
import kursadmin.domain.MailMottagare;
import kursadmin.domain.MailSetup;
import kursadmin.domain.PathConfig;


public class MailUrvalController implements Controller 
{

    protected final Log logger = LogFactory.getLog(getClass());
    private LokalManagerInterface lokalManager;
    private InstruktorManagerInterface instruktorManager;
    private ConfigManagerInterface configManager;
    private PathConfig pathConfig;
    private KursManagerInterface kursManager;
    private KursTypManagerInterface kursTypManager;
    private MailManagerInterface mailManager;
    private MailMottagarDao mottagarDao;
    private List<Instruktor> instruktorList;
    private List<Lokal> lokalList;
    private List<Kurs> kursList;
    private List<KursTyp> typList;    
    private List<KursNiva> nivaList;    
    private ArrayList<MailMottagare> mottagarLista = new ArrayList<MailMottagare>();
    private Set<Integer> raderaLista = new HashSet<Integer>();
    private boolean laggtill = true;
    private boolean obetalda = false;
    private MailConfig mail;
    private ArrayList<String> bilagor = new ArrayList<String>();
    private GrundConfig gc; 
    
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {   
    	Map<String, Object> modelMap = new HashMap<String, Object>();
    	ArrayList<MailMottagare> addLista = new ArrayList<MailMottagare>();
    	modelMap.put("instruktorer", instruktorList);
    	modelMap.put("lokaler", lokalList);
    	modelMap.put("kurser", kursList);
    	modelMap.put("typer", typList);
    	modelMap.put("nivaer", nivaList);
    	modelMap.put("bilagor", bilagor);
    	modelMap.put("mailConfig", mail);
    	    	
    	if (MultipartFormDataRequest.isMultipartFormData(request))
    	{    	    	
    		modelMap.put("antal", new Integer(mottagarLista.size()));
      		modelMap.put("mottagarLista", mottagarLista);
	    	try 
	    	{

	    		MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request);
	    		for (Enumeration<String> pars = mrequest.getParameterNames(); pars.hasMoreElements();)
	    		{		          		
	    			String par = pars.nextElement();
	    			logger.info("Paramater " + par + " värde " + mrequest.getParameter(par));	    			
	    		}
	        	// Hämta mailet ifrån inmatningen först och främst
	    		//logger.info("param laggill" + mrequest.getParameter("laggtill"));
	        	if (mrequest.getParameter("rubrik") != null)
	        		mail.setRubrik(mrequest.getParameter("rubrik"));
	        	if (mrequest.getParameter("body") != null)
	        		mail.setBody(mrequest.getParameter("body"));
	        	if (mrequest.getParameter("footer") != null)    		
	        		mail.setFooter(mrequest.getParameter("footer"));
	        	modelMap.put("mailConfig", mail);
	        	if (mrequest.getParameter("laggtill") != null)
	        		laggtill = mrequest.getParameter("laggtill").equals("laggtill");
	        	modelMap.put("laggtill", laggtill);
	        	obetalda = mrequest.getParameter("obetalda") != null;
	        	modelMap.put("obetalda", obetalda);
	    		if (mrequest.getParameter("_action") != null && mrequest.getParameter("_action").equals("remove"))
	    		{
	    				bilagor.remove(mrequest.getParameter("_item"));
	    				return new ModelAndView("WEB-INF/jsp/mailurval.jsp", "modelMap", modelMap);
	    		}	    						
	    			
	    		Hashtable files = mrequest.getFiles();
	            if ( (files != null) && (!files.isEmpty()) )
	            {
	                UploadFile file = (UploadFile) files.get("bilaga");	                
	                if (file != null && file.getFileName() != null) 
	                {	
	                	bilagor.add(file.getFileName());
	                	modelMap.put("bilagor", bilagor);
	                	UploadBean upBean = new UploadBean();
	                	upBean.setStoremodel(UploadBean.FOLDERSTORE);
	                	upBean.setFolderstore(pathConfig.getBilagor());
	                	upBean.setOverwrite(true);	
	                	upBean.store(mrequest, "bilaga");		                	
	                }
	            }
	            if (mrequest.getParameter("_action") != null && mrequest.getParameter("_action").equals("reload"))
	            	return new ModelAndView("WEB-INF/jsp/mailurval.jsp", "modelMap", modelMap);
	            if (mrequest.getParameter("kurs") != null && mrequest.getParameter("kurs").length() > 0)
	        	{
	        		ArrayList<MailMottagare> tmpList = null;
	        		int kid = Integer.parseInt(mrequest.getParameter("kurs"));
	        		if (kid == 0) 
	        			tmpList =  (ArrayList<MailMottagare>) this.mottagarDao.getMailMottagare();
	        		else
	        		{
	        			if (obetalda)
	        				tmpList =  (ArrayList<MailMottagare>) this.mottagarDao.getMailMottagareKursEjBetalt(kid);
	        			else
	        				tmpList =  (ArrayList<MailMottagare>) this.mottagarDao.getMailMottagareKurs(kid);
	        		}
	        		if (laggtill)
	        			removeDups(tmpList);
	        		else
	        			mottagarLista = tmpList;
	        		modelMap.put("mottagarLista", mottagarLista);
	        		modelMap.put("antal", new Integer(mottagarLista.size()));
	        	    return new ModelAndView("WEB-INF/jsp/mailurval.jsp", "modelMap", modelMap);            
	        	}
	        	if (mrequest.getParameter("niva") != null && mrequest.getParameter("niva").length() > 0)
	        	{
	        		if (laggtill)	
	        			removeDups(this.mottagarDao.getMailMottagareNiva(Integer.parseInt(mrequest.getParameter("kurstyp")),Integer.parseInt(mrequest.getParameter("niva"))));
	        		else 
	        			mottagarLista = (ArrayList<MailMottagare>)this.mottagarDao.getMailMottagareNiva(Integer.parseInt(mrequest.getParameter("kurstyp")), Integer.parseInt(mrequest.getParameter("niva")));
	        		modelMap.put("mottagarLista", mottagarLista);
	        		modelMap.put("antal", new Integer(mottagarLista.size()));
	                return new ModelAndView("WEB-INF/jsp/mailurval.jsp", "modelMap", modelMap);            
	        	}
	        	if (mrequest.getParameter("lokal") != null && mrequest.getParameter("lokal").length() > 0)
	        	{
	        		if (laggtill)
	        			removeDups(this.mottagarDao.getMailMottagareLokal(Integer.parseInt(mrequest.getParameter("lokal"))));
	        		else 
	        			mottagarLista = (ArrayList<MailMottagare>)this.mottagarDao.getMailMottagareLokal(Integer.parseInt(mrequest.getParameter("lokal")));
	        		modelMap.put("mottagarLista", mottagarLista);   
	        		modelMap.put("antal", new Integer(mottagarLista.size()));
	                return new ModelAndView("WEB-INF/jsp/mailurval.jsp", "modelMap", modelMap);            
	        	}
	          	if (mrequest.getParameter("instruktor") != null && mrequest.getParameter("instruktor").length() > 0)
	        	{      		
	          		if (laggtill)
	          			removeDups(this.mottagarDao.getMailMottagareInstruktor(Integer.parseInt(mrequest.getParameter("instruktor"))));
	    			else 
	        			mottagarLista = (ArrayList<MailMottagare>)this.mottagarDao.getMailMottagareInstruktor(Integer.parseInt(mrequest.getParameter("instruktor")));
	          		modelMap.put("antal", new Integer(mottagarLista.size()));
	          		modelMap.put("mottagarLista", mottagarLista);   
	        		return new ModelAndView("WEB-INF/jsp/mailurval.jsp", "modelMap", modelMap);            
	        	}
	          	for (Enumeration<String> pars = mrequest.getParameterNames(); pars.hasMoreElements();)
	    		{	
	          		
	    			String par = pars.nextElement();
	    			// logger.info("Paramater " + par + " värde " + mrequest.getParameter(par));
	    			if (par.startsWith("radera"))		
	    				raderaLista.add(Integer.parseInt(par.substring(6)));
	    			else if (par.startsWith("add"))
	    			{
	    				MailMottagare mm = new MailMottagare();
	    				mm.setPid(Integer.parseInt(par.substring(3)));
	    				mm.setEmail(mrequest.getParameter("E" + par.substring(3)));
	    				mm.setNamn(mrequest.getParameter("N" + par.substring(3)));
	    				addLista.add(mm);
	    			}
	    		}
	          	if (raderaLista.size() > 0)
	          	{
	          		
	          		ArrayList<MailMottagare> tmpList = new ArrayList<MailMottagare>();
	          		for (MailMottagare mm: mottagarLista)
	          		{	      			
	          			if (!raderaLista.contains(new Integer(mm.getPid())))
	          				tmpList.add(mm);
	          		}	
	          		mottagarLista.clear();
	          		mottagarLista = tmpList;
	          		modelMap.put("antal", new Integer(mottagarLista.size()));
	          		modelMap.put("mottagarLista", mottagarLista);
	          		raderaLista.clear();
	          	}
	          	if (addLista.size() > 0)
	          	{	
	          		if (laggtill)
	          			removeDups(addLista);
	          		else
	          			mottagarLista = addLista;
	          		modelMap.put("antal", new Integer(mottagarLista.size()));
	          		modelMap.put("mottagarLista", mottagarLista);
	          	}	
	          	if (mrequest.getParameter("personer") != null && !mrequest.getParameter("personer").equals(""))         		
	          	{
	          		if (mrequest.getParameter("personer").equals("yes"))
	          		{	
	          			ArrayList<MailMottagare> tmpList = (ArrayList<MailMottagare>) mottagarDao.getMailMottagare("a", true);
	          			modelMap.put("personer", tmpList);
	          			return new ModelAndView("WEB-INF/jsp/personeradd.jsp", "modelMap", modelMap);
	          		}
	          		else
	          		{
	          			if (laggtill)
		          			removeDups(this.mottagarDao.getMailMottagareEjBetalt());
		    			else 
		        			mottagarLista = (ArrayList<MailMottagare>)this.mottagarDao.getMailMottagareEjBetalt();
		          		modelMap.put("antal", new Integer(mottagarLista.size()));
		          		modelMap.put("mottagarLista", mottagarLista);   
		        		return new ModelAndView("WEB-INF/jsp/mailurval.jsp", "modelMap", modelMap);  
	          		}
	          	}
	          	if (mrequest.getParameter("enamn") != null && mrequest.getParameter("enamn").length() > 0)
	        	{
	          		// logger.info("lägge till personer pÂ " + mrequest.getParameter("enamn"));
	          		ArrayList<MailMottagare> tmpList = (ArrayList<MailMottagare>) mottagarDao.getMailMottagare(mrequest.getParameter("enamn"), true);
	          		modelMap.put("personer", tmpList);
	          		return new ModelAndView("WEB-INF/jsp/personeradd.jsp", "modelMap", modelMap);
	        	}
	          	if (mrequest.getParameter("fnamn") != null && mrequest.getParameter("fnamn").length() > 0)
	        	{      		
	         		ArrayList<MailMottagare> tmpList = (ArrayList<MailMottagare>) mottagarDao.getMailMottagare(mrequest.getParameter("fnamn"), false);
	          		modelMap.put("personer", tmpList);
	          		return new ModelAndView("WEB-INF/jsp/personeradd.jsp", "modelMap", modelMap);   
	        	}
	          	// Det måsta var sök i lägg till personer
	          	if (mrequest.getParameter("fnamn") != null && mrequest.getParameter("enamn") != null)
	          	{
	         		ArrayList<MailMottagare> tmpList = (ArrayList<MailMottagare>) mottagarDao.getMailMottagare(mrequest.getParameter("fnamn"), false);
	          		modelMap.put("personer", tmpList);
	          		return new ModelAndView("WEB-INF/jsp/personeradd.jsp", "modelMap", modelMap);   

	          	}
	          	if (mrequest.getParameter("_action") != null && mrequest.getParameter("_action").equals("submit"))
	          	{
	          		int i = 0;
	          		String[] toLista = new String[1];
	          		for (MailMottagare mm: mottagarLista)
	          		{	
	          			if (gc.isFakeMail())
	          				toLista[0] = gc.getAnmalanMail();
	          			else
	          				toLista[0] = mm.getEmail();      			
	          			
	          			if (bilagor.size() == 0)
		          			mailManager.sendMail(mail, toLista, null);
		          		else
		          			mailManager.sendMail(mail, toLista, (String[]) bilagor.toArray(new String[bilagor.size()]));
	          		}
	          		
	          		
	          		return new ModelAndView("WEB-INF/jsp/main.jsp", "modelMap", modelMap);
	          	}
	    		    		
			} 
	    	catch (UploadException e) 
	    	{		
				e.printStackTrace();
			} 
    	}	            	    	    	
    	if (request.getParameter("start") != null)
    	{    		
    		mottagarLista.clear();   
    		bilagor.clear();
    		gc = configManager.getGrundConfig();
    		modelMap.put("mottagarLista", mottagarLista);
    		modelMap.put("antal", new Integer(mottagarLista.size()));
    		modelMap.put("mailConfig", mail);
    		modelMap.put("laggtill", new Boolean(true));
    		modelMap.put("obetalda", new Boolean(false));
    		kursList = kursManager.getKursMiniList();
    		typList = kursTypManager.getKursTypList();
            nivaList = kursTypManager.getKursNivaList();
            instruktorList = this.instruktorManager.getInstruktors();
            lokalList = this.lokalManager.getLokaler();
    		return new ModelAndView("WEB-INF/jsp/mailurval.jsp", "modelMap", modelMap);
    	}
    	
      	return new ModelAndView("WEB-INF/jsp/mailurval.jsp", "modelMap", modelMap);    	               
    }
         
    public void setKursManager(KursManagerInterface kursManager) 
    {
        this.kursManager = kursManager;
        kursList = kursManager.getKursMiniList();
    }
    public void setKursTypManager(KursTypManagerInterface kursTypManager) 
    {
        this.kursTypManager = kursTypManager;
        typList = kursTypManager.getKursTypList();
        nivaList = kursTypManager.getKursNivaList();
    }
    public void setInstruktorManager(InstruktorManagerInterface instruktorManager) 
    {
        this.instruktorManager = instruktorManager;
        instruktorList = this.instruktorManager.getInstruktors();
    }
    public void setLokalManager(LokalManagerInterface lokalManager) 
    {
        this.lokalManager = lokalManager;
        lokalList = this.lokalManager.getLokaler();
    }
    public void setConfigManager(ConfigManagerInterface configManager) 
    {
        this.configManager = configManager;
        gc = configManager.getGrundConfig();
        MailSetup ms = this.configManager.getMailSetup();
        mail = new MailConfig();
        mail.setBody("");
        mail.setRubrik("");
        mail.setFooter(ms.getFooter());
        this.pathConfig = this.configManager.getPathConfig();
    }
    
    public void setMailManager(MailManagerInterface mailManager) 
    {
        this.mailManager = mailManager;
    }
    public void setMailMottagarDao(MailMottagarDao mailMottagarDao) 
    {
        this.mottagarDao = mailMottagarDao;
    }
    
    private void removeDups(List<MailMottagare> tmpList)
    {
    	for (MailMottagare mm : mottagarLista)
    		raderaLista.add(new Integer(mm.getPid()));
		for (MailMottagare mm: tmpList)
			if (!raderaLista.contains(new Integer(mm.getPid())))
				mottagarLista.add(mm);
		raderaLista.clear();	
    }
}

