package kursadmin.service;

import kursadmin.domain.Config;
import kursadmin.domain.Elev;
import kursadmin.domain.Faktura;
import kursadmin.domain.GrundConfig;
import kursadmin.domain.KursAll;
import kursadmin.domain.MailConfig;
import kursadmin.domain.MailSetup;
import kursadmin.domain.PathConfig;
import kursadmin.domain.TackConfig;
import kursadmin.repository.ConfigDao;
import java.util.ArrayList;
import java.util.List;
public class ConfigManager implements ConfigManagerInterface
{
	private static final long serialVersionUID = 1110458746499785780L;
	private ConfigDao configDao;
	
	public MailConfig getMailConfig(String mailtyp, int kurstyp)
	{
		List<Config> lista = configDao.getConfigList(mailtyp + "_" + kurstyp);
		MailConfig mc = new MailConfig();
		for (Config cf: lista)
		{
			if (cf.getNamn().equals("rubrik"))
				mc.setRubrik(cf.getVarde());
			else if (cf.getNamn().equals("body"))
				mc.setBody(cf.getVarde());
			else if (cf.getNamn().equals("footer"))
				mc.setFooter(cf.getVarde());
			else if (cf.getNamn().equals("ingress"))
				mc.setIngress(cf.getVarde());
			else if (cf.getNamn().equals("finish"))
				mc.setFinish(cf.getVarde());
		}
		return mc;
	}
	
	public void setMailConfig (String type, int kurstyp, MailConfig mc)
	{
		Config cf;
		List<Config> lista = new ArrayList<Config>();
		cf = new Config(type + "_" + kurstyp, "rubrik", mc.getRubrik());
		lista.add(cf);
		cf = new Config(type + "_" + kurstyp, "body", mc.getBody());
		lista.add(cf);
		cf = new Config(type + "_" + kurstyp, "footer", mc.getFooter());
		lista.add(cf);
		cf = new Config(type + "_" + kurstyp, "ingress", mc.getIngress());
		lista.add(cf);
		cf = new Config(type + "_" + kurstyp, "finish", mc.getFinish());
		lista.add(cf);
		configDao.insertConfigList(lista);
	}
	public GrundConfig getGrundConfig()
	{
		List<Config> lista = configDao.getConfigList("grund");
		GrundConfig gc = GrundConfig.getGrundConfig();
		for (Config cf: lista)
		{
			if (cf.getNamn().equals("anmActionUrl"))
				gc.setAnmActionUrl(cf.getVarde());
			else if (cf.getNamn().equals("anmalanMail"))
				gc.setAnmalanMail(cf.getVarde());
			else if (cf.getNamn().equals("betalMail"))
				gc.setBetalMail(cf.getVarde());	
			else if (cf.getNamn().equals("fakturaMail"))
				gc.setFakturaMail(cf.getVarde());	
			else if (cf.getNamn().equals("fakeMail"))
				gc.setFakeMail(Boolean.parseBoolean(cf.getVarde()));
			else if (cf.getNamn().equals("logga"))
				gc.setLogga(cf.getVarde());
			else if (cf.getNamn().equals("style"))
				gc.setStyle(cf.getVarde());
			else if (cf.getNamn().equals("loggaH"))
				gc.setLoggaH(Integer.parseInt(cf.getVarde()));
			else if (cf.getNamn().equals("loggaW"))
				gc.setLoggaW(Integer.parseInt(cf.getVarde()));
			
		}
		return gc;		
	}
	
	public void setGrundConfig(GrundConfig gc)
	{
		Config cf;
		List<Config> lista = new ArrayList<Config>();
		cf = new Config("grund", "anmActionUrl", gc.getAnmActionUrl());
		lista.add(cf);
		cf = new Config("grund", "anmalanMail", gc.getAnmalanMail());
		lista.add(cf);
		cf = new Config("grund", "betalMail", gc.getBetalMail());
		lista.add(cf);
		cf = new Config("grund", "fakturaMail", gc.getFakturaMail());
		lista.add(cf);
		cf = new Config("grund", "fakeMail", "" + gc.isFakeMail());
		lista.add(cf);
		cf = new Config("grund", "logga", gc.getLogga());
		lista.add(cf);
		cf = new Config("grund", "loggaH", "" + gc.getLoggaH());
		lista.add(cf);
		cf = new Config("grund", "loggaW", "" + gc.getLoggaW());
		lista.add(cf);
		cf = new Config("grund", "style", gc.getStyle());
		lista.add(cf);		
		configDao.insertConfigList(lista);
	}
	
	public PathConfig getPathConfig()
	{
		List<Config> lista = configDao.getConfigList("path");
		PathConfig pc = new PathConfig();
		for (Config cf: lista)
		{
			if (cf.getNamn().equals("bilagor"))
				pc.setBilagor(cf.getVarde());
			else if (cf.getNamn().equals("bilder"))
				pc.setBilder(cf.getVarde());
			else if (cf.getNamn().equals("programpath"))
				pc.setProgrampath(cf.getVarde());	
			else if (cf.getNamn().equals("programurl"))
				pc.setProgramurl(cf.getVarde());	
			else if (cf.getNamn().equals("programpunkter"))
				pc.setProgrampunkter(cf.getVarde());
		}
		return pc;		
	}
	
