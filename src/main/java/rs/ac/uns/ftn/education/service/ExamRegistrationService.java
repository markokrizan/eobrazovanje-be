package rs.ac.uns.ftn.education.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.education.exception.AppException;
import rs.ac.uns.ftn.education.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.education.model.ExamRegistration;
import rs.ac.uns.ftn.education.model.ExamRegistrationStatus;
import rs.ac.uns.ftn.education.repository.ExamRegistrationRepository;

@Service
public class ExamRegistrationService {
  
  @Autowired
  private ExamRegistrationRepository examRegistrationRepository;

  public Page<ExamRegistration> getAll(Pageable pageable) {
    return examRegistrationRepository.findAll(pageable);
  }

  public ExamRegistration getOne(Long examRegistrationId) {    
      return examRegistrationRepository.findById(examRegistrationId)
        .orElseThrow(() -> new ResourceNotFoundException("ExamRegistration", "id", examRegistrationId));
  }

  public Page<ExamRegistration> getStudentExamRegistrations(Long studentId, Pageable pageable) {
    return examRegistrationRepository.findByStudent_Id(studentId, pageable);
  }

  public Page<ExamRegistration> getTeacherExamRegistrations(Long teacherId, Pageable pageable) {
    return examRegistrationRepository.findByExam_Course_Engagements_Teacher_IdAndExamRegistrationStatus(
      teacherId,
      ExamRegistrationStatus.REGISTERED,
      pageable
    );
  }

  public ExamRegistration save(ExamRegistration examRegistration) {
    Optional<ExamRegistration> existingRegistration = examRegistrationRepository.findByExam_IdAndStudent_Id(
      examRegistration.getExam().getId(), 
      examRegistration.getStudent().getId()
    );

    if (!existingRegistration.isEmpty()) {
      throw new AppException(
        "Exam id " + examRegistration.getExam().getId() + " was already registred by student id " + examRegistration.getStudent().getId()
      );
    }

    ExamRegistration savedExamRegistration = examRegistrationRepository.save(examRegistration);
    examRegistrationRepository.refresh(savedExamRegistration);

    return savedExamRegistration;
  }

  public void delete(Long examRegistrationId) {
    examRegistrationRepository.deleteById(examRegistrationId);
  }
}
