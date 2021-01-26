package rs.ac.uns.ftn.education.service.report;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import rs.ac.uns.ftn.education.service.ExamRegistrationService;
import rs.ac.uns.ftn.education.service.GradeService;
import rs.ac.uns.ftn.education.service.StudentService;
import rs.ac.uns.ftn.education.model.Student;
import rs.ac.uns.ftn.education.model.ExamRegistration;
import rs.ac.uns.ftn.education.model.Grade;

@Component
public class StudentDocumentCreator implements DocumentCreator {

  @Autowired
  private StudentService studentService;

  @Autowired
  private GradeService gradeService;

  @Autowired
  private ExamRegistrationService examRegistrationService;

  @Override
  public DocumentCreatorType getType() {
    return DocumentCreatorType.STUDENT;
  }

  @Override
  public void create(Document document, Long studentId) throws DocumentException {
    Student student = studentService.getOne(studentId);

    DocumentCreatorHelper.addTitle(document, "Student report");

    DocumentCreatorHelper.addText(document, "First name: ", DocumentCreatorHelper.FONT_BOLD, false);
    DocumentCreatorHelper.addText(document, student.getFirstName(), null, true);

    DocumentCreatorHelper.addText(document, "Last name: ", DocumentCreatorHelper.FONT_BOLD, false);
    DocumentCreatorHelper.addText(document, student.getLastName(), null, true);

    DocumentCreatorHelper.addText(document, "School id: ", DocumentCreatorHelper.FONT_BOLD, false);
    DocumentCreatorHelper.addText(document, student.getSchoolIdNumber(), null, true);

    DocumentCreatorHelper.addText(document, "Email: ", DocumentCreatorHelper.FONT_BOLD, false);
    DocumentCreatorHelper.addText(document, student.getEmail(), null, true);

    DocumentCreatorHelper.addText(document, "Phone number: ", DocumentCreatorHelper.FONT_BOLD, false);
    DocumentCreatorHelper.addText(document, student.getPhoneNumber(), null, true);

    DocumentCreatorHelper.addText(document, "Study program: ", DocumentCreatorHelper.FONT_BOLD, false);
    DocumentCreatorHelper.addText(document, student.getStudyProgram().getName(), null, true);

    DocumentCreatorHelper.addText(document, "Current year: " , DocumentCreatorHelper.FONT_BOLD, false);
    DocumentCreatorHelper.addText(document, student.getCurrentStudyYear().name(), null, true);

    DocumentCreatorHelper.addText(document, "Financial status: " , DocumentCreatorHelper.FONT_BOLD, false);
    DocumentCreatorHelper.addText(document, student.getFinancialStatus().name(), null, true);

    DocumentCreatorHelper.addSeparator(document);
    DocumentCreatorHelper.addText(document, "Passed exams: ", DocumentCreatorHelper.FONT_BOLD, true);

    Stream<Grade> grades = gradeService.getStudentGrades(studentId, Pageable.unpaged()).get();

    Integer gradeTableColumns = 2;
    String[] gradeTableColumnNames = { "Course", "Grade" }; 
    List<String> gradeTableCells = new ArrayList<String>();

    grades.forEach(grade -> {
      gradeTableCells.add(grade.getExam().getCourse().getName());
      gradeTableCells.add(grade.getGradeValue().toString());
    });

    DocumentCreatorHelper.addTable(document, gradeTableColumns, gradeTableColumnNames, gradeTableCells);
    DocumentCreatorHelper.addNewLine(document);

    DocumentCreatorHelper.addText(document, "Average grade: " + student.getAverageGrade(), DocumentCreatorHelper.FONT_BOLD, true);

    DocumentCreatorHelper.addSeparator(document);

    DocumentCreatorHelper.addText(document, "Exam history: ", DocumentCreatorHelper.FONT_BOLD, true);

    Integer examHistoryTableColumns = 3;
    String[] examHistoryColumnNames = { "Course", "Exam date", "Status" }; 
    List<String> examHistoryCells = new ArrayList<String>();

    Stream<ExamRegistration> examRegistrations = examRegistrationService.getStudentExamRegistrations(studentId, Pageable.unpaged()).get();

    examRegistrations.forEach(examRegistration -> {
      examHistoryCells.add(examRegistration.getExam().getCourse().getName());
      examHistoryCells.add(examRegistration.getExam().getExamDate().toString());
      examHistoryCells.add(examRegistration.getExamRegistrationStatus().name());
    });

    DocumentCreatorHelper.addTable(document, examHistoryTableColumns, examHistoryColumnNames, examHistoryCells);
    DocumentCreatorHelper.addNewLine(document);

    DocumentCreatorHelper.addSeparator(document);
  }
}
