package rs.ac.uns.ftn.education.service.report;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import java.util.Arrays;
import java.util.List;

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

  public static void addTable(Document document, Integer columns, String[] columnNames, List<String> cells) throws DocumentException {
    PdfPTable table = new PdfPTable(columns);
    DocumentCreatorHelper.addTableHeader(table, columnNames);
    DocumentCreatorHelper.addRows(table, cells);

    document.add(table);
  }

  private static void addTableHeader(PdfPTable table, String[] columnNames) {
    Arrays.stream(columnNames)
      .forEach(columnTitle -> {
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
        header.setBorderWidth(2);
        header.setPhrase(new Phrase(columnTitle));
        table.addCell(header);
    });
  }

  private static void addRows(PdfPTable table, List<String> cells) {
    cells.stream().forEach(cellText -> table.addCell(cellText));
  }
}
