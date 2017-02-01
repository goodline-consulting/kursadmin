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

import java.text.ParseException;
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

public class BgcListController implements Controller 
{

    protected final Log logger = LogFactory.getLog(getClass());
    private BgcManagerInterface bgcManager;
    private List<Betalning> betalningar = null;
    private String criteria = "";
    private Date fromDat, toDat;   
    private String namn = null;
    
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {   
    	 
    	List<Date> dagar = bgcManager.getDates();
    	//Date da[] = new Date[dagar.size()];
		//fromDat = dagar.toArray(da)[0];
    	
    	Map<String, Object> modelMap = new HashMap<String, Object>();    
    	
    	if (request.getParameter("_action") != null && request.getParameter("_action").equals("oplacerade"))
    	{    		
    		criteria = "oplacerade";
    		
    	}
    	else if (request.getParameter("namn") != null && !request.getParameter("namn").equals(""))
    	{
    		criteria = "namn";
    		namn = request.getParameter("namn");
    		
    	}    	
    	else if (request.getParameter("_action") != null && request.getParameter("_action").equals("leta"))
    	{	  	
    		fromDat = dagar.get(0);
    		criteria = "interval";
    		toDat = null;
    		if (!request.getParameter("fromdat").equals(""))
				try 
    			{
					fromDat = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fromdat"));
				} 
    			catch (ParseException e) 
    			{
					
				}
    		if (!request.getParameter("tomdat").equals(""))
    			try 
        		{
    				toDat = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("tomdat"));
    			} 
        		catch (ParseException e) 
        		{
    				
    			}
        	else
        		toDat = fromDat;
    		betalningar = bgcManager.searchBetalningar(fromDat, toDat);
    	}
    	else if (request.getParameter("radera") != null)
    	{
    		bgcManager.deleteBetalning(Integer.parseInt(request.getParameter("radera")));
    	}    	
    	else if (criteria.equals(""))
    	{
    		fromDat = dagar.get(0);
    		toDat = fromDat;
    		
    	}
    	if (criteria.equals("interval"))
    		betalningar = bgcManager.searchBetalningar(fromDat, toDat);
    	else if (criteria.equals("oplacerade"))
    		betalningar = bgcManager.searchOplaceradeBetalningar();
    	else if (criteria.equals("namn"))
    		betalningar = bgcManager.searchPersonBetalningar(namn);
    	modelMap.put("betalningar", betalningar);
    	modelMap.put("dagar", dagar);
        return new ModelAndView("WEB-INF/jsp/bgclist.jsp", "modelMap", modelMap);
    }
   
    public void setBgcManager(BgcManagerInterface bgcManager) 
    {
        this.bgcManager = bgcManager;    
    }
  
}

