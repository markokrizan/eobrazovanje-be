package rs.ac.uns.ftn.education.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import rs.ac.uns.ftn.education.model.Course;

public interface CourseRepository extends BaseRepository<Course, Long> {
  Page<Course> findByStudyPrograms_Id(Long studyProgramId, Pageable pageable);
  Page<Course> findByEngagements_Teacher_Id(Long teacherId, Pageable pageable);
}
