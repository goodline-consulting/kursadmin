package kursadmin.repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import kursadmin.domain.Betalning;


public class JdbcBetalningDao extends SimpleJdbcDaoSupport implements BetalningDao  
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
	public boolean DatumExists(Date datum)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	   	keepAlive();	   	
	   	int i = getSimpleJdbcTemplate().queryForInt("select exists(select id from betalning where betaldatum  = '" + sdf.format(datum) + "' and betalkanal = 'Bgmax')");
	   	if (i == 0)       	
	   		return false;
	   	else
	   		return true;        
	}    
	public List<Betalning> getOplacerade()
	{
		keepAlive();
		return getSimpleJdbcTemplate().query("select * from betalning where placerad = false", new BetalningMapper());
	}
	
	public List<Betalning> getPersonBetalning(String name)
	{
		keepAlive();
		return getSimpleJdbcTemplate().query("select * from betalning where name like '%" + name + "%' order by id", new BetalningMapper());
	}	
	public List<Betalning> getBetalningarTillgodoList(int pid)
	{
		keepAlive();
		return getSimpleJdbcTemplate().query("select * from betalning where pid = " + pid, new BetalningMapper());
	}
	public List<Betalning> getBetalningList(int fakturanr)
	{
		keepAlive();
		return getSimpleJdbcTemplate().query("select * from betalning where fakturanr = " + fakturanr + " order by id", new BetalningMapper());
	}
	public List<Betalning> getBetalningList(Date fromDat, Date toDat)
	{
		keepAlive();		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return getSimpleJdbcTemplate().query("select * from betalning where betaldatum >= '" + sdf.format(fromDat) + "' and betaldatum <= '" + sdf.format(toDat) + "' order by id", new BetalningMapper());
	}
	public List<Betalning> getBetalningList(Date datum)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		keepAlive();
		return getSimpleJdbcTemplate().query("select * from betalning where betaldatum = '" + sdf.format(datum) + "' order by id", new BetalningMapper());
	}
	public List<Date> getDates()
	{
		// Vi returnerar bara datum hšgst ett halvŒr tillbaka
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar gregCal = Calendar.getInstance();
		gregCal.add(Calendar.MONTH, -6);
	    Date datum = new Date(gregCal.getTimeInMillis());
		keepAlive();
		return getSimpleJdbcTemplate().query("select distinct betaldatum from betalning where betaldatum > '" + sdf.format(datum) + "' order by betaldatum desc", new DateMapper());
		
	}
	public Betalning getBetalning(int id)
    {
    	keepAlive();  	 	
    	return getSimpleJdbcTemplate().query("select * from betalning where id = " + id, new BetalningMapper()).get(0);    	        
    }
    public void insertBetalningar(List<Betalning> betalningar)
    {
    	keepAlive();
    	for (Betalning betalning: betalningar)
    	{	 
    		insertBetalning(betalning);
    	}	
    }
    public void insertBetalning(Betalning betalning)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update("insert into betalning (parentid, pid, amount, name, betalkanal, betaldatum, info, fakturanr, placerat, placerad) values(:parentid, :pid, :amount, :name, :betalkanal, :betaldatum, :info, :fakturanr, :placerat, :placerad)",
                                    new MapSqlParameterSource().addValue("parentid", betalning.getParentid())
                                    						   .addValue("pid", betalning.getPid())
                                                               .addValue("amount", betalning.getAmount())
                                                               .addValue("name", betalning.getName())
                                                               .addValue("betalkanal", betalning.getBetalkanal())
                                                               .addValue("betaldatum", betalning.getBetaldatum())
                                                               .addValue("info", betalning.getInfo())                                                              
                                                               .addValue("fakturanr", betalning.getFakturanr())
                                                               .addValue("placerad", betalning.isPlacerad())
                                                               .addValue("placerat", betalning.getPlacerat()));
    }
    public void updateBetalning(Betalning betalning)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update("update betalning set parentid = :parentid, pid = :pid, amount = :amount, name = :name, betalkanal = :betalkanal, betaldatum = :betaldatum, info = :info, fakturanr = :fakturanr, placerat = :placerat, placerad = :placerad where id = :id",
                                    new MapSqlParameterSource().addValue("parentid", betalning.getParentid())
                                    						   .addValue("pid", betalning.getPid())
                                                               .addValue("amount", betalning.getAmount())
                                                               .addValue("name", betalning.getName())
                                                               .addValue("betalkanal", betalning.getBetalkanal())
                                                               .addValue("betaldatum", betalning.getBetaldatum())
                                                               .addValue("info", betalning.getInfo())                                                              
                                                               .addValue("id", betalning.getId())
                                                               .addValue("fakturanr", betalning.getFakturanr())
                                                               .addValue("placerat", betalning.getPlacerat())
                                                               .addValue("placerad", betalning.isPlacerad()));
    }
    public void deleteBetalning(int id)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update("delete from betalning where id = ?", id);
    }
    private static class BetalningMapper implements ParameterizedRowMapper<Betalning> 
    {

        public Betalning mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
        	Betalning betalning = new Betalning();
        	betalning.setId(rs.getInt("id"));
        	betalning.setParentid(rs.getInt("parentid"));
        	betalning.setPid(rs.getInt("pid"));
        	betalning.setAmount(rs.getDouble("amount"));
        	betalning.setName(rs.getString("name"));
        	betalning.setBetalkanal(rs.getString("betalkanal"));
        	betalning.setBetaldatum(rs.getDate("betaldatum"));
        	betalning.setInfo(new StringBuffer(rs.getString("info")));        	
        	betalning.setFakturanr(rs.getInt("fakturanr"));        	
        	betalning.setPlacerat(rs.getDouble("placerat"));
        	betalning.setPlacerad(rs.getBoolean("placerad"));
        	return betalning;
        }
    }
    private static class DateMapper implements ParameterizedRowMapper<Date> 
    {

        public Date mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
        	return rs.getDate("betaldatum");        	
        }
    }
}













