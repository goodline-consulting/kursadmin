package kursadmin.service;

import kursadmin.domain.Nvaro;
import kursadmin.repository.NvaroDao;
import java.io.Serializable;
import java.util.List;


public class NvaroManager implements NvaroManagerInterface
{	
	private NvaroDao nvaroDao;
	
	public List<Nvaro> getNvaro(int kid)
	{
		return nvaroDao.getNvaroList(kid);
	}
	public void setNvaro(int kid, List<Nvaro> lista)
	{
		nvaroDao.setNvaro(kid, lista);
	}
	public void setNvaroDao(NvaroDao nvaroDao)
	{
		this.nvaroDao = nvaroDao;
	}
	public int getAntalNarvarande(int kid, int kurstillf)
	{
		return nvaroDao.getAntalNarvarande(kid, kurstillf);
	}
}










    
    
    
    

