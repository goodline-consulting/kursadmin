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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import kursadmin.domain.Betalning;
import kursadmin.service.BgcManagerInterface;
import kursadmin.service.KursTypManager;

public class BgcController implements Controller 
{

    protected final Log logger = LogFactory.getLog(getClass());
    private BgcManagerInterface bgcManager;
    private List<Betalning> betalningar = null;
    
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {   
    	Map<String, Object> modelMap = new HashMap<String, Object>();    	 
    	if (MultipartFormDataRequest.isMultipartFormData(request))
    	{    	    	    		
	    	try 
	    	{
	    		MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request);
	    		if (mrequest.getParameter("_action").equals("reload"))
	    		{	 
	    			betalningar = null;
		    		Hashtable files = mrequest.getFiles();
		            if ( (files != null) && (!files.isEmpty()) )
		            {
		                UploadFile file = (UploadFile) files.get("bgcfil");	                
		                if (file != null && file.getFileName() != null) 
		                {	
		                	 try
		                	 {
		                		BufferedReader br = new BufferedReader(new InputStreamReader(file.getInpuStream()));		                		
		                		betalningar = bgcManager.getBetalningar(br);
		                	 }
		                	 catch (Exception e)
		                	 {
		                		 modelMap.put("error", e.getMessage());
		                	 }		                	
		                }
		                else 
		                	modelMap.put("error", "En fil som skall läsas in måste anges!");
		            }
	    		}   
	    		else if (mrequest.getParameter("_action").equals("exekvera"))
	    		{	 
	    			//logger.info("Nu kör vi prickar av");
	    			bgcManager.prickaAv(betalningar);   
	    			bgcManager.sparaBetalningar(betalningar);
	    			modelMap.put("close", true);
	    		}
	    		if (betalningar != null)
	    		{	
	    			
	    			/*
	    			for(Betalning bet: betalningar)
	    			{
	    				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    				logger.info(sdf.format(bet.getBetaldatum()) + " " + bet.getAmount() + " " + bet.getName() + " [" + bet.getRefnr() + "] (" + bet.getInfo() + ")" + " " + bet.getBetalkanal());				
	    			}
	    			*/
	    			modelMap.put("betalningar", betalningar);
	    			modelMap.put("antal", betalningar.size());
	    		}	
			} 
	    	catch (UploadException e) 
	    	{		
				e.printStackTrace();
			}
    	}	        
    	return new ModelAndView("WEB-INF/jsp/bgcupload.jsp", "modelMap", modelMap);
    }
   
    public void setBgcManager(BgcManagerInterface bgcManager) 
    {
        this.bgcManager = bgcManager;    
    }
 
}

