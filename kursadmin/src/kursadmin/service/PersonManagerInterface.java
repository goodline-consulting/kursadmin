package kursadmin.service;

import kursadmin.domain.Person;
import java.io.Serializable;
import java.util.List;

public interface PersonManagerInterface extends Serializable
{
	public Person getPerson(int pid);
	
	public List<Person> getPersons();
	
	public List<Person> getPersonsObet();
	
	public List<Person> getPersonsOfakt();
    
	public List<Person> getPersons(String pattern, boolean isEnamn);
	
	public void deletePerson(int pid);
	
	public void updatePerson(Person person);
	
	public void updatePerson(Person person, int kid);
	
	public void insertPerson(Person person);
	
	public void insertPerson(Person person, int kid);
	
	public int personHarOfakturerat(int pid, int kid);
	
	public void betalaSaldo(Person person);
	
	public void flyttaSaldo(Person personFrom, int pidTo);
}










    
    
    
    

