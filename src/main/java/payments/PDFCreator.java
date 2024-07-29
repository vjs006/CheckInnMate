package payments;

import accessors.Guest;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import database.MongoModifier;

import java.time.LocalDate;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PDFCreator {

    public void createPDF(Guest guest) throws FileNotFoundException {
        LocalDate date = LocalDate.now();
        SubBill b = new SubBill(); // Create an instance
        HashMap<String, Double> purchase = b.getPurchases();
        double total;

        String path = "invoice.pdf";
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument, PageSize.A4);
        Paragraph producPara = new Paragraph("CHECKINNMATE");
        document.add(producPara.setBold().setFontSize(30f).setTextAlignment(TextAlignment.CENTER));

        float threecol = 190f;
        float threecol1 = 520f;
        float width[] = {threecol1};
        float col[] = {threecol, threecol, threecol};

        Border gb = new SolidBorder(2f);
        Table divider = new Table(width);
        divider.setBorder(gb);
        document.add(divider);

        Paragraph para1 = new  Paragraph("Date: " + String.valueOf(date)).setTextAlignment(TextAlignment.RIGHT);
        document.add(para1);

        Paragraph para = new Paragraph("INVOICE");
        document.add(para.setBold().setFontSize(20f).setTextAlignment(TextAlignment.CENTER));

        Table table = new Table(col);
        table.addCell(new Cell().add(new Paragraph("Description")).setBold());
        table.addCell(new Cell().add(new Paragraph("Quantity")).setTextAlignment(TextAlignment.CENTER).setBold());
        table.addCell(new Cell().add(new Paragraph("Price")).setTextAlignment(TextAlignment.RIGHT).setBold().setMarginRight(15f));
// m has been created only as a sample
        //HashMap<String,Double> m = (new MongoModifier()).getBill(guest.getUsername()).getPurchases();
        HashMap<String,Double> m = guest.getBill().getPurchases();
        total = guest.getBill().getTotalAmount();
        //m.put("Swimming",500.0);
        //m.put("Gym",600.0);

// used for the main one
        List<Product> prodL = new ArrayList<>();
        for (Map.Entry<String, Double> entry : m.entrySet()) {
            //System.out.println();
            String k = entry.getKey();
            Double v = entry.getValue();
            prodL.add(new Product(k, v));
        }
        for (Product prod : prodL) {
            table.addCell(new Cell().add(new Paragraph(prod.getPname())).setBorder(Border.NO_BORDER).setMarginLeft(10f));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(1))).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(prod.getPriceperpiece()))).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).setMarginRight(15f));
        }

        document.add(table.setMarginBottom(30f));
        Paragraph tot = new Paragraph("Total: "+ total);
        document.add(tot.setFontSize(15f).setBold().setTextAlignment(TextAlignment.RIGHT));
        Paragraph gst = new Paragraph("GST : 18% ");
        Double final_value = total * 118 / (double)100;
        document.add(gst.setFontSize(16f).setTextAlignment(TextAlignment.RIGHT));
        Paragraph final_val = new Paragraph("Final Bill "+ final_value);
        document.add(final_val.setFontSize(20f).setTextAlignment(TextAlignment.RIGHT).setBold());
        document.close();
    }
}
