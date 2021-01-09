package rs.ac.uns.ftn.education.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.education.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.education.model.Exam;
import rs.ac.uns.ftn.education.model.Student;
import rs.ac.uns.ftn.education.model.Term;
import rs.ac.uns.ftn.education.repository.ExamRepository;

@Service
public class ExamService {
  
  @Autowired
  private ExamRepository examRepository;

  @Autowired
  private StudentService studentService;

  @Autowired
  private TermService termService;


  public Page<Exam> getAll(Pageable pageable) {
    return examRepository.findAll(pageable);
  }

  public Exam getOne(Long examId) {    
      return examRepository.findById(examId)
        .orElseThrow(() -> new ResourceNotFoundException("Exam", "id", examId));
  }

  public List<Exam> getPossibleStudentExamRegistrationExams(Long studentId) {
    Student student = studentService.getOne(studentId);
    List<Integer> currentAndPreviousYears = student.getCurrentAndPreviousStudyYears()
      .stream()
      .map(year -> year.ordinal())
      .collect(Collectors.toList());
    Term currentTerm = termService.getCurrentTerm();

    return examRepository.getPossibleStudentExamRegistrationExams(
      studentId, 
      student.getStudyProgram().getId(), 
      currentAndPreviousYears, 
      currentTerm.getId()
    );
  }

  public List<Exam> getPassedExams(Long studentId) {
    Student student = studentService.getOne(studentId);

    return examRepository.getPassedExams(
      studentId,
      student.getStudyProgram().getId()
    );
  }

  public Exam save(Exam exam) {
    Exam savedExam = examRepository.save(exam);
    examRepository.refresh(savedExam);

    return savedExam;
  }

  public void delete(Long examId) {
    examRepository.deleteById(examId);
  }
}
