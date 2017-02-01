package kursadmin.web;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import kursadmin.service.ElevManagerInterface;
import kursadmin.service.KursManagerInterface;
import kursadmin.service.PersonManagerInterface;

public class TeachersController implements Controller 
{

    protected final Log logger = LogFactory.getLog(getClass());
    private PersonManagerInterface personManager;
    private KursManagerInterface kursManager;
    ElevManagerInterface elevManager;
    
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {   
    	Map<String, Object> modelMap = new HashMap<String, Object>();  
    	modelMap.put("arsta", this.kursManager.getKurser("201%AR", "", 7));
    	modelMap.put("pumpan", this.kursManager.getKurser("201%PU", "", 7));
    	modelMap.put("sumpan", this.kursManager.getKurser("201%SB", "", 7));
    	modelMap.put("alta", this.kursManager.getKurser("201%AL", "", 7));
    	//modelMap.put("orminge", this.kursManager.getKurser("201%NA", "", 7));
    	//modelMap.put("hagersten", this.kursManager.getKurser("201%AS", "", 7));
    	modelMap.put("gubben", this.kursManager.getKurser("201%GB", "", 7));
    	//modelMap.put("skondal", this.kursManager.getKurser("201%SK", "", 7));
    	modelMap.put("antElever", new Integer(elevManager.getAntElever(1)));
        return new ModelAndView("WEB-INF/jsp/teachersmain.jsp", "modelMap", modelMap);
        
    }
    public void setPersonManager(PersonManagerInterface personManager) 
    {
        this.personManager = personManager;
    }
    public void setKursManager(KursManagerInterface kursManager) 
    {
        this.kursManager = kursManager;
    }
    public void setElevManager(ElevManagerInterface elevManager) 
    {
        this.elevManager = elevManager;
    }
}

