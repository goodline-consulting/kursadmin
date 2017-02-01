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
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.HashMap;

import kursadmin.domain.Instruktor;
import kursadmin.domain.PathConfig;
import kursadmin.repository.InstruktorDao;
import kursadmin.service.ConfigManagerInterface;
import kursadmin.service.InstruktorManagerInterface;

public class InstruktorFormController implements Controller 
{

    protected final Log logger = LogFactory.getLog(getClass());
    private InstruktorManagerInterface instruktorManager;
    private ConfigManagerInterface configManager;
    private PathConfig pathConfig;
    Instruktor instruktor = null;
    
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {   
    	Map<String, Object> modelMap = new HashMap<String, Object>();
    	modelMap.put("images", "bilder");
    	if (request.getParameter("recid") != null)
    	{
    		int recid = Integer.parseInt(request.getParameter("recid")); 
    		if (recid == 0)
    			instruktor = new Instruktor();
    		else
    			instruktor = this.instruktorManager.getInstruktor(recid);
    		modelMap.put("instruktor", instruktor);
    		
    		return new ModelAndView("WEB-INF/jsp/instruktoredit.jsp", "modelMap", modelMap);
    	}  
    	if (MultipartFormDataRequest.isMultipartFormData(request))
    	{    	    	    		
	    	try 
	    	{
	    		MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request);
	    		Hashtable files = mrequest.getFiles();
	            if ( (files != null) && (!files.isEmpty()) )
	            {
	                UploadFile file = (UploadFile) files.get("bildfil");	                
	                if (file != null && file.getFileName() != null) 
	                {	
	                	instruktor.setBild(file.getFileName());
	                	UploadBean upBean = new UploadBean();
	                	upBean.setStoremodel(UploadBean.FOLDERSTORE);
	                	upBean.setFolderstore(pathConfig.getBilder());
	                	logger.info("Bildbiblo: " + pathConfig.getBilder());
	                	upBean.setOverwrite(false);	
	                	upBean.store(mrequest, "bildfil");		                	
	                }
	            }
	            instruktor.setAdress(mrequest.getParameter("adress"));
	            instruktor.setEmail(mrequest.getParameter("email"));
	            instruktor.setInfo(mrequest.getParameter("info"));
	            instruktor.setMobil(mrequest.getParameter("mobil"));
	            instruktor.setPostadress(mrequest.getParameter("postadress"));
	            instruktor.setNamn(mrequest.getParameter("namn"));
	            instruktor.setPostnr(mrequest.getParameter("postnr"));
	            instruktor.setTelefon(mrequest.getParameter("telefon"));
	            instruktor.setBildx(Integer.parseInt(mrequest.getParameter("bredd")));
	            instruktor.setBildy(Integer.parseInt(mrequest.getParameter("hojd")));
	            modelMap.put("instruktor", instruktor);
	            modelMap.put("images", "bilder");
	            
	            if (mrequest.getParameter("_action").equals("reload"))
	            	return new ModelAndView("WEB-INF/jsp/instruktoredit.jsp", "modelMap", modelMap);
	            if (instruktor.getIid() == 0)
	            	instruktorManager.insertInstruktor(instruktor);
	            else
	            	instruktorManager.updateInstruktor(instruktor);
	    		for (Enumeration<String> pars = mrequest.getParameterNames(); pars.hasMoreElements();)
	    		{	    		
	    			String par = pars.nextElement();
	    			logger.info("parameter: " + par + " värde " + mrequest.getParameter(par));	    						
	    		}	
	    		
			} 
	    	catch (UploadException e) 
	    	{		
				logger.info("Stacktrace: " + e.getMessage());
			}
    	}	        
    	return new ModelAndView("instruktorer.htm");
    }
    public void setInstruktorManager(InstruktorManagerInterface instruktorManager) 
    {
        this.instruktorManager = instruktorManager;
    }
    public void setConfigManager(ConfigManagerInterface configManager) 
    {
        this.configManager = configManager;    
        this.pathConfig = this.configManager.getPathConfig();
        
    }


}

