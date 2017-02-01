package kursadmin.service;

import kursadmin.domain.Kalender;
import java.io.Serializable;
import java.util.List;

public interface KalenderManagerInterface extends Serializable
{
	public Kalender getKalender(int cid);
	
	public List<Kalender> getKalender();
	    
	public void deleteKalender(int cid);
	
	public void updateKalender(Kalender kalender);
	
	public void insertKalender(Kalender kalender);
}










    
    
    
    

