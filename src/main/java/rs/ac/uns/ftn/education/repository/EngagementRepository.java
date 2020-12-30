package rs.ac.uns.ftn.education.repository;

import rs.ac.uns.ftn.education.model.Engagement;

public interface EngagementRepository extends BaseRepository<Engagement, Long> {
  Engagement findByCourse_IdAndTeacher_Id(Long courseId, Long teacherId);
}
