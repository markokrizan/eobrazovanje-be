package rs.ac.uns.ftn.education.service.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.stereotype.Service;

@Service
public class ReportService {

  public ByteArrayInputStream generateReport(DocumentCreator documentCreator, Long modelId) throws FileNotFoundException, DocumentException {
    Document document = new Document();
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    PdfWriter.getInstance(document, out);
    document.open();

    documentCreator.create(document, modelId);
  
    document.close();

    return new ByteArrayInputStream(out.toByteArray());
  }
}
