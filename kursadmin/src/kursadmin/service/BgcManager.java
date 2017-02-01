
package kursadmin.service;

import java.io.BufferedReader;
//import java.io.File;
import java.io.FileNotFoundException;
//import java.io.FileReader;
import java.io.IOException;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import kursadmin.domain.Betalning;
import kursadmin.domain.Elev;
import kursadmin.domain.Faktura;
import kursadmin.domain.Fakturarad;
import kursadmin.domain.KursAll;
import kursadmin.domain.MailConfig;
import kursadmin.domain.Person;
import kursadmin.repository.BetalningDao;
import kursadmin.repository.ElevDao;
import kursadmin.repository.KursDao;
import kursadmin.repository.PersonDao;
public class BgcManager implements BgcManagerInterface
{
	private final String _Beg01 ="01BGMAX";
	private final String _Avs05 = "05";
	private final String _Bet20 = "20";
	//private final String _Bet21 = "21";
	private final String _Inf25 = "25";
	private final String _Nam26 = "26";
	//private final String _Adr27 = "27";
	//private final String _Adr28 = "28";
	//private final String _Org29 = "29";
	private final String _Ins15 = "15";
	//private final String _End70 = "70";
	private final String[] betKanaler = {"Elektronisk","Leverantšrsbet","Blankett","Autogiro"};
	
	private BetalningDao betalningDao;
	private ElevDao 	 elevDao;
	private PersonDao 	 personDao;
	private KursDao      kursDao;
	KursAnmalanManagerInterface kursAnmalanManager;
	private ConfigManagerInterface configManager;
	FakturaManagerInterface fakturaManager;
	protected final Log logger = LogFactory.getLog(getClass());
	
