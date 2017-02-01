package kursadmin.utils;

import java.io.IOException;

import kursadmin.service.GenPdfFakturaInterface;

import com.itextpdf.text.DocumentException;

public class DivTest 
{

	public static void main(String[] args) 
	{
		System.out.println("Classpath:" + System.getProperty("java.class.path"));
		Class cls;
		try 
		{
			cls = Class.forName("kursadmin.service.pdfgen." + "GoodLineLdFakturaImpl");
			GenPdfFakturaInterface gi = (GenPdfFakturaInterface) cls.newInstance();
			gi.genPdfFaktura(null, "3880.pdf", null);
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (InstantiationException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (DocumentException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
