package rs.ac.uns.ftn.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.education.model.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    
}
