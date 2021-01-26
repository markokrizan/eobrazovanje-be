package rs.ac.uns.ftn.education.service.report;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;

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
import com.itextpdf.text.pdf.draw.LineSeparator;

public class DocumentCreatorHelper {
  public static final Font DEFAULT_FONT = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.BLACK);
  public static final Font FONT_BOLD = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);

  public static void addText(Document document, String text, Font font, Boolean addEmptyLine) throws DocumentException {
    Font textFont = font != null ? font : DEFAULT_FONT;

    Chunk chunk = new Chunk(text, textFont);

    document.add(chunk);

    if (addEmptyLine) {
      addNewLine(document);
    }
  }

  public static void addTitle(Document document, String text) throws DocumentException {
    Paragraph title = new Paragraph(text, new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD));
    title.setAlignment(Element.ALIGN_CENTER);

    document.add(title);
    addNewLine(document);
  }

  public static void addNewLine(Document document) throws DocumentException {
    document.add(new Paragraph("\n"));
  }

  public static void addTable(Document document, Integer columns, String[] columnNames, List<String> cells) throws DocumentException {
    PdfPTable table = new PdfPTable(columns);
    DocumentCreatorHelper.addTableHeader(table, columnNames);
    DocumentCreatorHelper.addRows(table, cells);

    table.setWidthPercentage(100);
    document.add(table);
  }

  public static void addSeparator(Document document) throws DocumentException {
    LineSeparator line = new LineSeparator();
    document.add(line);
    addNewLine(document);
  }

  private static void addTableHeader(PdfPTable table, String[] columnNames) {
    Arrays.stream(columnNames)
      .forEach(columnTitle -> {
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
        header.setBorderWidth(1);
        header.setPhrase(new Phrase(columnTitle));
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(header);
    });
  }

  private static void addRows(PdfPTable table, List<String> cells) {
    cells.stream().forEach(cellText -> table.addCell(cellText));
  }
}
