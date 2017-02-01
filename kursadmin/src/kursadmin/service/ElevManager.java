package kursadmin.service;

import kursadmin.domain.Elev;
import kursadmin.repository.ElevDao;
import java.util.List;

public class ElevManager implements ElevManagerInterface
{	
	private static final long serialVersionUID = -5551079742775053399L;
	private ElevDao elevDao;
	
	public Elev getElev(int kid, int pid)
	{
		return elevDao.getElev(kid, pid);
	}
	public int getAntElever(int kurstyp)
	{
		return elevDao.getAntElever(kurstyp);
	}
	public Elev getElev(int referens)
	{
		return elevDao.getElev(referens);
	}
	public boolean elevExists(int kid, int pid)
	{
		return elevDao.elevExists(kid, pid);
	}
	public boolean elevExists(int referens)
	{
		return elevDao.elevExists(referens);
	}
	public List<Elev> getElever(int kid)
	{
		return elevDao.getElevList(kid);
	}
	public List<Elev> getEleverPaFaktura(int fakturanr)
	{
		return elevDao.getEleverPaFaktura(fakturanr);
	}

	public void deleteElev(int kid, int pid)
	{
		elevDao.deleteElev(kid, pid);
	}
	public void updateElev(Elev elev)
	{
		elevDao.updateElev(elev);
	}
	public void insertElev(Elev elev)
	{
		elevDao.insertElev(elev);
	}
	public void flyttaElev(int pid, int fromKid, int toKid)
	{
		elevDao.flyttaElev(pid, fromKid, toKid);
	}
	public void setElevDao(ElevDao elevDao)
	{
		this.elevDao = elevDao;
	}
	
}
