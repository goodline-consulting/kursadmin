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
 

public class JdbcConfigDao extends SimpleJdbcDaoSupport implements ConfigDao 
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
	
	public List<Config> getConfigList(String kategori)
    {
		keepAlive();      
        return getSimpleJdbcTemplate().query("select * from config where kategori  = '" +
                                                     kategori + "'", new ConfigMapper());
        
    }
	public Config getConfig(String kategori, String namn)
    {
		keepAlive();
		String 	query = "select * from config where kategori = " +
    	                kategori + " and namn = " + namn;
    	
    	return getSimpleJdbcTemplate().query(query, new ConfigMapper()).get(0);    	                
    }
	
        
	public void updateConfig(Config conf)
    {
		 keepAlive();
         getSimpleJdbcTemplate().update(
            "update config set varde = :varde where kategori = :kategori and namn = :namn",
            new MapSqlParameterSource().addValue("kategori", conf.getKategori())
            						   .addValue("namn", conf.getNamn())
            						   .addValue("varde", conf.getVarde()));
    }
	public void deleteConfig(Config conf)
    {
		keepAlive();
    	getSimpleJdbcTemplate().update("delete from config where kategori = ? and namn = ?", conf.getKategori(), conf.getNamn());
    }
	            
	public void insertConfig(Config conf)
    {
		keepAlive();
    	getSimpleJdbcTemplate().update("insert into config (kategori, namn, varde) values(:kategori, :namn, :varde)",                  
               new MapSqlParameterSource().addValue("kategori", conf.getKategori())
                						   .addValue("namn", conf.getNamn())
                                           .addValue("varde", conf.getVarde()));
    }
	public void insertConfigList(List<Config> list)
	{
		keepAlive();
		Config cf = list.get(0);
		getSimpleJdbcTemplate().update("delete from config where kategori = ?", cf.getKategori());
		for (Config conf: list)
		{
			insertConfig(conf);
		}
	}
    
    private static class ConfigMapper implements ParameterizedRowMapper<Config> 
    {

        public Config mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
            Config config = new Config();            
            config.setKategori(rs.getString("kategori"));                     
            config.setNamn(rs.getString("namn"));
            config.setVarde(rs.getString("varde"));
            return config;
        }
    }
}
