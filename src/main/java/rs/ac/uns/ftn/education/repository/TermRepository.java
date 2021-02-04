package rs.ac.uns.ftn.education.repository;

import java.util.Date;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.education.model.Term;

public interface TermRepository extends BaseRepository<Term, Long> {
  @Query("SELECT t FROM Term t WHERE t.from < CURRENT_DATE AND CURRENT_DATE < t.to")
  Term getCurrentTerm();

  @Query("SELECT t FROM Term t WHERE ?1 <= t.to AND t.from <= ?2")
  Page<Term> findByDateRange(Date from, Date to, Pageable pageable);
}
