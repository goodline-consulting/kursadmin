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
import java.util.Enumeration;
import java.util.Map;
import java.util.HashMap;

import kursadmin.domain.Config;
import kursadmin.domain.KursNiva;
import kursadmin.domain.KursTyp;
import kursadmin.repository.LokalDao;
import kursadmin.service.KursTypManagerInterface;
import kursadmin.service.LokalManagerInterface;
import kursadmin.service.OrganisationManagerInterface;

public class KursTypController implements Controller 
{

    protected final Log logger = LogFactory.getLog(getClass());
    private KursTypManagerInterface kursTypManager;
    OrganisationManagerInterface organisationManager;
    private KursTyp kursTyp;
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {   
    	Map<String, Object> modelMap = new HashMap<String, Object>();   
    	if (request.getMethod() == "POST")
    	{
    		kursTyp.setResehantering(false);
    		kursTyp.setMathantering(false);
    		kursTyp.setRumshantering(false);
    		ArrayList<KursNiva> nivaList = new ArrayList<KursNiva>();
    		for (Enumeration<String> pars = request.getParameterNames(); pars.hasMoreElements();)
    		{
    			String par = pars.nextElement();
    			//logger.info("par: " + par + " value " + request.getParameter(par));
    			if (par.equals("namn"))
    			{
    				kursTyp.setNamn(request.getParameter(par));
    			}
    			else if (par.equals("info"))
    			{
    				kursTyp.setInfo(request.getParameter(par));
    			}
    			else if (par.equals("momssats"))
    			{
    				kursTyp.setMomssats(Double.parseDouble(request.getParameter(par).replace(',', '.')));
    			}
    			else if (par.equals("momsbak"))
    			{
    				kursTyp.setMomsbak(Double.parseDouble(request.getParameter(par).replace(',', '.')));
    			}
    			else if (par.equals("typavrabatt"))
    			{
    				if (request.getParameter(par).equals("procent"))
    					kursTyp.setTypavrabatt(KursTyp.procentrabatt);
    				else if (request.getParameter(par).equals("kronor"))
    					kursTyp.setTypavrabatt(KursTyp.kronrabatt);
    				else
    					kursTyp.setTypavrabatt(KursTyp.ingenrabatt);
    			}
    			else if (par.equals("rabatter"))
    			{
    				kursTyp.setRabatter(request.getParameter(par));
    			}
    			else if (par.equals("resa"))
    			{
    				kursTyp.setResehantering(true);
    			}
    			else if (par.equals("mat"))
    			{
    				kursTyp.setMathantering(true);
    			}
    			else if (par.equals("boende"))
    			{
    				kursTyp.setRumshantering(true);
    			}
    			else if (par.equals("fakturatyp"))
    			{
    				kursTyp.setFakturatyp(Integer.parseInt(request.getParameter(par)));
    			}
    			else if (par.equals("organisation"))
    			{
    				//logger.info("namn " + par.toString() +  " " + request.getParameter(par));
    				kursTyp.setOid(Integer.parseInt(request.getParameter(par)));
    			}
    			else if (par.equals("fakturaklass"))
    			{
    				kursTyp.setFakturaklass(request.getParameter(par));
    			}
    			else if (par.startsWith("humle"))
    			{
					KursNiva kn = new KursNiva();
					kn.setNamn(request.getParameter(par));
					kn.setNid(Integer.parseInt(par.substring(5)));
					kn.setTid(kursTyp.getTid());
	                nivaList.add(kn);	                
    			}
    			
    		}  
    		//logger.info("namn " + kursTyp.getNamn());
    		//logger.info("info " + kursTyp.getInfo());
    		if (kursTyp.getTid() == 0)
    			kursTypManager.insertKursTyp(kursTyp, nivaList);
    		else
    		{
    			kursTypManager.updateKursTyp(kursTyp);   			
    			kursTypManager.insertKursNivaList(nivaList, kursTyp.getTid());
    		}
    		kursTyp = null;
            modelMap.put("kurstyper", this.kursTypManager.getKursTypList());
            return new ModelAndView("WEB-INF/jsp/kurstyper.jsp", "modelMap", modelMap);
    	}
    	if (request.getParameter("recid") != null)
    	{
    		return new ModelAndView("WEB-INF/jsp/kurstypinfo.jsp", "kurstyp", this.kursTypManager.getKursTyp(Integer.parseInt(request.getParameter("recid"))));
    	}	
    	if (request.getParameter("edit") != null)
    	{
    		if (Integer.parseInt(request.getParameter("edit")) == 0)
    		{	
    			kursTyp = new KursTyp();
    			kursTyp.setTid(0);
    		}	
    		else
    		{	
    			kursTyp = kursTypManager.getKursTyp(Integer.parseInt(request.getParameter("edit")));
    			modelMap.put("nivaer", kursTypManager.getKursNivaList(kursTyp.getTid()));
    		}	
    		modelMap.put("kurstyp", kursTyp);
    		modelMap.put("orgs", organisationManager.getOrgList());
    		return new ModelAndView("WEB-INF/jsp/kurstypedit.jsp", "modelMap", modelMap);
    	}	
    	if (request.getParameter("radera") != null)
    	{
    		if (! kursTypManager.deleteKursTyp(Integer.parseInt(request.getParameter("radera"))))
    			modelMap.put("error", "Radering ej tillåten, kurstypen anvŠnds!");
    		modelMap.put("kurstyper", this.kursTypManager.getKursTypList());
            return new ModelAndView("WEB-INF/jsp/kurstyper.jsp", "modelMap", modelMap);
    	}
    	kursTyp = null;
        modelMap.put("kurstyper", this.kursTypManager.getKursTypList());
        return new ModelAndView("WEB-INF/jsp/kurstyper.jsp", "modelMap", modelMap);
        
    }
    public void setKursTypManager(KursTypManagerInterface kursTypManager) 
    {
        this.kursTypManager = kursTypManager;
    }
    public void setOrganisationManager(OrganisationManagerInterface organisationManager)
    {
    	this.organisationManager = organisationManager;
    }

}

