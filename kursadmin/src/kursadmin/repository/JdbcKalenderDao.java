package kursadmin.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import kursadmin.domain.Kalender;
import kursadmin.domain.Lokal; 

public class JdbcKalenderDao extends SimpleJdbcDaoSupport implements KalenderDao 
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
	
	public List<Kalender> getKalender() 
    { 
		keepAlive();
        return getSimpleJdbcTemplate().query("select * from kalender order by tidpunkt desc", 
                               new KalenderMapper());
        
    }
    
    public Kalender getKalender(int cid)
    {
    	keepAlive();
    	String 	query = "select * from kalender where cid = " + cid;
    	
    	return getSimpleJdbcTemplate().query(query, new KalenderMapper()).get(0);    	         
        
    }
 
    public void updateKalender(Kalender kalender) 
    {
    	keepAlive();
        getSimpleJdbcTemplate().update(
            "update kalender set tidpunkt = :tidpunkt, rubrik = :rubrik, info = :info, vikt = :vikt, kvitt = :kvitt, larm = :larm where cid = :cid",
            new MapSqlParameterSource().addValue("cid", kalender.getCid())
            					       .addValue("tidpunkt", kalender.getTidpunkt())
                                       .addValue("rubrik", kalender.getRubrik())
            						   .addValue("info", kalender.getInfo())
                                       .addValue("vikt", kalender.getVikt())
                                       .addValue("kvitt", kalender.isKvitt())                                       
                                       .addValue("larm", kalender.isLarm()));                                              
    }
    
    public void deleteKalender(int cid)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update("delete from kalender where cid = ?", cid);
    }
    
    public void insertKalender(Kalender kalender)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update(
        "insert into kalender (tidpunkt, rubrik, info, vikt, kvitt, larm) values(:tidpunkt, :rubrik, :info, :vikt, :kvitt, :larm)",
         new MapSqlParameterSource().addValue("tidpunkt", kalender.getTidpunkt())
            					    .addValue("rubrik", kalender.getRubrik())
                					.addValue("info", kalender.getInfo())
                					.addValue("vikt", kalender.getVikt())
                					.addValue("kvitt", kalender.isKvitt())                                       
                					.addValue("larm", kalender.isLarm()));
     }
    
    private static class KalenderMapper implements ParameterizedRowMapper<Kalender> 
    {

        public Kalender mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
            Kalender kalender = new Kalender();
            kalender.setCid(rs.getInt("cid"));
            //kalender.setTidpunkt(new java.util.Date(rs.getDate("tidpunkt").getTime()));
            kalender.setTidpunkt(rs.getTimestamp("tidpunkt"));
            kalender.setRubrik(rs.getString("rubrik"));
            kalender.setInfo(rs.getString("info"));
            kalender.setKvitt(rs.getBoolean("kvitt"));            
            kalender.setLarm(rs.getBoolean("larm"));
            kalender.setVikt(rs.getInt("vikt"));
            if (kalender.getInfo().length() > 0)
            	kalender.setSummering(kalender.getInfo().substring(0, kalender.getInfo().length() > 40 ? 40 : kalender.getInfo().length() - 1) + "...");
            else
            	kalender.setSummering("");
            return kalender;
        }
    }
}
