package kursadmin.repository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
 
public class JdbcDaoKeepAlive 
{
	
	protected final Log logger = LogFactory.getLog(getClass());
	Properties props = new Properties();	
	DriverManagerDataSource ds = new DriverManagerDataSource();
	
	public JdbcDaoKeepAlive()
	{
		try 
		{
			props.load(JdbcDaoKeepAlive.class.getResourceAsStream("../../jdbc.properties"));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{		
			e.printStackTrace();
		}
		
		ds.setDriverClassName(props.getProperty("jdbc.driverClassName"));
		ds.setUrl(props.getProperty("jdbc.url"));
		ds.setUsername(props.getProperty("jdbc.username"));
		ds.setPassword(props.getProperty("jdbc.password"));
	}	
		
	public void keepAlive()
	{
		JdbcTemplate jtp = new JdbcTemplate(ds);
		//logger.info("Kepp-the-fucker-alive: " + jtp.queryForInt("select pris from kurs where kid = 320"));
		jtp.queryForInt("select 1");
		
		
        		
	}
}