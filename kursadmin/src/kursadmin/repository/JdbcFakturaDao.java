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

import kursadmin.domain.Faktura;
import kursadmin.domain.Fakturarad;
import kursadmin.domain.Instruktor; 

public class JdbcFakturaDao extends SimpleJdbcDaoSupport implements FakturaDao 
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
	      
    public List<Faktura> getFakturor(int pid) 
    {
    	keepAlive();
        List<Faktura> fakturor = getSimpleJdbcTemplate().query("select * from faktura where pid = " + pid + " order by skapad desc", 
                               new FakturaMapper());
        
        for (Faktura faktura: fakturor)
        {
        	faktura.setRader(getSimpleJdbcTemplate().query("select * from fakturarad where fakturanr = " + (faktura.getFakturatyp() == Faktura.kreditFaktura ? faktura.getKopplad() :faktura.getFakturanr() + " order by radid"), new FakturaradMapper()));
        }
        return fakturor;
    }
    
    public Faktura getFaktura(int fakturanr)
    {
    	keepAlive();
    	String 	query = "select * from faktura where fakturanr = " + fakturanr;  
    	List<Faktura> fakturor = getSimpleJdbcTemplate().query(query, new FakturaMapper());
    	if (fakturor.size() == 0)
        	return null;
    	Faktura faktura = fakturor.get(0); 
    	query = "select * from fakturarad where fakturanr = " + (faktura.getFakturatyp() == Faktura.kreditFaktura ? faktura.getKopplad() :fakturanr) + " order by radid";
    	faktura.setRader(getSimpleJdbcTemplate().query(query, new FakturaradMapper()));
        return faktura;        
    }
    
    public int insertFaktura(Faktura faktura)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update(
                "insert into faktura (pid, belopp, betalt, rabatt, moms, skickad, betald, skuld, tillgodo,betalas,kopplad,fakturatyp, info, krediterat, oid) values(:pid, :belopp, :betalt, :rabatt, :moms, :skickad, :betald, :skuld, :tillgodo, :betalas, :kopplad, :fakturatyp, :info, :krediterat, :oid)",
                new MapSqlParameterSource().addValue("pid", faktura.getPid())
                						   .addValue("belopp", faktura.getBelopp())
                                           .addValue("betalt", faktura.getBetalt())
                                           .addValue("rabatt", faktura.getRabatt())
                                           .addValue("moms", faktura.getMoms())
                                           .addValue("skickad", faktura.getSkickad())                                           
                                           .addValue("betald", faktura.getBetald())                                           
                                           .addValue("skuld", faktura.getSkuld())
                                           .addValue("tillgodo", faktura.getTillgodo())
                                           .addValue("kopplad", faktura.getKopplad())
                                           .addValue("fakturatyp", faktura.getFakturatyp())
                                           .addValue("info", faktura.getInfo())
                                           .addValue("krediterat", faktura.getKrediterat())
                                           .addValue("oid", faktura.getOid())
                                           .addValue("betalas", faktura.getBetalas()));
    	int fakturanr =  getSimpleJdbcTemplate().queryForInt("select last_insert_id()");
    	if (faktura.getRader() != null)
    		insertFakturarader(fakturanr, faktura.getRader());
    	return fakturanr;
    }
    
    public void updateFaktura(Faktura faktura) 
    {
    	keepAlive();
    	int fakturanr = faktura.getFakturanr();
        getSimpleJdbcTemplate().update(
            "update faktura set pid = :pid, belopp = :belopp, betalt = :betalt, rabatt = :rabatt, moms = :moms, skickad = :skickad, betald = :betald, skuld = :skuld, tillgodo = :tillgodo, betalas = :betalas, kopplad = :kopplad, fakturatyp = :fakturatyp, info = :info, krediterat = :krediterat, oid = :oid where fakturanr = :fakturanr",
            new MapSqlParameterSource().addValue("fakturanr", fakturanr)
            						   .addValue("pid", faktura.getPid())
			   						   .addValue("belopp", faktura.getBelopp())
			   						   .addValue("betalt", faktura.getBetalt())
			   						   .addValue("rabatt", faktura.getRabatt())
                                       .addValue("moms", faktura.getMoms())
			   						   .addValue("skickad", faktura.getSkickad())                                           
			   						   .addValue("betald", faktura.getBetald())			   						   
			   						   .addValue("skuld", faktura.getSkuld())
			   						   .addValue("tillgodo", faktura.getTillgodo())
			   						   .addValue("kopplad", faktura.getKopplad())
                                       .addValue("fakturatyp", faktura.getFakturatyp())
                                       .addValue("info", faktura.getInfo())
                                       .addValue("krediterat", faktura.getKrediterat())
                                       .addValue("oid", faktura.getOid())
			   						   .addValue("betalas", faktura.getBetalas()));     
        if (faktura.getRader() != null)
        	insertFakturarader(fakturanr, faktura.getRader());
    }
    
    private void  insertFakturarader(int fakturanr, List<Fakturarad> rader)
    {
    	getSimpleJdbcTemplate().update("delete from fakturarad where fakturanr = ?", fakturanr);
    	for (Fakturarad rad: rader)
    	{
    		getSimpleJdbcTemplate().update(
                "insert into fakturarad (fakturanr, kid, belopp, rabatt, moms, spec) values(:fakturanr, :kid, :belopp, :rabatt, :moms, :spec)",
                new MapSqlParameterSource().addValue("fakturanr", fakturanr)
                						   .addValue("belopp", rad.getBelopp())
                                           .addValue("rabatt", rad.getRabatt())
                                           .addValue("moms", rad.getMoms())
                                           .addValue("kid", rad.getKid())
                                           .addValue("spec", rad.getSpec()));
                                           
    	}
    }
    
    public void deleteFaktura(int fakturanr)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update("delete from faktura where fakturanr = ?", fakturanr);
    	getSimpleJdbcTemplate().update("delete from fakturarad where fakturanr = ?", fakturanr);
    	getSimpleJdbcTemplate().update("update elev set fakturanr = 0 where fakturanr = ?", fakturanr);
    	getSimpleJdbcTemplate().update("delete from betalning where fakturanr = ?", fakturanr);
    }
        
    private static class FakturaMapper implements ParameterizedRowMapper<Faktura> 
    {

        public Faktura mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
            Faktura faktura = new Faktura();
            faktura.setFakturanr(rs.getInt("fakturanr"));
            faktura.setPid(rs.getInt("pid"));
            faktura.setBelopp(rs.getDouble("belopp"));
            faktura.setBetalt(rs.getDouble("betalt"));
            faktura.setSkapad(rs.getDate("skapad"));
            faktura.setSkickad(rs.getDate("skickad"));
            faktura.setBetald(rs.getDate("betald"));
            faktura.setRabatt(rs.getDouble("rabatt"));
            faktura.setMoms(rs.getDouble("moms"));            
            faktura.setSkuld(rs.getDouble("skuld"));
            faktura.setTillgodo(rs.getDouble("tillgodo"));
            faktura.setBetalas(rs.getDate("betalas"));
            faktura.setFakturatyp(rs.getInt("fakturatyp"));
            faktura.setKopplad(rs.getInt("kopplad"));
            faktura.setInfo(rs.getString("info"));
            faktura.setKrediterat(rs.getDouble("krediterat"));
            faktura.setOid(rs.getInt("oid"));
            return faktura;
        }
    }
    private static class FakturaradMapper implements ParameterizedRowMapper<Fakturarad> 
    {

        public Fakturarad mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
            Fakturarad fakturarad = new Fakturarad();
            fakturarad.setRadid(rs.getInt("radid"));
            fakturarad.setKid(rs.getInt("kid"));
            fakturarad.setFakturanr(rs.getInt("fakturanr"));            
            fakturarad.setBelopp(rs.getDouble("belopp"));
            fakturarad.setRabatt(rs.getDouble("rabatt"));
            fakturarad.setMoms(rs.getDouble("moms"));       
            fakturarad.setSpec(rs.getString("spec"));
            return fakturarad;
        }
    }
}
