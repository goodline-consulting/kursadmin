package kursadmin.repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import kursadmin.domain.*; 


public class JdbcKursDao extends SimpleJdbcDaoSupport implements KursDao 
{

	protected final Log logger = LogFactory.getLog(getClass());
	private boolean varTermin;
	private static java.util.Date terminStart, terminSlut;
	private String KursAllQuery = 
					"select kurs.kid, kurs.beteckning, kurs.kursnamn, kurs.niva, kurs.rumsalternativ, kurs.matalternativ, kurs.resealternativ, kursniva.namn, kurs.tid, kurstyp.namn,kurstyp.momssats, kurstyp.momsbak, kurstyp.typavrabatt, kurstyp.rabatter, kurstyp.fakturatyp," +		                                           
					"kurstyp.mathantering, kurstyp.resehantering, kurstyp.rumshantering, kurs.pris, kurs.lkost, kurs.ikost, kurs.mkost, kurs.okost, kurs.lokal, lokal.lokalnamn, kurs.instruktor, instruktor.namn, kurs.veckodag, " +
					"kurs.startdatum, kurs.starttid, kurs.lengd, kurs.status from kurs " + 
					"inner join instruktor on kurs.instruktor = instruktor.iid " +
					"inner join lokal on kurs.lokal = lokal.lid " +
					"inner join kurstyp on kurs.tid = kurstyp.tid " +
					"inner join kursniva on kurs.tid = kursniva.tid and kurs.niva = kursniva.nid ";		
	public JdbcKursDao()
	{
		GregorianCalendar cal;
		varTermin = new GregorianCalendar().get(Calendar.MONTH) < 7;
		if (varTermin)
		{
			cal = new GregorianCalendar();
			cal.set(Calendar.MONTH, 1);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			terminStart = cal.getTime();
			cal.set(Calendar.MONTH, 6);
			cal.set(Calendar.DAY_OF_MONTH, 30);
			terminSlut = cal.getTime();
		}
		else
		{
			cal = new GregorianCalendar();
			cal.set(Calendar.MONTH, 7);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			terminStart = cal.getTime();
			cal.set(Calendar.MONTH, 12);
			cal.set(Calendar.DAY_OF_MONTH, 31);
			terminSlut = cal.getTime();
	
		}			
	}
	
	private void keepAlive()
	{
		try
        {
        	getSimpleJdbcTemplate().queryForInt("select 1"); 
                                       	
        }
        catch (Exception e)
        {
        	return;
        }		
	}
	
	
	public List<Kurs> getKursMiniList() 
    {
		keepAlive();
        return getSimpleJdbcTemplate()
        .query("select kurs.kid, kurs.beteckning, kurs.kursnamn from kurs " +         	   
        	   "order by kurs.startdatum desc",
               new KursMiniMapper());        
    }
	public List<Kurs> getKursMiniList(int pid, Date startDate)
	{
		keepAlive();
        return getSimpleJdbcTemplate()
        .query("select kurs.kid, kurs.beteckning, kurs.kursnamn from kurs " +
        	   "where kid in (select kid from elev where pid = " + pid + ") and " +
        	   " kurs.startdatum > '" + startDate +  "' order by kurs.startdatum",
               new KursMiniMapper());        
    }
	public List<Kurs> getCurrentKursList()
	{
		keepAlive();
        return getSimpleJdbcTemplate().query("select kurs.kid, kurs.beteckning, kurs.kursnamn from kurs where kid IN " +
        		                             "(select kid from kurstillf where kurstillf.dag > curdate()) order by kurs.tid, kurs.niva",
               new KursMiniMapper());        
    }
	public List<Kurs> getCurrentKursMiniList(int kursTyp, int pid)
	{
		keepAlive();
        return getSimpleJdbcTemplate().query("select kurs.kid, kurs.beteckning, kurs.kursnamn from kurs where kurs.tid = " + kursTyp + 
        									"  and kid IN (select kid from kurstillf where kurstillf.dag > curdate()) and " + 
        									"kid in (select kid from elev where pid = " + pid + ")", new KursMiniMapper());        
    }
	// Ger lista med kurser som redan är fakturerade men rabattberättigande och aktuella
	public List<Kurs>  getRabattKursMiniList(int pid, int fakturanr)
	{
		keepAlive();
        return getSimpleJdbcTemplate().query("select kurs.kid, kurs.beteckning, kurs.kursnamn  from kurs " +
        		                            " inner join kurstyp on kurs.tid = kurstyp.tid where kurstyp.typavrabatt <> " + KursTyp.ingenrabatt +  
        									" and kid IN (select kid from kurstillf where kurstillf.dag > curdate()) and" + 
        									" kid in (select kid from elev where pid = " + pid + " and fakturanr <> 0 and fakturanr <> " + fakturanr +  ")" +
        									" and kurs.status <> 1", new KursMiniMapper());        
    }
	public List<Kurs> getDansUtlard(int ppid)
	{
	//select beteckning, kurstillf.dag, kurstillf.seq from kurs join kurstillf on kurstillf.kid = kurs.kid where kurstillf.kid in (select kid from kursprogram where ppid = 379) and seq in (select seq from kursprogram where ppid = 379);
	return null;
	}
	public List<KursAll>  getOfakturerade(int pid)
	{
		keepAlive();
		List<KursAll> kursAll = getSimpleJdbcTemplate()
        .query(KursAllQuery + 
        	   "where kid in (select kid from elev where pid = " + pid + " and fakturanr = 0) order by kurs.startdatum desc", 
			   new KursAllMapper());
        return kursAll;
    }
	public List<KursAll> getKursList(Date startDate) 
    {
		
		keepAlive();
        List<KursAll> kursAll = getSimpleJdbcTemplate()
        .query(KursAllQuery +  " where kurs.startdatum >= '" + startDate +  "' order by kurs.startdatum desc", new KursAllMapper());
        return kursAll;
    }
	
