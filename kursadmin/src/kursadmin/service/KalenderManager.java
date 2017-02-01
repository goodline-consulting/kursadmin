package kursadmin.service;

import kursadmin.domain.Kalender;
import kursadmin.repository.KalenderDao;
import java.util.List;

public class KalenderManager implements KalenderManagerInterface
{
	
	private static final long serialVersionUID = 6463882659644223889L;
	private KalenderDao kalenderDao;
	
	public Kalender getKalender(int cid)
	{
		return kalenderDao.getKalender(cid);
	}
	
	public List<Kalender> getKalender()
	{
		return kalenderDao.getKalender();
	}
	    
	public void deleteKalender(int cid)
	{
		kalenderDao.deleteKalender(cid);
	}
	
	public void updateKalender(Kalender kalender)
	{
		kalenderDao.updateKalender(kalender);
	}
	
	public void insertKalender(Kalender kalender)
	{
		kalenderDao.insertKalender(kalender);
	}
	
	public void setKalenderDao(KalenderDao kalenderDao)
	{
		this.kalenderDao = kalenderDao;
	}
}
