package kursadmin.web;

import org.jdom.output.XMLOutputter;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.itextpdf.text.DocumentException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import kursadmin.service.ConfigManagerInterface;
import kursadmin.service.ElevManagerInterface;
import kursadmin.service.FakturaManagerInterface;
import kursadmin.service.GenPdfFakturaInterface;
import kursadmin.service.KursAnmalanManagerInterface;
import kursadmin.service.KursManagerInterface;
import kursadmin.service.PersonManagerInterface;
import kursadmin.service.MailManagerInterface;
import kursadmin.service.pdfgen.GoodLineLdFakturaImpl;
import kursadmin.domain.Elev;
import kursadmin.domain.Faktura;
import kursadmin.domain.GrundConfig;
import kursadmin.domain.KursAll;
import kursadmin.domain.MailConfig;
import kursadmin.domain.Person;


public class ElevController implements Controller 
{

    protected final Log logger = LogFactory.getLog(getClass());
    private final String defMatRubrik = "Kost";
    private final String defReseRubrik = "Resa";
    private final String defLogiRubrik = "Boende";
    private final String defLogi2Rubrik = "Rum";
    private ElevManagerInterface elevManager;
    private PersonManagerInterface personManager;
    private ConfigManagerInterface configManager;
    private KursManagerInterface kursManager;
    private MailManagerInterface mailManager;
    private FakturaManagerInterface fakturaManager;
    private GrundConfig grundConfig;
    private List<Elev> elever;
    private ArrayList<Elev> borta;
    private KursAll kursAll;
    int kid = 0;
    private String bet;
    KursAnmalanManagerInterface kursAnmalanManager;
    private int lastRequest = 1;
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {   
    	Map<String, Object> modelMap = new HashMap<String, Object>();   
    	modelMap.put("kid", new Integer(kid));
    	modelMap.put("beteckning", bet);
    	modelMap.put("mailstatus", grundConfig.isFakeMail());
    	int pris = 0;
    	boolean aktiv = true;   	
    	String info = "";
    	String rum = null;
    	int logi = 0;
    	int mat = 0;
    	int resa = 0;
    	//logger.info("Request method:" +  request.getMethod());
    	if (request.getMethod().equals("POST"))
    	{
    		 for(int i = 0; i < elever.size(); i++)
             {				 				 				
				 Elev elev = elever.get(i);
				
				 if ((request.getParameter("manpris" + (i + 1)) != null) && request.getParameter("pris" + (i + 1)) != null)
				 {	 
					 pris = Integer.parseInt(request.getParameter("pris" + (i + 1)));
					 elev.setManpris(true);
					 if (elev.getPris() != pris)	           		 
			           		elev.setAndrad(true);
			           	 elev.setPris(pris);
				 }      	 
	             	
	            if (request.getParameter("logi" + (i + 1)) != null)
	            {	
	            	 logi = Integer.parseInt(request.getParameter("logi" + (i + 1)));
		            if (elev.getLogi() != logi)
		            {
		            	elev.setLogi(logi);
		            	elev.setAndrad(true);	            	
		            }
	            }    
	            if (request.getParameter("mat" + (i + 1)) != null)
	            {	
	            	 mat = Integer.parseInt(request.getParameter("mat" + (i + 1)));
		            if (elev.getMat() != mat)
		            {
		            	elev.setMat(mat);
		            	elev.setAndrad(true);	            	
		            }
	            }    
	            if (request.getParameter("resa" + (i + 1)) != null)
	            {	
	            	 resa = Integer.parseInt(request.getParameter("resa" + (i + 1)));
		            if (elev.getResa() != resa)
		            {
		            	elev.setResa(resa);
		            	elev.setAndrad(true);	            	
		            }
	            }
	            rum = request.getParameter("rum" + (i + 1));
	            if (elev.getRum() != rum)
	            {
	            	elev.setRum(rum);
	            	elev.setAndrad(true);	            	
	            }
	            info = request.getParameter("info" + (i + 1));
	            if (((elev.getInfo() == null) && (info != null)) || !elev.getInfo().equals(info))
	            	 elev.setAndrad(true);
	             elev.setInfo(info);
	             
	             aktiv = request.getParameter("aktiv" + (i + 1)) != null;
	             if (elev.isAktiv() != aktiv)
	           	 {	 
	           		 elev.setAndrad(true);
	           		 elev.setAktiv(aktiv);
	           	 } 
             }
    	}
    	if (request.getParameter("mailstatus") != null)
    	{
    		grundConfig.setFakeMail(!grundConfig.isFakeMail());
    		configManager.setGrundConfig(grundConfig);
    		modelMap.put("mailstatus", grundConfig.isFakeMail());	
    	}
    	if (request.getParameter("_action") != null && request.getParameter("_action").equals("spara"))
    	{
    		lastRequest = 2;   		 
    		 for (Elev elev: elever)
    	     {
    			 if (elev.isNy())
    	       		 elevManager.insertElev(elev);
    			 else if (elev.isAndrad())	 
	           	 	 elevManager.updateElev(elev);	           	 	 
             }
    		 for(Elev elev: borta)
    		 {    			 
    			 elevManager.deleteElev(elev.getKid(), elev.getPid());    	
    		 }
    		 borta.clear();
    		 elever = this.elevManager.getElever(kid);
    	}
    		
    	if (request.getParameter("radera") != null && lastRequest != 4)
    	{
    		lastRequest = 4;
    		int idx = Integer.parseInt(request.getParameter("radera"));
    		borta.add(elever.remove(idx));
    		modelMap.put("elever", elever);    		
    		return new ModelAndView("WEB-INF/jsp/elever.jsp", "modelMap", modelMap);

    	}
    	if (request.getParameter("_action") != null && request.getParameter("_action").equals("addfirst"))
    	{    		
    		lastRequest = 1;
    		modelMap.put("personer", markPersons(personManager.getPersons()));    		
    		return new ModelAndView("WEB-INF/jsp/eleveradd.jsp", "modelMap", modelMap);
    	}
    	if (request.getParameter("enamn") != null && request.getParameter("enamn").length() > 0)
    	{
    		lastRequest = 1;
    		modelMap.put("personer", markPersons(personManager.getPersons(request.getParameter("enamn"), true)));    		
            return new ModelAndView("WEB-INF/jsp/eleveradd.jsp", "modelMap", modelMap);
            
    	}
    	if (request.getParameter("fnamn") != null && request.getParameter("fnamn").length() > 0)
    	{
    		lastRequest = 1;
    		modelMap.put("personer", markPersons(personManager.getPersons(request.getParameter("fnamn"), false)));    		
            return new ModelAndView("WEB-INF/jsp/eleveradd.jsp", "modelMap", modelMap);
    	}
    	if (request.getParameter("_action") != null && request.getParameter("_action").equals("add"))
    	{
    		lastRequest = 1;
    		kursAll = kursManager.getKursAll(kid);
    		for (Enumeration<String> pars = request.getParameterNames(); pars.hasMoreElements();)
    		{	
    			String par = pars.nextElement();
    			if (par.startsWith("person"))
    			{	    				
    				Elev elev = new Elev();
    				elev.setKid(kid);
    				elev.setPid(Integer.parseInt(par.substring(6)));
    				elev.setNamn(request.getParameter(par));
    				elev.setAktiv(true);
    				elev.setPris((int) kursManager.calcPrice(elev.getPid(), kid));
    				elev.setEmail(personManager.getPerson(elev.getPid()).getEmail());
    				elev.setNy(true);
    				elev.setEnabled(true);
    				elever.add(elev);    			
    				// logger.info("L‰gger till " + elev.getNamn() + "(" + elev.getPid() + ")");    	    			    
    			}	
    		}	
    		//modelMap.put("elever", elever);
            //return new ModelAndView("WEB-INF/jsp/elever.jsp", "modelMap", modelMap);
    	}
    	// Fakturera och skicka bekräftelse
    	if (request.getParameter("_action") != null && request.getParameter("_action").equals("faktura"))
    	{   		    	    		    		
    		int nummer = Integer.parseInt(request.getParameter("_item"));
    		Elev elev = elever.get(nummer);
    		elev.setEnabled(false);
    		elevManager.updateElev(elev);
    		// logger.info("Elev: faktura " + elev.getNamn() + " fakturoro: " + personManager.personHarOfakturerat(elev.getPid(), elev.getKid()));
    		if (personManager.personHarOfakturerat(elev.getPid(), elev.getKid()) > 1)
    		{
    			// Finns flera kurser som är ofakturerade fråga om dessa skall faktureras ?
    			return new ModelAndView(new RedirectView("fakturera.htm?kid=" + kid + "&pid=" + elev.getPid()));
    		}
    		fakturera(elev.getPid(), "" + elev.getKid());
    		elev = elevManager.getElev(elev.getKid(), elev.getPid());
    		elever.set(nummer, elev);
    		modelMap.put("msg", "Anmälningsbekräftelse och faktura har skickats till " + elev.getNamn());
    	}
    	if (request.getParameter("_action") != null && request.getParameter("_action").equals("omfaktura"))
    	{   		    	
    		int nummer = Integer.parseInt(request.getParameter("_item"));
    		Elev elev = elever.get(nummer);
    		elev.setEnabled(false);
    		elev.setAbekr(true);
    		elevManager.updateElev(elev);
    		ArrayList<Elev> tmpElever = new ArrayList<Elev>();
    		tmpElever.add(elev);
    		Faktura faktura = fakturaManager.fakturera(elev.getFakturanr(), tmpElever);
	    	kursAnmalanManager.sendAnmBekr(faktura);
    		elev = elevManager.getElev(elev.getKid(), elev.getPid());
    		
    		elever.set(nummer, elev);
    		modelMap.put("msg", "Anmälningsbekräftelse och faktura har skickats till " + elev.getNamn());    		
       		// logger.info("Efter anrop " + faktura.getFakturanr() + " " + faktura.getFakturanr() + ".pdf");
    		//MailConfig mc = configManager.parseMailConfig("anmbekr", kursAll, elev);    		
    		//mailManager.sendMail(mc, new String[]{elev.getEmail()});    		
    	}
    	// Skicka ny bekräftelse och faktura
    	if (request.getParameter("_action") != null && request.getParameter("_action").equals("open"))
    	{   		    
    		Elev elev = elever.get(Integer.parseInt(request.getParameter("_item")));
    		Faktura faktura = fakturaManager.getFaktura(elev.getFakturanr());
    		if (faktura.isMultiFaktura())
    			modelMap.put("msg", "Fakturan omfattar flera objekt och kan ej räknas om");	    			    			    		
    		else
    		{	
	    		elev.setEnabled(true);
				elev.setAbekr(false);
				elev.setAndrad(true);
				modelMap.put("msg", "Elevposten är öppnad för ändring och ev omfakturering");
    		}	
    	}    	
    	else if (request.getParameter("_action") != null && request.getParameter("_action").equals("bbekr"))
    	{   
    		lastRequest = 1;
    		kursAll = kursManager.getKursAll(kid);
    		Elev elev = elever.get(Integer.parseInt(request.getParameter("_item")));
    		elev.setBbekr(true);
    		elev.setAndrad(true);
    		Faktura faktura = fakturaManager.getFaktura(elev.getFakturanr());
    		kursAnmalanManager.sendBetBekr(faktura);
    		//Elev elev = setBekr(true, (Integer.parseInt(request.getParameter("_item"))), request);  					
			
    		//MailConfig mc = configManager.parseMailConfig("betbekr", kursAll, elev);    		
    		//mailManager.sendMail(mc, new String[]{elev.getEmail()});
    	}
    	else if (request.getParameter("_action") != null && request.getParameter("_action").equals("move"))
    	{  
    		lastRequest = 1;
    		int idx = -1;
    		int pid = Integer.parseInt(request.getParameter("_item"));
    		for (Elev elev: elever)
    			if (elev.getPid() == pid)
    			{
    				idx = elever.indexOf(elev);
    				break;
    			}
    		elever.remove(idx);		    		
    	}
    	
    	if (request.getParameter("kid") != null)
    	{
    		if (request.getParameter("fakturaretur") != null)
    		{	
    			modelMap.put("msg", request.getParameter("fakturaretur"));
    			fakturera(Integer.parseInt(request.getParameter("pid")), request.getParameter("kurser"));
    		}	
    		kid = Integer.parseInt(request.getParameter("kid"));
    		kursAll = kursManager.getKursAll(kid);
    		elever = this.elevManager.getElever(kid);
    		borta = new ArrayList<Elev>(); 
    		modelMap.put("kid", new Integer(kid));    		 
    		
    		
    	}
    	for (Elev elev : elever)
    		if (elev.getFakturanr() == 0 || !elev.isAbekr())
    			elev.setEnabled(true);
    	modelMap.put("kursAll", kursAll);
    	extractExtraData(kursAll, modelMap);
    	modelMap.put("beteckning", kursAll.getBeteckning());
    	modelMap.put("elever", elever);
    	lastRequest = 1;
        return new ModelAndView("WEB-INF/jsp/elever.jsp", "modelMap", modelMap);        
    }
    private void fakturera(int pid, String kidArr)
    {
    	if (!kidArr.equals(""))
    	{	
	    	String kids[] = kidArr.split(",");
	    	ArrayList<Elev> elever = new ArrayList<Elev>();
	    	for (int i = 0; i < kids.length; i++)
	    	{	
	    		Elev elev = elevManager.getElev(Integer.parseInt(kids[i]), pid);
	    		elev.setAbekr(true);	    		
	    		elever.add(elev);
	    	}
	    	Faktura faktura = fakturaManager.fakturera(0, elever);
	    	kursAnmalanManager.sendAnmBekr(faktura);
    	}	
    	
    }
    private List<Person> markPersons(List<Person> pl)
    {
    	Set<Integer> pids = new HashSet<Integer>();
    	for (Elev e: elever)
    		pids.add(new Integer(e.getPid()));
    	for (Person p: pl)
    	{
    		if (pids.contains(p.getPid()))
    			p.setInfo("elev");
    	}
    	return pl;
    }
    private void extractExtraData(KursAll kursAll, Map<String, Object> modelMap)
    {
    	if (kursAll.isMathantering())
    	{
    		ArrayList<String> matLista = new ArrayList<String>();
    		String[] alternativ = kursAll.getMatalternativ().split(",");
    		for (int i = 1; i < alternativ.length; i++)
    			matLista.add(alternativ[i].split(":")[0]);
    		modelMap.put("matLista", matLista);
    		modelMap.put("matRubrik", alternativ[0].equals("*") ? defMatRubrik : alternativ[0]);
    	}
    	if (kursAll.isResehantering())
    	{
    		ArrayList<String> reseLista = new ArrayList<String>();
    		String[] alternativ = kursAll.getResealternativ().split(",");
    		for (int i = 1; i < alternativ.length; i++)
    			reseLista.add(alternativ[i].split(":")[0]);
    		modelMap.put("reseLista", reseLista);
    		modelMap.put("reseRubrik", alternativ[0].equals("*") ? defReseRubrik : alternativ[0]);;
    	}
    	if (kursAll.isRumshantering())
    	{
    		ArrayList<String> logiLista = new ArrayList<String>();
    		String[] alternativ = kursAll.getRumsalternativ().split(",");
    		for (int i = 1; i < alternativ.length; i++)
    			logiLista.add(alternativ[i].split(":")[0]);
    		modelMap.put("logiLista", logiLista);
    		modelMap.put("logiRubrik", alternativ[0].split(":")[0].equals("*") ? defLogiRubrik : alternativ[0].split(":")[0]);
    		modelMap.put("logi2Rubrik", alternativ[0].split(":")[1].equals("*") ? defLogi2Rubrik : alternativ[0].split(":")[1]);
    	}    		
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
        grundConfig = configManager.getGrundConfig();
    }
    public void setKursManager(KursManagerInterface kursManager) 
    {
        this.kursManager = kursManager;
    }
    public void setKursAnmalanManager(KursAnmalanManagerInterface kursAnmalanManager) 
    {
        this.kursAnmalanManager = kursAnmalanManager;
    }
    public void setMailManager(MailManagerInterface mailManager) 
    {
        this.mailManager = mailManager;
    }
    public void setFakturaManager(FakturaManagerInterface fakturaManager)
    {
    	this.fakturaManager = fakturaManager;
    }
   
    
}

