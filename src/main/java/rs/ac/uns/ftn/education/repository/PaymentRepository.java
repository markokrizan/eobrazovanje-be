package rs.ac.uns.ftn.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.education.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    
}
