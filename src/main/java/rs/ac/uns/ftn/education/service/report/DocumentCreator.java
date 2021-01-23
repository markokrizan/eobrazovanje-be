package rs.ac.uns.ftn.education.service.report;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

public interface DocumentCreator {
  public DocumentCreatorType getType();
  public void create(Document document, Long modelId) throws DocumentException;
}
