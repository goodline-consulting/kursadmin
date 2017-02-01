package kursadmin.repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.security.userdetails.jdbc.JdbcDaoImpl;

public class MyUserDetailService extends JdbcDaoImpl  
{
	protected final Log logger = LogFactory.getLog(getClass());
	public MyUserDetailService()
	{
		super();
	}
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException 
	{	
		//logger.info("HERE COMES JOHNY");
		try
        {
        	new SimpleJdbcTemplate(getDataSource()).queryForInt("select 1"); 	                                       	
        }
        catch (DataAccessResourceFailureException e)
        {	        	
        }		
		return super.loadUserByUsername(username);    			
	}	
	
}
