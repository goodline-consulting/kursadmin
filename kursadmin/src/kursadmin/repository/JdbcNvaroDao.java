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
import kursadmin.domain.Nvaro;
 

public class JdbcNvaroDao extends SimpleJdbcDaoSupport implements NvaroDao 
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
	    
    public List<Nvaro> getNvaroList(int kid)
    {
    	keepAlive();
        List<Nvaro> nvaro = getSimpleJdbcTemplate().query("select kid, seq, pid from nvaro where kid = " +
                                                     kid + " order by kid, seq", new NvaroMapper());
        return nvaro;
    }
    
    public void setNvaro(int kid, List<Nvaro> lista)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update("delete from nvaro where kid = ?", kid);
    	for (Nvaro nvaro: lista)
    	{	 
    		getSimpleJdbcTemplate().update("insert into nvaro (kid, seq, pid) values(:kid, :seq, :pid)",
                                    new MapSqlParameterSource().addValue("kid", kid)
                                                               .addValue("seq", nvaro.getSeq())
                                                               .addValue("pid", nvaro.getPid()));
    	}
    }
        
    public int getAntalNarvarande(int kid, int kurstillf)
    {
    	return getSimpleJdbcTemplate().queryForInt("select count(*) from nvaro where kid = " + kid + " and seq = " + kurstillf);
    }
    
    private static class NvaroMapper implements ParameterizedRowMapper<Nvaro> 
    {

        public Nvaro mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
            Nvaro nvaro = new Nvaro();            
            nvaro.setKid(rs.getInt("kid"));
            nvaro.setSeq(rs.getInt("seq"));            
            nvaro.setPid(rs.getInt("pid"));                  
            return nvaro;
        }
    }
}
