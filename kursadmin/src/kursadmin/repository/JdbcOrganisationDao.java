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
import kursadmin.domain.Organisation;



public class JdbcOrganisationDao extends SimpleJdbcDaoSupport implements OrganisationDao 
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
	public List<Organisation> getOrgList()
	{
		keepAlive();
        List<Organisation> orgs = getSimpleJdbcTemplate().
                               query("select * from organisation order by orgnamn", 
                               new OrganisationMapper());
        return orgs;
	}
    public Organisation getOrganisation(int oid)
    {
    	String 	query = "select * from organisation where oid = " + oid;
    	keepAlive();
    	List<Organisation> orgs = getSimpleJdbcTemplate().query(query, new OrganisationMapper());
    	if (orgs.size() > 0)
    		return orgs.get(0);
    	else
    		return null;
    }
    public Organisation getOrganisation(String orgnr)
    {
    	String 	query = "select * from organisation where orgnr = " + orgnr;
    	keepAlive();
    	List<Organisation> orgs = getSimpleJdbcTemplate().query(query, new OrganisationMapper());
    	if (orgs.size() > 0)
    		return orgs.get(0);
    	else
    		return null;
    }
    public void updateOrganisation(Organisation org)
    {
    	keepAlive();
        getSimpleJdbcTemplate().update(
            "update organisation set orgtyp = :orgtyp, orgnr = :orgnr, orgnamn = :orgnamn, momsnr = :momsnr, momsredo = :momsredo, bankgiro = :bankgiro, plusgiro = :plusgiro, kontaktperson = :kontaktperson, adress = :adress,  postnr = :postnr, postadress = :postadress, email = :email, telefon = :telefon, info = :info where oid = :oid",
            new MapSqlParameterSource().addValue("oid", org.getOid())
            						   .addValue("orgtyp", org.getOrgtyp())
            						   .addValue("orgnr", org.getOrgnr())
            						   .addValue("orgnamn", org.getOrgnamn())
            						   .addValue("momsnr", org.getMomsnr())
            						   .addValue("momsredo", org.isMomsredo())
            						   .addValue("bankgiro", org.getBankgiro())
            						   .addValue("plusgiro", org.getPlusgiro())
            						   .addValue("kontaktperson", org.getKontaktperson())
            						   .addValue("adress", org.getAdress())
                                       .addValue("postnr", org.getPostnr())
            						   .addValue("postadress", org.getPostadress())
            						   .addValue("email", org.getEmail()) 
            						   .addValue("telefon", org.getTelefon())
                                       .addValue("info", org.getInfo()));		
    }
    public void insertOrganisation(Organisation org)
    {
    	keepAlive();
        getSimpleJdbcTemplate().update(
            "insert into organisation (orgtyp, orgnr, orgnamn, momsnr, momsredo, bankgiro, plusgiro, kontaktperson, adress, postnr, postadress, email, telefon, info) values(:orgtyp, :orgnr, :orgnamn, :momsnr, :momsredo, :bankgiro, :plusgiro, :kontaktperson, :adress, :postnr, :postadress, :email, :telefon, :info)",
            new MapSqlParameterSource().addValue("orgtyp", org.getOrgtyp())
            						   .addValue("orgnr", org.getOrgnr())
            						   .addValue("orgnamn", org.getOrgnamn())
            						   .addValue("momsnr", org.getMomsnr())
            						   .addValue("momsredo", org.isMomsredo())
            						   .addValue("bankgiro", org.getBankgiro())
            						   .addValue("plusgiro", org.getPlusgiro())
            						   .addValue("kontaktperson", org.getKontaktperson())
            						   .addValue("adress", org.getAdress())
                                       .addValue("postnr", org.getPostnr())
            						   .addValue("postadress", org.getPostadress())
            						   .addValue("email", org.getEmail()) 
            						   .addValue("telefon", org.getTelefon())
                                       .addValue("info", org.getInfo()));	
    }
    public void deleteOrganisation(int oid)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update("delete from organisation where oid = ?", oid);
    }
    
    private static class OrganisationMapper implements ParameterizedRowMapper<Organisation> 
    {

        public Organisation mapRow(ResultSet rs, int rowNum) throws SQLException 
        {       	
        	Organisation org = new Organisation();
            org.setOid(rs.getInt("oid"));
            org.setOrgtyp(rs.getInt("orgtyp"));
            org.setOrgnr(rs.getString("orgnr"));
            org.setOrgnamn(rs.getString("orgnamn"));
            org.setMomsnr(rs.getString("momsnr"));
            org.setMomsredo(rs.getBoolean("momsredo"));
            org.setBankgiro(rs.getString("bankgiro"));
            org.setPlusgiro(rs.getString("plusgiro"));
            org.setKontaktperson(rs.getString("kontaktperson"));
            org.setAdress(rs.getString("adress"));
            org.setPostadress(rs.getString("postadress"));
            org.setPostnr(rs.getString("postnr"));
            org.setEmail(rs.getString("email"));
            org.setTelefon(rs.getString("telefon"));
            org.setInfo(rs.getString("info"));
            
            return org;
        }
    }
}
