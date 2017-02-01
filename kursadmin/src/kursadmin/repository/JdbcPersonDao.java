package kursadmin.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import kursadmin.domain.Person; 

public class JdbcPersonDao extends SimpleJdbcDaoSupport implements PersonDao 
{

	protected final Log logger = LogFactory.getLog(getClass());
	private void keepAlive()
	{
		try
        {
        	getSimpleJdbcTemplate().queryForInt("select 1"); 
                                       	
        }
        catch (DataAccessResourceFailureException e)
        {
        	return;
        }		
	}
	
    public List<Person> getPersonList() 
    {
    	keepAlive();
        List<Person> persons = getSimpleJdbcTemplate().
                               query("select * from person order by enamn", 
                               new PersonMapper());
        return persons;
    }
    public List<Person> getPersonList(String pattern, boolean isEnamn)
    {
    	keepAlive();
    	String query;
    	if (isEnamn)
    		query = "select * from person where enamn like '" + pattern + "%' order by enamn";
    	else
    		query = "select * from person where fnamn like '" + pattern + "%' order by enamn";
        List<Person> persons = getSimpleJdbcTemplate().
                               query(query, 
                               new PersonMapper());
        return persons;
    }

    public List<Person> getPersonList(String fnamn, String enamn)
    {
    	keepAlive();
    	String query;
    	query = "select * from person where fnamn = '" + fnamn + "' and enamn = '" + enamn + "'";
        List<Person> persons = getSimpleJdbcTemplate().
                               query(query, 
                               new PersonMapper());
        return persons;
    }
    public List<Person> getPersonsObet()
    {
    	keepAlive();
    	String query;
    	query = "select * from person where person.pid in (select pid from faktura where faktura.betald is null)";
        List<Person> persons = getSimpleJdbcTemplate().
                               query(query, 
                               new PersonMapper());
        return persons;
    }
	
	public List<Person> getPersonsOfakt()
	{
    	keepAlive();
    	String query;
    	query = "select * from person where person.pid in (select pid from elev where fakturanr = 0)";
        List<Person> persons = getSimpleJdbcTemplate().
                               query(query, 
                               new PersonMapper());
        return persons;
    }
	
    public Person getPerson(int pid)
    {
    	String 	query = "select * from person where pid = " + pid;
    	keepAlive();
    	List<Person> persons = getSimpleJdbcTemplate().query(query, new PersonMapper());
    	if (persons.size() > 0)
    		return persons.get(0);
    	else
    		return null;
        
    }
 
