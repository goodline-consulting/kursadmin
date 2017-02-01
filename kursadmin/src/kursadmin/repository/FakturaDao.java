package kursadmin.repository;
import java.util.List;

import kursadmin.domain.Faktura;
import kursadmin.domain.Instruktor;

public interface FakturaDao 
{
	public List<Faktura> getFakturor(int pid);    
    public Faktura getFaktura(int fakturanr);
    public int insertFaktura(Faktura faktura);
    public void updateFaktura(Faktura faktura);
    public void deleteFaktura(int fakturanr);
}













