package rs.ac.uns.ftn.education.service.report;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import rs.ac.uns.ftn.education.service.GradeService;
import rs.ac.uns.ftn.education.service.StudentService;
import rs.ac.uns.ftn.education.model.Student;
import rs.ac.uns.ftn.education.model.Grade;

import com.itextpdf.text.Font;

@Component
public class StudentDocumentCreator implements DocumentCreator {

  @Autowired
  private StudentService studentService;

  @Autowired
  private GradeService gradeService;

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

    DocumentCreatorHelper.addText(document, "Passed exams: ", null);
    DocumentCreatorHelper.addNewLine(document);

    Stream<Grade> grades = gradeService.getStudentGrades(studentId, Pageable.unpaged()).get();

    Integer numberOfColumns = 2;
    String[] columnNames = { "Course", "Grade" }; 
    List<String> cells = new ArrayList<String>();

    grades.forEach(grade -> {
      cells.add(grade.getExam().getCourse().getName());
      cells.add(grade.getGradeType().name());
    });

    DocumentCreatorHelper.addTable(document, numberOfColumns, columnNames, cells);
  }
}
