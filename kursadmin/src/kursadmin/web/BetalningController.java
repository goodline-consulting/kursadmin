package kursadmin.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


import kursadmin.domain.Betalning;
import kursadmin.domain.Elev;
import kursadmin.domain.Faktura;
import kursadmin.domain.Fakturarad;
import kursadmin.domain.KursAll;
import kursadmin.domain.MailConfig;
import kursadmin.domain.Person;
import kursadmin.repository.BetalningDao;
import kursadmin.service.ConfigManagerInterface;
import kursadmin.service.ElevManagerInterface;
import kursadmin.service.FakturaManagerInterface;
import kursadmin.service.KursAnmalanManagerInterface;
import kursadmin.service.KursManagerInterface;
import kursadmin.service.MailManagerInterface;
import kursadmin.service.PersonManagerInterface;
import kursadmin.utils.KontrollSiffra;
public class BetalningController implements Controller 
{

    protected final Log logger = LogFactory.getLog(getClass());
    private KursManagerInterface kursManager;
    private PersonManagerInterface personManager;
    private ElevManagerInterface elevManager;
    BetalningDao betalningDao;
    private ConfigManagerInterface configManager;
    private FakturaManagerInterface fakturaManager;
    KursAnmalanManagerInterface kursAnmalanManager;
    private List<Elev> elever;
	private List<KursAll> kurser;
	private Person person;
	private Faktura faktura = null;
	private String target = "ekonomi.htm";
	private Betalning betalning = null;
	private Betalning oldBetalning = null;
	private String placering = null;
	
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {   
    	int betalt;
    	Date betaldatum;
    	Map<String, Object> modelMap = new HashMap<String, Object>();     	
   		if (request.getParameter("id") != null)
   		{
   			target = "bgclist.htm";
   			betalning = betalningDao.getBetalning(Integer.parseInt(request.getParameter("id")));
   			modelMap.put("target", target);
   			if (betalning.getAmount() < 0) // Kreditbetalning vi sŠtter den bara som utbetald
   			{
   				betalning.setPlacerat(betalning.getAmount());
   				betalning.setPlacerad(true);
   				betalning.setInfo(new StringBuffer("Krediteringen utbetald"));
   				betalningDao.updateBetalning(betalning);
   				modelMap.put("msg", "Krediteringen Šr nu markerad som utbetald till " + betalning.getName());
   	    		return new ModelAndView("WEB-INF/jsp/betalning.jsp?klar=yes", "modelMap", modelMap);
   			}
   			if (betalning.getFakturanr() != 0) // Knoppa av en betalning
    		{
   				// LŠgg šver allt oplacerat kapital pŒ den nya betalningen sŒ lŠnge.
   				oldBetalning = betalning;    			
    			betalning = new Betalning();
    			betalning.setName(oldBetalning.getName());
    			betalning.setBetalkanal(oldBetalning.getBetalkanal());
    			betalning.setAmount(oldBetalning.getAmount() - oldBetalning.getPlacerat());
    			betalning.setParentid(oldBetalning.getId());
    			betalning.setInfo(new StringBuffer("HŠrršr frŒn betalning " + oldBetalning.getId()));
    			betalning.setBetaldatum(oldBetalning.getBetaldatum());   
    			betalning.setPlacerat(0);
    			oldBetalning.setAmount(oldBetalning.getPlacerat());
    			oldBetalning.setPlacerad(true);    			
    		}
   			modelMap.put("placering", "faktura");
   			modelMap.put("betalsatt", "Bgmax");
   	    	return new ModelAndView("WEB-INF/jsp/betalning.jsp?search=yes", "modelMap", modelMap);
   			
   		}
   		else if (request.getParameter("refnr") != null && 
   				 request.getParameter("registrera") == null &&
   				request.getParameter("placerakund") == null)
    	{
    		// betalning = null; Vem fan har satt denna till null?
   			if (request.getParameter("fromelev") != null)
        		target = "elever.htm?kid=" + request.getParameter("fromelev");    		
        	else if (betalning == null)
        		target = "ekonomi.htm";
    		modelMap.put("target", target);
    		String refnr = request.getParameter("refnr");
    		
    		// Kolla om placering gŠller person
    		placering = request.getParameter("placering");
    		if (placering != null && placering.equals("person"))
    		{
    			if (betalning == null) // Endast befintlig betalning kan placeras pŒ kund
    			{
    				modelMap.put("msg", "Endast befintlig betalning kan placeras pŒ kund");
        			return new ModelAndView("WEB-INF/jsp/betalning.jsp?ref_error=true", "modelMap", modelMap);
    			}
    			if (!checkKundnr(refnr))
    			{
    				modelMap.put("msg", "Felaktigt angivet kundnummer");
    				modelMap.put("placering", "person");
        			return new ModelAndView("WEB-INF/jsp/betalning.jsp?ref_error=true", "modelMap", modelMap);
    			}
    			person = personManager.getPerson(Integer.parseInt(refnr));
    			if (person == null)
    			{
    				modelMap.put("msg", "Kund saknas");
    				modelMap.put("placering", "person");
        			return new ModelAndView("WEB-INF/jsp/betalning.jsp?ref_error=true", "modelMap", modelMap);
    			}
    			modelMap.put("person", person);    			
    			modelMap.put("betalt", (int) betalning.getAmount());
    			
    			return new ModelAndView("WEB-INF/jsp/betalning.jsp", "modelMap", modelMap);
    		}
    		
    		
    		// Placeringen gŠller kurs
    		if (!checkRefnr(refnr))
    		{	
    			if (placering != null)
    	    		modelMap.put("placering", placering);
    			modelMap.put("msg", "Felaktigt angivet fakturanummer");
    			return new ModelAndView("WEB-INF/jsp/betalning.jsp?ref_error=true", "modelMap", modelMap);
    		}
    		int fakturanr = Integer.parseInt(refnr.substring(0, refnr.length() - 1));
    		
    		faktura = fakturaManager.getFaktura(fakturanr);
    		
    		if (faktura == null)
    		{
    			if (placering != null)
    	    		modelMap.put("placering", placering);
    			modelMap.put("msg", "Kan ej hitta faktura som matchar angivet referensnummer");
    			return new ModelAndView("WEB-INF/jsp/betalning.jsp?no_found=true", "modelMap", modelMap);
    		}
    		
    		person = personManager.getPerson(faktura.getPid());
			
			kurser = new ArrayList<KursAll>();
			elever = new ArrayList<Elev>();
			for (Fakturarad fakturarad: faktura.getRader())
			{    			    				
				KursAll kurs = kursManager.getKursAll(fakturarad.getKid());
				Elev elev = elevManager.getElev(kurs.getKid(), person.getPid());
				if (elev == null)
				// Eleven kanske inte gŒr pŒ kursen lŠngre men vi behšver en elev fšr att fšrdela betalningen rŠtt.	
				{
					elev = new Elev();
					elev.setNy(true); // fšr att kolla att det Šr en fejk elev
					elev.setKid(fakturarad.getKid());
					elev.setPid(person.getPid());
					elev.setPris((int)fakturarad.getBelopp());
				}
				kurs.setPris((int)fakturarad.getBelopp());
				kurser.add(kurs);
				elever.add(elev);
			}    	
			
			modelMap.put("target", target);
			modelMap.put("faktura", faktura);
    	    modelMap.put("kurser", kurser);   		
    		modelMap.put("person", person);
    		
    		if (betalning != null)
    		{	
    			modelMap.put("info", betalning.getInfo());
    			modelMap.put("betalsatt", betalning.getBetalkanal());
    			modelMap.put("betaldatum", betalning.getBetaldatum());
    			if (betalning.getAmount() >= faktura.getBelopp() - faktura.getBetalt())
    				modelMap.put("betalt", (int) (faktura.getBelopp() - faktura.getBetalt()));
    			else
    				modelMap.put("betalt", (int) betalning.getAmount());
    		}
    		else
    		{	
    			modelMap.put("betalsatt", "Plusgiro");
    			modelMap.put("betaldatum", new Date());
    			modelMap.put("betalt", (int) ((faktura.getBelopp() - faktura.getBetalt())));
    		}	    
    		if (faktura.getBetald() != null)
    		{
    			if (placering != null)
    	    		modelMap.put("placering", placering);
    			modelMap.put("msg", "Fakturan Šr redan fullt betald");
    			return new ModelAndView("WEB-INF/jsp/betalning.jsp?betald_error=yes","modelMap", modelMap);
    		}	
    		return new ModelAndView("WEB-INF/jsp/betalning.jsp", "modelMap", modelMap);
    	}	
   		else if (request.getParameter("placerakund") != null) //Betalningen skall placeras pŒ kund som tillgodohavande
   		{
   			modelMap.put("target", target);
   			betalt = checkBetalt(request.getParameter("betalt"));    		
    		if (betalt == -1) 
    		{    			
    			modelMap.put("msg", "Felaktig summa angiven");
    			return new ModelAndView("WEB-INF/jsp/betalning.jsp?error=yes", "modelMap", modelMap);
    		}      		    		
    		betalning.setPlacerat(betalning.getAmount());
    		betalning.setFakturanr(0);
    		betalning.setPid(person.getPid());
    		betalning.setPlacerad(true);
    		if (oldBetalning != null && (betalning.getName().indexOf('(') != -1))
    		{
    			String tmp = betalning.getName().substring(betalning.getName().indexOf('('));
				betalning.setName(person.getFnamn() + " " + person.getEnamn() + " " + tmp);
    		}
    		else
    			betalning.setName(person.getFnamn() + " " + person.getEnamn());
    		if (oldBetalning != null)
			{			
    			betalning.setInfo(betalning.getInfo().append(" Kund tillgodo"));
				betalningDao.updateBetalning(oldBetalning);
				betalningDao.insertBetalning(betalning);
			}
			else 
			{
				betalning.setInfo(new StringBuffer("Kund tillgodo"));
				betalningDao.updateBetalning(betalning);
			}	
    		person.setSaldo(person.getSaldo() + - betalning.getPlacerat());
    		personManager.updatePerson(person);
    		modelMap.put("msg", "Betalningen Šr nu placerad tillgodo pŒ " + betalning.getName());
    		betalning = null;
    		oldBetalning = null;
    		placering = null;
    		return new ModelAndView("WEB-INF/jsp/betalning.jsp?klar=yes", "modelMap", modelMap);
   		}
    	else if (request.getParameter("registrera") != null)
    	{
    		modelMap.put("faktura", faktura);
    		modelMap.put("kurser", kurser);
    		modelMap.put("person", person);
    		modelMap.put("betalt", request.getParameter("betalt"));
    		//modelMap.put("betaldatum", request.getParameter("betaldatum"));
    		modelMap.put("betalsatt", request.getParameter("betalsatt"));
    		modelMap.put("target", target);
    		betalt = checkBetalt(request.getParameter("betalt"));
    		
    		if (betalt == -1) 
    		{
    			modelMap.put("msg", "Felaktig summa angiven");
    			return new ModelAndView("WEB-INF/jsp/betalning.jsp?error=yes", "modelMap", modelMap);
    		}    		
    		if (betalt > faktura.getBelopp())
    		{
    			modelMap.put("msg", "Angiven summa Šr hšgre Šn fakturans belopp");
    			return new ModelAndView("WEB-INF/jsp/betalning.jsp?error=yes", "modelMap", modelMap);
    		}
    		if (betalt > (faktura.getBelopp() - faktura.getBetalt()))
    		{
    			modelMap.put("msg", "Med denna betalning šverstiger inbetalt belopp fakturabeloppet");
    			return new ModelAndView("WEB-INF/jsp/betalning.jsp?error=yes", "modelMap", modelMap);
    		}
    		if ((faktura.getBetalt() + betalt) < faktura.getBelopp())
    			faktura.setBetalt(faktura.getBetalt() + betalt);
    		else
    			faktura.setBetalt(faktura.getBelopp());
    		if (request.getParameter("betaldatum").equals(""))
    			betaldatum = new Date();
    		else
    		{
    			try 
    			{
    				betaldatum = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("betaldatum"));
				} 
    			catch (ParseException e) 
				{
					modelMap.put("msg", "Felaktigt angivet betaldatum");
	    			return new ModelAndView("WEB-INF/jsp/betalning.jsp?error=yes", "modelMap", modelMap);
				}
    		}
    		modelMap.put("betaldatum", betaldatum);    	
    		boolean mailad = false;
    		double summa = faktura.getBetalt();
    		String kidArr = "";
    		if (request.getParameter("maila") != null)
    		{    			
    			kursAnmalanManager.sendBetBekr(faktura);   			
        		mailad = true;
    		}
    		
    		// GŒ igenom alla elever och fšrdela betalningen
    		for (Elev elev: elever)
    		{
    			elev.setBetaldatum(betaldatum);
    			if (summa >= elev.getPris())
    			{	
    				elev.setBetalt(elev.getPris());
    				summa -= elev.getPris();
    			}	
    			else
    			{
    				elev.setBetalt((int)summa);
    				summa = 0;
    			}
    			elev.setBbekr(mailad);
    			if (!elev.isNy())
    				elevManager.updateElev(elev);
        		kidArr += elev.getBeteckning() + ",";
        		if (summa == 0)
        			break;
    		}
    		if (faktura.getBelopp() == faktura.getBetalt())
    			faktura.setBetald(betaldatum);
    		fakturaManager.updateFaktura(faktura);
    		if (betalning == null)
    		{    			
    			betalning = new Betalning();   			
    			betalning.setAmount(betalt);
        		betalning.setBetaldatum(betaldatum);
    		}
    		if (oldBetalning != null && (betalning.getName().indexOf('(') != -1))
    		{
    			String tmp = betalning.getName().substring(betalning.getName().indexOf('('));
				betalning.setName(person.getFnamn() + " " + person.getEnamn() + " " + tmp);
    		}
    		else
    			betalning.setName(person.getFnamn() + " " + person.getEnamn());
    		betalning.setPlacerat(betalt); 
    		
    		if (betalning.getAmount() == betalning.getPlacerat())
    			betalning.setPlacerad(true);
    		else
    			betalning.setPlacerad(false);
    		//betalning.setRefnr("" + faktura.getOcr());    		
    		betalning.setBetalkanal(request.getParameter("betalsatt"));
    		//betalning.setKurs(kidArr.substring(0, kidArr.length() - 1));
    		betalning.setFakturanr(faktura.getFakturanr());   		
    		betalning.setInfo(new StringBuffer(request.getParameter("info")));
    		if (target.startsWith("bgc"))
    		{	
    			if (oldBetalning != null)
    			{
    				// Om det fanns mer pengar pŒ betalningen Šn vad som kunnat placerats sŒ
    				// lŠggs resten tillbaka pŒ ursprungsbetalningen.
    				if (betalning.getAmount() > betalning.getPlacerat())
    				{
    					oldBetalning.setAmount(oldBetalning.getAmount() + (betalning.getAmount() - betalning.getPlacerat()));
    					if (oldBetalning.getAmount() > oldBetalning.getPlacerat())
    						oldBetalning.setPlacerad(false);
    					else
    						oldBetalning.setPlacerad(true);
    					betalning.setAmount(betalning.getPlacerat());
    					betalning.setPlacerad(true);
    				}
    				betalningDao.updateBetalning(oldBetalning);
    				betalningDao.insertBetalning(betalning);
    			}
    			else
    				betalningDao.updateBetalning(betalning);    		
    		}	
    		else
    			betalningDao.insertBetalning(betalning);
    		betalning = null;
    		oldBetalning = null;
    		placering = null;
    		modelMap.put("msg", "Betalningen Šr nu registrerad");
    		if (target.startsWith("elev") || target.startsWith("bgc"))
    			return new ModelAndView("WEB-INF/jsp/betalning.jsp?klar=yes", "modelMap", modelMap);
    		else
    			return new ModelAndView("WEB-INF/jsp/betalning.jsp?search=yes&ok=yes", "modelMap", modelMap);
    	}
    	else if (request.getParameter("start") != null)
    	{	
    		target = "ekonomi.htm";
    		betalning = null;
    		oldBetalning = null;
    	}	
    	modelMap.put("target", target);
    	if (placering != null)
    		modelMap.put("placering", placering);
    	return new ModelAndView("WEB-INF/jsp/betalning.jsp?search=yes", "modelMap", modelMap);
    }
    private int checkBetalt(String summa)
    {
    	int result;
    	try
		{
			result = Integer.parseInt(summa);
		}
		catch (NumberFormatException e)
		{
			return -1;
		}
		return result;
    }
    private boolean checkRefnr(String refnr)
    {    	
    	char kontrollSiffra;
		if (refnr.length() < 2)
			return false;
		try
		{
			Integer.parseInt(refnr);
		}
		catch (NumberFormatException e)
		{
			return false;
		}
		kontrollSiffra = KontrollSiffra.calc(refnr.substring(0, refnr.length() - 1)).charAt(0);
		if (kontrollSiffra != refnr.charAt(refnr.length() - 1))
			return false;
		return true;
    }
    private boolean checkKundnr(String refnr)
    {    	
    	try
		{
			Integer.parseInt(refnr);
		}
		catch (NumberFormatException e)
		{
			return false;
		}
		return true;
    }
    public void setKursManager(KursManagerInterface kursManager) 
    {
        this.kursManager = kursManager;
    }
    public void setPersonManager(PersonManagerInterface personManager) 
    {
        this.personManager = personManager;
    }
    public void setElevManager(ElevManagerInterface elevManager) 
    {
        this.elevManager = elevManager;
    }
    public void setBetalningDao(BetalningDao betalningDao)
	{
		this.betalningDao = betalningDao;
	}
    public void setConfigManager(ConfigManagerInterface configManager)
    {
    	this.configManager = configManager;
    }
    public void setfakturaManager(FakturaManagerInterface fakturaManager) 
    {
        this.fakturaManager = fakturaManager;
    }
    public void setKursAnmalanManager(KursAnmalanManagerInterface kursAnmalanManager) 
    {
        this.kursAnmalanManager = kursAnmalanManager;
    }
}

