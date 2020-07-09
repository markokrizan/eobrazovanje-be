package rs.ac.uns.ftn.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.education.model.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    
}
