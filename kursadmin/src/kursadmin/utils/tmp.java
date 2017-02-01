/*package kursadmin.web;

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

import kursadmin.domain.KursAnmalan;
import kursadmin.domain.Person;
import kursadmin.repository.LokalDao;
import kursadmin.service.KursAnmalanManagerInterface;
import kursadmin.service.LokalManagerInterface;

public class tmp implements Controller 
{

    protected final Log logger = LogFactory.getLog(getClass());

    private KursAnmalanManagerInterface kursAnmalanManager;
    boolean manuell;
    int lastRequest = 1; // 1 = första call, 2 = add, 3 = vald, 4 = radera
    List<KursAnmalan> kaList = null;
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
    	//logger.info("lastRequest " + lastRequest);
    	Map<String, Object> modelMap = new HashMap<String, Object>();   
    	for (Enumeration<String> pars = request.getParameterNames(); pars.hasMoreElements();)
		{	    		
			String par = pars.nextElement();
			//logger.info("Parameter: " + par + " Value: " + request.getParameter(par) + " Last req: " + lastRequest + " Method: " + request.getMethod());
					
		}   	
    	if (request.getParameter("add") != null && lastRequest != 2)
    	{ 
    		lastRequest = 2;
    		
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
    			
    			modelMap.put("personer", personList);
    			modelMap.put("kursanmalan", ka);
    			modelMap.put("antal", new Integer(personList.size()));
    			return new ModelAndView("WEB-INF/jsp/anmalningsfraga.jsp", "modelMap", modelMap);
    		}
    		else
    		{    			
        		ka.setPid(0);
        		try
        		{   			    			    				
        			
        			modelMap.put("error", ka.getFnamn() + " " + ka.getEnamn() + " har lagts till på " + kursAnmalanManager.creKursDeltagare(kaList));
        			
        		}
        		catch(Exception e)
        		{
        			modelMap.put("error", e.getMessage());
        		}    	
        		modelMap.put("anmalningar", kursAnmalanManager.getKursAnmalanList()); 
        		if (manuell)
        			return new ModelAndView(new RedirectView("elever.htm?kid=" + ka.getKid() + "&bet=" + ka.getBeteckning()));			
        		else
        			return new ModelAndView("WEB-INF/jsp/anmalningar.jsp", "modelMap", modelMap);	
    		}    		
    	}
    	if (request.getParameter("vald") != null && lastRequest != 3)
    	{
    		lastRequest = 3;
    		int pid = Integer.parseInt(request.getParameter("vald"));
    		KursAnmalan ka = kaList.get(0);
    		ka.setHandled(manuell);
    		ka.setPid(pid);
    		try
    		{   			    			    				   			 
    			modelMap.put("error", ka.getFnamn() + " " + ka.getEnamn() + " har lagts till på " + kursAnmalanManager.creKursDeltagare(kaList));
    			
    		}
    		catch(Exception e)
    		{    		
    			modelMap.put("error", e.getMessage());
    		}    			
    		modelMap.put("anmalningar", kursAnmalanManager.getKursAnmalanList());
    		if (manuell)
    			return new ModelAndView(new RedirectView("elever.htm?kid=" + ka.getKid() + "&bet=" + ka.getBeteckning()));			
    		else
    			return new ModelAndView("WEB-INF/jsp/anmalningar.jsp", "modelMap", modelMap);			
    	}	
    	if (request.getParameter("radera") != null)
    	{
    		lastRequest = 4;
    		manuell = false;
    		this.kursAnmalanManager.deleteKursAnmalan(Integer.parseInt(request.getParameter("radera")));
    		modelMap.put("anmalningar", kursAnmalanManager.getKursAnmalanList());
            return new ModelAndView("WEB-INF/jsp/anmalningar.jsp", "modelMap", modelMap);
    	}    	           
    	lastRequest = 1;
        modelMap.put("anmalningar", this.kursAnmalanManager.getKursAnmalanList());
        return new ModelAndView("WEB-INF/jsp/anmalningar.jsp", "modelMap", modelMap);
        
    }
    public void setkursAnmalanManager(KursAnmalanManagerInterface kursAnmalanManager) 
    {
        this.kursAnmalanManager = kursAnmalanManager;
    }
    
}

*/