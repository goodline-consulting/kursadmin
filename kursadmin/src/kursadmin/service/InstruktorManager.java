package kursadmin.service;

import kursadmin.domain.Instruktor;
import kursadmin.repository.InstruktorDao;

import java.io.Serializable;
import java.util.List;

public class InstruktorManager implements InstruktorManagerInterface
{
	private InstruktorDao instruktorDao;
	
	public Instruktor getInstruktor(int iid)
	{
		return instruktorDao.getInstruktor(iid);
	}
	
	public List<Instruktor> getInstruktors() 
    {
        return instruktorDao.getInstruktorList();
    }
		
	public void deleteInstruktor(int iid)
	{
		instruktorDao.deleteInstruktor(iid);
	}
	public void updateInstruktor(Instruktor instruktor)
	{
		instruktorDao.updateInstruktor(instruktor);
	}	
	public void insertInstruktor(Instruktor instruktor)
	{
		instruktorDao.insertInstruktor(instruktor);
	}
	
	public void setInstruktorDao(InstruktorDao instruktorDao)
	{
		this.instruktorDao = instruktorDao;
	}
}
