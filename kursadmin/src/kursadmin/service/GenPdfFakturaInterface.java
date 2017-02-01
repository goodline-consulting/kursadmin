package kursadmin.service;
import java.io.IOException;

import org.jdom.Document;

import com.itextpdf.text.DocumentException;

public interface GenPdfFakturaInterface 
{
	void genPdfFaktura(org.jdom.Document xmlFaktura, String fileName, String imagePath) throws IOException, DocumentException;	
}
