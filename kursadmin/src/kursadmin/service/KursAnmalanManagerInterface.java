package kursadmin.service;

import kursadmin.domain.Elev;
import kursadmin.domain.Faktura;
import kursadmin.domain.KursAll;
import kursadmin.domain.KursAnmalan;
import kursadmin.domain.MailConfig;
import kursadmin.domain.Person;


import java.io.Serializable;
import java.util.List;

public interface KursAnmalanManagerInterface extends Serializable
{
	public List<KursAnmalan> getKursAnmalanList();    
    public KursAnmalan getKursAnmalan(int aid);
    public void insertKursAnmalan(KursAnmalan kursAnmalan);
    public KursAll creKursAnmalan(int kid, String fnamn, String enamn, String telefon, String email, String info);
    public void deleteKursAnmalan(int aid);
    public List<Person> getPersoner(String fnamn, String enamn);
    public void sendAnmBekr(Faktura faktura);
    public void sendBetBekr(Faktura faktura);
    public List<Elev> creKursDeltagare(List<KursAnmalan> kaList) throws Exception;        
}










    
    
    
    