	public void setPathConfig(PathConfig pc)
	{
		Config cf;
		List<Config> lista = new ArrayList<Config>();
		cf = new Config("path", "bilagor", pc.getBilagor());
		lista.add(cf);
		cf = new Config("path", "bilder", pc.getBilder());
		lista.add(cf);
		cf = new Config("path", "programpath", pc.getProgrampath());
		lista.add(cf);
		cf = new Config("path", "programurl", pc.getProgramurl());
		lista.add(cf);
		cf = new Config("path", "programpunkter", pc.getProgrampunkter());
		lista.add(cf);
		configDao.insertConfigList(lista);
	}
	public MailSetup getMailSetup()
	{
		List<Config> lista = configDao.getConfigList("mail");
		MailSetup ms = new MailSetup();
		for (Config cf: lista)
		{
			if (cf.getNamn().equals("footer"))
				ms.setFooter(cf.getVarde());
			else if (cf.getNamn().equals("sender"))
				ms.setSender(cf.getVarde());
//			else if (cf.getNamn().equals("fakturaMail"))
//				ms.setFakturaMail(cf.getVarde());
//			else if (cf.getNamn().equals("anmalanMail"))
//				ms.setAnmalanMail(cf.getVarde());
						
		}
		return ms;		
	}
	
	public void setMailSetup(MailSetup ms)
	{
		Config cf;
		List<Config> lista = new ArrayList<Config>();
		cf = new Config("mail", "footer", ms.getFooter());
		lista.add(cf);
		cf = new Config("mail", "sender", ms.getSender());
		lista.add(cf);		
//		cf = new Config("mail", "fakturaMail", ms.getFakturaMail());
//		lista.add(cf);		
//		cf = new Config("mail", "anmalanMail", ms.getAnmalanMail());
//		lista.add(cf);		
		configDao.insertConfigList(lista);
	}

	public TackConfig getTackConfig(int kurstyp)
	{
		List<Config> lista = configDao.getConfigList("tack_" + kurstyp);
		TackConfig tc = new TackConfig();
		for (Config cf: lista)
		{
			if (cf.getNamn().equals("header"))
				tc.setHeader(cf.getVarde());
			else if (cf.getNamn().equals("body"))
				tc.setBody(cf.getVarde());
			else if (cf.getNamn().equals("footer"))
				tc.setFooter(cf.getVarde());
			else if (cf.getNamn().equals("actionUrl"))
				tc.setActionUrl(cf.getVarde());
			else if (cf.getNamn().equals("logga"))
				tc.setLogga(cf.getVarde());
			else if (cf.getNamn().equals("style"))
				tc.setStyle(cf.getVarde());
			else if (cf.getNamn().equals("loggaH"))
				tc.setLoggaH(Integer.parseInt(cf.getVarde()));
			else if (cf.getNamn().equals("loggaW"))
				tc.setLoggaW(Integer.parseInt(cf.getVarde()));
		}
		return tc;
	}
	public void setTackConfig(int kurstyp, TackConfig tc)
	{
		Config cf;
		List<Config> lista = new ArrayList<Config>();
		cf = new Config("tack_" + kurstyp, "header", tc.getHeader());
		lista.add(cf);
		cf = new Config("tack_" + kurstyp, "body", tc.getBody());
		lista.add(cf);
		cf = new Config("tack_" + kurstyp, "footer", tc.getFooter());
		lista.add(cf);	
		cf = new Config("tack_" + kurstyp, "actionUrl", tc.getActionUrl());
		lista.add(cf);		
		cf = new Config("tack_" + kurstyp, "logga", tc.getLogga());
		lista.add(cf);		
		cf = new Config("tack_" + kurstyp, "style", tc.getStyle());
		lista.add(cf);
		cf = new Config("tack_" + kurstyp, "loggaH", "" + tc.getLoggaH());
		lista.add(cf);
		cf = new Config("tack_" + kurstyp, "loggaW", "" + tc.getLoggaW());
		lista.add(cf);
		configDao.insertConfigList(lista);
	}
			
	public List<Config> getConfigList (String type)
	{
		return configDao.getConfigList(type);
		
	}
	public void setConfigList(List<Config> lista)
	{
		configDao.insertConfigList(lista);
	}
	public void insertConfig(Config conf)
	{
		configDao.insertConfig(conf);
	}
    public void deleteConfig(Config conf)
    {
    	configDao.deleteConfig(conf);
    }
    public void updateConfig(Config conf)
    {
    	configDao.updateConfig(conf);
    }
    public void setConfigDao(ConfigDao configDao)
    {
    	this.configDao = configDao; 
    }      
}   











    
    
    
    

