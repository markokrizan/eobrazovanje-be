package rs.ac.uns.ftn.education.service.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import rs.ac.uns.ftn.education.service.StudentService;
import rs.ac.uns.ftn.education.model.Student;

import com.itextpdf.text.Font;

@Component
public class StudentDocumentCreator implements DocumentCreator {

  @Autowired
  private StudentService studentService;

  @Override
  public DocumentCreatorType getType() {
    return DocumentCreatorType.STUDENT;
  }

  @Override
  public void create(Document document, Long studentId) throws DocumentException {
    Student student = studentService.getOne(studentId);

    Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);

    DocumentCreatorHelper.addText(document, "Student report", titleFont);
    DocumentCreatorHelper.addNewLine(document);
    DocumentCreatorHelper.addText(document, "First name: " + student.getFirstName(), null);
    DocumentCreatorHelper.addNewLine(document);
    DocumentCreatorHelper.addText(document, "Last name: " + student.getLastName(), null);
    DocumentCreatorHelper.addNewLine(document);
    DocumentCreatorHelper.addText(document, "Study program: " + student.getStudyProgram().getName(), null);
    DocumentCreatorHelper.addNewLine(document);
    DocumentCreatorHelper.addText(document, "Current year: " + student.getCurrentStudyYear(), null);
    DocumentCreatorHelper.addNewLine(document);
    DocumentCreatorHelper.addText(document, "Financial status: " + student.getFinancialStatus(), null);
    DocumentCreatorHelper.addNewLine(document);
    DocumentCreatorHelper.addTable(document);
  }
}
