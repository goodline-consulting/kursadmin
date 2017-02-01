package kursadmin.service;

import kursadmin.domain.KursNiva;
import kursadmin.domain.KursTyp;
import java.io.Serializable;
import java.util.List;

public interface KursTypManagerInterface extends Serializable
{
	public List<KursTyp> getKursTypList();    
	public KursTyp getKursTyp(int tid);
    public void insertKursTyp(KursTyp kursTyp, List<KursNiva> nivaer);
    public boolean deleteKursTyp(int tid);
    public void updateKursTyp(KursTyp kursTyp);
    
    public List<KursNiva> getKursNivaList();
    public List<KursNiva> getKursNivaList(int tid);
    public void insertKursNivaList(List<KursNiva> list, int tid);   
}










    
    
    
    

