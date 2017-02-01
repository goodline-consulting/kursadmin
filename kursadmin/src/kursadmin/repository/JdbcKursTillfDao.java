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

import kursadmin.domain.KursTillf;


public class JdbcKursTillfDao extends SimpleJdbcDaoSupport implements KursTillfDao 
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
   
    public List<KursTillf> getKursTillfList(int kid) 
    {
    	keepAlive();
        List<KursTillf> lista = getSimpleJdbcTemplate().query("select * from kurstillf where kurstillf.kid = " + 
        		                kid + " order by kid, seq", 
                               new KursTillfMapper());        
        return lista;
    }      
   
    private void deleteKursTillf(int kid)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update("delete from kurstillf where kid = ?", kid);
    }
    
    public void setKursTillfList(int kid, List<KursTillf> lista)
    {
    	keepAlive();
    	deleteKursTillf(kid);
    	for (KursTillf kursTillf: lista)
    	{	 

    		getSimpleJdbcTemplate().update("insert into kurstillf (kid, dag) values(:kid, :dag)",
                                    new MapSqlParameterSource().addValue("kid", kid)
                                                               .addValue("dag", kursTillf.getDag()));
    	}	
    }    
    public int getAntKurstillf(int kid)
    {
    	keepAlive();
    	return getSimpleJdbcTemplate().queryForInt("select count(*) from kurstillf where kid = " + kid);
    }
    
    private static class KursTillfMapper implements ParameterizedRowMapper<KursTillf> 
    {

        public KursTillf mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
            KursTillf kursTillf = new KursTillf();
            kursTillf.setSeq(rs.getInt("seq"));
            kursTillf.setKid(rs.getInt("kid"));            
            kursTillf.setDag(rs.getDate("dag"));            
            return kursTillf;
        }
    }
}
