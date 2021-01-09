package rs.ac.uns.ftn.education.repository;

import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.education.model.Term;

public interface TermRepository extends BaseRepository<Term, Long> {
  @Query("SELECT t FROM Term t WHERE t.from < CURRENT_DATE AND CURRENT_DATE < t.to")
  Term getCurrentTerm();
}
