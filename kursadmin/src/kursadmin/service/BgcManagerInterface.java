package kursadmin.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import kursadmin.domain.Betalning;
import kursadmin.domain.Elev;
import kursadmin.domain.Person;

public interface BgcManagerInterface 
{
	public List<Betalning> getBetalningar(BufferedReader br) throws Exception;
	public void prickaAv(List<Betalning> betalningar);
	public void sparaBetalningar(List<Betalning> betalningar);
	public List<Betalning> searchBetalningar(Date fromDat, Date toDat);
	public List<Betalning> searchOplaceradeBetalningar();
	public List<Betalning> searchPersonBetalningar(String name);
	public List<Date> getDates();
	public void deleteBetalning(int id);
}
