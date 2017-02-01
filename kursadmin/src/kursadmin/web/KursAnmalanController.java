package kursadmin.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;
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

import kursadmin.domain.Elev;
import kursadmin.domain.Faktura;
import kursadmin.domain.KursAll;
import kursadmin.domain.KursAnmalan;
import kursadmin.domain.Person;
import kursadmin.repository.LokalDao;
import kursadmin.service.ElevManagerInterface;
import kursadmin.service.FakturaManagerInterface;
import kursadmin.service.KursAnmalanManagerInterface;
import kursadmin.service.KursManagerInterface;
import kursadmin.service.LokalManagerInterface;
import kursadmin.service.PersonManagerInterface;

public class KursAnmalanController implements Controller 
{

    protected final Log logger = LogFactory.getLog(getClass());

    private KursAnmalanManagerInterface kursAnmalanManager;
    private FakturaManagerInterface fakturaManager;
    private KursManagerInterface kursManager;
    private PersonManagerInterface personManager;
    private ElevManagerInterface elevManager;
    boolean manuell = false;
    boolean pidPending = false;   
    List<KursAnmalan> kaList = null;
    List<Elev> elever = null;
    
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
    	//logger.info("lastRequest " + lastRequest);
    	Map<String, Object> modelMap = new HashMap<String, Object>();   
    	/*
    	for (Enumeration<String> pars = request.getParameterNames(); pars.hasMoreElements();)
		{	    		
			String par = pars.nextElement();
			//logger.info("Parameter: " + par + " Value: " + request.getParameter(par) + " Last req: " + lastRequest + " Method: " + request.getMethod());
					
		}
		*/   	
    	if (request.getParameter("add") != null)
    	{ 
    		manuell = request.getParameter("manuell") != null;
    		String[] tmp = request.getParameter("add").split(",");   		
    		kaList = new ArrayList<KursAnmalan>();    		
    		for (int i = 0; i < tmp.length; i++)
    		{    	    		
    		    kursAnmalanManager.getKursAnmalan(Integer.parseInt(tmp[i])).setHandled(manuell);     		
    		    kaList.add(kursAnmalanManager.getKursAnmalan(Integer.parseInt(tmp[i])));    		
    		}       		
    		KursAnmalan ka = kaList.get(0);    		
    		ArrayList<Person> personList = (ArrayList<Person>) kursAnmalanManager.getPersoner(ka.getFnamn(), ka.getEnamn());    		
    		if (personList.size() > 0)
    		{
    			pidPending = true;
    			modelMap.put("personer", personList);
    			modelMap.put("kursanmalan", ka);
    			modelMap.put("antal", new Integer(personList.size()));
    			return new ModelAndView("WEB-INF/jsp/anmalningsfraga.jsp", "modelMap", modelMap);
    		}    		
    		else 
    		{
    			pidPending = false;
        		ka.setPid(0);
    		}	
    	}    	
    	else if (request.getParameter("vald") != null)
    	{
    		kaList.get(0).setPid(Integer.parseInt(request.getParameter("vald")));
    		pidPending = false;
    	}	
    	if (request.getParameter("fakturaretur") != null)
		{				
    		modelMap.put("error", fakturera(Integer.parseInt(request.getParameter("pid")), request.getParameter("kurser")));
		}
    	else if (kaList != null && !pidPending)
    	{	  		
    		try    		
    		{  
    			String kidArr = new String();
    			elever = kursAnmalanManager.creKursDeltagare(kaList);
    			
    			kaList = null;
    			KursAll kursAll = kursManager.getKursAll(elever.get(0).getKid());
    			// Fšr tillfŠllet hanteras alla anmŠlningar till resor och  fester manuellt
    			if (manuell || kursAll.harPrisTillagg())
        			return new ModelAndView(new RedirectView("elever.htm?kid=" + elever.get(0).getKid() + "&bet=" + elever.get(0).getBeteckning()));
    			for (Elev elev: elever)
    			{
    				kidArr += elev.getKid() + ",";
    			}   
				kidArr = kidArr.substring(0, kidArr.length() - 1);
    			// Kolla om det finns fler kurser som kan faktureras samtidigt
    			if (personManager.personHarOfakturerat(elever.get(0).getPid(), elever.get(0).getKid()) > elever.size())
    			{
    				return new ModelAndView(new RedirectView("fakturera.htm?kidArr=" + kidArr + "&pid=" + elever.get(0).getPid()));
    			}    			    			 
    			modelMap.put("error", fakturera(elever.get(0).getPid(), kidArr));  
    		}
    		catch(Exception e)
    		{    		
    			kaList = null;
    			modelMap.put("error", e.getMessage());
    		}    			
    		// modelMap.put("anmalningar", kursAnmalanManager.getKursAnmalanList());
    		// return new ModelAndView("WEB-INF/jsp/anmalningar.jsp", "modelMap", modelMap);    		
    	}	
    	
    	if (request.getParameter("radera") != null)
    	{
    		manuell = false;
    		this.kursAnmalanManager.deleteKursAnmalan(Integer.parseInt(request.getParameter("radera")));
    		modelMap.put("anmalningar", kursAnmalanManager.getKursAnmalanList());
            return new ModelAndView("WEB-INF/jsp/anmalningar.jsp", "modelMap", modelMap);
    	}    	             	
        modelMap.put("anmalningar", this.kursAnmalanManager.getKursAnmalanList());
        return new ModelAndView("WEB-INF/jsp/anmalningar.jsp", "modelMap", modelMap);
        
    }
    private String fakturera(int pid, String kidArr)
    {
    	Faktura faktura = null;
    	if (!kidArr.equals(""))	// inget skall faktureras
    	{	
	    	String kids[] = kidArr.split(",");
	    	ArrayList<Elev> fakturaElever = new ArrayList<Elev>();
	    	for (int i = 0; i < kids.length; i++)
	    	{	
	    		Elev elev = elevManager.getElev(Integer.parseInt(kids[i]), pid);
	    		elev.setAbekr(true);
	    		fakturaElever.add(elev);
	    	}
	    	faktura = fakturaManager.fakturera(0, fakturaElever);
    	}	
    	String msg = elever.get(0).getNamn() + " har lagts till pŒ "; 
		for (Elev elev: elever)
		{
			msg = msg + elev.getBeteckning() + ",\\n";
		}
		msg = msg.substring(0, msg.lastIndexOf(","));
		if (kidArr.equals(""))
			msg += "\\nOBS! Ingen kurs har fakturerats.";
		else
			kursAnmalanManager.sendAnmBekr(faktura);
    	elever.clear();
    	return msg;
    }
    
    public void setElevManager(ElevManagerInterface elevManager) 
    {
        this.elevManager = elevManager;
    }
    public void setkursAnmalanManager(KursAnmalanManagerInterface kursAnmalanManager) 
    {
        this.kursAnmalanManager = kursAnmalanManager;
    }
    public void setfakturaManager(FakturaManagerInterface fakturaManager) 
    {
        this.fakturaManager = fakturaManager;
    }
    public void setKursManager(KursManagerInterface kursManager) 
    {
        this.kursManager = kursManager;
    }
    public void setPersonManager(PersonManagerInterface personManager) 
    {
        this.personManager = personManager;
    }
}

