package rs.ac.uns.ftn.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.education.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    
}
