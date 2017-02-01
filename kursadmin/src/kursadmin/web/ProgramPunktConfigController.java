package kursadmin.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javazoom.upload.MultipartFormDataRequest;
import javazoom.upload.UploadException;
import javazoom.upload.UploadFile;
import javazoom.upload.UploadBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Date;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.HashMap;

import kursadmin.domain.ProgramPunkt;
import kursadmin.repository.ProgramDao;
import kursadmin.service.KursTypManager;

public class ProgramPunktConfigController implements Controller 
{

    protected final Log logger = LogFactory.getLog(getClass());
    private ProgramDao programDao;
    private KursTypManager kursTypManager;
    private ArrayList<ProgramPunkt> punktList;
    
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {   
    	Map<String, Object> modelMap = new HashMap<String, Object>();
    	modelMap.put("typer", kursTypManager.getKursTypList());
    	int antal = 0;  
    	if (MultipartFormDataRequest.isMultipartFormData(request))
    	{    	    	    		
	    	try 
	    	{
	    		MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request);
	    		if (mrequest.getParameter("_action").equals("reload"))
	    		{	 
	    			if (mrequest.getParameter("kurstyp") == null)
	    			{
	    				modelMap.put("error", "Du måste välja en kurstyp");
	    				return new ModelAndView("WEB-INF/jsp/programpunktconfig.jsp", "modelMap", modelMap);
	    			}	
	    		    int tid = Integer.parseInt(mrequest.getParameter("kurstyp"));
		    		Hashtable files = mrequest.getFiles();
		            if ( (files != null) && (!files.isEmpty()) )
		            {
		                UploadFile file = (UploadFile) files.get("punktfil");	                
		                if (file != null && file.getFileName() != null) 
		                {	
		                	 try
		                	 {
		                		BufferedReader br = new BufferedReader(new InputStreamReader(file.getInpuStream()));
		                		String strLine;	   
		                		punktList = new ArrayList<ProgramPunkt>();
		                		while ((strLine = br.readLine()) != null)   
		                		{	                		
		                			antal++;  
		                			String[] punktStr = strLine.split(";");
		                		    if (punktStr.length != 4)
		                		    {
		                		    	modelMap.put("error", "Felaktig programfil, se rad " + antal);
		        	    				return new ModelAndView("WEB-INF/jsp/programpunktconfig.jsp", "modelMap", modelMap);
		                		    }
		                		    ProgramPunkt pkt = new ProgramPunkt();
		                		    pkt.setTid(tid);
		                		    pkt.setNamn(punktStr[0]);
		                		    pkt.setHeader(punktStr[1]);
		                		    pkt.setUrl(punktStr[2]);
		                		    pkt.setInfo("");
		                		    pkt.setAktuell(true);
		                		    pkt.setUrl2(punktStr[3]);
		                		    punktList.add(pkt);
		                		}	                		 
		                		br.close();
		                		programDao.loadProgramPunkter(punktList);
		                	 }
		                	 catch (Exception e)
		                	 {
		                		logger.info("Error: " + e.getMessage());
		                	 }		                	
		                }
		                modelMap.put("error", antal + " Programpunkter har laddats");
		            }
	    		}    
		            
		        /*
	    		for (Enumeration<String> pars = mrequest.getParameterNames(); pars.hasMoreElements();)
	    		{	    		
	    			String par = pars.nextElement();
	    			logger.info("parameter: " + par + " v‰rde " + mrequest.getParameter(par));	    						
	    		}	
	    		*/    		
			} 
	    	catch (UploadException e) 
	    	{		
				e.printStackTrace();
			}
    	}	        
    	return new ModelAndView("WEB-INF/jsp/programpunktconfig.jsp", "modelMap", modelMap);
    }
   
    public void setProgramDao(ProgramDao programDao) 
    {
        this.programDao = programDao;    
    }
    public void setKursTypManager(KursTypManager kursTypManager)
    {
    	this.kursTypManager = kursTypManager;
    }

}

