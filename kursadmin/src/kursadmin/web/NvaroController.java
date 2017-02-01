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
import kursadmin.service.ElevManagerInterface;
import kursadmin.service.KursManagerInterface;
import kursadmin.service.NvaroManagerInterface;
import kursadmin.repository.KursTillfDao;
import kursadmin.domain.Elev;
import kursadmin.domain.KursTillf;
import kursadmin.domain.Nvaro;

public class NvaroController implements Controller 
{

    protected final Log logger = LogFactory.getLog(getClass());
    private ElevManagerInterface elevManager;
    private NvaroManagerInterface nvaroManager;
    private KursTillfDao kursTillfDao;
    private KursManagerInterface kursManager;
    private ArrayList<Elev> elever;
    private List<Nvaro> nvaroList;
    private List<KursTillf> kursTillf;
    private boolean fromHome = false;
    private int kid = 0;
    private String bet;
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {   
    	Map<String, Object> modelMap = new HashMap<String, Object>();   
    	
    	
    	if (request.getParameter("_action") != null && request.getParameter("_action").equals("spara"))
    	{
    		boolean update = false;
    		nvaroList = new ArrayList<Nvaro>();
    		for (Enumeration<String> pars = request.getParameterNames(); pars.hasMoreElements();)
    		{	
    			String par = pars.nextElement();
    			if (par.startsWith("T"))
    			{	
    				//logger.info("LŠgger till " + par + " " + request.getParameter(par));
    				Nvaro nvaro = new Nvaro();
    				nvaro.setKid(kid);
    				nvaro.setPid(Integer.parseInt(request.getParameter(par)));
    				nvaro.setSeq(Integer.parseInt(par.substring(1, par.indexOf(':'))));
    				nvaroList.add(nvaro);
    			}	
    		}	
    		nvaroManager.setNvaro(kid, nvaroList);
    	    // logger.info("From home:" + fromHome);
    		if (fromHome)
    			return new ModelAndView(new RedirectView("teachers.htm"));
    		else
    			return new ModelAndView(new RedirectView("kurser.htm"));
    	}
    	if (request.getParameter("_action") != null && request.getParameter("_action").equals("backa"))
    	{
    		if (fromHome)  // "WEB-INF/jsp/goaway.jsp"
    			return new ModelAndView(new RedirectView("teachers.htm"));
    		else
    			return new ModelAndView(new RedirectView("kurser.htm"));
    	}	    	  	    	
    	if (request.getParameter("kid") != null)
    	{
    		kid = Integer.parseInt(request.getParameter("kid"));
    		//bet = request.getParameter("bet");
    		elever = new ArrayList<Elev>();
    		for (Elev elev : this.elevManager.getElever(kid))
    			if (elev.isAktiv())
    				elever.add(elev);
    		
    		nvaroList = this.nvaroManager.getNvaro(kid);
    	    kursTillf = this.kursTillfDao.getKursTillfList(kid);
    	    setNvaro();
    	    if (request.getParameter("homepage") != null)
    	    	fromHome = true;
    	    else 
    	    	fromHome = false;
    	}	
    	// logger.info("KID:" + kid);
    	modelMap.put("kursAll", kursManager.getKursAll(kid));
    	modelMap.put("beteckning", bet);
    	modelMap.put("elever", elever);   
    	modelMap.put("antal", elever.size());
    	modelMap.put("kursTillf", kursTillf);
        return new ModelAndView("WEB-INF/jsp/nvaro.jsp", "modelMap", modelMap);        
    }
    
    private void setNvaro()
    {    	
    	for (Elev elev: elever)
    	{
    		boolean nvArr[] = new boolean[kursTillf.size()]; 

    		for (Nvaro nvaro: nvaroList)
    		{
    			if (nvaro.getPid() == elev.getPid())
    			{
    				nvArr[nvaro.getSeq() - 1] = true;
    			}   			
    		}
    		elev.setNvaro(nvArr);
    	}
    }
    
    public void setElevManager(ElevManagerInterface elevManager) 
    {
        this.elevManager = elevManager;
    }
    public void setNvaroManager(NvaroManagerInterface nvaroManager) 
    {
        this.nvaroManager = nvaroManager;
    }
    public void setKursTillfDao(KursTillfDao kursTillfDao) 
    {
        this.kursTillfDao = kursTillfDao;
    }
    public void setKursManager(KursManagerInterface kursManager)
    {
    	this.kursManager = kursManager;
    }
}

