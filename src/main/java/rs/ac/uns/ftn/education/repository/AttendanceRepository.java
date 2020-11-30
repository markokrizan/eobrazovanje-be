package rs.ac.uns.ftn.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.education.model.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    
}