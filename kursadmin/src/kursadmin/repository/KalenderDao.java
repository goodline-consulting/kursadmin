package kursadmin.repository;
import java.util.List;

import kursadmin.domain.Kalender;


public interface KalenderDao 
{
	public Kalender getKalender(int cid);
	
	public List<Kalender> getKalender();
	    
	public void deleteKalender(int cid);
	
	public void updateKalender(Kalender kalender);
	
	public void insertKalender(Kalender kalender);
}













