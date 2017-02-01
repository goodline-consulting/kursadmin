package kursadmin.service;

import kursadmin.domain.Anvandare;
import kursadmin.repository.AnvandareDao;

import java.io.Serializable;
import java.util.List;

public class AnvandareManager implements AnvandareManagerInterface
{
/*
 * 
 public Anvandare getAnvandare(String namn);
	
	public List<Anvandare> getAnvandare();
    
	public void deleteAnvandare(String namn);
	
	public void insertAnvandare(Anvandare anvandare);
 * 
 * 
 */
	private AnvandareDao anvandareDao;
	
	public Anvandare getAnvandare(String namn)
	{
		return anvandareDao.getAnvandare(namn);
	}
	
	public List<Anvandare> getAnvandareList() 
    {
        return anvandareDao.getAnvandareList();
    }
		
	public void deleteAnvandare(String namn)
	{
		anvandareDao.deleteAnvandare(namn);
	}
	
	public void insertAnvandare(Anvandare anvandare)
	{
		anvandareDao.insertAnvandare(anvandare);
	}
	
	public void updateAnvandare(Anvandare anvandare)
	{
		anvandareDao.updateAnvandare(anvandare);
	}
	
	public void setAnvandareDao(AnvandareDao anvandareDao)
	{
		this.anvandareDao = anvandareDao;
	}
}
