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

import kursadmin.domain.Instruktor; 
import kursadmin.domain.KursAnmalan;

public class JdbcKursAnmalanDao extends SimpleJdbcDaoSupport implements KursAnmalanDao
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
	              
    public List<KursAnmalan> getKursAnmalanList() 
    {
    	keepAlive();
        List<KursAnmalan> kursAnmalningar = getSimpleJdbcTemplate().query("select kursanmalan.*, beteckning from kursanmalan inner join kurs on kursanmalan.kid = kurs.kid order by tidpunkt", 
                               new KursAnmalanMapper());
        return kursAnmalningar;
    }
    
    public KursAnmalan getKursAnmalan(int aid)
    {
    	keepAlive();
    	String 	query = "select kursanmalan.*, beteckning from kursanmalan inner join kurs on kursanmalan.kid = kurs.kid where aid = " + aid;    	
    	KursAnmalan kursAnmalan = getSimpleJdbcTemplate().query(query, new KursAnmalanMapper()).get(0);
        return kursAnmalan;         
    }

    public void deleteKursAnmalan(int aid)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update("delete from kursanmalan where aid = ?", aid);
    }
    
    public void insertKursAnmalan(KursAnmalan kursAnmalan)
    {
    	keepAlive();
    	if (kursAnmalan.getInfo().length() > 80)
    		kursAnmalan.setInfo(kursAnmalan.getInfo().substring(0, 80));
    	getSimpleJdbcTemplate().update(
                "insert into kursanmalan (kid, tidpunkt, fnamn, enamn, telefon, email, handled, info) values(:kid, :tidpunkt, :fnamn, :enamn, :telefon, :email, :handled, :info)",
                new MapSqlParameterSource().addValue("kid", kursAnmalan.getKid())
                						   .addValue("tidpunkt", kursAnmalan.getTidpunkt())	
                						   .addValue("fnamn", kursAnmalan.getFnamn())
                                           .addValue("enamn", kursAnmalan.getEnamn())
                                           .addValue("telefon", kursAnmalan.getTelefon())                                           
                                           .addValue("email", kursAnmalan.getEmail())
                                           .addValue("info", kursAnmalan.getInfo())
                                           .addValue("handled", kursAnmalan.isHandled()));
    }
    
    private static class KursAnmalanMapper implements ParameterizedRowMapper<KursAnmalan> 
    {
        public KursAnmalan mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
        	KursAnmalan kursAnmalan = new KursAnmalan();
        	kursAnmalan.setAid(rs.getInt("aid"));
        	kursAnmalan.setTidpunkt(rs.getTimestamp("tidpunkt"));
        	kursAnmalan.setKid(rs.getInt("kid"));
        	kursAnmalan.setFnamn(rs.getString("fnamn"));
        	kursAnmalan.setEnamn(rs.getString("enamn"));
        	kursAnmalan.setEmail(rs.getString("email"));        	            
        	kursAnmalan.setTelefon(rs.getString("telefon"));
        	kursAnmalan.setInfo(rs.getString("info"));
        	kursAnmalan.setHandled(rs.getBoolean("handled"));
        	kursAnmalan.setBeteckning(rs.getString("beteckning"));
            return kursAnmalan;
        }
    }
}
