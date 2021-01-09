package rs.ac.uns.ftn.education.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.education.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.education.model.Term;

import rs.ac.uns.ftn.education.repository.TermRepository;

@Service
public class TermService {

  @Autowired
  private TermRepository termRepository;

  public Page<Term> getAll(Pageable pageable) {
    return termRepository.findAll(pageable);
  }

  public Term getOne(Long termId) {    
      return termRepository.findById(termId)
        .orElseThrow(() -> new ResourceNotFoundException("Term", "id", termId));
  }

  public Term getCurrentTerm() {
    return termRepository.getCurrentTerm();
  }

  public Term save(Term term) {
    return termRepository.save(term);
  }

  public void delete(Long termId) {
    termRepository.deleteById(termId);
  }
}