    public void updatePerson(Person person) 
    {
    	keepAlive();
        getSimpleJdbcTemplate().update(
            "update person set adress = :adress, postadress = :postadress, email = :email, enamn = :enamn, fnamn = :fnamn, info = :info, mobil = :mobil, postnr = :postnr, telefon = :telefon, inskriven = :inskriven, saldo = :saldo where pid = :pid",
            new MapSqlParameterSource().addValue("postadress", person.getPostadress())
            						   .addValue("pid", person.getPid()) 
            						   .addValue("adress", person.getAdress())
                                       .addValue("email", person.getEmail())
                                       .addValue("enamn", person.getEnamn())
                                       .addValue("fnamn", person.getFnamn())
                                       .addValue("info", person.getInfo())
                                       .addValue("mobil", person.getMobil())
                                       .addValue("postnr", person.getPostnr())
                                       .addValue("telefon", person.getTelefon())
                                       .addValue("inskriven", person.getInskriven())
                                       .addValue("saldo", person.getSaldo()));	
    }
    public void updatePerson(Person person, int kid)
    {
    	keepAlive();
    	updatePerson(person);
    	int i = getSimpleJdbcTemplate().queryForInt("select exists(select pid from elev where kid = " + kid + " and pid = " + person.getPid() + ")");
    	if (i == 0)
    	{	
    		i = getSimpleJdbcTemplate().queryForInt("select pris from kurs where kid = " + kid);
	    	getSimpleJdbcTemplate().update(
	    			"insert into elev(pid, kid, betalt, aktiv, abekr, bbekr, info, pris) values(:pid, :kid, 0, true, false, false, '', :pris)",
	                new MapSqlParameterSource().addValue("kid", kid)
	                						   .addValue("pris", i)	
	                						   .addValue("pid", person.getPid()));
    	}	
    }
    public void deletePerson(int pid)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update("delete from nvaro where pid = ?", pid);
    	getSimpleJdbcTemplate().update("delete from elev where pid = ?", pid);
    	getSimpleJdbcTemplate().update("delete from person where pid = ?", pid);
    	getSimpleJdbcTemplate().update("delete from betalning where betalning.fakturanr in (select fakturanr from faktura where faktura.pid = ?)", pid);
    	getSimpleJdbcTemplate().update("delete from faktura where pid = ?", pid);
    }
    
    public int insertPerson(Person person)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update(
                "insert into person (adress, postadress, email, enamn, fnamn, info, mobil, postnr, telefon, saldo) values(:adress, :postadress, :email, :enamn, :fnamn, :info, :mobil, :postnr, :telefon, :saldo)",
                new MapSqlParameterSource().addValue("adress", person.getAdress())
                                           .addValue("postadress", person.getPostadress())
                                           .addValue("email", person.getEmail())
                                           .addValue("enamn", person.getEnamn())
                                           .addValue("fnamn", person.getFnamn())
                                           .addValue("info", person.getInfo())
                                           .addValue("mobil", person.getMobil())
                                           .addValue("postnr", person.getPostnr())
                                           .addValue("telefon", person.getTelefon())
                                           .addValue("saldo", person.getSaldo()));
    	return getSimpleJdbcTemplate().queryForInt("select last_insert_id()"); 
    }
    public void insertPerson(Person person, int kid)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update(
                "insert into person (adress, postadress, email, enamn, fnamn, info, mobil, postnr, telefon, saldo) values(:adress, :postadress, :email, :enamn, :fnamn, :info, :mobil, :postnr, :telefon, :saldo)",
                new MapSqlParameterSource().addValue("adress", person.getAdress())
                                           .addValue("postadress", person.getPostadress())
                                           .addValue("email", person.getEmail())
                                           .addValue("enamn", person.getEnamn())
                                           .addValue("fnamn", person.getFnamn())
                                           .addValue("info", person.getInfo())
                                           .addValue("mobil", person.getMobil())
                                           .addValue("postnr", person.getPostnr())
                                           .addValue("telefon", person.getTelefon())
                                           .addValue("saldo", person.getSaldo()));
    	getSimpleJdbcTemplate().update(
    			"insert into elev(pid, kid, betalt, aktiv, abekr, bbekr, info, pris, fakturanr) values(last_insert_id(), :kid, 0, true, false, false, '', (select pris from kurs where kid = :kid), 0)",
                new MapSqlParameterSource().addValue("kid", kid));
                                           
    }
    public int personHarOfakturerat(int pid, int kid)
    {
    	keepAlive();
    	return  getSimpleJdbcTemplate().queryForInt("select count(kid) from elev where  pid = " + pid + " and fakturanr = 0");  	
    }
    private static class PersonMapper implements ParameterizedRowMapper<Person> 
    {

        public Person mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
            Person person = new Person();
            person.setPid(rs.getInt("pid"));
            person.setAdress(rs.getString("adress"));
            person.setPostadress(rs.getString("postadress"));
            person.setEmail(rs.getString("email"));
            person.setEnamn(rs.getString("enamn"));
            person.setFnamn(rs.getString("fnamn"));
            person.setInfo(rs.getString("info"));
            person.setMobil(rs.getString("mobil"));
            person.setPostnr(rs.getString("postnr"));
            person.setTelefon(rs.getString("telefon"));
            person.setInskriven(rs.getTimestamp("inskriven"));
            person.setSaldo(rs.getDouble("saldo"));
            return person;
        }
    }
}
