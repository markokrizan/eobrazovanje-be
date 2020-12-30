package rs.ac.uns.ftn.education.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import rs.ac.uns.ftn.education.model.Grade;

public interface GradeRepository extends BaseRepository<Grade, Long> {
  Page<Grade> findByStudent_Id(Long studentId, Pageable pageable);
}
