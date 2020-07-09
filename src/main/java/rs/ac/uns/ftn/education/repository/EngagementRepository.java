package rs.ac.uns.ftn.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.education.model.Course;

public interface EngagementRepository extends JpaRepository<Course, Long> {
    
}
