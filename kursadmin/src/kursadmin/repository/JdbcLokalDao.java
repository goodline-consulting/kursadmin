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

import kursadmin.domain.Lokal; 

public class JdbcLokalDao extends SimpleJdbcDaoSupport implements LokalDao 
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

    public List<Lokal> getLokalList() 
    {
        keepAlive();
        List<Lokal> lokaler = getSimpleJdbcTemplate().query("select * from lokal order by lokalnamn", 
                               new LokalMapper());
        return lokaler;                
    }	
    
    public Lokal getLokal(int lid)
    {
    	keepAlive();
    	String 	query = "select * from lokal where lid = " + lid;    	
    	Lokal lokal = getSimpleJdbcTemplate().query(query, new LokalMapper()).get(0);    	
        return lokal; 
        
    }
 
    public void updateLokal(Lokal lokal) 
    {
    	keepAlive();
        int count = getSimpleJdbcTemplate().update(
            "update lokal set " +
            "adress = :adress, " +
            "postadress = :postadress, " +
            "email = :email, " +
            "lokalnamn = :lokalnamn, " +
            "info = :info, " +
            "mobil = :mobil, " +
            "postnr = :postnr, " +
            "telefon = :telefon, " +
            "kontakt = :kontakt, " +
            "timpris = :timpris, " +
            "vagbesk = :vagbesk, " +
            "bildurl = :bildurl " +
            "where lid = :lid",
            new MapSqlParameterSource().addValue("lid", lokal.getLid())
                                       .addValue("postadress", lokal.getPostadress())
            						   .addValue("adress", lokal.getAdress())
                                       .addValue("email", lokal.getEmail())
                                       .addValue("lokalnamn", lokal.getLokalnamn())                                       
                                       .addValue("info", lokal.getInfo())
                                       .addValue("mobil", lokal.getMobil())
                                       .addValue("postnr", lokal.getPostnr())
                                       .addValue("telefon", lokal.getTelefon())
                                       .addValue("kontakt", lokal.getKontakt())
                                       .addValue("timpris", lokal.getTimpris())
                                       .addValue("vagbesk", lokal.getVagbesk())
                                       .addValue("bildurl", lokal.getBildurl()));
       
    }
    
    public void deleteLokal(int lid)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update("delete from lokal where lid = ?", lid);
    	
    }
    
    public void insertLokal(Lokal lokal)
    {
    	keepAlive();
    	int count = getSimpleJdbcTemplate().update(
                "insert into lokal (adress, postadress, email, lokalnamn, info, mobil, postnr, telefon, kontakt, timpris, vagbesk, bildurl) values(:adress, :postadress, :email, :lokalnamn, :info, :mobil, :postnr, :telefon, :kontakt, :timpris, :vagbesk, :bildurl)",
                new MapSqlParameterSource().addValue("adress", lokal.getAdress())
                						   .addValue("postadress", lokal.getPostadress())
                                           .addValue("email", lokal.getEmail())
                                           .addValue("lokalnamn", lokal.getLokalnamn())                                           
                                           .addValue("info", lokal.getInfo())
                                           .addValue("mobil", lokal.getMobil())
                                           .addValue("postnr", lokal.getPostnr())
                                           .addValue("telefon", lokal.getTelefon())
                                           .addValue("kontakt", lokal.getKontakt())
                                           .addValue("timpris", lokal.getTimpris())
                                           .addValue("vagbesk", lokal.getVagbesk())
                                           .addValue("bildurl", lokal.getBildurl()));
    }
    
    private static class LokalMapper implements ParameterizedRowMapper<Lokal> 
    {

        public Lokal mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
            Lokal lokal = new Lokal();
            lokal.setLid(rs.getInt("lid"));
            lokal.setAdress(rs.getString("adress"));
            lokal.setPostadress(rs.getString("postadress"));
            lokal.setEmail(rs.getString("email"));
            lokal.setLokalnamn(rs.getString("lokalnamn"));            
            lokal.setInfo(rs.getString("info"));
            lokal.setMobil(rs.getString("mobil"));
            lokal.setPostnr(rs.getString("postnr"));
            lokal.setTelefon(rs.getString("telefon"));
            lokal.setKontakt(rs.getString("kontakt"));
            lokal.setTimpris(rs.getInt("timpris"));
            lokal.setVagbesk(rs.getString("vagbesk"));
            lokal.setBildurl(rs.getString("bildurl"));
            return lokal;
        }
    }
}
