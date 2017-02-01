package kursadmin.service;

import kursadmin.domain.Elev;
import kursadmin.domain.Faktura;
import kursadmin.domain.Fakturarad;
import kursadmin.domain.GrundConfig;
import kursadmin.domain.Kurs;
import kursadmin.domain.KursAll;
import kursadmin.domain.KursAnmalan;
import kursadmin.domain.KursTyp;
import kursadmin.domain.MailConfig;
import kursadmin.domain.PathConfig;
import kursadmin.domain.Person;
import kursadmin.repository.ElevDao;
import kursadmin.repository.KursAnmalanDao;
import kursadmin.repository.KursDao;
import kursadmin.repository.PersonDao;
import kursadmin.service.pdfgen.GoodLineLdFakturaImpl;


import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.itextpdf.text.DocumentException;

class KursAnmalanManager implements KursAnmalanManagerInterface
{
	private KursAnmalanDao kursAnmalanDao;
	private PersonDao personDao;
	private KursDao kursDao;
	private ElevDao elevDao;
	private MailManagerInterface mailManager;
	private ConfigManagerInterface configManager;
	private FakturaManager fakturaManager;
	private KursTypManagerInterface kursTypManager;
	
	protected final Log logger = LogFactory.getLog(getClass());
	public List<KursAnmalan> getKursAnmalanList()
	{
		return kursAnmalanDao.getKursAnmalanList();
	}
    public KursAnmalan getKursAnmalan(int aid)
    {
    	return kursAnmalanDao.getKursAnmalan(aid);
    }
    public void insertKursAnmalan(KursAnmalan kursAnmalan)
    {
    	kursAnmalanDao.insertKursAnmalan(kursAnmalan);
    }
    
    public KursAll creKursAnmalan(int kid, String fnamn, String enamn, String telefon, String email, String info)
    {

    	KursAll kursAll = kursDao.getKursAll(kid);
    	KursAnmalan ka = new KursAnmalan();
    	ka.setKid(kid);
    	ka.setBeteckning(kursAll.getBeteckning());
    	ka.setFnamn(fnamn);
    	ka.setEnamn(enamn);
    	ka.setTelefon(telefon);
    	ka.setEmail(email);
    	ka.setHandled(false);
    	ka.setTidpunkt(new Date());
    	ka.setInfo(info);
    	insertKursAnmalan(ka);
    	
    	return kursAll;
    }
    public void deleteKursAnmalan(int aid)
    {
    	kursAnmalanDao.deleteKursAnmalan(aid);
    }
    public List<Person> getPersoner(String fnamn, String enamn)
    {
    	return personDao.getPersonList(fnamn, enamn);
    }
    public List<Elev> creKursDeltagare(List<KursAnmalan> kaList) throws Exception   
    {
    	Person person;
    	int pid;
 
    	
    	
    	//List<Kurs> kuList = null;
    	
    	// Hämta första posten och uppdatera eller skapa personen
    	//boolean result = true;
    	if (kaList.get(0).getPid()== 0)
    	{
    		person = new Person();
    		person.setFnamn(kaList.get(0).getFnamn());
    		person.setEnamn(kaList.get(0).getEnamn());
    		person.setEmail(kaList.get(0).getEmail());
    		person.setTelefon(kaList.get(0).getTelefon());
    		pid = personDao.insertPerson(person);
    	}
    	else
    	{	
    		pid = kaList.get(0).getPid();
    		// Passa pÂ att uppdatera mailadress och telefon nummer
    		person = personDao.getPerson(pid);
    		person.setEmail(kaList.get(0).getEmail());    		
    		if ((! (null == kaList.get(0).getTelefon())) && (!(kaList.get(0).getTelefon().length() == 0)))
    			person.setTelefon(kaList.get(0).getTelefon());
    		personDao.updatePerson(person);
    	}
    	
    	// Kolla först att personen inte redan går på någon av kurserna
    	// Passa på och hämta kursAll objektet
    	for (KursAnmalan ka: kaList)
    	{
    		if (elevDao.elevExists(ka.getKid(), pid))
    			throw new Exception(ka.getFnamn() + " " + ka.getEnamn() + " går redan på " + ka.getBeteckning());
    		ka.setPid(pid);
    		ka.setKurs(kursDao.getKursAll(ka.getKid()));
    	}
    	List<Elev> elever = new ArrayList<Elev>();
    	for (KursAnmalan ka: kaList)
    	{
    		
    		//KursAll kurs = kursDao.getKursAll(ka.getKid());
    		
    		Elev elev = new Elev();
    		elev.setAbekr(false);
    		elev.setBbekr(false);
    		elev.setAktiv(true);
    		elev.setBetalt(0);
    		elev.setKid(ka.getKid());   		
    		elev.setPid(ka.getPid());
    		elev.setNamn(ka.getFnamn() + " " + ka.getEnamn());
    		elev.setInfo(ka.getInfo());
    		elev.setBeteckning(ka.getBeteckning());
    		elevDao.insertElev(elev);    
    		elever.add(elev);
    		kursAnmalanDao.deleteKursAnmalan(ka.getAid());
    	}
    	return elever;
    	/*
    	// Skicka bekräftelsemail om allt gått bra och den inte skall hanteras manuellt
    	if ((result == true) && !ka.isHandled())
    	{
    		elev.setAbekr(true);
    		elevDao.updateElev(elev);
    		// För at få tag på referensnumret
    		elev = elevDao.getElev(ka.getKid(), ka.getPid());
    		logger.info(elev.getNamn() + " Refnummer:" + elev.getRefnr());
    		MailConfig mc = configManager.parseMailConfig("anmbekr", kurs, elev);    		
    		mailManager.sendMail(mc, new String[]{person.getEmail()});
    	 }
    	 */
    	
    }
    