	public List<KursAll> getKursList(String filter1, String filter2, int filterType)
	{
		keepAlive();
		/*
		 * filtertypes:
		 * 1 = Beteckning
		 * 2 = Kurstyp
		 * 3 = Kurstyp och nivÂ
		 * 4 = lokal
		 * 5 = instruktor
		 * 6 = lista med kurs id:n
		 * 7 = Samma som 1 fast i tidsordning
		 * 8 = Samma som 6 fast sorterat bara efter nivå
		 */
		 String baseQuery = KursAllQuery; 
		 String orderQuery = " order by kurs.startdatum desc";
		 String filterQuery = null;
		 switch (filterType)
		 {
			 case 1:
				 filterQuery = "where kurs.beteckning like '" + filter1 + "%'";
				 orderQuery  = " order by kurs.beteckning";
				 break;
			 case 2:
				 filterQuery = "where kurs.tid = " + filter1;
				 break;				 
			 case 3:
				 filterQuery = "where kurs.tid = " + filter1 + " and kurs.niva = " + filter2;
				 break;
			 case 4:
				 filterQuery = "where kurs.lokal = " + filter1;
				 break;
			 case 5:
				 filterQuery = "where kurs.instruktor = " + filter1;
				 break;	 
			 case 6:
				 filterQuery = "where kurs.kid in (" + filter1 + ")";
				 orderQuery  = " order by lokal, niva";
				 break;	 	
			 case 7:
				 filterQuery = "where kurs.beteckning like '" + filter1 + "%'";
				 orderQuery  = " order by kurs.startdatum desc";
				 break;	 
			 case 8:
				 filterQuery = "where kurs.kid in (" + filter1 + ")";
				 orderQuery  = " order by niva";
				 break;	 	 
		 } // switch
				 
		 List<KursAll> kursAll = getSimpleJdbcTemplate()
	        					.query(baseQuery + filterQuery + orderQuery,
	        				     new KursAllMapper());
	        return kursAll;
	}
	public List<KursAll> getKursList(int pid)
	{		
		keepAlive(); 
		String baseQuery = KursAllQuery;
		String orderQuery = " order by kurs.startdatum desc";
	    String filterQuery = "where kid in (select kid from elev where pid = " + pid + ")";
		
		 List<KursAll> kursAll = getSimpleJdbcTemplate()
	        					.query(baseQuery + filterQuery + orderQuery,
	        				     new KursAllMapper());
	        return kursAll;
	}
	public List<KursAll> getKopieKandidater(int tid, int nid)
	{
		keepAlive();
        List<KursAll> kursAll = getSimpleJdbcTemplate()
        .query(KursAllQuery +
			   "where kurs.tid = " + tid  +  " and kurs.niva = " + nid + " and status <> 1", 
			   new KursAllMapper());
        return kursAll;
    
	}
	public KursAll getKursAll(int kid)
    {
		keepAlive();
		String 	query = KursAllQuery + " where kurs.kid = " + kid;
 	   					
    	
    	KursAll kursAll = getSimpleJdbcTemplate().query(query, new KursAllMapper()).get(0);
    	List<KursTillf> tillf = getSimpleJdbcTemplate().query("select * from kurstillf where kurstillf.kid = " + kid, 
                new KursTillfMapper());    	
    	List<java.util.Date> lista = new ArrayList<java.util.Date>();
    	for (KursTillf kursTillf: tillf)
    		lista.add(new java.util.Date(kursTillf.getDag().getTime()));
    	kursAll.setKurstillf(lista);
        return kursAll; 
        
    }
    
