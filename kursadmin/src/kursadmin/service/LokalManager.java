package kursadmin.service;

import kursadmin.domain.Lokal;
import kursadmin.repository.LokalDao;

import java.io.Serializable;
import java.util.List;

public class LokalManager implements LokalManagerInterface
{
	private LokalDao lokalDao;
	
	public Lokal getLokal(int lid)
	{
		return lokalDao.getLokal(lid);
	}
	
	public List<Lokal> getLokaler() 
    {
        return lokalDao.getLokalList();
    }
		
	public void deleteLokal(int lid)
	{
		lokalDao.deleteLokal(lid);
	}
	public void updateLokal(Lokal lokal)
	{
		lokalDao.updateLokal(lokal);
	}	
	public void insertLokal(Lokal lokal)
	{
		lokalDao.insertLokal(lokal);
	}
	
	public void setLokalDao(LokalDao lokalDao)
	{
		this.lokalDao = lokalDao;
	}
}
