package kursadmin.service;

import kursadmin.domain.KursNiva;
import kursadmin.domain.KursTyp;
import kursadmin.repository.KursTypDao;
import java.util.List;

public class KursTypManager implements KursTypManagerInterface
{
	
	private static final long serialVersionUID = 8601372566010759216L;
	private KursTypDao kursTypDao;
	
	public List<KursTyp> getKursTypList()
	{
		return kursTypDao.getKursTypList();
	}
    
	public void insertKursTyp(KursTyp kursTyp, List<KursNiva> nivaer)
    {
    	kursTypDao.insertKursTyp(kursTyp, nivaer);
    }
	
	public KursTyp getKursTyp(int tid)
	{
		return kursTypDao.getKursTyp(tid);
	}
    
	public boolean deleteKursTyp(int tid)
	{
		return kursTypDao.deleteKursTyp(tid);
	}
    
	public void updateKursTyp(KursTyp kursTyp)
	{
		kursTypDao.updateKursTyp(kursTyp);
	}
    
    public List<KursNiva> getKursNivaList()
    {
    	return kursTypDao.getKursNivaList();
    }
    
    public List<KursNiva> getKursNivaList(int tid)
    {
    	return kursTypDao.getKursNivaList(tid);
    }
    
    public void insertKursNivaList(List<KursNiva> list, int tid)
    {
    	kursTypDao.insertKursNivaList(list, tid);
    }
    
    public void setKursTypDao(KursTypDao kursTypDao)
	{
		this.kursTypDao = kursTypDao;
	}
}










    
    
    
    