    public Kurs getKurs(int kid)
    {
    	String 	query = "select * from kurs where kid = " + kid;
    	keepAlive();    	
    	Kurs kurs = getSimpleJdbcTemplate().query(query, new KursMapper()).get(0);    	
        return kurs; 
        
    }
    
    public int getOid(int kid)
    {
    	String 	query = "select oid from kurstyp, kurs where kid = " + kid + " and kurstyp.tid = kurs.tid";
    	keepAlive();    	
    	return getSimpleJdbcTemplate().queryForInt(query);    	
        
    }
    
    public void updateKurs(Kurs kurs) 
    {
    	keepAlive();
        getSimpleJdbcTemplate().update(        		
            "update kurs set " + 
            "beteckning = :beteckning, " +
            "kursnamn = :kursnamn, " +
            "niva = :niva, " +
            "tid = :tid," +
            "pris = :pris, " + 
            "lokal = :lokal, " +
            "ikost = :ikost, " +
            "lkost = :lkost, " +
            "mkost = :mkost, " +
            "okost = :okost, " +
            "instruktor = :instruktor, " +
            "veckodag = :veckodag, " +
            "startdatum = :startdatum, " +
            "starttid = :starttid, " +
            "status = :status, " +
            "lengd = :lengd, " +
            "rumsalternativ = :rumsalternativ," +
            "resealternativ = :resealternativ," +
            "matalternativ = :matalternativ " +
            "where kid = :kid",
            new MapSqlParameterSource().addValue("kid", kurs.getKid())
                                       .addValue("beteckning", kurs.getBeteckning())
            						   .addValue("kursnamn", kurs.getKursnamn())
                                       .addValue("niva", kurs.getNiva())
                                       .addValue("tid", kurs.getTid())
                                       .addValue("pris", kurs.getPris())                                       
                                       .addValue("lokal", kurs.getLokal())
                                       .addValue("ikost", kurs.getIkost())
                                       .addValue("lkost", kurs.getLkost())
                                       .addValue("mkost", kurs.getMkost())
                                       .addValue("okost", kurs.getOkost())
                                       .addValue("instruktor", kurs.getInstruktor())
                                       .addValue("veckodag", kurs.getVeckodag())
                                       .addValue("startdatum", kurs.getStartdatum())
                                       .addValue("starttid", kurs.getStarttid())
                                       .addValue("lengd", kurs.getLengd())
                                       .addValue("status", kurs.getStatus())
                                       .addValue("rumsalternativ", kurs.getRumsalternativ())
        							   .addValue("resealternativ", kurs.getResealternativ())	
                                       .addValue("matalternativ", kurs.getMatalternativ()));        
    }
    
    
    public void deleteKurs(int kid)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update("delete from nvaro where kid = ?", kid);
    	getSimpleJdbcTemplate().update("delete from elev where kid = ?", kid);
    	getSimpleJdbcTemplate().update("delete from kurstillf where kid = ?", kid);
    	getSimpleJdbcTemplate().update("delete from kurs where kid = ?", kid);
    	getSimpleJdbcTemplate().update("delete from kursprogram where kid = ?", kid);
    }
    
    public void insertKurs(Kurs kurs)
    {
    	keepAlive();
    	getSimpleJdbcTemplate().update(
                "insert into kurs (beteckning, kursnamn, niva, tid, pris, ikost, lkost, mkost, okost, lokal, instruktor, veckodag, startdatum, starttid, lengd, status, rumsalternativ, resealternativ, matalternativ) values(:beteckning, :kursnamn, :niva, :tid, :pris, :ikost, :lkost, :mkost, :okost, :lokal, :instruktor, :veckodag, :startdatum, :starttid, :lengd, :status, :rumsalternativ, :resealternativ, :matalternativ)",
                new MapSqlParameterSource().addValue("beteckning", kurs.getBeteckning()) 
                						   .addValue("kursnamn", kurs.getKursnamn())
                                           .addValue("niva", kurs.getNiva())
                                           .addValue("tid", kurs.getTid())
                                           .addValue("pris", kurs.getPris())       
                                           .addValue("ikost", kurs.getIkost())
                                           .addValue("lkost", kurs.getLkost())
                                           .addValue("mkost", kurs.getMkost())
                                           .addValue("okost", kurs.getOkost())
                                           .addValue("lokal", kurs.getLokal())
                                           .addValue("instruktor", kurs.getInstruktor())
                                           .addValue("veckodag", kurs.getVeckodag())
                                           .addValue("startdatum", kurs.getStartdatum())
                                           .addValue("starttid", kurs.getStarttid())
                                           .addValue("lengd", kurs.getLengd())
                                           .addValue("status", kurs.getStatus())
                                           .addValue("rumsalternativ", kurs.getRumsalternativ())
            							   .addValue("resealternativ", kurs.getResealternativ())	
                                           .addValue("matalternativ", kurs.getMatalternativ()));  
    }
    
    public void updateKursAll(KursAll kursAll)    
    {    	
    	
    	int i = 0;
    	int kid = kursAll.getKid();
    	keepAlive();
    	getSimpleJdbcTemplate().update("delete from kurstillf where kid = :kid" , new MapSqlParameterSource().addValue("kid", kursAll.getKid()));
    	for (java.util.Date tillf: kursAll.getKurstillf())
    	{	
    		if (i == 0)
    			kursAll.setStartdatum(new java.sql.Date(tillf.getTime()));
    		i++;
    		getSimpleJdbcTemplate().update("insert into kurstillf (seq, kid, dag) values(" + i + ", " + kid + ", :dag)" , 
                    new MapSqlParameterSource().addValue("dag", new java.sql.Date(tillf.getTime())));
    	}	    	
    	updateKurs(ToKurs(kursAll));  
    	getSimpleJdbcTemplate().update("delete from kursprogram  where kid = :kid and seq not in (select seq from kurstillf where kurstillf.kid = :kid)" , new MapSqlParameterSource().addValue("kid", kursAll.getKid()));
    }
    
    public void insertKursAll(KursAll kursAll)
    {
    	int i = 0;  
    	keepAlive();
    	for (java.util.Date tillf: kursAll.getKurstillf())
    	{
    		if (i == 0)
    		{    	    	
    			kursAll.setStartdatum(new java.sql.Date(tillf.getTime()));
    			insertKurs(ToKurs(kursAll));
    			
    		}	
    		i++;
    		getSimpleJdbcTemplate().update("insert into kurstillf (seq, kid, dag) values(" + i + ",last_insert_id() , :dag)" , 
                    new MapSqlParameterSource().addValue("dag", new java.sql.Date(tillf.getTime())));
    	}	
    	   	
    }
   
    private Kurs ToKurs(KursAll kursAll)
    {
    	Kurs kurs = new Kurs();
    	kurs.setBeteckning(kursAll.getBeteckning());    	
    	kurs.setInstruktor(kursAll.getInstruktor());
    	kurs.setKid(kursAll.getKid());
    	kurs.setKursnamn(kursAll.getKursnamn());
    	kurs.setLengd(kursAll.getLengd());
    	kurs.setLokal(kursAll.getLokal());
    	kurs.setNiva(kursAll.getNiva());
    	kurs.setTid(kursAll.getTid());
    	kurs.setPris(kursAll.getPris());
    	kurs.setIkost(kursAll.getIkost());
    	kurs.setLkost(kursAll.getLkost());
    	kurs.setMkost(kursAll.getMkost());
    	kurs.setOkost(kursAll.getOkost());
    	kurs.setStartdatum(kursAll.getStartdatum());
    	kurs.setStarttid(kursAll.getStarttid());
    	kurs.setVeckodag(kursAll.getVeckodag());
    	kurs.setStatus(kursAll.getStatus());
    	kurs.setMatalternativ(kursAll.getMatalternativ());
    	kurs.setResealternativ(kursAll.getResealternativ());
    	kurs.setRumsalternativ(kursAll.getRumsalternativ());
    	return kurs;
    }
    
    private static class KursMapper implements ParameterizedRowMapper<Kurs> 
    {
    	
        public Kurs mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
            Kurs kurs = new Kurs();
            kurs.setKid(rs.getInt("kid"));
            kurs.setBeteckning(rs.getString("beteckning"));
            kurs.setInstruktor(rs.getInt("instruktor"));
            kurs.setKursnamn(rs.getString("kursnamn"));
            kurs.setLengd(rs.getInt("lengd"));
            kurs.setLokal(rs.getInt("lokal"));
            kurs.setNiva(rs.getInt("niva"));
            kurs.setTid(rs.getInt("tid"));
            kurs.setPris(rs.getInt("pris"));
            kurs.setIkost(rs.getInt("ikost"));
            kurs.setLkost(rs.getInt("lkost"));
            kurs.setMkost(rs.getInt("mkost"));
            kurs.setOkost(rs.getInt("okost"));
            kurs.setStartdatum(rs.getDate("startdatum"));
            kurs.setStarttid(rs.getString("starttid"));
            kurs.setVeckodag(rs.getString("veckodag"));
            kurs.setStatus(rs.getInt("status"));
            kurs.setMatalternativ(rs.getString("matalternativ"));
            kurs.setResealternativ(rs.getString("resealternativ"));
            kurs.setRumsalternativ(rs.getString("rumsalternativ"));
            return kurs;
        }
    }
    private static class KursMiniMapper implements ParameterizedRowMapper<Kurs> 
    {
    	
        public Kurs mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
            Kurs kurs = new Kurs();
            kurs.setKid(rs.getInt("kid"));
            kurs.setBeteckning(rs.getString("beteckning"));           
            kurs.setKursnamn(rs.getString("kursnamn"));                        
            return kurs;
        }
    }
    private static class KursAllMapper implements ParameterizedRowMapper<KursAll> 
    {
    	
        public KursAll mapRow(ResultSet rs, int rowNum) throws SQLException 
        {
            KursAll kursAll = new KursAll();
            kursAll.setKid(rs.getInt("kid"));
            kursAll.setBeteckning(rs.getString("beteckning"));
            kursAll.setInstruktor(rs.getInt("instruktor"));                                             
            kursAll.setInstruktorNamn(rs.getString("instruktor.namn"));
            kursAll.setKursnamn(rs.getString("kursnamn"));
            kursAll.setLengd(rs.getInt("lengd"));
            kursAll.setLokal(rs.getInt("lokal"));
            kursAll.setLokalNamn(rs.getString("lokalnamn"));
            kursAll.setNiva(rs.getInt("niva"));
            kursAll.setTid(rs.getInt("tid"));
            kursAll.setPris(rs.getInt("pris"));
            kursAll.setIkost(rs.getInt("ikost"));
            kursAll.setLkost(rs.getInt("lkost"));
            kursAll.setMkost(rs.getInt("mkost"));
            kursAll.setOkost(rs.getInt("okost"));
            kursAll.setStartdatum(rs.getDate("startdatum"));
            kursAll.setStarttid(rs.getString("starttid"));
            kursAll.setVeckodag(rs.getString("veckodag"));
            kursAll.setKursTyp(rs.getString("kurstyp.namn"));
            kursAll.setKursNiva(rs.getString("kursniva.namn"));
            kursAll.setStatus(rs.getInt("status"));
            kursAll.setMomssats(rs.getDouble("momssats"));
            kursAll.setMomsbak(rs.getDouble("momsbak"));
            kursAll.setTypavrabatt(rs.getInt("typavrabatt"));
            kursAll.setRabatter(rs.getString("rabatter"));
            kursAll.setFakturatyp(rs.getInt("fakturatyp"));
            kursAll.setMathantering(rs.getBoolean("mathantering"));
            kursAll.setResehantering(rs.getBoolean("resehantering"));
            kursAll.setRumshantering(rs.getBoolean("rumshantering"));
            kursAll.setMatalternativ(rs.getString("matalternativ"));
            kursAll.setResealternativ(rs.getString("resealternativ"));
            kursAll.setRumsalternativ(rs.getString("rumsalternativ"));
            if (kursAll.getStartdatum().after(terminStart) &&  kursAll.getStartdatum().before(terminSlut))
            	kursAll.setThisSemester(true);
            return kursAll;
        }
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
