package rs.ac.uns.ftn.education.repository;

import java.util.List;
import java.util.Optional;

import rs.ac.uns.ftn.education.model.Document;

public interface DocumentRepository extends BaseRepository<Document, Long> {
    List<Document> findByStudent_Id(Long studentId);
    Optional<Document> findByStudent_IdAndName(Long studentId, String name);
}
