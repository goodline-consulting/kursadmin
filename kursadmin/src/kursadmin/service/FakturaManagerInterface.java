package kursadmin.service;

import kursadmin.domain.Elev;
import kursadmin.domain.Faktura;
import kursadmin.domain.Lokal;
import kursadmin.domain.Person;

import java.io.Serializable;
import java.util.List;

public interface FakturaManagerInterface extends Serializable
{
	public Faktura fakturera(int fakturanr, List<Elev> elever);
	public Faktura fakturera(Elev elev, int koppling, String info); // TIlläggsfaktura
	public Faktura SkapaKredit(int fakturanr, double belopp, String info);
	public Faktura SkapaKundfordran(Elev elev, double belopp, String info);
	public void setTillGodo(Faktura faktura);
	public double betalaFakturaMedSaldo(Person person, int fakturanr);
	public void utbetala(Faktura faktura);
	public List<Faktura> getFakturor(int pid);    
    public Faktura getFaktura(int fakturanr);
    public org.jdom.Document getXmlFaktura(int fakturanr);
    public int insertFaktura(Faktura faktura);
    public void updateFaktura(Faktura faktura);
    public void updateFakturaRader(int fakturanr);
    public void deleteFaktura(int fakturanr);
}










    
    
    
    

