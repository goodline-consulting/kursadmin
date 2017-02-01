package kursadmin.service;

import kursadmin.domain.Betalning;
import kursadmin.domain.Elev;
import kursadmin.domain.Faktura;
import kursadmin.domain.Fakturarad;
import kursadmin.domain.KursAll;
import kursadmin.domain.KursAnmalan;
import kursadmin.domain.KursTyp;
import kursadmin.domain.Lokal;
import kursadmin.domain.Organisation;
import kursadmin.domain.Person;
import kursadmin.repository.BetalningDao;
import kursadmin.repository.ElevDao;
import kursadmin.repository.FakturaDao;
import kursadmin.repository.OrganisationDao;
import kursadmin.repository.PersonDao;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;


public class FakturaManager implements FakturaManagerInterface
{
	FakturaDao fakturaDao;
	KursManagerInterface kursManager;
	PersonDao personDao;
	ElevDao elevDao;
	OrganisationDao organisationDao;
	BetalningDao betalningDao;
	private final int forfallodagar = 14;
	private final int minibetaldagar = 7;
	HashMap<Integer, KursAll> kurser = null;
	
	public Faktura fakturera(int fakturanr, List<Elev> elever)
	{
		int pid = elever.get(0).getPid();
		int firstKid = 0;  // vi tar reda p� fakturerande organisation utifr�n den f�rsta kursen p� fakturan
		double belopp = 0;
		double beloppTot = 0;
		double rabatt = 0;
		double rabattTot = 0;
		double rabattSats = 0;
		double moms = 0;
		double momsTot = 0;
		double saldo = 0;		
		Faktura faktura = null;
		Calendar gregCal = Calendar.getInstance();		
		int cnt;
		
		kurser = new HashMap<Integer, KursAll>();		
		gregCal.add(Calendar.YEAR, - 1);
		Date betalas = new Date(gregCal.getTimeInMillis());
		
		// Bygg upp en lista med inblandade kurser
		for (Elev elev: elever)
		{
			KursAll kurs = kursManager.getKursAll(elev.getKid());
			kurser.put(new Integer(elev.getKid()), kurs);
			if (firstKid == 0)
				firstKid = kurs.getKid();
		}
		
		// Sortera listan  s� att vi f�r de dyraste kurserna f�rst
    	Collections.sort(elever, new Comparator<Elev>()
    	{    		
            public int compare(Elev e1, Elev e2) 
            {
                KursAll k1 = kurser.get(e1.getKid());
                KursAll k2 = kurser.get(e2.getKid());
            	if (k1.getPris() > k2.getPris())
                	return -1;
                if (k1.getPris() == k2.getPris())
                	return 0;
                return 1;               
            }
        });
    	// Finns tidigare rabattber�ttigade kurser 
    	cnt = kursManager.getRabattKursMiniList(pid, fakturanr).size();
		if (fakturanr == 0)
		{
			faktura = new Faktura();			
			faktura.setPid(elever.get(0).getPid());
			faktura.setFakturanr(insertFaktura(faktura));				
		}
		else
		{	
			faktura = fakturaDao.getFaktura(fakturanr);			
		}
		faktura.setRader(new ArrayList<Fakturarad>());
		// Kolla om kunden ha tillgodo eller skuld
		Person person = personDao.getPerson(pid);
		saldo = person.getSaldo();
		double tmpSaldo = 0;
		if (saldo < 0) // tillgodo
		{	
			tmpSaldo = Math.abs(saldo);
		}	
		
		for (Elev elev: elever)
		{	
			cnt++;			
			KursAll kurs = kurser.get(elev.getKid());
			if (kurs.getStartdatum().before(betalas))
				betalas = kurs.getStartdatum();
			elev.setFakturanr(faktura.getFakturanr());
			
			// Om priset �r satt manuellt s� �r det det som g�ller
			if (elev.isManpris())
			{	
				belopp = elev.getPris();
				// OM det manuella priset �r l�gre an nprmalpriset s� s�tter vi rabbatt.
				// annars motivierar vi ingenting
				if (elev.getPris() < kurs.getPris()) 
					rabatt = kurs.getPris() - belopp;
			}	
			else
			{	
				belopp = kurs.getPris();
				double tmpPris;
				double extraPris = 0;
				double tmpMoms;
				// Kolla om kurstypen har n�gra till�ggskostnader
				if (elev.getLogi() != 0)
				{
					tmpPris = kurs.getRumsPris(elev.getLogi());
					if (tmpPris != 0)
					{	
						extraPris += tmpPris;
						tmpMoms = kurs.getMomsbak() * belopp / 100;  
						Fakturarad fakturarad = new Fakturarad();
						fakturarad.setFakturanr(elev.getFakturanr());
						fakturarad.setBelopp(tmpPris);
						fakturarad.setKid(elev.getKid());
						fakturarad.setMoms(tmpMoms);
						fakturarad.setRabatt(0);
						if (tmpPris < 0)
							fakturarad.setSpec("Avdrag f�r " + kurs.getFakturaTextLogi(elev.getLogi()));
						else
							fakturarad.setSpec("Till�gg f�r " + kurs.getFakturaTextLogi(elev.getLogi()));
						faktura.getRader().add(fakturarad);						
					}	
				}
				if (elev.getMat() != 0)
				{
					tmpPris = kurs.getMatPris(elev.getMat());
					if (tmpPris != 0)
					{	
						extraPris += tmpPris;
						tmpMoms = kurs.getMomsbak() * belopp / 100;  
						Fakturarad fakturarad = new Fakturarad();
						fakturarad.setFakturanr(elev.getFakturanr());
						fakturarad.setBelopp(tmpPris);
						fakturarad.setKid(elev.getKid());
						fakturarad.setMoms(tmpMoms);
						fakturarad.setRabatt(0);
						if (tmpPris < 0)
							fakturarad.setSpec("Avdrag f�r " + kurs.getFakturaTextMat(elev.getMat()));
						else
							fakturarad.setSpec("Till�gg f�r " + kurs.getFakturaTextMat(elev.getMat()));
						faktura.getRader().add(fakturarad);						
					}	
				}
				if (elev.getResa() != 0)
				{
					tmpPris = kurs.getResePris(elev.getResa());
					if (tmpPris != 0)
					{	
						extraPris += tmpPris;
						tmpMoms = kurs.getMomsbak() * belopp / 100;  
						Fakturarad fakturarad = new Fakturarad();
						fakturarad.setFakturanr(elev.getFakturanr());
						fakturarad.setBelopp(tmpPris);
						fakturarad.setKid(elev.getKid());
						fakturarad.setMoms(tmpMoms);
						fakturarad.setRabatt(0);
						if (tmpPris < 0)
							fakturarad.setSpec("Avdrag f�r " + kurs.getFakturaTextResa(elev.getResa()));
						else
							fakturarad.setSpec("Till�gg f�r " + kurs.getFakturaTextResa(elev.getResa()));
						faktura.getRader().add(fakturarad);						
					}
				}
				beloppTot += extraPris;
				rabatt = 0;
	    		// ex p� rabattstr�ng 0,400,400,400,500,500
				// Kolla f�rst om kurstypen hanterar rabatter
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
		    		belopp = (int) (belopp - rabatt);
				}	
	    		elev.setPris((int) (belopp + extraPris));
			}	
			if (tmpSaldo > 0)
			{
				elev.setBetaldatum(new Date());
    			if (tmpSaldo >= elev.getPris())
    			{	
    				elev.setBetalt(elev.getPris());
    				tmpSaldo -= elev.getPris();
    			}	
    			else
    			{
    				elev.setBetalt((int)tmpSaldo);
    				tmpSaldo = 0;
    			}
    			elev.setBbekr(true);    			
			}
			moms = kurs.getMomsbak() * belopp / 100;    		
			// Uppdatera elev och skpa fakturarad
			elevDao.updateElev(elev);
			Fakturarad fakturarad = new Fakturarad();
			fakturarad.setFakturanr(elev.getFakturanr());
			fakturarad.setBelopp(belopp);
			fakturarad.setKid(elev.getKid());
			fakturarad.setMoms(moms);
			fakturarad.setRabatt(rabatt);
			faktura.getRader().add(0,fakturarad);				
    		rabattTot += rabatt; 
    		beloppTot += belopp;
    		momsTot += moms;
		}
		