	public List<Betalning> getBetalningar(BufferedReader input) throws Exception
	{
		boolean iAvsnitt = false;
		Betalning bet = null;
		ArrayList<Betalning> betList = new ArrayList<Betalning>();
		try 
		{
			
			String line = input.readLine();
			
		
			if (! line.startsWith(_Beg01))
				throw new Exception("Filen saknar korrekt inledningspost (01BGMAX)");
			Calendar cal = new GregorianCalendar(Integer.parseInt(line.substring(24, 28)),
												 Integer.parseInt(line.substring(28, 30)) - 1,
												 Integer.parseInt(line.substring(30, 32)));
			// Vi kollar att filen inte redan Šr inlŠst
			if (betalningDao.DatumExists(cal.getTime()))
			{	
				betList = null;
				throw new Exception("Filen har redan lŠsts in");
			}	
			// Nu gŒr vi in i en loop och lŠser tills slutet
			while ((line = input.readLine()) != null)
			{
				
				// logger.info("HŠr kommer raden " + line);
				if (line.startsWith(_Avs05))
				{
					iAvsnitt = true;
					// Kolla eventuellt att bankgironumret stŠmmer
					continue;
				}
				if (line.startsWith(_Bet20))
				{
					if (bet != null)
					{						
						betList.add(bet);
					}						
					bet = new Betalning();
					bet.setAmount(Double.parseDouble(line.substring(37, 53) + "." + line.substring(53, 55)));
					int refkod = Integer.parseInt(line.substring(55, 56));
					// HŠr kan ytterligare kodning komma om man vill hantera fler varianter pŒ refnr
					if (refkod == 2)
						bet.setRefnr(line.substring(12, 37).trim());
					else
						bet.setRefnr(null);
					bet.setBetaldatum(cal.getTime());
					// bet.setBetalkanal(betKanaler[Integer.parseInt(line.substring(56, 57)) - 1]);
					bet.setBetalkanal("Bgmax");
					continue;
				}
				if (line.startsWith(_Inf25))
				{
					bet.getInfo().append((bet.getInfo().length() > 0 ? "\n" : "") + line.substring(2, 52).trim());
				}
				if (line.startsWith(_Nam26))
				{
					bet.setName(line.substring(2, 37).trim());
				}
				
				if (line.startsWith(_Ins15))
				{					
					// LŠgg in sista betalningsposten,Kolla belopp osv, avlsuta avsnittet
					if (bet != null)
					{
						betList.add(bet);
					}
					bet = null;
					iAvsnitt = false;
					continue;
				}
				// Hoppa alla andra posttyper
				// System.out.println(line);
				continue;
				
				/*
				if (line.startsWith(_Bet21))
				{
					continue;
				} 
				if (line.startsWith(_Adr27))
				{
					continue;
				}
				if (line.startsWith(_Adr28))
				{
					continue;
				}
				if (line.startsWith(_Org29))
				{
					continue;
				}
				*/
				
			}						
			input.close();
		} 
		catch (FileNotFoundException e) 
		{		
			throw new Exception(e.getMessage());
		} 
		catch (IOException e) 
		{
			throw new Exception("Unknown I/O Exception");
		}			
		
		return betList;
		
	}
	public void prickaAv(List<Betalning> betalningar)
	{		
		KursAll kurs = null;
		Faktura faktura;
		double summa = 0;
		double placerat = 0;
		boolean mailad = false;
		for(Betalning bet: betalningar)
		{
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// logger.info(sdf.format(bet.betDat) + " " + bet.amount + " " + bet.name + " [" + bet.refnr + "] (" + bet.info + ")" + " " + bet.betKanal);
			if (bet.getRefnr() == null)
			{
				if (bet.getName() == null)
					bet.setName("OkŠnd inbetalare");
				bet.setRefnr("");
				bet.setFakturanr(0);
				continue;				
			}
			faktura = fakturaManager.getFaktura(bet.getFakturanr());
			if (faktura == null)
			{
				bet.setInfo(bet.getInfo().append("Referensnummer " + bet.getFakturanr() + " kan inte hittas"));
				bet.setFakturanr(0);
				continue;
			}
			summa = bet.getAmount();
			placerat = faktura.getBelopp() - faktura.getBetalt();
			if (placerat > summa )
				placerat = summa;
			Person person = personDao.getPerson(faktura.getPid());
			if (!(person.getEmail() == null) && !person.getEmail().equals(""))
				mailad = true;
			if (bet.getName() == null)
				bet.setName(person.getFnamn() + " " + person.getEnamn());
			else if (!bet.getName().equals(person.getFnamn() + " " + person.getEnamn()))
				bet.setName(person.getFnamn() + " " + person.getEnamn() + "(" + bet.getName() + ")");
			faktura.setBetalt(faktura.getBetalt() + summa);
			faktura.setBetald(bet.getBetaldatum());
			// Bygg upp en lista med elever
			ArrayList<Elev> elever = new ArrayList<Elev>();
			for (Fakturarad fakturarad: faktura.getRader())
			{    			    				
				kurs = kursDao.getKursAll(fakturarad.getKid());
				Elev elev = elevDao.getElev(kurs.getKid(), person.getPid());
				if (elev == null)
				// Eleven kanske inte gŒr pŒ kursen lŠngre mne vi behšver en elev fšr att fšrdela betalningen rŠtt.	
				{
					elev = new Elev();
					elev.setNy(true); // fšr att kolla att det Šr en fejk elev
					elev.setKid(fakturarad.getKid());
					elev.setPid(person.getPid());
					elev.setPris((int)fakturarad.getBelopp());
				}				
				elever.add(elev);
			}    	
			for (Elev elev: elever)
			{
				elev.setBetaldatum(faktura.getBetald());
    			if (summa >= elev.getPris())
    			{	
    				elev.setBetalt(elev.getPris());
    				summa -= elev.getPris();
    			}	
    			else
    			{
    				elev.setBetalt((int)summa);
    				summa = 0;
    			}
    			elev.setBbekr(mailad);
    			elev.setBetaldatum(faktura.getBetald());
    			if (!elev.isNy())
    				elevDao.updateElev(elev);
			}			
			
			// Skicka mail innan betaltbelopp justeras. PŒ sŒ sŠtt kan mailfunktionen se att man betalt fšr mycket.
			kursAnmalanManager.sendBetBekr(faktura);
			// Om betalningen šverskrider kostnaden sŒ lŠggs šverskjutande del pŒ personens saldo.
			// Nej, En avknoppad betalning pŒ ŒterstŒende summa skapas. Det vanliga Šr att nŒgon betalat fšr nŒgon annan
			// Den nyskapade betalningen kan sedan placeras pŒ valfri kunds faktura eller lŠggas som tillgodo pŒ kunden.
			
			if (faktura.getBetalt() > faktura.getBelopp())
			{
				bet.setInfo(bet.getInfo().append("Betalning šverskrider fakturabeloppet!"));				
				// Placera pengarna pŒ personens saldo				
				/*
				person.setSaldo(person.getSaldo()  + - (faktura.getBetalt() - faktura.getBelopp()));
				personDao.updatePerson(person);
				*/
			
				// justera betalt beloppet till fakturabeloppet.
				faktura.setBetalt(faktura.getBelopp());				
			}
			// Kolla om den inte Šr fullt betald och sŠtt i sŒ fall betald = null
			bet.setPlacerat(placerat);
			if (bet.getAmount() == placerat)
				bet.setPlacerad(true);
			else
				bet.setPlacerad(false);
			if (faktura.getBelopp() > faktura.getBetalt())
				faktura.setBetald(null);
			fakturaManager.updateFaktura(faktura);
			// betalningDao.updateBetalning(bet);
		} 			
	}
	
	public void sparaBetalningar(List<Betalning> betalningar)
	{
		betalningDao.insertBetalningar(betalningar);
	}
	
	public List<Betalning> searchBetalningar(Date fromDat, Date toDat)
	{
		if (fromDat == toDat)
			return betalningDao.getBetalningList(fromDat);
		else 
			return betalningDao.getBetalningList(fromDat, toDat);
	}
	
	public List<Betalning> searchOplaceradeBetalningar()
	{
 		return betalningDao.getOplacerade();
	}
	
	public List<Betalning> searchPersonBetalningar(String name)
	{
 		return betalningDao.getPersonBetalning(name);
	}
	
	public List<Date> getDates()
	{
		return betalningDao.getDates();
	}
	public void deleteBetalning(int id)
	{
		this.betalningDao.deleteBetalning(id);
	}
		
	public void setBetalningDao(BetalningDao betalningDao)
	{
		this.betalningDao = betalningDao;
	}
	public void setElevDao(ElevDao elevDao)
	{
		this.elevDao = elevDao;
	}
	public void setPersonDao(PersonDao personDao)
	{
		this.personDao = personDao;
	}
	public void setKursDao(KursDao kursDao)
	{
		this.kursDao = kursDao;
	}
	public void setKursAnmalanManager(KursAnmalanManagerInterface kursAnmalanManager)
    {
    	this.kursAnmalanManager = kursAnmalanManager;
    }
	public void setConfigManager(ConfigManagerInterface configManager)
    {
    	this.configManager = configManager;
    }
	public void setfakturaManager(FakturaManagerInterface fakturaManager) 
    {
        this.fakturaManager = fakturaManager;
    }
}
