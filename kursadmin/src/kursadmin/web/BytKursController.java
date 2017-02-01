package kursadmin.web;

import org.springframework.web.servlet.mvc.Controller;
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

import kursadmin.domain.Elev;
import kursadmin.domain.Faktura;
import kursadmin.domain.KursAll;
import kursadmin.repository.ElevDao;
import kursadmin.repository.KursDao;
import kursadmin.service.ElevManagerInterface;
import kursadmin.service.FakturaManagerInterface;
import kursadmin.service.KursAnmalanManagerInterface;
import kursadmin.service.KursManagerInterface;
import kursadmin.service.LokalManagerInterface;
import kursadmin.service.InstruktorManagerInterface;
public class BytKursController implements Controller 
{

    protected final Log logger = LogFactory.getLog(getClass());
    private KursManagerInterface kursManager;
    private ElevManagerInterface elevManager;
    private FakturaManagerInterface fakturaManager;
    private KursAnmalanManagerInterface kursAnmalanManager;
    private int pid;
    private int oldKid;
    private int newKid;
    private String oldBeteckning = null;
    
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {   
    	Map<String, Object> modelMap = new HashMap<String, Object>();   
    	
    	if (request.getParameter("_item") != null && request.getParameter("_item").length() > 0)
    	{
    		newKid = Integer.parseInt(request.getParameter("_item"));
    		modelMap.put("klar", new Integer(0));
    		modelMap.put("pid", new Integer(pid));
    		KursAll oldKurs = kursManager.getKursAll(oldKid);
    		oldBeteckning = oldKurs.getBeteckning();
    		KursAll newKurs = kursManager.getKursAll(newKid);
    		Elev elev = elevManager.getElev(oldKid, pid);
    		modelMap.put("namn", elev.getNamn());  
			modelMap.put("newKurs", newKurs);  
			modelMap.put("oldKurs", oldKurs);
    		if (elev.getEmail() != null)
				modelMap.put("email", elev.getEmail());
    		// Om kursen är ofakturerad är det bara att flytta.
    		if (elev.getFakturanr() == 0)
    			elevManager.flyttaElev(pid, oldKid, newKid);
    		
    		else if (oldKurs.getPris() == newKurs.getPris())
    		{
    			elevManager.flyttaElev(pid, oldKid, newKid); 
    			//fakturaManager.updateFakturaRader(elev.getFakturanr());
    		}
    		
			
    		// Skall kunden krediteras, hur mycket?
    		else if (oldKurs.getPris() > newKurs.getPris())
    		{
    			modelMap.put("belopp", (int) oldKurs.getPris() - newKurs.getPris());
    			// Är den redan betald?
    			if (elev.getPris() == elev.getBetalt())
    			{
    				return new ModelAndView("WEB-INF/jsp/bytkursfraga.jsp?billigare=betald", "modelMap", modelMap);
    			}
    			else
    			{ 	 return new ModelAndView("WEB-INF/jsp/bytkursfraga.jsp?billigare=obetald", "modelMap", modelMap);
    				   
    			}    			
    		}
    		// Skall det skapas en tilläggsfaktura på mellanskillnaden?
    		else if (oldKurs.getPris() < newKurs.getPris())
    		{
    			
    			// modelMap.put("nyttBelopp", (int) kursManager.calcPrice(pid, newKid, true));
    			modelMap.put("belopp", (int) newKurs.getPris() - oldKurs.getPris());
    			// Är den redan betald?
    			if (elev.getPris() == elev.getBetalt())
    			{
    				return new ModelAndView("WEB-INF/jsp/bytkursfraga.jsp?dyrare=betald", "modelMap", modelMap);
    			}
    			else
    			{ 	 return new ModelAndView("WEB-INF/jsp/bytkursfraga.jsp?dyrare=obetald", "modelMap", modelMap);
    				   
    			}
    		}
    		
    	}
    	else if (request.getParameter("todo") != null) // Svar på fakturafråga
    	{
    		modelMap.put("klar", new Integer(0));
    		modelMap.put("pid", new Integer(pid));
    		elevManager.flyttaElev(pid, oldKid, newKid);
    		Elev elev = elevManager.getElev(newKid, pid);
    		int fakturanr = elev.getFakturanr();
    		String maila = request.getParameter("maila");
    		Faktura faktura = null;
    		if (request.getParameter("todo").equals("omfakturera"))
    		{    			
    			
    			List<Elev> elever = elevManager.getEleverPaFaktura(fakturanr);
    			for (Elev eleven: elever)
    			{	
    				eleven.setFakturanr(0);
    				elevManager.updateElev(eleven);
    			}	
 				faktura = fakturaManager.fakturera(fakturanr, elever);
    		}
    		else if (request.getParameter("todo").equals("tillagg"))
    		{
    			elev.setManpris(true);
    			elev.setPris(Integer.parseInt(request.getParameter("belopp")));
    			faktura = fakturaManager.fakturera(elev, fakturanr, "Tillägg för byte ifrån " + oldBeteckning);    			
    		}
    		else if (request.getParameter("todo").equals("kundf"))
    		{
    			fakturaManager.SkapaKundfordran(elev, Integer.parseInt(request.getParameter("belopp")), "Kursbyte ifrån " + oldBeteckning + ", lagt som kundfordran");
    		}
    		else if (request.getParameter("todo").equals("kundt"))
    		{
    			fakturaManager.SkapaKredit(fakturanr, Integer.parseInt(request.getParameter("belopp")), "Kursbyte ifrån " + oldBeteckning + ", krediteras");		
    		}
    		if (faktura != null && maila != null)
    			kursAnmalanManager.sendAnmBekr(faktura);
    	}	
    	else
    	{    		    		
    	    pid = Integer.parseInt(request.getParameter("pid"));
    	    oldKid = Integer.parseInt(request.getParameter("kid"));
    	    modelMap.put("kurser", this.kursManager.getKurser(request.getParameter("beteckning").substring(0,5), "", 1));
    		modelMap.put("klar", new Integer(oldKid));
    		modelMap.put("namn", request.getParameter("namn"));
    	}

    	return new ModelAndView("WEB-INF/jsp/bytkurs.jsp", "modelMap", modelMap);
    }
    public void setKursManager(KursManagerInterface kursManager) 
    {
        this.kursManager = kursManager;
    }
    public void setElevManager(ElevManagerInterface elevManager) 
    {
        this.elevManager = elevManager;
    }
    public void setFakturaManager(FakturaManagerInterface fakturaManager) 
    {
        this.fakturaManager = fakturaManager;
    }
    public void setKursAnmalanManager(KursAnmalanManagerInterface kursAnmalanManager) 
    {
        this.kursAnmalanManager = kursAnmalanManager;
    }
}

