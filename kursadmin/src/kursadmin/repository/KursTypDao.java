package kursadmin.repository;
import java.util.List;
import kursadmin.domain.KursNiva;
import kursadmin.domain.KursTyp;

public interface KursTypDao 
{
    public List<KursTyp> getKursTypList();
    public List<KursNiva> getKursNivaList();
    public List<KursNiva> getKursNivaList(int tid);
    
    public KursTyp getKursTyp(int tid);
    public void insertKursTyp(KursTyp kursTyp, List<KursNiva> nivaer);
    public boolean deleteKursTyp(int tid);
    public void updateKursTyp(KursTyp kursTyp);
    
    public void insertKursNivaList(List<KursNiva> list, int tid);    
}













