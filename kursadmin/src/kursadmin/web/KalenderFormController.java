package kursadmin.web;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import kursadmin.service.ConfigManager;
import kursadmin.service.KalenderManager;
import kursadmin.domain.Config;
import kursadmin.domain.Kalender;

public class KalenderFormController extends SimpleFormController 
{
    /** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private KalenderManager kalenderManager;
    private ConfigManager configManager;
    
    public ModelAndView onSubmit(Object command)
            throws ServletException 
    {
    	Kalender kalender = (Kalender) command; 
    	GregorianCalendar cal =  new GregorianCalendar(kalender.getAr(), 
  			   kalender.getMan() - 1,
  			   kalender.getDag(),
  			   kalender.getTim(),
  			   kalender.getMin());
    	kalender.setTidpunkt(cal.getTime());
    	if (kalender.getRubrik().charAt(0) == '-')
    	{	    		    		    	
        	kalender.setRubrik(kalender.getAltrubrik());
    	}	    	
        if ((kalender).getCid() == 0)
        	kalenderManager.insertKalender(kalender);
        else
        	kalenderManager.updateKalender(kalender);        
        return new ModelAndView(new RedirectView(getSuccessView()));
    }

    protected Object formBackingObject(HttpServletRequest request) throws ServletException 
    {
    	int cid = Integer.parseInt(request.getParameter("recid"));
    	if (cid == 0)
    	{	
    		Kalender kal =  new Kalender();
    		kal.setTidpunkt(new Date());
    		kal.setTim(17);
    		return kal;
    	}	
    	else
    	{
    		boolean found = false;
    		Kalender kal = kalenderManager.getKalender(cid);
    		GregorianCalendar cal =  new GregorianCalendar();
    		cal.setTime(kal.getTidpunkt());
    		kal.setAr(cal.get(Calendar.YEAR));
    		kal.setMan(cal.get(Calendar.MONTH) + 1);
    		kal.setDag(cal.get(Calendar.DAY_OF_MONTH));
    		kal.setTim(cal.get(Calendar.HOUR_OF_DAY));
    		kal.setMin(cal.get(Calendar.MINUTE));
    		for (Config cfg :configManager.getConfigList("kalakt"))
    			if (kal.getRubrik().equals(cfg.getVarde()))
    			{
    				found = true;
    				break;
    			}
    		if (!found)    		
    			kal.setAltrubrik(kal.getRubrik());
        	return kal;
    	}	
        
    }

    protected Map referenceData(HttpServletRequest request) throws Exception
    {
    	ArrayList<Integer> arlista = new ArrayList<Integer>();
    	ArrayList<Integer> manlista = new ArrayList<Integer>();
    	ArrayList<Integer> daglista = new ArrayList<Integer>();
		
    	int year = new GregorianCalendar().get(Calendar.YEAR);		
		for (int i = 0; i < 5; i++)
			arlista.add(new Integer(year++));
		int man = new GregorianCalendar().get(Calendar.MONTH) + 1;
		for (int i = man; i <= 12; i++)
			manlista.add(new Integer(man + (i - man)));
		for (int i = 1; i < man; i++)
			manlista.add(new Integer(i));
		int dag = new GregorianCalendar().get(Calendar.DAY_OF_MONTH);
		for (int i = dag; i <= 31; i++)
			daglista.add(new Integer(dag + (i - dag)));
		for (int i = 1; i < dag; i++)
			daglista.add(new Integer(i));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("arlista", arlista);    
		map.put("manlista", manlista);   
		map.put("daglista", daglista);  
		map.put("statlista", configManager.getConfigList("kalvikt"));
		map.put("aktlista", configManager.getConfigList("kalakt"));
		return map;
    }
    public void setKalenderManager(KalenderManager kalenderManager) 
    {
        this.kalenderManager = kalenderManager;
    }

    public KalenderManager KalenderManager() 
    {
        return kalenderManager;
    }
    public void setconfigManager(ConfigManager configManager) 
    {
        this.configManager = configManager;
    }

    public ConfigManager getConfigManager() 
    {
        return configManager;
    }
}
