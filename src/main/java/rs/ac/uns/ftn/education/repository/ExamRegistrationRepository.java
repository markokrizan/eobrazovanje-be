package rs.ac.uns.ftn.education.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import rs.ac.uns.ftn.education.model.ExamRegistration;

public interface ExamRegistrationRepository extends BaseRepository<ExamRegistration, Long> {
  Page<ExamRegistration> findByStudent_Id(Long studentId, Pageable pageable);
  Page<ExamRegistration> findByCourse_Engagements_Teacher_Id(Long teacherId, Pageable pageable);
}
