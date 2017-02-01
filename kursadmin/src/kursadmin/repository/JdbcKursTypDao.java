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

import kursadmin.domain.Config;
import kursadmin.domain.KursNiva;
import kursadmin.domain.KursTyp;
 

public class JdbcKursTypDao extends SimpleJdbcDaoSupport implements KursTypDao 
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
	
    public List<KursTyp> getKursTypList()
    {
    	keepAlive();      
        return getSimpleJdbcTemplate().query("select * from kurstyp", new KursTypMapper());
        
    }
   
    public KursTyp getKursTyp(int tid)
    {
    	keepAlive();
    	String 	query = "select * from kurstyp where tid = " + tid;  	                    	
    	return getSimpleJdbcTemplate().query(query, new KursTypMapper()).get(0);    	                
    }    
        
    public void updateKursTyp(KursTyp kursTyp)
    {
    	keepAlive();   
    	getSimpleJdbcTemplate().update(
            "update kurstyp set namn = :namn, info = :info, momssats = :momssats, momsbak= :momsbak, typavrabatt = :typavrabatt, rabatter = :rabatter, fakturatyp = :fakturatyp, rumshantering = :rumshantering, mathantering = :mathantering, resehantering = :resehantering, oid = :oid, fakturaklass = :fakturaklass where tid = :tid",
            new MapSqlParameterSource().addValue("namn", kursTyp.getNamn())
            						   .addValue("info", kursTyp.getInfo())
            						   .addValue("momssats", kursTyp.getMomssats())
            						   .addValue("momsbak", kursTyp.getMomsbak())
            						   .addValue("typavrabatt", kursTyp.getTypavrabatt())
            						   .addValue("rabatter", kursTyp.getRabatter())
            						   .addValue("tid", kursTyp.getTid())
            						   .addValue("fakturatyp", kursTyp.getFakturatyp())
            						   .addValue("rumshantering", kursTyp.isRumshantering())
					                   .addValue("mathantering", kursTyp.isMathantering())
					                   .addValue("resehantering", kursTyp.isResehantering())
					                   .addValue("oid", kursTyp.getOid())
					                   .addValue("fakturaklass", kursTyp.getFakturaklass()));
    }
        
    public boolean deleteKursTyp(int tid)
    {
    	keepAlive();
    	if (getSimpleJdbcTemplate().queryForInt("select exists(select tid from kurs where tid = ?)", tid) == 1)
    		return false;
    	getSimpleJdbcTemplate().update("delete from kursniva where tid = ?", tid);
    	getSimpleJdbcTemplate().update("delete from kurstyp where tid = ?", tid);
    	return true;
    }
	            
    public void insertKursTyp(KursTyp kursTyp, List<KursNiva> nivaer)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update("insert into kurstyp (namn, info, momssats, momsbak, typavrabatt, rabatter, fakturatyp, rumshantering, mathantering, resehantering, oid, fakturaklass) values(:namn, :info, :momssats, :momsbak, :typavrabatt, :rabatter, :fakturatyp, :rumshantering, :mathantering, :resehantering, :oid, fakturaklass)",                  
                                new MapSqlParameterSource().addValue("namn", kursTyp.getNamn())
                						                   .addValue("info", kursTyp.getInfo())
                						                   .addValue("momssats", kursTyp.getMomssats())
                						                   .addValue("momsbak", kursTyp.getMomsbak())
                						                   .addValue("typavrabatt", kursTyp.getTypavrabatt())
                						                   .addValue("rabatter", kursTyp.getRabatter())
                						                   .addValue("fakturatyp", kursTyp.getFakturatyp())
                						                   .addValue("rumshantering", kursTyp.isRumshantering())
                						                   .addValue("mathantering", kursTyp.isMathantering())
                						                   .addValue("resehantering", kursTyp.isResehantering())
                						                   .addValue("oid", kursTyp.getOid())
                						                   .addValue("fakturaklass", kursTyp.getFakturaklass()));
    	for (KursNiva niva : nivaer)    		
    	getSimpleJdbcTemplate().update("insert into kursniva (tid, nid, namn) values(last_insert_id(), :nid, :namn)", 
                                new MapSqlParameterSource().addValue("nid", niva.getNid())
                                						   .addValue("namn", niva.getNamn()));
	}	
    
    public void insertKursNiva(KursNiva kursNiva)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update("insert into kursniva (tid, nid, namn) values(:tid, :nid, :namn)",                  
                new MapSqlParameterSource().addValue("tid", kursNiva.getTid())
						                   .addValue("nid", kursNiva.getNid())
						                   .addValue("namn", kursNiva.getNamn()));
    }
        
    public void insertKursNivaList(List<KursNiva> list, int tid)
	{
    	keepAlive();
		getSimpleJdbcTemplate().update("delete from kursniva where tid = ?", tid);
		for (KursNiva kursNiva: list)
		{
			insertKursNiva(kursNiva);
		}
	}
    
    public List<KursNiva> getKursNivaList()
    {
    	keepAlive();
    	return getSimpleJdbcTemplate().query("select * from kursniva", new KursNivaMapper());
    }
    public List<KursNiva> getKursNivaList(int tid)
    {
    	keepAlive();
       	return getSimpleJdbcTemplate().query("select * from kursniva where tid = " + tid, new KursNivaMapper());
    }

    private static class KursTypMapper implements ParameterizedRowMapper<KursTyp> 
    {

        public KursTyp mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
            KursTyp kursTyp = new KursTyp();            
            kursTyp.setTid(rs.getInt("tid"));                     
            kursTyp.setNamn(rs.getString("namn"));
            kursTyp.setInfo(rs.getString("info"));
            kursTyp.setMomssats(rs.getDouble("momssats"));
            kursTyp.setMomsbak(rs.getDouble("momsbak"));
            kursTyp.setTypavrabatt(rs.getInt("typavrabatt"));
            kursTyp.setRabatter(rs.getString("rabatter"));
            kursTyp.setFakturatyp(rs.getInt("fakturatyp"));
            kursTyp.setMathantering(rs.getBoolean("mathantering"));
            kursTyp.setResehantering(rs.getBoolean("resehantering"));
            kursTyp.setRumshantering(rs.getBoolean("rumshantering"));
            kursTyp.setOid(rs.getInt("oid"));
            kursTyp.setFakturaklass(rs.getString("fakturaklass"));
            return kursTyp;
        }
    }
    private static class KursNivaMapper implements ParameterizedRowMapper<KursNiva> 
    {

        public KursNiva mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
        	KursNiva kursNiva = new KursNiva();            
        	kursNiva.setTid(rs.getInt("tid"));
        	kursNiva.setNid(rs.getInt("nid"));
        	kursNiva.setNamn(rs.getString("namn"));
        	
            return kursNiva;
        }
    }
}
