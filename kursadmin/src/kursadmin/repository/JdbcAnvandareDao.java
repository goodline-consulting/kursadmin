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

import kursadmin.domain.Anvandare;
import kursadmin.domain.Instruktor; 

public class JdbcAnvandareDao extends SimpleJdbcDaoSupport implements AnvandareDao 
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
	
    public List<Anvandare> getAnvandareList()
    {
    	keepAlive();
        List<Anvandare> anvandare = getSimpleJdbcTemplate().query("select users.username, password, authority from users inner join authorities on users.username = authorities.username", 
                               new AnvandareMapper());
        return anvandare;
    }
    
    public Anvandare getAnvandare(String namn)
    {
    	keepAlive();
    	String 	query = "select users.username, password, authority from users inner join authorities on users.username = authorities.username where users.username = '" + namn + "'";    	
    	Anvandare anvandare = getSimpleJdbcTemplate().query(query, new AnvandareMapper()).get(0);    	
        return anvandare; 
        
    }
 
    
    public void deleteAnvandare(String namn)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update("delete from authorities where username = ?", namn);
    	getSimpleJdbcTemplate().update("delete from users where username = ?", namn);
    	
    }
    
    public void insertAnvandare(Anvandare anvandare)
    {
    	keepAlive();
    	//logger.info(anvandare.getNamn() + " " + anvandare.getPassord() + " " + anvandare.getRoll());
    	getSimpleJdbcTemplate().update(
                "insert into users (username, password, enabled) values(:namn, :passord, :enabled)",
                new MapSqlParameterSource().addValue("namn", anvandare.getNamn())
                						   .addValue("passord", anvandare.getPassord())
                                           .addValue("enabled", 1));
    	getSimpleJdbcTemplate().update(
                "insert into authorities (username, authority) values(:namn, :roll)",
                new MapSqlParameterSource().addValue("namn", anvandare.getNamn())
                						   .addValue("roll", anvandare.getRoll()));
                                           
	
    }
    public void updateAnvandare(Anvandare anvandare)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update(
    	            "update users set password = :passord where username = :namn",
    	            new MapSqlParameterSource().addValue("passord", anvandare.getPassord())
    	                                       .addValue("namn", anvandare.getNamn()));
    	getSimpleJdbcTemplate().update(
                "update authorities set authority = :roll where username = :namn",
                new MapSqlParameterSource().addValue("namn", anvandare.getNamn())
                						   .addValue("roll", anvandare.getRoll()));
                                           
	
    }
    
    private static class AnvandareMapper implements ParameterizedRowMapper<Anvandare> 
    {

        public Anvandare mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
        	Anvandare anvandare = new Anvandare();            
            anvandare.setNamn(rs.getString("username"));            
            anvandare.setPassord(rs.getString("password"));
            anvandare.setRoll(rs.getString("authority"));
            return anvandare;
        }
    }
}