    public void sendAnmBekr(Faktura faktura)
    {
    	Person person = personDao.getPerson(faktura.getPid());
    	GrundConfig gc = configManager.getGrundConfig();
    	KursAll kurs = kursDao.getKursAll(faktura.getRader().get(0).getKid());
    	KursTyp kurstyp = kursTypManager.getKursTyp(kurs.getTid());
    	String pdfPath = null;
    	String email = person.getEmail();
    	if (gc.isFakeMail() || email == null)
    		email = gc.getFakturaMail();
    	 
    	MailConfig mc = parseMailConfig("anmbekr", person, faktura);
    	// System.out.println("Classpath:" + System.getProperty("java.class.path"));
    	if (kurstyp.getFakturatyp() == KursTyp.pdfFaktura)
    	{
    		PathConfig pc = configManager.getPathConfig();
    		pdfPath = pc.getBilagor() + "/" + faktura.getFakturanr() + ".pdf";
    		Class cls;
			try 
			{
				cls = Class.forName("kursadmin.service.pdfgen." + kurstyp.getFakturaklass());
				GenPdfFakturaInterface gi = (GenPdfFakturaInterface) cls.newInstance();
				gi.genPdfFaktura(fakturaManager.getXmlFaktura(faktura.getFakturanr()), pdfPath, pc.getBilder());
			} 
			catch (ClassNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (InstantiationException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (IllegalAccessException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (DocumentException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mailManager.sendMailKurs(mc, new String[]{email}, faktura.getFakturanr() + ".pdf");
	    	// Ta bort bilagan så att vi inte blir översvämmade
	    	File f = new File(pdfPath);
	    	f.delete();
    	}
    	else
    		mailManager.sendMailKurs(mc, new String[]{email}, null);
    	
    }
    public void sendBetBekr(Faktura faktura)
    {
    	Person person = personDao.getPerson(faktura.getPid());	
    	GrundConfig gc = configManager.getGrundConfig();
    	String email = person.getEmail();
    	if (gc.isFakeMail() || email == null)
    		email = gc.getBetalMail();
    	
    	MailConfig mc = parseMailConfig("betbekr", person, faktura);
    	mailManager.sendMailKurs(mc, new String[]{email}, null);	
    }    
    private MailConfig parseMailConfig(String type,  Person person, Faktura faktura)
	{
		String rubrik;
		String body;
		String footer;
		String ingress;
		String finish;		
		
		List<KursAll> kurser = new ArrayList<KursAll>();
		for (Fakturarad fakturarad: faktura.getRader())
		{    			    				
			KursAll kurs = kursDao.getKursAll(fakturarad.getKid());
			kurser.add(kurs);
		}    
		
		MailConfig mf = configManager.getMailConfig(type, kurser.get(0).getTid());		
		rubrik = parseText(mf.getRubrik(), kurser.get(0), person, faktura, kurser.size() > 1);				
		mf.setRubrik(rubrik);
		if (type.equals("anmbekr"))
		{	
			ingress = parseText(mf.getIngress(), kurser.get(0), person, faktura, kurser.size() > 1);
			mf.setIngress(ingress);
			body = "";
			//  Huvudtexten sätts en gång för varje kurs
			for (KursAll kurs: kurser)
			{
				body += parseText(mf.getBody(), kurs, person, faktura, kurser.size() > 1);	
			}			
			finish = parseText(mf.getFinish(), kurser.get(0), person, faktura, kurser.size() > 1);
			mf.setFinish(finish);
		}
		else
			body = parseText(mf.getBody(), kurser.get(0), person, faktura, kurser.size() > 1);
		mf.setBody(body);
		footer = parseText(mf.getFooter(), kurser.get(0), person, faktura, kurser.size() > 1);						
		mf.setFooter(footer);
		return mf;		
	}
    
    private String parseText(String text, KursAll kurs, Person person, Faktura faktura, boolean pluralis)
    {
    	String tmpStr = "";
    	String replStr = "";
    	int idx;	
    	if (text == null)
    		return "";
		if (text.indexOf('#') != -1)
		{
			text = text.replaceAll("#refnr", faktura.getOcr());
			text = text.replaceAll("#beteckning", kurs.getBeteckning());
			text = text.replaceAll("#kursnamn", kurs.getKursnamn());
			text = text.replaceAll("#startdag", kurs.getStartdatum().toString());
			text = text.replaceAll("#starttid", kurs.getStarttid());
			text = text.replaceAll("#veckodag", kurs.getVeckodag());
			text = text.replaceAll("#plats", kurs.getLokalNamn());
			text = text.replaceAll("#kursavgift", (int) faktura.getBelopp() + ":-");
			text = text.replaceAll("#fornamn", person.getFnamn());
			text = text.replaceAll("#efternamn", person.getEnamn());
			text = text.replaceAll("#förfallodag", faktura.getBetalas() + "");
			text = text.replaceAll("#betalning", (int) faktura.getBetalt() + ":-");
			text = text.replaceAll("#restbelopp", (int) (faktura.getBelopp() - faktura.getBetalt()) + ":-");
			if ((idx = text.indexOf("#ombetalt")) > -1)
			{
				tmpStr = text;
				text = tmpStr.substring(0, idx);
				if (faktura.getBetalt() >= faktura.getBelopp())
				{
					text = text + tmpStr.substring(idx + 9, tmpStr.indexOf("#slut"));					
				}
				else
				{
					
					if ((idx = tmpStr.indexOf("#annars")) > -1)
					{
						text = text + tmpStr.substring(idx + 7, tmpStr.indexOf('#', idx + 9)); 
					}
				}
				text = text + tmpStr.substring(tmpStr.lastIndexOf("#slut") + 5);
			}
			// Singularis/Pluralis
			idx = 0;
			while ((idx = text.indexOf("#<", idx)) > -1)
			{	
				tmpStr = text.substring(idx, text.indexOf('>', idx + 2) + 1);
				if (pluralis)
					replStr = tmpStr.substring(tmpStr.indexOf('-') + 1, tmpStr.indexOf('>'));
				else
					replStr = tmpStr.substring(2, tmpStr.indexOf('-'));				
				text = text.replaceAll(tmpStr, replStr);
			}
		}
		return text;
    }
    /////////
    public void setKursAnmalanDao(KursAnmalanDao kursAnmalanDao)
    {
    	this.kursAnmalanDao = kursAnmalanDao;
    }
    public void setPersonDao(PersonDao personDao)
    {
    	this.personDao = personDao;
    }
    public void setKursDao(KursDao kursDao)
    {
    	this.kursDao = kursDao;
    }
    public void setElevDao(ElevDao elevDao)
    {
    	this.elevDao = elevDao;
    }
    public void setMailManager(MailManagerInterface mailManager)
    {
    	this.mailManager = mailManager;
    }
    public void setConfigManager(ConfigManagerInterface configManager)
    {
    	this.configManager = configManager;
    }
    public void setFakturaManager(FakturaManager fakturaManager)
    {
    	this.fakturaManager = fakturaManager;
    }
    public void setKursTypManager(KursTypManagerInterface  kursTypManager)
    {
    	this.kursTypManager = kursTypManager;
    }
}










    
    
    
    

