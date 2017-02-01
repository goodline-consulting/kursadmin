package kursadmin.web;
import java.util.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kursadmin.service.KursManager;
import kursadmin.domain.KursAll;
import kursadmin.domain.KursTyp;
import kursadmin.service.LokalManager;
import kursadmin.service.InstruktorManager;
import kursadmin.service.KursTypManagerInterface;

public class KursFormController extends SimpleFormController 
{
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private KursManager kursManager;
    private LokalManager lokalManager;
    private InstruktorManager instruktorManager;
    private KursTypManagerInterface kursTypManager;
    
    public ModelAndView onSubmit(Object command)
            throws ServletException 
    {
        
        //logger.info("Updating " + ((KursAll) command).getBeteckning());
        if (((KursAll) command).getKid() == 0)
        {
        	
        	kursManager.insertKursAll((KursAll) command);
        }	
        else
        	kursManager.updateKursAll((KursAll) command);        

        return new ModelAndView(new RedirectView(getSuccessView()));
    }
    protected boolean isFormChangeRequest(HttpServletRequest request, Object command)
    {
    	// logger.info("Antal " + request.getParameter("antal"));
    	KursAll kursAll = (KursAll) command;
    	if (request.getParameter("_action").equals("del-item"))
    	{	
    		//logger.info("deleting " + request.getParameter("_item"));
    		kursAll.delKurstillf(Integer.parseInt(request.getParameter("_item")) -1);
    		return true;
    	}
    	if (request.getParameter("_action").equals("del-all"))
    	{	    		
    		ArrayList<Date> kurstillf = new ArrayList<Date>();
        	Date dd = new Date(System.currentTimeMillis());
        	kurstillf.add(dd);
        	kursAll.setKurstillf(kurstillf);    		
    		return true;
    	}
    	if (request.getParameter("_action").equals("create"))
    	{	
    		int ant = Integer.parseInt(request.getParameter("antal"));
    		ArrayList<Date> kurstillf = (ArrayList<Date>) kursAll.getKurstillf();
    		Date startDate = kurstillf.get(kurstillf.size() - 1);
    		Calendar gregCal = Calendar.getInstance();
    		gregCal.setTime(startDate);
    		for (int i = 0; i < ant; i++)
    		{
    			gregCal.add(Calendar.DAY_OF_MONTH, 7);
    			Date dd = new Date(gregCal.getTimeInMillis());
    			kurstillf.add(dd);
    		}    		
    		kursAll.setKurstillf(kurstillf);    		
    		return true;
    	}
    	return false;
    }
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) 
    {
    	binder.registerCustomEditor(Date.class, null,
    			new CustomDateEditor(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("sv", "SE")), false));
    }
    
    protected boolean suppressValidation(HttpServletRequest request, Object command)
    {    	
    	if (request.getParameter("_action").equals("submit"))
    		return false;
    	return true;
    }
    
    protected Map referenceData(HttpServletRequest request) throws Exception
    {    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("lokaler", lokalManager.getLokaler());
    	map.put("instruktorer", instruktorManager.getInstruktors());
    	map.put("typer", kursTypManager.getKursTypList());
    	map.put("nivaer", kursTypManager.getKursNivaList());
    	return map;
    }
    
    protected Object formBackingObject(HttpServletRequest request) throws ServletException 
    {    	       
    	int kid = Integer.parseInt(request.getParameter("recid"));
    	int i   = 0;
    	int tid = 0;
    	KursAll kursAll;
    	if (kid == 0)
    	{	
    		kursAll =  new KursAll();
    		for (KursTyp kt: kursTypManager.getKursTypList())
    		{
    			if (i == 0)
    				tid = kt.getTid();
    			if (kt.getInfo().indexOf("default") >= 0)
    			kursAll.setTid(kt.getTid());
    		}	
    	}   
    	else
    		kursAll = kursManager.getKursAll(kid);        
        if (kursAll.getKurstillf() == null)
        {
        	ArrayList<Date> kurstillf = new ArrayList<Date>();
        	Date dd = new java.sql.Date(System.currentTimeMillis());
        	kurstillf.add(dd);
        	kursAll.setKurstillf(kurstillf);
        }
        
        return kursAll;
    }
    public void setLokalManager(LokalManager lokalManager) 
    {
        this.lokalManager = lokalManager;
    }
    public LokalManager getLokalManager() 
    {
        return this.lokalManager;
    }
    public void setInstruktorManager(InstruktorManager instruktorManager) 
    {
        this.instruktorManager = instruktorManager;
    }
    public InstruktorManager getInstruktorManager() 
    {
        return this.instruktorManager;
    }
    public void setKursTypManager(KursTypManagerInterface kursTypManager) 
    {
        this.kursTypManager = kursTypManager;
    }

    public KursTypManagerInterface getKursTypManager() 
    {
        return kursTypManager;
    }
    public void setKursManager(KursManager kursManager) 
    {
        this.kursManager = kursManager;
    }

    public KursManager getKursManager() 
    {
        return kursManager;
    }

}
