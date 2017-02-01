package kursadmin.service.pdfgen;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Element;

import kursadmin.domain.KursAll;
import kursadmin.service.GenPdfFakturaInterface;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GoodLineLdFakturaImpl implements GenPdfFakturaInterface 
{

    /** The resulting PDF file. */
    public static final String RESULT = "faktura.pdf";
    String LOGGA = null;
   
    public static void main(String [ ] args)
    {
    	Element fakturaElement = new Element("faktura");
    	Element raderElement = new Element("fakturarader");
    	
    	org.jdom.Document xmlFaktura = new org.jdom.Document(fakturaElement);
    	fakturaElement.setAttribute(new Attribute("nr", "3880"));
    	fakturaElement.addContent(new Element("fakturadatum").addContent("2013-01-01"));
    	fakturaElement.addContent(new Element("kundnummer").addContent("4711"));
    	fakturaElement.addContent(new Element("kundnamn").addContent("Per Persson"));
    	fakturaElement.addContent(new Element("belopp").addContent("1000"));
    	fakturaElement.addContent(new Element("moms").addContent("56,60"));
    	fakturaElement.addContent(new Element("betalningsmottagare").addContent("Goodline Consulting AB"));
    	fakturaElement.addContent(new Element("betalningsadress").addContent("c/o Levin, Hammarby Allé 24"));
    	fakturaElement.addContent(new Element("betalningspostadress").addContent("STOCKHOLM"));
    	fakturaElement.addContent(new Element("betalningspostnummer").addContent("120 61"));
    	fakturaElement.addContent(new Element("forfallodag").addContent("2013-02-01"));
    	fakturaElement.addContent(new Element("ocr").addContent("38802"));
    	fakturaElement.addContent(new Element("org").addContent("5303301013"));
    	fakturaElement.addContent(new Element("vat").addContent("SE530330101301"));
    	fakturaElement.addContent(new Element("tfn").addContent("+46732511804"));
    	fakturaElement.addContent(new Element("email").addContent("info@goodline.se"));
    	fakturaElement.addContent(new Element("bankgiro").addContent("471-7575"));
    	fakturaElement.addContent(new Element("plusgiro").addContent(""));

		Element radElement = new Element("fakturarad");	
		radElement.addContent(new Element("spec").addContent("Newcomer 1 (2013VTAR-1A) i Årsta Folkets hus"));
		radElement.addContent(new Element("belopp").addContent("1000"));
		radElement.addContent(new Element("moms").addContent("56,60"));
		raderElement.addContent(radElement);
		/*
		radElement = new Element("fakturarad");	
		radElement.addContent(new Element("spec").addContent("Newcomer 2 (2013VTAR-2A) i Årsta Folkets hus"));
		radElement.addContent(new Element("belopp").addContent("1000"));
		radElement.addContent(new Element("moms").addContent("56,60"));
		raderElement.addContent(radElement);
		*/
		
		fakturaElement.addContent(raderElement);
    	try {
			new GoodLineLdFakturaImpl().genPdfFaktura(xmlFaktura, "3880.pdf", "webapps/kursadmin");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
   
    public void genPdfFaktura(org.jdom.Document xmlFaktura, String filename, String imagePath) throws IOException, DocumentException 
    {   
    	LOGGA = imagePath + "/goodline.tiff";
    	// step 1
        Document document = new Document();
        // step 2
        //System.out.println("Writing to File:" + filename);
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        //PdfPCell cell;
        //Paragraph rubrik;
        //System.out.println("Catalina base:" + System.getProperty("catalina.base"));
        document.setPageSize(PageSize.A4);
        document.setMargins(36, 36, 108, 36);
        document.open();
        document.add(createHeader(xmlFaktura.getRootElement()));
        document.add(createBody(xmlFaktura.getRootElement()));
        document.add(createFooter(xmlFaktura.getRootElement()));
        // step 5
        document.close();        
    }
    
   
    public PdfPCell tomCell()
    {
    	PdfPCell cell;
    	cell = new PdfPCell();
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }
    
    private PdfPTable createFooter(org.jdom.Element xmlFaktura)
	throws IOException, DocumentException
	{
    	PdfPTable table = new PdfPTable(1);
    	PdfPCell cell;
    	table.setSpacingBefore(20);
    	table.setWidthPercentage(90);
    	PdfPTable innerTable = new PdfPTable(4);
    	Paragraph rubrik;
    	
    	rubrik = new Paragraph("Betalningsanvisning:", new Font(FontFamily.HELVETICA, 10, Font.BOLD));
	    cell = new PdfPCell(rubrik);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setColspan(4);
	    cell.setIndent(4);
	    cell.setPaddingTop(8);
	    innerTable.addCell(cell);
	    
	    rubrik = new Paragraph("OBS! Om betalning sker vid bank eller postkontor måste giltig Bg-talong ifyllas. Ange alltid OCR nr.", new Font(FontFamily.HELVETICA, 8, Font.NORMAL));
	    cell = new PdfPCell(rubrik);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setColspan(4);
	    cell.setIndent(4);
	    cell.setPaddingBottom(10);
	    innerTable.addCell(cell);
	    
	    rubrik = new Paragraph("Betalningsmottagare", new Font(FontFamily.HELVETICA, 8, Font.BOLD));
	    cell = new PdfPCell(rubrik);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setColspan(4);
	    cell.setIndent(4);
	    innerTable.addCell(cell);
	    
	    rubrik = new Paragraph(xmlFaktura.getChildText("betalningsmottagare"), new Font(FontFamily.HELVETICA, 8, Font.NORMAL));
	    cell = new PdfPCell(rubrik);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setColspan(4);
	    cell.setIndent(4);
	    innerTable.addCell(cell);

	    rubrik = new Paragraph(xmlFaktura.getChildText("betalningsadress"), new Font(FontFamily.HELVETICA, 8, Font.NORMAL));
	    cell = new PdfPCell(rubrik);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setColspan(4);
	    cell.setIndent(4);
	    innerTable.addCell(cell);
	    
	    rubrik = new Paragraph(xmlFaktura.getChildText("betalningspostnummer") + " " + xmlFaktura.getChildText("betalningspostadress"), new Font(FontFamily.HELVETICA, 8, Font.NORMAL));
	    cell = new PdfPCell(rubrik);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setColspan(4);
	    cell.setIndent(4);
	    cell.setPaddingBottom(10);
	    innerTable.addCell(cell);

    	rubrik = new Paragraph("Förfallodatum", new Font(FontFamily.HELVETICA, 8, Font.BOLD));
	    cell = new PdfPCell(rubrik);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setIndent(4);
	    innerTable.addCell(cell);
	    	   
	    rubrik = new Paragraph("Betalningsreferens(OCR)", new Font(FontFamily.HELVETICA, 8, Font.BOLD));
	    cell = new PdfPCell(rubrik);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    innerTable.addCell(cell);
    	
	    rubrik = new Paragraph("Bankgiro", new Font(FontFamily.HELVETICA, 8, Font.BOLD));
	    cell = new PdfPCell(rubrik);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    innerTable.addCell(cell);
	    
	    rubrik = new Paragraph("Att betala", new Font(FontFamily.HELVETICA, 8, Font.BOLD));
	    cell = new PdfPCell(rubrik);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    innerTable.addCell(cell);
	    
	    cell = new PdfPCell(new Paragraph(xmlFaktura.getChildText("forfallodag"), new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setIndent(4);
	    innerTable.addCell(cell);
	    
	    cell = new PdfPCell(new Paragraph(xmlFaktura.getChildText("ocr"), new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    innerTable.addCell(cell);
    	
	    cell = new PdfPCell(new Paragraph(xmlFaktura.getChildText("bankgiro"), new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    innerTable.addCell(cell);
    	
	    cell = new PdfPCell(new Paragraph(xmlFaktura.getChildText("belopp"), new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setPaddingBottom(10);
	    innerTable.addCell(cell);
	    
	    RoundRectangle roundRectangle = new RoundRectangle(new int[]{ 0xFF, 0x00, 0xFF, 0x00 });
    	cell = new PdfPCell(innerTable);
    	cell.setBackgroundColor(new BaseColor(255,255,224));
    	cell.setCellEvent(roundRectangle);
    	cell.setBorder(PdfPCell.NO_BORDER);
    	table.addCell(cell);
    	return table;
	}
    private PdfPTable createBody(org.jdom.Element xmlFaktura)
		throws IOException, DocumentException
	{
    	PdfPTable table = new PdfPTable(new float[]{6, 2});
    	PdfPCell cell;
    	table.setSpacingBefore(20);
    	table.setWidthPercentage(90);
        table.getDefaultCell().setBackgroundColor(new BaseColor(221,255,204));        
        table.addCell(new Phrase("Benämning", new Font(FontFamily.HELVETICA, 12, Font.BOLD)));
        cell = new PdfPCell(new Phrase("Belopp", new Font(FontFamily.HELVETICA, 12, Font.BOLD)));
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setBackgroundColor(new BaseColor(221,255,204));  
        table.addCell(cell);            
        table.setHeaderRows(1);        
        table.getDefaultCell().setBackgroundColor(null);
        // loopa igenom kurserna
        Element rader = xmlFaktura.getChild("fakturarader");
        List<Element> kursLista = rader.getChildren("fakturarad");
        for (Element kurs : kursLista)
        {	
	        cell = new PdfPCell(new Phrase(kurs.getChildText("spec")));
	        cell.setBorder(PdfPCell.LEFT);
	        cell.setPaddingTop(5);
	        table.addCell(cell);
	        cell = new PdfPCell(new Phrase(kurs.getChildText("belopp")));
	        cell.setBorder(PdfPCell.RIGHT);
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	        cell.setPaddingTop(5);
	        table.addCell(cell);
        } 
        cell = new PdfPCell();
        cell.setBorder(PdfPCell.LEFT);
        cell.setPaddingTop(200);
        table.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(PdfPCell.RIGHT);
        table.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(PdfPCell.LEFT|PdfPCell.BOTTOM);
        table.addCell(cell);
        cell = new PdfPCell();
        cell.setBorder(PdfPCell.RIGHT|PdfPCell.BOTTOM);
        table.addCell(cell);   
        cell = new PdfPCell(createInfo(xmlFaktura));
         
        table.addCell(cell);
        cell = new PdfPCell(createSumma(xmlFaktura));
        table.addCell(cell);
                                    
    	return table;
	}
    public PdfPTable createSumma(org.jdom.Element xmlFaktura)
		throws IOException, DocumentException
	{
    	PdfPTable table = new PdfPTable(2);
    	PdfPCell cell;
    	Paragraph rubrik;
    	rubrik = new Paragraph("Varav moms 6%:", new Font(FontFamily.HELVETICA, 10, Font.BOLD));
	    cell = new PdfPCell(rubrik);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Paragraph(xmlFaktura.getChildText("moms"), new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	    table.addCell(cell);
	    
	    
	    cell = new PdfPCell();
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setColspan(2);		    
	    cell.setPaddingTop(4);
	    table.addCell(cell);
	    
	    rubrik = new Paragraph("Att betala", new Font(FontFamily.HELVETICA, 10, Font.BOLD));
	    cell = new PdfPCell(rubrik);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setColspan(2);
	    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    table.addCell(cell);
	    
	    rubrik = new Paragraph(xmlFaktura.getChildText("belopp"), new Font(FontFamily.HELVETICA, 10, Font.BOLD));
	    cell = new PdfPCell(rubrik);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setColspan(2);	    
	    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    table.addCell(cell);
    	
	    
	    return table;
	}
    public PdfPTable createInfo(org.jdom.Element xmlFaktura)
    	throws IOException, DocumentException
    {
    	PdfPTable table = new PdfPTable(3);
    	PdfPCell cell;
    	Paragraph rubrik;
    	
    	rubrik = new Paragraph("Postadress", new Font(FontFamily.HELVETICA, 8, Font.BOLD));
	    cell = new PdfPCell(rubrik);
	    cell.setIndent(4);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell);
	    
	    rubrik = new Paragraph("Org nummer", new Font(FontFamily.HELVETICA, 8, Font.BOLD));
	    cell = new PdfPCell(rubrik);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell);
    	
	    rubrik = new Paragraph("Telefon", new Font(FontFamily.HELVETICA, 8, Font.BOLD));
	    cell = new PdfPCell(rubrik);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell);
	    
	    
	    cell = new PdfPCell(new Paragraph(xmlFaktura.getChildText("betalningsadress"), new Font(FontFamily.HELVETICA, 8, Font.NORMAL)));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setIndent(4);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Paragraph(xmlFaktura.getChildText("org"), new Font(FontFamily.HELVETICA, 8, Font.NORMAL)));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell);
    	
	    cell = new PdfPCell(new Paragraph(xmlFaktura.getChildText("tfn"), new Font(FontFamily.HELVETICA, 8, Font.NORMAL)));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Paragraph(xmlFaktura.getChildText("betalningspostnummer") + " " + xmlFaktura.getChildText("betalningspostadress"), new Font(FontFamily.HELVETICA, 8, Font.NORMAL)));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setIndent(4);
	    table.addCell(cell);
	    
	    rubrik = new Paragraph("Momsreg-nr/VAT-nr", new Font(FontFamily.HELVETICA, 8, Font.BOLD));
	    cell = new PdfPCell(rubrik);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell);
    	
	    rubrik = new Paragraph("E-mail", new Font(FontFamily.HELVETICA, 8, Font.BOLD));
	    cell = new PdfPCell(rubrik);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 8, Font.NORMAL)));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setIndent(4);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Paragraph(xmlFaktura.getChildText("vat"), new Font(FontFamily.HELVETICA, 8, Font.NORMAL)));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell);
    	
	    cell = new PdfPCell(new Paragraph(xmlFaktura.getChildText("email"), new Font(FontFamily.HELVETICA, 8, Font.NORMAL)));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Paragraph("", new Font(FontFamily.HELVETICA, 8, Font.NORMAL)));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setPaddingTop(10);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Paragraph("Innehar F-skattebevis", new Font(FontFamily.HELVETICA, 8, Font.NORMAL)));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setColspan(2);
	    cell.setPaddingTop(10);
	    cell.setPaddingBottom(10);
	    table.addCell(cell);
	    
	    return table;
    }
    
    public PdfPTable createHeader(org.jdom.Element xmlFaktura)
    	throws IOException, DocumentException
    {
	    PdfPTable table = new PdfPTable(new float[]{5, 3, 3});
	    table.setWidthPercentage(100);
	    table.setSpacingBefore(5);
	    PdfPCell cell;
	    Paragraph rubrik;
	    // a cell with an image
	    Image img = Image.getInstance(LOGGA);
	    img.scalePercent(25);
	    cell = new PdfPCell(img);
	    cell.setBorder(PdfPCell.NO_BORDER);	   
	    table.addCell(cell);
	    rubrik = new Paragraph("FAKTURA", new Font(FontFamily.HELVETICA, 18, Font.BOLD));
	    cell = new PdfPCell(rubrik);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
	    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	    table.addCell(cell);
	    cell = new PdfPCell(new Phrase(xmlFaktura.getAttributeValue("nr")));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
	    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	    table.addCell(cell);
	    table.addCell(tomCell());
	    cell = new PdfPCell(new Phrase("Utskriftsdatum"));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
	    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	    cell.setPaddingTop(10);
	    table.addCell(cell);
	    cell = new PdfPCell(new Phrase(xmlFaktura.getChildText("fakturadatum")));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
	    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	    table.addCell(cell);
	    cell = new PdfPCell(new Phrase("Kundnummer: " + xmlFaktura.getChildText("kundnummer")));
	    cell.setPaddingLeft(25);
	    cell.setPaddingTop(20);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
	    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	    table.addCell(cell);
	    cell = new PdfPCell(new Phrase(xmlFaktura.getChildText("kundnamn"), new Font(FontFamily.HELVETICA, 12, Font.BOLD)));
	    cell.setPaddingTop(20);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
	    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	    table.addCell(cell);
	    table.addCell(tomCell());
	    return table;
    }

    /**
     * Inner class with a cell event that draws a border with rounded corners.
     */
    class RoundRectangle implements PdfPCellEvent {
        /** the border color described as CMYK values. */
        protected int[] color;
        /** Constructs the event using a certain color. */
        public RoundRectangle(int[] color) {
            this.color = color;
        }
        
        public void cellLayout(PdfPCell cell, Rectangle rect,
                PdfContentByte[] canvas) {
            PdfContentByte cb = canvas[PdfPTable.LINECANVAS];
            cb.roundRectangle(
                rect.getLeft() + 1.5f, rect.getBottom() + 1.5f, rect.getWidth() - 3,
                rect.getHeight() - 3, 4);
            cb.setLineWidth(1.5f);
            cb.setCMYKColorStrokeF(color[0], color[1], color[2], color[3]);
            cb.stroke();
        }
    }	
}

	
	
	
	

