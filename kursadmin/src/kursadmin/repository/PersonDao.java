package kursadmin.repository;
import java.util.List;
import kursadmin.domain.Person;

public interface PersonDao 
{
    public List<Person> getPersonList();
    public List<Person> getPersonList(String pattern, boolean isEnamn);
    public List<Person> getPersonList(String fnamn, String enamn);
    public List<Person> getPersonsObet();	
	public List<Person> getPersonsOfakt();
    public Person getPerson(int pid);
    public void updatePerson(Person person);
    public void updatePerson(Person person, int kid);
    public int insertPerson(Person person);
    public void insertPerson(Person person, int kid);
    public void deletePerson(int pid);
    public int personHarOfakturerat(int pid, int kid);
}













