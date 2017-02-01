package kursadmin.service;

import kursadmin.domain.Elev;
import kursadmin.domain.Kurs;
import kursadmin.domain.KursAll;
import kursadmin.domain.KursEkonomi;
import kursadmin.domain.KursTyp;
import kursadmin.domain.StimRapport;
import kursadmin.repository.ElevDao;
import kursadmin.repository.KursDao;
import kursadmin.repository.KursTillfDao;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class KursManager implements KursManagerInterface
{
	private KursDao kursDao;
	private ElevDao elevDao;
	private KursTillfDao kursTillfDao;
	private NvaroManager nvaroManager;
	
	protected final Log logger = LogFactory.getLog(getClass());
	public List<Kurs> getKursMiniList() 
	{
		return kursDao.getKursMiniList();
	}
	public List<Kurs> getKursMiniList(int pid, Date startDate)
	{
		return kursDao.getKursMiniList(pid, startDate);
	}
	public List<Kurs> getRabattKursMiniList(int pid, int fakturanr)
	{
		return kursDao.getRabattKursMiniList(pid, fakturanr);
	}
	public List<KursAll>  getOfakturerade(int pid)
	{
		return kursDao.getOfakturerade(pid);
	}
	public List<Kurs> getCurrentKursList()
	{
		return kursDao.getCurrentKursList();
	}
	public List<KursAll> getKurser(Date startDate)
	{
		return kursDao.getKursList(startDate);
	}
	
	public List<KursAll> getKurser(String filter1, String filter2, int filterType)
	{
		return kursDao.getKursList(filter1, filter2, filterType);
	}
	public List<KursAll> getKurser(int pid)
	{
		return kursDao.getKursList(pid);
	}
	public List<KursAll> getKopieKandidater(int tid, int nid)
	{
		return kursDao.getKopieKandidater(tid, nid);
	}
	public KursAll getKursAll(int kid)
	{
		return kursDao.getKursAll(kid);
	}
	public Kurs getKurs(int kid)
	{
		return kursDao.getKurs(kid);
	}
	public int getOid(int kid)
	{
		return kursDao.getOid(kid);
	}
	public int getAntKursTillf(int kid)
	{
		return kursTillfDao.getAntKurstillf(kid);
	}
	public void updateKurs(Kurs kurs)
	{
		kursDao.updateKurs(kurs);
	}
	public void insertKurs(Kurs kurs)
	{
		kursDao.insertKurs(kurs);
	}
	public void updateKursAll(KursAll kursAll)    
	{
	   kursDao.updateKursAll(kursAll);	  
	}
	public void insertKursAll(KursAll kursAll)
	{
		kursDao.insertKursAll(kursAll);
	}
	public void deleteKurs(int kid)
	{
		kursDao.deleteKurs(kid);
	}
	
	public double calcPrice(int pid, int kid, boolean kursbyte)
	{
		double rabattSats = 0;
		double belopp = 0;
		double rabatt = 0;
		int cnt = kursDao.getRabattKursMiniList(pid, 0).size();
		if (!kursbyte)
			cnt++;
		KursAll kurs = kursDao.getKursAll(kid);
		belopp = kurs.getPris();
		// ex på rabattsträng 0,400,400,400,500,500
		if (kurs.getTypavrabatt() != KursTyp.ingenrabatt)
		{	
			String rabattArr[] = kurs.getRabatter().split(",");
			if (rabattArr.length < cnt)
				rabattSats = Integer.parseInt(rabattArr[rabattArr.length - 1]);
			else
				rabattSats = Integer.parseInt(rabattArr[cnt - 1]);
			if (kurs.getTypavrabatt() == KursTyp.procentrabatt && rabattSats > 0)
				rabatt = (rabattSats * belopp) / 100;
			else
				rabatt = rabattSats;
		}	
		belopp = (int) (belopp - rabatt);
		return belopp;
	}
	public double calcPrice(int pid, int kid)
	{
		return calcPrice(pid, kid, false);
	}
	public KursEkonomi getEkonomi(int kid)
	{
		int inkomster = 0;
		int ikost     = 0;
		int lkost     = 0;
		int mkost     = 0;
		int okost     = 0;		
		int elever    = 0;
		int betalande = 0;
		int br        = 0;
		
		List<Elev> elevList = elevDao.getElevList(kid);
		KursAll ka = kursDao.getKursAll(kid);		
		KursEkonomi ke =  new KursEkonomi();
		for (Elev el: elevList)
		{
			if (el.getBetalt() > 0)
				betalande++;
			elever++;
			inkomster = inkomster + el.getBetalt();
			mkost     = mkost + ka.getMkost();
		}	
		ikost = ka.getIkost() * this.getAntKursTillf(kid);
		lkost = ka.getLkost() * this.getAntKursTillf(kid);		
		okost = ka.getOkost();
		inkomster = inkomster - (int) ((5.66 * inkomster) / 100);
		ke.setPris(ka.getPris());
		ke.setTotkost(ikost + lkost + mkost + okost);
		ke.setBeteckning(ka.getBeteckning());
		ke.setKursnamn(ka.getKursnamn());
		ke.setLokal(ka.getLokalNamn());
		ke.setElever(elever);
		ke.setBetalande(betalande);
		ke.setIkost(ikost);
		ke.setLkost(lkost);
		ke.setMkost(mkost);
		ke.setOkost(okost);
		ke.setInkomster(inkomster);
		ke.setNetto(inkomster - ke.getTotkost());
		br =  ka.getPris() > 0 ? (int) (ke.getTotkost() / ka.getPrisExMoms()) : 0;
		if (ka.getPris() > 0)
			if (ke.getTotkost() % ka.getPris() != 0)
				br++;
		ke.setBreakeven(br);
		logger.info("ex moms:" + ka.getPrisExMoms());
		return ke;
	}
	public List<KursEkonomi> getEkonomi(int[] kidArr)
	{
		
		int ikostTot = 0;
		int lkostTot = 0;
		int mkostTot = 0;
		int okostTot = 0;
		int kostTot  = 0;
		int inkomsterTot = 0;
		int eleverTot = 0;
		int betalandeTot = 0;
		int nettoTot = 0;
		int brTot = 0;
		
		ArrayList<KursEkonomi> ekArr = new ArrayList<KursEkonomi>();
		for (int i = 0; i < kidArr.length; i++)
		{
			KursEkonomi ke = getEkonomi(kidArr[i]);			 
			ikostTot       = ikostTot + ke.getIkost();
			lkostTot       = lkostTot + ke.getLkost();
			mkostTot       = mkostTot + ke.getMkost();
			okostTot       = okostTot + ke.getOkost();
			kostTot        = kostTot  + ke.getTotkost();
			inkomsterTot   = inkomsterTot + ke.getInkomster();
			eleverTot      = eleverTot + ke.getElever();
			betalandeTot   = betalandeTot + ke.getBetalande();
			nettoTot       = nettoTot + ke.getNetto();
			brTot          = brTot + ke.getBreakeven();
			ekArr.add(ke);			
		}
		KursEkonomi totalt = new KursEkonomi();
		totalt.setBeteckning("Totalt");
		totalt.setBetalande(betalandeTot);
		totalt.setElever(eleverTot);
		totalt.setIkost(ikostTot);		
		totalt.setLkost(lkostTot);
		totalt.setMkost(mkostTot);
		totalt.setOkost(okostTot);
		totalt.setTotkost(kostTot);
		totalt.setInkomster(inkomsterTot);
		totalt.setNetto(nettoTot);
		totalt.setBreakeven(brTot);
		ekArr.add(0, totalt);
		return ekArr;
	}
	public StimRapport getStimRapport(int[] kidArr)
	{
		StimRapport sr = new StimRapport();
		for (int i = 0; i < kidArr.length; i++)
		{
			KursAll kurs = kursDao.getKursAll(kidArr[i]);
			for (int j = 1; j <= getAntKursTillf(kidArr[i]); j++ )
			{	
				int nvarande = nvaroManager.getAntalNarvarande(kidArr[i], j);
				if (nvarande <= 20)
					sr.setAntUpptill20(sr.getAntUpptill20() + 1);
				else if (nvarande <= 40)
					sr.setAntUpptill40(sr.getAntUpptill40() + 1);
				else if (nvarande > 40)
					sr.setAnt40Plus(sr.getAnt40Plus() + 1);				
			}	
		}
		sr.setSummaUpptill20(sr.getAntUpptill20() * StimRapport.getPrisupptill20());
		sr.setSummaUpptill40(sr.getAntUpptill40() * StimRapport.getPrisupptill40());
		sr.setSumma40Plus(sr.getAnt40Plus() * StimRapport.getPris40plus());
		sr.setSummaTot(sr.getSummaUpptill20() + sr.getSummaUpptill40() + sr.getSumma40Plus());
		sr.setAntTot(sr.getAntUpptill20() + sr.getAntUpptill40() + sr.getAnt40Plus());
		return sr;
	}
	public void setElevDao(ElevDao elevDao)
	{
		this.elevDao = elevDao;
	}
	public void setKursDao(KursDao kursDao)
	{
		this.kursDao = kursDao;
	}
	public void setKursTillfDao(KursTillfDao kursTillfDao)
	{
		this.kursTillfDao = kursTillfDao;
	}
	public void setNvaroManager(NvaroManager nvaroManager)
	{
		this.nvaroManager = nvaroManager;
	}
}
