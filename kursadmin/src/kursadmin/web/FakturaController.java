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
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import kursadmin.domain.Faktura;
import kursadmin.domain.Fakturarad;
import kursadmin.domain.Kurs;
import kursadmin.domain.KursAll;
import kursadmin.domain.Person;
import kursadmin.repository.BetalningDao;
import kursadmin.service.ElevManagerInterface;
import kursadmin.service.FakturaManagerInterface;
import kursadmin.service.KursManagerInterface;
import kursadmin.service.PersonManagerInterface;
public class FakturaController implements Controller 
{

    protected final Log logger = LogFactory.getLog(getClass());
    private KursManagerInterface kursManager;
    private ElevManagerInterface elevManager;
    private PersonManagerInterface personManager;
    private FakturaManagerInterface fakturaManager;
    private BetalningDao betalningDao;
    private int fakturanr = 0;    
    private int pid = 0;
    //private String kidArr = null;
    private String target = null;
    private Person person = null;    
    private List<KursAll> kurser = new ArrayList<KursAll>();
    private List<Faktura> fakturor = null;
    
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {   
    	Map<String, Object> modelMap = new HashMap<String, Object>();   
    	
    	// Användaren har valt
    	if (request.getMethod().equals("POST"))
    	{
    		if (request.getParameter("kreditfakturanr") != null)
    		{
    			
    			fakturanr = Integer.parseInt(request.getParameter("kreditfakturanr"));    			
    			fakturaManager.SkapaKredit(fakturanr, 
    									   Integer.parseInt(request.getParameter("belopp")), 
    									   request.getParameter("info"));
    		}
    		return new ModelAndView(new RedirectView("faktura.htm?person=" + person.getPid()));
    	}
    	// Detta är första anropet
    	else    		
    	{    		
    		if (request.getParameter("kreditera") != null) // Vi kommer ifrån listningen
    		{
    			Faktura faktura = fakturor.get(Integer.parseInt(request.getParameter("kreditera")));
    			person = personManager.getPerson(faktura.getPid());
    			modelMap.put("maxbelopp", (int) faktura.getBelopp());
    			modelMap.put("fakturanr", faktura.getFakturanr());
    			modelMap.put("namn", person.getFnamn() + " " + person.getEnamn());
    			modelMap.put("belopp", (int) faktura.getBelopp());
    			return new ModelAndView("WEB-INF/jsp/kreditera.jsp", "modelMap", modelMap);
    		}
    		if (request.getParameter("tillgodo") != null) // Vi kommer ifrån listningen
    		{
    			Faktura faktura = fakturor.get(Integer.parseInt(request.getParameter("tillgodo")));
    			person = personManager.getPerson(faktura.getPid());
    			fakturaManager.setTillGodo(faktura);    			
    			modelMap.put("msg", (int) faktura.getBelopp() + " har lagts tillgodo på " + person.getFnamn() + " " + person.getEnamn());
    			
    		}
    		if (request.getParameter("utbetala") != null) // Vi kommer ifrån listningen
    		{
    			Faktura faktura = fakturor.get(Integer.parseInt(request.getParameter("utbetala")));
    			person = personManager.getPerson(faktura.getPid());
    			fakturaManager.utbetala(faktura);  			
    			modelMap.put("msg", (int) faktura.getBelopp() + " har lagts för utbetalning till " + person.getFnamn() + " " + person.getEnamn());
    			
    		}
    		if (request.getParameter("visa") != null) // Vi kommer ifrån kursdeltagarbilden
    		{	
    			fakturanr = Integer.parseInt(request.getParameter("visa"));
    			Faktura faktura = fakturaManager.getFaktura(fakturanr);
    			if (faktura == null)
    			{
    				modelMap.put("msg", "Fakturan saknas");
    				return new ModelAndView("WEB-INF/jsp/visafaktura.jsp", "modelMap", modelMap);
    			}
    			Person person = personManager.getPerson(faktura.getPid());
    			
    			List<KursAll> kurser = new ArrayList<KursAll>();
    			for (Fakturarad fakturarad : faktura.getRader())
    			{    			    				
    				KursAll kurs = kursManager.getKursAll(fakturarad.getKid());
    				kurs.setPris((int)fakturarad.getBelopp());   
    				if (fakturarad.getSpec() != null)
    				{	
    					kurs.setKursnamn(fakturarad.getSpec());
    					kurs.setStartdatum(null);
    				}
    				kurser.add(kurs);
    			}    		
    			modelMap.put("faktura", faktura);
        	    modelMap.put("kurser", kurser);   		
        		modelMap.put("person", person);
        		modelMap.put("betalningar", betalningDao.getBetalningList(fakturanr));
        		return new ModelAndView("WEB-INF/jsp/visafaktura.jsp", "modelMap", modelMap);
    		}
    		else if (request.getParameter("person") != null) // Vi kommer ifrån bla, bla
    		{	
    			target = "personer.htm";
    			pid = Integer.parseInt(request.getParameter("person"));
    			person = personManager.getPerson(pid);
    		}
    		else if (request.getParameter("radera") != null)
    		{    			
    			//modelMap.put("msg", "Fakturan får ej raderas");
    			// return new ModelAndView("WEB-INF/jsp/visafakturor.jsp?error=yes", "modelMap", modelMap);
    			fakturaManager.deleteFaktura(Integer.parseInt(request.getParameter("radera")));
    			
    		}
    		fakturor = fakturaManager.getFakturor(pid);
    		
    		for (Faktura faktura: fakturor)
    		{
    			String kurser = new String();
    			
    			for (Fakturarad fakturarad : faktura.getRader())
    			{    			    				
    				Kurs k = kursManager.getKurs(fakturarad.getKid());    				
    				kurser += k.getBeteckning() + ", ";
    			}  
    			/*
    			for (Elev elev: elevManager.getEleverPaFaktura(faktura.getFakturanr()))
    			{
    				kurser += elev.getBeteckning() + ", ";
    			}
    			*/
    			if (kurser.length() > 2)
    				faktura.setKids(kurser.substring(0, kurser.length() - 2));
    		}
    		
			modelMap.put("fakturor", fakturor);        	       		
    		modelMap.put("person", person);
    		return new ModelAndView("WEB-INF/jsp/visafakturor.jsp", "modelMap", modelMap);
    	}
    	//return new ModelAndView(new RedirectView("www.apple.com/se"));
    }
    
    public void setKursManager(KursManagerInterface kursManager) 
    {
        this.kursManager = kursManager;
    }
    public void setElevManager(ElevManagerInterface elevManager) 
    {
        this.elevManager = elevManager;
    }
    public void setPersonManager(PersonManagerInterface personManager) 
    {
        this.personManager = personManager;
    }
    public void setfakturaManager(FakturaManagerInterface fakturaManager) 
    {
        this.fakturaManager = fakturaManager;
    }
    public void setBetalningDao(BetalningDao betalningDao)
	{
		this.betalningDao = betalningDao;
	}
}

