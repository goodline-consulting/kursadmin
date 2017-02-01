package kursadmin.repository;
import java.util.List;
import kursadmin.domain.KursAnmalan;

public interface KursAnmalanDao 
{
    public List<KursAnmalan> getKursAnmalanList();    
    public KursAnmalan getKursAnmalan(int aid);
    public void insertKursAnmalan(KursAnmalan kursAnmalan);
    public void deleteKursAnmalan(int aid);
}








