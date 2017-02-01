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

import java.io.Serializable;
import java.util.List;

public interface ConfigManagerInterface extends Serializable
{
	
	public MailConfig getMailConfig(String mailtyp, int kurstyp);	
	public void setMailConfig (String type, int kurstyp, MailConfig mc);
	public PathConfig getPathConfig();
	public void setPathConfig(PathConfig pc);
	public GrundConfig getGrundConfig();
	public void setGrundConfig(GrundConfig pc);
	public MailSetup getMailSetup();
	public void setMailSetup(MailSetup ms);
	public TackConfig getTackConfig(int kurstyp);
	public void setTackConfig(int kurstyp, TackConfig tf);
	public List<Config> getConfigList (String type);
	public void setConfigList(List<Config> lista);
	public void insertConfig(Config conf);
    public void deleteConfig(Config conf);
    public void updateConfig(Config conf);
    
}    










    
    
    
    

