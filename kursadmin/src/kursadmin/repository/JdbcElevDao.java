package kursadmin.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import kursadmin.domain.Elev;
import kursadmin.domain.Person;
 

public class JdbcElevDao extends SimpleJdbcDaoSupport implements ElevDao 
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
	public int getAntElever(int kurstyp)
	{
		keepAlive();
		return getSimpleJdbcTemplate().queryForInt("select count(pid) from elev where kid in (select kid from kurs where tid = " + kurstyp + " and status <> 1)");
	}
    public List<Elev> getElevList(int kid)
    {      
    	keepAlive();
        List<Elev> elever = getSimpleJdbcTemplate().query("select elev.*, person.fnamn, person.enamn, person.email, kurs.beteckning from elev inner join person on person.pid = elev.pid inner join kurs on kurs.kid = elev.kid where elev.kid = " +
                                                     kid + " order by person.enamn", new ElevMapper());
        return elever;
    }
    public List<Elev> getEleverPaFaktura(int fakturanr)
    {
    	keepAlive();
        return getSimpleJdbcTemplate().query("select elev.*, person.fnamn, person.enamn, person.email, kurs.beteckning from elev inner join person on person.pid = elev.pid inner join kurs on kurs.kid = elev.kid where elev.fakturanr = " +
                                                     fakturanr + " order by person.enamn", new ElevMapper());
       
    }
    public List<Elev> getEleverFromFaktura(int fakturanr)
    {      
    	keepAlive();
        List<Elev> lokaler = getSimpleJdbcTemplate().query("select elev.*, person.fnamn, person.enamn, person.email, kurs.beteckning from elev inner join person on person.pid = elev.pid inner join kurs on kurs.kid = elev.kid where fakturanr = " +
                                                     fakturanr + " order by person.enamn", new ElevMapper());
        return lokaler;
    }
    
    public Elev getElev(int kid, int pid)
    {
    	keepAlive();
    	
    	String 	query = "select elev.*, person.fnamn, person.enamn, person.email, kurs.beteckning from elev inner join person on person.pid = elev.pid inner join kurs on kurs.kid = elev.kid where elev.kid = " +
    	                kid + " and elev.pid = " + pid;
    	List<Elev> elever =  getSimpleJdbcTemplate().query(query, new ElevMapper());
    	if (elever.size() == 0)
    		return null;    	  
        return elever.get(0); 
        
    }
    public Elev getElev(int id)
    {
    	keepAlive();
    	
    	String 	query = "select elev.*, person.fnamn, person.enamn, person.email, kurs.beteckning from elev inner join person on person.pid = elev.pid inner join kurs on kurs.kid = elev.kid where id = " + id;                   	
    	Elev elev = getSimpleJdbcTemplate().query(query, new ElevMapper()).get(0);    	
        return elev; 
        
    }
    public boolean elevExists(int kid, int pid)
    {
    	keepAlive();
    	int i = getSimpleJdbcTemplate().queryForInt("select exists(select pid from elev where kid = " + kid + " and pid = " + pid + ")");
    	if (i == 0)       	
    		return false;
    	else
    		return true;        
    }    
    public boolean elevExists(int id)
    {
    	keepAlive();
    	int i = getSimpleJdbcTemplate().queryForInt("select exists(select referens from elev where id = " + id + ")");
    	if (i == 0)       	
    		return false;
    	else
    		return true;        
    }  
    public void updateElev(Elev elev) 
    {    	
    	keepAlive();
        getSimpleJdbcTemplate().update(
            "update elev set betalt = :betalt, pris = :pris, abekr = :abekr, bbekr = :bbekr, info = :info, aktiv = :aktiv, anmald = :anmald, betaldatum = :betaldatum, fakturanr = :fakturanr, manpris = :manpris, rum = :rum, logi = :logi, mat = :mat, resa = :resa where kid = :kid and pid = :pid",
            new MapSqlParameterSource().addValue("kid", elev.getKid())
            						   .addValue("pid", elev.getPid())
            						   .addValue("betalt", elev.getBetalt())
            						   .addValue("pris", elev.getPris())
            						   .addValue("abekr", elev.isAbekr())                                           
            						   .addValue("bbekr", elev.isBbekr())
            						   .addValue("aktiv", elev.isAktiv())
            						   .addValue("anmald", elev.getAnmald())
            						   .addValue("betaldatum", elev.getBetaldatum())
            						   .addValue("fakturanr", elev.getFakturanr())
            						   .addValue("info", elev.getInfo())
            						   .addValue("manpris", elev.isManpris())
            						   .addValue("logi", elev.getLogi())
            						   .addValue("mat", elev.getMat())
            						   .addValue("resa", elev.getResa())
            						   .addValue("rum", elev.getRum()));    
    }
        
    public void deleteElev(int kid, int pid)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update("delete from elev where kid = ? and pid = ?", kid, pid);
    }
    
    public void insertElev(Elev elev)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update(
                "insert into elev (kid, pid, betalt, pris, abekr, bbekr, info, aktiv, anmald, betaldatum, fakturanr, manpris, logi, mat, resa, rum) values(:kid, :pid, :betalt, :pris, :abekr, :bbekr, :info, :aktiv, :anmald, :betaldatum, :fakturanr, :manpris, :logi, :mat, :resa, :rum)",
                new MapSqlParameterSource().addValue("kid", elev.getKid())
                						   .addValue("pid", elev.getPid())
                                           .addValue("betalt", elev.getBetalt())
                                           .addValue("pris", elev.getPris())
                                           .addValue("abekr", elev.isAbekr())                                           
                                           .addValue("bbekr", elev.isBbekr())
                                           .addValue("aktiv", elev.isAktiv())
                                           .addValue("anmald", elev.getAnmald())
            						       .addValue("betaldatum", elev.getBetaldatum())
            						       .addValue("fakturanr", elev.getFakturanr())
                                           .addValue("info", elev.getInfo())
                                           .addValue("manpris", elev.isManpris())
                                           .addValue("logi", elev.getLogi())
                						   .addValue("mat", elev.getMat())
                						   .addValue("resa", elev.getResa())
                						   .addValue("rum", elev.getRum()));
    }
    public void insertElev(Elev elev, Person person)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update(
    			"insert into person (email, enamn, fnamn, telefon) values(:email, :enamn, :fnamn, :telefon)",
                new MapSqlParameterSource().addValue("email", person.getEmail())
                                           .addValue("enamn", person.getEnamn())
                                           .addValue("fnamn", person.getFnamn())
                                           .addValue("telefon", person.getTelefon()));
    	elev.setPid(getSimpleJdbcTemplate().queryForInt("select last_insert_id()"));
    	insertElev(elev);
    	/*
    	getSimpleJdbcTemplate().update(
                "insert into elev (kid, pid, betalt, pris, abekr, bbekr, info, aktiv, anmald, betaldatum, fakturanr, manpris) values(:kid, last_insert_id(), :betalt, :pris, :abekr, :bbekr, :info, :aktiv, :anmald, :betaldatum, :fakturanr, :manpris)",
                new MapSqlParameterSource().addValue("kid", elev.getKid())                						   
                                           .addValue("betalt", elev.getBetalt())
                                           .addValue("pris", elev.getPris())
                                           .addValue("abekr", elev.isAbekr())                                           
                                           .addValue("bbekr", elev.isBbekr())
                                           .addValue("aktiv", elev.isAktiv())
                                           .addValue("anmald", elev.getAnmald())
            						       .addValue("betaldatum", elev.getBetaldatum())
            						       .addValue("fakturanr", elev.getFakturanr())
                                           .addValue("info", elev.getInfo())
                                           .addValue("manpris", elev.isManpris()));
        */                                       
    }
    public void flyttaElev(int pid, int fromKid, int toKid)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update("update elev set kid = :toKid, anmald = :anmald where kid = :kid and pid = :pid",
                new MapSqlParameterSource().addValue("kid", fromKid)
                				           .addValue("pid", pid)
                				           .addValue("anmald", new Date())
                				           .addValue("toKid", toKid));
    	getSimpleJdbcTemplate().update("update nvaro set kid = :toKid where kid = :kid and pid = :pid",
                new MapSqlParameterSource().addValue("kid", fromKid)
                				           .addValue("pid", pid)
                				           .addValue("toKid", toKid));
                
    }
    private static class ElevMapper implements ParameterizedRowMapper<Elev> 
    {

        public Elev mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
            Elev elev = new Elev();
            elev.setAbekr(rs.getBoolean("abekr"));
            elev.setBbekr(rs.getBoolean("bbekr"));
            elev.setAktiv(rs.getBoolean("aktiv"));
            elev.setBetalt(rs.getInt("betalt"));
            elev.setPris(rs.getInt("pris"));
            elev.setInfo(rs.getString("info"));            
            elev.setKid(rs.getInt("kid"));
            elev.setPid(rs.getInt("pid"));
            elev.setNamn(rs.getString("fnamn") + " " + rs.getString("enamn"));
            elev.setEmail(rs.getString("email"));
            elev.setResa(rs.getInt("resa"));
            elev.setLogi(rs.getInt("logi"));
            elev.setMat(rs.getInt("mat"));
            elev.setRum(rs.getString("rum"));
            elev.setAnmald(rs.getDate("anmald"));
            elev.setBetaldatum(rs.getDate("betaldatum"));
            elev.setFakturanr(rs.getInt("fakturanr"));
            elev.setManpris(rs.getBoolean("manpris"));
            elev.setBeteckning(rs.getString("beteckning"));
            elev.setNy(false);
            elev.setAndrad(false);
            return elev;
        }
    }
}
