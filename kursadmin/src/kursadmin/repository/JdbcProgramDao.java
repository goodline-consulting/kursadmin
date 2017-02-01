package kursadmin.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import kursadmin.domain.Config;
import kursadmin.domain.KursNiva;
import kursadmin.domain.KursProgram;
import kursadmin.domain.KursTyp;
import kursadmin.domain.ProgramPunkt;
 

public class JdbcProgramDao extends SimpleJdbcDaoSupport implements ProgramDao 
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
	
	public List<ProgramPunkt> getProgramPunktList()
    {
    	keepAlive();      
        return getSimpleJdbcTemplate().query("select programpunkt.*, kurstyp.namn from programpunkt left join kurstyp on programpunkt.tid = kurstyp.tid order by programpunkt.namn", new ProgramPunktMapper());
        
    }
   
	public List<ProgramPunkt> getProgramPunktList(int tid)
    {
    	keepAlive();      
        return getSimpleJdbcTemplate().query("select programpunkt.*, kurstyp.namn from programpunkt left join kurstyp on programpunkt.tid = kurstyp.tid where programpunkt.tid = " + 
        		                              tid + " order by programpunkt.namn",
        		                              new ProgramPunktMapper());        
    }
	public List<ProgramPunkt> getProgramPunktList(int tid, String filter, boolean aktuella)
	{
		String q = "select programpunkt.*, kurstyp.namn from programpunkt left join kurstyp on programpunkt.tid = kurstyp.tid where "; 
		boolean tidPresent = false;
		boolean filterPresent = false;
		keepAlive();
		if (tid != 0)
		{	
			q = q + "programpunkt.tid = " + tid;
			tidPresent = true;
		}	
		if (filter.length() > 0)
		{
			if (tidPresent)
				q = q + " and";
			q = q + " programpunkt.namn like '" + filter + "%'";
			filterPresent = true;
		}
		if (aktuella)
		{
			if (filterPresent || tidPresent)
				q = q + " and";
			q = q + " aktuell = true";
		}
		q = q + " order by programpunkt.namn";
		return getSimpleJdbcTemplate().query(q, new ProgramPunktMapper());      
	}
	public ProgramPunkt getProgramPunkt(int ppid)
    {
    	keepAlive();
    	String 	query = "select * from programpunkt where ppid = " + ppid;  	                    	
    	return getSimpleJdbcTemplate().query(query, new ProgramPunktMapper()).get(0);    	
    }    
        
    public void updateProgramPunkt(ProgramPunkt programPunkt)
    {
    	keepAlive();   
    	getSimpleJdbcTemplate().update(
            "update programpunkt set tid = :tid, namn = :namn, url = :url, header = :header, info = :info, url2 = :url2, aktuell = :aktuell where ppid = :ppid",
            new MapSqlParameterSource().addValue("tid", programPunkt.getTid())
            						   .addValue("namn", programPunkt.getNamn())
            						   .addValue("url", programPunkt.getUrl())
            						   .addValue("header", programPunkt.getHeader())
            						   .addValue("info", programPunkt.getInfo())
            						   .addValue("url2", programPunkt.getUrl2())
            						   .addValue("aktuell", programPunkt.isAktuell())
            						   .addValue("ppid", programPunkt.getPpid()));
    }
        
    public boolean deleteProgramPunkt(int ppid)
    {
    	keepAlive();
    	if (getSimpleJdbcTemplate().queryForInt("select exists(select ppid from kursprogram where ppid = ?)", ppid) == 1)
    		return false;    	
    	getSimpleJdbcTemplate().update("delete from programpunkt where ppid = ?", ppid);
    	return true;
    }
	            
    public void insertProgramPunkt(ProgramPunkt programPunkt)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update("insert into programpunkt (tid, namn, url, header, info, url2, aktuell) values(:tid, :namn, :url, :header, :info, :url2, :aktuell)",                  
                                new MapSqlParameterSource().addValue("tid", programPunkt.getTid())
    													   .addValue("namn", programPunkt.getNamn())
                                						   .addValue("header", programPunkt.getHeader())	
                                						   .addValue("url", programPunkt.getUrl())
                                						   .addValue("info", programPunkt.getInfo())
                                						   .addValue("url2", programPunkt.getUrl2())
                                						   .addValue("aktuell", programPunkt.isAktuell()));
	}	
    
    public void loadProgramPunkter(List<ProgramPunkt> punktList)
    {
    	/* 
    	 * befintliga programpunkter  för kurstypen läses in till en hashmap
    	 * för att kolla att det inte läggs in nÂgra dubletter.
    	 */
    	HashSet<String> map = new HashSet<String>();    	
    	
    	for (ProgramPunkt pkt: getProgramPunktList(punktList.get(0).getTid()))
    	{
    		map.add(pkt.getNamn());
    	}
    	for (ProgramPunkt pkt: punktList)
    	{
    		if (map.contains(pkt.getNamn()))
    			continue;
    		insertProgramPunkt(pkt);
    	}
    }
    public List<KursProgram> getKursProgram(int kid)
    {
    	keepAlive();
      	String 	query = "select kursprogram.*, namn from kursprogram inner join programpunkt on programpunkt.ppid = kursprogram.ppid where kid = " + kid;  	                    	
    	return getSimpleJdbcTemplate().query(query, new KursProgramMapper());  
    }
    
    public void setKursProgram(List<KursProgram> progList)
    {
    	keepAlive();
    	int kid = progList.get(0).getKid();
    	getSimpleJdbcTemplate().update("delete from kursprogram where kid = ?", kid);
    	for (KursProgram kp : progList)
    	{
    		getSimpleJdbcTemplate().update("insert into kursprogram (kid, seq, ppid) values(:kid, :seq, :ppid)",                  
                    new MapSqlParameterSource().addValue("kid", kp.getKid())
                    						   .addValue("seq", kp.getSeq())	
                    						   .addValue("ppid", kp.getPpid()));                    						   
    	}
    }
    
    private static class ProgramPunktMapper implements ParameterizedRowMapper<ProgramPunkt> 
    {

        public ProgramPunkt mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
            ProgramPunkt programPunkt = new ProgramPunkt();            
            programPunkt.setPpid(rs.getInt("ppid")); 
            programPunkt.setTid(rs.getInt("tid")); 
            programPunkt.setNamn(rs.getString("programpunkt.namn"));
            programPunkt.setUrl(rs.getString("url"));
            programPunkt.setHeader(rs.getString("header"));
            programPunkt.setInfo(rs.getString("info"));
            programPunkt.setUrl2(rs.getString("url2"));
            programPunkt.setAktuell(rs.getBoolean("aktuell"));
            try
            {
            	
            	programPunkt.setKurstyp(rs.getString("kurstyp.namn"));            	
            }
            catch (SQLException e)
            {
            	programPunkt.setKurstyp("");
            }
            if (programPunkt.getKurstyp() == null)
            	programPunkt.setKurstyp("Alla");
            return programPunkt;
        }
    }
    private static class KursProgramMapper implements ParameterizedRowMapper<KursProgram> 
    {

        public KursProgram mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
            KursProgram kursProgram = new KursProgram();
            kursProgram.setKid(rs.getInt("kid"));
            kursProgram.setSeq(rs.getInt("seq"));
            kursProgram.setPpid(rs.getInt("ppid"));                     
            kursProgram.setNamn(rs.getString("namn"));                        
            return kursProgram;
        }
    }
}
