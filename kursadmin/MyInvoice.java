/*
 * This class is part of the book "iText in Action - 2nd Edition"
 * written by Bruno Lowagie (ISBN: 9781935182610)
 * For more info, go to: http://itextpdf.com/examples/
 * This example only works with the AGPL version of iText.
 */

package part1.chapter04;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;



import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class MyInvoice 
{

    /** The resulting PDF file. */
    public static final String RESULT
        = "results/part1/chapter04/first_invoice.pdf";
    public static final String LOGGA
    = "resources/img/goodline.tiff";
    /**
     * Main method.
     * @param    args    no arguments needed
     * @throws DocumentException 
     * @throws IOException
     */
    public static void main(String[] args)
        throws IOException, DocumentException {
        new MyInvoice().createPdf(RESULT);
    }
    
    /**
     * Creates a PDF with information about the movies
     * @param    filename the name of the PDF file that will be created.
     * @throws    DocumentException 
     * @throws    IOException
     */
    public void createPdf(String filename)
        throws IOException, DocumentException 
    {
    	// step 1
        Document document = new Document();
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        //PdfPCell cell;
        //Paragraph rubrik;
        System.out.println("Top" + document.topMargin() + " right " + document.rightMargin() + " left" + document.leftMargin() + "bot " + document.bottomMargin());
        document.setPageSize(PageSize.A4);
        document.setMargins(36, 36, 108, 36);
        document.open();
        document.add(createHeader());
        document.add(createBody());
        document.add(createFooter());
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
    
    public PdfPTable createFooter()
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
	    
	    rubrik = new Paragraph("GoodLine Consulting AB", new Font(FontFamily.HELVETICA, 8, Font.NORMAL));
	    cell = new PdfPCell(rubrik);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setColspan(4);
	    cell.setIndent(4);
	    innerTable.addCell(cell);

	    rubrik = new Paragraph("c/o Levin, Kryssargatan 7", new Font(FontFamily.HELVETICA, 8, Font.NORMAL));
	    cell = new PdfPCell(rubrik);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setColspan(4);
	    cell.setIndent(4);
	    innerTable.addCell(cell);
	    
	    rubrik = new Paragraph("120 63 STOCKHOLM", new Font(FontFamily.HELVETICA, 8, Font.NORMAL));
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
	    
	    cell = new PdfPCell(new Paragraph("2012-02-01", new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setIndent(4);
	    innerTable.addCell(cell);
	    
	    cell = new PdfPCell(new Paragraph("1232323", new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    innerTable.addCell(cell);
    	
	    cell = new PdfPCell(new Paragraph("471-7575", new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    innerTable.addCell(cell);
    	
	    cell = new PdfPCell(new Paragraph("1 000:-", new Font(FontFamily.HELVETICA, 10, Font.BOLD)));
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
    public PdfPTable createBody()
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
        cell = new PdfPCell(new Phrase("Newcomer 1 Vårterminen 2012"));
        cell.setBorder(PdfPCell.LEFT);
        cell.setPaddingTop(10);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("1000,00"));
        cell.setBorder(PdfPCell.RIGHT);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        cell.setPaddingTop(10);
        table.addCell(cell);
        
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
        
        cell = new PdfPCell(createInfo());
         
        table.addCell(cell);
        cell = new PdfPCell(createSumma());
        table.addCell(cell);
                                    
    	return table;
	}
    public PdfPTable createSumma()
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
	    
	    cell = new PdfPCell(new Paragraph("56,60", new Font(FontFamily.HELVETICA, 10, Font.NORMAL)));
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
	    
	    rubrik = new Paragraph("1 000:-", new Font(FontFamily.HELVETICA, 10, Font.BOLD));
	    cell = new PdfPCell(rubrik);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setColspan(2);	    
	    cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    table.addCell(cell);
    	
	    
	    return table;
	}
    public PdfPTable createInfo()
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
	    
	    
	    cell = new PdfPCell(new Paragraph("c/o Levin", new Font(FontFamily.HELVETICA, 8, Font.NORMAL)));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setIndent(4);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Paragraph("556793-8716", new Font(FontFamily.HELVETICA, 8, Font.NORMAL)));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell);
    	
	    cell = new PdfPCell(new Paragraph("073-2511804", new Font(FontFamily.HELVETICA, 8, Font.NORMAL)));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Paragraph("Kryssargatan 7", new Font(FontFamily.HELVETICA, 8, Font.NORMAL)));
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
	    
	    cell = new PdfPCell(new Paragraph("120 63 STOCKHOLM", new Font(FontFamily.HELVETICA, 8, Font.NORMAL)));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setIndent(4);
	    table.addCell(cell);
	    
	    cell = new PdfPCell(new Paragraph("SE556793871601", new Font(FontFamily.HELVETICA, 8, Font.NORMAL)));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    table.addCell(cell);
    	
	    cell = new PdfPCell(new Paragraph("robert@goodline.se", new Font(FontFamily.HELVETICA, 8, Font.NORMAL)));
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
    
    public PdfPTable createHeader()
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
	    cell = new PdfPCell(new Phrase("50130"));
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
	    cell = new PdfPCell(new Phrase("2011-12-29"));
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
	    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	    table.addCell(cell);
	    cell = new PdfPCell(new Phrase("Kundnummer: 2345"));
	    cell.setPaddingLeft(25);
	    cell.setPaddingTop(20);
	    cell.setBorder(PdfPCell.NO_BORDER);
	    cell.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
	    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	    table.addCell(cell);
	    cell = new PdfPCell(new Phrase("Levin, Robert", new Font(FontFamily.HELVETICA, 12, Font.BOLD)));
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