		if (saldo != 0)
		{
			if (saldo < 0) // tillgodo
			{	
				saldo = Math.abs(saldo);
				faktura.setTillgodo(saldo);
				// om tillgodobeloppet �r st�rre �n fakturabeloppet s� drar vi bara av upp till 0
				if (saldo > beloppTot)
				{	
					saldo = saldo - beloppTot;
					faktura.setBetalt(beloppTot);
					faktura.setBetald(new Date());
					beloppTot = 0;
				}	
				else
				{	
					beloppTot -= saldo;
					faktura.setBetalt(saldo);
					saldo = 0;
				}	
			}	
			else // Vi l�gger p� skulden
			{	
				faktura.setSkuld(saldo);
				beloppTot += saldo;
				saldo = 0;
			}
			person.setSaldo(- saldo);
			personDao.updatePerson(person);
		}
		// F�rfallodatum 2 veckor innan kursstart eller inom en vecka om kursen startat.
		gregCal.setTime(betalas);
		gregCal.add(Calendar.DAY_OF_MONTH, - forfallodagar);
		betalas = gregCal.getTime();
		Date idag = new Date();
		gregCal.setTime(idag);
		gregCal.add(Calendar.DAY_OF_MONTH, minibetaldagar);
		idag = gregCal.getTime();
		if (betalas.before(idag))
			betalas = idag;
		faktura.setBetalas(betalas);
		faktura.setBelopp(beloppTot);
    	faktura.setRabatt(rabattTot);
    	faktura.setMoms(momsTot);
    	faktura.setOid(kursManager.getOid(firstKid));
    	if (faktura.getBelopp() > faktura.getBetalt())
    		faktura.setBetald(null);
    	updateFaktura(faktura);    	
		return faktura;
	}
	
	public void updateFakturaRader(int fakturanr)
	{
		Faktura faktura = fakturaDao.getFaktura(fakturanr);
		faktura.setRader(new ArrayList<Fakturarad>());
		for (Elev elev: elevDao.getEleverPaFaktura(fakturanr))
		{
			KursAll kursAll = kursManager.getKursAll(elev.getKid());
			double moms = (kursAll.getMomsbak() * elev.getPris()) / 100;
			Fakturarad fakturarad = new Fakturarad();
			fakturarad.setFakturanr(elev.getFakturanr());
			fakturarad.setBelopp(elev.getPris());
			fakturarad.setKid(elev.getKid());
			fakturarad.setMoms(moms);
			fakturarad.setRabatt(0);
			faktura.getRader().add(fakturarad);
		}
		fakturaDao.updateFaktura(faktura);		
	}
	public Faktura fakturera(Elev elev, int koppling, String info)
	{
		// Skapar en till�ggsfaktura. Beloppet �r givet av elev.pris
		Faktura faktura = new Faktura();
		faktura.setPid(elev.getPid());
		faktura.setBelopp(elev.getPris());
		KursAll kursAll = kursManager.getKursAll(elev.getKid());
		double moms = (kursAll.getMomsbak() * elev.getPris()) / 100;
		faktura.setMoms(moms);
		faktura.setFakturatyp(Faktura.tillaggsFaktura);
		faktura.setKids("" + elev.getKid());
		faktura.setKopplad(koppling);
		faktura.setInfo(info);
		faktura.setFakturanr(fakturaDao.insertFaktura(faktura));
		faktura.setOid(kursManager.getOid(kursAll.getKid()));
		elev.setBbekr(false);
		elev.setFakturanr(faktura.getFakturanr());
		elev.setBetalt(0);
		elev.setBetaldatum(null);
		elevDao.updateElev(elev);
		faktura.setRader(new ArrayList<Fakturarad>());
		Fakturarad fakturarad = new Fakturarad();
		fakturarad.setFakturanr(elev.getFakturanr());
		fakturarad.setBelopp(elev.getPris());
		fakturarad.setKid(elev.getKid());
		fakturarad.setMoms(moms);
		fakturarad.setRabatt(0);
		faktura.getRader().add(fakturarad);
		fakturaDao.updateFaktura(faktura);
		return faktura;
	}
	public Faktura SkapaKundfordran(Elev elev, double belopp, String info)
	{
		// En kundfordran �r en uppskjuten betalning f�r t.ex ett kursbyte till en dyrare kurs.
		// Fordran tas ut n�sta g�ng kunden faktureras. F�r att det skall finnas n�gon sp�rbarhet till varf�r 
		// kunden har en skuld s� skapas en till�ggsfaktura som �r betald
		Faktura faktura = new Faktura();
		faktura.setPid(elev.getPid());
		faktura.setBelopp(belopp);
		KursAll kursAll = kursManager.getKursAll(elev.getKid());
		double moms = (kursAll.getMomsbak() * belopp) / 100;
		faktura.setMoms(moms);
		faktura.setFakturatyp(Faktura.tillaggsFaktura);
		faktura.setBetalt(belopp);
		faktura.setBetald(new Date());
		faktura.setInfo(info);
		faktura.setRader(new ArrayList<Fakturarad>());
		faktura.setOid(kursManager.getOid(kursAll.getKid()));
		Fakturarad fakturarad = new Fakturarad();			
		fakturarad.setBelopp(belopp);
		fakturarad.setKid(elev.getKid());
		fakturarad.setMoms(moms);
		fakturarad.setRabatt(0);		
		faktura.getRader().add(fakturarad);
		Person person = personDao.getPerson(elev.getPid());		
		person.setSaldo(belopp);
		personDao.updatePerson(person);
		return fakturaDao.getFaktura(fakturaDao.insertFaktura(faktura));
		
	}
	public Faktura SkapaKredit(int fakturanr, double belopp, String info)
	{
		// Kreditfaktura skapas utifr�n en annan faktura. �r den andra fakturan obetald s� anv�nds kreditfakturan
		// f�r att "betala" den andra fakturan. P� s� s�tt s� kommer det inte att g� att placera pengar som eventuellt 
		// kommer in senare. Dessa kommer d� att l�ggas kunden tillgodo.
		// Om den andra fakturan �r betald s� skapas en betalning till kundens saldo. Eventuell utbetalning av
		// krediten sker d� ifr�n person.saldo via betalning  med negativt belopp som pekar p� kunden.
		
		Faktura faktura = fakturaDao.getFaktura(fakturanr);
		Faktura kredit = new Faktura();
		kredit.setPid(faktura.getPid()); 		
		kredit.setFakturanr(insertFaktura(kredit));
		kredit.setFakturatyp(Faktura.kreditFaktura);
		kredit.setKopplad(fakturanr);
		kredit.setInfo(info);
		Person person = personDao.getPerson(faktura.getPid());
		if (belopp == 0)
			kredit.setBelopp(faktura.getBelopp());
		else
			kredit.setBelopp(belopp);
		
		// Kolla om fakturan inte �r betald. I s� fall anv�nds kreditfakturan till att betala fakturan s� l�ngt som m�jligt
		if (faktura.getBetald() == null)
		{
			// Om kreditbeloppet �verstiger vad som �r kvar att betala p� fakturan s� skapas betalningar p� 
			// restbeloppet p� fakturan. Resten blir kvar p� kreditfakturan som obetalt.
			if (kredit.getBelopp() > faktura.getBelopp() - faktura.getBetalt())
			{
				skapaKreditBetalningar((int) (faktura.getBelopp() - faktura.getBetalt()), faktura, kredit, person);
				kredit.setBetalt((int) (faktura.getBelopp() - faktura.getBetalt()));
			}
			else
			{	
				skapaKreditBetalningar((int) kredit.getBelopp(), faktura, kredit, person);
				kredit.setBetald(new Date());
				kredit.setBetalt(kredit.getBelopp());				
			}	
			faktura.setBetalt(kredit.getBetalt());
			if (faktura.getBelopp() == faktura.getBetalt())
				faktura.setBetald(new Date());
		}
		
		// Backa momsen vilket inte �r l�tt. Man f�r ta procentuella momsen p� hela fakturan och dra p� kreditfakturan
		if (belopp == 0) // kreditfakturan �r p� hela beloppet
			kredit.setMoms(- faktura.getMoms());
		else // r�kna ut hur stor del av fakturan som �r moms och s�tt det som moms p� kredit
		{
			double momsProcent = (faktura.getMoms() / faktura.getBelopp()) * 100;
			kredit.setMoms(- (momsProcent * kredit.getBelopp() / 100));
		}		
		faktura.setKrediterat(faktura.getKrediterat() + kredit.getBelopp());
		// S�tt negativa belopp p� kreditfakturan
		kredit.setBelopp(- kredit.getBelopp());
		kredit.setOid(faktura.getOid());
		if (kredit.getBetalt() > 0)
			kredit.setBetalt(- kredit.getBetalt());
		personDao.updatePerson(person);
		fakturaDao.updateFaktura(kredit);
		fakturaDao.updateFaktura(faktura);
		return kredit;
	}
	public void setTillGodo(Faktura faktura)
	{
		// Skapa en betalning ifr�n fakturan till kundens saldo
		Person person = personDao.getPerson(faktura.getPid());
		Betalning betalning = new Betalning();
		betalning.setName(person.getFnamn() + " " + person.getEnamn());
		betalning.setBetalkanal("Kreditfaktura");
		betalning.setAmount(faktura.getBelopp() - faktura.getBetalt());		
		betalning.setInfo(new StringBuffer("Kreditering lagd tillgodo"));
		betalning.setBetaldatum(new Date());   
		betalning.setPlacerat(betalning.getAmount());
		betalning.setPlacerad(true);
		betalning.setPid(faktura.getPid());
		betalning.setFakturanr(faktura.getFakturanr());
		betalningDao.insertBetalning(betalning);
		faktura.setBetalt(faktura.getBelopp());
		faktura.setBetald(new Date());
		fakturaDao.updateFaktura(faktura);
		person.setSaldo(person.getSaldo() + betalning.getAmount());
		personDao.updatePerson(person);
		
	}
	// G�r en utbetalning av en kreditfaktura
	public void utbetala(Faktura faktura)
	{
		// Skapa en betalning ifr�n fakturan till "banken" = oplacerad
		// Betalningen "placeras" sedan n�r bankbetalningen gjorts manuellt - �n s� l�nge
		Person person = personDao.getPerson(faktura.getPid());
		Betalning betalning = new Betalning();
		betalning.setName(person.getFnamn() + " " + person.getEnamn());
		betalning.setBetalkanal("Kreditfaktura");
		betalning.setAmount(faktura.getBelopp());		
		betalning.setInfo(new StringBuffer("Kreditering lagd f�r utbetalning"));
		betalning.setBetaldatum(new Date());   
		betalning.setPlacerat(0);
		betalning.setPlacerad(false);
		betalning.setPid(0);
		betalning.setFakturanr(faktura.getFakturanr());
		betalningDao.insertBetalning(betalning);
		faktura.setBetalt(faktura.getBelopp());
		faktura.setBetald(new Date());
		fakturaDao.updateFaktura(faktura);
		
	}
	public double betalaFakturaMedSaldo(Person person, int fakturanr)
	{
		double returbelopp = 0;
		Faktura faktura = fakturaDao.getFaktura(fakturanr);
		if ((person.getSaldo() >= 0) || faktura.getBetald() != null)
			return (double) person.getSaldo();		
		double belopp = Math.abs(person.getSaldo());		
		double restbelopp = faktura.getBelopp() - faktura.getBetalt();
		
		if (belopp > restbelopp)
			returbelopp = belopp - restbelopp;
		Betalning betalning = new Betalning();
		betalning.setName(person.getFnamn() + " " + person.getEnamn());
		betalning.setBetalkanal("Kundsaldo");
		betalning.setAmount(belopp);		
		betalning.setInfo(new StringBuffer(""));
		betalning.setBetaldatum(new Date());   
		betalning.setPlacerat(belopp);
		betalning.setPlacerad(true);
		betalning.setPid(person.getPid());
		betalning.setFakturanr(faktura.getFakturanr());
		faktura.setBetalt(faktura.getBetalt() + belopp);
		if (faktura.getBelopp() == faktura.getBetalt())
			faktura.setBetald(new Date());
		faktura.setInfo("Betald ifr�n " + betalning.getName() + "'s kundsaldo");
		// Placera ut betalningen p� ing�ende kurser
		for (Fakturarad rad : faktura.getRader())
		{
			Elev elev = elevDao.getElev(rad.getKid(), faktura.getPid());
			elev.setBetaldatum(new Date());
			if (belopp >= elev.getPris())
			{	
				elev.setBetalt(elev.getPris());
				belopp -= elev.getPris();
			}	
			else
			{
				elev.setBetalt((int)belopp);
				belopp = 0;				
			}
			elevDao.updateElev(elev);
			if (belopp == 0)
				break;
		}
		// Vi skickar ingen betalningsbekr�ftelse
		fakturaDao.updateFaktura(faktura);
		betalningDao.insertBetalning(betalning);
		return returbelopp;
	}
	// Betalningsfunktionr f�r fakturor
	public void skapaKreditBetalningar(int belopp, Faktura faktura, Faktura kredit, Person person)
	{
		// F�rst skapar vi betalningen ifr�n kreditfakturan till saldot. Saldot beh�ver aldrig r�ras.
		Betalning betalning = new Betalning();
		betalning.setName(person.getFnamn() + " " + person.getEnamn());
		betalning.setBetalkanal("Kreditfaktura");
		betalning.setAmount(- belopp);		
		betalning.setInfo(new StringBuffer("Kreditering"));
		betalning.setBetaldatum(new Date());   
		betalning.setPlacerat(- belopp);
		betalning.setPlacerad(true);
		betalning.setPid(faktura.getPid());
		betalning.setFakturanr(kredit.getFakturanr());
		
		// Sedan betalningen ifr�n Saldot till fakturan
		Betalning betalning2 = new Betalning();
		betalning2.setName(person.getFnamn() + " " + person.getEnamn());
		betalning2.setBetalkanal("Kundsaldo");
		betalning2.setAmount(belopp);		
		betalning2.setInfo(new StringBuffer("Kreditering"));
		betalning2.setBetaldatum(new Date());   
		betalning2.setPlacerat(belopp);
		betalning2.setPlacerad(true);
		betalning2.setPid(faktura.getPid());
		betalning2.setFakturanr(faktura.getFakturanr());
		
		betalningDao.insertBetalning(betalning);
		betalningDao.insertBetalning(betalning2);
	}
	
	// L�gniv�metoder f�r fakturor 
	public List<Faktura> getFakturor(int pid)
	{
		return fakturaDao.getFakturor(pid);
	}
    public Faktura getFaktura(int fakturanr)
    {
    	return fakturaDao.getFaktura(fakturanr);
    }
    public org.jdom.Document getXmlFaktura(int fakturanr)
    {
    	/*
    	<faktura nr = "1232>
    	<fakturadatum></fakturadatum>
    	<kundnummer></kundnummer>
    	<kundnamn></kundnamn>
    	<belopp></belopp>
    	<moms></moms>
    	<betalningsmottagare>GoodLine consulting</betalningsmottagare>
    	<betalningsadress></betalningsadress>
    	<betalningspostnummer></betalningspostnummer>
    	<betalningspostadress></betalningspostadress>
    	<forfallodatum></forfallodatum>
    	<ocr></ocr>
    	<org></org>
    	<vat></vat>
    	<tfn></tfn>
    	<email></email>
    	<bankgiro></bankgiro>
    	<plusgiro></plusgiro>
    	<fakturarader>
    		<fakturarad>
    			<spec></spec>
    			<belopp></belopp>
    			<moms></moms>
    		</fakturarad>
    	</fakturarader>
    	</faktura>
    	*/
    	Faktura fak = getFaktura(fakturanr);
    	// H�mta fakturerande organisation
    	Organisation organisation = organisationDao.getOrganisation(fak.getOid());
    	// H�mta fakturerad person
    	Person person = personDao.getPerson(fak.getPid());
    	NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("sv", "SE"));	
    	// Skapa xmldokumentet
    	Element fakturaElement = new Element("faktura");
    	Element raderElement = new Element("fakturarader");
    	
    	Document xmlFaktura = new Document(fakturaElement);
    	fakturaElement.setAttribute(new Attribute("nr", "" + fak.getFakturanr()));
    	fakturaElement.addContent(new Element("fakturadatum").addContent("" + fak.getSkapad()));
    	fakturaElement.addContent(new Element("kundnummer").addContent("" + person.getPid()));
    	fakturaElement.addContent(new Element("kundnamn").addContent(person.getHeltNamn()));
    	fakturaElement.addContent(new Element("belopp").addContent("" + nf.format(fak.getBelopp())));
    	fakturaElement.addContent(new Element("moms").addContent("" + nf.format(fak.getMoms())));
    	fakturaElement.addContent(new Element("betalningsmottagare").addContent(organisation.getOrgnamn()));
    	fakturaElement.addContent(new Element("betalningsadress").addContent(organisation.getAdress()));
    	fakturaElement.addContent(new Element("betalningspostadress").addContent(organisation.getPostadress()));
    	fakturaElement.addContent(new Element("betalningspostnummer").addContent(organisation.getPostnr()));
    	fakturaElement.addContent(new Element("forfallodag").addContent("" + fak.getBetalas()));
    	fakturaElement.addContent(new Element("ocr").addContent(fak.getOcr()));
    	fakturaElement.addContent(new Element("org").addContent(organisation.getOrgnr()));
    	fakturaElement.addContent(new Element("vat").addContent(organisation.getVat()));
    	fakturaElement.addContent(new Element("tfn").addContent(organisation.getTelefon()));
    	fakturaElement.addContent(new Element("email").addContent(organisation.getEmail()));
    	fakturaElement.addContent(new Element("bankgiro").addContent(organisation.getBankgiro()));
    	fakturaElement.addContent(new Element("plusgiro").addContent(organisation.getPlusgiro()));
    	
    	// H�mta fakturarader    	
    	for (Fakturarad rad : fak.getRader())
    	{
    		Element radElement = new Element("fakturarad");
    		KursAll kurs = kursManager.getKursAll(rad.getKid());
    		radElement.addContent(new Element("spec").addContent("Linedance " + kurs.getKursnamn() + "(" + kurs.getBeteckning() + ") i " + kurs.getLokalNamn()));
    		radElement.addContent(new Element("belopp").addContent("" + nf.format(rad.getBelopp())));
    		//System.out.println("Belopp:" + nf.format(rad.getBelopp()));
    		radElement.addContent(new Element("moms").addContent("" + rad.getMoms()));
    		raderElement.addContent(radElement);
    	}
    	fakturaElement.addContent(raderElement);
    	return xmlFaktura;
    }
    public int insertFaktura(Faktura faktura)
    {
    	return fakturaDao.insertFaktura(faktura);
    }
    public void updateFaktura(Faktura faktura)
    {
    	fakturaDao.updateFaktura(faktura);
    }
    public void deleteFaktura(int fakturanr)
    { 
    	// Om man tar bort en kreditfaktura s� m�ste man �ven r�kna ned krediterat belopp ifr�n fakturan.
    	Faktura faktura = fakturaDao.getFaktura(fakturanr);
    	if (faktura.getFakturatyp() == Faktura.kreditFaktura)
    	{
    		Faktura huvudFaktura = fakturaDao.getFaktura(faktura.getKopplad());
    		huvudFaktura.setKrediterat(huvudFaktura.getKrediterat() + faktura.getBelopp());
    		fakturaDao.updateFaktura(huvudFaktura);
    	}
    	else // Ta bort betalning p� ekonomisk info p� eleven och eventuella betalningar
    	{
    		
    	}
    	fakturaDao.deleteFaktura(fakturanr);
    }
    
    
    // Hj�lpobjekt
    public void setBetalningDao(BetalningDao betalningDao)
	{
		this.betalningDao = betalningDao;
	}
    public void setKursManager(KursManagerInterface kursManager)
    {
    	this.kursManager = kursManager;
    }
    public void setPersonDao(PersonDao personDao)
    {
    	this.personDao = personDao;
    }
    public void setFakturaDao(FakturaDao fakturaDao)
    {
    	this.fakturaDao = fakturaDao;
    }
    public void setElevDao(ElevDao elevDao)
    {
    	this.elevDao = elevDao;
    }
    public void setOrganisationDao(OrganisationDao organisationDao)
    {
    	this.organisationDao = organisationDao;
    }
    
}





    
    
    
    

