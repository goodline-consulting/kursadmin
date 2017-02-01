package kursadmin.service;

import kursadmin.domain.Betalning;
import kursadmin.domain.Person;
import kursadmin.repository.BetalningDao;
import kursadmin.repository.PersonDao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PersonManager implements PersonManagerInterface
{
	private PersonDao personDao;
	private BetalningDao betalningDao;
	
	public Person getPerson(int pid)
	{
		return personDao.getPerson(pid);
	}
	public List<Person> getPersonsObet()
	{
		return personDao.getPersonsObet();
	}
	public List<Person> getPersonsOfakt()
	{
		return personDao.getPersonsOfakt();
	}
	public List<Person> getPersons() 
    {
        return personDao.getPersonList();       
    }
	public void betalaSaldo(Person person)
	{		
		Betalning betalning = new Betalning();
		betalning.setName(person.getFnamn() + " " + person.getEnamn());
		betalning.setBetalkanal("Kundsaldo");
		betalning.setAmount(person.getSaldo());		
		betalning.setInfo(new StringBuffer("Kundsaldo lagt fšr utbetalning"));
		betalning.setBetaldatum(new Date());   
		betalning.setPlacerat(0);
		betalning.setPlacerad(false);
		betalning.setPid(person.getPid());
		betalning.setFakturanr(0);
		betalningDao.insertBetalning(betalning);
		person.setSaldo(0);
	}
	public void flyttaSaldo(Person personFrom, int pidTo)
	{				
		Person personTo   = personDao.getPerson(pidTo);
		Betalning betalning = new Betalning();
		betalning.setName(personFrom.getFnamn() + " " + personFrom.getEnamn() + "(KUND " + personFrom.getPid() + ")");
		betalning.setBetalkanal("Kundsaldo");
		betalning.setAmount(personFrom.getSaldo());		
		betalning.setInfo(new StringBuffer("Saldo flyttat till " + personTo.getFnamn() + " " + personTo.getEnamn()));
		betalning.setBetaldatum(new Date());   
		betalning.setPlacerat(betalning.getAmount());
		betalning.setPlacerad(true);
		betalning.setPid(personTo.getPid());
		betalning.setFakturanr(0);
		betalningDao.insertBetalning(betalning);
		personFrom.setSaldo(0);
		personTo.setSaldo(personTo.getSaldo() + betalning.getAmount());
		personDao.updatePerson(personTo);
	}
	public List<Person> getPersons(String pattern, boolean isEnamn)
	{
		return personDao.getPersonList(pattern, isEnamn);
	}
	public void deletePerson(int pid)
	{
		personDao.deletePerson(pid);
	}
	public void updatePerson(Person person)
	{
		personDao.updatePerson(person);
	}
	public void updatePerson(Person person, int kid)
	{
		personDao.updatePerson(person, kid);
	}
	public void insertPerson(Person person)
	{
		personDao.insertPerson(person);
	}
	public void insertPerson(Person person, int kid)
	{
		personDao.insertPerson(person, kid);
	}
	public void setPersonDao(PersonDao personDao)
	{
		this.personDao = personDao;
	}
	public void setBetalningDao(BetalningDao betalningDao)
	{
		this.betalningDao = betalningDao;
	}
	public int personHarOfakturerat(int pid, int kid)
	{
		return personDao.personHarOfakturerat(pid, kid);
	}
	
}
