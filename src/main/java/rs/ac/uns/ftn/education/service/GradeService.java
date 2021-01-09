package rs.ac.uns.ftn.education.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.education.exception.AppException;
import rs.ac.uns.ftn.education.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.education.model.ExamRegistration;
import rs.ac.uns.ftn.education.model.ExamRegistrationStatus;
import rs.ac.uns.ftn.education.model.Grade;
import rs.ac.uns.ftn.education.repository.GradeRepository;

@Service
public class GradeService {
  
  @Autowired
  private GradeRepository gradeRepository;

  @Autowired
  private ExamRegistrationService examRegistrationService;

  public Page<Grade> getAll(Pageable pageable) {
    return gradeRepository.findAll(pageable);
  }

  public Page<Grade> getStudentGrades(Long studentId, Pageable pageable) {
    return gradeRepository.findByStudent_Id(studentId, pageable);
  }

  public Grade getOne(Long gradeId) {    
      return gradeRepository.findById(gradeId)
        .orElseThrow(() -> new ResourceNotFoundException("Grade", "id", gradeId));
  }

  public Grade save(Grade grade) {
    Grade savedGrade = gradeRepository.save(grade);
    gradeRepository.refresh(savedGrade);

    ExamRegistration gradeExamRegistration = examRegistrationService.getOne(
      grade.getExamRegistration().getId()
    );
    gradeExamRegistration.setExamRegistrationStatus(ExamRegistrationStatus.PASSED);
    examRegistrationService.save(gradeExamRegistration);

    return savedGrade;
  }

  public void delete(Long gradeId) {
    gradeRepository.deleteById(gradeId);
  }
}
