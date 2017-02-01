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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import kursadmin.repository.ElevDao;
import kursadmin.service.GenAnmInterface;
import kursadmin.service.ConfigManagerInterface;
import kursadmin.service.ElevManagerInterface;
import kursadmin.service.GenOversiktInterface;
import kursadmin.service.KursManagerInterface;
import kursadmin.service.LokalManagerInterface;
import kursadmin.service.PersonManagerInterface;
import kursadmin.service.MailManagerInterface;
import kursadmin.domain.Elev;
import kursadmin.domain.GrundConfig;
import kursadmin.domain.Kurs;
import kursadmin.domain.KursAll;
import kursadmin.domain.Lokal;
import kursadmin.domain.MailConfig;
import kursadmin.domain.PathConfig;
import kursadmin.domain.Person;


public class RapportController implements Controller 
{

    protected final Log logger = LogFactory.getLog(getClass());
    private ElevManagerInterface elevManager;
    private PersonManagerInterface personManager;
    private ConfigManagerInterface configManager;
    private KursManagerInterface kursManager;
    private LokalManagerInterface lokalManager;
    private GenAnmInterface anmGen;
    private GenOversiktInterface oversiktGen;
    private ArrayList<Integer> kidLista = null;
    private List<KursAll> kursLista = null;
    private List<Elev> elever;
    
    int kid = 0;
    private String bet;
    private String kidString = null;
    
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {   
    	Map<String, Object> modelMap = new HashMap<String, Object>();   
    	
    	
    	for (Enumeration<String> pars = request.getParameterNames(); pars.hasMoreElements();)
		{	    		
			String par = pars.nextElement();
			// logger.info("Par: " + par + " Värde: " + request.getParameter(par));			
		}
    	if (request.getParameter("_action") != null && request.getParameter("_action").equals("elevuppf"))
    	{  
    		if (kidLista.size() == 1)
    		{		    		
	    		KursAll ka = kursLista.get(0);
	    		elever     = elevManager.getElever(ka.getKid());	    		
	    		int vidare = 0;
	    		int cnt    = 0;	    		
	    		modelMap.put("kursAll", ka);	    		
	    		for (Elev elev : elever)
	    		{
	    			cnt++;
	    			List<Kurs> kl = kursManager.getKursMiniList(elev.getPid(), ka.getStartdatum());	    			
	    			StringBuffer sb = new StringBuffer();
	    			int i = 0;
	    			for (Kurs k : kl)
	    			{
	    				if (i > 0)
	    					sb.append(", ");
	    				sb.append(k.getBeteckning());
	    				i++;
	    			}
	    			if (i > 0)
	    				vidare++;
	    			elev.setInfo(sb.toString());
	    		}
	    		modelMap.put("Antal", new Integer(cnt));
	    		modelMap.put("Vidare", new Integer(vidare));
	    		modelMap.put("Procent", new Integer((int)(((float) vidare / (float) cnt) * 100)));
	    		modelMap.put("elever", elever);
	    		return new ModelAndView("WEB-INF/jsp/elevuppf.jsp", "modelMap", modelMap);
    		}
    		else
    		{	
    			modelMap.put("error", "Denna rapport kan bara kšras på en kurs");    			
    		}
    	}
    	if (request.getParameter("_action") != null && request.getParameter("_action").equals("ekonomi"))
    	{  
    		int[] kidArr = new int[kidLista.size()];
    		int i = 0;
    		for (Integer kid: kidLista)
    		{	
    			kidArr[i++] = kid.intValue();    		
    		}        		
    		modelMap.put("ekonomi", this.kursManager.getEkonomi(kidArr));
    		modelMap.put("antal", new Integer(kidLista.size() + 1));
    		return new ModelAndView("WEB-INF/jsp/kursekonomi.jsp", "modelMap", modelMap);	
    	}
    	if (request.getParameter("_action") != null && request.getParameter("_action").equals("stim"))
    	{  
    		int[] kidArr = new int[kidLista.size()];
    		int i = 0;
    		for (Integer kid: kidLista)
    		{	
    			kidArr[i++] = kid.intValue();    		
    		}        		
    		modelMap.put("stim", this.kursManager.getStimRapport(kidArr));
    		modelMap.put("c", new Integer(kidLista.size() + 1));
    		return new ModelAndView("WEB-INF/jsp/stimrapport.jsp", "modelMap", modelMap);	
    	}
    	if (request.getParameter("_action") != null && request.getParameter("_action").equals("kursanmalan"))
    	{  
    		List<Lokal> lokalLista = new ArrayList<Lokal>();
    		List<Integer> lidLista = new ArrayList<Integer>();
    		PathConfig paths = configManager.getPathConfig();
    		GrundConfig grundConfig = configManager.getGrundConfig();
    		for (KursAll ka: kursLista)
    		{	   			
    			if (lidLista.contains(ka.getLokal()))
    				continue;
    			lidLista.add(ka.getLokal());
    			lokalLista.add(lokalManager.getLokal(ka.getLokal()));    		
    		}        		   		
    		return new ModelAndView(new RedirectView(anmGen.GenAnmWeb(kursLista, lokalLista, paths, grundConfig)));
    	}
    	if (request.getParameter("_action") != null && request.getParameter("_action").equals("kursoversikt"))
    	{  
    		int i = 0;
    		Map<Integer, Lokal> lokalMap = new HashMap<Integer, Lokal>();
    		PathConfig paths = configManager.getPathConfig();
    		
    		kursLista = kursManager.getKurser(kidString, "", 8); 
    		
    		for (KursAll ka: kursLista)
    		{	   			
    			ka = kursManager.getKursAll(ka.getKid());
    			kursLista.set(i++, ka);
    			if (lokalMap.containsKey(ka.getLokal()))
    				continue;
    			lokalMap.put(ka.getLokal(), lokalManager.getLokal(ka.getLokal()));  			   			
    		}        		   		
    		return new ModelAndView(new RedirectView(oversiktGen.GenOversiktWeb(kursLista, lokalMap, paths)));
    	}
    	// Kolla om det Šr fšrsta anropet
    	if (request.getParameter("kidlist") != null)
    	{
    		kidString = request.getParameter("kidlist");
    		String[] kids = request.getParameter("kidlist").split(",");
    		kidLista = new ArrayList<Integer>();
    		for (int i = 0; i < kids.length; i++)
    			kidLista.add(new Integer(kids[i]));  	
    		kursLista = kursManager.getKurser(kidString, "", 6);    		    	
    	}	    
    	modelMap.put("kursLista", kursLista);
        return new ModelAndView("WEB-INF/jsp/rapporter.jsp", "modelMap", modelMap);        
    }
          
    public void setElevManager(ElevManagerInterface elevManager) 
    {
        this.elevManager = elevManager;
    }
    public void setPersonManager(PersonManagerInterface personManager) 
    {
        this.personManager = personManager;
    }
    public void setConfigManager(ConfigManagerInterface configManager) 
    {
        this.configManager = configManager;
    }
    public void setKursManager(KursManagerInterface kursManager) 
    {
        this.kursManager = kursManager;
    }
    public void setLokalManager(LokalManagerInterface lokalManager) 
    {
        this.lokalManager = lokalManager;
    }
    public void setAnmGen(GenAnmInterface anmGen) 
    {
        this.anmGen = anmGen;
    }
    public void setOversiktGen(GenOversiktInterface oversiktGen) 
    {
        this.oversiktGen = oversiktGen;
    }
}

