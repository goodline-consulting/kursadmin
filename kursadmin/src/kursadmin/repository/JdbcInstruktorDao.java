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

public class JdbcInstruktorDao extends SimpleJdbcDaoSupport implements InstruktorDao 
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
	
    public List<Instruktor> getInstruktorList() 
    {
    	keepAlive();
        List<Instruktor> instruktors = getSimpleJdbcTemplate().query("select * from instruktor order by namn", 
                               new InstruktorMapper());
        return instruktors;
    }
    
    public Instruktor getInstruktor(int iid)
    {
    	keepAlive();
    	String 	query = "select * from instruktor where iid = " + iid;    	
    	Instruktor instruktor = getSimpleJdbcTemplate().query(query, new InstruktorMapper()).get(0);    	
        return instruktor; 
        
    }
 
    public void updateInstruktor(Instruktor instruktor) 
    {
    	keepAlive();
        getSimpleJdbcTemplate().update(
            "update instruktor set adress = :adress, postadress = :postadress, email = :email, namn = :namn, info = :info, mobil = :mobil, postnr = :postnr, telefon = :telefon, bild = :bild, bildx = :bildx, bildy = :bildy where iid = :iid",
            new MapSqlParameterSource().addValue("iid", instruktor.getIid())
                                       .addValue("postadress", instruktor.getPostadress())
            						   .addValue("adress", instruktor.getAdress())
                                       .addValue("email", instruktor.getEmail())
                                       .addValue("namn", instruktor.getNamn())                                       
                                       .addValue("info", instruktor.getInfo())
                                       .addValue("mobil", instruktor.getMobil())
                                       .addValue("postnr", instruktor.getPostnr())
                                       .addValue("telefon", instruktor.getTelefon())
        							   .addValue("bild", instruktor.getBild())
        							   .addValue("bildx", instruktor.getBildx())
        							   .addValue("bildy", instruktor.getBildy()));     
    }
    
    public void deleteInstruktor(int iid)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update("delete from instruktor where iid = ?", iid);
    }
    
    public void insertInstruktor(Instruktor instruktor)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update(
                "insert into instruktor (adress, postadress, email, namn, info, mobil, postnr, telefon, bild, bildx, bildy) values(:adress, :postadress, :email, :namn, :info, :mobil, :postnr, :telefon, :bild, :bildx, :bildy)",
                new MapSqlParameterSource().addValue("adress", instruktor.getAdress())
                						   .addValue("postadress", instruktor.getPostadress())
                                           .addValue("email", instruktor.getEmail())
                                           .addValue("namn", instruktor.getNamn())                                           
                                           .addValue("info", instruktor.getInfo())
                                           .addValue("mobil", instruktor.getMobil())
                                           .addValue("postnr", instruktor.getPostnr())
                                           .addValue("telefon", instruktor.getTelefon())
                                           .addValue("bild", instruktor.getBild())
                                           .addValue("bildx", instruktor.getBildx())
            							   .addValue("bildy", instruktor.getBildy()));    
    }
    
    private static class InstruktorMapper implements ParameterizedRowMapper<Instruktor> 
    {

        public Instruktor mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
            Instruktor instruktor = new Instruktor();
            instruktor.setIid(rs.getInt("iid"));
            instruktor.setAdress(rs.getString("adress"));
            instruktor.setPostadress(rs.getString("postadress"));
            instruktor.setEmail(rs.getString("email"));
            instruktor.setNamn(rs.getString("namn"));            
            instruktor.setInfo(rs.getString("info"));
            instruktor.setMobil(rs.getString("mobil"));
            instruktor.setPostnr(rs.getString("postnr"));
            instruktor.setTelefon(rs.getString("telefon"));
            instruktor.setBild(rs.getString("bild"));
            instruktor.setBildx(rs.getInt("bildx"));
            instruktor.setBildy(rs.getInt("bildy"));
            return instruktor;
        }
    }
}
