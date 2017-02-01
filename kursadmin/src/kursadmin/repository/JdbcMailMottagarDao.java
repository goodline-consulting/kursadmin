package kursadmin.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import kursadmin.domain.MailMottagare;

public class JdbcMailMottagarDao extends SimpleJdbcDaoSupport implements MailMottagarDao 
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
	// Alla som inte går kurs fšr nŠrvarande    	
	public List<MailMottagare> getMailMottagare()
	{
		keepAlive();
	    List<MailMottagare> lista = getSimpleJdbcTemplate().query(
	    		"select pid, email, fnamn, enamn  from person where email <> '' and pid not in" +
	    		"(select pid from elev where kid in" +
	    		"(select kid from kurs where kid in" + 
	    		"(select kid from kurstillf where kurstillf.dag > DATE_SUB(CURDATE(),INTERVAL 30 DAY)))) order by enamn, fnamn",
               new MailMottagareMapper());
	    return lista;

	}
	// Alla som har obetalda fakturor
	public List<MailMottagare> getMailMottagareEjBetalt()
	{
		keepAlive();
	    List<MailMottagare> lista = getSimpleJdbcTemplate().query(
	    		"select pid, email, fnamn, enamn  from person where email <> '' and pid in " +
	    		"(select pid from faktura where faktura.betald is null) order by enamn, fnamn",
               new MailMottagareMapper());
	    return lista;

	}
	// Alla som går viss kurs
    public List<MailMottagare> getMailMottagareKurs(int kid)
    {
    	keepAlive();
    	List<MailMottagare> lista = getSimpleJdbcTemplate().query(
	    		"select pid, email, fnamn, enamn  from person where email <> '' and pid in" +
	    		"(select pid from elev where kid = " + kid + ") order by enamn, fnamn",	    		
                new MailMottagareMapper());
	    return lista;
    }
 // Alla som går viss kurs och ej har betalat
    public List<MailMottagare> getMailMottagareKursEjBetalt(int kid)
    {
    	keepAlive();
    	List<MailMottagare> lista = getSimpleJdbcTemplate().query(
	    		"select pid, email, fnamn, enamn  from person where email <> '' and pid in" +
	    		"(select pid from elev where kid = " + kid + " and betalt < pris) order by enamn, fnamn",	    		
                new MailMottagareMapper());
	    return lista;
    }
    public List<MailMottagare> getMailMottagareNiva(int kursTyp, int niva)
    {
    	keepAlive();
    	List<MailMottagare> lista;
    	if (niva > 0)
    		lista = getSimpleJdbcTemplate().query(
    			"select pid, email, fnamn, enamn  from person where email <> '' and pid in" +
	    		"(select pid from elev where kid in" +
	    		"(select kid from kurs where kurs.tid = " + kursTyp + " and kurs.niva = " + niva + " and kid in" + 
	    		"(select kid from kurstillf where kurstillf.dag > DATE_SUB(CURDATE(),INTERVAL 30 DAY)))) order by enamn, fnamn",
                new MailMottagareMapper());
    	else
    		lista = getSimpleJdbcTemplate().query(
        			"select pid, email, fnamn, enamn  from person where email <> '' and pid in" +
    	    		"(select pid from elev where kid in" +
    	    		"(select kid from kurs where kid in" + 
    	    		"(select kid from kurstillf where kurstillf.dag > DATE_SUB(CURDATE(),INTERVAL 30 DAY)))) order by enamn, fnamn",
                    new MailMottagareMapper());
    		
	    return lista;
    }
    public List<MailMottagare> getMailMottagareLokal(int lokal)
    {
    	keepAlive();
    	List<MailMottagare> lista = getSimpleJdbcTemplate().query(
    			"select pid, email, fnamn, enamn  from person where email <> '' and pid in" +
	    		"(select pid from elev where kid in" +
	    		"(select kid from kurs where kurs.lokal = " + lokal + " and kid in" + 
	    		"(select kid from kurstillf where kurstillf.dag > DATE_SUB(CURDATE(),INTERVAL 30 DAY)))) order by enamn, fnamn",new MailMottagareMapper());
	    return lista;
    }
    public List<MailMottagare> getMailMottagareInstruktor(int instruktor)
    {
    	keepAlive();
    	List<MailMottagare> lista;
    	if (instruktor == 0)
    		 lista = getSimpleJdbcTemplate().query(
       			"select iid, email, namn  from instruktor where email <> ''", new MailMottagareMapper2());
    	else
    		lista = getSimpleJdbcTemplate().query(
    			"select pid, email, fnamn, enamn  from person where email <> '' and pid in" +
	    		"(select pid from elev where kid in" +
	    		"(select kid from kurs where kurs.instruktor = " + instruktor + " and kid in" + 
	    		"(select kid from kurstillf where kurstillf.dag > DATE_SUB(CURDATE(),INTERVAL 30 DAY)))) order by enamn, fnamn",new MailMottagareMapper());
	    return lista;
    }
    public List<MailMottagare> getMailMottagare(String pattern, boolean isEnamn)
    {
    	keepAlive();
    	String query;
    	if (pattern.length() == 0)
    		query = "select pid, email, fnamn, enamn from person where email <> '' order by enamn, fnamn";
    	else if (isEnamn)
    		query = "select pid, email, fnamn, enamn from person where email <> '' and enamn like '" + pattern + "%' order by enamn, fnamn";
    	else
    		query = "select pid, email, fnamn, enamn from person where email <> '' and fnamn like '" + pattern + "%' order by enamn, fnamn";    	
        List<MailMottagare> lista = getSimpleJdbcTemplate().
                               query(query, 
                               new MailMottagareMapper());
        return lista;
    }
     
    private static class MailMottagareMapper implements ParameterizedRowMapper<MailMottagare> 
    {

        public MailMottagare mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
            MailMottagare mailmot = new MailMottagare();
            mailmot.setPid(rs.getInt("pid"));
            mailmot.setEmail(rs.getString("email"));
            mailmot.setNamn(rs.getString("fnamn") + " " + rs.getString("enamn"));
            return mailmot;
        }
    }
    private static class MailMottagareMapper2 implements ParameterizedRowMapper<MailMottagare> 
    {

        public MailMottagare mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
            MailMottagare mailmot = new MailMottagare();
            mailmot.setPid(rs.getInt("iid"));
            mailmot.setEmail(rs.getString("email"));
            mailmot.setNamn(rs.getString("namn"));
            return mailmot;
        }
    }
}
