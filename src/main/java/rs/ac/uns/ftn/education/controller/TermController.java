package rs.ac.uns.ftn.education.controller;

import org.springframework.web.bind.annotation.*;

import rs.ac.uns.ftn.education.model.Term;
import rs.ac.uns.ftn.education.payload.TermRequest;
import rs.ac.uns.ftn.education.service.TermService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class TermController {

  @Autowired
  private TermService termService;

  @Autowired
  private ModelMapper modelMapper;

  @GetMapping("/terms")
  public Page<Term> getAll(@PageableDefault(size = 10) Pageable pageable) {
      return termService.getAll(pageable);
  }

  @GetMapping("/terms/{termId}")
  public Term getOne(@PathVariable("termId") Long termId) {    
      return termService.getOne(termId);
  }

  @PostMapping("/terms")
  @PreAuthorize("hasRole('ADMIN')")
  public Term save(@Valid @RequestBody TermRequest termRequest) {
      Term term = modelMapper.map(termRequest, Term.class);

      return termService.save(term);
  }

  @DeleteMapping("/terms/{termId}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable("termId") Long termId) {
    termService.delete(termId);
  }
}
