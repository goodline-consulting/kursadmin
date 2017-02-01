package kursadmin.repository;
import java.util.List;
import kursadmin.domain.Elev;
import kursadmin.domain.MailMottagare;

public interface MailMottagarDao 
{
	public List<MailMottagare> getMailMottagare();
	public List<MailMottagare> getMailMottagare(String pattern, boolean isEnamn);
	public List<MailMottagare> getMailMottagareEjBetalt();
    public List<MailMottagare> getMailMottagareKurs(int kid);
    public List<MailMottagare> getMailMottagareKursEjBetalt(int kid);
    public List<MailMottagare> getMailMottagareNiva(int kursTyp, int niva);
    public List<MailMottagare> getMailMottagareLokal(int lokal);
    public List<MailMottagare> getMailMottagareInstruktor(int instruktor);    
}













