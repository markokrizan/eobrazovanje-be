package rs.ac.uns.ftn.education.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.education.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    public Optional<Student> findByUserId(Long id);
}
