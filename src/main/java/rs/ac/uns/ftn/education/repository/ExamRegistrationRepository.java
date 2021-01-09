package rs.ac.uns.ftn.education.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import rs.ac.uns.ftn.education.model.ExamRegistration;
import rs.ac.uns.ftn.education.model.ExamRegistrationStatus;

public interface ExamRegistrationRepository extends BaseRepository<ExamRegistration, Long> {
  Page<ExamRegistration> findByStudent_Id(Long studentId, Pageable pageable);
  Page<ExamRegistration> findByExam_Course_Engagements_Teacher_IdAndExamRegistrationStatus(
    Long teacherId,
    ExamRegistrationStatus examRegistrationStatus,
    Pageable pageable
  );
}
