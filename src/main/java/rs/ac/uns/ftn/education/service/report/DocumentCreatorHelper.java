package rs.ac.uns.ftn.education.service.report;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import java.util.stream.Stream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class DocumentCreatorHelper {
  public static final Font DEFAULT_FONT = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.BLACK);

  public static void addText(Document document, String text, Font font) throws DocumentException {
    Font textFont = font != null ? font : DEFAULT_FONT;

    Chunk chunk = new Chunk(text, textFont);

    document.add(chunk);
  }

  public static void addNewLine(Document document) throws DocumentException {
    document.add(new Paragraph("\n"));
  }

  public static void addTable(Document document) throws DocumentException {
    PdfPTable table = new PdfPTable(3);
    DocumentCreatorHelper.addTableHeader(table);
    DocumentCreatorHelper.addRows(table);

    document.add(table);
  }

  private static void addTableHeader(PdfPTable table) {
    Stream.of("column header 1", "column header 2", "column header 3")
      .forEach(columnTitle -> {
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
        header.setBorderWidth(2);
        header.setPhrase(new Phrase(columnTitle));
        table.addCell(header);
    });
  }

  private static void addRows(PdfPTable table) {
    table.addCell("row 1, col 1");
    table.addCell("row 1, col 2");
    table.addCell("row 1, col 3");
  }
}
